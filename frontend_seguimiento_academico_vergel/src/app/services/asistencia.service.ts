import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Asistencia } from '../models/asistencia';

@Injectable({ providedIn: 'root' })
export class AsistenciaService {

  private readonly apiUrl = '/api/attendances';

  constructor(private http: HttpClient) {}

  // Listar todos
  getAsistencias(): Observable<Asistencia[]> {
    return this.http.get<Asistencia[]>(this.apiUrl);
  }

  // Buscar por id
  getPorId(id: number): Observable<Asistencia> {
    return this.http.get<Asistencia>(`${this.apiUrl}/${id}`);
  }

  // Crear
  crearAsistencia(body: any): Observable<Asistencia> {
    return this.http.post<Asistencia>(this.apiUrl, body);
  }

  // Actualizar
  actualizarAsistencia(id: number, body: any): Observable<Asistencia> {
    return this.http.put<Asistencia>(`${this.apiUrl}/${id}`, body);
  }

  // Eliminar
  eliminarAsistencia(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
