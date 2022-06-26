package com.nassih.bankapi;

import com.nassih.bankapi.dtos.BankAccountDTO;
import com.nassih.bankapi.dtos.CurrentBankAccountDTO;
import com.nassih.bankapi.dtos.CustomerDTO;
import com.nassih.bankapi.dtos.SavingBankAccountDTO;
import com.nassih.bankapi.entities.*;
import com.nassih.bankapi.enums.AccountStatus;
import com.nassih.bankapi.enums.OperationType;
import com.nassih.bankapi.exceptions.BalanceNotSufficientException;
import com.nassih.bankapi.exceptions.BankAccountNotFoundException;
import com.nassih.bankapi.exceptions.CustomerNotFoundException;
import com.nassih.bankapi.repositories.AccountOperationRepository;
import com.nassih.bankapi.repositories.BankAccountRepository;
import com.nassih.bankapi.repositories.CustomerRepository;
import com.nassih.bankapi.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class BankApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankApiApplication.class, args);
    }

    //@Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
     return args -> {
         Stream.of("yassine", "hassan", "simo", "hiba", "hajar").forEach(name -> {
             CustomerDTO customerDTO = new CustomerDTO();
             customerDTO.setName(name);
             customerDTO.setEmail(name+"@gmail.com");
             bankAccountService.saveCustomer(customerDTO);
         });
         bankAccountService.listCustomers().forEach(customer -> {
             try {
                 bankAccountService.saveCurrentBankAccount(Math.random()*1000, customer.getId(), 9000);
                 bankAccountService.saveSavingBankAccount(Math.random()*1000,customer.getId(),5.5);

             } catch (CustomerNotFoundException e) {
                 e.printStackTrace();
             }
         });
         List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
         for (BankAccountDTO bankAccount: bankAccounts) {
             for (int i = 0; i < 15; i++) {
                 String accountId;
                 if(bankAccount instanceof SavingBankAccountDTO){
                     accountId = ((SavingBankAccountDTO) bankAccount).getId();
                 }
                 else{
                     accountId = ((CurrentBankAccountDTO) bankAccount).getId();
                 }
                 bankAccountService.credit(accountId,500 * Math.random()*100000,"Credit");
                 bankAccountService.debit(accountId,100*Math.random()*50,"Debit");
             }
         }

     };
    }

    //@Bean
    CommandLineRunner star(CustomerRepository customerRepository,
                           BankAccountRepository bankAccountRepository,
                           AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("Hassan", "Oussama", "Yassine", "Youssef", "Anas", "Marwa", "Hajar", "Marwan").forEach(name ->{

                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });

            customerRepository.findAll().forEach(cust -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });

            bankAccountRepository.findAll().forEach(acc -> {
                for (int i = 0; i < 25; i++) {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()* 1000);
                    accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }
            });
        };
    }
}
