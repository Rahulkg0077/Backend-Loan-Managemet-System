package com.hackathone.LMS.controller;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hackathone.LMS.Entity.Loan;
import com.hackathone.LMS.ErrorMessages.ErrorResponse;
import com.hackathone.LMS.Service.LoanService;
 
public class LoanControllerTest {
 
    @InjectMocks
    private LoanController loanController;
 
    @Mock
    private LoanService loanService;
 
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    public void testGetLoanDetailsById_Success() throws Exception {
        Loan mockLoan = new Loan(); // Create a mock loan object
        mockLoan.setLoanId(1L);
        try {
			when(loanService.getLoanDetailsByPanId("ABCD")).thenReturn(mockLoan);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
        ResponseEntity<?> response = loanController.getLoanByPanId("ABCD");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockLoan, response.getBody());
    }
 
    @Test
    public void testGetLoanDetailsById_NotFound() throws Exception  {
        try {
			when(loanService.getLoanDetailsByPanId("abcd")).thenThrow(new RuntimeException("Loan not found"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
        ResponseEntity<?> response = loanController.getLoanByPanId("abcd");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(new ErrorResponse("Loan details are not available for this ID: 1", 404), response.getBody());
    }
 
    @Test
    public void testApplyLoan_Success() {
        Loan loanToApply = new Loan(); // Create loan object with required details
        Loan savedLoan = new Loan(); // Create mock saved loan object
        savedLoan.setLoanId(1L);
        when(loanService.applyLoan(loanToApply)).thenReturn(savedLoan);
 
        ResponseEntity<?> response = loanController.applyLoan(loanToApply);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(savedLoan, response.getBody());
    }
 
    @Test
    public void testApplyLoan_Forbidden() throws Exception {
        Loan loanToApply = new Loan(); // Create loan object with required details
        when(loanService.applyLoan(loanToApply)).thenThrow(new RuntimeException("This user cannot apply for loan"));
 
        ResponseEntity<?> response = loanController.applyLoan(loanToApply);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(new ErrorResponse("This user cannot apply for loan...", 403), response.getBody());
    }
 
    @Test
    public void testMakePartPayment_Success() {
        Loan updatedLoan = new Loan(); // Create mock updated loan object
        try {
			when(loanService.makePartPayment(1L, 100.0)).thenReturn(updatedLoan);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
        ResponseEntity<?> response = loanController.makePartPayment(1L, 100.0);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedLoan, response.getBody());
    }
 
    @Test
    public void testUpdateLoan_Success() throws Exception{
        Loan updatedLoan = new Loan(); // Create mock updated loan object
        when(loanService.updateLoan(1L, 5000.0, 12)).thenReturn(updatedLoan);
 
        ResponseEntity<?> response = loanController.updateLoan(1L, 5000.0, 12);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedLoan, response.getBody());
    }
 
  
}