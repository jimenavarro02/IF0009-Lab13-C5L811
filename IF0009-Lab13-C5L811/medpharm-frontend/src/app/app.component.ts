import { Component, inject } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { AuthService } from './services/auth.service';

@Component({
  selector:'app-root',
  standalone:true,
  imports:[RouterOutlet, RouterLink],
  template:`
    <nav class="nav">
      <div><strong>MedPharm Express</strong></div>
      @if (auth.isAuthenticated()) {
        <div>
          <a routerLink="/recetas">Recetas</a>
          <a routerLink="/nueva-receta">Nueva receta</a>
          <button class="btn secondary" (click)="logout()">Cerrar sesión</button>
        </div>
      }
    </nav>
    <router-outlet />
  `
})
export class AppComponent {
  readonly auth = inject(AuthService);
  private router = inject(Router);
  logout(){ this.auth.logout(); this.router.navigate(['/login']); }
}
