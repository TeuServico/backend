package com.teuServico.backTeuServico.shared.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuração para a documentação OpenAPI (Swagger) do projeto Teu Servico.
 * <p>
 * Esta configuração define:
 * <ul>
 *     <li>Informações básicas da API (título e versão)</li>
 *     <li>Esquema de segurança baseado em autenticação Bearer com JWT</li>
 *     <li>Respostas padrão reutilizáveis para cenários comuns de erro</li>
 * </ul>
 */
@Configuration
public class SwaggerConfig {
    /**
     * Bean que configura a instância do {@link OpenAPI} utilizada pelo Swagger.
     * <p>
     * Define o esquema de segurança JWT e adiciona respostas padronizadas para facilitar
     * a reutilização em endpoints da API.
     * @return instância personalizada de {@link OpenAPI}
     */
    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info().title("Teu Servico").version("v1"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT"))
                        .addResponses("FalhaNaRequisicao", new ApiResponse().description("Dados inválidos"))
                        .addResponses("Conflito", new ApiResponse().description("Exceção de negócio"))
                        .addResponses("ErroInterno", new ApiResponse().description("Erro interno do servidor"))
                        .addResponses("NaoAutenticado", new ApiResponse().description("Credencias inválidas, token ausente, inválido ou expirado"))
                        .addResponses("NaoAutorizado", new ApiResponse().description("Usuário autenticado, mas sem permissão")));
    }
}