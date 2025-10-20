package com.teuServico.backTeuServico.appServicos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class OfertaServicoRequestDTO {
    @NotNull(message = "profissionalId é inválido")
    @Positive(message = "profissionalId nao deve ser menor que 1")
    private UUID profissionalId;

    @NotNull(message = "tipoServicoId é inválido")
    @Positive(message = "tipoServicoId nao deve ser menor que 1")
    private Long tipoServicoId;

    @NotBlank(message = "descricao é inválido")
    private String descricao;

}