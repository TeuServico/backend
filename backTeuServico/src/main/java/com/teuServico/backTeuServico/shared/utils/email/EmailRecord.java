package com.teuServico.backTeuServico.shared.utils.email;

/**
 * Record que representa os dados básicos de um e-mail.
 * <p>
 * Contém informações sobre:
 * <ul>
 *     <li>Destinatário do e-mail</li>
 *     <li>Assunto da mensagem</li>
 *     <li>Corpo da mensagem(conteúdo do email)</li>
 * </ul>
 *
 * @param destinatario       endereço de e-mail do destinatário
 * @param assunto            assunto da mensagem
 * @param corpoDaMensagem    conteúdo do email
 */
public record EmailRecord(String destinatario, String assunto, String corpoDaMensagem) {
}