package ar.edu.unpaz.veterinaria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.edu.unpaz.veterinaria.model.Mascota;

/**
 * Repositorio JPA para mascotas.
 *
 * JpaRepository<Mascota, Long> indica que este repositorio administra entidades
 * Mascota y que el id de la entidad es de tipo Long.
 *
 * Al extender JpaRepository, Spring Data JPA da metodos CRUD listos sin tener
 * que escribir SQL manual para operaciones basicas.
 *
 * Tambien se muestran tres tipos de consultas vistas en clase:
 * - Consulta derivada: findByNombreContainingIgnoreCase(...) y findByEspecieIgnoreCase(...).
 * - Consulta JPQL: buscarPorDuenio(...), consulta la entidad Mascota y la relacion con Duenio.
 * - Consulta SQL nativa: contarPorEspecieNativa(...), consulta directamente la tabla mascota.
 */
public interface MascotaRepository extends JpaRepository<Mascota, Long> {

	List<Mascota> findByNombreContainingIgnoreCase(String nombre);

	List<Mascota> findByEspecieIgnoreCase(String especie);

	@Query("select m from Mascota m where m.duenio.id = :duenioId")
	List<Mascota> buscarPorDuenio(@Param("duenioId") Long duenioId);

	@Query(value = "select count(*) from mascota where especie = :especie", nativeQuery = true)
	long contarPorEspecieNativa(@Param("especie") String especie);
}
