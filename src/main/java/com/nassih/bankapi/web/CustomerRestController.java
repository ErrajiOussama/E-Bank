package com.nassih.bankapi.web;

import com.nassih.bankapi.dtos.CustomerDTO;
import com.nassih.bankapi.entities.Customer;
import com.nassih.bankapi.exceptions.CustomerNotFoundException;
import com.nassih.bankapi.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return bankAccountService.listCustomers();
    }

    @GetMapping("/customers/search")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "kw", defaultValue = "") String kw){
        return bankAccountService.searchCustomers("%"+kw+"%");
    }


    @GetMapping("/customer/{id}")
    public CustomerDTO getCustomer(
            @PathVariable(name = "id") Long customerId) throws CustomerNotFoundException { //indique que la valeur de customerId
                            //est elle meme que le id passer on parameter
        return bankAccountService.getCustomer(customerId);
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return bankAccountService.saveCustomer(customerDTO);
    }

    @PutMapping("/customer/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId,
                                      @RequestBody CustomerDTO customerDTO){
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }

    @DeleteMapping("/customer/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId){
        bankAccountService.deleteCustomer(customerId);
    }
}
