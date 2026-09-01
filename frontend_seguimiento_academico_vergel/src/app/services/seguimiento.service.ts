import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Seguimiento } from '../models/seguimiento';

@Injectable({ providedIn: 'root' })
export class SeguimientoService {
  private readonly apiUrl = '/api/followups';
  constructor(private http: HttpClient) {}

  getPorAlerta(alertId: number): Observable<Seguimiento[]> {
    return this.http.get<Seguimiento[]>(`${this.apiUrl}/alert/${alertId}`);
  }
  crear(body: any): Observable<Seguimiento> { return this.http.post<Seguimiento>(this.apiUrl, body); }
}
