package com.teuServico.backTeuServico.appServicos.repository;

import com.teuServico.backTeuServico.appServicos.model.TipoServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoServicoRepository extends JpaRepository<TipoServico, Long> {
}
