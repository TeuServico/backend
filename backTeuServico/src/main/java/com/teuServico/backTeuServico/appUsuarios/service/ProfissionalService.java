package com.teuServico.backTeuServico.appUsuarios.service;

import com.teuServico.backTeuServico.appUsuarios.dto.*;
import com.teuServico.backTeuServico.appUsuarios.model.CredencialUsuario;
import com.teuServico.backTeuServico.appUsuarios.model.Profissional;
import com.teuServico.backTeuServico.appUsuarios.repository.ProfissionalRepository;
import com.teuServico.backTeuServico.shared.utils.Paginacao;
import com.teuServico.backTeuServico.shared.utils.email.EmailService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Serviço responsável pelas operações relacionadas ao profissional.
 * <p>
 */
@Service
public class ProfissionalService {
    private final UsuarioBaseService usuarioBaseService;
    private final ProfissionalRepository profissionalRepository;
    private final CredenciaisUsuarioService credenciaisUsuarioService;
    private final EmailService emailService;

    /**
     * Construtor da classe {@code ProfissionalService}.
     *
     * @param usuarioBaseService serviço utilitário para operações comuns de usuário
     * @param profissionalRepository repositório de persistência de profissionais
     * @param credenciaisUsuarioService serviço de autenticação e registro de credenciais
     * @param emailService serviço de envio de e-mails
     */
    public ProfissionalService(UsuarioBaseService usuarioBaseService, ProfissionalRepository profissionalRepository, CredenciaisUsuarioService credenciaisUsuarioService, EmailService emailService) {
        this.usuarioBaseService = usuarioBaseService;
        this.profissionalRepository = profissionalRepository;
        this.credenciaisUsuarioService = credenciaisUsuarioService;

        this.emailService = emailService;
    }

    /**
     * Cria um novo profissional no sistema com base nas credenciais e dados pessoais fornecidos.
     * <p>
     * Valida CPF e telefone, registra as credenciais como PROFISSIONAL, salva os dados do profissional
     * criptografados no banco de dados e envia um e-mail de confirmação de criação de conta.
     * @param credenciaisUsuarioRequestDTO DTO contendo e-mail e senha do profissional
     * @param profissionalRequestDTO DTO contendo dados pessoais do profissional.
     * @return {@link TokenJWT} autenticado para o profissional recém-criado
     */
    public TokenJWT criarUsuarioProfissional(CredenciaisUsuarioRequestDTO credenciaisUsuarioRequestDTO, ProfissionalRequestDTO profissionalRequestDTO){
        usuarioBaseService.validarUnicidadeInfoUsuario(profissionalRequestDTO.getCpf(), profissionalRequestDTO.getTelefone());
        CredencialUsuario credencialUsuario = new CredencialUsuario(credenciaisUsuarioRequestDTO);
        TokenJWT tokenJWT = credenciaisUsuarioService.registrar(credencialUsuario, "PROFISSIONAL");
        Profissional profissional = new Profissional(profissionalRequestDTO, credencialUsuario);
        profissionalRepository.save(usuarioBaseService.criptografarUsuario(profissional));
        emailService.enviarEmailDeCriacaoDeConta(credencialUsuario.getEmail());
        return tokenJWT;
    }

    /**
     * Recupera os dados do perfil do profissional autenticado.
     * <p>
     * Utiliza o token JWT para identificar o profissional e retorna os dados formatados
     * em um {@link ProfissionalResponseDTO}.
     *
     * @param token token JWT de autenticação do profissional
     * @return {@link ProfissionalResponseDTO} contendo os dados do perfil do profissional
     */
    public ProfissionalResponseDTO meuPerfil(JwtAuthenticationToken token){
        UUID idCredencial = UUID.fromString(token.getName());
        return usuarioBaseService.meuPerfil(idCredencial, profissionalRepository::findByCredencialUsuario_Id, ProfissionalResponseDTO::new);
    }

}
