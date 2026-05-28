package ar.edu.unpaz.veterinaria.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;

/**
 * Paciente concreto atendido en la clinica veterinaria.
 *
 * Hereda de {@link PacienteVeterinario} los datos comunes de todo paciente y
 * agrega informacion propia de una mascota, como especie, raza, peso y duenio.
 */
@Entity
public class Mascota extends PacienteVeterinario {

	@Enumerated(EnumType.STRING)
	private Especie especie;

	private String raza;
	private double peso;

	@ManyToOne
	private Duenio duenio;

	protected Mascota() {
	}

	public Mascota(String nombre, LocalDate fechaNacimiento, Especie especie, String raza, double peso, Duenio duenio) {
		super(nombre, fechaNacimiento);
		this.especie = especie;
		this.raza = raza;
		this.peso = peso;
		this.duenio = duenio;
	}

	public Especie getEspecie() {
		return especie;
	}

	public void setEspecie(Especie especie) {
		this.especie = especie;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public Duenio getDuenio() {
		return duenio;
	}

	public void setDuenio(Duenio duenio) {
		this.duenio = duenio;
	}
}
