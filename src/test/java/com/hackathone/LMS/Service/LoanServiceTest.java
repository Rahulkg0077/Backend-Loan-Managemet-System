package com.hackathone.LMS.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import com.hackathone.LMS.Entity.Loan;
import com.hackathone.LMS.Entity.Otp;
import com.hackathone.LMS.Entity.User;
import com.hackathone.LMS.Repository.LoanRepository;
import com.hackathone.LMS.Repository.OtpRepository;
import com.hackathone.LMS.Repository.UserRepository;

public class LoanServiceTest {

    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OtpRepository otpRepository;

    @Mock
    private JavaMailSender mailSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateOtpByPan() {
        User user = new User();
        user.setEmail("test@mail.com");
        when(userRepository.findByPanId(any(String.class))).thenReturn(user);

        loanService.generateOtpByPan("ABCDE1234F");

        // Verify that the OTP was generated and sent
        Optional<Otp> otp = otpRepository.findByEmail("test@mail.com");
        assertNotNull(otp);
    }

    @Test
    void testValidateOtp() {
        Otp otp = new Otp();
        otp.setOtp("123456");
        otp.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        when(otpRepository.findByEmail(any(String.class))).thenReturn(Optional.of(otp));

        boolean isValid = loanService.validateOtp("test@mail.com", "123456");

        assertEquals(true, isValid);
    }

    @Test
    void testApplyLoan() {
        User user = new User();
        user.setPanId("ABCDE1234F");
        user.setSalary(50000.0);
        when(userRepository.findByPanId(any(String.class))).thenReturn(user);

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setLoanAmount(100000.0);
        loan.setTenureInMonths(12);

        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        Loan savedLoan = loanService.applyLoan(loan);

        assertNotNull(savedLoan);
        assertEquals("Approved", savedLoan.getLoanStatus());
    }
}
