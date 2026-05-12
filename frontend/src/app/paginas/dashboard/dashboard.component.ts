import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReporteService, Indicadores } from '../../core/servicios/reporte.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {

  indicadores: Indicadores | null = null;

  constructor(private reporteService: ReporteService) { }

  // Carga los datos al iniciar
  ngOnInit(): void {
    this.reporteService.indicadores().subscribe(datos => {
      this.indicadores = datos;
    });
  }
}
