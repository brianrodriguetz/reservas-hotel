import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { delay } from 'rxjs/operators';
import { Sesion } from '../modelos';
import { EMPLEADOS_MOCK } from '../datos/datos-mock';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private sesion: Sesion | null = null;

  constructor() {
    // Carga sesion guardada
    const guardada = localStorage.getItem('hotel_sesion');
    if (guardada) {
      this.sesion = JSON.parse(guardada);
    }
  }

  // Inicia sesion
  login(usuario: string, password: string): Observable<Sesion> {
    // Usuarios de prueba
    if (usuario === 'admin' && password === 'admin') {
      const s: Sesion = {
        idEmpleado: 101,
        nombreCompleto: 'Brian Rodriguez',
        usuario: 'admin',
        cargo: 'Administrador',
        esAdmin: true
      };
      this.guardarSesion(s);
      return of(s).pipe(delay(500));
    }

    if (usuario === 'empleado' && password === 'empleado') {
      const s: Sesion = {
        idEmpleado: 102,
        nombreCompleto: 'Jose Espin',
        usuario: 'empleado',
        cargo: 'Recepcionista',
        esAdmin: false
      };
      this.guardarSesion(s);
      return of(s).pipe(delay(500));
    }

    // Busca en lista mock
    const emp = EMPLEADOS_MOCK.find(e => e.usuario === usuario && e.estado === 'Activo');
    if (!emp) {
      return throwError(() => new Error('Usuario o contrasena incorrectos')).pipe(delay(400));
    }

    const s: Sesion = {
      idEmpleado: emp.idPersona,
      nombreCompleto: emp.nombre + ' ' + emp.apellido,
      usuario: emp.usuario,
      cargo: emp.cargo,
      esAdmin: emp.cargo === 'Administrador'
    };
    this.guardarSesion(s);
    return of(s).pipe(delay(500));
  }

  // Cierra sesion
  logout(): void {
    this.sesion = null;
    localStorage.removeItem('hotel_sesion');
  }

  // Verifica autenticacion
  estaAutenticado(): boolean {
    return this.sesion !== null;
  }

  // Verifica admin
  esAdministrador(): boolean {
    return this.sesion?.esAdmin === true;
  }

  // Obtiene sesion actual
  getSesion(): Sesion | null {
    return this.sesion;
  }

  // Guarda sesion
  private guardarSesion(s: Sesion): void {
    this.sesion = s;
    localStorage.setItem('hotel_sesion', JSON.stringify(s));
  }
}
