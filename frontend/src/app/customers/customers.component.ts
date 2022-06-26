import { Component, OnInit } from '@angular/core';
import {catchError, map, Observable, throwError} from 'rxjs';
import { Customer } from '../model/customer.model';
import { CustomerService } from '../services/customer.service';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";


@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css']
})
export class CustomersComponent implements OnInit {

  customers! : Observable<Array<Customer>>;
  errorMessage! : string; //string | undefined;  :string = "";
  searchFormGroup : FormGroup | undefined;


  constructor(private customerService: CustomerService, private fb: FormBuilder, private router: Router) { }

  ngOnInit(): void {

    this.searchFormGroup = this.fb.group({
      kw: this.fb.control("")
    });

    this.handleSearchCustomers();
    //remplace le code au de sous, car kw par default = ""
    // this.customers = this.customerService.getCustomers().pipe(
    //   catchError(err => {
    //     this.errorMessage = err.message;
    //     return throwError(err);
    //   })
    // );
    /* this.customerService.getCustomers().subscribe({
      next: (data) => {
        this.customers = data;
      },
      error: (err) => {
        this.errorMessage = err.message;
      }
    }) */

  }

  handleSearchCustomers() {
    let kw = this.searchFormGroup?.value.kw;
    this.customers = this.customerService.searchCustomers(kw).pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(err);
      })
    );
  }

  handleDeleteCustomer(c: Customer) {
    let conf = confirm("Are you sure?")
    if(!conf) return ;

    this.customerService.deleteCustomer(c.id).subscribe({
      next: resp => {
        this.customers = this.customers.pipe(map(data => {
          let index = data.indexOf(c);
          data.slice(index, 1);
          return data;
        }))
      },
      error : err => {
        console.log(err)
      }
    });
  }

  handleCustomerAccounts(customer: Customer) {
    this.router.navigateByUrl("/customer-accounts/"+customer.id, {state : customer});
  }
}
