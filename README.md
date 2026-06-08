# Veterinaria App

Sistema de gestión de clínica veterinaria desarrollado como proyecto integrador
para Laboratorio de Software.

La aplicación expone una API REST con Spring Boot y también incluye una interfaz
web simple en HTML, CSS y JavaScript para realizar CRUD de dueños, mascotas y
turnos.

## Autor

Eduardo Maximiliano González

## Tecnologías

- Java 17
- Maven
- Spring Boot 4.0.6
- Spring Web MVC
- Spring Data JPA
- Hibernate
- H2 Database
- HTML, CSS y JavaScript

## Funcionalidades

- CRUD de dueños.
- CRUD de mascotas asociadas a un dueño.
- CRUD de turnos asociados a una mascota.
- Cálculo automático del costo estimado de un turno.
- Carga inicial de datos de ejemplo.
- Consola H2 habilitada para inspeccionar la base de datos.

## Arquitectura

El proyecto está organizado bajo una arquitectura MVC por capas:

```text
controller -> recibe requests HTTP y devuelve respuestas JSON
service    -> contiene reglas de negocio y coordina operaciones
repository -> accede a datos mediante JpaRepository
model      -> define entidades JPA y reglas simples del dominio
strategy   -> contiene reglas intercambiables de cálculo de costos
config     -> carga datos iniciales de prueba
```

La aplicación es monolítica: todo el backend vive en un solo proyecto Spring
Boot. Para este parcial es una decisión intencional porque el alcance es un CRUD
académico y permite explicar con claridad el flujo entre controlador, servicio,
repositorio y base de datos.

## POO y patrones aplicados

- Clases y objetos: entidades `Duenio`, `Mascota` y `Turno`.
- Herencia: `Duenio` hereda de `Persona`; `Mascota` hereda de `PacienteVeterinario`.
- Clases abstractas: `Persona` y `PacienteVeterinario`.
- Interfaces: `CostoConsultaStrategy`.
- Polimorfismo: `TurnoService` usa varias implementaciones de `CostoConsultaStrategy`, sin depender de una clase concreta.
- Strategy: cada motivo de consulta tiene su propia clase de cálculo de costo.

No se usan `enum` en el modelo. Los valores como especie, motivo y estado se
guardan como texto simple para mantener el proyecto fácil de leer. La variación
de comportamiento se resuelve con Strategy, por ejemplo en el cálculo del costo
de un turno.

## Consultas JPA incluidas

El proyecto incluye ejemplos simples de consultas vistas en clase:

- Método derivado: `findByNombreContainingIgnoreCase`.
- Consulta JPQL con `@Query`.
- Consulta SQL nativa con `nativeQuery = true`.

## API principal

```text
GET    /api/duenios
POST   /api/duenios
PUT    /api/duenios/{id}
DELETE /api/duenios/{id}

GET    /api/mascotas
POST   /api/mascotas
PUT    /api/mascotas/{id}
DELETE /api/mascotas/{id}

GET    /api/turnos
POST   /api/turnos
PUT    /api/turnos/{id}
DELETE /api/turnos/{id}
```

## Base de datos

La aplicación usa H2 en memoria:

```text
jdbc:h2:mem:veterinaria
```

Consola H2:

```text
http://localhost:8080/h2-console
```

Datos de acceso:

```text
JDBC URL: jdbc:h2:mem:veterinaria
User: sa
Password:
```

## Cómo ejecutar

Con Maven instalado:

```powershell
mvn spring-boot:run
```

En Windows PowerShell o CMD:

```powershell
.\mvnw.cmd spring-boot:run
```

En Linux/macOS:

```bash
./mvnw spring-boot:run
```

La interfaz web queda disponible en:

```text
http://localhost:8080
```

## Relación con los requisitos del parcial

El sistema cumple la primera versión solicitada para la práctica:

- Backend en Java con Spring Boot.
- Arquitectura MVC con controller, service, repository y model.
- Persistencia con JPA, Hibernate y H2.
- CRUD implementado con JpaRepository.
- Frontend simple que consume la API REST.
- Aplicación de conceptos de POO y patrones de diseño.
- Diagrama de clases, DER y documentación técnica breve disponibles en `docs/`.

## Documentación y diagramas

- [`docs/documentacion-tecnica.md`](docs/documentacion-tecnica.md): descripción técnica breve del sistema para GitHub.
- `docs/documentacion-tecnica.pdf`: versión en PDF para presentar o imprimir.
- [`docs/diagrama-clases-veterinaria.png`](docs/diagrama-clases-veterinaria.png): diagrama de clases del sistema.
- [`docs/DER-veterinaria.png`](docs/DER-veterinaria.png): diagrama entidad-relación.

