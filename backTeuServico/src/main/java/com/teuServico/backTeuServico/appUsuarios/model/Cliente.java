package com.teuServico.backTeuServico.appUsuarios.model;

import com.teuServico.backTeuServico.appUsuarios.dto.ClienteRequestDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "clientes")
public class Cliente extends UsuarioBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    public Cliente() {}

    public Cliente(ClienteRequestDTO clienteRequestDTO) {
        // TODO obter dados de clienteRequestDTO
    }

}