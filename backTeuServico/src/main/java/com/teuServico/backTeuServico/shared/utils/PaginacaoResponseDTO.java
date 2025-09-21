package com.teuServico.backTeuServico.shared.utils;

import lombok.Getter;
import java.util.List;

@Getter
public class PaginacaoResponseDTO<T> {
    private long totalElementos;
    private int totalPaginas;
    private int paginaAtual;
    private List<T> conteudo;

    public PaginacaoResponseDTO() {}

    public PaginacaoResponseDTO(long totalElementos, int totalPaginas, int paginaAtual, List<T> conteudo) {
        this.totalElementos = totalElementos;
        this.totalPaginas = totalPaginas;
        this.paginaAtual = paginaAtual + 1;
        this.conteudo = conteudo;
    }
}