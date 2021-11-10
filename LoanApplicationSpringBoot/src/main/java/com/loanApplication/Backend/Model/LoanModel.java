package com.loanApplication.Backend.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class LoanModel {
	@Id
	@Column
	private String loanId;
	@Column
	private String customerId;
	@Column
	private int loanAmount;
	@Column
	private String tradeDate;
	@Column
	private String startDate;
	@Column
	private String endDate;
	@Column
	private int loanDuration;
	@Column
	private String paymentInterval;
	@Column
	private int payments;
	@Column
	private float rateOfInterest;
	@Column
	private String paymentTerm;
	@Column
	private int InterestExtimate;
	@Column
	private boolean payment;
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public int getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(int loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getLoanDuration() {
		return loanDuration;
	}
	public void setLoanDuration(int loanDuration) {
		this.loanDuration = loanDuration;
	}
	public String getPaymentInterval() {
		return paymentInterval;
	}
	public void setPaymentInterval(String paymentInterval) {
		this.paymentInterval = paymentInterval;
	}
	public int getPayments() {
		return payments;
	}
	public void setPayments(int payments) {
		this.payments = payments;
	}
	public float getRateOfInterest() {
		return rateOfInterest;
	}
	public void setRateOfInterest(float rateOfInterest) {
		this.rateOfInterest = rateOfInterest;
	}
	public String getPaymentTerm() {
		return paymentTerm;
	}
	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}
	public int getInterestExtimate() {
		return InterestExtimate;
	}
	public void setInterestExtimate(int interestExtimate) {
		InterestExtimate = interestExtimate;
	}
	public boolean isPayment() {
		return payment;
	}
	public void setPayment(boolean payment) {
		this.payment = payment;
	}

}
