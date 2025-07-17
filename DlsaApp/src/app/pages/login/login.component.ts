/**
 * LoginComponent – Benutzeranmeldungskomponente
 *
 * Funktionen:
 * - Anzeige und Validierung eines Login-Formulars
 * - Authentifizierung über AuthService
 * - Weiterleitung nach erfolgreichem Login
 * - Fehleranzeige bei fehlgeschlagener Anmeldung via PrimeNG Toast
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

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
    // Benötigte Angular- und PrimeNG-Module
    CommonModule,
    CardModule,
    FormsModule,
    ReactiveFormsModule,
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
    Toast
  ],
  providers: [MessageService]
})
export class LoginComponent {

  // Formulargruppe für Login mit Validierung
  loginForm: FormGroup;

  constructor(
    private authService: AuthService,
    private router: Router,
    private messageService: MessageService
  ) {
    // Initialisierung des Login-Formulars mit Pflichtfeldern
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
  }

  // Methode zur Anmeldung des Benutzers
  login(): void {
    this.authService.login(this.loginForm.value).subscribe({
      next: (response) => {
        // Bei Erfolg: Weiterleitung zum Dashboard
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        // Formular zurücksetzen bei Fehler
        this.loginForm.reset();

        // Fehlerbehandlung und Toast-Nachricht
        if (err.error.description) {
          this.messageService.add({
            severity: 'warn',
            summary: err.error.title,
            detail: err.error.description
          });
        } else {
          this.messageService.add({
            severity: 'warn',
            summary: "Verbindungsfehler!",
            detail: "Es gab einen Fehler bei der API-Anfrage."
          });
        }
      }
    });
  }
}