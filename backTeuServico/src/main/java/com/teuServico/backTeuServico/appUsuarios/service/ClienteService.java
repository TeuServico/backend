package com.teuServico.backTeuServico.appUsuarios.service;

import ch.qos.logback.core.net.server.Client;
import com.teuServico.backTeuServico.appUsuarios.dto.ClienteRequestDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.ClienteResponseDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.CredenciaisUsuarioRequestDTO;
import com.teuServico.backTeuServico.appUsuarios.model.Cliente;
import com.teuServico.backTeuServico.appUsuarios.model.CredencialUsuario;
import com.teuServico.backTeuServico.appUsuarios.model.enums.RoleEnum;
import com.teuServico.backTeuServico.appUsuarios.repository.ClienteRepository;
import com.teuServico.backTeuServico.appUsuarios.repository.CredenciaisUsuarioRepository;
import com.teuServico.backTeuServico.shared.utils.Paginacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    private final UsuarioBaseService usuarioBaseService;
    private final ClienteRepository clienteRepository;
    private final CredenciaisUsuarioRepository credenciaisUsuarioRepository;
    private final Paginacao paginacao;

    @Autowired
    public ClienteService(UsuarioBaseService usuarioBaseService, ClienteRepository clienteRepository, CredenciaisUsuarioRepository credenciaisUsuarioRepository, Paginacao paginacao) {
        this.usuarioBaseService = usuarioBaseService;
        this.clienteRepository = clienteRepository;
        this.credenciaisUsuarioRepository = credenciaisUsuarioRepository;
        this.paginacao = paginacao;
    }


    public ClienteResponseDTO criarUsuarioCliente(CredenciaisUsuarioRequestDTO credenciaisUsuarioRequestDTO, ClienteRequestDTO clienteRequestDTO){
        usuarioBaseService.validarUnicidadeUsuario(clienteRequestDTO.getCpf(), credenciaisUsuarioRequestDTO.getEmail(), clienteRequestDTO.getTelefone());
        CredencialUsuario credencialUsuario = new CredencialUsuario(credenciaisUsuarioRequestDTO);
        credencialUsuario.setRole(RoleEnum.CLIENTE);
        credenciaisUsuarioRepository.save(credencialUsuario);
        Cliente cliente = new Cliente(clienteRequestDTO, credencialUsuario);
        clienteRepository.save(usuarioBaseService.criptografarUsuario(cliente));
        return new ClienteResponseDTO(usuarioBaseService.descriptografarUsuario(cliente));
    }

}
