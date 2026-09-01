import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { Nota } from '../../models/nota';
import { NotaService } from '../../services/nota.service';
import { MatriculaService } from '../../services/matricula.service';
import { Matricula } from '../../models/matricula';
import { MateriaService } from '../../services/materia.service';
import { Materia } from '../../models/materia';

type Vista = 'listar' | 'crear' | 'buscarId' | 'buscarNombre' | 'actualizar';

@Component({
  selector: 'app-nota',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './nota.component.html',
  styleUrl: './nota.component.css'
})
export class NotaComponent implements OnInit {
  vista = signal<Vista>('listar');
  items = signal<Nota[]>([]);
  loading = signal(true);
  error = signal(false);
  form: FormGroup;
  editForm: FormGroup;
  editId: number | null = null;
  idBuscar = '';
  encontrado: Nota | null = null;
  buscadoId = false;
  idEdit = '';
  texto = '';
  resultados: Nota[] = [];
  buscadoNombre = false;
  matriculas: Matricula[] = [];
  materias: Materia[] = [];
  actualizable = true;

  private TR: Record<string,string> = { PRESENT:'Presente', ABSENT:'Ausente', LATE:'Tarde', EXCUSED:'Excusa', ACTIVE:'Activa', RETIRED:'Retirada' };
  tr(v: any) { return (v != null && this.TR[v]) ? this.TR[v] : v; }

  constructor(private servicio: NotaService, private fb: FormBuilder, private matriculaService: MatriculaService, private materiaService: MateriaService) {
    this.form = this.fb.group({
      enrollmentId: [null, [Validators.required]],
      subjectId: [null, [Validators.required]],
      value: [null, [Validators.required, Validators.min(0), Validators.max(5)]],
      period: ['', []],
    });
    this.editForm = this.fb.group({
      enrollmentId: [null, [Validators.required]],
      subjectId: [null, [Validators.required]],
      value: [null, [Validators.required, Validators.min(0), Validators.max(5)]],
      period: ['', []],
    });
  }

  ngOnInit(): void {
    this.matriculaService.getMatriculas().subscribe(x => this.matriculas = x);
    this.materiaService.getMaterias().subscribe(x => this.materias = x);
    this.cargar();
  }

  setView(v: Vista) { this.vista.set(v); if (v === 'buscarNombre') { this.resultados = []; this.buscadoNombre = false; } }

  cargar() {
    this.loading.set(true); this.error.set(false);
    this.servicio.getNotas().subscribe({
      next: (x) => { this.items.set(x); this.loading.set(false); },
      error: (err) => { console.error(err); this.error.set(true); this.loading.set(false); }
    });
  }

  private construir(v: any, isUpdate: boolean): any {
    const body: any = {};
    body.enrollmentId = Number(v.enrollmentId);
    body.subjectId = Number(v.subjectId);
    body.value = Number(v.value);
    if (v.period) body.period = v.period;
    return body;
  }

  crear() {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.servicio.crearNota(this.construir(this.form.value, false)).subscribe({
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
    this.servicio.eliminarNota(id).subscribe({
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
        value: (res as any).value ?? null,
        period: (res as any).period ?? '',
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
    this.servicio.actualizarNota(this.editId, this.construir(this.editForm.value, true)).subscribe({
      next: () => { alert('Actualizado correctamente'); this.editId = null; this.cargar(); this.setView('listar'); },
      error: (err) => alert(err?.error?.message || 'No se pudo actualizar')
    });
  }
}
