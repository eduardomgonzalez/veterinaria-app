package ar.edu.unpaz.veterinaria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.edu.unpaz.veterinaria.model.Duenio;

/**
 * Repositorio JPA para acceder a los duenios persistidos.
 *
 * JpaRepository<Duenio, Long> indica que este repositorio administra entidades
 * Duenio y que el id de la entidad es de tipo Long.
 *
 * Al extender JpaRepository, Spring Data JPA da metodos listos como:
 * findAll(), findById(), save() y deleteById().
 *
 * En este repositorio se muestran consultas derivadas y una consulta JPQL:
 * - findByDni(...): consulta derivada que busca por el atributo dni.
 * - findByNombreContainingIgnoreCase(...): consulta derivada que busca por nombre sin importar mayusculas/minusculas.
 * - buscarConTelefonoCargado(): consulta JPQL, porque consulta la entidad Duenio y sus atributos.
 */
public interface DuenioRepository extends JpaRepository<Duenio, Long> {

	Optional<Duenio> findByDni(String dni);

	List<Duenio> findByNombreContainingIgnoreCase(String nombre);

	@Query("select d from Duenio d where d.telefono is not null and d.telefono <> ''")
	List<Duenio> buscarConTelefonoCargado();
}
