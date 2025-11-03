package com.teuServico.backTeuServico.appServicos.dto;

import com.teuServico.backTeuServico.appServicos.model.OfertaServico;
import com.teuServico.backTeuServico.appServicos.model.TipoServico;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OfertaServicoResponseDTO {
    @Schema(description = "Identificador único da oferta de serviço")
    private Long id;
    @Schema(description = "Identificador único do profissional")
    private UUID profissionalId;

    @Schema(description = "Nome completo do profissional", example = "Rodrigo da Silva")
    private String profissionalNome;

    @Schema(description = "Descrição pessoal do profissional", example = "programador apaixonado por resolver problemas com código. especialista em desenvolvimento web, sempre em busca de aprender novas tecnologias e criar soluções que fazem a diferença. café, código e criatividade são meu combustível diário.")
    private String profissionalSobreMim;

    private TipoServicoResponseDTO tipoServico;
    @Schema(description = "Descrição da oferta de serviço", example = "Desenvolvimento de uma página web utilizando Java e React")
    private String descricao;

    @Schema(description = "Tags de uma oferta de servico",example = "[\"Java\", \"Spring\", \"PostgreSQL\", \"React\", \"AWS S3\"]")
    private List<String> tags;

    public OfertaServicoResponseDTO() {
    }

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
