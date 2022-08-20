package com.akbank.bankingsystem.service;

import com.akbank.bankingsystem.model.AccountType;
import com.akbank.bankingsystem.model.responses.ExchangeResponse;
import com.akbank.bankingsystem.model.responses.GoldPriceResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Service
public class ExchangeService {

    private RestTemplate restTemplate=new RestTemplate();
    private HttpHeaders headers=new HttpHeaders();
    private HttpEntity<?> requestEntity=new HttpEntity<>(this.headers);


    public Double exchange(Double amount, AccountType senderAccountType, AccountType receiverAccountType){

        if(senderAccountType.equals(AccountType.GOLD) || receiverAccountType.equals(AccountType.GOLD)){
            amount = goldExchange(amount, senderAccountType,receiverAccountType);
            return amount;
        }

        String url="https://api.collectapi.com/economy/exchange?int="+amount+"&to="+receiverAccountType+"&base="+senderAccountType;
        ResponseEntity<ExchangeResponse> response=this.restTemplate.exchange(url, HttpMethod.GET,this.requestEntity, ExchangeResponse.class);
        String calculated = response.getBody().getResult().getData().get(0).getCalculated();
        return Double.valueOf(calculated);
    }

    //this api gives gold prices only in Turkish lira
    //todo refactor
    public Double goldExchange(Double amount, AccountType senderType, AccountType receiverType){
        String url="https://api.collectapi.com/economy/goldPrice";
        ResponseEntity<GoldPriceResponse> response =this.restTemplate.exchange(url, HttpMethod.GET,this.requestEntity, GoldPriceResponse.class);
        GoldPriceResponse responseResult = response.getBody();

        // get gram gold price in Turkish lira
        Double goldPriceTRY = responseResult.getResult().get(0).getSelling();

        if(receiverType.equals(AccountType.GOLD)){
            Double exchanged = exchange(amount, senderType, AccountType.TRY);
            return exchanged/goldPriceTRY;
        }

        Double goldPriceByAccountType;

        if(!receiverType.equals(AccountType.TRY)){
            goldPriceByAccountType = exchange(goldPriceTRY, AccountType.TRY, receiverType);
        }

        goldPriceByAccountType=goldPriceTRY;

        if(senderType.equals(AccountType.GOLD)){
            return amount*goldPriceByAccountType;
        }

        return amount/goldPriceByAccountType;

    }



    @PostConstruct
    private void setHeaders() {
        this.headers.add("content-type","application/json");
        this.headers.add("authorization","apikey 5nuGDLGPozvO3cs6jwtF95:6n214egOSOXbmXnTwSAlnX");
        this.headers.add("user-agent", "Application");
    }


}
