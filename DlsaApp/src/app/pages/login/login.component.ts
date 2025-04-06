import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, FormsModule, NgForm, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { InputGroupModule } from 'primeng/inputgroup';
import { FluidModule } from 'primeng/fluid';
import { InputIconModule } from 'primeng/inputicon';
import { FloatLabelModule } from 'primeng/floatlabel';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { TextareaModule } from 'primeng/textarea';
import { MessageService} from 'primeng/api';
import { RouterModule } from '@angular/router';
import { PasswordModule } from 'primeng/password';
import { RippleModule } from 'primeng/ripple';
import { Toast } from 'primeng/toast';

@Component({
  selector: 'app-login',
  template: `
<div class="flex items-center justify-center min-h-screen bg-gray-100">


  <!--<div class="bg-white shadow-lg rounded-2xl p-6 w-96">
    <h2 class="text-xl font-semibold mb-4">Centered Card</h2>
    <p class="text-gray-600">This card is centered vertically and horizontally.</p>
  </div>-->

  <p-card [style]="{ width: '25rem', overflow: 'hidden' }">
    <!--<ng-template #header>
        <img alt="Card" class="w-full" src="https://primefaces.org/cdn/primeng/images/card-ng.jpg" />
    </ng-template>-->
    <ng-template #title> LOGIN </ng-template>


    <form [formGroup]="loginForm" (ngSubmit)="login()" class="grid grid-cols-1 md:grid-cols-1 gap-4">

      <p-toast />

      <p-floatlabel variant="in">
            <input pInputText id="over_label_user" formControlName="username" name="username" [fluid]="true" />
            <label for="over_label_user">Username</label>
      </p-floatlabel>

      <p-floatlabel variant="in">
        <p-password formControlName="password" name="password" [feedback]="false" [fluid]="true" [toggleMask]="true" inputId="over_label_pw"/>
        <label for="over_label_pw">Passwort</label>
      </p-floatlabel>

      <div class="flex mt-1">
            <p-button label="Login" class="w-full" styleClass="w-full" type="submit" [disabled]="!loginForm.valid" />
      </div>

    </form>
</p-card>



</div>
 
  `,
  standalone: true,
  imports: [
    CommonModule, 
    CardModule,
    FormsModule,
    ButtonModule,
    InputGroupModule,
    FluidModule,
    InputIconModule,
    FloatLabelModule,
    InputGroupAddonModule,
    TextareaModule,
    InputTextModule, 
    PasswordModule, 
    RouterModule, 
    RippleModule,
    Toast,
    ReactiveFormsModule],
    providers: [MessageService]
})

export class LoginComponent {

  loginForm: FormGroup;

  constructor(
    private authService: AuthService, 
    private router: Router,
    private messageService: MessageService) { 
      this.loginForm = new FormGroup({
        username: new FormControl('', Validators.required),
        password: new FormControl('', Validators.required),
      })
    }

  login(): void {

    this.authService.login(this.loginForm.value).subscribe({
      next: (response) => {
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        this.loginForm.reset();
        this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
      }
    });

  }
}
