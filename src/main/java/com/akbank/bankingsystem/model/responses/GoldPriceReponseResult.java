package com.akbank.bankingsystem.model.responses;

import lombok.Data;

@Data
public class GoldPriceReponseResult {

    private String name;
    private Double buying;
    private String buyingstr;
    private Double selling;
    private String sellingstr;
    private String time;
    private String date;
    private String datetime;
    private String rate;
}


// {
//         "name": "Gram Altın",
//         "buying": 952.572,
//         "buyingstr": "952.572",
//         "selling": 952.695,
//         "sellingstr": "952.695",
//         "time": "17:23:31",
//         "date": "2022-07-15",
//         "datetime": "2022-07-15T14:23:31.000Z",
//         "rate": -0.58
//         },