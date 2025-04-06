import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { StyleClassModule } from 'primeng/styleclass';
import { LayoutService } from '../service/layout.service';
import { AuthService } from '../../services/auth.service';

@Component({
    selector: 'app-topbar',
    standalone: true,
    imports: [RouterModule, CommonModule, StyleClassModule],
    template: ` <div class="layout-topbar">
        <div class="layout-topbar-logo-container">
            <button class="layout-menu-button layout-topbar-action" (click)="layoutService.onMenuToggle()">
                <i class="pi pi-bars"></i>
            </button>
            <a class="layout-topbar-logo" routerLink="/">
                <span>DLS Verwaltung</span>
            </a>
        </div>

        <div class="layout-topbar-actions">
            
        </div>

        <div class="layout-topbar-actions">
            <div class="layout-topbar-menu">
                <div class="layout-topbar-menu-content">
                    <span class="layout-topbar-span">Username: {{userName}}</span>
                    <span class="layout-topbar-span">Rolle: {{userRole}}</span>
                    <button type="button" (click)="logout()" class="layout-topbar-action">
                        <i class="pi pi-sign-out"></i>
                    </button>
                </div>
            </div>
        </div>
    </div>`
})
export class AppTopbar {
    userRole: string | null;
    userName: string | null;

    constructor(public layoutService: LayoutService, private authService:AuthService) {
        this.userRole = this.authService.getUserRole();
        this.userName = this.authService.getUserName();
    }

    logout() {
        this.authService.logout();
      }
}
