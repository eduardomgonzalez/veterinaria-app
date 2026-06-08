package ar.edu.unpaz.veterinaria.strategy;

import org.springframework.stereotype.Component;

import ar.edu.unpaz.veterinaria.model.Turno;

/**
 * Estrategia para calcular el costo de un servicio de peluqueria.
 */
@Component
public class CostoPeluqueriaStrategy implements CostoConsultaStrategy {

	@Override
	public String getMotivo() {
		return "PELUQUERIA";
	}

	@Override
	public double calcular(Turno turno) {
		return 7000;
	}
}
