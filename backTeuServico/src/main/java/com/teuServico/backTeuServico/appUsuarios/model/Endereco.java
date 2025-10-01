package com.teuServico.backTeuServico.appUsuarios.model;

import com.teuServico.backTeuServico.appUsuarios.model.enums.EstadoEnum;
import jakarta.persistence.*;

@Embeddable
public class Endereco {
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;

    @Enumerated(EnumType.STRING)
    private EstadoEnum estado;

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