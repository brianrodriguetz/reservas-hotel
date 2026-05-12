import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { delay } from 'rxjs/operators';
import { Cliente } from '../modelos';
import { CLIENTES_MOCK } from '../datos/datos-mock';

@Injectable({ providedIn: 'root' })
export class ClienteService {

  private clientes: Cliente[] = [...CLIENTES_MOCK];
  private proximoId: number = 100;
  private demora: number = 300;

  constructor() { }

  // Lista todos
  listar(): Observable<Cliente[]> {
    return of([...this.clientes]).pipe(delay(this.demora));
  }

  // Busca por id
  buscarPorId(id: number): Observable<Cliente | undefined> {
    const c = this.clientes.find(x => x.idPersona === id);
    return of(c ? { ...c } : undefined).pipe(delay(this.demora));
  }

  // Busca por texto
  buscar(texto: string): Observable<Cliente[]> {
    if (!texto) {
      return this.listar();
    }
    const t = texto.toLowerCase();
    const filtrados = this.clientes.filter(c =>
      c.numeroDocumento.toLowerCase().includes(t) ||
      c.nombre.toLowerCase().includes(t) ||
      c.apellido.toLowerCase().includes(t)
    );
    return of(filtrados).pipe(delay(this.demora));
  }

  // Crea uno nuevo
  crear(cliente: Cliente): Observable<Cliente> {
    const repetido = this.clientes.some(c => c.numeroDocumento === cliente.numeroDocumento);
    if (repetido) {
      return throwError(() => new Error('Ya existe un cliente con ese numero de documento'))
        .pipe(delay(this.demora));
    }
    cliente.idPersona = this.proximoId++;
    this.clientes.push(cliente);
    return of(cliente).pipe(delay(this.demora));
  }

  // Actualiza
  actualizar(cliente: Cliente): Observable<Cliente> {
    const i = this.clientes.findIndex(c => c.idPersona === cliente.idPersona);
    if (i === -1) {
      return throwError(() => new Error('Cliente no encontrado')).pipe(delay(this.demora));
    }
    this.clientes[i] = { ...cliente };
    return of(this.clientes[i]).pipe(delay(this.demora));
  }

  // Elimina
  eliminar(id: number): Observable<void> {
    const i = this.clientes.findIndex(c => c.idPersona === id);
    if (i === -1) {
      return throwError(() => new Error('Cliente no encontrado')).pipe(delay(this.demora));
    }
    this.clientes.splice(i, 1);
    return of(void 0).pipe(delay(this.demora));
  }
}
