package com.teuServico.backTeuServico.appUsuarios.controller;

import com.teuServico.backTeuServico.appUsuarios.dto.ClienteRequestDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.ClienteResponseDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.CredenciaisUsuarioRequestDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.CriarClienteDTO;
import com.teuServico.backTeuServico.appUsuarios.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente/")
public class ClienteController {
    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("criarCliente")
    public ClienteResponseDTO criarCliente(@RequestBody @Valid CriarClienteDTO criarClienteDTO){
        return clienteService.criarUsuarioCliente(criarClienteDTO.getCredenciaisUsuarioRequestDTO(), criarClienteDTO.getClienteRequestDTO());
    }
}
