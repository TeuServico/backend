package com.teuServico.backTeuServico.appUsuarios.service;

import com.teuServico.backTeuServico.appUsuarios.model.*;
import com.teuServico.backTeuServico.appUsuarios.repository.ClienteRepository;
import com.teuServico.backTeuServico.appUsuarios.repository.CredenciaisUsuarioRepository;
import com.teuServico.backTeuServico.appUsuarios.repository.ProfissionalRepository;
import com.teuServico.backTeuServico.shared.exceptions.BusinessException;
import com.teuServico.backTeuServico.shared.utils.Criptografia;
import com.teuServico.backTeuServico.shared.utils.BaseService;
import org.springframework.stereotype.Service;

@Service
public class UsuarioBaseService {
    private final ClienteRepository clienteRepository;
    private final ProfissionalRepository profissionalRepository;
    private final Criptografia criptografia;
    private final BaseService baseService;

    public UsuarioBaseService(ClienteRepository clienteRepository, ProfissionalRepository profissionalRepository, Criptografia criptografia, BaseService baseService) {
        this.clienteRepository = clienteRepository;
        this.profissionalRepository = profissionalRepository;
        this.criptografia = criptografia;
        this.baseService = baseService;
    }

    private boolean cpfJaCadastrado(String cpf) {
        return clienteRepository.findByCpf(cpf).isPresent() ||
                profissionalRepository.findByCpf(cpf).isPresent();
    }

    private boolean telefoneJaCadastrado(String telefone) {
        return clienteRepository.findByTelefone(telefone).isPresent() ||
                profissionalRepository.findByTelefone(telefone).isPresent();
    }


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

    public void validarUnicidadeInfoUsuario(String cpf, String telefone) {
        cpf = criptografia.criptografar(cpf);
        telefone = criptografia.criptografar(telefone);
       if (cpfJaCadastrado(cpf)) {
            throw new BusinessException("CPF já cadastrado");
        } else if (telefoneJaCadastrado(telefone)) {
            throw new BusinessException("Telefone já cadastrado");
        }
    }


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

}
