package com.akbank.bankingsystem.service;

import com.akbank.bankingsystem.model.Account;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.sql.Timestamp;
import java.util.Objects;

@Repository
public class AccountRepository {

    @SneakyThrows
    public Account create(Account account){
        ObjectOutputStream outputStream=new ObjectOutputStream(new FileOutputStream(new File("accounts/"+account.getNumber())));
        account.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
        outputStream.writeObject(account);
        outputStream.close();
        return account;
    }

    @SneakyThrows
    public Account getAccount(Long id){
        ObjectInputStream inputStream=new ObjectInputStream(new FileInputStream(new File("accounts/"+id)));
        Account account =(Account)inputStream.readObject();
        return  account;
    }

    public Account update(Account account){
        Account accountInFile = getAccount(account.getNumber());

        if(Objects.nonNull(accountInFile)){
            account.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
            return create(account);
        }else{
            //throw account not fount exception
            return null;
        }
    }

}
