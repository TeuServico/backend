package com.teuServico.backTeuServico.appUsuarios.model;

import com.teuServico.backTeuServico.appUsuarios.dto.ProfissionalRequestDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Entidade que representa um profissional cadastrado na plataforma.
 * <p>
 * Herda atributos comuns de {@link UsuarioBase}.
 * Adiciona campos específicos como profissão e sobre mim.
 */
@Getter
@Setter
@Entity
@Table(name = "profissionais")
public class Profissional extends UsuarioBase {

    /**
     * Identificador único do profissional.
     * <p>
     * Gerado automaticamente como UUID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Texto descritivo sobre o profissional.
     * <p>
     * Campo opcional que pode conter informações adicionais como experiência, formação ou especializações.
     */
    @Column
    private String sobreMim;

    /**
     * Profissão principal do profissional.
     * <p>
     * Campo obrigatório que identifica a área de atuação.
     */
    @Column(nullable = false)
    private String profissao;

    /**
     * Construtor padrão necessário para JPA.
     */
    public Profissional(){}

    /**
     * Construtor que inicializa um profissional com base nos dados recebidos via DTO.
     *
     * @param profissionalRequestDTO DTO contendo os dados pessoais e profissionais
     * @param credencialUsuario credencial de acesso associada ao profissional
     */
    public Profissional(ProfissionalRequestDTO profissionalRequestDTO, CredencialUsuario credencialUsuario){
        super.nomeCompleto = profissionalRequestDTO.getNomeCompleto();
        super.telefone = profissionalRequestDTO.getTelefone();
        super.cpf = profissionalRequestDTO.getCpf();
        super.endereco = profissionalRequestDTO.getEndereco();
        super.credencialUsuario = credencialUsuario;
        this.sobreMim = profissionalRequestDTO.getSobreMim();
        this.profissao = profissionalRequestDTO.getProfissao();
    }
}