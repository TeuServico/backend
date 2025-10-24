package com.teuServico.backTeuServico.appUsuarios.repository;
import com.teuServico.backTeuServico.appUsuarios.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, UUID> {
    Optional<Profissional> findByCpf(String cpf);
    Optional<Profissional> findByTelefone(String telefone);
    Optional<Profissional> findByCredencialUsuario_Id(UUID idCredencial);
}
