package com.loanApplication.Backend.Controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.loanApplication.Backend.Model.EmiModel;
import com.loanApplication.Backend.Model.LoanModel;
import com.loanApplication.Backend.Model.RegisterCustomerModel;
import com.loanApplication.Backend.Services.LoanApplicationService;


@RestController
@CrossOrigin(origins =  "http://localhost:4200")
@RequestMapping("/api")
public class LoanApplicationController {
	@Autowired
	LoanApplicationService loanApplicationService;
	@GetMapping("/customer/{customerId}")
	private RegisterCustomerModel getCustomerDetails(@PathVariable("customerId") String customerId) {
		return loanApplicationService.getCustomerDetails(customerId);
	}

	
	@GetMapping("/verify-customer")
	private RegisterCustomerModel verifyCustomer(@RequestParam("email") String email, @RequestParam("password") String password) {
		return loanApplicationService.getCustomerDetails(email, password);
	}
	
	@PostMapping("/add-customer")
	private RegisterCustomerModel saveCustomer(@RequestBody RegisterCustomerModel customer) {
		return loanApplicationService.saveCustomerDetails(customer);
	}
	
	@GetMapping("/loans/{customerId}")
	private List<LoanModel> getLoans(@PathVariable("customerId") String customerId) {
		return loanApplicationService.getLoansByCustomerId(customerId);
	}


	
	@PostMapping("/loan")
	private LoanModel saveLoan(@RequestBody LoanModel loan) {
		return loanApplicationService.saveLoan(loan);
	}

	@PutMapping("/update-loanstatus/{loanId}")
	private LoanModel approvedLoan(@PathVariable("loanId") String LoanId) {
		return loanApplicationService.approvedLoan(LoanId);
	}


}
