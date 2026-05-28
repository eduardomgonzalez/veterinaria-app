package ar.edu.unpaz.veterinaria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.edu.unpaz.veterinaria.model.Duenio;

/**
 * Repositorio JPA para acceder a los duenios persistidos.
 */
public interface DuenioRepository extends JpaRepository<Duenio, Long> {

	Optional<Duenio> findByDni(String dni);

	List<Duenio> findByNombreContainingIgnoreCase(String nombre);

	@Query("select d from Duenio d where d.telefono is not null and d.telefono <> ''")
	List<Duenio> buscarConTelefonoCargado();
}
