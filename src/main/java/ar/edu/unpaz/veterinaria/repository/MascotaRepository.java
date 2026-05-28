package ar.edu.unpaz.veterinaria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.edu.unpaz.veterinaria.model.Especie;
import ar.edu.unpaz.veterinaria.model.Mascota;

/**
 * Repositorio JPA para mascotas.
 *
 * Incluye ejemplos de consultas derivadas, JPQL y SQL nativo para poder
 * defender las variantes vistas en clase.
 */
public interface MascotaRepository extends JpaRepository<Mascota, Long> {

	List<Mascota> findByNombreContainingIgnoreCase(String nombre);

	List<Mascota> findByEspecie(Especie especie);

	@Query("select m from Mascota m where m.duenio.id = :duenioId")
	List<Mascota> buscarPorDuenio(@Param("duenioId") Long duenioId);

	@Query(value = "select count(*) from mascota where especie = :especie", nativeQuery = true)
	long contarPorEspecieNativa(@Param("especie") String especie);
}
