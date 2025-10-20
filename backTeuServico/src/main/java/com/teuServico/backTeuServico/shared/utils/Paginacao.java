package com.teuServico.backTeuServico.shared.utils;
import com.teuServico.backTeuServico.shared.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Serviço utilitário responsável por realizar a paginação de entidades genéricas.
 */
@Service
public class Paginacao {
    /**
     * Lista entidades de forma paginada a partir de um repositório JPA
     *
     * @param <T> Tipo da entidade original.
     * @param <R> Tipo mapeado para retorno(ReponseDTO)
     * @param pagina Número da página solicitada (base 1).
     * @param qtdMaximaDeElementos Numeros de elementos que estarao presentes em um página
     * @param repository Repositório JPA da entidade.
     * @param mapper Função de mapeamento da entidade original para o tipo de retorno(ReponseDTO) do conteudo
     * @param sort Critério de ordenação a ser aplicado.
     * @return Um objeto {@link PaginacaoResponseDTO} contendo os dados paginados
     * @throws BusinessException Se o número da página for maior que o total de páginas disponíveis.
     */
    public <T, R, ID> PaginacaoResponseDTO<R> listarTodos(int pagina, int qtdMaximaDeElementos, JpaRepository<T, ID> repository, Function<T, R> mapper, Sort sort) {
        int numeroPagina= pagina;
        numeroPagina = Math.max(0, numeroPagina - 1);
        Pageable pageable = PageRequest.of(numeroPagina, qtdMaximaDeElementos, sort);
        Page<T> page = repository.findAll(pageable);
        if (page.getTotalPages() >= 1 && numeroPagina >= page.getTotalPages()) {
            throw new BusinessException("O número da página não pode ser maior que o total de páginas.");
        }
        List<R> conteudo = page.getContent().stream().map(mapper).collect(Collectors.toList());
        return new PaginacaoResponseDTO<>(page.getTotalElements(), page.getTotalPages(), page.getNumber(), conteudo);
    }


    /**
     * Lista entidades de forma paginada a partir de um metodo de busca Pageable -> Page
     *
     * @param <T> Tipo da entidade original.
     * @param <R> Tipo mapeado para retorno(ReponseDTO)
     * @param pagina Número da página solicitada (base 1).
     * @param qtdMaximaDeElementos Numeros de elementos que estarao presentes em um página
     * @param metodoDeBusca função que recebe um {@link Pageable} e retorna uma {@link Page} de elementos do tipo {@code T}
     * @param mapper Função de mapeamento da entidade original para o tipo de retorno(ReponseDTO) do conteudo
     * @param sort Critério de ordenação a ser aplicado.
     * @return Um objeto {@link PaginacaoResponseDTO} contendo os dados paginados
     * @throws BusinessException Se o número da página for maior que o total de páginas disponíveis.
     */

    public <T, R> PaginacaoResponseDTO<R> listarPor(int pagina, int qtdMaximaDeElementos, Function<Pageable, Page<T>> metodoDeBusca, Function<T, R> mapper, Sort sort) {
        int numeroPagina = Math.max(0, pagina - 1);
        Pageable pageable = PageRequest.of(numeroPagina, qtdMaximaDeElementos, sort);
        Page<T> page = metodoDeBusca.apply(pageable);
        if (page.getTotalPages() >= 1 && numeroPagina >= page.getTotalPages()) {
            throw new BusinessException("O número da página não pode ser maior que o total de páginas.");
        }
        List<R> conteudo = page.getContent().stream().map(mapper).collect(Collectors.toList());
        return new PaginacaoResponseDTO<>(page.getTotalElements(), page.getTotalPages(), page.getNumber(), conteudo);
    }


}
