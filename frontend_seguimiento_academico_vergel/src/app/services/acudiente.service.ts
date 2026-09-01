import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Acudiente } from '../models/acudiente';

@Injectable({ providedIn: 'root' })
export class AcudienteService {

  private readonly apiUrl = '/api/parents';

  constructor(private http: HttpClient) {}

  // Listar todos
  getAcudientes(): Observable<Acudiente[]> {
    return this.http.get<Acudiente[]>(this.apiUrl);
  }

  // Buscar por id
  getPorId(id: number): Observable<Acudiente> {
    return this.http.get<Acudiente>(`${this.apiUrl}/${id}`);
  }

  // Crear
  crearAcudiente(body: any): Observable<Acudiente> {
    return this.http.post<Acudiente>(this.apiUrl, body);
  }

  // Actualizar
  actualizarAcudiente(id: number, body: any): Observable<Acudiente> {
    return this.http.put<Acudiente>(`${this.apiUrl}/${id}`, body);
  }

  // Eliminar
  eliminarAcudiente(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
