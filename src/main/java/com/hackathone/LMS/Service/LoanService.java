package com.hackathone.LMS.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackathone.LMS.Entity.Loan;
import com.hackathone.LMS.Entity.User;
import com.hackathone.LMS.Repository.LoanRepository;
import com.hackathone.LMS.Repository.UserRepository;

@Service
public class LoanService {

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private UserRepository userRepository;

	private final Double rateOfInterest = 8.5;

	public Loan getLoanDetailsById(Long id) throws Exception {
		return loanRepository.findById(id).orElseThrow(() -> new Exception());
	}

	public List<Loan> getAllLoanDetails() {
		return loanRepository.findAll();
	}

	public Loan applyLoan(Loan loan) {
		User user = userRepository.findByPanId(loan.getUser().getPanId());
				
		if (user != null && user.ishaveLoan()) {
			return null;
		} else {
			
			if(user == null)
				user = loan.getUser();

			double emi = calculateEMI(loan.getLoanAmount(), rateOfInterest, loan.getTenureInMonths());
			loan.setEmi(emi);
			loan.setInterestRate(rateOfInterest);
			loan.setCreatedAt(LocalDateTime.now());

			if (emi <= getMaxEMI(user.getSalary())) {
				user.sethaveLoan(true);
				loan.setLoanStatus("Approved");
			} else {
				loan.setLoanStatus("Rejected");
				loan.setRejectionReason("EMI exceeds maximum allowed limit");
			}

			loan.setUser(user);
			return loanRepository.save(loan);
		}
	}

	public Loan makePartPayment(Long loanId, Double partPaymentAmount) throws Exception {
		Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new Exception());
		Double principal = loan.getLoanAmount() - partPaymentAmount;

		loan.setLoanAmount(principal);
		if (principal == 0) {
			User user = loan.getUser();
			user.sethaveLoan(false);
			loan.setLoanStatus("Completed");
			loan.setUser(user);
		}

		return loanRepository.save(loan);

	}

	public User findByPanId(String panId) {
		return userRepository.findByPanId(panId);
	}

	private double calculateEMI(double loanAmount, double interestRate, int tenureInMonths) {
		double monthlyInterestRate = interestRate / (12 * 100); // monthly interest rate
		return (loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, tenureInMonths))
				/ (Math.pow(1 + monthlyInterestRate, tenureInMonths) - 1);
	}

	private double getMaxEMI(double salary) {
		return (salary * 30) / 100;
	}
}
