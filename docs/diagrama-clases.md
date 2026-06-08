# Diagrama de clases - Veterinaria App

Este diagrama muestra las clases principales del sistema, sus relaciones y los
conceptos de POO aplicados: herencia, clases abstractas, interfaces,
polimorfismo y patron Strategy.

> Este archivo representa un diagrama de clases.

```mermaid
classDiagram
direction LR

class Persona {
  <<abstract>>
  -Long id
  -String nombre
  -String telefono
  -String email
  +Long getId()
  +String getNombre()
  +void setNombre(String nombre)
  +String getTelefono()
  +void setTelefono(String telefono)
  +String getEmail()
  +void setEmail(String email)
}

class Duenio {
  -String dni
  -String direccion
  -List~Mascota~ mascotas
  +String getDni()
  +void setDni(String dni)
  +String getDireccion()
  +void setDireccion(String direccion)
  +List~Mascota~ getMascotas()
}

class PacienteVeterinario {
  <<abstract>>
  -Long id
  -String nombre
  -LocalDate fechaNacimiento
  +Long getId()
  +String getNombre()
  +void setNombre(String nombre)
  +LocalDate getFechaNacimiento()
  +void setFechaNacimiento(LocalDate fechaNacimiento)
}

class Mascota {
  -String especie
  -String raza
  -double peso
  -Duenio duenio
  +String getEspecie()
  +void setEspecie(String especie)
  +String getRaza()
  +void setRaza(String raza)
  +double getPeso()
  +void setPeso(double peso)
  +Duenio getDuenio()
  +void setDuenio(Duenio duenio)
}

class Turno {
  -Long id
  -LocalDateTime fechaHora
  -String motivo
  -String estado
  -Mascota mascota
  -String observacion
  -double costoEstimado
  +Long getId()
  +LocalDateTime getFechaHora()
  +void setFechaHora(LocalDateTime fechaHora)
  +String getMotivo()
  +void setMotivo(String motivo)
  +String getEstado()
  +void setEstado(String estado)
  +Mascota getMascota()
  +void setMascota(Mascota mascota)
  +double getCostoEstimado()
  +void setCostoEstimado(double costoEstimado)
  +void confirmar()
  +void cancelar()
}

Persona <|-- Duenio : herencia
PacienteVeterinario <|-- Mascota : herencia
Duenio "1" o-- "*" Mascota : tiene
Turno "*" --> "1" Mascota : se agenda para

class CostoConsultaStrategy {
  <<interface>>
  +String getMotivo()
  +double calcular(Turno turno)
}

class CostoControlStrategy {
  +String getMotivo()
  +double calcular(Turno turno)
}

class CostoVacunacionStrategy {
  +String getMotivo()
  +double calcular(Turno turno)
}

class CostoUrgenciaStrategy {
  +String getMotivo()
  +double calcular(Turno turno)
}

class CostoPeluqueriaStrategy {
  +String getMotivo()
  +double calcular(Turno turno)
}

CostoConsultaStrategy <|.. CostoControlStrategy : implementa
CostoConsultaStrategy <|.. CostoVacunacionStrategy : implementa
CostoConsultaStrategy <|.. CostoUrgenciaStrategy : implementa
CostoConsultaStrategy <|.. CostoPeluqueriaStrategy : implementa
TurnoService --> CostoConsultaStrategy : usa Strategy
TurnoService --> Turno : calcula costo

note for CostoConsultaStrategy "Patron Strategy: cada implementacion calcula el costo segun el motivo del turno."

class DuenioController {
  -DuenioService duenioService
  +List~Duenio~ listar(String nombre)
  +ResponseEntity~Duenio~ buscar(Long id)
  +ResponseEntity~Duenio~ crear(Duenio duenio)
  +ResponseEntity~Duenio~ actualizar(Long id, Duenio datos)
  +ResponseEntity~Void~ eliminar(Long id)
}

class MascotaController {
  -MascotaService mascotaService
  -DuenioService duenioService
  +List~Mascota~ listar(String nombre, String especie, Long duenioId)
  +ResponseEntity~Mascota~ buscar(Long id)
  +ResponseEntity~Mascota~ crear(MascotaRequest request)
  +ResponseEntity~Mascota~ actualizar(Long id, MascotaRequest request)
  +ResponseEntity~Void~ eliminar(Long id)
}

class TurnoController {
  -TurnoService turnoService
  -MascotaService mascotaService
  +List~Turno~ listar(String estado, boolean proximos)
  +ResponseEntity~Turno~ buscar(Long id)
  +ResponseEntity~Turno~ crear(TurnoRequest request)
  +ResponseEntity~Turno~ actualizar(Long id, TurnoRequest request)
  +ResponseEntity~Void~ eliminar(Long id)
}

class DuenioService {
  -DuenioRepository duenioRepository
  +List~Duenio~ listar()
  +Optional~Duenio~ buscarPorId(Long id)
  +Duenio guardar(Duenio duenio)
  +void eliminar(Long id)
  +List~Duenio~ buscarPorNombre(String nombre)
}

class MascotaService {
  -MascotaRepository mascotaRepository
  +List~Mascota~ listar()
  +Optional~Mascota~ buscarPorId(Long id)
  +Mascota guardar(Mascota mascota)
  +void eliminar(Long id)
  +List~Mascota~ buscarPorNombre(String nombre)
  +List~Mascota~ buscarPorEspecie(String especie)
  +List~Mascota~ buscarPorDuenio(Long duenioId)
}

class TurnoService {
  -TurnoRepository turnoRepository
  -List~CostoConsultaStrategy~ estrategiasCosto
  +List~Turno~ listar()
  +Optional~Turno~ buscarPorId(Long id)
  +List~Turno~ buscarPorEstado(String estado)
  +List~Turno~ buscarProximos()
  +Turno guardar(Turno turno)
  +void eliminar(Long id)
  -CostoConsultaStrategy buscarEstrategia(String motivo)
}

DuenioController --> DuenioService : delega
MascotaController --> MascotaService : delega
MascotaController --> DuenioService : busca duenio
TurnoController --> TurnoService : delega
TurnoController --> MascotaService : busca mascota

class JpaRepository {
  <<interface>>
  +findAll()
  +findById()
  +save()
  +deleteById()
}

class DuenioRepository {
  <<interface>>
  +Optional~Duenio~ findByDni(String dni)
  +List~Duenio~ findByNombreContainingIgnoreCase(String nombre)
  +List~Duenio~ buscarConTelefonoCargado()
}

class MascotaRepository {
  <<interface>>
  +List~Mascota~ findByNombreContainingIgnoreCase(String nombre)
  +List~Mascota~ findByEspecieIgnoreCase(String especie)
  +List~Mascota~ buscarPorDuenio(Long duenioId)
  +long contarPorEspecieNativa(String especie)
}

class TurnoRepository {
  <<interface>>
  +List~Turno~ findByEstadoIgnoreCase(String estado)
  +List~Turno~ buscarProximos(LocalDateTime desde)
  +long contarPendientesNativo()
}

JpaRepository <|-- DuenioRepository : extiende
JpaRepository <|-- MascotaRepository : extiende
JpaRepository <|-- TurnoRepository : extiende

DuenioService --> DuenioRepository : usa
MascotaService --> MascotaRepository : usa
TurnoService --> TurnoRepository : usa

DuenioRepository ..> Duenio : persiste
MascotaRepository ..> Mascota : persiste
TurnoRepository ..> Turno : persiste
```

## Lectura rapida

- `Persona` y `PacienteVeterinario` son clases abstractas: representan conceptos generales.
- `Duenio` y `Mascota` son entidades concretas que heredan atributos comunes.
- `Duenio` tiene muchas `Mascota`; cada `Mascota` pertenece a un `Duenio`.
- `Turno` se agenda para una `Mascota`.
- Los controllers reciben solicitudes HTTP y delegan en services.
- Los services contienen la logica de negocio y usan repositories.
- Los repositories extienden `JpaRepository` para persistir datos con JPA/Hibernate.
- `CostoConsultaStrategy` aplica Strategy: el service usa una interfaz y Spring inyecta las implementaciones concretas.
