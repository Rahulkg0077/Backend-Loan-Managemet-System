package com.hackathone.LMS.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackathone.LMS.Entity.Loan;
import com.hackathone.LMS.Entity.User;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long>{

	List<Loan> findByLoanStatus(String status);
	Loan findLoanByUser(User user);

}
