package ar.edu.unpaz.veterinaria.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
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
import ar.edu.unpaz.veterinaria.service.DuenioService;

/**
 * Controlador REST para administrar duenios.
 *
 * Recibe solicitudes HTTP, delega la logica en {@link DuenioService} y devuelve
 * respuestas JSON para que el frontend pueda consumirlas.
 */
@RestController
@RequestMapping("/api")
public class DuenioController {

	private final DuenioService duenioService;

	public DuenioController(DuenioService duenioService) {
		this.duenioService = duenioService;
	}

	@GetMapping("/duenios")
	public List<Duenio> listar(@RequestParam(required = false) String nombre) {
		if (nombre != null && !nombre.isBlank()) {
			return duenioService.buscarPorNombre(nombre);
		}
		return duenioService.listar();
	}

	@GetMapping("/duenios/{id}")
	public ResponseEntity<Duenio> buscar(@PathVariable Long id) {
		Optional<Duenio> duenioBuscado = duenioService.buscarPorId(id);

		if (duenioBuscado.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(duenioBuscado.get());
	}

	@PostMapping("/duenios")
	public ResponseEntity<Duenio> crear(@RequestBody Duenio duenio) {
		Duenio duenioGuardado = duenioService.guardar(duenio);
		return ResponseEntity.status(HttpStatus.CREATED).body(duenioGuardado);
	}

	@PutMapping("/duenios/{id}")
	public ResponseEntity<Duenio> actualizar(@PathVariable Long id, @RequestBody Duenio datos) {
		Optional<Duenio> duenioBuscado = duenioService.buscarPorId(id);

		if (duenioBuscado.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Duenio duenio = duenioBuscado.get();
		duenio.setNombre(datos.getNombre());
		duenio.setTelefono(datos.getTelefono());
		duenio.setEmail(datos.getEmail());
		duenio.setDni(datos.getDni());
		duenio.setDireccion(datos.getDireccion());

		return ResponseEntity.ok(duenioService.guardar(duenio));
	}

	@DeleteMapping("/duenios/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		if (duenioService.buscarPorId(id).isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		duenioService.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}
