package com.teuServico.backTeuServico.appUsuarios.model;

import com.teuServico.backTeuServico.appUsuarios.model.enums.EstadoEnum;
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
public class Endereco {
    @NotBlank(message = "rua é inválido")
    @Size(max = 100, message = "rua deve ter no máximo 100 caracteres")
    private String rua;

    @NotBlank(message = "numero é inválido")
    @Size(max = 10, message = "numero deve ter no máximo 5 caracteres")
    private String numero;

    @NotBlank(message = "complemento é inválido")
    @Size(max = 50, message = "complemento deve ter no máximo 50 caracteres")
    private String complemento;

    @NotBlank(message = "bairro é inválido")
    @Size(max = 100, message = "bairro deve ter no máximo 100 caracteres")
    private String bairro;

    @NotBlank(message = "cidade é inválido")
    @Size(max = 100, message = "cidade deve ter no máximo 100 caracteres")
    private String cidade;

    @NotNull(message = "estado é inválido")
    @Enumerated(EnumType.STRING)
    private EstadoEnum estado;

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