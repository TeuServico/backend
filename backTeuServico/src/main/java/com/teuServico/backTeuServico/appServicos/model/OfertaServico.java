package com.teuServico.backTeuServico.appServicos.model;

import com.teuServico.backTeuServico.appServicos.dto.OfertaServicoRequestDTO;
import com.teuServico.backTeuServico.appUsuarios.model.Profissional;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "ofertas_servicos", indexes = {
        @Index(name = "idx_id_profissional", columnList = "id_profissional"),
        @Index(name = "idx_tipo_servico", columnList = "tipo_servico")
})
public class OfertaServico {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tipo_servico", nullable = false)
    private TipoServico tipoServico;

    @ManyToOne
    @JoinColumn(name = "id_profissional", nullable = false)
    private Profissional profissional;

    @Column(nullable = false)
    private String descricao;

    @ElementCollection
    @CollectionTable(name = "oferta_servico_tags", joinColumns = @JoinColumn(name = "oferta_servico_id"),
            indexes = @Index(name = "idx_tag", columnList = "tag")
    )
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    public OfertaServico(){

    }

    public OfertaServico(OfertaServicoRequestDTO ofertaServicoRequestDTO, TipoServico tipoServico, Profissional profissional){
        this.descricao = ofertaServicoRequestDTO.getDescricao();
        this.tags = ofertaServicoRequestDTO.getTags();
        this.tipoServico = tipoServico;
        this.profissional = profissional;
    }

}
