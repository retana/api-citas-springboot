# API Citas de Taller (Spring Boot)

Proyecto REST para gestión de usuarios, vehículos y citas, pensado para aprendizaje con buenas prácticas: seguridad JWT, documentación OpenAPI/Swagger, pruebas unitarias y colección Postman. Mantiene la estructura original en capas (controller, service, repository, model).

## Tecnologías
- Java 17
- Spring Boot 3.2.9 (Web, Data JPA, Security)
- JPA/Hibernate (MariaDB en runtime, H2 en tests)
- JWT (jjwt 0.12.x)
- Swagger/OpenAPI (springdoc 2.6.0)
- JUnit 5, Mockito, JaCoCo

## Estructura
- `model/`: Entidades JPA (`Usuario`, `Vehiculo`, `Cita`)
- `model/repository/`: Repositorios Spring Data
- `service/`: Lógica de negocio sencilla
- `controller/`: Endpoints REST (`Auth`, `Usuario`, `Vehiculo`, `Cita`)
- `security/`: Configuración de Spring Security + filtro JWT
- `config/`: Configuración de OpenAPI
- `exception/`: Manejador global de errores
- `postman/`: Colección para probar endpoints
- `src/test/`: Pruebas unitarias y configuración de tests (H2, JaCoCo)

## Configuración
Archivo: `src/main/resources/application.properties`

- Puerto: `server.port=9999`
- Base de datos (MariaDB por defecto):
  - `spring.datasource.url=jdbc:mariadb://localhost:3306/citas`
  - `spring.datasource.username=root`
  - `spring.datasource.password=123`
  - `spring.jpa.hibernate.ddl-auto=update`
- JWT:
  - `security.jwt.secret` (Base64, 32 bytes recomendado)
  - `security.jwt.expiration-ms` (milisegundos, por defecto 3600000 = 1h)

Puedes sobrescribir cualquier propiedad con variables de entorno (estándar de Spring):
- Ejemplo (Mac/Linux): `export SECURITY_JWT_SECRET=$(openssl rand -base64 32)`

## Ejecución
1) Arranca MariaDB y crea la BD `citas` (si no existe).
2) Ajusta credenciales en `application.properties` o variables de entorno.
3) Compila y ejecuta:
   - `mvn clean spring-boot:run`
4) Documentación Swagger UI:
   - `http://localhost:9999/swagger-ui.html`

## Autenticación (JWT)
- Registro: `POST /auth/register`
  - Crea usuario con rol por defecto `USER`. Contraseña se guarda con BCrypt (nunca se expone en respuestas JSON).
- Login: `POST /auth/login`
  - Devuelve `{ "token": "<JWT>" }`.
- Uso: En peticiones protegidas, encabezado `Authorization: Bearer <JWT>`.
- Roles: Campo `Usuario.rol` (ej. `USER`, `ADMIN`). Se mapea a autoridades `ROLE_<rol>`.

## Endpoints Principales
- `POST /auth/register` — registrar usuario
- `POST /auth/login` — obtener JWT
- `GET /usuarios` — listar usuarios
- `POST /usuarios` — crear usuario (password write-only)
- `GET /vehiculos` — listar vehículos
- `POST /vehiculos` — crear vehículo
- `GET /vehiculos/{id}` — obtener por id
- `PUT /vehiculos/{id}` — actualizar
- `DELETE /vehiculos/{id}` — eliminar
- `GET /citas` — listar citas
- `POST /citas` — crear cita
- `GET /citas/{id}` — obtener por id
- `PUT /citas/{id}` — actualizar
- `DELETE /citas/{id}` — eliminar

Notas:
- Todos los endpoints excepto `/auth/**` están protegidos (requieren JWT).
- Puedes asignar `rol` manualmente en DB a `ADMIN` si deseas restringir ciertas operaciones con anotaciones como `@PreAuthorize` (ver sección Mejoras).

## Modelos
- `Usuario`: `id`, `nombre`, `username`, `password` (write-only), `rol`
- `Vehiculo`: `id`, `marca`, `modelo`, `placa`, `tipo`, `usuario` (ManyToOne)
- `Cita`: `id`, `fecha`, `hora`, `comentario`, `motivo`, `taller`, `estado`, `vehiculo` (ManyToOne)

## Manejador de Errores
- `exception/GlobalExceptionHandler`: centraliza respuestas de error comunes.

## Colección Postman
- Importa `postman/Citas.postman_collection.json`.
- Variables incluidas: `{{baseUrl}}` (por defecto `http://localhost:9999`) y `{{token}}`.
- Flujo sugerido:
  1) Auth - Register
  2) Auth - Login (guarda `{{token}}` automáticamente)
  3) Probar endpoints protegidos

## Pruebas y Cobertura
- Perfil de tests (`src/test/resources/application-test.properties`) usa H2 en memoria (modo MySQL) y `ddl-auto=create-drop` para tests rápidos y aislados.
- Ejecutar pruebas y generar cobertura:
  - `mvn clean test`
  - Reporte JaCoCo: `target/site/jacoco/index.html`
- Tipos de pruebas incluidas:
  - Servicios (`UsuarioService`, `VehiculoService`, `CitaService`)
  - Seguridad (`JwtService`, `CustomUserDetailsService`)
  - Controladores (`Auth`, `Usuario`, `Vehiculo`, `Cita`) con `@WebMvcTest` y filtros deshabilitados

## Buenas Prácticas Implementadas
- Seguridad stateless con JWT y BCrypt para contraseñas
- Documentación automática con OpenAPI/Swagger + esquema de seguridad bearer
- Password write-only en `Usuario` para no exponer hashes
- Separación en capas (Controller/Service/Repository)
- Manejador global de errores y DTOs para autenticación
- Colección Postman lista para uso en clase
- Tests unitarios con H2 y reporte de cobertura (JaCoCo)

## Mejoras Sugeridas (Opcionales para la clase)
- Validaciones con `jakarta.validation` (`@NotBlank`, `@Email`, etc.) y `@Valid` en controladores
- Autorización por roles en endpoints sensibles, por ejemplo:
  - `@PreAuthorize("hasRole('ADMIN')")` en `DELETE /vehiculos/**` y `DELETE /citas/**`
- Migraciones de DB con Flyway
- Tipos fecha/hora con `LocalDate`/`LocalTime` en `Cita`
- Tests de integración con contexto real y seguridad activa

## Compatibilidad Swagger
- Este proyecto usa Spring Boot 3.2.9 (Spring 6.1.x) + springdoc 2.6.0, combinación estable.
- Si actualizas a Boot 3.4/3.5 (Spring 6.2), asegúrate de usar una versión de `springdoc` compatible para evitar `NoSuchMethodError` en `ControllerAdviceBean`.

## Preguntas Frecuentes
- ¿Usuario admin? Regístrate y cambia `rol` a `ADMIN` en la base de datos.
- ¿Dónde está la UI de Swagger? `http://localhost:9999/swagger-ui.html`
- ¿Cómo genero el secreto JWT? `openssl rand -base64 32`

---
¡Listo! Con esto puedes ejecutar, autenticarte, explorar la documentación, y practicar pruebas unitarias con tus estudiantes.
