import { AbstractControl, ValidationErrors } from '@angular/forms';

export function positivoValidator(control: AbstractControl): ValidationErrors | null {
  const value = control.value;
  if (value === null || value === '') return null;
  const number = Number(value);
  return Number.isInteger(number) && number > 0 ? null : { positivo: true };
}
