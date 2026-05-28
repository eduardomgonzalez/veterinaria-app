package ar.edu.unpaz.veterinaria.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import ar.edu.unpaz.veterinaria.model.Especie;
import ar.edu.unpaz.veterinaria.model.Mascota;
import ar.edu.unpaz.veterinaria.repository.MascotaRepository;

/**
 * Servicio de aplicacion para mascotas.
 */
@Service
public class MascotaService extends AbstractCrudService<Mascota> {

	private final MascotaRepository mascotaRepository;

	public MascotaService(MascotaRepository mascotaRepository) {
		this.mascotaRepository = mascotaRepository;
	}

	@Override
	protected JpaRepository<Mascota, Long> getRepository() {
		return mascotaRepository;
	}

	public List<Mascota> buscarPorNombre(String nombre) {
		return mascotaRepository.findByNombreContainingIgnoreCase(nombre);
	}

	public List<Mascota> buscarPorEspecie(Especie especie) {
		return mascotaRepository.findByEspecie(especie);
	}

	public List<Mascota> buscarPorDuenio(Long duenioId) {
		return mascotaRepository.buscarPorDuenio(duenioId);
	}

	@Override
	protected void antesDeGuardar(Mascota mascota) {
		if (mascota.getNombre() == null || mascota.getNombre().isBlank()) {
			throw new IllegalArgumentException("El nombre de la mascota es obligatorio");
		}
		if (mascota.getDuenio() == null || mascota.getDuenio().getId() == null) {
			throw new IllegalArgumentException("La mascota debe tener un duenio existente");
		}
	}
}
