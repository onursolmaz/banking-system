package com.akbank.bankingsystem.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum AccountType {
    TRY,
    USD,
    EUR,
    GOLD,
    @JsonProperty
    INVALID;


    @JsonCreator
    public static AccountType valueOfOrInvalid(String name) {
        try {
            return AccountType.valueOf(name);
        } catch (IllegalArgumentException e) {
            return INVALID;
        }
    }
}
