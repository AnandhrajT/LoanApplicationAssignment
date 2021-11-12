package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserModel {

	@Id
	private long accountNumber;
	private String name;
	private String emailId;
	private String password;
	private String confirmPassword;
	
	public UserModel() {
	}
	
	public UserModel(String name, String emailId, long accountNumber, String password, String confirmPassword) {
		super();
		this.name = name;
		this.emailId = emailId;
		this.accountNumber = accountNumber;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}
	
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
}
