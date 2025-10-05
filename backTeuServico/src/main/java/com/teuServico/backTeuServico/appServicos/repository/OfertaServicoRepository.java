package com.teuServico.backTeuServico.appServicos.repository;

import com.teuServico.backTeuServico.appServicos.model.OfertaServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfertaServicoRepository extends JpaRepository<OfertaServico, Long> {
}
