package com.nassih.bankapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CA")   //in case of SINGLE_TABLE
@Data @AllArgsConstructor @NoArgsConstructor
public class CurrentAccount extends  BankAccount {
    private double overDraft;
}
