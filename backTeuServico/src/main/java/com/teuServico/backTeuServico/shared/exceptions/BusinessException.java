package com.teuServico.backTeuServico.shared.exceptions;

public class BusinessException extends  RuntimeException{

    public BusinessException(String message) { // retorna uma mensagem simples
        super(message);
    }

    public BusinessException(String message, Object ... params) { // retorna uma mensagem customizada atraves do varargs
        super(String.format(message, params));
    }

}
