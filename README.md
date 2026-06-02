# ms-auth

Microservicio encargado de la autenticación de usuarios y control de acceso basado en roles en el sistema **Caleta Lo Abarca**. Forma parte del sistema de gestión de subasta artesanal desarrollado con arquitectura de microservicios Spring Boot.

## ¿Qué hace?

Gestiona el registro e inicio de sesión de todos los usuarios del sistema. Al hacer login, genera un token **JWT** firmado que incluye el nombre de usuario y su rol. Este token identifica al usuario en cada petición y determina a qué funciones del sistema puede acceder según su perfil.

## Roles del sistema

| Rol | Descripción |
|---|---|
| `PESCADOR` | Registra su llegada y captura, ve el estado de sus lotes y sus liquidaciones |
| `COMPRADOR` | Ingresa órdenes de compra con precio máximo por especie, ve resultados de subasta y horario de retiro |
| `ADMINISTRADOR` | Abre la jornada de subasta, gestiona lotes, activa vedas, genera liquidaciones y reportes Sernapesca |

## Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/api/auth/register` | Registra un nuevo usuario con rol |
| POST | `/api/auth/login` | Inicia sesión y devuelve token JWT |
| GET | `/api/auth/usuarios/rol?rol=` | Lista usuarios activos por rol |

## Ejemplo de uso

**Registrar usuario:**
```json
POST /api/auth/register
{
  "username": "don.segundo",
  "password": "123456",
  "rol": "PESCADOR"
}
```

**Login:**
```json
POST /api/auth/login
{
  "username": "don.segundo",
  "password": "123456"
}
```

**Respuesta del login:**
```json
{
  "id": 1,
  "username": "don.segundo",
  "rol": "PESCADOR",
  "token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJkb24uc2VndW5kbyIsInJvbCI6IlBFU0NBRE9SIn0..."
}
```

## Seguridad

- Las contraseñas se almacenan encriptadas con **BCrypt**
- Los tokens JWT tienen expiración de 24 horas (86400000 ms)
- El token incluye el `username` y el `rol` del usuario

## Tecnologías

- Java 21
- Spring Boot 3.4.5
- Spring Security
- Spring Data JPA
- Spring Boot Validation
- JSON Web Token (JJWT 0.12.3)
- PostgreSQL (Neon)
- Lombok

## Configuración

Crear el archivo `src/main/resources/application.properties` con:

```properties
spring.application.name=ms-auth
server.port=8085

spring.datasource.url=jdbc:postgresql://<HOST>/auth_db?sslmode=require&channelBinding=require
spring.datasource.username=<USUARIO>
spring.datasource.password=<PASSWORD>
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

jwt.secret=clave-secreta-super-larga-para-caleta-lo-abarca-2026
jwt.expiration=86400000
```

## Cómo correr

```bash
mvnw.cmd spring-boot:run
```

El servicio queda disponible en `http://localhost:8085`

## Estructura del proyecto

```
ms-auth/
├── config/        → SecurityConfig, JwtConfig
├── controller/    → AuthController (endpoints REST)
├── service/       → AuthService (lógica de autenticación)
├── repository/    → UsuarioRepository (JPA + @Query)
├── model/         → Usuario (entidad JPA con enum Rol)
├── dto/           → LoginRequestDTO, RegisterRequestDTO, AuthResponseDTO, Mapper
├── util/          → JwtUtil (generación y validación de tokens)
└── exception/     → GlobalExceptionHandler, RecursoNoEncontradoException
```

## Parte del sistema

Este microservicio es parte del sistema **Caleta Lo Abarca** junto a:
`ms-pescadores` · `ms-embarcaciones` · `ms-especies` · `ms-capturas` · `ms-subastas` · `ms-compradores` · `ms-pagos` · `ms-bodega` · `ms-vedas` · `ms-reportes`
