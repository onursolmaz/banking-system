package com.akbank.bankingsystem.model.responses;

import lombok.Data;

import java.util.List;

@Data
public class ExchangeResponseResult {

    private String base;
    private String lastupdate;
    private String name;
    private Double selling;
    private List<exchangeData> data;
}
