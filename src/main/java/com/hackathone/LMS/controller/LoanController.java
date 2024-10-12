package com.hackathone.LMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hackathone.LMS.Entity.Loan;
import com.hackathone.LMS.Entity.User;
import com.hackathone.LMS.ErrorMessages.ErrorResponse;
import com.hackathone.LMS.Service.LoanService;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

	@Autowired
	private LoanService loanService;

	@GetMapping("/{loanId}")
	public ResponseEntity<?> getLoanDetailsById(@PathVariable Long loanId) {
		try {
			Loan loan = loanService.getLoanDetailsById(loanId);
			return ResponseEntity.ok(loan);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("Loan details are not avaalble for this ID: " + loanId, 404));
		}
	}

	@PostMapping("/apply")
	public ResponseEntity<?> applyLoan(@RequestBody Loan laon) {
		Loan savedLoan = loanService.applyLoan(laon);
		if (savedLoan != null) {
			return ResponseEntity.ok(savedLoan);
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new ErrorResponse("This user cannot apply for loan...", 403));
		}
	}

	@PutMapping("/partpayment/{loanId}")
	public ResponseEntity<?> makePartPayment(@PathVariable Long loanId, @RequestParam Double partPayment){
		try {
			Loan updateLoan = loanService.makePartPayment(loanId, partPayment);
			return ResponseEntity.ok(updateLoan);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Loan not found for the Id : " + loanId, 404));
		}
	}
	
	@GetMapping("/PAN/{panId}")
	public ResponseEntity<?> findUserByPanId(@PathVariable String panId) {

		User user = loanService.findByPanId(panId);
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("User with this PAN is available ", 404));
		}

	}

	@GetMapping
	public ResponseEntity<?> getAllLoanDetails() {
		List<Loan> loanList = loanService.getAllLoanDetails();
		return ResponseEntity.ok(loanList);
	}
}
