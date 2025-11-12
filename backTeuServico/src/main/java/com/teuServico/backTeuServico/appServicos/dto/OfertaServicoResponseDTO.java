package com.teuServico.backTeuServico.appServicos.dto;

import com.teuServico.backTeuServico.appServicos.model.OfertaServico;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

/**
 * DTO utilizado para retornar os dados de uma oferta de serviço.
 * <p>
 * Contém informações da oferta, do tipo de serviço e do profissional responsável.
 */
@Getter
@Setter
public class OfertaServicoResponseDTO {

    /**
     * Identificador único da oferta de serviço.
     */
    @Schema(description = "Identificador único da oferta de serviço")
    private Long id;

    /**
     * Descrição da oferta de serviço.
     */
    @Schema(description = "Descrição da oferta de serviço", example = "Desenvolvimento de uma página web utilizando Java e React")
    private String descricao;

    /**
     * Lista de tags associadas à oferta de serviço.
     */
    @Schema(description = "Tags de uma oferta de servico",example = "[\"Java\", \"Spring\", \"PostgreSQL\", \"React\", \"AWS S3\"]")
    private List<String> tags;

    /**
     * Dados do tipo de serviço associado à oferta.
     */
    private TipoServicoResponseDTO tipoServico;

    /**
     * Identificador único do profissional responsável pela oferta.
     */
    @Schema(description = "Identificador único do profissional")
    private UUID profissionalId;

    /**
     * Nome completo do profissional.
     */
    @Schema(description = "Nome completo do profissional", example = "Rodrigo da Silva")
    private String profissionalNome;

    /**
     * Descrição pessoal do profissional.
     */
    @Schema(description = "Descrição pessoal do profissional", example = "programador apaixonado por resolver problemas com código. especialista em desenvolvimento web, sempre em busca de aprender novas tecnologias e criar soluções que fazem a diferença. café, código e criatividade são meu combustível diário.")
    private String profissionalSobreMim;

    /**
     * Construtor padrão necessário para serialização.
     */
    public OfertaServicoResponseDTO() {
    }

    /**
     * Construtor que inicializa o DTO com base na entidade {@link OfertaServico}.
     *
     * @param ofertaServico entidade contendo os dados da oferta
     */
    public OfertaServicoResponseDTO(OfertaServico ofertaServico) {
        this.id = ofertaServico.getId();
        this.profissionalId = ofertaServico.getProfissional().getId();
        this.profissionalNome = ofertaServico.getProfissional().getNomeCompleto();
        this.profissionalSobreMim = ofertaServico.getProfissional().getSobreMim();
        this.tipoServico = new TipoServicoResponseDTO(ofertaServico.getTipoServico());
        this.descricao = ofertaServico.getDescricao();
        this.tags = ofertaServico.getTags();
    }
}
