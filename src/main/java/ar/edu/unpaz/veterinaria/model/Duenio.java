package ar.edu.unpaz.veterinaria.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

/**
 * Persona responsable de una o mas mascotas.
 *
 * Esta entidad participa en una relacion uno-a-muchos con {@link Mascota}: un
 * duenio puede registrar varias mascotas, pero cada mascota pertenece a un
 * duenio principal.
 */
@Entity
public class Duenio extends Persona {

	private String dni;
	private String direccion;

	@JsonIgnore
	@OneToMany(mappedBy = "duenio", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Mascota> mascotas = new ArrayList<>();

	protected Duenio() {
	}

	public Duenio(String nombre, String telefono, String email, String dni, String direccion) {
		super(nombre, telefono, email);
		this.dni = dni;
		this.direccion = direccion;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public List<Mascota> getMascotas() {
		return mascotas;
	}
}
