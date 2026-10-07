import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector:'app-login',
  standalone:true,
  imports:[ReactiveFormsModule],
  template:`
  <main class="container">
    <div class="card" style="max-width:460px;margin:50px auto">
      <h1>Inicio de sesión</h1>
      <p>MedPharm Express</p>
      <form [formGroup]="form" (ngSubmit)="login()">
        <div class="form-group">
          <label for="username">Usuario</label>
          <input id="username" formControlName="username">
          @if (form.controls.username.touched && form.controls.username.invalid) {
            <div class="error">El usuario es requerido.</div>
          }
        </div>
        <div class="form-group">
          <label for="password">Contraseña</label>
          <input id="password" type="password" formControlName="password">
          @if (form.controls.password.touched && form.controls.password.invalid) {
            <div class="error">La contraseña es requerida y debe tener mínimo 6 caracteres.</div>
          }
        </div>
        @if (error) { <div class="error">{{error}}</div> }
        <button class="btn" type="submit" [disabled]="form.invalid || loading">
          {{loading ? 'Ingresando...' : 'Ingresar'}}
        </button>
      </form>
      <hr>
      <small>Prueba: medico1 / password123 o farma1 / password123</small>
    </div>
  </main>`
})
export class LoginComponent {
  private fb=inject(FormBuilder);
  private auth=inject(AuthService);
  private router=inject(Router);
  form=this.fb.nonNullable.group({
    username:['',Validators.required],
    password:['',[Validators.required,Validators.minLength(6)]]
  });
  loading=false; error='';

  login(){
    if(this.form.invalid){this.form.markAllAsTouched();return;}
    this.loading=true; this.error='';
    this.auth.login(this.form.getRawValue()).subscribe({
      next:()=>this.router.navigate(['/recetas']),
      error:err=>{this.loading=false; this.error=err?.error?.detail ?? 'Credenciales inválidas.';}
    });
  }
}
