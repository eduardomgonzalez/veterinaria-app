package ar.edu.unpaz.veterinaria.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * Turno de atencion solicitado para una mascota.
 *
 * El turno registra fecha, motivo, estado, observaciones y costo estimado. El
 * costo se calcula en la capa de servicio mediante una estrategia configurable.
 */
@Entity
public class Turno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime fechaHora;
	private String motivo;
	private String estado = "PENDIENTE";
	@ManyToOne
	private Mascota mascota;

	private String observacion;
	private double costoEstimado;

	protected Turno() {
	}

	public Turno(LocalDateTime fechaHora, String motivo, Mascota mascota, String observacion) {
		this.fechaHora = fechaHora;
		this.motivo = motivo;
		this.mascota = mascota;
		this.observacion = observacion;
	}

	public Long getId() {
		return id;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Mascota getMascota() {
		return mascota;
	}

	public void setMascota(Mascota mascota) {
		this.mascota = mascota;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public double getCostoEstimado() {
		return costoEstimado;
	}

	public void setCostoEstimado(double costoEstimado) {
		this.costoEstimado = costoEstimado;
	}

	/**
	 * Cambia el estado del turno a confirmado.
	 */
	public void confirmar() {
		this.estado = "CONFIRMADO";
	}

	/**
	 * Cancela el turno cuando ya no sera atendido.
	 */
	public void cancelar() {
		this.estado = "CANCELADO";
	}
}
