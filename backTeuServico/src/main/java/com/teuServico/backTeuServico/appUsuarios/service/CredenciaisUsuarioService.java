package com.teuServico.backTeuServico.appUsuarios.service;

import com.teuServico.backTeuServico.appUsuarios.dto.CredenciaisUsuarioRequestDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.TokenJWT;
import com.teuServico.backTeuServico.appUsuarios.model.CredencialUsuario;
import com.teuServico.backTeuServico.appUsuarios.model.enums.RoleEnum;
import com.teuServico.backTeuServico.appUsuarios.repository.CredenciaisUsuarioRepository;
import com.teuServico.backTeuServico.shared.exceptions.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Optional;

@Service
public class CredenciaisUsuarioService {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CredenciaisUsuarioRepository credenciaisUsuarioRepository;

    public CredenciaisUsuarioService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, BCryptPasswordEncoder bCryptPasswordEncoder, CredenciaisUsuarioRepository credenciaisUsuarioRepository) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
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

    public String generateResetToken(String email) {
        Instant now = Instant.now();
        var expiresIn = 300L;
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(email)
                .claim("type", "reset-password")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String validateResetToken(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            if (!"reset-password".equals(jwt.getClaim("type"))) {
                throw new RuntimeException("Tipo de token inválido");
            }
            return jwt.getSubject();
        } catch (JwtException e) {
            throw new RuntimeException("Token inválido ou expirado");
        }
    }

    public ResponseEntity<?> esquecerSenha(String email, String linkFrontParaRedefinirAsenha){
        if(email.isBlank() || linkFrontParaRedefinirAsenha.isBlank()){
            throw new BusinessException("email e linkFrontParaRedefinirAsenha não podem ser inválidos");
        }
        Optional<CredencialUsuario> credencialUsuario = credenciaisUsuarioRepository.findByEmail(email);
        if(credencialUsuario.isEmpty()){
            throw new BusinessException("Nenhum usuário com esse email foi encontrado");
        }
        String token = generateResetToken(email);
        String link = linkFrontParaRedefinirAsenha+"?token="+token;

        return ResponseEntity.ok("Email para redefinição de senha enviado, será expirado em 5 minutos");
    }

}
