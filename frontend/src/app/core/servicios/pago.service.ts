import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { delay } from 'rxjs/operators';
import { Pago } from '../modelos';
import { PAGOS_MOCK } from '../datos/datos-mock';

@Injectable({ providedIn: 'root' })
export class PagoService {

  private pagos: Pago[] = [...PAGOS_MOCK];
  private proximoId: number = 6000;
  private demora: number = 300;

  constructor() { }

  // Lista todos
  listar(): Observable<Pago[]> {
    return of([...this.pagos]).pipe(delay(this.demora));
  }

  // Pagos de una reserva
  porReserva(idReserva: number): Observable<Pago[]> {
    return of(this.pagos.filter(p => p.idReserva === idReserva)).pipe(delay(this.demora));
  }

  // Registra un pago
  registrar(pago: Pago): Observable<Pago> {
    pago.idPago = this.proximoId++;
    pago.fechaPago = new Date().toISOString();
    pago.estado = 'Aprobado';
    this.pagos.unshift(pago);
    return of(pago).pipe(delay(this.demora));
  }
}
