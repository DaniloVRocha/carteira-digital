package com.danilo.carteira.dto;

import java.io.Serializable;

public class EmailDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;

	public EmailDTO() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
