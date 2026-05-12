import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Cliente } from '../../core/modelos';
import { ClienteService } from '../../core/servicios/cliente.service';

@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './clientes.component.html',
  styleUrl: './clientes.component.scss'
})
export class ClientesComponent implements OnInit {

  clientes: Cliente[] = [];
  formulario: FormGroup;
  clienteSeleccionado: Cliente | null = null;
  textoBusqueda: string = '';
  cargando: boolean = false;
  mensaje: { tipo: string; texto: string } | null = null;

  constructor(
    private clienteService: ClienteService,
    private fb: FormBuilder
  ) {
    this.formulario = this.fb.group({
      tipoDocumento: ['CC', Validators.required],
      numeroDocumento: ['', [Validators.required, Validators.minLength(4)]],
      nombre: ['', [Validators.required, Validators.minLength(2)]],
      apellido: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', Validators.email],
      telefono: [''],
      direccion: [''],
      fechaNacimiento: [''],
      nacionalidad: ['']
    });
  }

  ngOnInit(): void {
    this.cargarClientes();
  }

  // Carga los clientes
  cargarClientes(): void {
    this.cargando = true;
    this.clienteService.listar().subscribe({
      next: (datos) => {
        this.clientes = datos;
        this.cargando = false;
      },
      error: () => {
        this.cargando = false;
      }
    });
  }

  // Busca clientes
  buscar(): void {
    this.cargando = true;
    this.clienteService.buscar(this.textoBusqueda).subscribe(datos => {
      this.clientes = datos;
      this.cargando = false;
    });
  }

  // Selecciona uno de la tabla
  seleccionar(c: Cliente): void {
    this.clienteSeleccionado = c;
    this.formulario.patchValue({
      tipoDocumento: c.tipoDocumento,
      numeroDocumento: c.numeroDocumento,
      nombre: c.nombre,
      apellido: c.apellido,
      email: c.email || '',
      telefono: c.telefono || '',
      direccion: c.direccion || '',
      fechaNacimiento: c.fechaNacimiento || '',
      nacionalidad: c.nacionalidad || ''
    });
  }

  // Limpia el formulario
  limpiar(): void {
    this.formulario.reset({ tipoDocumento: 'CC' });
    this.clienteSeleccionado = null;
    this.mensaje = null;
  }

  // Guarda un nuevo cliente
  guardar(): void {
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      this.mostrarMensaje('error', 'Revise los campos del formulario');
      return;
    }

    const datos = this.formulario.value as Cliente;
    this.clienteService.crear(datos).subscribe({
      next: (nuevo) => {
        this.mostrarMensaje('exito', 'Cliente registrado con ID ' + nuevo.idPersona);
        this.cargarClientes();
        this.limpiar();
      },
      error: (err) => {
        this.mostrarMensaje('error', err.message);
      }
    });
  }

  // Actualiza cliente
  actualizar(): void {
    if (!this.clienteSeleccionado) {
      this.mostrarMensaje('error', 'Seleccione un cliente de la tabla');
      return;
    }
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    const datos: Cliente = {
      ...this.clienteSeleccionado,
      ...this.formulario.value
    };

    this.clienteService.actualizar(datos).subscribe({
      next: () => {
        this.mostrarMensaje('exito', 'Cliente actualizado correctamente');
        this.cargarClientes();
        this.limpiar();
      },
      error: (err) => {
        this.mostrarMensaje('error', err.message);
      }
    });
  }

  // Elimina cliente
  eliminar(): void {
    if (!this.clienteSeleccionado) {
      this.mostrarMensaje('error', 'Seleccione un cliente de la tabla');
      return;
    }
    const c = this.clienteSeleccionado;
    if (!confirm('Eliminar al cliente ' + c.nombre + ' ' + c.apellido + '?')) {
      return;
    }

    this.clienteService.eliminar(c.idPersona).subscribe({
      next: () => {
        this.mostrarMensaje('exito', 'Cliente eliminado');
        this.cargarClientes();
        this.limpiar();
      },
      error: (err) => {
        this.mostrarMensaje('error', err.message);
      }
    });
  }

  // Verifica campo invalido
  campoInvalido(nombre: string): boolean {
    const campo = this.formulario.get(nombre);
    if (!campo) {
      return false;
    }
    return campo.invalid && (campo.dirty || campo.touched);
  }

  // Muestra mensaje temporal
  private mostrarMensaje(tipo: string, texto: string): void {
    this.mensaje = { tipo, texto };
    setTimeout(() => { this.mensaje = null; }, 4000);
  }
}
