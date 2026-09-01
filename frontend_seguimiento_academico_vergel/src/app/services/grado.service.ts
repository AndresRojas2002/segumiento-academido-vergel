import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Grado } from '../models/grado';

@Injectable({ providedIn: 'root' })
export class GradoService {

  private readonly apiUrl = '/api/grades';

  constructor(private http: HttpClient) {}

  // Listar todos
  getGrados(): Observable<Grado[]> {
    return this.http.get<Grado[]>(this.apiUrl);
  }

  // Buscar por id
  getPorId(id: number): Observable<Grado> {
    return this.http.get<Grado>(`${this.apiUrl}/${id}`);
  }

  // Crear
  crearGrado(body: any): Observable<Grado> {
    return this.http.post<Grado>(this.apiUrl, body);
  }

  // Actualizar
  actualizarGrado(id: number, body: any): Observable<Grado> {
    return this.http.put<Grado>(`${this.apiUrl}/${id}`, body);
  }

  // Eliminar
  eliminarGrado(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
