package com.teuServico.backTeuServico.shared.utils;

import com.teuServico.backTeuServico.appUsuarios.model.Cliente;
import com.teuServico.backTeuServico.appUsuarios.model.Profissional;
import com.teuServico.backTeuServico.appUsuarios.repository.ClienteRepository;
import com.teuServico.backTeuServico.appUsuarios.repository.ProfissionalRepository;
import com.teuServico.backTeuServico.shared.exceptions.BusinessException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Serviço utilitário que centraliza validações comuns a toda aplicação.
 */
@Service
public class BaseService {
    private final ClienteRepository clienteRepository;
    private final ProfissionalRepository profissionalRepository;

    /**
     * Construtor da classe BaseService.
     *
     * @param clienteRepository      repositório de clientes
     * @param profissionalRepository repositório de profissionais
     */
    public BaseService(ClienteRepository clienteRepository, ProfissionalRepository profissionalRepository) {
        this.clienteRepository = clienteRepository;
        this.profissionalRepository = profissionalRepository;
    }

    /**
     * Normaliza uma string convertendo-a para letras minúsculas.
     *
     * @param conteudo conteúdo a ser normalizado
     * @return string em minúsculas
     */
    public String normalizarString(String conteudo){
        return conteudo.toLowerCase();
    }

    /**
     * Valida se o parâmetro fornecido é um ID numérico válido.
     *
     * @param valorParametro valor a ser validado
     * @throws BusinessException se o valor for nulo, vazio ou não numérico
     */
    public void validarId(String valorParametro){
        verificarCampo("id", valorParametro);
        if (!valorParametro.matches("\\d+")){
            throw new BusinessException("ID inválido, não é numerico!");
        }
    }

    /**
     * Converte uma string em número inteiro, lançando exceção personalizada em caso de erro.
     *
     * @param numero            string a ser convertida
     * @param mensagemException mensagem da exceção em caso de falha
     * @return número inteiro convertido
     * @throws BusinessException se a conversão falhar
     */
    public int transformarEmNumeroInt(String numero, String mensagemException){
        try {
            return Integer.parseInt(numero);
        } catch (NumberFormatException e) {
            throw new BusinessException(mensagemException);
        }
    }

    /**
     * Verifica se um campo obrigatório foi preenchido.
     *
     * @param nomeParametro  nome do campo
     * @param valorParametro valor do campo
     * @throws BusinessException se o campo for nulo ou vazio
     */
    public void verificarCampo(String nomeParametro, String valorParametro) {
        if (valorParametro == null || valorParametro.isBlank()) {
            String motivo = valorParametro == null ? "nulo" : "vazio";
            throw new BusinessException(String.format("%s não pode ser %s", nomeParametro, motivo));
        }
    }

    /**
     * Extrai e valida o número da página a partir de uma string.

     * @param pagina string representando o número da página
     * @return número da página como inteiro
     * @throws BusinessException se o valor for inválido ou menor que 1
     */
    public int extrairNumeroPaginaValido(String pagina){
        verificarCampo("pagina", pagina);
        int numeroPagina = transformarEmNumeroInt(pagina, "Número de página inválido: " + pagina);
        if(numeroPagina < 1){
            throw new BusinessException("Pagina nao pode ser menor que 1");
        }
        return numeroPagina;
    }

    /**
     * Valida e converte uma string para UUID.
     * @param uuid string a ser validada
     * @return UUID convertido
     * @throws BusinessException se o valor for inválido
     */
    public UUID validarUUID(String uuid){
        verificarCampo("uuid", uuid);
        try {
            return UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new BusinessException("UUID inválido: " + uuid);
        }
    }

    /**
     * Busca um cliente com base no token JWT fornecido.
     * @param token token JWT contendo o ID da credencial
     * @return cliente correspondente
     * @throws BusinessException se o cliente não for encontrado
     */
    public Cliente buscarClientePorTokenJWT(JwtAuthenticationToken token){
        UUID idCredencial = UUID.fromString(token.getName());
        Optional<Cliente> cliente = clienteRepository.findByCredencialUsuario_Id(idCredencial);
        if (cliente.isEmpty()){
            throw new BusinessException("Esse cliente nao foi encontrado");
        }
        return cliente.get();
    }

    /**
     * Busca um profissional com base no token JWT fornecido.
     * @param token token JWT contendo o ID da credencial
     * @return profissional correspondente
     * @throws BusinessException se o profissional não for encontrado
     */
    public Profissional buscarProfissionalPorTokenJWT(JwtAuthenticationToken token){
        UUID idCredencial = UUID.fromString(token.getName());
        Optional<Profissional> profissional = profissionalRepository.findByCredencialUsuario_Id(idCredencial);
        if (profissional.isEmpty()){
            throw new BusinessException("Esse profissional nao foi encontrado");
        }
        return profissional.get();
    }
}