package com.teuServico.backTeuServico.agendamento.service;

import com.teuServico.backTeuServico.agendamento.dto.AgendamentoRequestDTO;
import com.teuServico.backTeuServico.agendamento.dto.AgendamentoResponseDTO;
import com.teuServico.backTeuServico.agendamento.dto.ContraOfertaRequestDTO;
import com.teuServico.backTeuServico.agendamento.model.Agendamento;
import com.teuServico.backTeuServico.agendamento.model.ContraOferta;
import com.teuServico.backTeuServico.agendamento.model.enums.StatusEnum;
import com.teuServico.backTeuServico.agendamento.repository.AgendamentoRepository;
import com.teuServico.backTeuServico.appServicos.model.OfertaServico;
import com.teuServico.backTeuServico.appServicos.repository.OfertaServicoRepository;
import com.teuServico.backTeuServico.appUsuarios.model.Cliente;
import com.teuServico.backTeuServico.appUsuarios.model.Profissional;
import com.teuServico.backTeuServico.shared.exceptions.BusinessException;
import com.teuServico.backTeuServico.shared.utils.BaseService;
import com.teuServico.backTeuServico.shared.utils.Paginacao;
import com.teuServico.backTeuServico.shared.utils.PaginacaoResponseDTO;
import com.teuServico.backTeuServico.shared.utils.email.EmailService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class AgendamentoService {
    private final AgendamentoRepository agendamentoRepository;
    private final OfertaServicoRepository ofertaServicoRepository;
    private final Paginacao paginacao;
    private final BaseService baseService;
    private final EmailService emailService;

    public AgendamentoService(AgendamentoRepository agendamentoRepository, OfertaServicoRepository ofertaServicoRepository, Paginacao paginacao, BaseService baseService, EmailService emailService) {
        this.agendamentoRepository = agendamentoRepository;
        this.ofertaServicoRepository = ofertaServicoRepository;
        this.paginacao = paginacao;
        this.baseService = baseService;
        this.emailService = emailService;
    }

    private OfertaServico buscarOfertaServico(long idOfertaServico){
        Optional<OfertaServico> ofertaServico = ofertaServicoRepository.findById(idOfertaServico);
        if (ofertaServico.isEmpty()){
            throw new BusinessException("Essa oferta de servico nao foi encontrado");
        }
        return ofertaServico.get();
    }

    private Agendamento buscarAgendamentoPorId(String idAgendamento){
        return agendamentoRepository.findById(baseService.validarUUID(idAgendamento)).orElseThrow(() -> new BusinessException("Agendamento não foi encontrado"));
    }

    private void verificarSeAgendamentoPertenceAprofissional(Agendamento agendamento, Profissional profissional){
        if (!agendamento.getOfertaServico().getProfissional().getId().equals(profissional.getId())) {
            throw new BusinessException("Você não pode fazer operacoes sobre um agendamento que não lhe pertence");
        }
    }

    private void verificarSeAgendamentoPertenceAcliente(Agendamento agendamento, Cliente cliente){
        if (!agendamento.getCliente().getId().equals(cliente.getId())) {
            throw new BusinessException("Você não pode fazer operacoes sobre um agendamento que não lhe pertence");
        }
    }

    private void verificarSeAgendamentoEstaCancelado(Agendamento agendamento){
        if(agendamento.getStatus().equals(StatusEnum.CANCELADO)){
            throw new BusinessException("Não é possivel interagir com um agendamento que foi cancelado");
        }
    }

    private void verificarSeAgendamentoEstaEmAndamento(Agendamento agendamento){
        if(agendamento.getStatus().equals(StatusEnum.EM_ANDAMENTO)){
            throw new BusinessException("Solitação negada, agendamento está em andamento");
        }
    }

    private void removerContraOferta(Agendamento agendamento) {
        ContraOferta contraOferta = agendamento.getContraOferta();
        if (contraOferta != null) {
            contraOferta.setContraOfertaDataDeEntrega(null);
            contraOferta.setContraOfertaPrecoDesejado(null);
            agendamento.setContraOferta(null);
            agendamento.setTemContraOferta(false);
        }
    }

    private void notificarClienteEprofissionalViaEmail(Agendamento agendamento){
        emailService.notificarClienteEprofissional(agendamento);
    }

    public AgendamentoResponseDTO solicitarAgendamento(AgendamentoRequestDTO agendamentoRequestDTO, JwtAuthenticationToken token){
        Cliente cliente = baseService.buscarClientePorTokenJWT(token);
        OfertaServico ofertaServico = buscarOfertaServico(agendamentoRequestDTO.getOfertaServicoId());
        Agendamento agendamento = new Agendamento(agendamentoRequestDTO, cliente, ofertaServico);
        agendamentoRepository.save(agendamento);
        notificarClienteEprofissionalViaEmail(agendamento);
        return new AgendamentoResponseDTO(agendamento);
    }

    public PaginacaoResponseDTO<AgendamentoResponseDTO> meusAgendamentosCliente(String pagina, String qtdMaximaElementos, JwtAuthenticationToken token){
        baseService.verificarCampo("qtdMaximaElementos", qtdMaximaElementos);
        Cliente cliente = baseService.buscarClientePorTokenJWT(token);
        return paginacao.listarPor(
                baseService.extrairNumeroPaginaValido(pagina),
                baseService.transformarEmNumeroInt(qtdMaximaElementos, "qtdMaximaElementos é inválido"),
                pageable -> agendamentoRepository.findByCliente_Id(cliente.getId(), pageable),
                AgendamentoResponseDTO::new,
                Sort.by("status")
                );
    }

    public PaginacaoResponseDTO<AgendamentoResponseDTO> meusAgendamentosProfissional(String pagina, String qtdMaximaElementos, JwtAuthenticationToken token){
        baseService.verificarCampo("qtdMaximaElementos", qtdMaximaElementos);
        Profissional profissional = baseService.buscarProfissionalPorTokenJWT(token);
        return paginacao.listarPor(
                baseService.extrairNumeroPaginaValido(pagina),
                baseService.transformarEmNumeroInt(qtdMaximaElementos, "qtdMaximaElementos é inválido"),
                pageable -> agendamentoRepository.findByOfertaServico_Profissional_Id(profissional.getId(), pageable),
                AgendamentoResponseDTO::new,
                Sort.by("status")
        );
    }

    public ResponseEntity<String> profissionalAceitarAgendamento(String idAgendamento, JwtAuthenticationToken token) {
        Profissional profissional = baseService.buscarProfissionalPorTokenJWT(token);
        Agendamento agendamento = buscarAgendamentoPorId(idAgendamento);
        verificarSeAgendamentoEstaCancelado(agendamento);
        verificarSeAgendamentoPertenceAprofissional(agendamento, profissional);
        if (agendamento.getStatus().equals(StatusEnum.EM_ANDAMENTO)) {
            throw new BusinessException("Você já aceitou esse agendamento");
        }
        removerContraOferta(agendamento);
        agendamento.setStatus(StatusEnum.EM_ANDAMENTO);
        agendamentoRepository.save(agendamento);
        notificarClienteEprofissionalViaEmail(agendamento);
        return ResponseEntity.ok("Agendamento aceito com sucesso, por favor faça a entrega do serviço dentro do prazo");
    }

    public ResponseEntity<String> profissionalFazerContraOferta(ContraOfertaRequestDTO contraOfertaRequestDTO, JwtAuthenticationToken token){
        Profissional profissional = baseService.buscarProfissionalPorTokenJWT(token);
        Agendamento agendamento = buscarAgendamentoPorId(contraOfertaRequestDTO.getIdDoAgendamento());
        verificarSeAgendamentoEstaCancelado(agendamento);
        verificarSeAgendamentoPertenceAprofissional(agendamento, profissional);
        verificarSeAgendamentoEstaEmAndamento(agendamento);
        if (agendamento.getContraOferta() != null) {
            throw new BusinessException("Este agendamento já possui uma contra oferta");
        }
        agendamento.setContraOferta(new ContraOferta());
        agendamento.setTemContraOferta(true);
        agendamento.getContraOferta().setContraOfertaDataDeEntrega(contraOfertaRequestDTO.getDataEntrega());
        agendamento.getContraOferta().setContraOfertaPrecoDesejado(contraOfertaRequestDTO.getPrecoDesejado());
        agendamento.setStatus(StatusEnum.AGUARDANDO_CONFIRMACAO_CLIENTE);
        agendamentoRepository.save(agendamento);
        notificarClienteEprofissionalViaEmail(agendamento);
        return ResponseEntity.ok("Contra-proposta enviada com sucesso, aguarde a resposta do cliente");
    }

    public ResponseEntity<String> clienteAceitarAgendamentoContraOferta(String idAgendamento, JwtAuthenticationToken token){
        Cliente cliente = baseService.buscarClientePorTokenJWT(token);
        Agendamento agendamento = buscarAgendamentoPorId(idAgendamento);
        verificarSeAgendamentoEstaCancelado(agendamento);
        verificarSeAgendamentoPertenceAcliente(agendamento, cliente);
        verificarSeAgendamentoEstaEmAndamento(agendamento);
        if (!agendamento.isTemContraOferta()){
            throw new BusinessException("Esse agendamento não possui uma contraproposta");
        }
        agendamento.setDataDeEntrega(agendamento.getContraOferta().getContraOfertaDataDeEntrega());
        agendamento.setPrecoDesejado(agendamento.getContraOferta().getContraOfertaPrecoDesejado());
        removerContraOferta(agendamento);
        agendamento.setStatus(StatusEnum.EM_ANDAMENTO);
        agendamentoRepository.save(agendamento);
        notificarClienteEprofissionalViaEmail(agendamento);
        return ResponseEntity.ok("Você aceitou a contra-proposta oferecida pelo profissional, aguarde até a conclusão do serviço");
    }

    public ResponseEntity<String> clienteCancelarAgendamento(String idAgendamento, JwtAuthenticationToken token){
        Cliente cliente = baseService.buscarClientePorTokenJWT(token);
        Agendamento agendamento = buscarAgendamentoPorId(idAgendamento);
        verificarSeAgendamentoEstaCancelado(agendamento);
        verificarSeAgendamentoPertenceAcliente(agendamento, cliente);
        agendamento.setStatus(StatusEnum.CANCELADO);
        agendamentoRepository.save(agendamento);
        notificarClienteEprofissionalViaEmail(agendamento);
        return ResponseEntity.ok("Agendamento cancelado com sucesso");
    }

    public ResponseEntity<String> profissionalCancelarAgendamento(String idAgendamento, JwtAuthenticationToken token){
        Profissional profissional = baseService.buscarProfissionalPorTokenJWT(token);
        Agendamento agendamento = buscarAgendamentoPorId(idAgendamento);
        verificarSeAgendamentoEstaCancelado(agendamento);
        verificarSeAgendamentoPertenceAprofissional(agendamento, profissional);
        agendamento.setStatus(StatusEnum.CANCELADO);
        agendamentoRepository.save(agendamento);
        notificarClienteEprofissionalViaEmail(agendamento);
        return ResponseEntity.ok("Agendamento cancelado com sucesso");
    }

    public ResponseEntity<String> profissionalConcluirAgendamento(String idAgendamento, JwtAuthenticationToken token){
        Profissional profissional = baseService.buscarProfissionalPorTokenJWT(token);
        Agendamento agendamento = buscarAgendamentoPorId(idAgendamento);
        verificarSeAgendamentoPertenceAprofissional(agendamento, profissional);
        verificarSeAgendamentoEstaCancelado(agendamento);
        if (!agendamento.getStatus().equals(StatusEnum.EM_ANDAMENTO)) {
            throw new BusinessException("Você não pode concluir um agendamento que não está em andamento");
        }
        agendamento.setStatus(StatusEnum.CONCLUIDO);
        agendamentoRepository.save(agendamento);
        notificarClienteEprofissionalViaEmail(agendamento);
        return ResponseEntity.ok("Agendamento concluído com sucesso");
    }

}


