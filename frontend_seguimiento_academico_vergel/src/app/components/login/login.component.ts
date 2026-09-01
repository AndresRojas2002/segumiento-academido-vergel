import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ProfesorService } from '../../services/profesor.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  private auth = inject(AuthService);
  private profesorService = inject(ProfesorService);
  private router = inject(Router);

  userName = '';
  password = '';
  loading = signal(false);
  showReg = signal(false);
  reg: any = { name: '', lastName: '', userName: '', password: '', email: '', professorNumber: '', phone: '', address: '' };

  login() {
    if (!this.userName || !this.password) return;
    this.loading.set(true);
    this.auth.login(this.userName, this.password).subscribe({
      next: () => this.router.navigate(['/alertas']),
      error: (e) => { this.loading.set(false); alert(e?.error?.message || 'Credenciales inválidas'); }
    });
  }

  register() {
    const b = { ...this.reg };
    if (!b.phone) delete b.phone;
    if (!b.address) delete b.address;
    this.profesorService.crearProfesor(b).subscribe({
      next: () => { alert('Docente creado. Ya puedes ingresar.'); this.userName = this.reg.userName; this.showReg.set(false); },
      error: (e) => alert(e?.error?.message || 'No se pudo crear el docente')
    });
  }
}
