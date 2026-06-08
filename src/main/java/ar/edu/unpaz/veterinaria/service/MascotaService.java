package ar.edu.unpaz.veterinaria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.edu.unpaz.veterinaria.model.Mascota;
import ar.edu.unpaz.veterinaria.repository.MascotaRepository;

/**
 * Servicio de aplicacion para mascotas.
 */
@Service
public class MascotaService {

	private final MascotaRepository mascotaRepository;

	public MascotaService(MascotaRepository mascotaRepository) {
		this.mascotaRepository = mascotaRepository;
	}

	public List<Mascota> listar() {
		return mascotaRepository.findAll();
	}

	public Optional<Mascota> buscarPorId(Long id) {
		return mascotaRepository.findById(id);
	}

	public List<Mascota> buscarPorNombre(String nombre) {
		return mascotaRepository.findByNombreContainingIgnoreCase(nombre);
	}

	public List<Mascota> buscarPorEspecie(String especie) {
		return mascotaRepository.findByEspecieIgnoreCase(especie);
	}

	public List<Mascota> buscarPorDuenio(Long duenioId) {
		return mascotaRepository.buscarPorDuenio(duenioId);
	}

	public Mascota guardar(Mascota mascota) {
		if (mascota.getNombre() == null || mascota.getNombre().isBlank()) {
			throw new IllegalArgumentException("El nombre de la mascota es obligatorio");
		}
		if (mascota.getDuenio() == null || mascota.getDuenio().getId() == null) {
			throw new IllegalArgumentException("La mascota debe tener un duenio existente");
		}
		mascota.setEspecie(normalizar(mascota.getEspecie()));
		return mascotaRepository.save(mascota);
	}

	public void eliminar(Long id) {
		mascotaRepository.deleteById(id);
	}

	private String normalizar(String valor) {
		if (valor == null) {
			return "";
		}
		return valor.trim().toUpperCase();
	}
}
