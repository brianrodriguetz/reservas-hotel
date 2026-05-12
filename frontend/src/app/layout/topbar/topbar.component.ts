import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../core/servicios/auth.service';
import { Sesion } from '../../core/modelos';

@Component({
  selector: 'app-topbar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './topbar.component.html',
  styleUrl: './topbar.component.scss'
})
export class TopbarComponent {

  sesion: Sesion | null = null;

  constructor(private authService: AuthService) {
    this.sesion = this.authService.getSesion();
  }

  // Inicial del nombre
  getInicial(): string {
    if (!this.sesion) {
      return '';
    }
    return this.sesion.nombreCompleto.charAt(0).toUpperCase();
  }
}
