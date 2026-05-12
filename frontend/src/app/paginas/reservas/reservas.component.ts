import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Reserva } from '../../core/modelos';
import { ReservaService } from '../../core/servicios/reserva.service';

@Component({
  selector: 'app-reservas',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './reservas.component.html',
  styleUrl: './reservas.component.scss'
})
export class ReservasComponent implements OnInit {

  reservas: Reserva[] = [];

  constructor(private reservaService: ReservaService) { }

  ngOnInit(): void {
    this.cargar();
  }

  // Carga las reservas
  cargar(): void {
    this.reservaService.listar().subscribe(datos => {
      this.reservas = datos;
    });
  }

  // Clase visual segun estado
  claseEstado(estado: string): string {
    if (estado === 'Confirmada') return 'badge badge-primario';
    if (estado === 'Pendiente') return 'badge badge-acento';
    if (estado === 'En Curso') return 'badge badge-exito';
    if (estado === 'Cancelada' || estado === 'No Show') return 'badge badge-peligro';
    return 'badge';
  }

  // Cancela una reserva
  cancelar(r: Reserva): void {
    if (!confirm('Cancelar la reserva numero ' + r.idReserva + '?')) {
      return;
    }
    this.reservaService.cancelar(r.idReserva).subscribe(() => {
      this.cargar();
    });
  }
}
