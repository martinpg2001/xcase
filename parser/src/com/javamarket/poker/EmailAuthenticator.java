package com.javamarket.poker;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;

public class EmailAuthenticator extends jakarta.mail.Authenticator {

	private PasswordAuthentication authentication;

	public EmailAuthenticator(String username, String password) {
		authentication = new PasswordAuthentication(username, password);
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return authentication;
	}
}
