import {Routes} from '@angular/router';
import { RoleGuard } from './guards/role.guard';
import { LoginComponent } from './pages/login/login.component';
import { NoAuthGuard } from './guards/no-auth.guard';
import { AppLayout } from './layout/component/app.layout';
import { DocumentationComponent } from './pages/miscellaneous/documentation.component';
import { UserComponent } from './pages/adminsitration/user.component';
import { SettingsComponent } from './pages/miscellaneous/settings.component';
import { CategoryComponent } from './pages/adminsitration/category.component';
import { ActionComponent } from './pages/adminsitration/action.component';
import { SectorComponent } from './pages/adminsitration/sector.component';

/*canActivate: [RoleGuard], data: { roles: ['Administrator', 'Benutzer', 'Gast']*/

export const routes = [
  /*{ path: '', redirectTo: '/dashboard', pathMatch: 'full' as 'full' },*/
  { path: 'login', component: LoginComponent, canActivate: [NoAuthGuard] },
  {
    path: 'dashboard', component: AppLayout, children: [
      { path: '', redirectTo: 'user', pathMatch: 'full' as 'full' },
      //{ path: 'home', component: HomeComponent, canActivate: [RoleGuard], data: { roles: ['Administrator', 'Benutzer', 'Gast'] } },
      { path: 'user', component: UserComponent, canActivate: [RoleGuard], data: { roles: ['Administrator'] } },
      { path: 'categories', component: CategoryComponent, canActivate: [RoleGuard], data: { roles: ['Administrator'] } },
      { path: 'sectors', component: SectorComponent, canActivate: [RoleGuard], data: { roles: ['Administrator'] } },
      { path: 'actions', component: ActionComponent, canActivate: [RoleGuard], data: { roles: ['Administrator'] } },
      { path: 'settings', component: SettingsComponent, canActivate: [RoleGuard], data: { roles: ['Administrator'] } },
      { path: 'documentation', component: DocumentationComponent, canActivate: [RoleGuard], data: { roles: ['Administrator', 'Benutzer', 'Gast'] } }
    ]
  },
  //{path: 'protected', component: DashboardComponent, canActivate: [RoleGuard], data: { roles: ['Admin'] }},
  //{path: '**', component: PageNotFoundComponent},
  { path: '**', redirectTo: '/dashboard' }
];