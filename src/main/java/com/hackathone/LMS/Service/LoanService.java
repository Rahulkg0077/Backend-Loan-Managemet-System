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

	public Loan getLoanDetailsByPanId(String id) throws Exception {
		User user = userRepository.findByPanId(id);		
		return loanRepository.findLoanByUser(user);
	}

	public Loan applyLoan(Loan loan) {
		User user = userRepository.findByPanId(loan.getUser().getPanId());

		if (user != null && user.ishaveLoan()) {
			return null;
		} else {

			if (user == null)
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

			Double pendindEmi = loan.getLoanAmount() / emi;

			loan.setUser(user);
			userRepository.save(user);
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
			userRepository.save(user);
			loanRepository.delete(loan);
			return loan;
		} else {
			return loanRepository.save(loan);
		}

	}

	public Loan updateLoan(Long loanId, Double loanAmount, Integer tenureInMonths) {
		Loan loan = loanRepository.findById(loanId).orElse(null);

		Double updatedLoanAmount = loan.getLoanAmount() + loanAmount;
		Integer updatedTenure = loan.getTenureInMonths() + tenureInMonths;
		double emi = calculateEMI(updatedLoanAmount, rateOfInterest, updatedTenure);
		User user = userRepository.findByPanId(loan.getUser().getPanId());
		if (emi <= getMaxEMI(user.getSalary())) {
			loan.setLoanAmount(updatedLoanAmount);
			loan.setTenureInMonths(updatedTenure);
			loan.setEmi(emi);
			loan.setLoanStatus("Topup Approved");
		} else {
			loan.setLoanStatus("Topup Rejected");
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

	public void emiPayment() {

		List<Loan> loans = loanRepository.findByLoanStatus("Approved");
		
		if (!loans.isEmpty()) {

			for (Loan loan : loans) {
				System.out.println("After" + loan);
				if (loan.getLoanAmount() < loan.getEmi()) {
					loanRepository.delete(loan);
				} else {

					loan.setLoanAmount(loan.getLoanAmount() - loan.getEmi());

					loan.setTotalPendingEmis(loan.getTotalPendingEmis() - 1);

					if (loan.getLoanAmount() == (double) 0) {
						loanRepository.delete(loan);
					} else {
						
						System.out.println("After" + loan);
						loanRepository.save(loan);
					}
				}

			}
		}
	}
}
