package com.hackathone.LMS.Entity;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
 
import java.time.LocalDateTime;
 
import static org.junit.jupiter.api.Assertions.*;
 
public class LoanTest {
    private Loan loan;
    private User mockUser;
 
    @BeforeEach
    void setUp() {
        loan = new Loan();
        mockUser = Mockito.mock(User.class); // Mocking the User class
    }
 
    @Test
    void testLoanId() {
        loan.setLoanId(1L);
        assertEquals(1L, loan.getLoanId());
    }
 
    @Test
    void testUser() {
        loan.setUser(mockUser);
        assertEquals(mockUser, loan.getUser());
    }
 
    @Test
    void testLoanAmount() {
        loan.setLoanAmount(5000.0);
        assertEquals(5000.0, loan.getLoanAmount());
    }
 
    @Test
    void testInterestRate() {
        loan.setInterestRate(5.5);
        assertEquals(5.5, loan.getInterestRate());
    }
 
    @Test
    void testTenureInMonths() {
        loan.setTenureInMonths(12);
        assertEquals(12, loan.getTenureInMonths());
    }
 
    @Test
    void testEmi() {
        loan.setEmi(450.0);
        assertEquals(450.0, loan.getEmi());
    }
 
    @Test
    void testLoanStatus() {
        loan.setLoanStatus("Approved");
        assertEquals("Approved", loan.getLoanStatus());
    }
 
    @Test
    void testCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        loan.setCreatedAt(now);
        assertEquals(now, loan.getCreatedAt());
    }
 
    @Test
    void testRejectionReason() {
        loan.setRejectionReason("Insufficient Credit Score");
        assertEquals("Insufficient Credit Score", loan.getRejectionReason());
    }
 
    // Example test for interaction with mocked User
    @Test
    void testUserInteraction() {
        loan.setUser(mockUser);
        assertNotNull(loan.getUser());
 
        // Verify that the user is set as expected
        assertEquals(mockUser, loan.getUser());
        // Additional verifications can be added here depending on User's behavior
    }
}