import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Alerta } from '../models/alerta';

@Injectable({ providedIn: 'root' })
export class AlertaService {
  private readonly apiUrl = '/api/alerts';
  constructor(private http: HttpClient) {}

  getAlertas(): Observable<Alerta[]> { return this.http.get<Alerta[]>(this.apiUrl); }
  getPorEstado(status: string): Observable<Alerta[]> { return this.http.get<Alerta[]>(`${this.apiUrl}/status/${status}`); }
  evaluarTodo(): Observable<Alerta[]> { return this.http.post<Alerta[]>(`${this.apiUrl}/evaluate`, {}); }
  actualizarEstado(id: number, status: string): Observable<Alerta> {
    return this.http.patch<Alerta>(`${this.apiUrl}/${id}/status`, { status });
  }
  eliminar(id: number): Observable<void> { return this.http.delete<void>(`${this.apiUrl}/${id}`); }
}
