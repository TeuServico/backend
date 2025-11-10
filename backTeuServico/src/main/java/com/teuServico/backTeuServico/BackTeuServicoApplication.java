package com.teuServico.backTeuServico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BackTeuServicoApplication {

    public static void main(String[] args) {
        // Carregar variáveis de ambiente do .env antes do Spring inicializar
        Dotenv.configure()
                .ignoreIfMissing()
                .systemProperties()
                .load();

        SpringApplication.run(BackTeuServicoApplication.class, args);
    }

}
