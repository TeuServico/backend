package com.teuServico.backTeuServico.shared.utils;

import lombok.Getter;
import java.util.List;

/**
 * Classe genérica que representa um ResponseDTO para retornos paginados.
 * @param <T> o tipo do conteudo, isto é, os dados que serao retornados paginados
 */

@Getter
public class PaginacaoResponseDTO<T> {
    /**
     * Número total de elementos disponíveis na consulta.
     */
    private long totalElementos;

    /**
     * Número total de páginas disponíveis.
     */
    private int totalPaginas;

    /**
     * Número da página atual (indexada a partir de 1).
     */
    private int paginaAtual;

    /**
     * Lista de elementos contidos na página atual.
     */
    private List<T> conteudo;


    public PaginacaoResponseDTO() {}

    /**
     * Construtor para inicializar todos os campos da ResponseDTO paginada.
     *
     * @param totalElementos número total de elementos disponíveis.
     * @param totalPaginas número total de páginas disponíveis.
     * @param paginaAtual número da página atual (indexada a partir de 0, mas incrementamos +1, pois começamos com a pagina 1 em vez de pagina 0).
     * @param conteudo lista de elementos da página atual.
     */

    public PaginacaoResponseDTO(long totalElementos, int totalPaginas, int paginaAtual, List<T> conteudo) {
        this.totalElementos = totalElementos;
        this.totalPaginas = totalPaginas;
        this.paginaAtual = paginaAtual + 1;
        this.conteudo = conteudo;
    }
}