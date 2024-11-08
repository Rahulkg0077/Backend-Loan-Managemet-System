package com.hackathone.LMS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackathone.LMS.Entities.Loan;
import com.hackathone.LMS.ErrorMessages.BaseResponse;
import com.hackathone.LMS.services.LoanService;

@RestController
@RequestMapping("/loan")
public class LoanController {

	@Autowired
	private LoanService loanService;
	
	@PostMapping("/apply")
	public BaseResponse<?> applyLoan(@RequestBody Loan loan){
		return loanService.applyLoan(loan);
	}
	
	@GetMapping("/viewDetails")
	public BaseResponse<?> viewLoanDetails(@PathVariable Long loanId){
		return loanService.findLoanDetails(loanId);
	}
}
