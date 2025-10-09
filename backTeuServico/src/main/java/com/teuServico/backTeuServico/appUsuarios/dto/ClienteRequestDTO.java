package com.teuServico.backTeuServico.appUsuarios.dto;

import com.teuServico.backTeuServico.appUsuarios.model.Endereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
public class ClienteRequestDTO {
    @NotBlank(message = "nomeCompleto é inválido")
    @Size(max = 100, message = "nomeCompleto deve ter no máximo 100 caracteres")
    private String nomeCompleto;

    @NotBlank(message = "telefone é inválido")
    private String telefone;

    @NotBlank(message = "cpf é inválido")
    @CPF(message = "cpf é inválido")
    private String cpf;

    @NotNull(message = "endereco é obrigatório")
    @Valid
    private Endereco endereco;

}
