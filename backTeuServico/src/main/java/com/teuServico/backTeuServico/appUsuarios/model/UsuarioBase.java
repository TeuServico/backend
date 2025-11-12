package com.teuServico.backTeuServico.appUsuarios.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe base abstrata para os usuários do sistema, como {@link Cliente} e {@link Profissional}.
 * <p>
 * Contém atributos comuns como nome, CPF, telefone, endereço, foto de perfil e credenciais de acesso.
 * Utilizada como superclasse mapeada para herança JPA.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class UsuarioBase {

    /**
     * Nome completo do usuário.
     * <p>
     * Campo obrigatório com limite de 100 caracteres e armazenado criptografada
     */
    @Column(nullable = false, length = 100)
    protected String nomeCompleto;

    /**
     * Número de telefone do usuário, armazenado de forma única e criptografada.
     */
    @Column(nullable = false, unique = true)
    protected String telefone;

    /**
     * CPF do usuário, armazenado de forma única e criptografada.
     */
    @Column(nullable = false, unique = true)
    protected String cpf;

    /**
     * Endereço completo do usuário, representado por um objeto embutido.
     */
    @Embedded
    @Column(nullable = false)
    protected Endereco endereco;

    /**
     * URL da foto de perfil do usuário.
     * <p>
     * Campo opcional
     */
    @Column
    protected String UrlFotoPerfil; // TODO implementar metodo para update de foto de perfil

    /**
     * Credencial de acesso associada ao usuário.
     * <p>
     * Relacionamento um-para-um com a entidade {@link CredencialUsuario}.
     */
    @OneToOne
    @JoinColumn(name = "id_credenciais")
    protected CredencialUsuario credencialUsuario;
}