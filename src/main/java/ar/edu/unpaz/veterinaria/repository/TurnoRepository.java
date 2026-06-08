package ar.edu.unpaz.veterinaria.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.edu.unpaz.veterinaria.model.Turno;

/**
 * Repositorio JPA para turnos veterinarios.
 * 
 * JpaRepository<Turno, Long> indica que este repositorio administra entidades
 * Turno y que el id de la entidad es de tipo Long.
 *
 * Al extender JpaRepository, Spring Data JPA da metodos listos como:
 * findAll(), findById(), save() y deleteById().
 *
 * Tambien se muestran tres tipos de consultas vistas en clase:
 * - Consulta derivada: findByEstadoIgnoreCase(...), creada por el nombre del metodo.
 * - Consulta JPQL: buscarProximos(...), consulta la entidad Turno y su atributo fechaHora.
 * - Consulta SQL nativa: contarPendientesNativo(), consulta directamente la tabla turno.
 */
public interface TurnoRepository extends JpaRepository<Turno, Long> {

	List<Turno> findByEstadoIgnoreCase(String estado);

	@Query("select t from Turno t where t.fechaHora >= :desde order by t.fechaHora asc")
	List<Turno> buscarProximos(@Param("desde") LocalDateTime desde);

	@Query(value = "select count(*) from turno where estado = 'PENDIENTE'", nativeQuery = true)
	long contarPendientesNativo();
}
