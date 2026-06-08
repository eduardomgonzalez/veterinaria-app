package ar.edu.unpaz.veterinaria.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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
public class TurnoService {

	// Variable que conecta el service con el repository
	private final TurnoRepository turnoRepository;

	// Esta lista contiene todas las strategies de costo
	private final List<CostoConsultaStrategy> estrategiasCosto;

	/*
		Spring inyecta automaticamente:
			- TurnoRepository, para que el service pueda acceder a la base de datos.
			- List<CostoConsultaStrategy>, con todas las strategies registradas como beans.

		Cada clase que implementa CostoConsultaStrategy y tiene una anotacion de Spring
		queda disponible en esta lista.
	 */
	public TurnoService(TurnoRepository turnoRepository, List<CostoConsultaStrategy> estrategiasCosto) {
		this.turnoRepository = turnoRepository;
		this.estrategiasCosto = estrategiasCosto;
	}

	public List<Turno> listar() {
		return turnoRepository.findAll();
	}

	public Optional<Turno> buscarPorId(Long id) {
		return turnoRepository.findById(id);
	}

	public List<Turno> buscarPorEstado(String estado) {
		return turnoRepository.findByEstadoIgnoreCase(estado);
	}

	public List<Turno> buscarProximos() {
		return turnoRepository.buscarProximos(LocalDateTime.now());
	}

	public Turno guardar(Turno turno) {
		// No permite guardar un turno sin mascota
		if (turno.getMascota() == null || turno.getMascota().getId() == null) {
			throw new IllegalArgumentException("El turno debe estar asociado a una mascota existente");
		}

		// No permite guardar un turno sin fecha/hora
		if (turno.getFechaHora() == null) {
			throw new IllegalArgumentException("La fecha y hora del turno es obligatoria");
		}

		// Normaliza. Eso convierte a mayúscula y limpia espacios
		turno.setMotivo(normalizar(turno.getMotivo()));
		turno.setEstado(normalizarEstado(turno.getEstado()));

		// Calcula el costo, se usa patrón Strategy
		turno.setCostoEstimado(buscarEstrategia(turno.getMotivo()).calcular(turno));

		// Guarda el objeto en la base de datos usando JPA/Hibernate.
		return turnoRepository.save(turno);
	}

	public void eliminar(Long id) {
		turnoRepository.deleteById(id);
	}

	private CostoConsultaStrategy buscarEstrategia(String motivo) {
		/*
			Recorre las strategies disponibles y devuelve la que coincide con el motivo
			del turno. Por ejemplo: CONTROL, VACUNACION, URGENCIA o PELUQUERIA.
		 */
		for (CostoConsultaStrategy estrategia : estrategiasCosto) {
			if (estrategia.getMotivo().equals(motivo)) {
				return estrategia;
			}
		}
		throw new IllegalArgumentException("No existe una estrategia de costo para el motivo: " + motivo);
	}

	private String normalizar(String valor) {
		if (valor == null) {
			return "";
		}
		return valor.trim().toUpperCase();
	}

	private String normalizarEstado(String estado) {
		String estadoNormalizado = normalizar(estado);
		if (estadoNormalizado.isBlank()) {
			return "PENDIENTE";
		}
		return estadoNormalizado;
	}
}
