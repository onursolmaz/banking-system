package com.akbank.bankingsystem.model.responses;

import lombok.Data;

@Data
public class exchangeData {

    private String code;
    private String name;
    private String rate;
    private String calculatedstr;
    private String calculated;

}
