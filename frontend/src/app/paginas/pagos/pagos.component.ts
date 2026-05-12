import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Pago } from '../../core/modelos';
import { PagoService } from '../../core/servicios/pago.service';

@Component({
  selector: 'app-pagos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './pagos.component.html',
  styleUrl: './pagos.component.scss'
})
export class PagosComponent implements OnInit {

  pagos: Pago[] = [];

  constructor(private pagoService: PagoService) { }

  ngOnInit(): void {
    this.pagoService.listar().subscribe(datos => {
      this.pagos = datos;
    });
  }
}
