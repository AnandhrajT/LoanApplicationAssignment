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

	
	@PostMapping("/login")
	@CrossOrigin(origins="http://localhost:4200")
	public UserModel loginUser(@RequestBody UserModel user) throws Exception {
		String tempEmailId=user.getEmailId();
		String tempPassword=user.getPassword();
		UserModel obj=null;
		if(tempEmailId != null && tempPassword != null)
		{
			obj=service.fetchUserByEmailAddressAndPassword(tempEmailId, tempPassword);	
		}
		if(obj==null) {
			throw new Exception("Invalid User");
		}
		return obj;
	}
	
	@PostMapping("/Register")
	@CrossOrigin(origins="http://localhost:4200")
	public UserModel registerUser(@RequestBody UserModel user) throws Exception {
		String temp=user.getEmailId();
		if(temp!=null && "".equals(temp))
		{
			UserModel obj=service.fetchUserByEmailAddress(temp);
			if(obj != null) {
				throw new Exception("User Already Exsitis");
			}
		}
		UserModel obj=null;
		obj=service.saveUser(user);
		return obj;
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
