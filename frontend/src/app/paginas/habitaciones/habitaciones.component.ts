import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Habitacion } from '../../core/modelos';
import { HabitacionService } from '../../core/servicios/habitacion.service';

@Component({
  selector: 'app-habitaciones',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './habitaciones.component.html',
  styleUrl: './habitaciones.component.scss'
})
export class HabitacionesComponent implements OnInit {

  habitaciones: Habitacion[] = [];

  constructor(private habitacionService: HabitacionService) { }

  ngOnInit(): void {
    this.cargar();
  }

  // Carga las habitaciones
  cargar(): void {
    this.habitacionService.listar().subscribe(datos => {
      this.habitaciones = datos;
    });
  }

  // Clase visual segun estado
  claseEstado(estado: string): string {
    if (estado === 'Disponible') return 'badge badge-exito';
    if (estado === 'Ocupada') return 'badge badge-acento';
    if (estado === 'En Mantenimiento') return 'badge badge-peligro';
    return 'badge badge-primario';
  }
}
