package ar.edu.unpaz.veterinaria.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.edu.unpaz.veterinaria.model.EstadoTurno;
import ar.edu.unpaz.veterinaria.model.Turno;

/**
 * Repositorio JPA para turnos veterinarios.
 */
public interface TurnoRepository extends JpaRepository<Turno, Long> {

	List<Turno> findByEstado(EstadoTurno estado);

	@Query("select t from Turno t where t.fechaHora >= :desde order by t.fechaHora asc")
	List<Turno> buscarProximos(@Param("desde") LocalDateTime desde);

	@Query(value = "select count(*) from turno where estado = 'PENDIENTE'", nativeQuery = true)
	long contarPendientesNativo();
}
