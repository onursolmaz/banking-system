package com.akbank.bankingsystem.service;

import com.akbank.bankingsystem.model.Account;
import com.akbank.bankingsystem.model.AccountType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountService {

    //Constructor injection with lombok
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final AccountRepository accountRepository;
    private final ExchangeService exchangeService;

    // this method can refactor with creating custom exception and exception handling
    public ResponseEntity<?> create(Account account){
        Map response = new HashMap<>();

        if(!(account.getType().equals(AccountType.GOLD) || account.getType().equals(AccountType.TRY)
                || account.getType().equals(AccountType.USD)
                ||account.getType().equals(AccountType.EUR)) ) {

            response.put("message","Invalid Account Type");
            return new ResponseEntity<Map>(response,HttpStatus.BAD_REQUEST);
        }

        accountRepository.create(account);
        response.put("message","Account Created");
        response.put("accountNumber",account.getNumber());

        kafkaTemplate.send("logs",account.getNumber()+" account created time:"+account.getLastModifiedDate());

        return new ResponseEntity<Map>(response,HttpStatus.CREATED);
    }

    public Account getAccount(Long number){
        return accountRepository.getAccount(number);
    }

    public Account update(Account account){
      return accountRepository.update(account);
    }

    public Account deposit(Long number,Double amount){
        Account account = getAccount(number);
        account.setBalance(account.getBalance()+amount);
        String logMsg=account.getNumber()+" deposit amount:"+amount;
        kafkaTemplate.send("logs",logMsg);
        return update(account);
    }

    public Account withdraw(Long number,Double amount){
        Account account = getAccount(number);

        if(account.getBalance()>amount){
            account.setBalance(account.getBalance()-amount);
            String logMsg=account.getNumber()+" withdraw amount:"+amount;
            kafkaTemplate.send("logs",logMsg);
        }else{
            //throw exception
        }
        return update(account);
    }

    // this method can refactor with creating custom exception and exception handling
    public ResponseEntity<?> moneyTransfer(Long senderNumber, Long receiverNumber, Double amount ){
        Account senderAccount = getAccount(senderNumber);
        Account receiverAccount = getAccount(receiverNumber);
        Map response=new HashMap();

        if(senderAccount.getBalance()>=amount){

            senderAccount.setBalance(senderAccount.getBalance()-amount);
            //This service exchanges money according to account type
            Double amountByAccountType=amount;
            if(!senderAccount.getType().equals(receiverAccount.getType())){
                amountByAccountType = exchangeService.exchange(amount, senderAccount.getType(), receiverAccount.getType());
            }
            receiverAccount.setBalance(receiverAccount.getBalance()+amountByAccountType);
            update(receiverAccount);
            update(senderAccount);

            //log with kafka
            String logMsg=senderNumber+" transfer amount:"+amount+"["+senderAccount.getType()+"] transferred_account:"+receiverNumber;
            kafkaTemplate.send("logs",logMsg);

            response.put("message","Transferred Successfully");
            return new ResponseEntity<Map>(response, HttpStatus.OK);

        }else{

            response.put("message","Insufficient balance");
            return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
            //throw custom exception
        }
    }

}