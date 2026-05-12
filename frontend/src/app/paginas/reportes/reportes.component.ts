import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReporteService } from '../../core/servicios/reporte.service';

@Component({
  selector: 'app-reportes',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './reportes.component.html',
  styleUrl: './reportes.component.scss'
})
export class ReportesComponent implements OnInit {

  ocupacion: { estado: string; cantidad: number }[] = [];
  porMetodo: { metodo: string; cantidad: number; total: number }[] = [];

  constructor(private reporteService: ReporteService) { }

  ngOnInit(): void {
    this.reporteService.ocupacionPorEstado().subscribe(datos => {
      this.ocupacion = datos;
    });

    this.reporteService.ingresosPorMetodo().subscribe(datos => {
      this.porMetodo = datos;
    });
  }

  // Clase visual segun estado
  claseEstado(estado: string): string {
    if (estado === 'Disponible') return 'color-exito';
    if (estado === 'Ocupada') return 'color-acento';
    if (estado === 'En Mantenimiento') return 'color-peligro';
    return 'color-primario';
  }
}
