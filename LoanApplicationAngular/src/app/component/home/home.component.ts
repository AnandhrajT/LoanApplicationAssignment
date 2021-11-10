import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CustomerModel } from 'src/app/models/CustomerModel';
import { AuthServiceService } from 'src/app/Services/auth-service.service';
import { LoanServiceService } from 'src/app/Services/loan-service.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  model: CustomerModel | any;
  customerForm: FormGroup | any;
  customerId: string;
  spin: boolean;
  constructor(private loanService: LoanServiceService, private formBuilder: FormBuilder, private authService: AuthServiceService) {
    this.customerId = this.authService.getCustomerId();
    this.spin = true
    this.loanService.getCustomerDetails(this.customerId).subscribe((data: any) => {
      this.spin = false
      this.model = data;
      this.customerForm = this.formBuilder.group({
        customerId: [this.model.customerId,],
        name: [this.model.name],
        accno: [this.model.accno],
        phone: [this.model.phone],
        email: [this.model.email]
      });
    });
  }
  ngOnInit() {
    this.customerForm = this.formBuilder.group({
      customerId: [], name: [], accno: [], phone: [], email: []
    });
    this.customerForm.disable();
    console.log("welcome", this.model)
  }


}
