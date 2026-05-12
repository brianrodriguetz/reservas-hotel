import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { delay } from 'rxjs/operators';
import { Reserva } from '../modelos';
import { RESERVAS_MOCK } from '../datos/datos-mock';

@Injectable({ providedIn: 'root' })
export class ReservaService {

  private reservas: Reserva[] = [...RESERVAS_MOCK];
  private proximoId: number = 2000;
  private demora: number = 300;

  constructor() { }

  // Lista todas
  listar(): Observable<Reserva[]> {
    return of([...this.reservas]).pipe(delay(this.demora));
  }

  // Busca por id
  buscarPorId(id: number): Observable<Reserva | undefined> {
    return of(this.reservas.find(r => r.idReserva === id)).pipe(delay(this.demora));
  }

  // Crea nueva
  crear(reserva: Reserva): Observable<Reserva> {
    reserva.idReserva = this.proximoId++;
    reserva.fechaReserva = new Date().toISOString();
    this.reservas.unshift(reserva);
    return of(reserva).pipe(delay(this.demora));
  }

  // Cancela
  cancelar(id: number): Observable<void> {
    const i = this.reservas.findIndex(r => r.idReserva === id);
    if (i === -1) {
      return throwError(() => new Error('Reserva no encontrada')).pipe(delay(this.demora));
    }
    this.reservas[i].estado = 'Cancelada';
    return of(void 0).pipe(delay(this.demora));
  }
}
