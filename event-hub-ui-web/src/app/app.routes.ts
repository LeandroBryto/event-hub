import { Routes } from '@angular/router';
import { EventoListComponent } from './pages/evento-list/evento-list.component';
import { EventoAdminComponent } from './pages/evento-admin/evento-admin.component';
import { MeusIngressosComponent } from './pages/meus-ingressos/meus-ingressos.component';

export const routes: Routes = [
  { path: '', redirectTo: 'eventos', pathMatch: 'full' },
  { path: 'eventos', component: EventoListComponent },
  { path: 'admin', component: EventoAdminComponent },
  { path: 'meus-ingressos', component: MeusIngressosComponent }
];
