# Plan de authorities por rol (propuesta)

Este plan está diseñado para tu backend de *Virtual Classroom* considerando los roles actuales (`ROLE_ADMIN`, `ROLE_TEACHER`, `ROLE_STUDENT`) y el tipo de componentes expuestos en los controladores del proyecto.

## Criterios usados

1. **Mínimo privilegio**: cada rol solo recibe lo necesario para su trabajo.
2. **Separación de responsabilidades**: el *admin* configura, el *teacher* opera docencia, el *student* consume y participa.
3. **Escalabilidad**: authorities agrupadas por dominio para facilitar `@PreAuthorize` y mantenimiento.

## Matriz de acceso sugerida

| Componente / módulo | Authorities sugeridas | ROLE_ADMIN | ROLE_TEACHER | ROLE_STUDENT |
|---|---|---|---|---|
| **Autenticación** (`AuthController`) | `AUTH_LOGIN`, `AUTH_REFRESH`, `AUTH_LOGOUT` | ✅ | ✅ | ✅ |
| **Usuarios** (`UserController`) | `USER_READ`, `USER_WRITE`, `USER_DISABLE` | ✅ completo | 🔒 solo su propio perfil (`USER_SELF_READ`, `USER_SELF_WRITE`) | 🔒 solo su propio perfil (`USER_SELF_READ`, `USER_SELF_WRITE`) |
| **Administradores** (`AdminController`) | `ADMIN_READ`, `ADMIN_WRITE` | ✅ | ❌ | ❌ |
| **Docentes** (`TeacherController`) | `TEACHER_READ`, `TEACHER_WRITE` | ✅ | ✅ (lectura global + edición propia) | 🔍 lectura limitada |
| **Estudiantes** (`StudentController`) | `STUDENT_READ`, `STUDENT_WRITE` | ✅ | ✅ (lectura global + observaciones académicas) | ✅ edición propia |
| **Cursos** (`CourseController`) | `COURSE_READ`, `COURSE_WRITE` | ✅ | ✅ (crear/actualizar temario) | ✅ solo lectura |
| **Semestres** (`SemesterController`) | `SEMESTER_READ`, `SEMESTER_WRITE` | ✅ | ✅ lectura | ✅ lectura |
| **Turnos** (`ShiftController`) | `SHIFT_READ`, `SHIFT_WRITE` | ✅ | ✅ lectura | ✅ lectura |
| **Sede/Universidad** (`UniversityController`) | `UNIVERSITY_READ`, `UNIVERSITY_WRITE` | ✅ | ✅ lectura | ✅ lectura |
| **Programación de cursos** (`ScheduledCourseController`) | `SCHEDULE_READ`, `SCHEDULE_WRITE`, `SCHEDULE_ASSIGN_TEACHER` | ✅ | ✅ gestión de sus secciones | ✅ lectura de su malla |
| **Matrículas** (`EnrollmentController`) | `ENROLLMENT_READ`, `ENROLLMENT_WRITE`, `ENROLLMENT_APPROVE` | ✅ | ✅ lectura y validación académica | ✅ crear y ver sus matrículas |
| **Detalle de matrícula** (`EnrollmentDetailController`) | `ENROLLMENT_DETAIL_READ`, `ENROLLMENT_DETAIL_WRITE` | ✅ | ✅ lectura y ajustes docentes | ✅ lectura propia |
| **Cupones/beneficios** (`CouponController`) | `COUPON_READ`, `COUPON_WRITE`, `COUPON_APPLY` | ✅ administración completa | ✅ aplicar/ver vigentes | ✅ aplicar/ver propios |

## Catálogo base recomendado de authorities

### 1) Seguridad y administración
- `AUTH_LOGIN`, `AUTH_REFRESH`, `AUTH_LOGOUT`
- `USER_READ`, `USER_WRITE`, `USER_DISABLE`, `USER_SELF_READ`, `USER_SELF_WRITE`
- `ADMIN_READ`, `ADMIN_WRITE`

### 2) Académico
- `TEACHER_READ`, `TEACHER_WRITE`
- `STUDENT_READ`, `STUDENT_WRITE`
- `COURSE_READ`, `COURSE_WRITE`
- `SEMESTER_READ`, `SEMESTER_WRITE`
- `SHIFT_READ`, `SHIFT_WRITE`
- `UNIVERSITY_READ`, `UNIVERSITY_WRITE`
- `SCHEDULE_READ`, `SCHEDULE_WRITE`, `SCHEDULE_ASSIGN_TEACHER`

### 3) Matrícula y pagos
- `ENROLLMENT_READ`, `ENROLLMENT_WRITE`, `ENROLLMENT_APPROVE`
- `ENROLLMENT_DETAIL_READ`, `ENROLLMENT_DETAIL_WRITE`
- `COUPON_READ`, `COUPON_WRITE`, `COUPON_APPLY`

## Asignación mínima por rol

- **ROLE_ADMIN**: todas las authorities.
- **ROLE_TEACHER**:
  - Lectura académica amplia.
  - Escritura en programación de cursos, notas/seguimiento y operaciones docentes.
  - Sin acceso a administración de usuarios global (salvo perfil propio).
- **ROLE_STUDENT**:
  - Lectura de catálogo académico.
  - Escritura solo en su perfil y procesos de matrícula propios.
  - Sin administración de catálogos maestros.

## Recomendación de implementación incremental

1. Definir authorities en BD (`authority`, `role_authority`) y versionarlas en script SQL.
2. Activar `.anyRequest().authenticated()` en seguridad y reemplazar `permitAll()` por reglas por endpoint.
3. Añadir `@PreAuthorize("hasAuthority('X')")` en métodos sensibles.
4. Incorporar authorities de tipo `*_SELF_*` con validación de identidad (owner-check).
5. Cubrir con pruebas de autorización por rol (admin/teacher/student).

