package com.teuServico.backTeuServico.shared.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.cglib.proxy.UndeclaredThrowableException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

/**
 * Classe responsável por tratar exceções de forma global na aplicação.
 * Utiliza a anotação {@link RestControllerAdvice} para interceptar exceções lançadas pelos controllers
 * e retornar respostas padronizadas em formato JSON.
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Cria e retorna os headers padrão para respostas JSON.
     * @return {@link HttpHeaders} com o tipo de conteúdo definido como {@link MediaType#APPLICATION_JSON}.
     */
    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * Cria um objeto {@link ResponseError}
     *
     * @param message Mensagem de erro a ser exibida.
     * @param statusCode Código de status HTTP correspondente ao erro.
     * @return Instância de {@link ResponseError} preenchida.
     */
    private ResponseError responseError(String message, HttpStatus statusCode) {
        ResponseError responseError = new ResponseError();
        responseError.setStatus("error");
        responseError.setError(message);
        responseError.setStatusCode(statusCode.value());
        return responseError;
    }

    /**
     * Trata exceções genéricas não capturadas por handlers específicos.
     * Se a exceção for do tipo {@link UndeclaredThrowableException}, delega para o handler de {@link BusinessException}.
     * @param e Exceção lançada.
     * @param request Contexto da requisição.
     * @return {@link ResponseEntity} com os detalhes do erro.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneral(Exception e, WebRequest request) {
        if (e instanceof UndeclaredThrowableException undeclared) {
            Throwable cause = undeclared.getUndeclaredThrowable();
            if (cause instanceof BusinessException business) {
                return handleBusinessException(business, request);
            }
        }
        String message = "Erro no servidor, não foi possível processar sua solicitação.";
        ResponseError error = responseError(message, HttpStatus.INTERNAL_SERVER_ERROR);
        return handleExceptionInternal(e, error, defaultHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * Trata exceções do tipo {@link BusinessException}, que representam erros de regra de negócio.
     * @param e Exceção de negócio lançada.
     * @param request Contexto da requisição.
     * @return {@link ResponseEntity} com os detalhes do erro.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException e, WebRequest request) {
        ResponseError error = responseError(e.getMessage(), HttpStatus.CONFLICT);
        return handleExceptionInternal(e, error, defaultHeaders(), HttpStatus.CONFLICT, request);
    }

    /**
     * Trata erros de leitura da requisição, como campos mal formatados.
     * E verifica se o erro está relacionado ao formato de data e ajusta a mensagem de erro.
     * <P>caso contrario retorna:</P>
     * Erro ao ler a requisição: verifique o formato dos campos enviados.
     *
     * @param ex Exceção lançada ao não conseguir ler a requisição.
     * @param headers Cabeçalhos HTTP.
     * @param status  Código de status HTTP.
     * @param request Contexto da requisição.
     * @return {@link ResponseEntity} com os detalhes do erro.
     */
    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        String message = "Erro ao ler a requisição: verifique o formato dos campos enviados.";

        if (ex.getCause() instanceof InvalidFormatException formatException &&
                formatException.getTargetType().equals(java.time.LocalDate.class)) {
            message = "Formato de data inválido. Use o padrão yyyy-MM-dd.";
        }

        ResponseError error = responseError(message, HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(ex, error, defaultHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Trata erros de validação de argumentos nos métodos dos controllers.
     * Extrai todas as mensagens de erro dos campos inválidos e as concatena em uma única string.
     * @param ex Exceção lançada ao validar argumentos.
     * @param headers Cabeçalhos HTTP.
     * @param status  Código de status HTTP.
     * @param request Contexto da requisição.
     * @return {@link ResponseEntity} com os detalhes do erro.
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<String> mensagens = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        String mensagemFinal = String.join("; ", mensagens);
        ResponseError error = responseError(mensagemFinal, HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(ex, error, defaultHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Trata exceções do tipo {@link AccessDeniedException}, quando um usuário tenta acesso a um recurso ao qual não tem permissão
     * @param ex Exceção lancada
     * @param request Contexto da requisição.
     * @return {@link ResponseEntity} com os detalhes do erro.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
        String message = "Você não tem permissão para acessar este recurso.";
        ResponseError error = responseError(message, HttpStatus.FORBIDDEN);
        return handleExceptionInternal(ex, error, defaultHeaders(), HttpStatus.FORBIDDEN, request);
    }

}