import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { AppMenuitem } from './app.menuitem';
import { AuthService } from '../../services/auth.service';

@Component({
    selector: 'app-menu',
    standalone: true,
    imports: [CommonModule, AppMenuitem, RouterModule],
    template: `
    <ul class="layout-menu">
        <ng-container *ngFor="let item of model; let i = index">
            <li app-menuitem *ngIf="!item.separator" [item]="item" [index]="i" [root]="true"></li>
            <li *ngIf="item.separator" class="menu-separator"></li>
        </ng-container>
    </ul> `
})
export class AppMenu {
    model: MenuItem[] = [];
    isAdmin: boolean;
    isUser: boolean;
    isGuest: boolean;

    constructor(private authService:AuthService) {
        this.isAdmin = this.authService.isAdmin();
        this.isUser = this.authService.isUser();
        this.isGuest = this.authService.isGuest();
    }

    ngOnInit() {

        this.model = [
            {
                label: 'Home',
                visible: this.isUser,
                items: [
                    { label: 'Dashboard', icon: 'pi pi-fw pi-home', routerLink: ['/dashboard/home'], visible: this.isUser },
                    { label: 'Journal', icon: 'pi pi-fw pi-pencil', routerLink: ['/'], visible: this.isUser }
                ]
            },
            {
                label: 'Verwaltung',
                visible: this.isGuest,
                items: [
                    { label: 'Benutzer', icon: 'pi pi-fw pi-shield', routerLink: ['/dashboard/user'], visible: this.isAdmin },
                    { label: 'Mitglieder', icon: 'pi pi-fw pi-user', routerLink: ['/'], visible: this.isGuest },
                    { label: 'Funktionsgruppen', icon: 'pi pi-fw pi-users', routerLink: ['/'], visible: this.isUser },
                    { label: 'Sparten', icon: 'pi pi-fw pi-folder', routerLink: ['/dashboard/categories'], visible: this.isUser },
                    { label: 'Bereiche', icon: 'pi pi-fw pi-file', routerLink: ['/dashboard/sectors'], visible: this.isUser },
                    { label: 'Aktionen', icon: 'pi pi-fw pi-play', routerLink: ['/dashboard/actions'], visible: this.isUser },
                ]
            },
            {
                label: 'Sonstiges',
                visible: this.isGuest,
                items: [
                    { label: 'Einstellungen', icon: 'pi pi-fw pi-cog', routerLink: ['/dashboard/settings'], visible: this.isAdmin},
                    { label: 'Historie', icon: 'pi pi-fw pi-history', routerLink: ['/'], visible: this.isUser },
                    { label: 'Dokumentation', icon: 'pi pi-fw pi-book', routerLink: ['/dashboard/documentation'], visible: this.isGuest }
                ]
            },
        ];
    }
}
