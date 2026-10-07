export interface LoginResponse { token:string; username:string; rol:string; }
export interface Medicamento { id:number; codigo:string; nombre:string; stock:number; precioUnitario:number; }
export interface DetalleReceta {
  id?:number; medicamentoId:number; medicamentoNombre?:string;
  cantidad:number; dosisIndicada:string;
}
export interface Receta {
  id:number; codigoReceta:string; pacienteNombre:string; medicoId:number;
  medicoNombre:string; estado:string; fechaEmision:string; detalles:DetalleReceta[];
}
