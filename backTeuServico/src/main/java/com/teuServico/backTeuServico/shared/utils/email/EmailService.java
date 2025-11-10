package com.teuServico.backTeuServico.shared.utils.email;


import com.teuServico.backTeuServico.shared.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private static final String ORIGEM_DEFAULT =  "naoresponda@teuservico.com";
    private static final String MSG_CRIACAO_CONTA = "Parabéns, você acabou de criar sua conta na TeuServico. Agradecemos e esperamos que você encontre o que precisa!!!!";

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private EmailRecord criarEmailRecord(String destinatario, String assunto, String conteudoDaMensagem){
        return new EmailRecord(destinatario, assunto, conteudoDaMensagem);
    }

    private void enviarEmail(String assunto, String origem,String destinatario, String conteudoDaMensagem){
        var message = new SimpleMailMessage();
        EmailRecord email = criarEmailRecord(destinatario, assunto, conteudoDaMensagem);
        message.setFrom(origem);
        message.setTo(email.destinatario());
        message.setSubject(email.assunto());
        message.setText(email.corpoDaMensagem());
        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            throw new BusinessException("Falha ao enviar e-mail: " + e.getMessage());
        }
    }

    @Async
    public void enviarEmailDeCriacaoDeConta(String emailDoUsuario){
        try {
            enviarEmail(
                    "Criação de conta na teu serviço",
                    ORIGEM_DEFAULT,
                    emailDoUsuario,
                    MSG_CRIACAO_CONTA);
        }catch (BusinessException e){
            log.warn("Falha ao enviar e-mail para: " + emailDoUsuario);
        }
    }

}
