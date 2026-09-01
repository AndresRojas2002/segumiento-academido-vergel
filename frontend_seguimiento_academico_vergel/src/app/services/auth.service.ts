import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';

export interface AuthResponse { token: string; role: string; }

@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);
  private router = inject(Router);

  readonly user = signal<{ userName: string; role: string } | null>(this.restore());

  private restore() {
    if (!localStorage.getItem('jwt')) return null;
    return { userName: localStorage.getItem('user') || '', role: localStorage.getItem('role') || '' };
  }

  get token(): string | null { return localStorage.getItem('jwt'); }
  get isLoggedIn(): boolean { return !!this.token; }

  login(userName: string, password: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>('/api/auth/login', { userName, password }).pipe(
      tap(res => {
        localStorage.setItem('jwt', res.token);
        localStorage.setItem('role', res.role || '');
        localStorage.setItem('user', userName);
        this.user.set({ userName, role: res.role || '' });
      })
    );
  }

  logout() {
    localStorage.removeItem('jwt');
    localStorage.removeItem('role');
    localStorage.removeItem('user');
    this.user.set(null);
    this.router.navigate(['/login']);
  }
}
