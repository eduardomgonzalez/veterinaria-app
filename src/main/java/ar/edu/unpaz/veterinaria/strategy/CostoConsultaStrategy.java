package ar.edu.unpaz.veterinaria.strategy;

import ar.edu.unpaz.veterinaria.model.Turno;

/**
 * Interfaz del patrón Strategy para calcular costos de atención.
 *
 * Permite cambiar la regla de cálculo sin modificar la entidad {@link Turno}
 * ni el servicio que la utiliza.
 */
public interface CostoConsultaStrategy {

	/**
	 * Indica que motivo de consulta resuelve esta estrategia.
	 *
	 * @return motivo esperado en el turno
	 */
	String getMotivo();

	/**
	 * Calcula el costo estimado para un turno.
	 *
	 * @param turno turno a evaluar
	 * @return costo estimado de atencion
	 */
	double calcular(Turno turno);
}
