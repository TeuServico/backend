package com.teuServico.backTeuServico.shared.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

/**
 * Classe de configuração de segurança da aplicação.
 * Define políticas de autenticação, autorização e manipulação de JWT.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    /**
     * Recurso contendo a chave pública RSA para validação de tokens JWT.
     */
    @Value("${JWT_PUBLIC_KEY}")
    private Resource publicKeyResource;

    /**
     * Recurso contendo a chave privada RSA para assinatura de tokens JWT.
     */
    @Value("${JWT_PRIVATE_KEY}")
    private Resource privateKeyResource;

    /**
     * Configura a cadeia de filtros de segurança HTTP.
     * @param httpSecurity objeto de configuração do Spring Security
     * @return cadeia de filtros configurada
     * @throws Exception em caso de erro na configuração
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                        .requestMatchers("/tiposervico/buscar/**").permitAll()
                        .requestMatchers("/ofertaservico/buscar/**").permitAll()
                        .requestMatchers("/cliente/criar").permitAll()
                        .requestMatchers("/profissional/criar").permitAll()
                        .requestMatchers("/credenciais/login").permitAll()
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults());

        return httpSecurity.build();
    }

    /**
     * Converte o token JWT em uma instância de autenticação com base na claim "role".
     * @return conversor de autenticação JWT
     */
    @Bean
    public Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        return jwt -> {
            String role = jwt.getClaimAsString("role");
            Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
            return new JwtAuthenticationToken(jwt, authorities);
        };
    }

    /**
     * Lê e converte a chave pública RSA do recurso configurado.
     * @return chave pública RSA
     * @throws Exception em caso de erro na leitura ou conversão
     */
    @Bean
    public RSAPublicKey rsaPublicKey() throws Exception {
        String publicKeyContent = new String(publicKeyResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        publicKeyContent = publicKeyContent.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyContent);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(spec);
    }

    /**
     * Lê e converte a chave privada RSA do recurso configurado.
     * @return chave privada RSA
     * @throws Exception em caso de erro na leitura ou conversão
     */
    @Bean
    public RSAPrivateKey rsaPrivateKey() throws Exception {
        String privateKeyContent = new String(privateKeyResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        privateKeyContent = privateKeyContent.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyContent);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) keyFactory.generatePrivate(spec);
    }

    /**
     * Cria o decodificador de JWT usando a chave pública RSA.
     * @param publicKey chave pública RSA
     * @return instância de JwtDecoder
     */
    @Bean
    public JwtDecoder jwtDecoder(RSAPublicKey publicKey) {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    /**
     * Cria o codificador de JWT usando as chaves RSA.
     * @param publicKey  chave pública RSA
     * @param privateKey chave privada RSA
     * @return instância de JwtEncoder
     */
    @Bean
    public JwtEncoder jwtEncoder(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        JWK jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    /**
     * Codificador de senhas usando BCrypt.
     * @return instância de BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
