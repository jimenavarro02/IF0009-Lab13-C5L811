# IF0009-Lab13-carnet — MedPharm Express

Proyecto del Laboratorio 13 de Desarrollo de Software IV.

## 1. Tecnologías
- Java 17
- Spring Boot 3.5.6
- Spring Security + JWT (HMAC-SHA256)
- H2 Database en memoria
- JPA / Hibernate
- Angular 19 Standalone
- Formularios Reactivos
- Signals (`signal`, `computed`)
- HttpInterceptorFn
- AuthGuard
- Git / GitHub

## 2. Estructura
- `medpharm-backend/`: API REST Spring Boot.
- `medpharm-frontend/`: SPA Angular.
- `docs/`: evidencia de depuración.
- `README.md`: documentación técnica.

## 3. Ejecución del backend
Desde `medpharm-backend`:

```bash
mvn clean spring-boot:run
```

API: `http://localhost:8080`

H2 Console: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:medpharmdb`
- User: `sa`
- Password: vacío

## 4. Ejecución del frontend
Desde `medpharm-frontend`:

```bash
npm install
npm start
```

Aplicación: `http://localhost:4200`

## 5. Usuarios de prueba

> Nota: el hash incluido en el texto proporcionado para el laboratorio tenía 52 caracteres y no correspondía a un BCrypt completo de 60 caracteres. Para que el login sea funcional, `data.sql` utiliza un hash BCrypt válido para `password123`.

- `medico1` / `password123`
- `farma1` / `password123`

## 6. Endpoints
### Autenticación
`POST /api/v1/auth/login`

### Medicamentos
`GET /api/v1/medicamentos`

### Recetas
`GET /api/v1/recetas`
`GET /api/v1/recetas/estado/{estado}`
`POST /api/v1/recetas`
`PATCH /api/v1/recetas/{id}/estado`

Los endpoints protegidos requieren:

```text
Authorization: Bearer <token>
```

## 7. JWT e interceptor
El backend usa autenticación stateless. El `AuthTokenFilter` extrae el token desde `Authorization`, valida firma y expiración y establece el contexto de Spring Security.

Angular guarda el JWT en `localStorage`. El `authInterceptor` clona las solicitudes y agrega automáticamente:

```text
Authorization: Bearer <token>
```

El `authGuard` evita acceder a `/recetas` y `/nueva-receta` sin token.

## 8. Formularios reactivos
`LoginComponent` usa `FormGroup` y valida:
- username requerido.
- password requerido y mínimo 6 caracteres.

`RecetaFormComponent` usa un `FormGroup` anidado con `FormArray`, permitiendo múltiples medicamentos.

El validador `positivoValidator` acepta solamente enteros estrictamente mayores que cero.

## 9. Signals
`RecetasListComponent` almacena las recetas con `signal()` y usa `computed()` para filtrar dinámicamente por:
- TODAS
- PENDIENTE
- DESPACHADA
- CANCELADA

## 10. CORS
El backend permite `http://localhost:4200` y las cabeceras necesarias para el JWT, incluyendo `Authorization`.

## 11. Manejo de errores RFC 7807
`GlobalExceptionHandler` utiliza `ProblemDetail`, proporcionando respuestas estructuradas con `type`, `title`, `status` y `detail`.

Ejemplo:

```json
{
  "type": "https://medpharm.local/problems/400",
  "title": "Solicitud inválida",
  "status": 400,
  "detail": "La cantidad debe ser un entero mayor que 0."
}
```

## 12. Prueba de depuración 401
Para reproducir la prueba solicitada por el laboratorio:

1. Comentar temporalmente `authInterceptor` en `src/app/app.config.ts`.
2. Iniciar sesión.
3. Entrar a `/recetas`.
4. Abrir DevTools > Network.
5. Revisar la petición `GET /api/v1/recetas`.
6. Como ya no se envía `Authorization: Bearer <token>`, Spring Security rechaza la solicitud protegida.
7. Tomar captura de la petición y respuesta 401/403.
8. Guardarla como `docs/error_jwt_401.png`.
9. Volver a activar `withInterceptors([authInterceptor])`.

La captura real debe generarse durante la ejecución en el navegador; no se puede sustituir por una imagen inventada.

## 13. Commits semánticos mínimos
Usar al menos estos cinco commits durante el desarrollo:

```text
feat(backend): setup H2 database and JPA entities
feat(backend): add JWT authentication and security filter
feat(backend): implement recipes and medicines REST API
feat(angular): add login guard and JWT interceptor
feat(angular): add reactive recipe forms and signals dashboard
```

## 14. Checklist de entrega
- [ ] Backend compila con `mvn clean install`.
- [ ] Login devuelve JWT.
- [ ] `GET /api/v1/recetas` funciona con JWT.
- [ ] `GET /api/v1/medicamentos` funciona con JWT.
- [ ] Creación de receta funciona.
- [ ] Cambio de estado funciona.
- [ ] Stock se descuenta al despachar una receta pendiente.
- [ ] CORS funciona desde `localhost:4200`.
- [ ] Angular usa AuthService.
- [ ] Angular usa interceptor JWT.
- [ ] Angular usa AuthGuard.
- [ ] Dashboard usa `signal()` y `computed()`.
- [ ] Formulario usa `FormGroup` + `FormArray`.
- [ ] Existe `positivoValidator`.
- [ ] Se genera captura real de 401.
- [ ] La captura está en `docs/error_jwt_401.png`.
- [ ] README documenta el error y la solución.
- [ ] Se realizaron mínimo 5 commits semánticos.
- [ ] Repositorio GitHub público con nombre `IF0009-Lab13-carnet`.
