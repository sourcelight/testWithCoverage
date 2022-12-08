package com.stackfortech.testWithCoverage.service;

import com.stackfortech.testWithCoverage.model.Account;
import com.stackfortech.testWithCoverage.model.Transfer;
import org.springframework.http.ResponseEntity;

public interface AccountService
{

    public ResponseEntity<?> findOne(Long accountNumber);
    public ResponseEntity<?> depositToAccount(Account account);
    public ResponseEntity<?> withdrawFromAccount(Account account);
    public ResponseEntity<?> createAccount(Account account);
    public ResponseEntity<?> transfer(Transfer transfer);


}
