package ar.edu.unpaz.veterinaria.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unpaz.veterinaria.model.Duenio;
import ar.edu.unpaz.veterinaria.model.Especie;
import ar.edu.unpaz.veterinaria.model.Mascota;
import ar.edu.unpaz.veterinaria.service.DuenioService;
import ar.edu.unpaz.veterinaria.service.MascotaService;

/**
 * Controlador REST para el CRUD de mascotas.
 */
@RestController
@RequestMapping("/mascotas")
public class MascotaController {

	private final MascotaService mascotaService;
	private final DuenioService duenioService;

	public MascotaController(MascotaService mascotaService, DuenioService duenioService) {
		this.mascotaService = mascotaService;
		this.duenioService = duenioService;
	}

	@GetMapping
	public List<Mascota> listar(@RequestParam(required = false) String nombre,
			@RequestParam(required = false) Especie especie,
			@RequestParam(required = false) Long duenioId) {
		if (nombre != null && !nombre.isBlank()) {
			return mascotaService.buscarPorNombre(nombre);
		}
		if (especie != null) {
			return mascotaService.buscarPorEspecie(especie);
		}
		if (duenioId != null) {
			return mascotaService.buscarPorDuenio(duenioId);
		}
		return mascotaService.listar();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Mascota> buscar(@PathVariable Long id) {
		return mascotaService.buscarPorId(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Mascota> crear(@RequestBody MascotaRequest request) {
		return duenioService.buscarPorId(request.duenioId())
				.map(duenio -> ResponseEntity.ok(mascotaService.guardar(crearMascota(request, duenio))))
				.orElse(ResponseEntity.badRequest().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Mascota> actualizar(@PathVariable Long id, @RequestBody MascotaRequest request) {
		return mascotaService.buscarPorId(id)
				.flatMap(mascota -> duenioService.buscarPorId(request.duenioId())
						.map(duenio -> {
							mascota.setNombre(request.nombre());
							mascota.setFechaNacimiento(request.fechaNacimiento());
							mascota.setEspecie(request.especie());
							mascota.setRaza(request.raza());
							mascota.setPeso(request.peso());
							mascota.setDuenio(duenio);
							return ResponseEntity.ok(mascotaService.guardar(mascota));
						}))
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		if (mascotaService.buscarPorId(id).isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		mascotaService.eliminar(id);
		return ResponseEntity.noContent().build();
	}

	private Mascota crearMascota(MascotaRequest request, Duenio duenio) {
		return new Mascota(request.nombre(), request.fechaNacimiento(), request.especie(), request.raza(), request.peso(), duenio);
	}

	/**
	 * DTO simple para recibir datos desde el frontend sin exponer el grafo JPA.
	 */
	public record MascotaRequest(String nombre, LocalDate fechaNacimiento, Especie especie, String raza, double peso, Long duenioId) {
	}
}
