package com.teuServico.backTeuServico.appServicos.dto;

import com.teuServico.backTeuServico.appServicos.model.OfertaServico;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class OfertaServicoResponseDTO {
    private Long id;
    private UUID profissionalId;
    private String profissionalNome;
    private Long tipoServicoId;
    private String tipoServicoNome;
    private String descricao;

    public OfertaServicoResponseDTO() {
    }

    public OfertaServicoResponseDTO(OfertaServico ofertaServico) {
        this.id = ofertaServico.getId();
        this.profissionalId = ofertaServico.getProfissional().getId();
        this.profissionalNome = ofertaServico.getProfissional().getNomeCompleto();
        this.tipoServicoId = ofertaServico.getTipoServico().getId();
        this.tipoServicoNome = ofertaServico.getTipoServico().getNome();
        this.descricao = ofertaServico.getDescricao();
    }
}
