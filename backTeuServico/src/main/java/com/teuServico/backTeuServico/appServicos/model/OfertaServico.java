package com.teuServico.backTeuServico.appServicos.model;

import com.teuServico.backTeuServico.appServicos.dto.OfertaServicoRequestDTO;
import com.teuServico.backTeuServico.appUsuarios.model.Profissional;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ofertas_servicos")
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

    public OfertaServico(){

    }

    public OfertaServico(OfertaServicoRequestDTO ofertaServicoRequestDTO){
        // TODO obter dados de ofertaServicoRequestDTO
    }

}
