/**
 * App-Routing-Konfiguration
 *
 * Definiert die Navigationspfade, zugehörige Komponenten und Rollenzugriffe (RoleGuard)
 * - Verwendet: AuthGuard („NoAuthGuard“) für Login-Schutz
 * - Layout-Komponente: AppLayout als Container für Seiten mit Navigationsstruktur
 * - Rollenbasiertes Routing für Administrator, Benutzer, Gast
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { Routes } from '@angular/router';
import { RoleGuard } from './guards/role.guard';
import { NoAuthGuard } from './guards/no-auth.guard';

// Layout & Seiten-Komponenten
import { AppLayout } from './layout/component/app.layout';
import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './pages/home/dashboard.component';
import { AnnualEvaluationComponent } from './pages/home/annualevaluation.component';
import { JournalComponent } from './pages/home/journal.component';

// Administration
import { UserComponent } from './pages/adminsitration/user.component';
import { CategoryComponent } from './pages/adminsitration/category.component';
import { ActionComponent } from './pages/adminsitration/action.component';
import { SectorComponent } from './pages/adminsitration/sector.component';
import { GroupComponent } from './pages/adminsitration/group.component';
import { MemberComponent } from './pages/adminsitration/member.component';

// Sonstige Seiten
import { SettingsComponent } from './pages/miscellaneous/settings.component';
import { DocumentationComponent } from './pages/miscellaneous/documentation.component';
import { GroupChangesComponent } from './pages/miscellaneous/grougchanges.component';
import { MemberChangesComponent } from './pages/miscellaneous/memberchanges.component';

// Routing-Definitionen
export const routes: Routes = [
  // Login nur für nicht authentifizierte Nutzer
  { path: 'login', component: LoginComponent, canActivate: [NoAuthGuard] },

  {
    path: 'dashboard',
    component: AppLayout, // Layout mit Menü, Header etc.
    children: [
      {
        path: '',
        component: DashboardComponent,
        canActivate: [RoleGuard],
        data: { roles: ['Administrator', 'Benutzer', 'Gast'] }
      },
      {
        path: 'evaluation',
        component: AnnualEvaluationComponent,
        canActivate: [RoleGuard],
        data: { roles: ['Administrator', 'Benutzer'] }
      },
      {
        path: 'journal',
        component: JournalComponent,
        canActivate: [RoleGuard],
        data: { roles: ['Administrator', 'Benutzer'] }
      },
      {
        path: 'user',
        component: UserComponent,
        canActivate: [RoleGuard],
        data: { roles: ['Administrator'] }
      },
      {
        path: 'categories',
        component: CategoryComponent,
        canActivate: [RoleGuard],
        data: { roles: ['Administrator', 'Benutzer'] }
      },
      {
        path: 'sectors',
        component: SectorComponent,
        canActivate: [RoleGuard],
        data: { roles: ['Administrator', 'Benutzer'] }
      },
      {
        path: 'groups',
        component: GroupComponent,
        canActivate: [RoleGuard],
        data: { roles: ['Administrator', 'Benutzer'] }
      },
      {
        path: 'members',
        component: MemberComponent,
        canActivate: [RoleGuard],
        data: { roles: ['Administrator', 'Benutzer', 'Gast'] }
      },
      {
        path: 'actions',
        component: ActionComponent,
        canActivate: [RoleGuard],
        data: { roles: ['Administrator', 'Benutzer'] }
      },
      {
        path: 'settings',
        component: SettingsComponent,
        canActivate: [RoleGuard],
        data: { roles: ['Administrator'] }
      },
      {
        path: 'groupchanges',
        component: GroupChangesComponent,
        canActivate: [RoleGuard],
        data: { roles: ['Administrator', 'Benutzer'] }
      },
      {
        path: 'memberchanges',
        component: MemberChangesComponent,
        canActivate: [RoleGuard],
        data: { roles: ['Administrator', 'Benutzer'] }
      }
    ]
  },

  // Wildcard-Fallback: Weiterleitung zur Startseite
  { path: '**', redirectTo: '/dashboard' }
];
