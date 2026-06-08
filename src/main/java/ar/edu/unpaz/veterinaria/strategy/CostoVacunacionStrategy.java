package ar.edu.unpaz.veterinaria.strategy;

import org.springframework.stereotype.Component;

import ar.edu.unpaz.veterinaria.model.Turno;

/**
 * Estrategia para calcular el costo de un turno de vacunacion.
 */
@Component
public class CostoVacunacionStrategy implements CostoConsultaStrategy {

	@Override
	public String getMotivo() {
		return "VACUNACION";
	}

	@Override
	public double calcular(Turno turno) {
		return 8000;
	}
}
