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
import { MessageService } from 'primeng/api';
import { RouterModule } from '@angular/router';
import { PasswordModule } from 'primeng/password';
import { RippleModule } from 'primeng/ripple';
import { Toast } from 'primeng/toast';

@Component({
  selector: 'app-login',
  templateUrl: `./login.component.html`,
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

        if (err.error.description) {
          this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
        } else {
          this.messageService.add({ severity: 'warn', summary: "Verbindungsfehler!", detail: "Es gab einen Fehler bei der API-Anfrage." });
        }
      }
    });

  }
}
