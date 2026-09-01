# Backend — Seguimiento Académico y Alertas Tempranas · Sede El Vergel

Sistema web para la **Institución Educativa Uribe Gaviria, sede El Vergel**. Además de gestionar la información académica (estudiantes, acudientes, docentes, grados, materias, matrículas, notas y asistencia), **analiza esos datos y genera alertas tempranas** cuando detecta estudiantes en riesgo, y permite al docente registrar el **seguimiento** de cada caso.

> API REST construida con Spring Boot, con el frontend Angular ya integrado.

---

## ✨ Características

- Gestión de usuarios por rol: **docente, estudiante y acudiente**.
- Autenticación con **JWT** y contraseñas cifradas con **BCrypt**.
- Gestión académica: grados, materias, matrículas y notas.
- **Registro de asistencia** por estudiante, materia y fecha.
- **Motor de alertas tempranas** que detecta:
  - Bajo rendimiento (promedio por debajo de la nota mínima).
  - Dificultad en varias materias (dos o más materias perdidas).
  - Inasistencia (porcentaje de asistencia por debajo del mínimo).
- **Seguimiento** de las acciones que el docente toma frente a cada alerta.
- Documentación interactiva con **Swagger UI**.
- **Interfaz web (Angular)** servida por el mismo backend.

---

## 🛠️ Tecnologías

| Área | Tecnología |
|------|-----------|
| Lenguaje | Java 25 |
| Framework | Spring Boot 4.1 |
| Seguridad | Spring Security + JWT (jjwt) |
| Persistencia | Spring Data JPA / Hibernate |
| Base de datos | PostgreSQL |
| Mapeo DTO | MapStruct |
| Utilidades | Lombok |
| Documentación | SpringDoc OpenAPI (Swagger UI) |
| Build | Maven |
| Frontend | Angular 19 (integrado en `resources/static`) |

---

## ✅ Requisitos

- **Java 25**
- **Maven** (o el wrapper incluido `mvnw` / `mvnw.cmd`)
- **PostgreSQL** con una base de datos llamada `gestion_academica_vergel`

---

## ⚙️ Configuración

Los parámetros están en `src/main/resources/application.properties`. Ajusta los datos de tu base de datos:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gestion_academica_vergel
spring.datasource.username=postgres
spring.datasource.password=TU_CONTRASEÑA
```

Las tablas se crean solas al arrancar (`spring.jpa.hibernate.ddl-auto=update`).

Los **umbrales del motor de alertas** también se configuran aquí (los puede ajustar la institución sin tocar el código):

```properties
alert.threshold.passing-grade=3.0            # nota mínima aprobatoria
alert.threshold.min-subjects-at-risk=2       # materias perdidas para alertar
alert.threshold.min-attendance-percent=80.0  # % mínimo de asistencia
```

---

## ▶️ Cómo ejecutar

```bash
# Linux / Mac
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

Luego abre en el navegador:

- **Interfaz web:** http://localhost:8080/
- **Swagger UI (API):** http://localhost:8080/swagger-ui.html

---

## 🧱 Arquitectura (capas)

```
controller/   → reciben las peticiones HTTP y devuelven la respuesta
service/      → lógica de negocio y reglas (aquí vive el motor de alertas)
repository/   → acceso a la base de datos (Spring Data JPA)
model/
 ├─ entity/   → entidades (tablas)
 ├─ Dto/      → objetos de entrada/salida de la API
 └─ shared/   → enumeraciones (roles, estados, tipos de alerta…)
mapper/       → conversión entidad ⇄ DTO (MapStruct)
config/       → seguridad (JWT), Swagger, manejo de errores, reenvío SPA
util/         → utilidades (generación/validación de tokens)
```

---

## 🗃️ Entidades (10)

| Entidad | Qué representa |
|---------|----------------|
| `Students` | Estudiante (datos personales, acceso, acudiente, matrículas) |
| `Parent` | Acudiente responsable del estudiante |
| `Professors` | Docente (dicta materias, administra el sistema) |
| `Grade` | Grado o curso |
| `Subject` | Materia (pertenece a un grado y la dicta un docente) |
| `Enrollments` | Matrícula: une estudiante + grado, con estado y fecha |
| `Note` | Nota de una materia en una matrícula y periodo |
| `Attendance` | Registro de asistencia (presente/ausente/tarde/excusa) |
| `Alert` | Alerta temprana generada por el motor |
| `Followup` | Acción de seguimiento del docente sobre una alerta |

---

## 🌐 Principales endpoints (55 en total)

| Recurso | Ruta base |
|---------|-----------|
| Autenticación | `POST /api/auth/login` |
| Estudiantes | `/api/students` |
| Acudientes | `/api/parents` |
| Docentes | `/api/professors` |
| Grados | `/api/grades` |
| Materias | `/api/subjects` |
| Matrículas | `/api/enrollments` |
| Notas | `/api/notes` |
| Asistencia | `/api/attendances` |
| **Alertas** | `/api/alerts` · `POST /api/alerts/evaluate` |
| **Seguimiento** | `/api/followups` |

El catálogo completo (método, ruta y rol requerido) está en **Swagger UI**.

---

## 🚦 Cómo funciona el motor de alertas

1. Se ejecuta con `POST /api/alerts/evaluate` (todas las matrículas) o `POST /api/alerts/evaluate/{enrollmentId}` (una sola).
2. Para cada estudiante toma sus **notas** y su **asistencia** y aplica las tres reglas configurables.
3. Crea una **alerta** con su gravedad (ALTA/MEDIA/BAJA) y un mensaje explicativo. No duplica alertas abiertas del mismo tipo.
4. El docente registra el **seguimiento** (`POST /api/followups`); al hacerlo, la alerta pasa de *Abierta* a *En proceso*, y se cierra en *Resuelta*.

---

## 🔐 Seguridad

- El login devuelve un **token JWT** que el cliente envía en cada petición (`Authorization: Bearer ...`).
- Un filtro valida el token **antes** de llegar a cualquier controlador.
- Las contraseñas se guardan cifradas con BCrypt; nunca en texto plano.

---

## 🖥️ Frontend

La interfaz web (Angular) viene **compilada dentro de** `src/main/resources/static/`, por lo que el backend la sirve automáticamente en `http://localhost:8080/`. El código fuente del frontend está en el proyecto `frontend_seguimiento_academico_vergel`.

---

## 👤 Autor

Luis Andrés Rojas Acevedo — Trabajo de grado, Ingeniería de Sistemas, Corporación Universitaria Minuto de Dios.
