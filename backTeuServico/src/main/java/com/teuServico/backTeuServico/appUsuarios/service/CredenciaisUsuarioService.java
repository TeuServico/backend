package com.teuServico.backTeuServico.appUsuarios.service;

import com.teuServico.backTeuServico.appUsuarios.dto.CredenciaisUsuarioRequestDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.TokenJWT;
import com.teuServico.backTeuServico.appUsuarios.model.CredencialUsuario;
import com.teuServico.backTeuServico.appUsuarios.model.enums.RoleEnum;
import com.teuServico.backTeuServico.appUsuarios.repository.CredenciaisUsuarioRepository;
import com.teuServico.backTeuServico.shared.exceptions.BusinessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Optional;

@Service
public class CredenciaisUsuarioService {
    private final JwtEncoder jwtEncoder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CredenciaisUsuarioRepository credenciaisUsuarioRepository;

    public CredenciaisUsuarioService(JwtEncoder jwtEncoder, BCryptPasswordEncoder bCryptPasswordEncoder, CredenciaisUsuarioRepository credenciaisUsuarioRepository) {
        this.jwtEncoder = jwtEncoder;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.credenciaisUsuarioRepository = credenciaisUsuarioRepository;
    }

    private boolean credenciaisSaoValidas(CredencialUsuario credencialUsuario, CredenciaisUsuarioRequestDTO credenciaisUsuarioRequestDTO){
        return bCryptPasswordEncoder.matches(credenciaisUsuarioRequestDTO.getSenha(), credencialUsuario.getSenha());
    }

    private TokenJWT gerarTokenJWT(CredencialUsuario credencialUsuario){
        var now = Instant.now();
        var expiresIn = 3600L;
        var claims = JwtClaimsSet.builder()
                .issuer("TeuServico")
                .subject(credencialUsuario.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("role", credencialUsuario.getRole().name())
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new TokenJWT(jwtValue, expiresIn, credencialUsuario.getRole());
    }

    public TokenJWT login(CredenciaisUsuarioRequestDTO credenciaisUsuarioRequestDTO){
        Optional<CredencialUsuario> credencialUsuario = credenciaisUsuarioRepository.findByEmail(credenciaisUsuarioRequestDTO.getEmail());
        if(credencialUsuario.isEmpty()){
            throw new BusinessException("Usuário não encontrado");
        } else if (!credenciaisSaoValidas(credencialUsuario.get(), credenciaisUsuarioRequestDTO)) {
            throw new BusinessException("Senha inválida");
        }

        return gerarTokenJWT(credencialUsuario.get());
    }

    public TokenJWT registrar(CredencialUsuario credencial, String tipoUsuario) {

        Optional<CredencialUsuario> usuarioExistente = credenciaisUsuarioRepository.findByEmail(credencial.getEmail());
        if (usuarioExistente.isPresent()) {
            throw new BusinessException("Email já cadastrado");
        }

        if (tipoUsuario.equalsIgnoreCase("CLIENTE")) {
            credencial.setRole(RoleEnum.CLIENTE);
        } else if (tipoUsuario.equalsIgnoreCase("PROFISSIONAL")) {
            credencial.setRole(RoleEnum.PROFISSIONAL);
        } else {
            throw new BusinessException("Tipo de usuário inexistente");
        }
        
        credencial.setSenha(bCryptPasswordEncoder.encode(credencial.getSenha()));
        credenciaisUsuarioRepository.save(credencial);

        return gerarTokenJWT(credencial);
    }
}
