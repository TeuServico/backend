package com.teuServico.backTeuServico.shared.utils.email;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void enviarEmail(EmailRecord email){
        var messsage = new SimpleMailMessage();
        messsage.setFrom("naoresponda@teuservico.com");
        messsage.setTo(email.to());
        messsage.setSubject(email.assunto());
        messsage.setText(email.corpoDaMensagem());
        javaMailSender.send(messsage);
    }

}
