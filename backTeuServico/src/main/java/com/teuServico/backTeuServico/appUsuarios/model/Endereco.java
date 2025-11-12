package com.teuServico.backTeuServico.appUsuarios.model;

import com.teuServico.backTeuServico.appUsuarios.model.enums.EstadoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa o endereço completo de um usuário.
 * <p>
 * Esta classe é embutida em {@link UsuarioBase} e contém os dados de localização do usuario e eles serão criptografados
 */
@Getter
@Setter
@Embeddable
@Schema(description = "Endereço completo do usuario")
public class Endereco {

    /**
     * Nome da rua do endereço.
     */
    @Schema(description = "Nome da rua", example = "rua das Flores")
    @NotBlank(message = "rua é inválido")
    @Size(max = 100, message = "rua deve ter no máximo 100 caracteres")
    private String rua;

    /**
     * Número da residência ou unidade.
     */
    @Schema(description = "Número da residência", example = "123")
    @NotBlank(message = "numero é inválido")
    @Size(max = 10, message = "numero deve ter no máximo 5 caracteres")
    private String numero;

    /**
     * Complemento do endereço, como bloco, apartamento ou referência.
     */
    @Schema(description = "Complemento do endereço", example = "apto 202")
    @NotBlank(message = "complemento é inválido")
    @Size(max = 50, message = "complemento deve ter no máximo 50 caracteres")
    private String complemento;

    /**
     * Bairro onde o usuário reside.
     */
    @Schema(description = "Bairro", example = "boa Viagem")
    @NotBlank(message = "bairro é inválido")
    @Size(max = 100, message = "bairro deve ter no máximo 100 caracteres")
    private String bairro;

    /**
     * Cidade onde o usuário reside.
     */
    @Schema(description = "Cidade", example = "Recife")
    @NotBlank(message = "cidade é inválido")
    @Size(max = 100, message = "cidade deve ter no máximo 100 caracteres")
    private String cidade;

    /**
     * Estado (UF) onde o usuário reside.
     */
    @Schema(description = "Estado (UF)", example = "PE")
    @NotNull(message = "estado é inválido")
    @Enumerated(EnumType.STRING)
    private EstadoEnum estado;

    /**
     * Código de Endereçamento Postal (CEP).
     */
    @Schema(description = "CEP", example = "51030300")
    @NotBlank(message = "cep é inválido")
    private String cep;

    /**
     * Construtor padrão necessário para JPA.
     */
    public Endereco() {
    }

    /**
     * Construtor que inicializa todos os campos do endereço.
     *
     * @param rua nome da rua
     * @param numero número da residência
     * @param complemento complemento do endereço
     * @param bairro bairro
     * @param cidade cidade
     * @param estado estado (UF)
     * @param cep código postal
     */
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