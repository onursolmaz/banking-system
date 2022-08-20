package com.akbank.bankingsystem.model.responses;

import lombok.Data;

import java.util.List;

@Data
public class GoldPriceResponse {

    private boolean success;
    private List<GoldPriceReponseResult> result;
}
