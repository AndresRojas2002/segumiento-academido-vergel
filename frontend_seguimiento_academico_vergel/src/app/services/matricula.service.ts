import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Matricula } from '../models/matricula';

@Injectable({ providedIn: 'root' })
export class MatriculaService {

  private readonly apiUrl = '/api/enrollments';

  constructor(private http: HttpClient) {}

  // Listar todos
  getMatriculas(): Observable<Matricula[]> {
    return this.http.get<Matricula[]>(this.apiUrl);
  }

  // Buscar por id
  getPorId(id: number): Observable<Matricula> {
    return this.http.get<Matricula>(`${this.apiUrl}/${id}`);
  }

  // Crear
  crearMatricula(body: any): Observable<Matricula> {
    return this.http.post<Matricula>(this.apiUrl, body);
  }

  // Actualizar
  actualizarMatricula(id: number, body: any): Observable<Matricula> {
    return this.http.put<Matricula>(`${this.apiUrl}/${id}`, body);
  }

  // Eliminar
  eliminarMatricula(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
