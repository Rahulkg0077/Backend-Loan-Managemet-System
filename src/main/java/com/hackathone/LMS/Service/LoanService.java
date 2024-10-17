package com.hackathone.LMS.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.hackathone.LMS.Entity.Loan;
import com.hackathone.LMS.Entity.LoanDumpForCompleted;
import com.hackathone.LMS.Entity.LoanDumpForRejected;
import com.hackathone.LMS.Entity.Otp;
import com.hackathone.LMS.Entity.User;
import com.hackathone.LMS.Repository.LoanDumpForCompletedRepository;
import com.hackathone.LMS.Repository.LoanDumpForRejectedRepository;
import com.hackathone.LMS.Repository.LoanRepository;
import com.hackathone.LMS.Repository.OtpRepository;
import com.hackathone.LMS.Repository.UserRepository;

@Service
public class LoanService {

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LoanDumpForRejectedRepository rejectedLoans;

	@Autowired
	private LoanDumpForCompletedRepository comapltedLoans;

	@Autowired
	private OtpRepository otpRepository;
	
	@Autowired
	private JavaMailSender mailSender;

	private final Double rateOfInterest = 8.5;

	public Loan getLoanDetailsByPanId(String id) throws Exception {
		User user = userRepository.findByPanId(id);
		Loan loan = loanRepository.findLoanByUser(user);

		BigDecimal bd = new BigDecimal(Double.toString(loan.getLoanAmount()));
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		loan.setLoanAmount(bd.doubleValue());

		BigDecimal emi = new BigDecimal(Double.toString(loan.getEmi()));
		emi = emi.setScale(2, RoundingMode.HALF_UP);
		loan.setEmi(emi.doubleValue());
		return loan;
	}

	public void generateOtpByPan(String panId) {
		User user = userRepository.findByPanId(panId);
		sendOtp(user.getEmail());
	}

	public void sendOtp(String email) {
		String otp = generateOtp();
		Otp otpEntity = new Otp();
		otpEntity.setemail(email);
		otpEntity.setOtp(otp);
		otpEntity.setExpiryTime(LocalDateTime.now().plusMinutes(5));
		otpRepository.save(otpEntity);

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Your OTP to login into LoanManagementSystem");
		message.setText("Your Otp is :" + otp);
		mailSender.send(message);
	}
	
	public boolean validateOtp(String mail, String otp) {
		Optional<Otp> otpEntity = otpRepository.findByEmail(mail);
		if (otpEntity.isPresent()) {
			Otp otpDetails = otpEntity.get();
			if(otpDetails.getOtp().equals(otp) && otpDetails.getExpiryTime().isAfter(LocalDateTime.now())) {
				otpRepository.deleteById(otpDetails.getId());
				return true;
			}
		}
		
		return false;
	}

	public String generateOtp() {
		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);
		return String.valueOf(otp);
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
				moveRejectedAndCompletedLoanToDump(loan);
			}

			loan.setTotalPendingEmis(loan.getTenureInMonths());

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
			moveRejectedAndCompletedLoanToDump(loan);
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

	private void moveRejectedAndCompletedLoanToDump(Loan loan) {

		if (loan.getLoanStatus().equals("Completed")) {
			LoanDumpForCompleted loanDump = new LoanDumpForCompleted();
			loanDump.setLoanId(loan.getLoanId());
			loanDump.setUserId(loan.getUser().getUserId());
			loanDump.setLoanAmount(loan.getLoanAmount());
			loanDump.setTenureInMonths(loan.getTenureInMonths());
			loanDump.setInterestRate(loan.getInterestRate());
			loanDump.setEmi(loan.getEmi());
			loanDump.setTotalPendingEmis(loan.getTotalPendingEmis());
			loanDump.setLoanStatus(loan.getLoanStatus());
			loanDump.setRejectionReason(loan.getRejectionReason());
			loanDump.setCreatedAt(loan.getCreatedAt());

			comapltedLoans.save(loanDump);

		} else if (loan.getLoanStatus().equals("Rejected")) {
			LoanDumpForRejected loanDump = new LoanDumpForRejected();
			loanDump.setLoanId(loan.getLoanId());
			loanDump.setUserId(loan.getUser().getUserId());
			loanDump.setLoanAmount(loan.getLoanAmount());
			loanDump.setTenureInMonths(loan.getTenureInMonths());
			loanDump.setInterestRate(loan.getInterestRate());
			loanDump.setEmi(loan.getEmi());
			loanDump.setTotalPendingEmis(loan.getTotalPendingEmis());
			loanDump.setLoanStatus(loan.getLoanStatus());
			loanDump.setRejectionReason(loan.getRejectionReason());
			loanDump.setCreatedAt(loan.getCreatedAt());

			rejectedLoans.save(loanDump);
		}

		loanRepository.delete(loan);
	}

	public void emiPayment() {
		List<Loan> loans = loanRepository.findAll();

		for (Loan loan : loans) {
			if (loan.getLoanAmount() <= loan.getEmi()) {
				loan.setLoanStatus("Completed");
				moveRejectedAndCompletedLoanToDump(loan);
			} else {
				loan.setLoanAmount(loan.getLoanAmount() - loan.getEmi());
				loan.setTotalPendingEmis(loan.getTotalPendingEmis() - 1);

				if (loan.getLoanAmount() == 0) {
					loan.setLoanStatus("Completed");
					moveRejectedAndCompletedLoanToDump(loan);
				} else {
					loanRepository.save(loan);
				}
			}
		}
	}

}
