package ar.edu.unpaz.veterinaria.strategy;

import org.springframework.stereotype.Component;

import ar.edu.unpaz.veterinaria.model.Turno;

/**
 * Estrategia para calcular el costo de un turno de urgencia.
 */
@Component
public class CostoUrgenciaStrategy implements CostoConsultaStrategy {

	@Override
	public String getMotivo() {
		return "URGENCIA";
	}

	@Override
	public double calcular(Turno turno) {
		return 12000;
	}
}
