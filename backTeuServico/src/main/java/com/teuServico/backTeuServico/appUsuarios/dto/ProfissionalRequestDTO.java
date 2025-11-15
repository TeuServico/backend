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
 * DTO utilizado para receber os dados pessoais e profissionais do profissional no momento de cadastro.
 * <p>
 */
@Getter
@Setter
public class ProfissionalRequestDTO {

    /**
     * Nome completo do profissional.
     */
    @Schema(description = "Nome completo do profissional", example = "Rodrigo da Silva")
    @NotBlank(message = "nomeCompleto é inválido")
    @Size(max = 100, message = "nomeCompleto deve ter no máximo 100 caracteres")
    private String nomeCompleto;

    /**
     * Número de telefone do profissional.
     */
    @Schema(description = "Telefone do profissional", example = "81987654321")
    @NotBlank(message = "telefone é inválido")
    private String telefone;

    /**
     * CPF do profissional.
     */
    @Schema(description = "CPF do profissional", example = "22559547023")
    @NotBlank(message = "cpf é inválido")
    @CPF(message = "cpf é inválido")
    private String cpf;

    /**
     * Endereço completo do profissional.
     */
    @Schema(description = "Endereço completo do profissional")
    @NotNull(message = "endereco é obrigatório")
    @Valid
    private Endereco endereco;

    /**
     * Descrição pessoal do profissional.
     */
    @Schema(description = "Sobre mim do profissional", example = "Programador apaixonado por resolver problemas com código. Especialista em desenvolvimento web, sempre em busca de aprender novas tecnologias e criar soluções que fazem a diferença. Café, código e criatividade são meu combustível diário.")
    private String sobreMim;

    /**
     * Profissão do profissional.
     */
    @Schema(description = "Profissão do profissional", example = "PROGRAMADOR")
    @NotBlank(message = "profissao é obrigatorio")
    @Size(max = 40, message = "profissao não pode ultrapassar 40 caracteres")
    private String profissao;
}