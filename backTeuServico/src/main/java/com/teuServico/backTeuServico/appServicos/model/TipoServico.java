package com.teuServico.backTeuServico.appServicos.model;

import com.teuServico.backTeuServico.appServicos.dto.TipoServicoRequestDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade que representa um tipo de serviço disponível na plataforma.
 * <p>
 * Cada tipo de serviço possui um nome único e uma categoria associada.
 * Os dados são persistidos na tabela {@code tipos_servicos}.
 */
@Getter
@Setter
@Entity
@Table(name = "tipos_servicos")
public class TipoServico {

    /**
     * Identificador único do tipo de serviço.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Nome do tipo de serviço.
     * Deve ser único e conter no máximo 100 caracteres.
     */
    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    /**
     * Categoria à qual o tipo de serviço pertence.
     * Limitada a 30 caracteres.
     */
    @Column(length = 30)
    private String categoria;

    /**
     * Construtor padrão necessário para JPA.
     */
    public TipoServico() {}

    /**
     * Construtor que inicializa a entidade com base nos dados recebidos via DTO.
     * @param tipoServicoRequestDTO DTO com os dados do tipo de serviço
     */
    public TipoServico(TipoServicoRequestDTO tipoServicoRequestDTO) {
        this.nome = tipoServicoRequestDTO.getNome();
        this.categoria = tipoServicoRequestDTO.getCategoria();
    }

}