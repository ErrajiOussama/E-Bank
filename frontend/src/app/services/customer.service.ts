import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Customer } from '../model/customer.model';
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http:HttpClient) { }

  public getCustomers(): Observable<Array<Customer>>{
    return this.http.get<Array<Customer>>(environment.backendHost + "/customers")
  }

  public searchCustomers(kw: string): Observable<Array<Customer>>{
    return this.http.get<Array<Customer>>(environment.backendHost + "/customers/search?kw="+kw)
  }

  public saveCustomer(customer: Customer): Observable<Customer>{
    return this.http.post<Customer>(environment.backendHost + "/customer", customer)
  }

  public deleteCustomer(id: number){
    return this.http.delete(environment.backendHost + "/customer/"+id)
  }
}
