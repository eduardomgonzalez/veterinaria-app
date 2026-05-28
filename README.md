# Veterinaria App

Sistema de gestion de clinica veterinaria desarrollado como proyecto integrador
para Laboratorio de Software.

La aplicacion expone una API REST con Spring Boot y tambien incluye una interfaz
web simple en HTML, CSS y JavaScript para realizar CRUD de duenios, mascotas y
turnos.

## Autor

Eduardo Maximiliano Gonzalez

## Tecnologias

- Java 17
- Maven
- Spring Boot 4.0.6
- Spring Web MVC
- Spring Data JPA
- Hibernate
- H2 Database
- HTML, CSS y JavaScript

## Funcionalidades

- CRUD de duenios.
- CRUD de mascotas asociadas a un duenio.
- CRUD de turnos asociados a una mascota.
- Calculo automatico del costo estimado de un turno.
- Carga inicial de datos de ejemplo.
- Consola H2 habilitada para inspeccionar la base de datos.

## Arquitectura

El proyecto esta organizado bajo una arquitectura MVC por capas:

```text
controller -> recibe requests HTTP y devuelve respuestas JSON
service    -> contiene reglas de negocio y coordina operaciones
repository -> accede a datos mediante JpaRepository
model      -> define entidades JPA y reglas simples del dominio
strategy   -> contiene reglas intercambiables de calculo de costos
config     -> carga datos iniciales de prueba
```

## POO y patrones aplicados

- Clases y objetos: entidades `Duenio`, `Mascota` y `Turno`.
- Herencia: `Duenio` hereda de `Persona`; `Mascota` hereda de `PacienteVeterinario`.
- Clases abstractas: `BaseEntity`, `Persona`, `PacienteVeterinario` y `AbstractCrudService`.
- Interfaces: `CostoConsultaStrategy`.
- Polimorfismo: `TurnoService` usa la interfaz `CostoConsultaStrategy`, sin depender de una implementacion concreta.
- Strategy: permite cambiar la forma de calcular costos de turnos.
- Template Method: `AbstractCrudService` define el flujo comun de CRUD y deja ganchos para validaciones especificas.

## API principal

```text
GET    /duenios
POST   /duenios
PUT    /duenios/{id}
DELETE /duenios/{id}

GET    /mascotas
POST   /mascotas
PUT    /mascotas/{id}
DELETE /mascotas/{id}

GET    /turnos
POST   /turnos
PUT    /turnos/{id}
DELETE /turnos/{id}
```

## Base de datos

La aplicacion usa H2 en memoria:

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

## Como ejecutar

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

## Relacion con los requisitos del parcial

El sistema cumple la primera version solicitada para la practica:

- Backend en Java con Spring Boot.
- Arquitectura MVC con controller, service, repository y model.
- Persistencia con JPA, Hibernate y H2.
- CRUD implementado con JpaRepository.
- Frontend simple que consume la API REST.
- Aplicacion de conceptos de POO y patrones de diseno.

El DER y la documentacion tecnica breve quedan como siguientes entregables.
