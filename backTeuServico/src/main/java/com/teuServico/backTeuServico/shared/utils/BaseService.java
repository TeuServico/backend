package com.teuServico.backTeuServico.shared.utils;

import com.teuServico.backTeuServico.shared.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BaseService {
    public String normalizarString(String conteudo){
        return conteudo.toLowerCase();
    }

    public void validarId(String valorParametro){
        verificarCampo("id", valorParametro);
        if (!valorParametro.matches("\\d+")){
            throw new BusinessException("ID inválido, não é numerico!");
        }
    }

    public int transformarEmNumeroInt(String numero, String mensagemException){
        int numeroConvertido;
        try {
            numeroConvertido = Integer.parseInt(numero);
        } catch (NumberFormatException e) {
            throw new BusinessException(mensagemException);
        }
        return numeroConvertido;
    }

    public void verificarCampo(String nomeParametro, String valorParametro) {
        if (valorParametro == null || valorParametro.isBlank()) {
            String motivo = valorParametro == null ? "nulo" : "vazio";
            throw new BusinessException(String.format("%s não pode ser %s", nomeParametro, motivo));
        }
    }

    public int extrairNumeroPaginaValido(String pagina){
        verificarCampo("pagina", pagina);
        int numeroPagina = transformarEmNumeroInt(pagina, "Número de página inválido: " + pagina);
        if(numeroPagina < 1){
            throw new BusinessException("Pagina nao pode ser menor que 1");
        }
        return numeroPagina;
    }


}
