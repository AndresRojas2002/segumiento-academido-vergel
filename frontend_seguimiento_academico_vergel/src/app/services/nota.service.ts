import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Nota } from '../models/nota';

@Injectable({ providedIn: 'root' })
export class NotaService {

  private readonly apiUrl = '/api/notes';

  constructor(private http: HttpClient) {}

  // Listar todos
  getNotas(): Observable<Nota[]> {
    return this.http.get<Nota[]>(this.apiUrl);
  }

  // Buscar por id
  getPorId(id: number): Observable<Nota> {
    return this.http.get<Nota>(`${this.apiUrl}/${id}`);
  }

  // Crear
  crearNota(body: any): Observable<Nota> {
    return this.http.post<Nota>(this.apiUrl, body);
  }

  // Actualizar
  actualizarNota(id: number, body: any): Observable<Nota> {
    return this.http.put<Nota>(`${this.apiUrl}/${id}`, body);
  }

  // Eliminar
  eliminarNota(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
