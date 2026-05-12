import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { delay } from 'rxjs/operators';
import { Empleado } from '../modelos';
import { EMPLEADOS_MOCK } from '../datos/datos-mock';

@Injectable({ providedIn: 'root' })
export class EmpleadoService {

  private empleados: Empleado[] = [...EMPLEADOS_MOCK];
  private proximoId: number = 200;
  private demora: number = 300;

  constructor() { }

  // Lista todos
  listar(): Observable<Empleado[]> {
    return of([...this.empleados]).pipe(delay(this.demora));
  }

  // Busca por id
  buscarPorId(id: number): Observable<Empleado | undefined> {
    return of(this.empleados.find(e => e.idPersona === id)).pipe(delay(this.demora));
  }

  // Crea nuevo
  crear(emp: Empleado): Observable<Empleado> {
    const repetido = this.empleados.some(e => e.usuario === emp.usuario);
    if (repetido) {
      return throwError(() => new Error('El nombre de usuario ya esta en uso'))
        .pipe(delay(this.demora));
    }
    emp.idPersona = this.proximoId++;
    this.empleados.push(emp);
    return of(emp).pipe(delay(this.demora));
  }

  // Actualiza
  actualizar(emp: Empleado): Observable<Empleado> {
    const i = this.empleados.findIndex(e => e.idPersona === emp.idPersona);
    if (i === -1) {
      return throwError(() => new Error('Empleado no encontrado')).pipe(delay(this.demora));
    }
    this.empleados[i] = { ...emp };
    return of(this.empleados[i]).pipe(delay(this.demora));
  }

  // Desactiva
  desactivar(id: number): Observable<void> {
    const i = this.empleados.findIndex(e => e.idPersona === id);
    if (i === -1) {
      return throwError(() => new Error('Empleado no encontrado')).pipe(delay(this.demora));
    }
    this.empleados[i].estado = 'Inactivo';
    return of(void 0).pipe(delay(this.demora));
  }
}
