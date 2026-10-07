import { Component, computed, inject, signal } from '@angular/core';
import { Receta } from '../../models';
import { RecetaService } from '../../services/receta.service';
import { RouterLink } from '@angular/router';

@Component({
  selector:'app-recetas-list',
  standalone:true,
  imports:[RouterLink],
  template:`
  <main class="container">
    <div class="card">
      <div style="display:flex;justify-content:space-between;align-items:center;gap:10px;flex-wrap:wrap">
        <div><h1>Recetas médicas</h1><p>Consulta y gestión de recetas.</p></div>
        <a class="btn" routerLink="/nueva-receta">+ Nueva receta</a>
      </div>

      <div class="form-group" style="max-width:300px">
        <label>Filtrar por estado</label>
        <select [value]="estado()" (change)="estado.set($any($event.target).value)">
          <option value="TODAS">TODAS</option>
          <option value="PENDIENTE">PENDIENTE</option>
          <option value="DESPACHADA">DESPACHADA</option>
          <option value="CANCELADA">CANCELADA</option>
        </select>
      </div>

      @if(error){<div class="error">{{error}}</div>}
      <div style="overflow:auto">
      <table>
        <thead><tr><th>Código</th><th>Paciente</th><th>Médico</th><th>Estado</th><th>Medicamentos</th><th>Acciones</th></tr></thead>
        <tbody>
          @for (r of filtradas(); track r.id) {
            <tr>
              <td>{{r.codigoReceta}}</td>
              <td>{{r.pacienteNombre}}</td>
              <td>{{r.medicoNombre}}</td>
              <td><span class="badge" [class.pendiente]="r.estado==='PENDIENTE'" [class.despachada]="r.estado==='DESPACHADA'" [class.cancelada]="r.estado==='CANCELADA'">{{r.estado}}</span></td>
              <td>
                @for(d of r.detalles; track d.id) { <div>{{d.medicamentoNombre}} — {{d.cantidad}}</div> }
              </td>
              <td>
                @if(r.estado==='PENDIENTE'){
                  <div class="actions">
                    <button class="btn success" (click)="cambiarEstado(r,'DESPACHADA')">Despachar</button>
                    <button class="btn danger" (click)="cambiarEstado(r,'CANCELADA')">Cancelar</button>
                  </div>
                }
              </td>
            </tr>
          } @empty { <tr><td colspan="6">No hay recetas para este filtro.</td></tr> }
        </tbody>
      </table>
      </div>
    </div>
  </main>`
})
export class RecetasListComponent {
  private service=inject(RecetaService);
  recetas=signal<Receta[]>([]);
  estado=signal('TODAS');
  error='';
  filtradas=computed(()=>this.estado()==='TODAS'
    ? this.recetas()
    : this.recetas().filter(r=>r.estado===this.estado()));

  constructor(){this.cargar();}
  cargar(){this.service.getAll().subscribe({
    next:data=>this.recetas.set(data),
    error:err=>this.error=err?.error?.detail ?? 'No se pudieron cargar las recetas.'
  });}
  cambiarEstado(r:Receta,estado:string){
    this.service.updateEstado(r.id,estado).subscribe({
      next:actualizada=>this.recetas.update(list=>list.map(x=>x.id===actualizada.id?actualizada:x)),
      error:err=>this.error=err?.error?.detail ?? 'No se pudo actualizar el estado.'
    });
  }
}
