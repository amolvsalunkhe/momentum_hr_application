package com.momentum.models;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(nullable = false, name = "firstName")
	private String firstName;

	@Column(nullable = false, name = "lastName")
	private String lastName;
	
	@Column(nullable = false, name = "emailAddress")
	private String emailAddress;

	@Column(nullable = false, unique = true, name = "employeeNumber")
	private String employeeNumber;

	@OneToMany(mappedBy = "employee")
	private Set<Address> addresses;

	@OneToMany(mappedBy = "employee")
	private Set<ContactDetail> contactDetails;

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

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public Set<ContactDetail> getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(Set<ContactDetail> contactDetails) {
		this.contactDetails = contactDetails;
	}

	public Long getId() {
		return this.id;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

}
