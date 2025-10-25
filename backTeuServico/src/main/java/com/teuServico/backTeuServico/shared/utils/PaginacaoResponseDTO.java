package com.teuServico.backTeuServico.shared.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

/**
 * Classe genérica que representa um ResponseDTO para retornos paginados.
 * @param <T> o tipo do conteúdo, isto é, os dados que serão retornados paginados
 */
@Getter
@Schema(description = "Estrutura de resposta para dados paginados")
public class PaginacaoResponseDTO<T> {

    @Schema(description = "Número total de elementos disponíveis na consulta", example = "1")
    private long totalElementos;

    @Schema(description = "Número total de páginas disponíveis", example = "1")
    private int totalPaginas;

    @Schema(description = "Número da página atual (começando em 1)", example = "1")
    private int paginaAtual;

    @Schema(description = "Lista de elementos contidos na página atual")
    private List<T> conteudo;

    public PaginacaoResponseDTO() {}

    public PaginacaoResponseDTO(long totalElementos, int totalPaginas, int paginaAtual, List<T> conteudo) {
        this.totalElementos = totalElementos;
        this.totalPaginas = totalPaginas;
        this.paginaAtual = paginaAtual + 1;
        this.conteudo = conteudo;
    }
}