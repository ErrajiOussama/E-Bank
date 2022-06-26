package com.nassih.bankapi.services;

import com.nassih.bankapi.dtos.*;
import com.nassih.bankapi.entities.BankAccount;
import com.nassih.bankapi.entities.CurrentAccount;
import com.nassih.bankapi.entities.Customer;
import com.nassih.bankapi.entities.SavingAccount;
import com.nassih.bankapi.exceptions.BalanceNotSufficientException;
import com.nassih.bankapi.exceptions.BankAccountNotFoundException;
import com.nassih.bankapi.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBanalce, Long customerId, double overDraft) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBanalce, Long customerId, double interestRate) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;

    List<CustomerDTO> searchCustomers(String kw);
}
