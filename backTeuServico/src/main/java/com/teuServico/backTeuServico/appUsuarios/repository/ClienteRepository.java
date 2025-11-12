package com.teuServico.backTeuServico.appUsuarios.repository;

import com.teuServico.backTeuServico.appUsuarios.model.Cliente;
import com.teuServico.backTeuServico.appUsuarios.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositório de acesso a dados para a entidade {@link Cliente}.
 * <p>
 * Estende {@link JpaRepository} para fornecer operações CRUD e inclui métodos personalizados
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    /**
     * Busca um cliente pelo CPF criptografado.
     *
     * @param cpf CPF criptografado do cliente
     * @return {@link Optional} contendo o cliente, se encontrado
     */
    Optional<Cliente> findByCpf(String cpf);

    /**
     * Busca um cliente pelo telefone criptografado.
     *
     * @param telefone número de telefone criptografado do cliente
     * @return {@link Optional} contendo o cliente, se encontrado
     */
    Optional<Cliente> findByTelefone(String telefone);

    /**
     * Busca um cliente pelo ID da credencial associada.
     *
     * @param idCredencial identificador único da credencial do usuário
     * @return {@link Optional} contendo o cliente, se encontrado
     */
    Optional<Cliente> findByCredencialUsuario_Id(UUID idCredencial);
}
