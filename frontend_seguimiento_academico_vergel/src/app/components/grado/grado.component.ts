import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { Grado } from '../../models/grado';
import { GradoService } from '../../services/grado.service';

type Vista = 'listar' | 'crear' | 'buscarId' | 'buscarNombre' | 'actualizar';

@Component({
  selector: 'app-grado',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './grado.component.html',
  styleUrl: './grado.component.css'
})
export class GradoComponent implements OnInit {
  vista = signal<Vista>('listar');
  items = signal<Grado[]>([]);
  loading = signal(true);
  error = signal(false);
  form: FormGroup;
  editForm: FormGroup;
  editId: number | null = null;
  idBuscar = '';
  encontrado: Grado | null = null;
  buscadoId = false;
  idEdit = '';
  texto = '';
  resultados: Grado[] = [];
  buscadoNombre = false;
  actualizable = true;

  private TR: Record<string,string> = { PRESENT:'Presente', ABSENT:'Ausente', LATE:'Tarde', EXCUSED:'Excusa', ACTIVE:'Activa', RETIRED:'Retirada' };
  tr(v: any) { return (v != null && this.TR[v]) ? this.TR[v] : v; }

  constructor(private servicio: GradoService, private fb: FormBuilder) {
    this.form = this.fb.group({
      nameGrade: ['', [Validators.required]],
    });
    this.editForm = this.fb.group({
      nameGrade: ['', [Validators.required]],
    });
  }

  ngOnInit(): void {
    this.cargar();
  }

  setView(v: Vista) { this.vista.set(v); if (v === 'buscarNombre') { this.resultados = []; this.buscadoNombre = false; } }

  cargar() {
    this.loading.set(true); this.error.set(false);
    this.servicio.getGrados().subscribe({
      next: (x) => { this.items.set(x); this.loading.set(false); },
      error: (err) => { console.error(err); this.error.set(true); this.loading.set(false); }
    });
  }

  private construir(v: any, isUpdate: boolean): any {
    const body: any = {};
    body.nameGrade = v.nameGrade;
    return body;
  }

  crear() {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.servicio.crearGrado(this.construir(this.form.value, false)).subscribe({
      next: () => { alert('Creado correctamente'); this.form.reset(); this.cargar(); this.setView('listar'); },
      error: (err) => alert(err?.error?.message || 'No se pudo crear')
    });
  }

  buscarPorId() {
    const id = Number(this.idBuscar);
    if (!id) return;
    this.buscadoId = true; this.encontrado = null;
    this.servicio.getPorId(id).subscribe({
      next: (x) => this.encontrado = x,
      error: () => this.encontrado = null
    });
  }

  buscarPorNombre() {
    const t = this.texto.trim().toLowerCase();
    this.buscadoNombre = true;
    this.resultados = this.items().filter(o =>
      Object.values(o).some(val => typeof val === 'string' && val.toLowerCase().includes(t)));
  }

  borrar(id: number) {
    if (!confirm('¿Seguro que desea eliminar este registro?')) return;
    this.servicio.eliminarGrado(id).subscribe({
      next: () => { alert('Eliminado'); this.cargar(); },
      error: (err) => alert(err?.error?.message || 'No se pudo eliminar')
    });
  }

  cargarParaEditar(id?: number) {
    const theId = id ?? Number(this.idEdit);
    if (!theId) return;
    this.servicio.getPorId(theId).subscribe({
      next: (res) => {
        this.editId = res.id;
        this.editForm.reset();
        this.editForm.patchValue({
        nameGrade: (res as any).nameGrade ?? '',
        });
        this.vista.set('actualizar');
      },
      error: () => alert('No se encontró el registro')
    });
  }

  editarDesdeLista(id: number) { this.idEdit = String(id); this.cargarParaEditar(id); }

  actualizar() {
    if (!this.editId) { alert('Primero carga un registro por su ID'); return; }
    if (this.editForm.invalid) { this.editForm.markAllAsTouched(); return; }
    this.servicio.actualizarGrado(this.editId, this.construir(this.editForm.value, true)).subscribe({
      next: () => { alert('Actualizado correctamente'); this.editId = null; this.cargar(); this.setView('listar'); },
      error: (err) => alert(err?.error?.message || 'No se pudo actualizar')
    });
  }
}
