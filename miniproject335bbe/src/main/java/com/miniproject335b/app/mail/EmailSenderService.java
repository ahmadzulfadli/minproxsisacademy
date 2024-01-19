package com.miniproject335b.app.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendEmail(String to, String subject, String body) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("solidayoyomedical@gmail.com");
		msg.setTo(to);
		msg.setText(body);
		msg.setSubject(subject);
		mailSender.send(msg);
		
		System.out.println("Mail Sent");
	}
}
