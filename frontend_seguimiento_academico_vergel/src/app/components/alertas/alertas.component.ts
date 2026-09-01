import { Component, computed, inject, signal, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AlertaService } from '../../services/alerta.service';
import { SeguimientoService } from '../../services/seguimiento.service';
import { ProfesorService } from '../../services/profesor.service';
import { Alerta } from '../../models/alerta';
import { Seguimiento } from '../../models/seguimiento';
import { Profesor } from '../../models/profesor';

const L = {
  type: { LOW_PERFORMANCE: 'Bajo rendimiento', MULTIPLE_SUBJECTS_AT_RISK: 'Varias materias en riesgo', LOW_ATTENDANCE: 'Inasistencia' } as Record<string,string>,
  severity: { HIGH: 'ALTA', MEDIUM: 'MEDIA', LOW: 'BAJA' } as Record<string,string>,
  status: { OPEN: 'Abierta', IN_PROGRESS: 'En proceso', RESOLVED: 'Resuelta' } as Record<string,string>,
};

@Component({
  selector: 'app-alertas',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './alertas.component.html',
  styleUrl: './alertas.component.css'
})
export class AlertasComponent implements OnInit {
  private alertaService = inject(AlertaService);
  private seguimientoService = inject(SeguimientoService);
  private profesorService = inject(ProfesorService);
  L = L;

  alerts = signal<Alerta[]>([]);
  filter = signal<string>('ALL');
  evaluating = signal(false);
  professors: Profesor[] = [];
  followups: Record<number, Seguimiento[] | undefined> = {};
  showForm: Record<number, boolean> = {};
  form: Record<number, { action: string; result: string; professorId: number | null }> = {};
  private order: Record<string, number> = { HIGH: 0, MEDIUM: 1, LOW: 2 };

  filtered = computed(() => {
    const f = this.filter();
    const list = f === 'ALL' ? this.alerts() : this.alerts().filter(a => a.status === f);
    return [...list].sort((a, b) => (this.order[a.severity] - this.order[b.severity]) || a.studentName.localeCompare(b.studentName));
  });

  ngOnInit() {
    this.profesorService.getProfesores().subscribe({ next: p => this.professors = p, error: () => {} });
    this.load();
  }

  count(field: 'severity' | 'status', value: string): number {
    return this.alerts().filter(a => (a as any)[field] === value).length;
  }

  load() {
    this.alertaService.getAlertas().subscribe({
      next: (list) => {
        this.alerts.set(list);
        list.forEach(a => { if (!this.form[a.id]) this.form[a.id] = { action: '', result: '', professorId: null }; this.loadFollowups(a.id); });
      },
      error: (e) => alert(e?.error?.message || 'No se pudieron cargar las alertas')
    });
  }

  evaluate() {
    this.evaluating.set(true);
    this.alertaService.evaluarTodo().subscribe({
      next: (nuevas) => { this.evaluating.set(false); alert(nuevas.length ? `Se generaron ${nuevas.length} alerta(s) nueva(s)` : 'Sin novedades: nadie superó los umbrales'); this.load(); },
      error: (e) => { this.evaluating.set(false); alert(e?.error?.message || 'Error al evaluar'); }
    });
  }

  changeStatus(a: Alerta, status: string) {
    if (!status) return;
    this.alertaService.actualizarEstado(a.id, status).subscribe({
      next: () => this.load(),
      error: (e) => alert(e?.error?.message || 'No se pudo actualizar')
    });
  }

  toggleForm(id: number) { this.showForm[id] = !this.showForm[id]; }

  loadFollowups(id: number) {
    this.seguimientoService.getPorAlerta(id).subscribe({ next: fs => this.followups[id] = fs, error: () => {} });
  }

  submitFollowup(id: number) {
    const f = this.form[id];
    if (!f.action) return;
    const body: any = { alertId: id, action: f.action };
    if (f.result) body.result = f.result;
    if (f.professorId) body.professorId = f.professorId;
    this.seguimientoService.crear(body).subscribe({
      next: () => { this.form[id] = { action: '', result: '', professorId: null }; this.showForm[id] = false; this.load(); },
      error: (e) => alert(e?.error?.message || 'No se pudo registrar el seguimiento')
    });
  }
}
