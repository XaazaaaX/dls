import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { RouterLink, RouterLinkActive, RouterModule, RouterOutlet } from '@angular/router';
import { NavbarComponent } from '../shared/navbar/navbar.component';
import { CommonModule } from '@angular/common';
import { PanelMenuModule } from 'primeng/panelmenu';
import { MenubarModule } from 'primeng/menubar';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { MenuItem } from 'primeng/api';
import { Sidebar } from 'primeng/sidebar';
import { Checkbox } from 'primeng/checkbox';

@Component({
  selector: 'app-dashboard',
  imports: [
    RouterOutlet, 
    NavbarComponent,
    RouterModule, CommonModule, MenubarModule, PanelMenuModule, ButtonModule, CardModule, Sidebar, Checkbox],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

  test: any;

  constructor(private authService:AuthService){
    this.test = this.authService.getUserRole();
  }

  sidebarVisible = true;
  menuItems: MenuItem[] = [
    { label: 'Dashboard', icon: 'pi pi-home', routerLink: ['/admin-dashboard'] },
    { label: 'Benutzer', icon: 'pi pi-users', routerLink: ['/users'] },
    { label: 'Einstellungen', icon: 'pi pi-cog', routerLink: ['/settings'] },
    { label: 'Berichte', icon: 'pi pi-chart-line', routerLink: ['/reports'] },
    { label: 'Hilfe', icon: 'pi pi-question', routerLink: ['/help'] }
  ];

  /*
  toggleSidebar() {
    this.sidebarVisible = !this.sidebarVisible;
  }
    */

  logout() {
    console.log('Logout ausgeführt');
  }

  sidebarOpen = false;

  toggleSidebar() {
    this.sidebarOpen = !this.sidebarOpen;
  }

}
