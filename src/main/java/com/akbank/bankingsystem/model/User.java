package com.akbank.bankingsystem.model;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class User {

    private Long id;
    private String username;
    private String email;
    private String password;

    @Builder.Default
    private boolean enabled=true;

   private List<String> authorities;


}
