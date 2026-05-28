package ar.edu.unpaz.veterinaria.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * Clase abstracta base para entidades persistibles.
 *
 * Centraliza el identificador tecnico que comparten las entidades del dominio.
 * Al usar {@code @MappedSuperclass}, JPA copia el campo {@code id} en las
 * tablas concretas sin crear una tabla propia para esta clase.
 */
@MappedSuperclass
public abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Obtiene el identificador generado por la base de datos.
	 *
	 * @return id tecnico de la entidad
	 */
	public Long getId() {
		return id;
	}
}
