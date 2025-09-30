package com.project.appAppointment.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long clienteId;  // ID do cliente que agendou

    @Column(nullable = false)
    private Long tipoServicoId;  // ID do tipo de serviço

    @Column(nullable = false)
    private LocalDateTime dataHoraAgendamento;  // Data e hora do agendamento

    @Column(nullable = false, length = 20)
    private String status;  // Ex: "PENDENTE", "CONFIRMADO", "CANCELADO"

    @Column(length = 500)
    private String observacoes;  // Observações

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    // Construtor vazio (OBRIGATÓRIO)
    public Agendamento() {}

    // Construtor com parâmetros
    public Agendamento(Long clienteId, Long tipoServicoId, LocalDateTime dataHoraAgendamento, String status) {
        this.clienteId = clienteId;
        this.tipoServicoId = tipoServicoId;
        this.dataHoraAgendamento = dataHoraAgendamento;
        this.status = status;
    }

    @PrePersist
    protected void aocriar() {
        this.criadoEm = LocalDateTime.now();
        if (this.status == null) {
            this.status = "PENDENTE";
        }
    }

    // GETTERS E SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getTipoServicoId() { return tipoServicoId; }
    public void setTipoServicoId(Long tipoServicoId) { this.tipoServicoId = tipoServicoId; }

    public LocalDateTime getDataHoraAgendamento() { return dataHoraAgendamento; }
    public void setDataHoraAgendamento(LocalDateTime dataHoraAgendamento) { this.dataHoraAgendamento = dataHoraAgendamento; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
}
