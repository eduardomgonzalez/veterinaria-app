package ar.edu.unpaz.veterinaria.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;

/**
 * Turno de atencion solicitado para una mascota.
 *
 * El turno registra fecha, motivo, estado, observaciones y costo estimado. El
 * costo se calcula en la capa de servicio mediante una estrategia configurable.
 */
@Entity
public class Turno extends BaseEntity {

	private LocalDateTime fechaHora;

	@Enumerated(EnumType.STRING)
	private MotivoConsulta motivo;

	@Enumerated(EnumType.STRING)
	private EstadoTurno estado = EstadoTurno.PENDIENTE;

	@ManyToOne
	private Mascota mascota;

	private String observacion;
	private double costoEstimado;

	protected Turno() {
	}

	public Turno(LocalDateTime fechaHora, MotivoConsulta motivo, Mascota mascota, String observacion) {
		this.fechaHora = fechaHora;
		this.motivo = motivo;
		this.mascota = mascota;
		this.observacion = observacion;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public MotivoConsulta getMotivo() {
		return motivo;
	}

	public void setMotivo(MotivoConsulta motivo) {
		this.motivo = motivo;
	}

	public EstadoTurno getEstado() {
		return estado;
	}

	public void setEstado(EstadoTurno estado) {
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
		this.estado = EstadoTurno.CONFIRMADO;
	}

	/**
	 * Cancela el turno cuando ya no sera atendido.
	 */
	public void cancelar() {
		this.estado = EstadoTurno.CANCELADO;
	}
}
