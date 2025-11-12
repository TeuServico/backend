package com.teuServico.backTeuServico.appUsuarios.service;

import com.teuServico.backTeuServico.appUsuarios.dto.CredenciaisUsuarioRequestDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.TokenJWT;
import com.teuServico.backTeuServico.appUsuarios.model.CredencialUsuario;
import com.teuServico.backTeuServico.appUsuarios.model.enums.RoleEnum;
import com.teuServico.backTeuServico.appUsuarios.repository.CredenciaisUsuarioRepository;
import com.teuServico.backTeuServico.shared.exceptions.BusinessException;
import com.teuServico.backTeuServico.shared.utils.BaseService;
import com.teuServico.backTeuServico.shared.utils.email.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

/**
 * Serviço responsável pela autenticação, registro e recuperação de conta de usuários.
 */
@Service
public class CredenciaisUsuarioService {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CredenciaisUsuarioRepository credenciaisUsuarioRepository;
    private final BaseService baseService;
    private final EmailService emailService;

    /**
     * Construtor da classe {@code CredenciaisUsuarioService}.
     *
     * @param jwtEncoder componente para codificação de tokens JWT
     * @param jwtDecoder componente para decodificação de tokens JWT
     * @param bCryptPasswordEncoder encoder para criptografar senhas
     * @param credenciaisUsuarioRepository repositório de credenciais de usuários
     * @param baseService utilitário para validações genéricas
     * @param emailService serviço de envio de e-mails
     */
    public CredenciaisUsuarioService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, BCryptPasswordEncoder bCryptPasswordEncoder, CredenciaisUsuarioRepository credenciaisUsuarioRepository, BaseService baseService, EmailService emailService) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.credenciaisUsuarioRepository = credenciaisUsuarioRepository;
        this.baseService = baseService;
        this.emailService = emailService;
    }

    /**
     * Verifica se a senha fornecida pelo usuário corresponde à senha armazenada.
     *
     * @param credencialUsuario credencial armazenada no banco
     * @param credenciaisUsuarioRequestDTO credenciais fornecidas pelo usuário
     * @return {@code true} se a senha for válida, {@code false} caso contrário
     */
    private boolean credenciaisSaoValidas(CredencialUsuario credencialUsuario, CredenciaisUsuarioRequestDTO credenciaisUsuarioRequestDTO){
        return bCryptPasswordEncoder.matches(credenciaisUsuarioRequestDTO.getSenha(), credencialUsuario.getSenha());
    }

    /**
     * Gera um token JWT autenticado para o usuário.
     *
     * @param credencialUsuario credencial do usuário autenticado
     * @return objeto {@link TokenJWT} contendo o token, tempo de expiração e role
     */
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

    /**
     * Realiza o login do usuário com base nas credenciais fornecidas.
     *
     * @param credenciaisUsuarioRequestDTO DTO contendo e-mail e senha
     * @return {@link TokenJWT} autenticado se as credenciais forem válidas
     * @throws BusinessException se o usuário não for encontrado ou a senha estiver incorreta
     */
    public TokenJWT login(CredenciaisUsuarioRequestDTO credenciaisUsuarioRequestDTO){
        Optional<CredencialUsuario> credencialUsuario = credenciaisUsuarioRepository.findByEmail(credenciaisUsuarioRequestDTO.getEmail());
        if(credencialUsuario.isEmpty()){
            throw new BusinessException("Usuário não encontrado");
        } else if (!credenciaisSaoValidas(credencialUsuario.get(), credenciaisUsuarioRequestDTO)) {
            throw new BusinessException("Senha inválida");
        }

        return gerarTokenJWT(credencialUsuario.get());
    }

    /**
     * Registra um novo usuário no sistema com base no tipo informado.
     *
     * @param credencial credencial do novo usuário
     * @param tipoUsuario tipo de usuário: "CLIENTE" ou "PROFISSIONAL"
     * @return {@link TokenJWT} autenticado após o registro
     * @throws BusinessException se o e-mail já estiver cadastrado ou o tipo for inválido
     */
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

    /**
     * Gera um token JWT para recuperação de senha com validade de 5 minutos.
     *
     * @param email e-mail do usuário que solicitou a recuperação
     * @return token JWT como {@code String}
     */
    private String generateResetToken(String email) {
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

    /**
     * Valida o token JWT de recuperação de senha.
     * <p>
     * Verifica se o token está válido, não expirado e se é do tipo "reset-password".
     *
     * @param token token JWT recebido via link de recuperação
     * @return e-mail associado ao token
     * @throws BusinessException se o token for inválido, expirado ou de tipo incorreto
     */
    private String validateResetToken(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            if (!"reset-password".equals(jwt.getClaim("type"))) {
                throw new BusinessException("Tipo de token inválido");
            }
            return jwt.getSubject();
        } catch (JwtException e) {
            throw new BusinessException("Token inválido ou expirado");
        }
    }

    /**
     * Inicia o processo de recuperação de conta para o usuário.
     * <p>
     * Gera um token JWT de recuperação e envia um e-mail com o link de redefinição.
     *
     * @param email e-mail do usuário que deseja recuperar a conta
     * @param linkFrontParaRedefinirAsenha URL do frontend onde o token será embutido
     * @return resposta HTTP indicando sucesso no envio do e-mail
     * @throws BusinessException se o e-mail não estiver cadastrado ou os campos forem inválidos
     */
    public ResponseEntity<String> esquecerSenha(String email, String linkFrontParaRedefinirAsenha){
        baseService.verificarCampo("email", email);
        baseService.verificarCampo("linkFrontParaRedefinirAsenha", linkFrontParaRedefinirAsenha);
        Optional<CredencialUsuario> credencialUsuario = credenciaisUsuarioRepository.findByEmail(email);
        if(credencialUsuario.isEmpty()){
            throw new BusinessException("Nenhum usuário com esse email foi encontrado");
        }
        String token = generateResetToken(email);
        String link = linkFrontParaRedefinirAsenha+"?token="+token;
        emailService.enviarEmailParaRecuperacaoDeConta(credencialUsuario.get().getEmail(), link);
        return ResponseEntity.ok("Email para redefinição de senha enviado, será expirado em 5 minutos");
    }

    /**
     * Redefine a senha do usuário com base em um token de recuperação válido.
     * <p>
     * Após a redefinição, retorna um novo {@link TokenJWT} autenticado.
     * @param novaSenha nova senha definida pelo usuário
     * @param tokenResetPassword token JWT recebido via link de recuperação
     * @return novo {@link TokenJWT} autenticado
     * @throws BusinessException se o token for inválido ou o e-mail não estiver cadastrado
     */
    public TokenJWT recuperarContaResetSenha(String novaSenha, String tokenResetPassword){
        String email = validateResetToken(tokenResetPassword);
        CredencialUsuario credencialUsuario = credenciaisUsuarioRepository
                .findByEmail(email).orElseThrow(()
                -> new BusinessException("Nenhum usuário com esse email foi encontrado"));
        credencialUsuario.setSenha(bCryptPasswordEncoder.encode(novaSenha));
        credenciaisUsuarioRepository.save(credencialUsuario);
        return gerarTokenJWT(credencialUsuario);
    }

}
