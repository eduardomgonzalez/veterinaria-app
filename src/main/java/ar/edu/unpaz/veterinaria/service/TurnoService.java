package ar.edu.unpaz.veterinaria.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import ar.edu.unpaz.veterinaria.model.EstadoTurno;
import ar.edu.unpaz.veterinaria.model.Turno;
import ar.edu.unpaz.veterinaria.repository.TurnoRepository;
import ar.edu.unpaz.veterinaria.strategy.CostoConsultaStrategy;

/**
 * Servicio de aplicacion para turnos.
 *
 * Antes de persistir un turno calcula el costo estimado delegando la regla en
 * una estrategia. Esto separa la regla de negocio del modelo y del controlador.
 */
@Service
public class TurnoService extends AbstractCrudService<Turno> {

	private final TurnoRepository turnoRepository;
	private final CostoConsultaStrategy costoConsultaStrategy;

	public TurnoService(TurnoRepository turnoRepository, CostoConsultaStrategy costoConsultaStrategy) {
		this.turnoRepository = turnoRepository;
		this.costoConsultaStrategy = costoConsultaStrategy;
	}

	@Override
	protected JpaRepository<Turno, Long> getRepository() {
		return turnoRepository;
	}

	public List<Turno> buscarPorEstado(EstadoTurno estado) {
		return turnoRepository.findByEstado(estado);
	}

	public List<Turno> buscarProximos() {
		return turnoRepository.buscarProximos(LocalDateTime.now());
	}

	@Override
	protected void antesDeGuardar(Turno turno) {
		if (turno.getMascota() == null || turno.getMascota().getId() == null) {
			throw new IllegalArgumentException("El turno debe estar asociado a una mascota existente");
		}
		if (turno.getFechaHora() == null) {
			throw new IllegalArgumentException("La fecha y hora del turno es obligatoria");
		}
		turno.setCostoEstimado(costoConsultaStrategy.calcular(turno));
	}
}
