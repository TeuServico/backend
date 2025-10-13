package com.teuServico.backTeuServico.shared.utils;

import org.springframework.stereotype.Service;

@Service
public class BaseService {
    public String normalizarString(String conteudo){
        return conteudo.toLowerCase();
    }
}
