import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { Materia } from '../../models/materia';
import { MateriaService } from '../../services/materia.service';
import { ProfesorService } from '../../services/profesor.service';
import { Profesor } from '../../models/profesor';
import { GradoService } from '../../services/grado.service';
import { Grado } from '../../models/grado';

type Vista = 'listar' | 'crear' | 'buscarId' | 'buscarNombre' | 'actualizar';

@Component({
  selector: 'app-materia',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './materia.component.html',
  styleUrl: './materia.component.css'
})
export class MateriaComponent implements OnInit {
  vista = signal<Vista>('listar');
  items = signal<Materia[]>([]);
  loading = signal(true);
  error = signal(false);
  form: FormGroup;
  editForm: FormGroup;
  editId: number | null = null;
  idBuscar = '';
  encontrado: Materia | null = null;
  buscadoId = false;
  idEdit = '';
  texto = '';
  resultados: Materia[] = [];
  buscadoNombre = false;
  profesors: Profesor[] = [];
  grados: Grado[] = [];
  actualizable = true;

  private TR: Record<string,string> = { PRESENT:'Presente', ABSENT:'Ausente', LATE:'Tarde', EXCUSED:'Excusa', ACTIVE:'Activa', RETIRED:'Retirada' };
  tr(v: any) { return (v != null && this.TR[v]) ? this.TR[v] : v; }

  constructor(private servicio: MateriaService, private fb: FormBuilder, private profesorService: ProfesorService, private gradoService: GradoService) {
    this.form = this.fb.group({
      nameSubject: ['', [Validators.required]],
      description: ['', []],
      professorId: [null, [Validators.required]],
      gradeId: [null, [Validators.required]],
    });
    this.editForm = this.fb.group({
      nameSubject: ['', [Validators.required]],
      description: ['', []],
      professorId: [null, [Validators.required]],
      gradeId: [null, [Validators.required]],
    });
  }

  ngOnInit(): void {
    this.profesorService.getProfesores().subscribe(x => this.profesors = x);
    this.gradoService.getGrados().subscribe(x => this.grados = x);
    this.cargar();
  }

  setView(v: Vista) { this.vista.set(v); if (v === 'buscarNombre') { this.resultados = []; this.buscadoNombre = false; } }

  cargar() {
    this.loading.set(true); this.error.set(false);
    this.servicio.getMaterias().subscribe({
      next: (x) => { this.items.set(x); this.loading.set(false); },
      error: (err) => { console.error(err); this.error.set(true); this.loading.set(false); }
    });
  }

  private construir(v: any, isUpdate: boolean): any {
    const body: any = {};
    body.nameSubject = v.nameSubject;
    body.professorId = Number(v.professorId);
    body.gradeId = Number(v.gradeId);
    if (v.description) body.description = v.description;
    return body;
  }

  crear() {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.servicio.crearMateria(this.construir(this.form.value, false)).subscribe({
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
    this.servicio.eliminarMateria(id).subscribe({
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
        nameSubject: (res as any).nameSubject ?? '',
        description: (res as any).description ?? '',
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
    this.servicio.actualizarMateria(this.editId, this.construir(this.editForm.value, true)).subscribe({
      next: () => { alert('Actualizado correctamente'); this.editId = null; this.cargar(); this.setView('listar'); },
      error: (err) => alert(err?.error?.message || 'No se pudo actualizar')
    });
  }
}
