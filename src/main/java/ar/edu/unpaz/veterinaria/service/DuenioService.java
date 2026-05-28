package ar.edu.unpaz.veterinaria.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import ar.edu.unpaz.veterinaria.model.Duenio;
import ar.edu.unpaz.veterinaria.repository.DuenioRepository;

/**
 * Servicio de aplicacion para operaciones de duenios.
 */
@Service
public class DuenioService extends AbstractCrudService<Duenio> {

	private final DuenioRepository duenioRepository;

	public DuenioService(DuenioRepository duenioRepository) {
		this.duenioRepository = duenioRepository;
	}

	@Override
	protected JpaRepository<Duenio, Long> getRepository() {
		return duenioRepository;
	}

	public List<Duenio> buscarPorNombre(String nombre) {
		return duenioRepository.findByNombreContainingIgnoreCase(nombre);
	}

	@Override
	protected void antesDeGuardar(Duenio duenio) {
		if (duenio.getNombre() == null || duenio.getNombre().isBlank()) {
			throw new IllegalArgumentException("El nombre del duenio es obligatorio");
		}
	}
}
