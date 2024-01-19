package com.miniproject335b.app.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

public class MailChecker {
	@Autowired
	JavaMailSender javaMailSender;
	public static boolean ValidateMail(String email) {
		try {
			InternetAddress internetAddress = new InternetAddress(email);
			internetAddress.validate();
			return true;
		} catch (AddressException e) {
			return false;
		}
	}
}
