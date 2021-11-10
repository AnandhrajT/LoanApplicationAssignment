import { Injectable } from '@angular/core';
import { CustomerModel } from '../models/CustomerModel';
import { LoanServiceService } from './loan-service.service';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {
  login(value: any) {
    throw new Error('Method not implemented.');
  }

  customermodel!: CustomerModel;

  constructor(private loanService: LoanServiceService) {

  }
  async authenticate(username: string, password: string) {
    this.customermodel = await this.loanService.verifyCustomer(username, password).toPromise();

    if (this.customermodel.customerId != null) {
      sessionStorage.setItem('authenticaterUser', username);
      return true;
    }
    return false;
  }
  getCustomerId(): string {
    return this.customermodel.customerId;
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem('authenticaterUser')
    return !(user === null)
  }
  logout() {
    sessionStorage.removeItem('authenticaterUser')
  }
}
