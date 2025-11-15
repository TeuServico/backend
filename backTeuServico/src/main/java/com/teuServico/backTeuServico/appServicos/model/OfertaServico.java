package com.teuServico.backTeuServico.appServicos.model;

import com.teuServico.backTeuServico.appServicos.dto.OfertaServicoRequestDTO;
import com.teuServico.backTeuServico.appUsuarios.model.Profissional;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa uma oferta de serviço publicada por um profissional.
 * <p>
 * Contém a descrição da oferta, o tipo de serviço, o profissional responsável e as tags associadas.
 */
@Getter
@Setter
@Entity
@Table(name = "ofertas_servicos", indexes = {
        @Index(name = "idx_id_profissional", columnList = "id_profissional"),
        @Index(name = "idx_tipo_servico", columnList = "tipo_servico")
})
public class OfertaServico {

    /**
     * Identificador único da oferta de serviço.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Tipo de serviço oferecido.
     */
    @ManyToOne
    @JoinColumn(name = "tipo_servico", nullable = false)
    private TipoServico tipoServico;

    /**
     * Profissional responsável pela oferta.
     */
    @ManyToOne
    @JoinColumn(name = "id_profissional", nullable = false)
    private Profissional profissional;

    /**
     * Descrição detalhada da oferta de serviço.
     */
    @Column(nullable = false)
    private String descricao;

    /**
     * Lista de tags associadas à oferta
     */
    @ElementCollection
    @CollectionTable(name = "oferta_servico_tags", joinColumns = @JoinColumn(name = "oferta_servico_id"),
            indexes = {
                    @Index(name = "idx_tag", columnList = "tag"),
                    @Index(name = "idx_oferta_servico_id", columnList = "oferta_servico_id")
            }
    )
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    /**
     * Construtor padrão necessário para JPA.
     */
    public OfertaServico(){

    }

    /**
     * Construtor que inicializa a entidade com base nos dados recebidos via DTO.
     *
     * @param ofertaServicoRequestDTO DTO com os dados da oferta
     * @param tipoServico tipo de serviço associado
     * @param profissional profissional responsável pela oferta
     */
    public OfertaServico(OfertaServicoRequestDTO ofertaServicoRequestDTO, TipoServico tipoServico, Profissional profissional){
        this.descricao = ofertaServicoRequestDTO.getDescricao();
        this.tags = ofertaServicoRequestDTO.getTags();
        this.tipoServico = tipoServico;
        this.profissional = profissional;
    }

}
