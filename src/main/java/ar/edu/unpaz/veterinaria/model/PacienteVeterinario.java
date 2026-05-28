package ar.edu.unpaz.veterinaria.model;

import java.time.LocalDate;

import jakarta.persistence.MappedSuperclass;

/**
 * Abstraccion de un paciente atendido por la veterinaria.
 *
 * En esta primera version solo existe la entidad concreta {@link Mascota}, pero
 * la abstraccion deja claro que el sistema trabaja con pacientes veterinarios y
 * permite explicar herencia y clases abstractas.
 */
@MappedSuperclass
public abstract class PacienteVeterinario extends BaseEntity {

	private String nombre;
	private LocalDate fechaNacimiento;

	protected PacienteVeterinario() {
	}

	protected PacienteVeterinario(String nombre, LocalDate fechaNacimiento) {
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
}
