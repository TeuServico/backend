package com.teuServico.backTeuServico.shared.config;

import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;

@Configuration
public class DotenvConfig {

    @PostConstruct
    public void loadDotenv() {
        Dotenv.configure()
                .ignoreIfMissing()
                .systemProperties()
                .load();
    }
}
