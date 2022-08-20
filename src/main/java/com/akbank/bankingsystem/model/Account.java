package com.akbank.bankingsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.concurrent.ThreadLocalRandom;

@Data
@Alias("Account")
public class Account implements Serializable {

    //Genarate random account number 10 digit.
    private Long number= ThreadLocalRandom.current().nextLong(1000000000,9999999999L);
    private String name;
    private String surname;
    private String email;
    private String tc;
    private AccountType type;
    private Double balance=0d;
    @JsonIgnore
    private Timestamp lastModifiedDate=new Timestamp(System.currentTimeMillis());
}