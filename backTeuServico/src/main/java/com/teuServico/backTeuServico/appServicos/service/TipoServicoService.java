package com.teuServico.backTeuServico.appServicos.service;

import com.teuServico.backTeuServico.appServicos.dto.TipoServicoRequestDTO;
import com.teuServico.backTeuServico.appServicos.dto.TipoServicoResponseDTO;
import com.teuServico.backTeuServico.appServicos.model.TipoServico;
import com.teuServico.backTeuServico.appServicos.repository.TipoServicoRepository;
import com.teuServico.backTeuServico.shared.exceptions.BusinessException;
import com.teuServico.backTeuServico.shared.utils.BaseService;
import com.teuServico.backTeuServico.shared.utils.Paginacao;
import com.teuServico.backTeuServico.shared.utils.PaginacaoResponseDTO;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


/**
 * Serviço responsável pela gestão dos tipos de serviço disponíveis.
 * <p>
 */
@Service
public class TipoServicoService {
    private final BaseService baseService;
    private final TipoServicoRepository tipoServicoRepository;
    private final Paginacao paginacao;

    public TipoServicoService(BaseService baseService, TipoServicoRepository tipoServicoRepository, Paginacao paginacao) {
        this.baseService = baseService;
        this.tipoServicoRepository = tipoServicoRepository;
        this.paginacao = paginacao;
    }

    /**
     * Verifica se já existe um tipo de serviço com o mesmo nome.
     *
     * @param tipoServicoRequestDTO dados do tipo de serviço a ser validado
     * @throws BusinessException se o nome já estiver cadastrado
     */
    private void validarUnicadeTipoServico(TipoServicoRequestDTO tipoServicoRequestDTO){
        if(tipoServicoRepository.findFirstByNome(baseService.normalizarString(tipoServicoRequestDTO.getNome())).isPresent()) {
            throw new BusinessException("nome já está cadastrado");
        }
    }

    /**
     * Normaliza os campos de nome e categoria do tipo de serviço.
     * @param tipoServicoRequestDTO dados a serem normalizados
     * @return DTO com nome e categoria normalizados
     */
    private TipoServicoRequestDTO normalizarTipoServico(TipoServicoRequestDTO tipoServicoRequestDTO){
        tipoServicoRequestDTO.setNome(baseService.normalizarString(tipoServicoRequestDTO.getNome()));
        tipoServicoRequestDTO.setCategoria(baseService.normalizarString(tipoServicoRequestDTO.getCategoria()));
        return tipoServicoRequestDTO;
    }

    /**
     * Cria um novo tipo de serviço após validar unicidade e normalizar os dados.
     * @param tipoServicoRequestDTO dados do tipo de serviço
     * @return DTO com os dados do tipo de serviço criado
     */
    public TipoServicoResponseDTO criarTipoServico(TipoServicoRequestDTO tipoServicoRequestDTO){
        validarUnicadeTipoServico(tipoServicoRequestDTO);
        TipoServico tipoServico = new TipoServico(normalizarTipoServico(tipoServicoRequestDTO));
        tipoServicoRepository.save(tipoServico);
        return new TipoServicoResponseDTO(tipoServico);
    }

    /**
     * Retorna todos os tipos de serviço cadastrados, com paginação.
     * @param pagina número da página
     * @param qtdMaximoElementos quantidade máxima de elementos por página
     * @return lista paginada de tipos de serviço
     */
    public PaginacaoResponseDTO<TipoServicoResponseDTO> buscarTodos(String pagina, String qtdMaximoElementos){
        baseService.verificarCampo("qtdMaximo elementos", qtdMaximoElementos);
        return paginacao.listarTodos(
                baseService.extrairNumeroPaginaValido(pagina),
                baseService.transformarEmNumeroInt(qtdMaximoElementos,"qtdMaximoElementos é inválido" ),
                tipoServicoRepository,
                tipoServico -> new TipoServicoResponseDTO(tipoServico),
                Sort.by("categoria"));
    }

    /**
     * Retorna todos os tipos de serviço filtrados por categoria, com paginação.
     * @param categoria categoria a ser filtrada
     * @param pagina número da página
     * @param qtdMaximoElementos quantidade máxima de elementos por página
     * @return lista paginada de tipos de serviço da categoria informada
     */
    public PaginacaoResponseDTO<TipoServicoResponseDTO> buscarTodosPorCategoria(String categoria,String pagina, String qtdMaximoElementos){
        baseService.verificarCampo("categoria", categoria);
        baseService.verificarCampo("qtdMaximo elementos", qtdMaximoElementos);
        return paginacao.listarPor(
                baseService.extrairNumeroPaginaValido(pagina),
                baseService.transformarEmNumeroInt(qtdMaximoElementos,"qtdMaximoElementos é inválido"),
                pageable -> tipoServicoRepository.findByCategoria(baseService.normalizarString(categoria) ,pageable),
                tipoServico -> new TipoServicoResponseDTO(tipoServico),
                Sort.by("nome"));
    }

}
