package com.teuServico.backTeuServico.appServicos.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class OfertaServicoRequestDTO {
    private UUID profissionalId;
    private Long tipoServicoId;
    private String descricao;
}