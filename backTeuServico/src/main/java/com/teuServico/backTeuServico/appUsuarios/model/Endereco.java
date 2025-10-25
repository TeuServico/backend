package com.teuServico.backTeuServico.appUsuarios.model;

import com.teuServico.backTeuServico.appUsuarios.model.enums.EstadoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@Schema(description = "Endereço completo do usuario")
public class Endereco {
    @Schema(description = "Nome da rua", example = "rua das Flores")
    @NotBlank(message = "rua é inválido")
    @Size(max = 100, message = "rua deve ter no máximo 100 caracteres")
    private String rua;

    @Schema(description = "Número da residência", example = "123")
    @NotBlank(message = "numero é inválido")
    @Size(max = 10, message = "numero deve ter no máximo 5 caracteres")
    private String numero;

    @Schema(description = "Complemento do endereço", example = "apto 202")
    @NotBlank(message = "complemento é inválido")
    @Size(max = 50, message = "complemento deve ter no máximo 50 caracteres")
    private String complemento;

    @Schema(description = "Bairro", example = "boa Viagem")
    @NotBlank(message = "bairro é inválido")
    @Size(max = 100, message = "bairro deve ter no máximo 100 caracteres")
    private String bairro;

    @Schema(description = "Cidade", example = "Recife")
    @NotBlank(message = "cidade é inválido")
    @Size(max = 100, message = "cidade deve ter no máximo 100 caracteres")
    private String cidade;

    @Schema(description = "Estado (UF)", example = "PE")
    @NotNull(message = "estado é inválido")
    @Enumerated(EnumType.STRING)
    private EstadoEnum estado;

    @Schema(description = "CEP", example = "51030300")
    @NotBlank(message = "cep é inválido")
    private String cep;

    public Endereco() {
    }

    public Endereco(String rua, String numero, String complemento, String bairro, String cidade, EstadoEnum estado, String cep) {
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }
}