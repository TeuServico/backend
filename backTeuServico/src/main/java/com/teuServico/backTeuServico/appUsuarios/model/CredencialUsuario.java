package com.teuServico.backTeuServico.appUsuarios.model;

import com.teuServico.backTeuServico.appUsuarios.dto.CredenciaisUsuarioRequestDTO;
import com.teuServico.backTeuServico.appUsuarios.model.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Entidade que representa as credenciais de autenticação de um usuário na plataforma.
 * <p>
 * Contém informações como email, senha e o papel (role) do usuário, como CLIENTE ou PROFISSIONAL.
 * É associada a uma entidade de usuário via relacionamento {@code @OneToOne}.
 */
@Getter
@Setter
@Entity
@Table(name = "credenciais_usuarios")
public class CredencialUsuario {

    /**
     * Identificador único da credencial.
     * <p>
     * Gerado automaticamente como UUID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Papel (role) do usuário na plataforma.
     * <p>
     * Pode ser CLIENTE, PROFISSIONAL. conforme definido em {@link RoleEnum}.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleEnum role;

    /**
     * Email do usuário utilizado para login.
     * <p>
     * Deve ser único e previamente normalizado.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Senha do usuário utilizada para autenticação.
     * <p>
     * criptografada com Bcrypt.
     */
    @Column(nullable = false)
    private String senha;

    /**
     * Construtor padrão necessário para JPA.
     */
    public CredencialUsuario(){
    }

    /**
     * Construtor que inicializa a credencial com base nos dados recebidos via DTO.
     *
     * @param credenciaisUsuarioRequestDTO DTO contendo e-mail e senha do usuário
     */
    public CredencialUsuario(CredenciaisUsuarioRequestDTO credenciaisUsuarioRequestDTO){
        this.email = credenciaisUsuarioRequestDTO.getEmail();
        this.senha = credenciaisUsuarioRequestDTO.getSenha();
    }
}