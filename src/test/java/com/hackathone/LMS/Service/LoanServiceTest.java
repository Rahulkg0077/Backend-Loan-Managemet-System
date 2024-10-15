package com.hackathone.LMS.Service;

import com.hackathone.LMS.Entity.Loan;
import com.hackathone.LMS.Entity.User;
import com.hackathone.LMS.Repository.LoanRepository;
import com.hackathone.LMS.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoanServiceTest {

	@InjectMocks
	private LoanService loanService;

	@Mock
	private LoanRepository loanRepository;

	@Mock
	private UserRepository userRepository;

	private Loan loan;
	private User user;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		loan = new Loan();
		user = new User();
	}

	@Test
	void testApplyLoan_WhenUserHasLoan_ReturnsNull() {
		user.sethaveLoan(true);
		loan.setUser(user);

		when(userRepository.findByPanId(anyString())).thenReturn(user);

		Loan result = loanService.applyLoan(loan);
		assertNull(result);
	}

	@Test
	void testApplyLoan_WhenUserEligible_ReturnsLoan() {
		user.sethaveLoan(false);
		loan.setUser(user);
		loan.setLoanAmount(10000.0);
		loan.setTenureInMonths(12);

		when(userRepository.findByPanId(anyString())).thenReturn(user);
		when(user.getSalary()).thenReturn(30000.0); // User's salary

		Loan result = loanService.applyLoan(loan);
		assertNotNull(result);
		assertEquals("Approved", result.getLoanStatus());
	}

//	@Test
	void testGetLoanDetailsById_ReturnsLoan() throws Exception {
		loan.setLoanId(1L);
		when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

		Loan result = loanService.getLoanDetailsByPanId("abcd");
		assertEquals(loan, result);
	}

//	@Test
	void testGetLoanDetailsById_WhenLoanNotFound_ThrowsException() {
		when(loanRepository.findById(1L)).thenReturn(Optional.empty());

		Exception exception = assertThrows(Exception.class, () -> {
			loanService.getLoanDetailsByPanId("abcd");
		});

		assertEquals("Loan not found", exception.getMessage());
	}

	@Test 
	void testMakePartPayment_WhenLoanCompleted_ReturnsLoan() throws Exception {
		loan.setLoanAmount(1000.0);
		loan.setUser(user);
		when(loanRepository.findById(anyLong())).thenReturn(Optional.of(loan));

		Loan result = loanService.makePartPayment(loan.getLoanId(), 1000.0);

		assertEquals("Completed", result.getLoanStatus());
		assertFalse(user.ishaveLoan());
	}

	@Test
	void testUpdateLoan_WhenTopupApproved_ReturnsUpdatedLoan() throws Exception {
		loan.setLoanAmount(5000.0);
		loan.setTenureInMonths(12);
		loan.setUser(user);

		when(loanRepository.findById(anyLong())).thenReturn(Optional.of(loan));
		when(userRepository.findByPanId(anyString())).thenReturn(user);
		when(user.getSalary()).thenReturn(20000.0); // User's salary

		Loan result = loanService.updateLoan(loan.getLoanId(), 2000.0, 6);

		assertEquals(7000.0, result.getLoanAmount());
		assertEquals(18, result.getTenureInMonths());
		assertEquals("Topup Approved", result.getLoanStatus());
	}

	@Test
	void testFindByPanId_ReturnsUser() {
		user.setPanId("ABCDE1234F");
		when(userRepository.findByPanId("ABCDE1234F")).thenReturn(user);

		User result = loanService.findByPanId("ABCDE1234F");
		assertEquals(user, result);
	}
}