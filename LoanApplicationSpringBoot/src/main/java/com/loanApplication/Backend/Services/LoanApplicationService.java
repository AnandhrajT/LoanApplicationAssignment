package com.loanApplication.Backend.Services;


import org.apache.el.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loanApplication.Backend.Model.EmiModel;
import com.loanApplication.Backend.Model.LoanModel;
import com.loanApplication.Backend.Model.RegisterCustomerModel;
import com.loanApplication.Backend.Repository.EmiRepository;
import com.loanApplication.Backend.Repository.LoanApplicationRepository;
import com.loanApplication.Backend.Repository.LoanRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoanApplicationService {
	

	@Autowired
	LoanRepository loanRepository;
	@Autowired
	EmiRepository emiRepository;
	@Autowired
	LoanApplicationRepository loanApplicationRepository;
	private static final Logger logger = LoggerFactory.getLogger(LoanApplicationService.class);

	
	public RegisterCustomerModel saveCustomerDetails(RegisterCustomerModel customer) {
		customer.setCustomerId(generateKey("CID"));
		return loanApplicationRepository.save(customer);

	}
	
	private String generateKey(String prefix) {
		String key = UUID.randomUUID().toString().split("-")[0];
		return prefix+ key;
	}
	public RegisterCustomerModel getCustomerDetails(String customerId) {
		logger.info("Getting customer details for {}", customerId);
		return loanApplicationRepository.findById(customerId).get();
	}

	
	public RegisterCustomerModel getCustomerDetails(String email, String password) {
		List<RegisterCustomerModel> customerList = loanApplicationRepository.findByEmailAndPassword(email, password);
		if (customerList.isEmpty()) {
			return new RegisterCustomerModel();
		}
		logger.info("Verifying existing customer details for {}", email);
		return loanApplicationRepository.findByEmailAndPassword(email, password).get(0);
	}

	public LoanModel approvedLoan(String loanId) {
		// TODO Auto-generated method stub
		Optional<LoanModel> loanapprovedOptional = loanRepository.findById(loanId);
		LoanModel loan = new LoanModel();
		if (loanapprovedOptional.isPresent()) {
			loan = loanapprovedOptional.get();
			loan.setPayment(true);
		}
		logger.info("Approved Loan Status ", loanId);
		return loanRepository.save(loan);
	}

	public LoanModel saveLoan(LoanModel loanModel) {
		// TODO Auto-generated method stub
		loanModel.setInterestExtimate(10);
		loanModel.setPayment(false);
		loanModel.setLoanId(generateKey("LOAN"));
		logger.info("New Loan {} created", loanModel);
		return loanRepository.save(loanModel);

	}
	
	public List<LoanModel> getLoansByCustomerId(String customerId) {
		// TODO Auto-generated method stub
		List<LoanModel> loans = new ArrayList<LoanModel>();
		loanRepository.findByCustomerId(customerId).forEach(loan -> loans.add(loan));
		logger.info("Getting loan details for existing customer {}", customerId);
		return loans;
	}
	
	private void createPaymentSchedule(LoanModel loan) {
		String paymentTerm = loan.getPaymentTerm();
		if (paymentTerm.equals("Interest Only")) {
			createInterestOnlySchedule(loan);
		} else if (paymentTerm.equals("Even Principal")) {
			createEvenPrincipalSchedule(loan);
		}
	}



	private void createEvenPrincipalSchedule(LoanModel loan) {
		List<EmiModel> emiList = new ArrayList<EmiModel>();
		int perPaymentPrincipal = loan.getLoanAmount() / loan.getPayments();
		for (int i = 1; i <= loan.getPayments(); i++) {
			EmiModel emiModel = new EmiModel();
			emiModel.setPaymentId(generateKey("PAY"));
			emiModel.setLoanId(loan.getLoanId());
			emiModel.setPaymentDate(calculatePaymentDate(loan, loan.getPaymentInterval()));
			emiModel.setPrincipalAmount(loan.getLoanAmount());
			emiModel.setInterestExtimate(calculateInterest(loan,perPaymentPrincipal));
			emiModel.setPaymentStatus("PROJECTED");
			emiModel.setPaymentAmount(emiModel.getInterestExtimate()+perPaymentPrincipal);
			emiList.add(emiModel);
		}
		logger.info("Creating Even Principal Schedule for Loan {}", loan);
		emiRepository.saveAll(emiList);
	}

	private void createInterestOnlySchedule(LoanModel loan) {
		List<LoanModel> emiList = new ArrayList<LoanModel>();
		int netPrincipalAmount = loan.getLoanAmount();
		int perPaymentPrincipal = loan.getLoanAmount() / loan.getPayments();
		for (int i = 1; i <= loan.getPayments(); i++) {
			EmiModel emiModel = new EmiModel();
			emiModel.setPaymentId(generateKey("PAY"));
			emiModel.setLoanId(loan.getLoanId());
			emiModel.setPaymentDate(calculatePaymentDate(loan, loan.getPaymentInterval()));
			emiModel.setInterestExtimate(calculateInterest(loan,perPaymentPrincipal));
			if(i==loan.getPayments()) {
				emiModel.setPrincipalAmount(netPrincipalAmount);
				emiModel.setPaymentAmount((emiModel.getInterestExtimate())+(netPrincipalAmount));
			}else {
				emiModel.setPrincipalAmount(0);
				emiModel.setPaymentAmount(emiModel.getInterestExtimate());
			}
			emiModel.setPaymentStatus("Extimated");
			emiList.add(emiModel);
		}
		logger.info("Creating Interest Only  Schedule for Loan {}", loan);
		EmiRepository.saveAll(miModel);
	}
	private float calculateInterest(LoanModel loan, int perPaymentPrincipal) {
		float paymentSchedule = loan.getPayments();
		float principal = loan.getLoanAmount();
		float years = loan.getLoanDuration();
		float interestRate = loan.getRateOfInterest();
		int interestAmount =(int) ((principal * (years / paymentSchedule) * interestRate) / 100);
		principal = principal - perPaymentPrincipal;
		loan.setLoanAmount((int)principal);
		return interestAmount;
	}

	
	private String calculatePaymentDate(LoanModel loan, String paymentInterval) {
		String paymentDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;
		try {
			date = formatter.parse(loan.getStartDate());
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		Calendar paymentDateCalenar = Calendar.getInstance();
		paymentDateCalenar.setTime(date);
		switch (paymentInterval) {
		case "Monthly": {
			paymentDateCalenar.add(Calendar.MONTH, 1);
			paymentDate = "" + paymentDateCalenar.get(Calendar.DATE) + "-"
					+ (paymentDateCalenar.get(Calendar.MONTH) + 1) + "-" + paymentDateCalenar.get(Calendar.YEAR);
			break;
		}
		case "Quarterly": {
			paymentDateCalenar.add(Calendar.MONTH, 3);
			paymentDate = "" + paymentDateCalenar.get(Calendar.DATE) + "-"
					+ (paymentDateCalenar.get(Calendar.MONTH) + 1) + "-" + paymentDateCalenar.get(Calendar.YEAR);
			break;
		}
		case "Half Yearly": {
			paymentDateCalenar.add(Calendar.MONTH, 6);
			paymentDate = "" + paymentDateCalenar.get(Calendar.DATE) + "-"
					+ (paymentDateCalenar.get(Calendar.MONTH) + 1) + "-" + paymentDateCalenar.get(Calendar.YEAR);
			break;
		}
		case "Yearly": {
			paymentDateCalenar.add(Calendar.MONTH, 12);
			paymentDate = "" + paymentDateCalenar.get(Calendar.DATE) + "-"
					+ (paymentDateCalenar.get(Calendar.MONTH) + 1) + "-" + paymentDateCalenar.get(Calendar.YEAR);
			break;
		}

		}
		paymentDate = convertDateFormat(paymentDate);
		loan.setStartDate(paymentDate);
		return paymentDate;
	}

	
	private String convertDateFormat(String paymentDate) {
		if (paymentDate.charAt(1) == '-') {
			paymentDate = "0" + paymentDate;
		}
		if (paymentDate.charAt(4) == '-') {
			paymentDate = paymentDate.substring(0, 3) + "0" + paymentDate.substring(3);
		}
		return paymentDate;
	}

	}