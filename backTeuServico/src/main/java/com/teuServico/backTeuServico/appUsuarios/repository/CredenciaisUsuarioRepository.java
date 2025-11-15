package com.teuServico.backTeuServico.appUsuarios.repository;

import com.teuServico.backTeuServico.appUsuarios.model.CredencialUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositório de acesso a dados para a entidade {@link CredencialUsuario}.
 * <p>
 * Estende {@link JpaRepository} para fornecer operações CRUD e inclui métodos personalizados
 */

@Repository
public interface CredenciaisUsuarioRepository extends JpaRepository<CredencialUsuario, UUID> {
    /**
     * Busca uma credencial de usuário pelo e-mail.
     * <p>
     * O e-mail deve estar previamente normalizado e corresponde ao campo único de autenticação.
     *
     * @param email e-mail do usuário
     * @return {@link Optional} contendo a credencial, se encontrada
     */
    Optional<CredencialUsuario> findByEmail(String email);
}
