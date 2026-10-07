import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Medicamento } from '../models';

@Injectable({providedIn:'root'})
export class MedicamentoService {
  private readonly api='http://localhost:8080/api/v1/medicamentos';
  constructor(private http:HttpClient){}
  getAll():Observable<Medicamento[]>{ return this.http.get<Medicamento[]>(this.api); }
}
