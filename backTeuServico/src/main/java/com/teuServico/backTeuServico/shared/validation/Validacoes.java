package com.teuServico.backTeuServico.shared.validation;
import com.teuServico.backTeuServico.shared.exceptions.BusinessException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class Validacoes {
    public void verificarCampo(String nomeParametro, String valorParametro) {
        if (valorParametro == null || valorParametro.isBlank()) {
            String motivo = valorParametro == null ? "nulo" : "vazio";
            throw new BusinessException(String.format("%s não pode ser %s", nomeParametro, motivo));
        }
    }

    public void verificarDataNascimento(LocalDate dataNascimento){
        LocalDate hoje = LocalDate.now();
        LocalDate minData = LocalDate.of(1900, 1, 1);
        if (dataNascimento == null) {
            throw new BusinessException("Data de nascimento não pode ser nula.");
        }
        if (dataNascimento.isBefore(minData)) {
            throw new BusinessException("Data de nascimento não pode ser anterior a " + minData);
        }
        if (dataNascimento.isAfter(hoje)) {
            throw new BusinessException("Data de nascimento não pode estar no futuro.");
        }
    }

    public <T> void validarEmail(String email){
        verificarCampo("email", email);
        String regex = "^(?!.*\\.\\.)[A-Za-z0-9](?:[A-Za-z0-9._%+-]{0,62}[A-Za-z0-9])?@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if(!email.trim().matches(regex)){
            throw new BusinessException("O email é inválido");
        }
    }

    public void validarId(String valorParametro){
        if (valorParametro == null || valorParametro.isBlank()) {
            String motivo = valorParametro == null ? "nulo" : "vazio";
            throw new BusinessException(String.format("ID não pode ser %s", motivo));
        }
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

    public int extrairNumeroPaginaValido(String pagina){
        verificarCampo("pagina", pagina);
        int numeroPagina = transformarEmNumeroInt(pagina, "Número de página inválido: " + pagina);
        if(numeroPagina < 1){
            throw new BusinessException("Pagina nao pode ser menor que 1");
        }
        return numeroPagina;
    }


}