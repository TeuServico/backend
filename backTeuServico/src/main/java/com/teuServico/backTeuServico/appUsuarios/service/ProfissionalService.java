package com.teuServico.backTeuServico.appUsuarios.service;

import com.teuServico.backTeuServico.appUsuarios.dto.*;
import com.teuServico.backTeuServico.appUsuarios.model.CredencialUsuario;
import com.teuServico.backTeuServico.appUsuarios.model.Profissional;
import com.teuServico.backTeuServico.appUsuarios.repository.ProfissionalRepository;
import com.teuServico.backTeuServico.shared.utils.Paginacao;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfissionalService {
    private final UsuarioBaseService usuarioBaseService;
    private final ProfissionalRepository profissionalRepository;
    private final CredenciaisUsuarioService credenciaisUsuarioService;
    private final Paginacao paginacao;

    public ProfissionalService(UsuarioBaseService usuarioBaseService, ProfissionalRepository profissionalRepository, CredenciaisUsuarioService credenciaisUsuarioService, Paginacao paginacao) {
        this.usuarioBaseService = usuarioBaseService;
        this.profissionalRepository = profissionalRepository;
        this.credenciaisUsuarioService = credenciaisUsuarioService;
        this.paginacao = paginacao;
    }

    public TokenJWT criarUsuarioProfissional(CredenciaisUsuarioRequestDTO credenciaisUsuarioRequestDTO, ProfissionalRequestDTO profissionalRequestDTO){
        usuarioBaseService.validarUnicidadeInfoUsuario(profissionalRequestDTO.getCpf(), profissionalRequestDTO.getTelefone());
        CredencialUsuario credencialUsuario = new CredencialUsuario(credenciaisUsuarioRequestDTO);
        TokenJWT tokenJWT = credenciaisUsuarioService.registrar(credencialUsuario, "PROFISSIONAL");
        Profissional profissional = new Profissional(profissionalRequestDTO, credencialUsuario);
        profissionalRepository.save(usuarioBaseService.criptografarUsuario(profissional));
        return tokenJWT;
    }

    public ProfissionalResponseDTO meuPerfil(JwtAuthenticationToken token){
        UUID idCredencial = UUID.fromString(token.getName());
        return usuarioBaseService.meuPerfil(idCredencial, profissionalRepository::findByCredencialUsuario_Id, ProfissionalResponseDTO::new);
    }

}
