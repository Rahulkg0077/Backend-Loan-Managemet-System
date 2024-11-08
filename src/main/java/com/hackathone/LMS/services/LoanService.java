package com.hackathone.LMS.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackathone.LMS.Entities.Loan;
import com.hackathone.LMS.ErrorMessages.BaseResponse;
import com.hackathone.LMS.repositories.LoanRepository;

@Service
public class LoanService {

	@Autowired
	private LoanRepository loanRepo;
	
	public BaseResponse<?> findLoanDetails(Long loanId){
		
		Optional<Loan> loan = loanRepo.findById(loanId);
		
		if(loan.isEmpty())
			return new BaseResponse<>(null, "Loan not found for this User", 0, 400);
		
		return new BaseResponse<>(List.of(loan.get()), "Loan Found successfully", 1, 200);
	}
	
	public BaseResponse<?> applyLoan(Loan loan){
		
		loan.setCreatedAt(LocalDateTime.now());
		
		return null;
	}
}
