package com.teuServico.backTeuServico.appUsuarios.repository;

import com.teuServico.backTeuServico.appUsuarios.model.CredencialUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CredenciaisUsuarioRepository extends JpaRepository<CredencialUsuario, UUID> {
    Optional<CredencialUsuario> findByEmail(String email);
}
