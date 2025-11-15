package com.teuServico.backTeuServico.agendamento.repository;

import com.teuServico.backTeuServico.agendamento.model.Agendamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repositório responsável pelas operações de acesso a dados da entidade {@link Agendamento}.
 * <p>
 * Estende {@link JpaRepository} para fornecer métodos padrão de persistência e paginação.
 */
@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {
    /**
     * Retorna uma página de agendamentos vinculados ao cliente informado.
     * @param idCliente identificador do cliente
     * @param pageable informações de paginação
     * @return página de agendamentos do cliente
     */
    Page<Agendamento> findByCliente_Id(UUID idCliente, Pageable pageable);

    /**
     * Retorna uma página de agendamentos vinculados ao profissional informado.
     * @param idProfissional identificador do profissional
     * @param pageable informações de paginação
     * @return página de agendamentos do profissional
     */
    Page<Agendamento> findByOfertaServico_Profissional_Id(UUID idProfissional, Pageable pageable);
}
