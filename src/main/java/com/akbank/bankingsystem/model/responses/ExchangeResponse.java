package com.akbank.bankingsystem.model.responses;

import lombok.Data;

@Data
public class ExchangeResponse {

    private boolean success;
    private ExchangeResponseResult result;
}
