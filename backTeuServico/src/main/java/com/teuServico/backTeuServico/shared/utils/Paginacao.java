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

@Service
public class Paginacao {

    public <T, R> PaginacaoResponseDTO<R> listarEntidades(int pagina, JpaRepository<T, Long> repository, Function<T, R> mapper, Sort sort) {
        int numeroPagina= pagina;
        numeroPagina = Math.max(0, numeroPagina - 1);
        Pageable pageable = PageRequest.of(numeroPagina, 10, sort);
        Page<T> page = repository.findAll(pageable);
        if (numeroPagina >= page.getTotalPages()) {
            throw new BusinessException("O número da página não pode ser maior que o total de páginas.");
        }
        List<R> conteudo = page.getContent().stream().map(mapper).collect(Collectors.toList());
        return new PaginacaoResponseDTO<>(page.getTotalElements(), page.getTotalPages(), page.getNumber(), conteudo);
    }

}
