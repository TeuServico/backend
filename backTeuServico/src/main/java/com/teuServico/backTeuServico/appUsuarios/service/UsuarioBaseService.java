package com.teuServico.backTeuServico.appUsuarios.service;

import com.teuServico.backTeuServico.appUsuarios.model.*;
import com.teuServico.backTeuServico.appUsuarios.repository.ClienteRepository;
import com.teuServico.backTeuServico.appUsuarios.repository.CredenciaisUsuarioRepository;
import com.teuServico.backTeuServico.appUsuarios.repository.ProfissionalRepository;
import com.teuServico.backTeuServico.shared.exceptions.BusinessException;
import com.teuServico.backTeuServico.shared.utils.Criptografia;
import com.teuServico.backTeuServico.shared.utils.BaseService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

/**
 * Serviço utilitário para operações comuns entre usuários do sistema (Cliente e Profissional).
 * <p>
 */
@Service
public class UsuarioBaseService {
    private final ClienteRepository clienteRepository;
    private final ProfissionalRepository profissionalRepository;
    private final Criptografia criptografia;
    private final BaseService baseService;

    /**
     * Construtor da classe {@code UsuarioBaseService}.
     *
     * @param clienteRepository repositório de clientes
     * @param profissionalRepository repositório de profissionais
     * @param criptografia utilitário para criptografar e descriptografar dados
     * @param baseService serviço auxiliar para operacoes comuns a toda aplicacao
     */
    public UsuarioBaseService(ClienteRepository clienteRepository, ProfissionalRepository profissionalRepository, Criptografia criptografia, BaseService baseService) {
        this.clienteRepository = clienteRepository;
        this.profissionalRepository = profissionalRepository;
        this.criptografia = criptografia;
        this.baseService = baseService;
    }

    /**
     * Verifica se o CPF informado já está cadastrado como cliente ou profissional.
     *
     * @param cpf CPF criptografado
     * @return {@code true} se o CPF já estiver cadastrado, {@code false} caso contrário
     */
    private boolean cpfJaCadastrado(String cpf) {
        return clienteRepository.findByCpf(cpf).isPresent() ||
                profissionalRepository.findByCpf(cpf).isPresent();
    }

    /**
     * Verifica se o telefone informado já está cadastrado como cliente ou profissional.
     *
     * @param telefone telefone criptografado
     * @return {@code true} se o telefone já estiver cadastrado, {@code false} caso contrário
     */
    private boolean telefoneJaCadastrado(String telefone) {
        return clienteRepository.findByTelefone(telefone).isPresent() ||
                profissionalRepository.findByTelefone(telefone).isPresent();
    }

    /**
     * Normaliza os dados textuais do usuário.
     * @param usuarioBase instância de {@link UsuarioBase} contendo os dados a serem normalizados
     * @param <T> tipo genérico que estende {@link UsuarioBase}
     * @return instância normalizada do usuário
     */
    public <T extends UsuarioBase> T normalizarUsuario(T usuarioBase) {
        usuarioBase.setNomeCompleto(baseService.normalizarString(usuarioBase.getNomeCompleto()));
        CredencialUsuario credencialUsuario = usuarioBase.getCredencialUsuario();
        credencialUsuario.setEmail(baseService.normalizarString(credencialUsuario.getEmail()));

        Endereco endereco = usuarioBase.getEndereco();
        endereco.setRua(baseService.normalizarString(endereco.getRua()));
        endereco.setNumero(baseService.normalizarString(endereco.getNumero()));
        endereco.setComplemento(baseService.normalizarString(endereco.getComplemento()));
        endereco.setBairro(baseService.normalizarString(endereco.getBairro()));
        endereco.setCidade(baseService.normalizarString(endereco.getCidade()));
        endereco.setCep(baseService.normalizarString(endereco.getCep()));

        return usuarioBase;
    }

    /**
     * Valida se o CPF e telefone informados são únicos no sistema.
     * <p>
     * Os dados são criptografados antes da verificação.
     * @param cpf CPF do usuário
     * @param telefone telefone do usuário
     * @throws BusinessException se o CPF ou telefone já estiverem cadastrados
     */
    public void validarUnicidadeInfoUsuario(String cpf, String telefone) {
        cpf = criptografia.criptografar(cpf);
        telefone = criptografia.criptografar(telefone);
       if (cpfJaCadastrado(cpf)) {
            throw new BusinessException("CPF já cadastrado");
        } else if (telefoneJaCadastrado(telefone)) {
            throw new BusinessException("Telefone já cadastrado");
        }
    }

    /**
     * Normaliza e criptografa os dados sensíveis do usuário.
     * @param usuarioBase instância de {@link UsuarioBase} com os dados originais
     * @param <T> tipo genérico que estende {@link UsuarioBase}
     * @return instância criptografada do usuário
     */
    public <T extends UsuarioBase> T criptografarUsuario(T usuarioBase) {
        T usuario = normalizarUsuario(usuarioBase);
        usuario.setNomeCompleto(criptografia.criptografar(usuario.getNomeCompleto()));
        usuario.setTelefone(criptografia.criptografar(usuario.getTelefone()));
        usuario.setCpf(criptografia.criptografar(usuario.getCpf()));

        Endereco endereco = usuario.getEndereco();
        endereco.setRua(criptografia.criptografar(endereco.getRua()));
        endereco.setNumero(criptografia.criptografar(endereco.getNumero()));
        endereco.setComplemento(criptografia.criptografar(endereco.getComplemento()));
        endereco.setBairro(criptografia.criptografar(endereco.getBairro()));
        endereco.setCidade(criptografia.criptografar(endereco.getCidade()));
        endereco.setCep(criptografia.criptografar(endereco.getCep()));

        return usuario;
    }

    /**
     * Descriptografa os dados sensíveis do usuário.
     * @param usuarioBase instância de {@link UsuarioBase} com os dados criptografados
     * @param <T> tipo genérico que estende {@link UsuarioBase}
     * @return instância descriptografada do usuário
     */
    public <T extends UsuarioBase> T  descriptografarUsuario(T usuarioBase){

        usuarioBase.setNomeCompleto(criptografia.descriptografar(usuarioBase.getNomeCompleto()));
        usuarioBase.setTelefone(criptografia.descriptografar(usuarioBase.getTelefone()));
        usuarioBase.setCpf(criptografia.descriptografar(usuarioBase.getCpf()));

        Endereco endereco = usuarioBase.getEndereco();
        endereco.setRua(criptografia.descriptografar(endereco.getRua()));
        endereco.setNumero(criptografia.descriptografar(endereco.getNumero()));
        endereco.setComplemento(criptografia.descriptografar(endereco.getComplemento()));
        endereco.setBairro(criptografia.descriptografar(endereco.getBairro()));
        endereco.setCidade(criptografia.descriptografar(endereco.getCidade()));
        endereco.setCep(criptografia.descriptografar(endereco.getCep()));

        return usuarioBase;
    }

    /**
     * Recupera os dados do perfil de um usuário com base em sua credencial.
     * <p>
     * Utiliza uma função de busca e um mapeador para retornar o DTO correspondente.
     *
     * @param idCredencial ID da credencial do usuário
     * @param metodoDeBusca função que busca o usuário pelo ID da credencial
     * @param mapper função que transforma o usuário em DTO de resposta
     * @param <T> tipo do usuário que estende {@link UsuarioBase}
     * @param <R> tipo do DTO de resposta
     * @param <ID> tipo do identificador (geralmente {@link UUID})
     * @return DTO de resposta contendo os dados do perfil descriptografado
     * @throws BusinessException se o usuário não for encontrado
     */
    public <T extends UsuarioBase, R, ID> R meuPerfil(UUID idCredencial, Function<UUID, Optional<T>> metodoDeBusca, Function<T,R> mapper) {
        Optional<T> usuario = metodoDeBusca.apply(idCredencial);
        if(usuario.isEmpty()){
            throw new BusinessException("Não foram encontrdas informaçoes para esse usuario");
        }
        return mapper.apply(descriptografarUsuario(usuario.get()));
    }

}
