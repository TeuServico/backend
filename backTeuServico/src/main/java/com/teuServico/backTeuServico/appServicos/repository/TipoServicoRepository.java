package com.teuServico.backTeuServico.appServicos.repository;

import com.teuServico.backTeuServico.appServicos.model.TipoServico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositório responsável pelas operações de acesso a dados da entidade {@link TipoServico}.
 * <p>
 */
@Repository
public interface TipoServicoRepository extends JpaRepository<TipoServico, Long> {
    /**
     * Busca o primeiro tipo de serviço com nome exato informado.
     * @param nome nome do tipo de serviço
     * @return tipo de serviço correspondente, se existir
     */
    Optional<TipoServico> findFirstByNome(String nome);

    /**
     * Busca o primeiro tipo de serviço com categoria exata informada.
     * @param catetgoria nome da categoria
     * @return tipo de serviço correspondente, se existir
     */
    Optional<TipoServico> findFirstByCategoria(String catetgoria);

    /**
     * Busca todos os tipos de serviço de uma determinada categoria, com paginação.
     * @param categoria nome da categoria
     * @param pageable  informações de paginação
     * @return página com os tipos de serviço encontrados
     */
    Page<TipoServico> findByCategoria(String categoria,Pageable pageable);

}
