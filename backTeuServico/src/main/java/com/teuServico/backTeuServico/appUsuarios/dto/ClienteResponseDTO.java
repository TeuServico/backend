package com.teuServico.backTeuServico.appUsuarios.dto;

import com.teuServico.backTeuServico.appUsuarios.model.Cliente;
import com.teuServico.backTeuServico.appUsuarios.model.Endereco;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class ClienteResponseDTO {
    private UUID id;
    private String nomeCompleto;
    private String telefone;
    private String cpf;
    private Endereco endereco;

    public ClienteResponseDTO() {
    }

    public ClienteResponseDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nomeCompleto = cliente.getNomeCompleto();
        this.telefone = cliente.getTelefone();
        this.cpf = cliente.getCpf();
        this.endereco = cliente.getEndereco();
    }
}