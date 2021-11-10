package com.loanApplication.Backend.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class EmiModel {
	
	@Id
	@Column
	private String paymentId;
	@Column
	private String loanId;
	@Column
	private String paymentDate;
	@Column
	private int principalAmount;
	
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public int getPrincipalAmount() {
		return principalAmount;
	}
	public void setPrincipalAmount(int principalAmount) {
		this.principalAmount = principalAmount;
	}
	public float getInterestExtimate() {
		return interestExtimate;
	}
	public void setInterestExtimate(float interestExtimate) {
		this.interestExtimate = interestExtimate;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public float getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(float paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	@Column
	private float interestExtimate;
	@Column
	private String paymentStatus;
	@Column
	private float paymentAmount;
}
