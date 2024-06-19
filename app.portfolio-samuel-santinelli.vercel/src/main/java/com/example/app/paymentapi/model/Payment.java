package com.example.app.paymentapi.model;

import lombok.Data;

@Data
public class Payment {
    private String id;
    private Double amount;
    private String description;
}
