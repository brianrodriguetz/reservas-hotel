import { Routes } from '@angular/router';
import { AdminGuard, AuthGuard, NoAuthGuard } from './core/guards/auth.guard';
import { LoginComponent } from './paginas/login/login.component';
import { MainLayoutComponent } from './layout/main-layout/main-layout.component';
import { DashboardComponent } from './paginas/dashboard/dashboard.component';
import { ClientesComponent } from './paginas/clientes/clientes.component';
import { ReservasComponent } from './paginas/reservas/reservas.component';
import { HabitacionesComponent } from './paginas/habitaciones/habitaciones.component';
import { PagosComponent } from './paginas/pagos/pagos.component';
import { EmpleadosComponent } from './paginas/empleados/empleados.component';
import { ReportesComponent } from './paginas/reportes/reportes.component';

export const routes: Routes = [

  {
    path: 'login',
    component: LoginComponent,
    canActivate: [NoAuthGuard]
  },

  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'dashboard', component: DashboardComponent },
      { path: 'clientes', component: ClientesComponent },
      { path: 'reservas', component: ReservasComponent },
      { path: 'habitaciones', component: HabitacionesComponent },
      { path: 'pagos', component: PagosComponent },
      { path: 'empleados', component: EmpleadosComponent, canActivate: [AdminGuard] },
      { path: 'reportes', component: ReportesComponent, canActivate: [AdminGuard] },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },

  { path: '**', redirectTo: 'dashboard' }
];
