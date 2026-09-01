import { Routes } from '@angular/router';
import { LoginComponent, LayoutComponent, AlertasComponent, ProfesorComponent, AcudienteComponent, EstudianteComponent, GradoComponent, MateriaComponent, MatriculaComponent, NotaComponent, AsistenciaComponent } from './components';
import { authGuard } from './auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: '',
    component: LayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: 'alertas', component: AlertasComponent },
      { path: 'profesores', component: ProfesorComponent },
      { path: 'acudientes', component: AcudienteComponent },
      { path: 'estudiantes', component: EstudianteComponent },
      { path: 'grados', component: GradoComponent },
      { path: 'materias', component: MateriaComponent },
      { path: 'matriculas', component: MatriculaComponent },
      { path: 'notas', component: NotaComponent },
      { path: 'asistencias', component: AsistenciaComponent },
      { path: '', pathMatch: 'full', redirectTo: 'alertas' },
    ],
  },
  { path: '**', redirectTo: '' },
];
