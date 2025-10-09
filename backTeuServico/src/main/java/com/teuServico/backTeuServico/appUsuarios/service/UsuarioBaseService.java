package com.teuServico.backTeuServico.appUsuarios.service;

import com.teuServico.backTeuServico.appUsuarios.model.*;
import com.teuServico.backTeuServico.appUsuarios.repository.ClienteRepository;
import com.teuServico.backTeuServico.appUsuarios.repository.CredenciaisUsuarioRepository;
import com.teuServico.backTeuServico.appUsuarios.repository.ProfissionalRepository;
import com.teuServico.backTeuServico.shared.exceptions.BusinessException;
import com.teuServico.backTeuServico.shared.utils.Criptografia;
import org.springframework.stereotype.Service;

@Service
public class UsuarioBaseService {
    private final CredenciaisUsuarioRepository credenciaisUsuarioRepository;
    private final ClienteRepository clienteRepository;
    private final ProfissionalRepository profissionalRepository;
    private final Criptografia criptografia;

    public UsuarioBaseService(CredenciaisUsuarioRepository credenciaisUsuarioRepository, ClienteRepository clienteRepository, ProfissionalRepository profissionalRepository, Criptografia criptografia) {
        this.credenciaisUsuarioRepository = credenciaisUsuarioRepository;
        this.clienteRepository = clienteRepository;
        this.profissionalRepository = profissionalRepository;
        this.criptografia = criptografia;
    }

    private boolean cpfJaCadastrado(String cpf) {
        return clienteRepository.findByCpf(cpf).isPresent() ||
                profissionalRepository.findByCpf(cpf).isPresent();
    }

    private boolean telefoneJaCadastrado(String telefone) {
        return clienteRepository.findByTelefone(telefone).isPresent() ||
                profissionalRepository.findByTelefone(telefone).isPresent();
    }

    public <T extends UsuarioBase> T normalizarUsuario(T usuario){
        return usuario; //TODO implementar normalizacao dos dados
    }

    public void validarUnicidadeUsuario(String cpf, String email, String telefone) {
        cpf = criptografia.criptografar(cpf);
        telefone = criptografia.criptografar(telefone);
        if (credenciaisUsuarioRepository.findByEmail(email).isPresent()) {
            throw new BusinessException("Email já cadastrado");
        }else if (cpfJaCadastrado(cpf)) {
            throw new BusinessException("CPF já cadastrado");
        } else if (telefoneJaCadastrado(telefone)) {
            throw new BusinessException("Telefone já cadastrado");
        }
    }


    public <T extends UsuarioBase>  T criptografarUsuario(T usuario){
        usuario.setNomeCompleto(criptografia.criptografar(usuario.getNomeCompleto()));
        usuario.setTelefone(criptografia.criptografar(usuario.getTelefone()));
        usuario.setCpf(criptografia.criptografar(usuario.getCpf()));

        Endereco enderecoOriginal = usuario.getEndereco();
        Endereco enderecoCriptografado = new Endereco();

        enderecoCriptografado.setRua(criptografia.criptografar(enderecoOriginal.getRua()));
        enderecoCriptografado.setNumero(criptografia.criptografar(enderecoOriginal.getNumero()));
        enderecoCriptografado.setComplemento(criptografia.criptografar(enderecoOriginal.getComplemento()));
        enderecoCriptografado.setBairro(criptografia.criptografar(enderecoOriginal.getBairro()));
        enderecoCriptografado.setCidade(criptografia.criptografar(enderecoOriginal.getCidade()));
        enderecoCriptografado.setCep(criptografia.criptografar(enderecoOriginal.getCep()));
        enderecoCriptografado.setEstado(enderecoOriginal.getEstado());

        usuario.setEndereco(enderecoCriptografado);

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
