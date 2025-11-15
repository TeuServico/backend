package com.teuServico.backTeuServico.appServicos.repository;

import com.teuServico.backTeuServico.appServicos.model.OfertaServico;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

/**
 * Repositório responsável pelas operações de acesso a dados da entidade {@link OfertaServico}.
 * <p>
 */
@Repository
public interface OfertaServicoRepository extends JpaRepository<OfertaServico, Long> {

    /**
     * Busca todas as ofertas de serviço associadas a um profissional específico.
     * @param idProfissional ID do profissional
     * @param pageable informações de paginação
     * @return página com as ofertas encontradas
     */
    Page<OfertaServico> findByProfissional_Id(UUID idProfissional, Pageable pageable);

    /**
     * Busca ofertas de serviço cujo nome do tipo de serviço contenha o texto informado.
     * @param categoria nome parcial do tipo de serviço
     * @param pageable informações de paginação
     * @return página com as ofertas encontradas
     */
    Page<OfertaServico> findByTipoServico_NomeContaining(String categoria, Pageable pageable);

    /**
     * Busca ofertas de serviço por categoria do tipo de serviço.
     * @param categoria categoria do tipo de serviço
     * @param pageable  informações de paginação
     * @return página com as ofertas encontradas
     */
    Page<OfertaServico> findByTipoServico_Categoria(String categoria, Pageable pageable);

    /**
     * Busca ofertas de serviço que contenham pelo menos uma das tags fornecidas.
     * @param tags lista de tags para filtrar as ofertas
     * @param pageable informações de paginação
     * @return página com as ofertas encontradas
     */
    Page<OfertaServico> findByTagsIn(List<String> tags, Pageable pageable);
}
