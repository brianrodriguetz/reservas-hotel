import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../core/servicios/auth.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {

  esAdmin: boolean = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {
    this.esAdmin = this.authService.esAdministrador();
  }

  // Cerrar sesion
  cerrarSesion(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
