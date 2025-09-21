package com.teuServico.backTeuServico.shared.utils;
import com.teuServico.backTeuServico.shared.exceptions.BusinessException;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class Criptografia {
    private final String ALGORITHM = "AES";
    private final String SECRET_KEY = System.getenv("SECRET_KEY") ;

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