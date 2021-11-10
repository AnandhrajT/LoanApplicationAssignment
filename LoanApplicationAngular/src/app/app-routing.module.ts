import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ApplyLoanComponent } from './component/apply-loan/apply-loan.component';
import { HomeComponent } from './component/home/home.component';
import { LoginComponent } from './component/login/login.component';
import { RegisterComponent } from './component/register/register.component';
import { TotalLoansComponent } from './component/total-loans/total-loans.component';
import { LogoutComponent } from './component/logout/logout.component';
import { EmiPaymentsComponent } from './emi-payments/emi-payments.component';
import { RouteGuardService } from './Services/route-guard.service';


const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'Home', component: HomeComponent,canActivate:[RouteGuardService]},
  {path: 'Applyloan', component: ApplyLoanComponent,canActivate:[RouteGuardService]},
  {path: 'totalloans', component: TotalLoansComponent,canActivate:[RouteGuardService]},
  {path: 'logout', component: LogoutComponent,canActivate:[RouteGuardService]},
  {path:'emipayments', component: EmiPaymentsComponent,canActivate:[RouteGuardService]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
