package com.momentum.validatingforminput;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class EmployeeCreationForm {
	@NotNull(message = "First Name can not be null!!")
	@Pattern(regexp = "^[A-Za-z]+$", message = "First Name is invalid!!")
	private String firstName;

	@NotNull(message = "Last Name can not be null!!")
	@Pattern(regexp = "^[A-Za-z]+$", message = "Last Name is invalid!!")
	private String lastName;

	@NotNull(message = "Email address can not be null!!")
	private String emailAddress;

	@NotNull(message = "Employee number can not be null!!")
	private String employeeNumber;

	@NotNull(message = "Resendential address can not be null!!")
	@Pattern(regexp = "^[0-9A-Za-z, ]+$", message = "Resendential address is invalid!!")
	private String residentialAddress;

	@Pattern(regexp = "^[0-9A-Za-z, ]+$", message = "Postal address is invalid!!")
	private String postalAddress;

	private String landLineNumber;

	@NotNull(message = "Mobile number can not be null!!")
	private String mobileNumber;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getResidentialAddress() {
		return residentialAddress;
	}

	public void setResidentialAddress(String residentialAddresse) {
		this.residentialAddress = residentialAddresse;
	}

	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(String postalAddresse) {
		this.postalAddress = postalAddresse;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getLandLineNumber() {
		return landLineNumber;
	}

	public void setLandLineNumber(String landLineNumber) {
		this.landLineNumber = landLineNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
}
