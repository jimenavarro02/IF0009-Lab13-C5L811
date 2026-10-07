import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Receta } from '../models';

@Injectable({providedIn:'root'})
export class RecetaService {
  private readonly api='http://localhost:8080/api/v1/recetas';
  constructor(private http:HttpClient){}
  getAll():Observable<Receta[]>{return this.http.get<Receta[]>(this.api);}
  getByEstado(estado:string):Observable<Receta[]>{return this.http.get<Receta[]>(`${this.api}/estado/${estado}`);}
  create(payload:unknown):Observable<Receta>{return this.http.post<Receta>(this.api,payload);}
  updateEstado(id:number,estado:string):Observable<Receta>{
    return this.http.patch<Receta>(`${this.api}/${id}/estado`,{estado});
  }
}
