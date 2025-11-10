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

@Service
public class BaseService {
    private final ClienteRepository clienteRepository;
    private final ProfissionalRepository profissionalRepository;

    public BaseService(ClienteRepository clienteRepository, ProfissionalRepository profissionalRepository) {
        this.clienteRepository = clienteRepository;
        this.profissionalRepository = profissionalRepository;
    }

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

    public UUID validarUUID(String uuid){
        verificarCampo("uuid", uuid);
        try {
            return UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new BusinessException("UUID inválido: " + uuid);
        }
    }

    public Cliente buscarClientePorTokenJWT(JwtAuthenticationToken token){
        UUID idCredencial = UUID.fromString(token.getName());
        Optional<Cliente> cliente = clienteRepository.findByCredencialUsuario_Id(idCredencial);
        if (cliente.isEmpty()){
            throw new BusinessException("Esse cliente nao foi encontrado");
        }
        return cliente.get();
    }

    public Profissional buscarProfissionalPorTokenJWT(JwtAuthenticationToken token){
        UUID idCredencial = UUID.fromString(token.getName());
        Optional<Profissional> profissional = profissionalRepository.findByCredencialUsuario_Id(idCredencial);
        if (profissional.isEmpty()){
            throw new BusinessException("Esse profissional nao foi encontrado");
        }
        return profissional.get();
    }

}
