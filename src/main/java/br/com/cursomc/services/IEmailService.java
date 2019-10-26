package br.com.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import br.com.cursomc.domain.Pedido;

public interface IEmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendOrderConfirmationHtmlEmail(Pedido pedido);
	
	void sendHtmlEmail(MimeMessage msg);
	
		
}
