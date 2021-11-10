import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthServiceService } from 'src/app/Services/auth-service.service';
import { LoanServiceService } from 'src/app/Services/loan-service.service';

@Component({
  selector: 'app-apply-loan',
  templateUrl: './apply-loan.component.html',
  styleUrls: ['./apply-loan.component.css']
})
export class ApplyLoanComponent implements OnInit {

  @ViewChild('alert', { static: true })
  alert!: ElementRef;
  loanForm: FormGroup | any;
  customerId: string | any;
  loan: string | any;
  submitted = false;
  today = new Date();
  range: number | any;
  date: Date | any;
  payments: number = 0;

  constructor(private formBuilder: FormBuilder, private loanService: LoanServiceService, private router: Router, private authService: AuthServiceService) { }
  ngOnInit() {
    this.customerId = this.authService.getCustomerId();
    this.loanForm = this.formBuilder.group({
      customerId: [{ value: this.customerId, disabled: true }],
      loanAmount: ['', [Validators.required, Validators.max(10000000), Validators.min(10000)]],
      tradeDate: ['', Validators.required],
      startDate: ['', Validators.required],
      loanDuration: ['', Validators.required],
      endDate: [{ value: new Date(), disabled: true }],
      rateOfInterest: [{ value: 10, disabled: true }],
      paymentInterval: ['', [Validators.required]],
      payments: [{ value: 0, disabled: true }],
      paymentTerm: ['', [Validators.required]],
      interestExtimate: [{ value: 0, disabled: true }],
    });
  }

  get f() { return this.loanForm.controls; }


  setEndDate(event: any) {
    var range = (event.target.value) * 12;
    var startDate = this.loanForm.get('startDate').value;
    var date = new Date(startDate);
    if (range != 0) {
      this.loanForm.patchValue({
        endDate: new Date(date.setMonth(date.getMonth() + range)).toISOString().substring(0, 10)
      });
    }

  }

  calculateEmiPayments(event: any) {
    var totalMonths = parseInt(this.loanForm.get('loanDuration').value) * 12;
    var target = event.target;
    if (target.checked) {
      if (target.value == "Monthly") {
        this.payments = totalMonths;
      } else if (target.value == "Half Yearly") {
        this.payments = totalMonths / 6;
      } else if (target.value == "Yearly") {
        this.payments = totalMonths / 12;
      }
      this.loanForm.patchValue({
        payments: this.payments
      });
    }
  }

  calculateInterestExtimate(event: any) {
    var value = event.target.value;
    var principal = this.loanForm.get('loanAmount').value;
    var totalYears = this.loanForm.get('loanDuration').value;
    var rateOfInterest = this.loanForm.get('RateOfInterest').value;
    var payments = this.loanForm.get('payments').value;

    if (value != undefined) {
      var interestAmount: number | any = 0;
      var perPaymentPrincipal = (principal / payments);
      for (var i = 1; i <= payments; i++) {
        interestAmount = interestAmount + (principal * (totalYears / payments) * rateOfInterest) / 100;
        principal = principal - perPaymentPrincipal;
      }
    }

    this.loanForm.patchValue({
      interestExtimate: interestAmount
    });

  }
  keyPressNumbers(event: any) {
    var charCode = (event.which) ? event.which : event.keyCode;
    if ((charCode < 48 || charCode > 57)) {
      event.preventDefault();
      return false;
    } else {
      return true;
    }
  }

  onSubmit() {
    this.submitted = true;
    if (this.loanForm.invalid) {
      return;
    }
    this.loan.customerId = this.loanForm.get('customerId').value;
    this.loan.loanAmount = this.loanForm.get('loanAmount').value;
    this.loan.tradeDate = this.formatDate(this.loanForm.get('tradeDate').value);
    this.loan.startDate = this.formatDate(this.loanForm.get('startDate').value);
    this.loan.loanDuration = this.loanForm.get('loanDuration').value;
    this.loan.endDate = this.formatDate(this.loanForm.get('endDate').value);
    this.loan.rateOfInterest = this.loanForm.get('rateOfInterest').value;
    this.loan.paymentInterval = this.loanForm.get('paymentInterval').value;
    this.loan.payments = this.loanForm.get('payments').value;
    this.loan.paymentTerm = this.loanForm.get('paymentTerm').value;
    this.loan.interestExtimate = this.loanForm.get('interestExtimate').value;
    this.loanService.saveLoan(this.loan).subscribe(data => {
      console.log('Save Loan:' + data);
    });
    this.alert.nativeElement.classList.add('show');
    this.resetForm();
  }
  resetForm() {
    this.loanForm.reset();
    this.submitted = false;
  }
  formatDate(input: string) {
    var datePart = input.match(/\d+/g),
      year = datePart![0],
      month = datePart![1],
      day = datePart![2];

    return day + '-' + month + '-' + year;
  }


  closeAlert() {
    this.alert.nativeElement.classList.remove('show');
  }

}
function endDate(endDate: any, arg1: string, arg2: any): string {
  throw new Error('Function not implemented.');
}

