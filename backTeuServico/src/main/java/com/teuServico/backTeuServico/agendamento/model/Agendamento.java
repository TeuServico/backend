package com.teuServico.backTeuServico.agendamento.model;

import com.teuServico.backTeuServico.agendamento.dto.AgendamentoRequestDTO;
import com.teuServico.backTeuServico.appServicos.model.OfertaServico;
import com.teuServico.backTeuServico.appUsuarios.model.Cliente;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "agendamentos")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_oferta_servico", nullable = false)
    private OfertaServico ofertaServico;

    @Column(nullable = false)
    private LocalDateTime dataDeEntrega;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(length = 500)
    private String observacoes;

    @Column(nullable = false)
    private LocalDateTime dataSolicitacao;

    public Agendamento() {}

    public Agendamento(AgendamentoRequestDTO agendamentoRequestDTO) {
        // TODO obter os dados de AgendamentoRequestDTO
    }

    @PrePersist
    protected void aocriar() {
        this.dataSolicitacao = LocalDateTime.now();
        if (this.status == null) {
            this.status = "PENDENTE";
        }
    }

}
