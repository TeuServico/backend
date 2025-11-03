package com.teuServico.backTeuServico.agendamento.repository;

import com.teuServico.backTeuServico.agendamento.model.Agendamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {
    Page<Agendamento> findByCliente_Id(UUID idCliente, Pageable pageable);
    Page<Agendamento> findByOfertaServico_Profissional_Id(UUID idProfissional, Pageable pageable);
}
