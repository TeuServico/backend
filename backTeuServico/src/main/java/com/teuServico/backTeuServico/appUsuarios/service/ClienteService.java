package com.teuServico.backTeuServico.appUsuarios.service;

import com.teuServico.backTeuServico.appUsuarios.dto.ClienteRequestDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.ClienteResponseDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.CredenciaisUsuarioRequestDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.TokenJWT;
import com.teuServico.backTeuServico.appUsuarios.model.Cliente;
import com.teuServico.backTeuServico.appUsuarios.model.CredencialUsuario;
import com.teuServico.backTeuServico.appUsuarios.repository.ClienteRepository;
import com.teuServico.backTeuServico.shared.utils.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Serviço responsável pelas operações relacionadas ao cliente.
 * <p>
 * Inclui funcionalidades de criação de conta de cliente e recuperação de perfil,
 * integrando credenciais, dados pessoais e envio de e-mails.
 */
@Service
public class ClienteService {
    private final UsuarioBaseService usuarioBaseService;
    private final ClienteRepository clienteRepository;
    private final CredenciaisUsuarioService credenciaisUsuarioService;
    private final EmailService emailService;

    /**
     * Construtor da classe {@code ClienteService}.
     * @param usuarioBaseService serviço utilitário para operações comuns de usuário
     * @param clienteRepository repositório de persistência de clientes
     * @param credenciaisUsuarioService serviço de autenticação e registro de credenciais
     * @param emailService serviço de envio de e-mails
     */
    @Autowired
    public ClienteService(UsuarioBaseService usuarioBaseService, ClienteRepository clienteRepository, CredenciaisUsuarioService credenciaisUsuarioService, EmailService emailService) {
        this.usuarioBaseService = usuarioBaseService;
        this.clienteRepository = clienteRepository;
        this.credenciaisUsuarioService = credenciaisUsuarioService;
        this.emailService = emailService;
    }

    /**
     * Cria um novo cliente no sistema com base nas credenciais e dados pessoais fornecidos.
     * <p>
     * Valida CPF e telefone, registra as credenciais como CLIENTE, salva os dados do cliente
     * criptografados no banco de dados e envia um e-mail de confirmação de criação de conta.
     * @param credenciaisUsuarioRequestDTO DTO contendo e-mail e senha do cliente
     * @param clienteRequestDTO DTO contendo dados pessoais do cliente
     * @return {@link TokenJWT} autenticado para o cliente recém-criado
     */
    public TokenJWT criarUsuarioCliente(CredenciaisUsuarioRequestDTO credenciaisUsuarioRequestDTO, ClienteRequestDTO clienteRequestDTO){
        usuarioBaseService.validarUnicidadeInfoUsuario(clienteRequestDTO.getCpf(), clienteRequestDTO.getTelefone());
        CredencialUsuario credencialUsuario = new CredencialUsuario(credenciaisUsuarioRequestDTO);
        TokenJWT tokenJWT = credenciaisUsuarioService.registrar(credencialUsuario, "CLIENTE");
        Cliente cliente = new Cliente(clienteRequestDTO, credencialUsuario);
        clienteRepository.save(usuarioBaseService.criptografarUsuario(cliente));
        emailService.enviarEmailDeCriacaoDeConta(credencialUsuario.getEmail());
        return tokenJWT;
    }

    /**
     * Recupera os dados do perfil do cliente autenticado.
     * <p>
     * Utiliza o token JWT para identificar o cliente e retorna os dados descriptografados
     * em um {@link ClienteResponseDTO}.
     * @param token token JWT de autenticação do cliente
     * @return {@link ClienteResponseDTO} contendo os dados do perfil do cliente
     */
    public ClienteResponseDTO meuPerfil(JwtAuthenticationToken token){
        UUID idCredencial = UUID.fromString(token.getName());
        return usuarioBaseService.meuPerfil(idCredencial, clienteRepository::findByCredencialUsuario_Id, ClienteResponseDTO::new);
    }

}
