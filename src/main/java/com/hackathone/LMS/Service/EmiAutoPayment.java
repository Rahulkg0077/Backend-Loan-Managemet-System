package com.hackathone.LMS.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmiAutoPayment {

    @Autowired
    LoanService loanService;

    @Scheduled(fixedRate = 5000)
    public void EmiSchedule(){
        loanService.emiPayment();
    }
}