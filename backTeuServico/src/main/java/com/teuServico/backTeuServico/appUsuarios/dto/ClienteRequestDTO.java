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

@Getter
@Setter
@Schema(description = "Dados pessoais do cliente")
public class ClienteRequestDTO {

    @Schema(description = "Nome completo do cliente", example = "João da Silva")
    @NotBlank(message = "nomeCompleto é inválido")
    @Size(max = 100, message = "nomeCompleto deve ter no máximo 100 caracteres")
    private String nomeCompleto;

    @Schema(description = "Telefone do cliente", example = "81912345678")
    @NotBlank(message = "telefone é inválido")
    private String telefone;

    @Schema(description = "CPF do cliente",example = "64479682090")
    @NotBlank(message = "cpf é inválido")
    @CPF(message = "cpf é inválido")
    private String cpf;

    @Schema(description = "Endereço completo do cliente")
    @NotNull(message = "endereco é obrigatório")
    @Valid
    private Endereco endereco;

}
