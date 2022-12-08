package com.stackfortech.testWithCoverage.controller;

import com.stackfortech.testWithCoverage.model.Account;
import com.stackfortech.testWithCoverage.model.Transfer;
import com.stackfortech.testWithCoverage.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/account/{id}")
    public ResponseEntity<?> getAccountDetail(@PathVariable Long id)
    {
        return accountService.findOne(id);

    }
    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody Account account)
    {
        return accountService.createAccount(account);
    }

    @PutMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody Account account)
    {
        return accountService.depositToAccount(account);
    }

    @PutMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody Account account)
    {
        return accountService.withdrawFromAccount(account);
    }
    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody Transfer transfer)
    {
        return accountService.transfer(transfer);
    }

}
