/*
 * package com.hackathone.LMS.Service;
 * 
 * import com.hackathone.LMS.Entity.Loan; import com.hackathone.LMS.Entity.User;
 * import com.hackathone.LMS.Repository.LoanRepository; import
 * com.hackathone.LMS.Repository.UserRepository; import
 * org.junit.jupiter.api.BeforeEach; import org.junit.jupiter.api.Test; import
 * org.mockito.InjectMocks; import org.mockito.Mock; import
 * org.mockito.MockitoAnnotations;
 * 
 * import java.util.Optional;
 * 
 * import static org.junit.jupiter.api.Assertions.*; import static
 * org.mockito.Mockito.*;
 * 
 * public class LoanServiceTest {
 * 
 * @InjectMocks private LoanService loanService;
 * 
 * @Mock private LoanRepository loanRepository;
 * 
 * @Mock private UserRepository userRepository;
 * 
 * private Loan loan; private User user;
 * 
 * @BeforeEach void setUp() { MockitoAnnotations.openMocks(this); loan = new
 * Loan(); user = new User(); }
 * 
 * 
 * 
 * 
 * // @Test void testGetLoanDetailsById_ReturnsLoan() throws Exception {
 * loan.setLoanId(1L);
 * when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
 * 
 * // Loan result = loanService.getLoanDetailsByPanId("abcd");
 * assertEquals(loan, result); }
 * 
 * // @Test void testGetLoanDetailsById_WhenLoanNotFound_ThrowsException() {
 * when(loanRepository.findById(1L)).thenReturn(Optional.empty());
 * 
 * Exception exception = assertThrows(Exception.class, () -> { //
 * loanService.getLoanDetailsByPanId("abcd"); });
 * 
 * assertEquals("Loan not found", exception.getMessage()); }
 * 
 * 
 * 
 * 
 * @Test void testFindByPanId_ReturnsUser() { user.setPanId("ABCDE1234F");
 * when(userRepository.findByPanId("ABCDE1234F")).thenReturn(user);
 * 
 * User result = loanService.findByPanId("ABCDE1234F"); assertEquals(user,
 * result); }
 * 
 * @Test public void testMakePartPayment_PartialPayment() throws Exception {
 * Long loanId = 1L; Double partPaymentAmount = 500.0; Loan loan = new Loan();
 * loan.setLoanAmount(1000.0); User user = new User(); loan.setUser(user);
 * 
 * when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
 * when(loanRepository.save(any(Loan.class))).thenReturn(loan);
 * 
 * Loan updatedLoan = loanService.makePartPayment(loanId, partPaymentAmount);
 * 
 * assertNotNull(updatedLoan); assertEquals(500.0, updatedLoan.getLoanAmount());
 * verify(loanRepository, times(1)).save(loan); verify(userRepository,
 * never()).save(any(User.class)); verify(loanRepository,
 * never()).delete(any(Loan.class)); }
 * 
 * 
 * @Test public void testMakePartPayment_LoanNotFound() { Long loanId = 1L;
 * Double partPaymentAmount = 500.0;
 * 
 * when(loanRepository.findById(loanId)).thenReturn(Optional.empty());
 * 
 * Exception exception = assertThrows(Exception.class, () -> {
 * loanService.makePartPayment(loanId, partPaymentAmount); });
 * 
 * assertNotNull(exception); } }
 */