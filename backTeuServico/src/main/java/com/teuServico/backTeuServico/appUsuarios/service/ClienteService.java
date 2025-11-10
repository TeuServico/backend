package com.teuServico.backTeuServico.appUsuarios.service;

import com.teuServico.backTeuServico.appUsuarios.dto.ClienteRequestDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.ClienteResponseDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.CredenciaisUsuarioRequestDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.TokenJWT;
import com.teuServico.backTeuServico.appUsuarios.model.Cliente;
import com.teuServico.backTeuServico.appUsuarios.model.CredencialUsuario;
import com.teuServico.backTeuServico.appUsuarios.repository.ClienteRepository;
import com.teuServico.backTeuServico.shared.utils.Paginacao;
import com.teuServico.backTeuServico.shared.utils.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClienteService {
    private final UsuarioBaseService usuarioBaseService;
    private final ClienteRepository clienteRepository;
    private final CredenciaisUsuarioService credenciaisUsuarioService;
    private final EmailService emailService;

    @Autowired
    public ClienteService(UsuarioBaseService usuarioBaseService, ClienteRepository clienteRepository, CredenciaisUsuarioService credenciaisUsuarioService, EmailService emailService) {
        this.usuarioBaseService = usuarioBaseService;
        this.clienteRepository = clienteRepository;
        this.credenciaisUsuarioService = credenciaisUsuarioService;
        this.emailService = emailService;
    }

    public TokenJWT criarUsuarioCliente(CredenciaisUsuarioRequestDTO credenciaisUsuarioRequestDTO, ClienteRequestDTO clienteRequestDTO){
        usuarioBaseService.validarUnicidadeInfoUsuario(clienteRequestDTO.getCpf(), clienteRequestDTO.getTelefone());
        CredencialUsuario credencialUsuario = new CredencialUsuario(credenciaisUsuarioRequestDTO);
        TokenJWT tokenJWT = credenciaisUsuarioService.registrar(credencialUsuario, "CLIENTE");
        Cliente cliente = new Cliente(clienteRequestDTO, credencialUsuario);
        clienteRepository.save(usuarioBaseService.criptografarUsuario(cliente));
        emailService.enviarEmailDeCriacaoDeConta(credencialUsuario.getEmail());
        return tokenJWT;
    }

    public ClienteResponseDTO meuPerfil(JwtAuthenticationToken token){
        UUID idCredencial = UUID.fromString(token.getName());
        return usuarioBaseService.meuPerfil(idCredencial, clienteRepository::findByCredencialUsuario_Id, ClienteResponseDTO::new);
    }

}
