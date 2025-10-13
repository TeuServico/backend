package com.teuServico.backTeuServico.appUsuarios.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class UsuarioBase {

    @Column(nullable = false, length = 100)
    protected String nomeCompleto;

    @Column(nullable = false, unique = true)
    protected String telefone;

    @Column(nullable = false, unique = true)
    protected String cpf;

    @Embedded
    @Column(nullable = false)
    protected Endereco endereco;

    @Column
    protected String UrlFotoPerfil; // TODO implementar metodo para update de foto de perfil

    @OneToOne
    @JoinColumn(name = "id_credenciais")
    protected CredencialUsuario credencialUsuario;

}
