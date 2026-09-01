import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { Matricula } from '../../models/matricula';
import { MatriculaService } from '../../services/matricula.service';
import { EstudianteService } from '../../services/estudiante.service';
import { Estudiante } from '../../models/estudiante';
import { GradoService } from '../../services/grado.service';
import { Grado } from '../../models/grado';

type Vista = 'listar' | 'crear' | 'buscarId' | 'buscarNombre' | 'actualizar';

@Component({
  selector: 'app-matricula',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './matricula.component.html',
  styleUrl: './matricula.component.css'
})
export class MatriculaComponent implements OnInit {
  vista = signal<Vista>('listar');
  items = signal<Matricula[]>([]);
  loading = signal(true);
  error = signal(false);
  form: FormGroup;
  editId: number | null = null;
  idBuscar = '';
  encontrado: Matricula | null = null;
  buscadoId = false;
  idEdit = '';
  texto = '';
  resultados: Matricula[] = [];
  buscadoNombre = false;
  estudiantes: Estudiante[] = [];
  grados: Grado[] = [];
  actualizable = false;

  private TR: Record<string,string> = { PRESENT:'Presente', ABSENT:'Ausente', LATE:'Tarde', EXCUSED:'Excusa', ACTIVE:'Activa', RETIRED:'Retirada' };
  tr(v: any) { return (v != null && this.TR[v]) ? this.TR[v] : v; }

  constructor(private servicio: MatriculaService, private fb: FormBuilder, private estudianteService: EstudianteService, private gradoService: GradoService) {
    this.form = this.fb.group({
      students: [null, [Validators.required]],
      idGrado: [null, [Validators.required]],
      enrollmentDate: ['', []],
    });
  }

  ngOnInit(): void {
    this.estudianteService.getEstudiantes().subscribe(x => this.estudiantes = x);
    this.gradoService.getGrados().subscribe(x => this.grados = x);
    this.cargar();
  }

  setView(v: Vista) { this.vista.set(v); if (v === 'buscarNombre') { this.resultados = []; this.buscadoNombre = false; } }

  cargar() {
    this.loading.set(true); this.error.set(false);
    this.servicio.getMatriculas().subscribe({
      next: (x) => { this.items.set(x); this.loading.set(false); },
      error: (err) => { console.error(err); this.error.set(true); this.loading.set(false); }
    });
  }

  private construir(v: any, isUpdate: boolean): any {
    const body: any = {};
    body.students = Number(v.students);
    body.idGrado = Number(v.idGrado);
    if (v.enrollmentDate) body.enrollmentDate = v.enrollmentDate;
    return body;
  }

  crear() {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.servicio.crearMatricula(this.construir(this.form.value, false)).subscribe({
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
    this.servicio.eliminarMatricula(id).subscribe({
      next: () => { alert('Eliminado'); this.cargar(); },
      error: (err) => alert(err?.error?.message || 'No se pudo eliminar')
    });
  }
}
