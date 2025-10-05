package com.teuServico.backTeuServico.appUsuarios.dto;

import com.teuServico.backTeuServico.appUsuarios.model.Cliente;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class ClienteResponseDTO {
    private UUID id;
    private String nomeCompleto;
    private String email;
    private String telefone;

    public ClienteResponseDTO() {
    }

    public ClienteResponseDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nomeCompleto = cliente.getNomeCompleto();
        this.email = cliente.getCredencialUsuario().getEmail();
        this.telefone = cliente.getTelefone();
    }
}