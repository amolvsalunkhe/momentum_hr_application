package com.momentum.validatingforminput;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginForm {
	@NotNull(message = "UserName can not be null!!")
	@Size(min = 2, max = 30, message = "UserName can not be null!!")
	@Pattern(regexp = "^[A-Za-z]+$", message = "UserName is invalid!!")
	private String userName;
	
	@NotNull(message = "Password can not be null!!")
	@Size(min = 2, max = 30, message = "Password can not be null!!")
	@Pattern(regexp = "^[A-Za-z]+$", message = "Password is invalid!!")
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
