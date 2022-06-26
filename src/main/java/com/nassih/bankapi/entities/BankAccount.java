package com.nassih.bankapi.entities;

import com.nassih.bankapi.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
// Joined strategy
//@Inheritance(strategy = InheritanceType.JOINED)
//  Table per class strategy
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//  Single table strategy
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", length = 4)
@Data @NoArgsConstructor @AllArgsConstructor
public abstract class BankAccount {
    //in case of table_per_class, this class will not be used
    //to store any data, so we change it to "abstract", so it does not
    //transform it to a table in our database
    @Id
    private String id;
    private double balance;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY)
    //In one to many relations:
    //default fetch mode is Lazy, when it loads the bankAccount, it does not
    //load the list of account operations related to it.
    //but when it's set to 'EAGER', when it loads the bankAccount,
    //it loads the list of account operations related to it.
    private List<AccountOperation> accountOperations;

}
