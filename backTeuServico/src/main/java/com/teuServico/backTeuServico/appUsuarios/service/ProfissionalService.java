package com.teuServico.backTeuServico.appUsuarios.service;

import com.teuServico.backTeuServico.appUsuarios.dto.CredenciaisUsuarioRequestDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.ProfissionalRequestDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.ProfissionalResponseDTO;
import com.teuServico.backTeuServico.appUsuarios.model.CredencialUsuario;
import com.teuServico.backTeuServico.appUsuarios.model.Profissional;
import com.teuServico.backTeuServico.appUsuarios.model.enums.RoleEnum;
import com.teuServico.backTeuServico.appUsuarios.repository.CredenciaisUsuarioRepository;
import com.teuServico.backTeuServico.appUsuarios.repository.ProfissionalRepository;
import com.teuServico.backTeuServico.shared.utils.Paginacao;
import org.springframework.stereotype.Service;

@Service
public class ProfissionalService {
    private final UsuarioBaseService usuarioBaseService;
    private final ProfissionalRepository profissionalRepository;
    private final CredenciaisUsuarioRepository credenciaisUsuarioRepository;
    private final Paginacao paginacao;

    public ProfissionalService(UsuarioBaseService usuarioBaseService, ProfissionalRepository profissionalRepository, CredenciaisUsuarioRepository credenciaisUsuarioRepository, Paginacao paginacao) {
        this.usuarioBaseService = usuarioBaseService;
        this.profissionalRepository = profissionalRepository;
        this.credenciaisUsuarioRepository = credenciaisUsuarioRepository;
        this.paginacao = paginacao;
    }

    public ProfissionalResponseDTO criarUsuarioProfissional(CredenciaisUsuarioRequestDTO credenciaisUsuarioRequestDTO, ProfissionalRequestDTO profissionalRequestDTO){
        usuarioBaseService.validarUnicidadeUsuario(profissionalRequestDTO.getCpf(), credenciaisUsuarioRequestDTO.getEmail(), profissionalRequestDTO.getTelefone());
        CredencialUsuario credencialUsuario = new CredencialUsuario(credenciaisUsuarioRequestDTO);
        credencialUsuario.setRole(RoleEnum.PROFISSIONAL);
        credenciaisUsuarioRepository.save(credencialUsuario);
        Profissional profissional = new Profissional(profissionalRequestDTO, credencialUsuario);
        profissionalRepository.save(usuarioBaseService.criptografarUsuario(profissional));
        return new ProfissionalResponseDTO(usuarioBaseService.descriptografarUsuario(profissional));

    }

}
