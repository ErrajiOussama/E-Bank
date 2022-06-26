package com.nassih.bankapi.dtos;


import lombok.Data;

@Data
public class TransfertRequestDTO {
    private String accountSource;
    private String accountDestination;
    private double amount;
    private String description;
}
