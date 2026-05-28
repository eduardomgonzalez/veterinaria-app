package ar.edu.unpaz.veterinaria.strategy;

import org.springframework.stereotype.Component;

import ar.edu.unpaz.veterinaria.model.MotivoConsulta;
import ar.edu.unpaz.veterinaria.model.Turno;

/**
 * Estrategia concreta de calculo de costos.
 *
 * Asigna un importe base segun el motivo de consulta. En una version futura se
 * podria reemplazar por otra estrategia sin cambiar la capa de servicio.
 */
@Component
public class CostoConsultaBaseStrategy implements CostoConsultaStrategy {

	@Override
	public double calcular(Turno turno) {
		MotivoConsulta motivo = turno.getMotivo();

		if (motivo == MotivoConsulta.URGENCIA) {
			return 12000;
		}
		if (motivo == MotivoConsulta.VACUNACION) {
			return 8000;
		}
		if (motivo == MotivoConsulta.PELUQUERIA) {
			return 7000;
		}
		return 5000;
	}
}
