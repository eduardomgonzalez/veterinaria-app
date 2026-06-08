package ar.edu.unpaz.veterinaria.model;

import java.time.LocalDate;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * Abstraccion de un paciente atendido por la veterinaria.
 *
 * En esta primera version solo existe la entidad concreta {@link Mascota}, pero
 * la abstraccion deja claro que el sistema trabaja con pacientes veterinarios y
 * permite explicar herencia y clases abstractas.
 * 
 * MappedSuperclass dice que esta clase no va a tener una tabla propia, 
 * pero sus atributos se heredan y se mapean en la tabla de las clases hijas.
 * 
 * Es abstracta porque no quiero crear objetos PacienteVeterinario directamente.
 * Quiero crear objetos concretos como Mascota.
 */
@MappedSuperclass
public abstract class PacienteVeterinario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;
	private LocalDate fechaNacimiento;

	protected PacienteVeterinario() {
	}

	protected PacienteVeterinario(String nombre, LocalDate fechaNacimiento) {
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
	}

	public Long getId() {
		return id;
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
