package com.teuServico.backTeuServico.appUsuarios.repository;

import com.teuServico.backTeuServico.appUsuarios.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, UUID> {
}
