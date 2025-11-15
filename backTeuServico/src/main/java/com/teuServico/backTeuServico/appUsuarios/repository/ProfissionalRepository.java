package com.teuServico.backTeuServico.appUsuarios.repository;
import com.teuServico.backTeuServico.appUsuarios.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositório de acesso a dados para a entidade {@link Profissional}.
 * <p>
 * Estende {@link JpaRepository} para fornecer operações CRUD e inclui métodos personalizados
 */
@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, UUID> {
    /**
     * Busca um profissional pelo CPF criptografado.
     * @param cpf CPF criptografado do profissional
     * @return {@link Optional} contendo o profissional, se encontrado
     */
    Optional<Profissional> findByCpf(String cpf);

    /**
     * Busca um profissional pelo telefone criptografado.
     *
     * @param telefone número de telefone criptografado do profissional
     * @return {@link Optional} contendo o profissional, se encontrado
     */
    Optional<Profissional> findByTelefone(String telefone);

    /**
     * Busca um profissional pelo ID da credencial associada.
     *
     * @param idCredencial identificador único da credencial do usuário
     * @return {@link Optional} contendo o profissional, se encontrado
     */
    Optional<Profissional> findByCredencialUsuario_Id(UUID idCredencial);
}
