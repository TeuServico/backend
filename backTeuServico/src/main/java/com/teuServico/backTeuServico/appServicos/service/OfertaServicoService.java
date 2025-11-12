package com.teuServico.backTeuServico.appServicos.service;

import com.teuServico.backTeuServico.appServicos.dto.OfertaServicoRequestDTO;
import com.teuServico.backTeuServico.appServicos.dto.OfertaServicoResponseDTO;
import com.teuServico.backTeuServico.appServicos.model.OfertaServico;
import com.teuServico.backTeuServico.appServicos.model.TipoServico;
import com.teuServico.backTeuServico.appServicos.repository.OfertaServicoRepository;
import com.teuServico.backTeuServico.appServicos.repository.TipoServicoRepository;
import com.teuServico.backTeuServico.appUsuarios.model.Profissional;
import com.teuServico.backTeuServico.appUsuarios.repository.ProfissionalRepository;
import com.teuServico.backTeuServico.shared.exceptions.BusinessException;
import com.teuServico.backTeuServico.shared.utils.BaseService;
import com.teuServico.backTeuServico.shared.utils.Criptografia;
import com.teuServico.backTeuServico.shared.utils.Paginacao;
import com.teuServico.backTeuServico.shared.utils.PaginacaoResponseDTO;
import org.springframework.data.domain.Sort;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Serviço responsável pela gestão de ofertas de serviços criadas por profissionais.
 * <p>
 */
@Service
public class OfertaServicoService {
    private final BaseService baseService;
    private final OfertaServicoRepository ofertaServicoRepository;
    private final ProfissionalRepository profissionalRepository;
    private final TipoServicoRepository tipoServicoRepository;
    private final TipoServicoService tipoServicoService;
    private final Criptografia criptografia;
    private final Paginacao paginacao;

    public OfertaServicoService(BaseService baseService, OfertaServicoRepository ofertaServicoRepository, ProfissionalRepository profissionalRepository, TipoServicoRepository tipoServicoRepository, TipoServicoService tipoServicoService, Criptografia criptografia, Paginacao paginacao) {
        this.baseService = baseService;
        this.ofertaServicoRepository = ofertaServicoRepository;
        this.profissionalRepository = profissionalRepository;
        this.tipoServicoRepository = tipoServicoRepository;
        this.tipoServicoService = tipoServicoService;
        this.criptografia = criptografia;
        this.paginacao = paginacao;
    }

    /**
     * Descriptografa o nome do profissional antes de retornar o DTO.
     * @param ofertaServicoResponseDTO DTO com dados da oferta
     * @return DTO com nome do profissional descriptografado
     */
    private OfertaServicoResponseDTO retornarResponseDescriptografado(OfertaServicoResponseDTO ofertaServicoResponseDTO){
        ofertaServicoResponseDTO.setProfissionalNome(criptografia.descriptografar(ofertaServicoResponseDTO.getProfissionalNome()));
        return ofertaServicoResponseDTO;
    }

    /**
     * Valida os parâmetros de paginação.
     * @param pagina número da página
     * @param qtdMaximoElementos quantidade máxima de elementos por página
     */
    private void verificarCamposParaPaginacao(String pagina, String qtdMaximoElementos){
        baseService.verificarCampo("pagina", pagina);
        baseService.verificarCampo("qtdMaximoElementos", qtdMaximoElementos);
    }

    /**
     * Cria uma nova oferta de serviço associada ao profissional autenticado.
     * @param ofertaServicoRequestDTO dados da oferta
     * @param token token JWT do profissional
     * @return DTO da oferta criada
     */
    public OfertaServicoResponseDTO criarOfertaServico(OfertaServicoRequestDTO ofertaServicoRequestDTO, JwtAuthenticationToken token) {

        UUID idCredencial = UUID.fromString(token.getName());
        Profissional profissional = profissionalRepository.findByCredencialUsuario_Id(idCredencial)
                .orElseThrow(() -> new BusinessException("Profissional não foi encontrado"));

        TipoServico tipoServico = tipoServicoRepository.findById(ofertaServicoRequestDTO.getTipoServicoId())
                .orElseThrow(() -> new BusinessException("TipoServico não existe"));

        OfertaServico ofertaServico = new OfertaServico(ofertaServicoRequestDTO, tipoServico, profissional);
        ofertaServicoRepository.save(ofertaServico);

        return retornarResponseDescriptografado(new OfertaServicoResponseDTO(ofertaServico));
    }

    /**
     * Lista as ofertas de serviço criadas pelo profissional autenticado.
     * @param pagina número da página
     * @param qtdMaximoElementos quantidade máxima de elementos por página
     * @param token token JWT do profissional
     * @return lista paginada de ofertas
     */
    public PaginacaoResponseDTO<OfertaServicoResponseDTO> minhasOfertasServico(String pagina, String qtdMaximoElementos, JwtAuthenticationToken token) {
        verificarCamposParaPaginacao(pagina, qtdMaximoElementos);
        UUID idCredencial = UUID.fromString(token.getName());
        Optional<Profissional> profissional = profissionalRepository.findByCredencialUsuario_Id(idCredencial);
        if (profissional.isEmpty()) {
            throw new BusinessException("Profissional não foi encontrado");
        }
        return paginacao.listarPor(
                baseService.extrairNumeroPaginaValido(pagina),
                baseService.transformarEmNumeroInt(qtdMaximoElementos, "qtdMaximoElementos é inválido"),
                pageable -> ofertaServicoRepository.findByProfissional_Id(profissional.get().getId(), pageable),
                ofertaServico -> retornarResponseDescriptografado(new OfertaServicoResponseDTO(ofertaServico)),
                Sort.by("tipoServico.categoria")
        );
    }

    /**
     * Busca ofertas de serviço pelo nome do tipo de serviço.
     * @param pagina número da página
     * @param qtdMaximoElementos quantidade máxima de elementos por página
     * @param nome nome do tipo de serviço
     * @return lista paginada de ofertas
     */
    public PaginacaoResponseDTO<OfertaServicoResponseDTO> buscarOfertasServicosPorTipoServicoNome(String pagina, String qtdMaximoElementos, String nome) {
        verificarCamposParaPaginacao(pagina, qtdMaximoElementos);
        baseService.verificarCampo("nome", nome);
        return paginacao.listarPor(
                baseService.extrairNumeroPaginaValido(pagina),
                baseService.transformarEmNumeroInt(qtdMaximoElementos, "qtdMaximoElementos é inválido"),
                pageable -> ofertaServicoRepository.findByTipoServico_NomeContaining(baseService.normalizarString(nome), pageable),
                ofertaServico -> retornarResponseDescriptografado(new OfertaServicoResponseDTO(ofertaServico)),
                Sort.by("tipoServico.nome")
        );

    }

    /**
     * Busca ofertas de serviço pela categoria do tipo de serviço.
     * @param pagina número da página
     * @param qtdMaximoElementos quantidade máxima de elementos por página
     * @param categoria categoria do tipo de serviço
     * @return lista paginada de ofertas
     */
    public PaginacaoResponseDTO<OfertaServicoResponseDTO> buscarOfertasServicosPorTipoServicoCategoria(String pagina, String qtdMaximoElementos, String categoria){
        verificarCamposParaPaginacao(pagina, qtdMaximoElementos);
        baseService.verificarCampo("categoria",categoria);
        return paginacao.listarPor(
                baseService.extrairNumeroPaginaValido(pagina),
                baseService.transformarEmNumeroInt(qtdMaximoElementos, "qtdMaximoElementos é inválido"),
                pageable -> ofertaServicoRepository.findByTipoServico_Categoria(baseService.normalizarString(categoria), pageable),
                ofertaServico -> retornarResponseDescriptografado(new OfertaServicoResponseDTO(ofertaServico)),
                Sort.by("tipoServico.nome")
        );
    }
}