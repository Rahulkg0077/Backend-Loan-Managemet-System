package com.hackathone.LMS.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hackathone.LMS.Entities.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long>{

}
