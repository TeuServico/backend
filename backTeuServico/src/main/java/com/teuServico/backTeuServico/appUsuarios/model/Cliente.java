package com.teuServico.backTeuServico.appUsuarios.model;

import com.teuServico.backTeuServico.appUsuarios.dto.ClienteRequestDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Entidade que representa um cliente cadastrado na plataforma.
 * <p>
 * Herda atributos comuns de {@link UsuarioBase}.
 * Utilizada para armazenar e recuperar dados específicos de clientes.
 */
@Getter
@Setter
@Entity
@Table(name = "clientes")
public class Cliente extends UsuarioBase {

    /**
     * Identificador único do cliente.
     * <p>
     * Gerado automaticamente como UUID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Construtor padrão necessário para JPA.
     */
    public Cliente() {}

    /**
     * Construtor que inicializa um cliente com base nos dados recebidos via DTO.
     *
     * @param clienteRequestDTO DTO contendo os dados pessoais do cliente
     * @param credencialUsuario credencial de acesso associada ao cliente
     */
    public Cliente(ClienteRequestDTO clienteRequestDTO, CredencialUsuario credencialUsuario) {
        super.nomeCompleto = clienteRequestDTO.getNomeCompleto();
        super.telefone = clienteRequestDTO.getTelefone();
        super.cpf = clienteRequestDTO.getCpf();
        super.endereco = clienteRequestDTO.getEndereco();
        super.credencialUsuario = credencialUsuario;
    }
}