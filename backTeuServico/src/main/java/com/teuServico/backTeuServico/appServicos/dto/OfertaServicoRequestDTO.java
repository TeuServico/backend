package com.teuservico.backTeuServico.agendamento.appServicos.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class OfertaServicoRequestDTO {
    private UUID profissionalId;
    private Long tipoServicoId;
    private BigDecimal preco;
    private Integer duracaoMinutos;
    private String descricao;
}