package com.teuServico.backTeuServico.shared.utils.email;

import com.teuServico.backTeuServico.agendamento.model.Agendamento;
import com.teuServico.backTeuServico.agendamento.model.enums.StatusEnum;
import com.teuServico.backTeuServico.appUsuarios.model.Cliente;
import com.teuServico.backTeuServico.appUsuarios.model.Profissional;
import com.teuServico.backTeuServico.shared.exceptions.BusinessException;
import com.teuServico.backTeuServico.shared.utils.Criptografia;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
@Slf4j
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final Criptografia criptografia;

    public EmailService(JavaMailSender javaMailSender, Criptografia criptografia) {
        this.javaMailSender = javaMailSender;
        this.criptografia = criptografia;
    }

    private static final String ORIGEM_DEFAULT = "naoresponda@teuservico.com";
    private static final String MSG_CRIACAO_CONTA = "Parabéns, você acabou de criar sua conta na TeuServico. Agradecemos e esperamos que você encontre o que precisa!!!!";

    private EmailRecord criarEmailRecord(String destinatario, String assunto, String conteudoDaMensagem) {
        return new EmailRecord(destinatario, assunto, conteudoDaMensagem);
    }

    private void logFalhaEnvioEmail(Exception e) {
        log.warn("Falha ao enviar e-mail: {}", e.getMessage());
    }

    private void enviarEmail(String assunto, String origem, String destinatario, String conteudoDaMensagem) {
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
    public void enviarEmailDeCriacaoDeConta(String emailDoUsuario) {
        try {
            enviarEmail(
                    "Criação de conta na teu serviço",
                    ORIGEM_DEFAULT,
                    emailDoUsuario,
                    MSG_CRIACAO_CONTA);
        } catch (BusinessException e) {
            logFalhaEnvioEmail(e);
        }
    }


    private void enviarEmailDeStatusdeAgendamentoParaCliente(Agendamento agendamento) {
        Cliente cliente = agendamento.getCliente();
        Profissional profissional = agendamento.getOfertaServico().getProfissional();

        String contatoProfissional = "\n\nEntre em contato com o profissional para mais detalhes:\n"
                + "Telefone: " + criptografia.descriptografar(profissional.getTelefone()) + "\n"
                + "Email: " + profissional.getCredencialUsuario().getEmail();

        String mensagemTipoOperacao;
        StatusEnum status = agendamento.getStatus();

        switch (status) {
            case CANCELADO: {
                mensagemTipoOperacao = "\n\nSeu agendamento foi cancelado.\nSe tiver dúvidas, estamos à disposição.\nObrigado por usar o TeuServiço!";
                break;
            }
            case AGUARDANDO_CONFIRMACAO_PROFISSIONAL: {
                mensagemTipoOperacao = "Sua solicitação de agendamento foi enviada com sucesso.\nAguarde a confirmação do profissional.";
                break;
            }
            case AGUARDANDO_CONFIRMACAO_CLIENTE: {
                mensagemTipoOperacao = "O profissional " + criptografia.descriptografar(profissional.getNomeCompleto()) +
                        " enviou uma contra-oferta para seu agendamento.\n" +
                        "Data sugerida: " + agendamento.getContraOferta().getContraOfertaDataDeEntrega().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                        "Preço sugerido: R$ " + agendamento.getContraOferta().getContraOfertaPrecoDesejado() +
                        contatoProfissional;
                break;
            }
            case EM_ANDAMENTO: {
                mensagemTipoOperacao = "Seu serviço está em andamento.\nData prevista de entrega: " +
                        agendamento.getDataDeEntrega().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                        contatoProfissional;
                break;
            }
            case CONCLUIDO: {
                mensagemTipoOperacao = "\n\nO serviço foi finalizado com sucesso.\nEsperamos que tenha ficado satisfeito!" +
                        contatoProfissional;
                break;
            }
            default: {
                mensagemTipoOperacao = "\n\nAcompanhe o status do seu agendamento pelo sistema.";
                break;
            }
        }

        String mensagem = "Olá " + criptografia.descriptografar(cliente.getNomeCompleto()) + ",\n\n" + mensagemTipoOperacao;

        try {
            enviarEmail(
                    "Teu Servico - Agendamento",
                    ORIGEM_DEFAULT,
                    cliente.getCredencialUsuario().getEmail(),
                    mensagem);
        } catch (BusinessException e) {
            logFalhaEnvioEmail(e);
        }
    }


    private void enviarEmailDeStatusdeAgendamentoParaProfissional(Agendamento agendamento) {
        Profissional profissional = agendamento.getOfertaServico().getProfissional();
        Cliente cliente = agendamento.getCliente();

        String contatoCliente = "\n\nEntre em contato com o cliente para mais detalhes:\n"
                + "Telefone: " + criptografia.descriptografar(cliente.getTelefone()) + "\n"
                + "Email: " + cliente.getCredencialUsuario().getEmail();

        String mensagemTipoOperacao;
        StatusEnum status = agendamento.getStatus();

        switch (status) {
            case CANCELADO: {
                mensagemTipoOperacao = "\n\nSeu agendamento foi cancelado.\nSe tiver dúvidas, estamos à disposição.\nObrigado por usar o TeuServiço!";
                break;
            }
            case AGUARDANDO_CONFIRMACAO_PROFISSIONAL: {
                mensagemTipoOperacao = "Um novo agendamento foi solicitado por " +
                        criptografia.descriptografar(cliente.getNomeCompleto()) + ".\n" +
                        "Data sugerida: " + agendamento.getDataDeEntrega().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                        "\n\nAcesse o sistema para confirmar ou enviar uma contra-oferta.";
                break;
            }
            case AGUARDANDO_CONFIRMACAO_CLIENTE: {
                mensagemTipoOperacao = "Você enviou uma contra-oferta para " +
                        criptografia.descriptografar(cliente.getNomeCompleto()) + ".\n" +
                        "Aguardando resposta do cliente.\n" +
                        "Data sugerida: " + agendamento.getContraOferta().getContraOfertaDataDeEntrega().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                        "Preço sugerido: R$ " + agendamento.getContraOferta().getContraOfertaPrecoDesejado() +
                        contatoCliente;
                break;
            }
            case EM_ANDAMENTO: {
                mensagemTipoOperacao = "O agendamento com " +
                        criptografia.descriptografar(cliente.getNomeCompleto()) +
                        " foi confirmado e está em andamento.\n" +
                        "Data de entrega: " + agendamento.getDataDeEntrega().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                        contatoCliente;
                break;
            }
            default: {
                return;
            }
        }

        String mensagem = "Olá " + criptografia.descriptografar(profissional.getNomeCompleto()) + ",\n\n" + mensagemTipoOperacao;

        try {
            enviarEmail(
                    "Teu Servico - Agendamento",
                    ORIGEM_DEFAULT,
                    profissional.getCredencialUsuario().getEmail(),
                    mensagem);
        } catch (BusinessException e) {
            logFalhaEnvioEmail(e);
        }
    }

    @Async
    public void notificarClienteEprofissional(Agendamento agendamento){
        try {
            enviarEmailDeStatusdeAgendamentoParaCliente(agendamento);
            Thread.sleep(1000); // necessario para o mailtrap free
        } catch (BusinessException | InterruptedException e) {
            logFalhaEnvioEmail(e);
        }

        try {
            enviarEmailDeStatusdeAgendamentoParaProfissional(agendamento);
        } catch (BusinessException e) {
            logFalhaEnvioEmail(e);
        }
    }

}