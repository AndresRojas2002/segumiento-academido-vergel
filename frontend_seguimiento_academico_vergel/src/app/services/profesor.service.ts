import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Profesor } from '../models/profesor';

@Injectable({ providedIn: 'root' })
export class ProfesorService {

  private readonly apiUrl = '/api/professors';

  constructor(private http: HttpClient) {}

  // Listar todos
  getProfesores(): Observable<Profesor[]> {
    return this.http.get<Profesor[]>(this.apiUrl);
  }

  // Buscar por id
  getPorId(id: number): Observable<Profesor> {
    return this.http.get<Profesor>(`${this.apiUrl}/${id}`);
  }

  // Crear
  crearProfesor(body: any): Observable<Profesor> {
    return this.http.post<Profesor>(this.apiUrl, body);
  }

  // Actualizar
  actualizarProfesor(id: number, body: any): Observable<Profesor> {
    return this.http.put<Profesor>(`${this.apiUrl}/${id}`, body);
  }

  // Eliminar
  eliminarProfesor(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
