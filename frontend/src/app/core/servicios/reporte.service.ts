import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { delay } from 'rxjs/operators';
import { CLIENTES_MOCK, HABITACIONES_MOCK, PAGOS_MOCK, RESERVAS_MOCK } from '../datos/datos-mock';

export interface Indicadores {
  totalClientes: number;
  reservasActivas: number;
  habitacionesDisponibles: number;
  ingresosMes: number;
}

@Injectable({ providedIn: 'root' })
export class ReporteService {

  private demora: number = 250;

  constructor() { }

  // Indicadores del dashboard
  indicadores(): Observable<Indicadores> {
    const ahora = new Date();
    const inicioMes = new Date(ahora.getFullYear(), ahora.getMonth(), 1);

    let ingresos = 0;
    for (const p of PAGOS_MOCK) {
      if (p.estado === 'Aprobado' && new Date(p.fechaPago) >= inicioMes) {
        ingresos = ingresos + p.monto;
      }
    }

    let activas = 0;
    for (const r of RESERVAS_MOCK) {
      if (r.estado !== 'Cancelada' && r.estado !== 'No Show' && r.estado !== 'Finalizada') {
        activas = activas + 1;
      }
    }

    let disponibles = 0;
    for (const h of HABITACIONES_MOCK) {
      if (h.estado === 'Disponible') {
        disponibles = disponibles + 1;
      }
    }

    const datos: Indicadores = {
      totalClientes: CLIENTES_MOCK.length,
      reservasActivas: activas,
      habitacionesDisponibles: disponibles,
      ingresosMes: ingresos
    };

    return of(datos).pipe(delay(this.demora));
  }

  // Ocupacion por estado
  ocupacionPorEstado(): Observable<{ estado: string; cantidad: number }[]> {
    const conteo: { [key: string]: number } = {};
    for (const h of HABITACIONES_MOCK) {
      conteo[h.estado] = (conteo[h.estado] || 0) + 1;
    }
    const lista = Object.keys(conteo).map(k => ({ estado: k, cantidad: conteo[k] }));
    return of(lista).pipe(delay(this.demora));
  }

  // Ingresos por metodo de pago
  ingresosPorMetodo(): Observable<{ metodo: string; cantidad: number; total: number }[]> {
    const mapa: { [key: string]: { cantidad: number; total: number } } = {};
    for (const p of PAGOS_MOCK) {
      if (p.estado !== 'Aprobado') continue;
      if (!mapa[p.metodo]) {
        mapa[p.metodo] = { cantidad: 0, total: 0 };
      }
      mapa[p.metodo].cantidad = mapa[p.metodo].cantidad + 1;
      mapa[p.metodo].total = mapa[p.metodo].total + p.monto;
    }
    const lista = Object.keys(mapa).map(k => ({
      metodo: k,
      cantidad: mapa[k].cantidad,
      total: mapa[k].total
    }));
    return of(lista).pipe(delay(this.demora));
  }
}
