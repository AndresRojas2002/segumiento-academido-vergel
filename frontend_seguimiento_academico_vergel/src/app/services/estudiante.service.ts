import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Estudiante } from '../models/estudiante';

@Injectable({ providedIn: 'root' })
export class EstudianteService {

  private readonly apiUrl = '/api/students';

  constructor(private http: HttpClient) {}

  // Listar todos
  getEstudiantes(): Observable<Estudiante[]> {
    return this.http.get<Estudiante[]>(this.apiUrl);
  }

  // Buscar por id
  getPorId(id: number): Observable<Estudiante> {
    return this.http.get<Estudiante>(`${this.apiUrl}/${id}`);
  }

  // Crear
  crearEstudiante(body: any): Observable<Estudiante> {
    return this.http.post<Estudiante>(this.apiUrl, body);
  }

  // Actualizar
  actualizarEstudiante(id: number, body: any): Observable<Estudiante> {
    return this.http.put<Estudiante>(`${this.apiUrl}/${id}`, body);
  }

  // Eliminar
  eliminarEstudiante(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
