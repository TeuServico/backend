package com.teuServico.backTeuServico.agendamento.service;

import com.teuServico.backTeuServico.agendamento.dto.AgendamentoRequestDTO;
import com.teuServico.backTeuServico.agendamento.dto.AgendamentoResponseDTO;
import com.teuServico.backTeuServico.agendamento.model.Agendamento;
import com.teuServico.backTeuServico.agendamento.repository.AgendamentoRepository;
import com.teuServico.backTeuServico.appServicos.model.OfertaServico;
import com.teuServico.backTeuServico.appServicos.repository.OfertaServicoRepository;
import com.teuServico.backTeuServico.appUsuarios.model.Cliente;
import com.teuServico.backTeuServico.appUsuarios.model.Profissional;
import com.teuServico.backTeuServico.appUsuarios.repository.ClienteRepository;
import com.teuServico.backTeuServico.appUsuarios.repository.ProfissionalRepository;
import com.teuServico.backTeuServico.shared.exceptions.BusinessException;
import com.teuServico.backTeuServico.shared.utils.BaseService;
import com.teuServico.backTeuServico.shared.utils.Paginacao;
import com.teuServico.backTeuServico.shared.utils.PaginacaoResponseDTO;
//import com.teuServico.backTeuServico.shared.utils.email.EmailService;
import org.springframework.data.domain.Sort;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AgendamentoService {
    private final AgendamentoRepository agendamentoRepository;
    private final OfertaServicoRepository ofertaServicoRepository;
    private final ProfissionalRepository profissionalRepository;
    private final ClienteRepository clienteRepository;
    private final Paginacao paginacao;
    private final BaseService baseService;
//    private final EmailService emailService;

    public AgendamentoService(AgendamentoRepository agendamentoRepository, OfertaServicoRepository ofertaServicoRepository, ProfissionalRepository profissionalRepository, ClienteRepository clienteRepository, Paginacao paginacao, BaseService baseService) {
        this.agendamentoRepository = agendamentoRepository;
        this.ofertaServicoRepository = ofertaServicoRepository;
        this.profissionalRepository = profissionalRepository;
        this.clienteRepository = clienteRepository;
        this.paginacao = paginacao;
        this.baseService = baseService;
//        this.emailService = emailService;
    }

    private Cliente buscarClientePorToken(JwtAuthenticationToken token){
        UUID idCredencial = UUID.fromString(token.getName());
        Optional<Cliente> cliente = clienteRepository.findByCredencialUsuario_Id(idCredencial);
        if (cliente.isEmpty()){
            throw new BusinessException("Esse cliente nao foi encontrado");
        }
        return cliente.get();
    }

    private Profissional buscarProfissionalPorToken(JwtAuthenticationToken token){
        UUID idCredencial = UUID.fromString(token.getName());
        Optional<Profissional> profissional = profissionalRepository.findByCredencialUsuario_Id(idCredencial);
        if (profissional.isEmpty()){
            throw new BusinessException("Esse profissional nao foi encontrado");
        }
        return profissional.get();
    }

    private OfertaServico buscarOfertaServico(long idOfertaServico){
        Optional<OfertaServico> ofertaServico = ofertaServicoRepository.findById(idOfertaServico);
        if (ofertaServico.isEmpty()){
            throw new BusinessException("Essa oferta de servico nao foi encontrado");
        }
        return ofertaServico.get();
    }

    public AgendamentoResponseDTO solicitarAgendamento(AgendamentoRequestDTO agendamentoRequestDTO, JwtAuthenticationToken token){
        Cliente cliente = buscarClientePorToken(token);
        OfertaServico ofertaServico = buscarOfertaServico(agendamentoRequestDTO.getOfertaServicoId());
        Agendamento agendamento = new Agendamento(agendamentoRequestDTO, cliente, ofertaServico);
        agendamentoRepository.save(agendamento);
        return new AgendamentoResponseDTO(agendamento);
    }

    public PaginacaoResponseDTO<AgendamentoResponseDTO> meusAgendamentosCliente(String pagina, String qtdMaximaElementos, JwtAuthenticationToken token){
        baseService.verificarCampo("qtdMaximaElementos", qtdMaximaElementos);
        Cliente cliente = buscarClientePorToken(token);
        return paginacao.listarPor(
                baseService.extrairNumeroPaginaValido(pagina),
                baseService.transformarEmNumeroInt(qtdMaximaElementos, "qtdMaximaElementos é inválido"),
                pageable -> agendamentoRepository.findByCliente_Id(cliente.getId(), pageable),
                agendamento -> new AgendamentoResponseDTO(agendamento),
                Sort.by("status")
                );
    }

    public PaginacaoResponseDTO<AgendamentoResponseDTO> meusAgendamentosProfissional(String pagina, String qtdMaximaElementos, JwtAuthenticationToken token){
        baseService.verificarCampo("qtdMaximaElementos", qtdMaximaElementos);
        Profissional profissional = buscarProfissionalPorToken(token);
        return paginacao.listarPor(
                baseService.extrairNumeroPaginaValido(pagina),
                baseService.transformarEmNumeroInt(qtdMaximaElementos, "qtdMaximaElementos é inválido"),
                pageable -> agendamentoRepository.findByOfertaServico_Profissional_Id(profissional.getId(), pageable),
                agendamento -> new AgendamentoResponseDTO(agendamento),
                Sort.by("status")
        );
    }

}


