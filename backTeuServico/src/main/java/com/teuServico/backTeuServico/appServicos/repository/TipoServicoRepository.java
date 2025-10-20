package com.teuServico.backTeuServico.appServicos.repository;

import com.teuServico.backTeuServico.appServicos.model.TipoServico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoServicoRepository extends JpaRepository<TipoServico, Long> {
    Optional<TipoServico> findFirstByNome(String nome);
    Optional<TipoServico> findFirstByCategoria(String catetgoria);
    Page<TipoServico> findByCategoria(String categoria,Pageable pageable);

}
