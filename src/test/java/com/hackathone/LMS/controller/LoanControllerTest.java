/*
 * package com.hackathone.LMS.controller;
 * 
 * import static org.junit.jupiter.api.Assertions.assertEquals; import static
 * org.mockito.Mockito.when;
 * 
 * import org.junit.jupiter.api.BeforeEach; import org.junit.jupiter.api.Test;
 * import org.mockito.InjectMocks; import org.mockito.Mock; import
 * org.mockito.MockitoAnnotations; import org.springframework.http.HttpStatus;
 * import org.springframework.http.ResponseEntity;
 * 
 * import com.hackathone.LMS.Entity.Loan; import
 * com.hackathone.LMS.ErrorMessages.ErrorResponse; import
 * com.hackathone.LMS.Service.LoanService;
 * 
 * public class LoanControllerTest {
 * 
 * @InjectMocks private LoanController loanController;
 * 
 * @Mock private LoanService loanService;
 * 
 * @BeforeEach public void setUp() { MockitoAnnotations.openMocks(this); }
 * 
 * @Test public void testGetLoanDetailsById_Success() throws Exception { Loan
 * mockLoan = new Loan(); // Create a mock loan object mockLoan.setLoanId(1L);
 * try { when(loanService.getLoanDetailsByPanId("ABCD")).thenReturn(mockLoan); }
 * catch (Exception e) { // TODO Auto-generated catch block e.printStackTrace();
 * }
 * 
 * ResponseEntity<?> response = loanController.getLoanByPanId("ABCD");
 * assertEquals(HttpStatus.OK, response.getStatusCode()); assertEquals(mockLoan,
 * response.getBody()); }
 * 
 * 
 * 
 * @Test public void testApplyLoan_Success() { Loan loanToApply = new Loan(); //
 * Create loan object with required details Loan savedLoan = new Loan(); //
 * Create mock saved loan object savedLoan.setLoanId(1L);
 * when(loanService.applyLoan(loanToApply)).thenReturn(savedLoan);
 * 
 * ResponseEntity<?> response = loanController.applyLoan(loanToApply);
 * assertEquals(HttpStatus.OK, response.getStatusCode());
 * assertEquals(savedLoan, response.getBody()); }
 * 
 * @Test public void testMakePartPayment_Success() { Loan updatedLoan = new
 * Loan(); // Create mock updated loan object try {
 * when(loanService.makePartPayment(1L, 100.0)).thenReturn(updatedLoan); } catch
 * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
 * 
 * ResponseEntity<?> response = loanController.makePartPayment(1L, 100.0);
 * assertEquals(HttpStatus.OK, response.getStatusCode());
 * assertEquals(updatedLoan, response.getBody()); }
 * 
 * 
 * 
 * }
 */
package com.hackathone.LMS.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathone.LMS.Entity.Loan;
import com.hackathone.LMS.Entity.User;
import com.hackathone.LMS.Service.LoanService;

@WebMvcTest(LoanController.class)
public class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateOtpByPan() throws Exception {
        mockMvc.perform(post("/api/loans/generate")
                        .param("panId", "ABCDE1234F"))
                .andExpect(status().isOk());
    }

    @Test
    void testViewLoanDetails() throws Exception {
        when(loanService.validateOtp(any(String.class), any(String.class))).thenReturn(true);
        when(loanService.getLoanDetailsByPanId(any(String.class))).thenReturn(new Loan());

        mockMvc.perform(get("/api/loans/view")
                        .param("mail", "test@mail.com")
                        .param("otp", "123456")
                        .param("panId", "ABCDE1234F"))
                .andExpect(status().isOk());
    }

    @Test
    void testApplyLoan() throws Exception {
        Loan loan = new Loan();
        when(loanService.applyLoan(any(Loan.class))).thenReturn(loan);

        mockMvc.perform(post("/api/loans/apply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loan)))
                .andExpect(status().isOk());
    }

    @Test
    void testMakePartPayment() throws Exception {
        Loan loan = new Loan();
        when(loanService.makePartPayment(any(Long.class), any(Double.class))).thenReturn(loan);

        mockMvc.perform(put("/api/loans/partpayment/1")
                        .param("partPayment", "1000"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateLoan() throws Exception {
        Loan loan = new Loan();
        loan.setLoanStatus("Topup Approved"); // Set a default loan status

        when(loanService.updateLoan(any(Long.class), any(Double.class), any(Integer.class))).thenReturn(loan);

        mockMvc.perform(put("/api/loans/updateLoan/1")
                        .param("loanAmount", "50000")
                        .param("tenureInMOnths", "12"))
                .andExpect(status().isOk());
    }


    @Test
    void testFindUserByPanId() throws Exception {
        User user = new User();
        when(loanService.findByPanId(any(String.class))).thenReturn(user);

        mockMvc.perform(get("/api/loans/PAN/ABCDE1234F"))
                .andExpect(status().isOk());
    }
}
