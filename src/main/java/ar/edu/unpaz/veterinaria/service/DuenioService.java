package ar.edu.unpaz.veterinaria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.edu.unpaz.veterinaria.model.Duenio;
import ar.edu.unpaz.veterinaria.repository.DuenioRepository;

/**
 * Servicio de aplicacion para operaciones de duenios.
 */
@Service
public class DuenioService {

	private final DuenioRepository duenioRepository;

	public DuenioService(DuenioRepository duenioRepository) {
		this.duenioRepository = duenioRepository;
	}

	public List<Duenio> listar() {
		return duenioRepository.findAll();
	}

	public Optional<Duenio> buscarPorId(Long id) {
		return duenioRepository.findById(id);
	}

	public List<Duenio> buscarPorNombre(String nombre) {
		return duenioRepository.findByNombreContainingIgnoreCase(nombre);
	}

	public Duenio guardar(Duenio duenio) {
		if (duenio.getNombre() == null || duenio.getNombre().isBlank()) {
			throw new IllegalArgumentException("El nombre del duenio es obligatorio");
		}
		return duenioRepository.save(duenio);
	}

	public void eliminar(Long id) {
		duenioRepository.deleteById(id);
	}
}
