package ar.edu.unpaz.veterinaria.controller;

import java.time.LocalDateTime;
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

import ar.edu.unpaz.veterinaria.model.Mascota;
import ar.edu.unpaz.veterinaria.model.Turno;
import ar.edu.unpaz.veterinaria.service.MascotaService;
import ar.edu.unpaz.veterinaria.service.TurnoService;

/**
 * Controlador REST para administrar turnos de atencion.
 */
@RestController
@RequestMapping("/api")
public class TurnoController {

	// Variables que son las conexiones del controller con los services.
	private final TurnoService turnoService;
	private final MascotaService mascotaService;

	// Spring automáticamente crea los services y se los pasa al controller. (Inyección de dependencias)
	public TurnoController(TurnoService turnoService, MascotaService mascotaService) {
		this.turnoService = turnoService;
		this.mascotaService = mascotaService;
	}

	@GetMapping("/turnos")
	public List<Turno> listar(@RequestParam(required = false) String estado,
			@RequestParam(defaultValue = "false") boolean proximos) {
		if (estado != null) {
			return turnoService.buscarPorEstado(estado);
		}
		if (proximos) {
			return turnoService.buscarProximos();
		}
		return turnoService.listar();
	}

	@GetMapping("/turnos/{id}")
	public ResponseEntity<Turno> buscar(@PathVariable Long id) {
		Optional<Turno> turnoBuscado = turnoService.buscarPorId(id);

		if (turnoBuscado.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(turnoBuscado.get());
	}

	/**
	 * Recibe el JSON enviado desde el formulario de turnos.
	 *
	 * Spring convierte ese JSON en un {@link TurnoRequest}. Luego el controller
	 * busca la mascota usando el mascotaId recibido, crea el objeto {@link Turno}
	 * y delega el guardado al service.
	 */
	@PostMapping("/turnos")
	public ResponseEntity<Turno> crear(@RequestBody TurnoRequest request) {

		// Busca la mascota. (Ejemplo: mascotaId = 1)
		// Devuelve Optional<Mascota>. Puede haber una mascota o puede no haber nada.
		Optional<Mascota> mascotaBuscada = mascotaService.buscarPorId(request.mascotaId());

		// Si no existe devuelve HTTP 400 Bad Request
		// 400 Bad Request
		// sin body
		if (mascotaBuscada.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		// Si existe
		// Obtiene la mascota		
		Mascota mascota = mascotaBuscada.get();

		// Crea el objeto Turno
		Turno turno = crearTurno(request, mascota);

		/*
		* Llama al service para guardar el turno.
		*
		* Acá usamos la instancia de TurnoService que Spring inyectó
		* mediante el constructor del controller.
		*
		* El service devuelve el objeto Turno que fue guardado.
		*
		*/
		Turno turnoGuardado = turnoService.guardar(turno);

		/*
		* Devuelve HTTP 201 Created porque se creó un turno nuevo.
		* El objeto turnoGuardado se envia en el body de la respuesta.
		  Sería algo así:
		  	Status: 201 Created
		  	Body: turnoGuardado
		*
 		* Como este controller usa @RestController, Spring convierte automáticamente
 		* ese objeto Java a JSON antes de enviarlo al frontend.
		*/
		return ResponseEntity.status(HttpStatus.CREATED).body(turnoGuardado);
	}

	@PutMapping("/turnos/{id}")
	public ResponseEntity<Turno> actualizar(@PathVariable Long id, @RequestBody TurnoRequest request) {
		Optional<Turno> turnoBuscado = turnoService.buscarPorId(id);
		Optional<Mascota> mascotaBuscada = mascotaService.buscarPorId(request.mascotaId());

		if (turnoBuscado.isEmpty() || mascotaBuscada.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Turno turno = turnoBuscado.get();
		Mascota mascota = mascotaBuscada.get();
		turno.setFechaHora(request.fechaHora());
		turno.setMotivo(request.motivo());
		turno.setEstado(request.estado());
		turno.setMascota(mascota);
		turno.setObservacion(request.observacion());

		return ResponseEntity.ok(turnoService.guardar(turno));
	}

	@DeleteMapping("/turnos/{id}")
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
	 * DTO (Data Transfer Object - objeto para transportar datos) simple para recibir datos de turno desde el frontend.
	 *
	 * Se usa porque el frontend manda el id de la mascota, pero la entidad Turno necesita una Mascota real.
	 * 
	 * Aunque las llaves estén vacías, Java genera automáticamente por ser un record:

		fechaHora()
		motivo()
		estado()
		mascotaId()
		observacion()
	 */
	public record TurnoRequest(LocalDateTime fechaHora, String motivo, String estado, Long mascotaId, String observacion) {
	}
}
