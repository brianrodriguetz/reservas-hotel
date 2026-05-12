import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { delay } from 'rxjs/operators';
import { Habitacion } from '../modelos';
import { HABITACIONES_MOCK, RESERVAS_MOCK } from '../datos/datos-mock';

@Injectable({ providedIn: 'root' })
export class HabitacionService {

  private habitaciones: Habitacion[] = [...HABITACIONES_MOCK];
  private demora: number = 300;

  constructor() { }

  // Lista todas
  listar(): Observable<Habitacion[]> {
    return of([...this.habitaciones]).pipe(delay(this.demora));
  }

  // Busca por id
  buscarPorId(id: number): Observable<Habitacion | undefined> {
    return of(this.habitaciones.find(h => h.idHabitacion === id)).pipe(delay(this.demora));
  }

  // Disponibles en un rango de fechas
  disponiblesEnRango(fechaEntrada: string, fechaSalida: string, tipo: string): Observable<Habitacion[]> {
    const entrada = new Date(fechaEntrada);
    const salida = new Date(fechaSalida);

    const idsOcupados = new Set<number>();
    for (const r of RESERVAS_MOCK) {
      if (r.estado === 'Cancelada' || r.estado === 'No Show') {
        continue;
      }
      const fe = new Date(r.fechaEntrada);
      const fs = new Date(r.fechaSalida);
      const seSolapan = !(fs <= entrada || fe >= salida);
      if (seSolapan) {
        idsOcupados.add(r.idHabitacion);
      }
    }

    let disponibles = this.habitaciones.filter(h =>
      h.estado === 'Disponible' && !idsOcupados.has(h.idHabitacion)
    );

    if (tipo && tipo !== 'Todos') {
      disponibles = disponibles.filter(h => h.tipo === tipo);
    }

    return of(disponibles).pipe(delay(this.demora));
  }
}
