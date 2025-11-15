package com.teuServico.backTeuServico.shared.aws.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

/**
 * Classe de configuração AWS para integração com o serviço Amazon S3.
 * <p>
 * Esta classe configura um bean do {@link S3Client} permitindo a comunicação com buckets S3.
 * <p>
 * As credenciais e o endpoint são obtidos de variáveis de ambiente:
 * <ul>
 *   <li><strong>AWS_ENDPOINT</strong>: URL do endpoint S3 (ex: http://localhost:4566 para LocalStack)</li>
 *   <li><strong>AWS_ACESS_KEY</strong>: Chave de acesso AWS</li>
 *   <li><strong>AWS_SECRET_KEY</strong>: Chave secreta AWS</li>
 * </ul>
 */

@Configuration
public class AwsConfig {

    @Value("${AWS_ENDPOINT}")
    private String endpoint;

    @Value("${AWS_ACESS_KEY}")
    private String awsAcessKey;

    @Value("${AWS_SECRET_KEY}")
    private String awsSecretKey;

    /**
     * Cria e configura um bean do {@link S3Client} para acesso ao serviço Amazon S3.
     * @return instância configurada de {@link S3Client}
     */

    @Bean
    public S3Client s3Client(){
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(awsAcessKey, awsSecretKey);
        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .forcePathStyle(true)
                .endpointOverride(URI.create(endpoint))
                .region(Region.US_EAST_1)
                .serviceConfiguration(S3Configuration.builder().build())
                .build();
    }

}
