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
import { GroupComponent } from './pages/adminsitration/group.component';
import { MemberComponent } from './pages/adminsitration/member.component';
import { GroupChangesComponent } from './pages/miscellaneous/grougchanges.component';
import { MemberChangesComponent } from './pages/miscellaneous/memberchanges.component';
import { JournalComponent } from './pages/home/journal.component';
import { AnnualEvaluationComponent } from './pages/home/annualevaluation.component';
import { DashboardComponent } from './pages/home/dashboard.component';

/*canActivate: [RoleGuard], data: { roles: ['Administrator', 'Benutzer', 'Gast']*/

export const routes = [
  { path: 'login', component: LoginComponent, canActivate: [NoAuthGuard] },
  {
    path: 'dashboard', component: AppLayout, children: [
      { path: '', component: DashboardComponent, canActivate: [RoleGuard], data: { roles: ['Administrator', 'Benutzer', 'Gast'] } },
      { path: 'evaluation', component: AnnualEvaluationComponent, canActivate: [RoleGuard], data: { roles: ['Administrator', 'Benutzer'] } },
      { path: 'journal', component: JournalComponent, canActivate: [RoleGuard], data: { roles: ['Administrator', 'Benutzer'] } },
      { path: 'user', component: UserComponent, canActivate: [RoleGuard], data: { roles: ['Administrator'] } },
      { path: 'categories', component: CategoryComponent, canActivate: [RoleGuard], data: { roles: ['Administrator', 'Benutzer'] } },
      { path: 'sectors', component: SectorComponent, canActivate: [RoleGuard], data: { roles: ['Administrator', 'Benutzer'] } },
      { path: 'groups', component: GroupComponent, canActivate: [RoleGuard], data: { roles: ['Administrator', 'Benutzer'] } },
      { path: 'members', component: MemberComponent, canActivate: [RoleGuard], data: { roles: ['Administrator', 'Benutzer', 'Gast'] } },
      { path: 'actions', component: ActionComponent, canActivate: [RoleGuard], data: { roles: ['Administrator', 'Benutzer'] } },
      { path: 'settings', component: SettingsComponent, canActivate: [RoleGuard], data: { roles: ['Administrator'] } },
      { path: 'groupchanges', component: GroupChangesComponent, canActivate: [RoleGuard], data: { roles: ['Administrator', 'Benutzer'] } },
      { path: 'memberchanges', component: MemberChangesComponent, canActivate: [RoleGuard], data: { roles: ['Administrator', 'Benutzer'] } }
    ]
  },
  { path: '**', redirectTo: '/dashboard' }
];