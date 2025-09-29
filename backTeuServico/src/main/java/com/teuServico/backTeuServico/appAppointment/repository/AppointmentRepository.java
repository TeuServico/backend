package com.teuService.backTeuService.repository;

import com.teuService.backTeuService.entity.Agendamento;
import com.teuService.backTeuService.entity.enums.StatusAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para gerenciar operações de Agendamento (Appointment)
 * Fornece métodos para consultas personalizadas relacionadas a agendamentos
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Agendamento, Long> {

    // ==================== CONSULTAS POR CLIENTE ====================

    /**
     * Busca todos os agendamentos de um cliente específico
     */
    List<Agendamento> findByClienteId(Long clienteId);

    /**
     * Busca agendamentos de um cliente com status específico
     */
    @Query("SELECT a FROM Agendamento a WHERE a.cliente.id = :clienteId AND a.statusAgendamento = :status")
    List<Agendamento> findByClienteIdAndStatus(@Param("clienteId") Long clienteId,
                                               @Param("status") StatusAgendamento status);

    /**
     * Busca próximos agendamentos do cliente (data futura)
     */
    @Query("SELECT a FROM Agendamento a WHERE a.cliente.id = :clienteId AND a.dataHora >= :dataInicio ORDER BY a.dataHora ASC")
    List<Agendamento> findProximosAgendamentosCliente(@Param("clienteId") Long clienteId,
                                                      @Param("dataInicio") LocalDateTime dataInicio);

    /**
     * Busca histórico de agendamentos do cliente (datas passadas)
     */
    @Query("SELECT a FROM Agendamento a WHERE a.cliente.id = :clienteId AND a.dataHora < :dataAtual ORDER BY a.dataHora DESC")
    List<Agendamento> findHistoricoAgendamentosCliente(@Param("clienteId") Long clienteId,
                                                       @Param("dataAtual") LocalDateTime dataAtual);

    /**
     * Conta total de agendamentos de um cliente
     */
    @Query("SELECT COUNT(a) FROM Agendamento a WHERE a.cliente.id = :clienteId")
    long countByClienteId(@Param("clienteId") Long clienteId);

    // ==================== CONSULTAS POR PROFISSIONAL ====================

    /**
     * Busca todos os agendamentos de um profissional
     */
    @Query("SELECT a FROM Agendamento a WHERE a.servicoOfertado.profissional.id = :profissionalId")
    List<Agendamento> findByProfissionalId(@Param("profissionalId") Long profissionalId);

    /**
     * Busca agendamentos de um profissional com status específico
     */
    @Query("SELECT a FROM Agendamento a WHERE a.servicoOfertado.profissional.id = :profissionalId AND a.statusAgendamento = :status")
    List<Agendamento> findByProfissionalIdAndStatus(@Param("profissionalId") Long profissionalId,
                                                    @Param("status") StatusAgendamento status);

    /**
     * Busca próximos agendamentos do profissional
     */
    @Query("SELECT a FROM Agendamento a WHERE a.servicoOfertado.profissional.id = :profissionalId AND a.dataHora >= :dataInicio ORDER BY a.dataHora ASC")
    List<Agendamento> findProximosAgendamentosProfissional(@Param("profissionalId") Long profissionalId,
                                                           @Param("dataInicio") LocalDateTime dataInicio);

    /**
     * Busca agendamentos do profissional em um período específico
     */
    @Query("SELECT a FROM Agendamento a WHERE a.servicoOfertado.profissional.id = :profissionalId " +
            "AND a.dataHora BETWEEN :inicio AND :fim ORDER BY a.dataHora ASC")
    List<Agendamento> findByProfissionalIdAndDataHoraBetween(@Param("profissionalId") Long profissionalId,
                                                             @Param("inicio") LocalDateTime inicio,
                                                             @Param("fim") LocalDateTime fim);

    /**
     * Busca agendamentos do profissional em uma data específica
     */
    @Query("SELECT a FROM Agendamento a WHERE a.servicoOfertado.profissional.id = :profissionalId " +
            "AND DATE(a.dataHora) = :data ORDER BY a.dataHora ASC")
    List<Agendamento> findByProfissionalIdAndData(@Param("profissionalId") Long profissionalId,
                                                  @Param("data") LocalDate data);

    /**
     * Conta serviços concluídos por um profissional
     */
    @Query("SELECT COUNT(a) FROM Agendamento a WHERE a.servicoOfertado.profissional.id = :profissionalId " +
            "AND a.statusAgendamento = 'CONCLUIDO'")
    long countServicosConcluidos(@Param("profissionalId") Long profissionalId);

    // ==================== CONSULTAS POR STATUS ====================

    /**
     * Busca todos os agendamentos por status
     */
    List<Agendamento> findByStatusAgendamento(StatusAgendamento status);

    /**
     * Busca agendamentos pendentes (AGENDADO ou CONFIRMADO) até uma data específica
     */
    @Query("SELECT a FROM Agendamento a WHERE a.statusAgendamento IN ('AGENDADO', 'CONFIRMADO') " +
            "AND a.dataHora <= :dataLimite ORDER BY a.dataHora ASC")
    List<Agendamento> findAgendamentosPendentesAte(@Param("dataLimite") LocalDateTime dataLimite);

    /**
     * Busca agendamentos que podem ter sido perdidos (não compareceu)
     */
    @Query("SELECT a FROM Agendamento a WHERE a.statusAgendamento IN ('AGENDADO', 'CONFIRMADO') " +
            "AND a.dataHora < :dataAtual")
    List<Agendamento> findAgendamentosNaoFinalizados(@Param("dataAtual") LocalDateTime dataAtual);

    // ==================== CONSULTAS POR PERÍODO ====================

    /**
     * Busca agendamentos em um período específico
     */
    @Query("SELECT a FROM Agendamento a WHERE a.dataHora BETWEEN :inicio AND :fim ORDER BY a.dataHora ASC")
    List<Agendamento> findByDataHoraBetween(@Param("inicio") LocalDateTime inicio,
                                            @Param("fim") LocalDateTime fim);

    /**
     * Busca agendamentos de uma data específica
     */
    @Query("SELECT a FROM Agendamento a WHERE DATE(a.dataHora) = :data ORDER BY a.dataHora ASC")
    List<Agendamento> findByData(@Param("data") LocalDate data);

    /**
     * Busca agendamentos futuros
     */
    @Query("SELECT a FROM Agendamento a WHERE a.dataHora >= :dataInicio ORDER BY a.dataHora ASC")
    List<Agendamento> findAgendamentosFuturos(@Param("dataInicio") LocalDateTime dataInicio);

    /**
     * Busca agendamentos do dia atual
     */
    @Query("SELECT a FROM Agendamento a WHERE DATE(a.dataHora) = CURRENT_DATE ORDER BY a.dataHora ASC")
    List<Agendamento> findAgendamentosHoje();

    // ==================== CONSULTAS POR SERVIÇO ====================

    /**
     * Busca agendamentos por serviço ofertado
     */
    @Query("SELECT a FROM Agendamento a WHERE a.servicoOfertado.id = :servicoOfertadoId")
    List<Agendamento> findByServicoOfertadoId(@Param("servicoOfertadoId") Long servicoOfertadoId);

    /**
     * Busca agendamentos por categoria de serviço
     */
    @Query("SELECT a FROM Agendamento a WHERE a.servicoOfertado.servico.categoria = :categoria")
    List<Agendamento> findByServicoCategoria(@Param("categoria") String categoria);

    // ==================== VALIDAÇÕES E VERIFICAÇÕES ====================

    /**
     * Verifica se existe agendamento para um serviço ofertado em uma data/hora específica
     */
    boolean existsByServicoOfertadoIdAndDataHora(Long servicoOfertadoId, LocalDateTime dataHora);

    /**
     * Verifica se existe conflito de horário para um profissional
     */
    @Query("SELECT COUNT(a) > 0 FROM Agendamento a WHERE a.servicoOfertado.profissional.id = :profissionalId " +
            "AND a.dataHora = :dataHora AND a.statusAgendamento NOT IN ('CANCELADO', 'CONCLUIDO')")
    boolean existsConflitoProfissional(@Param("profissionalId") Long profissionalId,
                                       @Param("dataHora") LocalDateTime dataHora);

    /**
     * Verifica se cliente tem agendamento ativo (não cancelado/concluído) em determinado horário
     */
    @Query("SELECT COUNT(a) > 0 FROM Agendamento a WHERE a.cliente.id = :clienteId " +
            "AND a.dataHora = :dataHora AND a.statusAgendamento NOT IN ('CANCELADO', 'CONCLUIDO')")
    boolean existsAgendamentoAtivoCliente(@Param("clienteId") Long clienteId,
                                          @Param("dataHora") LocalDateTime dataHora);

    // ==================== CONSULTAS COM JOIN FETCH (Performance) ====================

    /**
     * Busca agendamento por ID com todas as relações carregadas
     */
    @Query("SELECT a FROM Agendamento a " +
            "JOIN FETCH a.cliente c " +
            "JOIN FETCH a.servicoOfertado so " +
            "JOIN FETCH so.servico s " +
            "JOIN FETCH so.profissional p " +
            "WHERE a.id = :id")
    Optional<Agendamento> findByIdWithAllRelations(@Param("id") Long id);

    /**
     * Busca agendamento por ID com cliente e serviço
     */
    @Query("SELECT a FROM Agendamento a " +
            "JOIN FETCH a.cliente " +
            "JOIN FETCH a.servicoOfertado " +
            "WHERE a.id = :id")
    Optional<Agendamento> findByIdWithClienteAndServico(@Param("id") Long id);

    /**
     * Busca agendamentos do cliente com todas as relações
     */
    @Query("SELECT a FROM Agendamento a " +
            "JOIN FETCH a.servicoOfertado so " +
            "JOIN FETCH so.servico " +
            "JOIN FETCH so.profissional " +
            "WHERE a.cliente.id = :clienteId " +
            "ORDER BY a.dataHora DESC")
    List<Agendamento> findByClienteIdWithAllRelations(@Param("clienteId") Long clienteId);

    // ==================== ESTATÍSTICAS ====================

    /**
     * Conta agendamentos por status
     */
    @Query("SELECT COUNT(a) FROM Agendamento a WHERE a.statusAgendamento = :status")
    long countByStatus(@Param("status") StatusAgendamento status);

    /**
     * Conta agendamentos em um período
     */
    @Query("SELECT COUNT(a) FROM Agendamento a WHERE a.dataHora BETWEEN :inicio AND :fim")
    long countByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    /**
     * Busca os serviços mais agendados
     */
    @Query("SELECT so.servico.nome, COUNT(a) as total FROM Agendamento a " +
            "JOIN a.servicoOfertado so " +
            "GROUP BY so.servico.id, so.servico.nome " +
            "ORDER BY total DESC")
    List<Object[]> findServicosMaisAgendados();

    /**
     * Busca profissionais mais procurados
     */
    @Query("SELECT p.nome, COUNT(a) as total FROM Agendamento a " +
            "JOIN a.servicoOfertado so " +
            "JOIN so.profissional p " +
            "WHERE a.statusAgendamento = 'CONCLUIDO' " +
            "GROUP BY p.id, p.nome " +
            "ORDER BY total DESC")
    List<Object[]> findProfissionaisMaisProcurados();

    /**
     * Busca taxa de cancelamento por profissional
     */
    @Query("SELECT p.nome, " +
            "COUNT(CASE WHEN a.statusAgendamento = 'CANCELADO' THEN 1 END) as cancelados, " +
            "COUNT(a) as total " +
            "FROM Agendamento a " +
            "JOIN a.servicoOfertado so " +
            "JOIN so.profissional p " +
            "GROUP BY p.id, p.nome")
    List<Object[]> findTaxaCancelamentoPorProfissional();

    /**
     * Busca horários mais populares
     */
    @Query("SELECT HOUR(a.dataHora) as hora, COUNT(a) as total " +
            "FROM Agendamento a " +
            "GROUP BY HOUR(a.dataHora) " +
            "ORDER BY total DESC")
    List<Object[]> findHorariosMaisPopulares();

    // ==================== RELATÓRIOS ====================

    /**
     * Busca agendamentos para relatório gerencial
     */
    @Query("SELECT a FROM Agendamento a " +
            "WHERE a.dataHora BETWEEN :inicio AND :fim " +
            "AND (:status IS NULL OR a.statusAgendamento = :status) " +
            "AND (:profissionalId IS NULL OR a.servicoOfertado.profissional.id = :profissionalId) " +
            "ORDER BY a.dataHora ASC")
    List<Agendamento> findParaRelatorio(@Param("inicio") LocalDateTime inicio,
                                        @Param("fim") LocalDateTime fim,
                                        @Param("status") StatusAgendamento status,
                                        @Param("profissionalId") Long profissionalId);

    /**
     * Busca resumo de agendamentos por dia
     */
    @Query("SELECT DATE(a.dataHora) as data, COUNT(a) as total, " +
            "COUNT(CASE WHEN a.statusAgendamento = 'CONCLUIDO' THEN 1 END) as concluidos, " +
            "COUNT(CASE WHEN a.statusAgendamento = 'CANCELADO' THEN 1 END) as cancelados " +
            "FROM Agendamento a " +
            "WHERE a.dataHora BETWEEN :inicio AND :fim " +
            "GROUP BY DATE(a.dataHora) " +
            "ORDER BY data ASC")
    List<Object[]> findResumoPorDia(@Param("inicio") LocalDateTime inicio,
                                    @Param("fim") LocalDateTime fim);

    // ==================== BUSCA AVANÇADA ====================

    /**
     * Busca agendamentos com múltiplos filtros
     */
    @Query("SELECT a FROM Agendamento a " +
            "WHERE (:clienteId IS NULL OR a.cliente.id = :clienteId) " +
            "AND (:profissionalId IS NULL OR a.servicoOfertado.profissional.id = :profissionalId) " +
            "AND (:status IS NULL OR a.statusAgendamento = :status) " +
            "AND (:dataInicio IS NULL OR a.dataHora >= :dataInicio) " +
            "AND (:dataFim IS NULL OR a.dataHora <= :dataFim) " +
            "ORDER BY a.dataHora DESC")
    List<Agendamento> findComFiltros(@Param("clienteId") Long clienteId,
                                     @Param("profissionalId") Long profissionalId,
                                     @Param("status") StatusAgendamento status,
                                     @Param("dataInicio") LocalDateTime dataInicio,
                                     @Param("dataFim") LocalDateTime dataFim);

    /**
     * Busca agendamentos disponíveis para reagendamento
     */
    @Query("SELECT a FROM Agendamento a " +
            "WHERE a.statusAgendamento = 'CANCELADO' " +
            "AND a.dataHora >= :dataMinima " +
            "ORDER BY a.dataHora ASC")
    List<Agendamento> findDisponiveisParaReagendamento(@Param("dataMinima") LocalDateTime dataMinima);
}