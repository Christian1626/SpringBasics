package com.basics.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserForm {
	@NotNull @Pattern(regexp = "[a-zA-Z ]*")
	private String firstname;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	
}
