package com.teuServico.backTeuServico.appServicos.repository;

import com.teuServico.backTeuServico.appServicos.model.OfertaServico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.UUID;

@Repository
public interface OfertaServicoRepository extends JpaRepository<OfertaServico, Long> {
    Page<OfertaServico> findByProfissional_Id(UUID idProfissional, Pageable pageable);
    Page<OfertaServico> findByTipoServico_NomeContaining(String categoria, Pageable pageable);
    Page<OfertaServico> findByTipoServico_Categoria(String categoria, Pageable pageable);
}
