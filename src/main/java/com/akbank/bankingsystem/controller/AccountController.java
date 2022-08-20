package com.akbank.bankingsystem.controller;

import com.akbank.bankingsystem.model.requests.AccountTransactionRequest;
import com.akbank.bankingsystem.model.Account;
import com.akbank.bankingsystem.model.requests.MoneyTransferRequest;
import com.akbank.bankingsystem.service.AccountService;
import com.akbank.bankingsystem.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/customer")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private  LogService logService;

    @GetMapping("/{number}")
    public ResponseEntity<?> getAccount(@PathVariable Long number){
        Account account = accountService.getAccount(Long.valueOf(number));
        return ResponseEntity.ok().lastModified(account.getLastModifiedDate().getTime()).body(account);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Account account){
        ResponseEntity<?> responseEntity = accountService.create(account);
        return responseEntity;
    }

    @PatchMapping("/{number}")
    public Account deposit(@PathVariable Long number,@RequestBody AccountTransactionRequest request){
        return accountService.deposit(Long.valueOf(number),request.getAmount());
    }

    @PatchMapping("/transfer/{number}")
    public ResponseEntity<?> moneyTransfer(@PathVariable Long number, @RequestBody MoneyTransferRequest request){
        ResponseEntity<?> responseEntity = accountService.moneyTransfer(number, request.getReceiverAccountNumber(), request.getAmount());
        return responseEntity;
    }

    @CrossOrigin
    @GetMapping("log/{number}")
    public List<String> getLogs(@PathVariable String number){
        return logService.getLogsByAccountNumber(number);
    }

    @PatchMapping("/withdraw/{number}")
    public Account withdraw(@PathVariable Long number,@RequestBody AccountTransactionRequest request){
        return accountService.withdraw(Long.valueOf(number),request.getAmount());
    }

}
