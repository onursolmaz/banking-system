package com.akbank.bankingsystem.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@AllArgsConstructor
@Alias("Bank")
public class Bank {


    private Long id;
    private String name;

}
