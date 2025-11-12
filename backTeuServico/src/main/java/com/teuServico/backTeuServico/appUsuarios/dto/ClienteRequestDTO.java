package com.teuServico.backTeuServico.appUsuarios.dto;

import com.teuServico.backTeuServico.appUsuarios.model.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

/**
 * DTO utilizado para receber os dados pessoais do cliente no momento do cadastro.
 * <p>
 */
@Getter
@Setter
@Schema(description = "Dados pessoais do cliente")
public class ClienteRequestDTO {

    /**
     * Nome completo do cliente.
     * <p>
     * Campo obrigatório, limitado a 100 caracteres.
     */
    @Schema(description = "Nome completo do cliente", example = "João da Silva")
    @NotBlank(message = "nomeCompleto é inválido")
    @Size(max = 100, message = "nomeCompleto deve ter no máximo 100 caracteres")
    private String nomeCompleto;

    /**
     * Número de telefone do cliente.
     * <p>
     * Campo obrigatório, deve conter apenas dígitos númericos.
     */
    @Schema(description = "Telefone do cliente", example = "81912345678")
    @NotBlank(message = "telefone é inválido")
    private String telefone;

    /**
     * CPF do cliente.
     * <p>
     * Campo obrigatório
     */
    @Schema(description = "CPF do cliente",example = "64479682090")
    @NotBlank(message = "cpf é inválido")
    @CPF(message = "cpf é inválido")
    private String cpf;

    /**
     * Endereço completo do cliente.
     * <p>
     * Campo obrigatório e validado via bean {@link Endereco}.
     */
    @Schema(description = "Endereço completo do cliente")
    @NotNull(message = "endereco é obrigatório")
    @Valid
    private Endereco endereco;
}