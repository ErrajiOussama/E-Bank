package com.nassih.bankapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.List;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @OneToMany(mappedBy = "customer")
    //@JsonProprety(access = JsonProprety.Access.WRITE_ONLY)
    //dans le cas des rest api, on a ici une relation bi-directionel
    //dans on aura un json avec un boocle infinie, on ajoutons cette annotation
    //on va l'ignorer
    private List<BankAccount> bankAccounts;
}
