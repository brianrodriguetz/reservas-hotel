import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/servicios/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  formulario: FormGroup;
  cargando: boolean = false;
  mensajeError: string = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.formulario = this.fb.group({
      usuario: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.minLength(3)]]
    });
  }

  // Inicia sesion
  ingresar(): void {
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    this.cargando = true;
    this.mensajeError = '';

    const usuario = this.formulario.value.usuario;
    const password = this.formulario.value.password;

    this.authService.login(usuario, password).subscribe({
      next: () => {
        this.cargando = false;
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        this.cargando = false;
        this.mensajeError = err.message;
      }
    });
  }

  // Salir
  salir(): void {
    if (confirm('Esta seguro de salir del sistema?')) {
      window.close();
    }
  }

  // Verifica si un campo es invalido
  campoInvalido(nombre: string): boolean {
    const campo = this.formulario.get(nombre);
    if (!campo) {
      return false;
    }
    return campo.invalid && (campo.dirty || campo.touched);
  }
}
