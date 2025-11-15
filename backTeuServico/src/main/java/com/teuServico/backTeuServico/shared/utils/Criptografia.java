package com.teuServico.backTeuServico.shared.utils;
import com.teuServico.backTeuServico.shared.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Classe utilitária responsável por criptografia e descriptografia utilizando o AES (Advanced Encryption Standard).
 * <p>
 * A chave secreta utilizada para criptografar e descriptografar os dados é a{@code SECRET_KEY}.
 * Essa chave deve ter o tamanho de 16, 24 ou 32 bytes, isto é, 16, 24 ou 32 caracteres.
 * <p>
 * Os dados sap criptografados em Base64
 */

@Component
public class Criptografia {

    /** Algoritmo de criptografia utilizado. */
    private final String ALGORITHM = "AES";

    /** Chave secreta obtida da variável de ambiente. */
    @Value("${CRIPTOGRAFIA_SECRET_KEY}")
    private String SECRET_KEY;

    /**
     * Criptografa o texto fornecido.
     *
     * @param texto Conteudo do tipo String a ser criptografado.
     * @return Texto criptografado em Base64.
     * @throws BusinessException Se ocorrer qualquer erro durante o processo de criptografia.
     */
    public String criptografar(String texto)   {
        try {
            SecretKeySpec chave = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cifra = Cipher.getInstance(ALGORITHM);
            cifra.init(Cipher.ENCRYPT_MODE, chave);
            byte[] textoCriptografado = cifra.doFinal(texto.getBytes());
            return Base64.getEncoder().encodeToString(textoCriptografado);
        }catch (Exception e){
            throw  new BusinessException("Erro: " + e);
        }
    }

    /**
     * Descriptografa o conteudo do tipo String.
     *
     * @param textoCriptografado Texto criptografado e codificado em Base64.
     * @return Texto descriptografado.
     * @throws BusinessException Se ocorrer qualquer erro durante o processo de descriptografia.
     */
    public String descriptografar(String textoCriptografado) {
        try {
            SecretKeySpec chave = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cifra = Cipher.getInstance(ALGORITHM);
            cifra.init(Cipher.DECRYPT_MODE, chave);
            byte[] textoDescriptografado = cifra.doFinal(Base64.getDecoder().decode(textoCriptografado));
            return new String(textoDescriptografado);
        }catch (Exception e){
            throw new BusinessException("Erro: " + e);
        }
    }
}