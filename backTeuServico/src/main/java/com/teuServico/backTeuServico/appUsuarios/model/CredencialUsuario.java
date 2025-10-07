package com.teuServico.backTeuServico.appUsuarios.model;

import com.teuServico.backTeuServico.appUsuarios.dto.CredenciaisUsuarioRequestDTO;
import com.teuServico.backTeuServico.appUsuarios.model.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "credenciais_usuarios")
public class CredencialUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleEnum role;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    public CredencialUsuario(){
    }

    public CredencialUsuario(CredenciaisUsuarioRequestDTO credenciaisUsuarioRequestDTO){
        this.email = credenciaisUsuarioRequestDTO.getEmail();
        this.senha = credenciaisUsuarioRequestDTO.getSenha();
    }

}
