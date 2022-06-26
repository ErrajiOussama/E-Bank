package com.nassih.bankapi.repositories;

import com.nassih.bankapi.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
