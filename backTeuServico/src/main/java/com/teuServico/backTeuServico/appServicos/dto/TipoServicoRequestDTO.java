package com.teuServico.backTeuServico.appServicos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipoServicoRequestDTO {
    @NotBlank(message = "nome é inválido")
    @Size(max = 30, message = "nome deve ter no máximo 30 caracteres")
    private String nome;

    @NotBlank(message = "categoria é inválido")
    @Size(max = 30, message = "categoria deve ter no máximo 30 caracteres")
    private String categoria;

}
