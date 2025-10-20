package com.teuServico.backTeuServico.shared.exceptions;

/**
 * Exceção personalizada para representar erros de negócio na aplicação.
 */
public class BusinessException extends  RuntimeException{
    /**
     * Cria uma BusinessException com uma mensagem simples.
     * @param message Mensagem descritiva do erro de negócio.
     */
    public BusinessException(String message) { // retorna uma mensagem simples
        super(message);
    }

    /**
     * Cria uma BusinessException com uma mensagem formatada.
     * <p>
     * Usa varargs para permitir a interpolação de parâmetros na mensagem.
     * </p>
     * Exemplo:
     * new BusinessException("Cliente %s não encontrado", nomeCliente)
     * @param message Mensagem com placeholders para formatação.
     * @param params Parâmetros que serão inseridos na mensagem formatada.
     */
    public BusinessException(String message, Object ... params) { // retorna uma mensagem customizada atraves do varargs
        super(String.format(message, params));
    }

}
