package ar.edu.unpaz.veterinaria.strategy;

import org.springframework.stereotype.Component;

import ar.edu.unpaz.veterinaria.model.Turno;

/**
 * Estrategia para calcular el costo de una consulta de control general.
 */
@Component
public class CostoControlStrategy implements CostoConsultaStrategy {

	@Override
	public String getMotivo() {
		return "CONTROL";
	}

	@Override
	public double calcular(Turno turno) {
		return 5000;
	}
}
