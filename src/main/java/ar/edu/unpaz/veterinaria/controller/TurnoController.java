package ar.edu.unpaz.veterinaria.controller;

import java.time.LocalDateTime;
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

import ar.edu.unpaz.veterinaria.model.EstadoTurno;
import ar.edu.unpaz.veterinaria.model.Mascota;
import ar.edu.unpaz.veterinaria.model.MotivoConsulta;
import ar.edu.unpaz.veterinaria.model.Turno;
import ar.edu.unpaz.veterinaria.service.MascotaService;
import ar.edu.unpaz.veterinaria.service.TurnoService;

/**
 * Controlador REST para administrar turnos de atencion.
 */
@RestController
@RequestMapping("/turnos")
public class TurnoController {

	private final TurnoService turnoService;
	private final MascotaService mascotaService;

	public TurnoController(TurnoService turnoService, MascotaService mascotaService) {
		this.turnoService = turnoService;
		this.mascotaService = mascotaService;
	}

	@GetMapping
	public List<Turno> listar(@RequestParam(required = false) EstadoTurno estado,
			@RequestParam(defaultValue = "false") boolean proximos) {
		if (estado != null) {
			return turnoService.buscarPorEstado(estado);
		}
		if (proximos) {
			return turnoService.buscarProximos();
		}
		return turnoService.listar();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Turno> buscar(@PathVariable Long id) {
		return turnoService.buscarPorId(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Turno> crear(@RequestBody TurnoRequest request) {
		return mascotaService.buscarPorId(request.mascotaId())
				.map(mascota -> ResponseEntity.ok(turnoService.guardar(crearTurno(request, mascota))))
				.orElse(ResponseEntity.badRequest().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Turno> actualizar(@PathVariable Long id, @RequestBody TurnoRequest request) {
		return turnoService.buscarPorId(id)
				.flatMap(turno -> mascotaService.buscarPorId(request.mascotaId())
						.map(mascota -> {
							turno.setFechaHora(request.fechaHora());
							turno.setMotivo(request.motivo());
							turno.setEstado(request.estado());
							turno.setMascota(mascota);
							turno.setObservacion(request.observacion());
							return ResponseEntity.ok(turnoService.guardar(turno));
						}))
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		if (turnoService.buscarPorId(id).isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		turnoService.eliminar(id);
		return ResponseEntity.noContent().build();
	}

	private Turno crearTurno(TurnoRequest request, Mascota mascota) {
		Turno turno = new Turno(request.fechaHora(), request.motivo(), mascota, request.observacion());
		if (request.estado() != null) {
			turno.setEstado(request.estado());
		}
		return turno;
	}

	/**
	 * DTO simple para recibir datos de turno desde el frontend.
	 */
	public record TurnoRequest(LocalDateTime fechaHora, MotivoConsulta motivo, EstadoTurno estado, Long mascotaId, String observacion) {
	}
}
