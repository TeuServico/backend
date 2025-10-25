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
public class ProfissionalRequestDTO {
    @Schema(description = "Nome completo do cliente", example = "Rodrigo da Silva")
    @NotBlank(message = "nomeCompleto é inválido")
    @Size(max = 100, message = "nomeCompleto deve ter no máximo 100 caracteres")
    private String nomeCompleto;

    @Schema(description = "Telefone do cliente", example = "81987654321")
    @NotBlank(message = "telefone é inválido")
    private String telefone;

    @Schema(description = "CPF do cliente",example = "22559547023")
    @NotBlank(message = "cpf é inválido")
    @CPF(message = "cpf é inválido")
    private String cpf;

    @Schema(description = "Endereço completo do cliente")
    @NotNull(message = "endereco é obrigatório")
    @Valid
    private Endereco endereco;

    @Schema(description = "Sobre mim do profissional", example = "Programador apaixonado por resolver problemas com código. Especialista em desenvolvimento web, sempre em busca de aprender novas tecnologias e criar soluções que fazem a diferença. Café, código e criatividade são meu combustível diário.")
    private String sobreMim;

    @Schema(description = "Profissão do profissional", example = "PROGRAMADOR")
    @NotBlank(message = "profissao é obrigatorio")
    @Size(max = 40, message = "profissao não pode ultrapassar 40 caracteres")
    private String profissao;
}