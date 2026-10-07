import { Component, inject } from '@angular/core';
import { FormArray, FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MedicamentoService } from '../../services/medicamento.service';
import { RecetaService } from '../../services/receta.service';
import { Medicamento } from '../../models';
import { positivoValidator } from '../../validators/positivo.validator';

@Component({
  selector:'app-receta-form',
  standalone:true,
  imports:[ReactiveFormsModule],
  template:`
  <main class="container">
    <div class="card">
      <h1>Nueva receta</h1>
      <form [formGroup]="form" (ngSubmit)="guardar()">
        <div class="form-group">
          <label>Nombre del paciente</label>
          <input formControlName="pacienteNombre">
          @if(form.controls.pacienteNombre.touched && form.controls.pacienteNombre.invalid){
            <div class="error">El nombre es requerido y debe tener mínimo 5 caracteres.</div>
          }
        </div>

        <h2>Medicamentos</h2>
        <div formArrayName="detalles">
          @for(grupo of detalles.controls; let i=$index; track i){
            <div class="card" [formGroupName]="i" style="margin-bottom:12px">
              <div class="grid grid-2">
                <div class="form-group">
                  <label>Medicamento</label>
                  <select formControlName="medicamentoId">
                    <option [ngValue]="null">Seleccione...</option>
                    @for(m of medicamentos; track m.id){
                      <option [ngValue]="m.id">{{m.codigo}} - {{m.nombre}} (stock: {{m.stock}})</option>
                    }
                  </select>
                  @if(grupo.get('medicamentoId')?.touched && grupo.get('medicamentoId')?.invalid){<div class="error">Seleccione un medicamento.</div>}
                </div>
                <div class="form-group">
                  <label>Cantidad</label>
                  <input type="number" formControlName="cantidad" min="1">
                  @if(grupo.get('cantidad')?.touched && grupo.get('cantidad')?.invalid){<div class="error">Debe ser un entero mayor a 0.</div>}
                </div>
              </div>
              <div class="form-group">
                <label>Dosis indicada</label>
                <input formControlName="dosisIndicada">
                @if(grupo.get('dosisIndicada')?.touched && grupo.get('dosisIndicada')?.invalid){<div class="error">La dosis es requerida.</div>}
              </div>
              @if(detalles.length>1){<button type="button" class="btn danger" (click)="quitar(i)">Eliminar</button>}
            </div>
          }
        </div>

        <div class="actions">
          <button type="button" class="btn secondary" (click)="agregar()">+ Agregar medicamento</button>
          <button type="submit" class="btn" [disabled]="form.invalid || guardando">Guardar receta</button>
        </div>
        @if(error){<p class="error">{{error}}</p>}
      </form>
    </div>
  </main>`
})
export class RecetaFormComponent {
  private fb=inject(FormBuilder);
  private recetas=inject(RecetaService);
  private meds=inject(MedicamentoService);
  private router=inject(Router);

  medicamentos:Medicamento[]=[];
  error=''; guardando=false;

  form=this.fb.group({
    pacienteNombre:['',[Validators.required,Validators.minLength(5)]],
    detalles:this.fb.array([])
  });

  get detalles(){return this.form.controls.detalles as FormArray;}

  constructor(){
    this.meds.getAll().subscribe({
      next:data=>this.medicamentos=data,
      error:err=>this.error=err?.error?.detail ?? 'No se pudo cargar el catálogo.'
    });
    this.agregar();
  }

  agregar(){
    this.detalles.push(this.fb.group({
      medicamentoId:[null as number|null,Validators.required],
      cantidad:[1,[Validators.required,positivoValidator]],
      dosisIndicada:['',Validators.required]
    }));
  }

  quitar(i:number){this.detalles.removeAt(i);}

  guardar(){
    if(this.form.invalid){this.form.markAllAsTouched();return;}
    this.guardando=true; this.error='';
    this.recetas.create(this.form.getRawValue()).subscribe({
      next:()=>this.router.navigate(['/recetas']),
      error:err=>{this.guardando=false;this.error=err?.error?.detail ?? 'No se pudo guardar la receta.';}
    });
  }
}
