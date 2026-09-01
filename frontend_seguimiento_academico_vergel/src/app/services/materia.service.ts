import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Materia } from '../models/materia';

@Injectable({ providedIn: 'root' })
export class MateriaService {

  private readonly apiUrl = '/api/subjects';

  constructor(private http: HttpClient) {}

  // Listar todos
  getMaterias(): Observable<Materia[]> {
    return this.http.get<Materia[]>(this.apiUrl);
  }

  // Buscar por id
  getPorId(id: number): Observable<Materia> {
    return this.http.get<Materia>(`${this.apiUrl}/${id}`);
  }

  // Crear
  crearMateria(body: any): Observable<Materia> {
    return this.http.post<Materia>(this.apiUrl, body);
  }

  // Actualizar
  actualizarMateria(id: number, body: any): Observable<Materia> {
    return this.http.put<Materia>(`${this.apiUrl}/${id}`, body);
  }

  // Eliminar
  eliminarMateria(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
