package com.teuServico.backTeuServico.shared.exceptions;
import java.util.Date;

/**
 * Padroniza as respostas da aplicação.
 */
public class ResponseError {
    /**
     * Data e hora em que o erro ocorreu.
     */
    private Date timestamp = new Date();
    /**
     * Status textual da resposta.
     */
    private String status = "error";
    /**
     * Código HTTP associado ao erro.
     */
    private int statusCode = 400;
    /**
     * Mensagem descritiva do erro ocorrido.
     */
    private String error;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}