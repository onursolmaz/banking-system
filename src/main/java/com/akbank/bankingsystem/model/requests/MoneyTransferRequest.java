package com.akbank.bankingsystem.model.requests;

import lombok.Data;

@Data
public class MoneyTransferRequest {

    private Long receiverAccountNumber;
    private Double amount;

}

