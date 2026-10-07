import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { LoginResponse } from '../models';

@Injectable({providedIn:'root'})
export class AuthService {
  private readonly api='http://localhost:8080/api/v1';
  private readonly tokenKey='medpharm_token';
  readonly isAuthenticated = signal<boolean>(!!localStorage.getItem(this.tokenKey));
  readonly username = signal<string>(localStorage.getItem('medpharm_username') ?? '');

  constructor(private http:HttpClient){}

  login(credentials:{username:string;password:string}):Observable<LoginResponse>{
    return this.http.post<LoginResponse>(`${this.api}/auth/login`,credentials).pipe(
      tap(response=>{
        localStorage.setItem(this.tokenKey,response.token);
        localStorage.setItem('medpharm_username',response.username);
        localStorage.setItem('medpharm_rol',response.rol);
        this.isAuthenticated.set(true);
        this.username.set(response.username);
      })
    );
  }

  logout(){
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem('medpharm_username');
    localStorage.removeItem('medpharm_rol');
    this.isAuthenticated.set(false);
    this.username.set('');
  }

  getToken():string|null { return localStorage.getItem(this.tokenKey); }
}
