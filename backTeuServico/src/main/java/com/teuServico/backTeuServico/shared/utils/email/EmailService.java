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

/**
 * Serviço responsável pelo envio de emails na aplicação TeuServico.
 * <p>
 * Este serviço lida com:
 * <ul>
 *     <li>Envio de e-mail de boas-vindas após criação de conta</li>
 *     <li>Notificações de status de agendamentos para clientes e profissionais</li>
 *     <li>Montagem e envio de mensagens com conteúdo personalizado</li>
 * </ul>
 * <p>
 * Utiliza {@link JavaMailSender} para envio de e-mails e {@link Criptografia} para tratar dados sensíveis.
 * Os métodos assíncronos permitem que o envio de e-mails não bloqueie o fluxo principal da aplicação.
 */
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

    /**
     * Cria uma instância de {@link EmailRecord} com os dados fornecidos.
     * @param destinatario        endereço de e-mail do destinatário
     * @param assunto             assunto da mensagem
     * @param conteudoDaMensagem  corpo do e-mail
     * @return objeto {@link EmailRecord} contendo os dados do e-mail
     */
    private EmailRecord criarEmailRecord(String destinatario, String assunto, String conteudoDaMensagem) {
        return new EmailRecord(destinatario, assunto, conteudoDaMensagem);
    }

    /**
     * Registra no log uma falha ocorrida durante o envio de e-mail.
     * @param e exceção capturada durante o envio
     */
    private void logFalhaEnvioEmail(Exception e) {
        log.warn("Falha ao enviar e-mail: {}", e.getMessage());
    }

    /**
     * Envia um email simples com os dados fornecidos.
     * <p>
     * Utiliza {@link EmailRecord} para encapsular os dados e {@link JavaMailSender} para realizar o envio.
     *
     * @param assunto             assunto do e-mail
     * @param origem              endereço de e-mail do remetente
     * @param destinatario        endereço de e-mail do destinatário
     * @param conteudoDaMensagem  corpo do e-mail
     * @throws BusinessException se ocorrer falha no envio
     */
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

    /**
     * Envia um e-mail de boas-vindas ao usuário após a criação da conta.
     * <p>
     * Este método é assíncrono para não bloquear o fluxo principal da aplicação.
     * @param emailDoUsuario endereço de email do novo usuário
     */
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


    /**
     * Envia um e-mail de notificação para o cliente com base no status do agendamento.
     * <p>
     * A mensagem é personalizada de acordo com o {@link StatusEnum} do agendamento:
     * <ul>
     *     <li><b>CANCELADO</b>: informa o cancelamento do serviço</li>
     *     <li><b>AGUARDANDO_CONFIRMACAO_PROFISSIONAL</b>: confirma o envio da solicitação</li>
     *     <li><b>AGUARDANDO_CONFIRMACAO_CLIENTE</b>: informa que o profissional enviou uma contra-oferta</li>
     *     <li><b>EM_ANDAMENTO</b>: informa que o serviço está em execução</li>
     *     <li><b>CONCLUIDO</b>: informa que o serviço foi finalizado</li>
     *     <li><b>default</b>: mensagem genérica de acompanhamento</li>
     * </ul>
     * <p>
     * Também inclui os dados de contato do profissional, como telefone e e-mail, descriptografados.
     *
     * @param agendamento objeto {@link Agendamento} com os dados do serviço e do cliente
     */
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


    /**
     * Envia um e-mail de notificação para o profissional com base no status do agendamento.
     * <p>
     * A mensagem é personalizada conforme o {@link StatusEnum} do agendamento:
     * <ul>
     *     <li><b>CANCELADO</b>: informa o cancelamento do serviço</li>
     *     <li><b>AGUARDANDO_CONFIRMACAO_PROFISSIONAL</b>: informa que um novo agendamento foi solicitado pelo cliente</li>
     *     <li><b>AGUARDANDO_CONFIRMACAO_CLIENTE</b>: informa que o profissional enviou uma contra-oferta e aguarda resposta</li>
     *     <li><b>EM_ANDAMENTO</b>: informa que o serviço foi confirmado e está em execução</li>
     *     <li><b>default</b>: não envia notificação</li>
     * </ul>
     * <p>
     * Também inclui os dados de contato do cliente, como telefone e e-mail, descriptografados.
     *
     * @param agendamento objeto {@link Agendamento} contendo os dados do serviço e do profissional
     */
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

    /**
     * Notifica o cliente e o profissional sobre o status atualizado de um agendamento.
     * <p>
     * Este método envia dois e-mails:
     * <ul>
     *     <li>Um para o cliente via {@link #enviarEmailDeStatusdeAgendamentoParaCliente(Agendamento)}</li>
     *     <li>Outro para o profissional via {@link #enviarEmailDeStatusdeAgendamentoParaProfissional(Agendamento)}</li>
     * </ul>
     * <p>
     * Este método é assíncrono para não bloquear o fluxo principal da aplicação.
     * Inclui um pequeno atraso entre os envios para compatibilidade com serviços de e-mail gratuitos como Mailtrap.
     *
     * @param agendamento objeto {@link Agendamento} contendo os dados do serviço, cliente e profissional
     */
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

    /**
     * Envia um e-mail de recuperação de conta para o usuário.
     * <p>
     * O e-mail contém um link para redefinição de senha, fornecido pela interface do frontend.
     * Este método é assíncrono para não bloquear o fluxo principal da aplicação.
     * <p>
     * @param emailUsuario endereço de e-mail do usuário que solicitou a recuperação
     * @param linkFrontParaRedefinicaoDeSenha URL do frontend para o token de redefinição de senha seja embutido
     */
    @Async
    public void enviarEmailParaRecuperacaoDeConta(String emailUsuario, String linkFrontParaRedefinicaoDeSenha){
        try{
            enviarEmail(
                    "Teu Servico - Recuperação da conta -> Reset de senha",
                    ORIGEM_DEFAULT,
                    emailUsuario,
                    "Olá, você solicitou a recuperação de sua conta na Teu servico, por favor, acesse o link abaixo \n\n"+
                            linkFrontParaRedefinicaoDeSenha
            );
        }catch (BusinessException e){
            logFalhaEnvioEmail(e);
        }

    }

    /**
     * Envia uma notificação por e-mail ao usuário informando que sua senha foi alterada com sucesso.
     * <p>
     * Este método é assíncrono para não bloquear o fluxo principal da aplicação.
     * @param emailUsuario endereço de e-mail do usuário que teve a senha redefinida
     */
    @Async
    private void notificarUsuarioRecupercaoSenhaAlterada(String emailUsuario){
        try {
            enviarEmail(
                    "Teu Servico - Recuperação da conta -> Senha alterada",
                    ORIGEM_DEFAULT,
                    emailUsuario,
                    "Olá, sua senha foi alterada com sucesso"
            );
        } catch (BusinessException e) {
            logFalhaEnvioEmail(e);
        }
    }

}