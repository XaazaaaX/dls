import { Component, ViewChild } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { StyleClassModule } from 'primeng/styleclass';
import { LayoutService } from '../service/layout.service';
import { AuthService } from '../../services/auth.service';
import { Popover, PopoverModule } from 'primeng/popover';
import { ButtonModule } from 'primeng/button';


@Component({
    selector: 'app-topbar',
    standalone: true,
    imports: [RouterModule, CommonModule, StyleClassModule, PopoverModule, ButtonModule],
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
                    <button type="button" (click)="toggle($event)" class="layout-topbar-action">
                        <i class="pi pi-user"></i>
                    </button>
                    <button type="button" (click)="logout()" class="layout-topbar-action">
                        <i class="pi pi-sign-out"></i>
                    </button>
                </div>
            </div>


            <!--<p-menu #menu [popup]="true" [model]="items"></p-menu>-->
            
        
        </div>
    </div>
    
    
    
    <p-popover #op>
  <div class="flex flex-col items-center p-4 min-w-[150px] text-md text-gray-800 dark:text-gray-200">
    <div class="flex w-full justify-between mb-2">
      <span class="font-semibold mr-4">Benutzername:</span>
      <span>{{ userName }}</span>
    </div>
    <div class="flex w-full justify-between">
      <span class="font-semibold mr-4">Rolle:</span>
      <span>{{ userRole }}</span>
    </div>
  </div>
</p-popover>`
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



    @ViewChild('op') op!: Popover;

    selectedMember = null;

    members = [
        { name: 'Amy Elsner', image: 'amyelsner.png', email: 'amy@email.com', role: 'Owner' },
        { name: 'Bernardo Dominic', image: 'bernardodominic.png', email: 'bernardo@email.com', role: 'Editor' },
        { name: 'Ioni Bowcher', image: 'ionibowcher.png', email: 'ioni@email.com', role: 'Viewer' },
    ];

    toggle(event: any) {
        this.op.toggle(event);
    }

    selectMember(member: any) {
        this.selectedMember = member;
        this.op.hide();
    }
}
