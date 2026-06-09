package ar.edu.unpaz.veterinaria.config;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ar.edu.unpaz.veterinaria.model.Duenio;
import ar.edu.unpaz.veterinaria.model.Mascota;
import ar.edu.unpaz.veterinaria.model.Turno;
import ar.edu.unpaz.veterinaria.service.DuenioService;
import ar.edu.unpaz.veterinaria.service.MascotaService;
import ar.edu.unpaz.veterinaria.service.TurnoService;

/**
 * Carga datos de ejemplo al iniciar la aplicacion.
 *
 * Estos datos facilitan probar el CRUD desde el navegador y mostrar la
 * aplicacion sin cargar todo desde cero.
 * 
 * Como se implementa CommandLineRunner, Spring ejecuta el metodo run durante el arranque.
 */
@Component
public class DatosIniciales implements CommandLineRunner {

	private final DuenioService duenioService;
	private final MascotaService mascotaService;
	private final TurnoService turnoService;

	public DatosIniciales(DuenioService duenioService, MascotaService mascotaService, TurnoService turnoService) {
		this.duenioService = duenioService;
		this.mascotaService = mascotaService;
		this.turnoService = turnoService;
	}

	@Override
	public void run(String... args) {
		if (!duenioService.listar().isEmpty()) {
			return;
		}

		Duenio ana = duenioService.guardar(new Duenio("Lionel Messi", "1122334455", "lmessi@mail.com", "30111222", "San Miguel"));
		Duenio carlos = duenioService.guardar(new Duenio("Julian Alvarez", "1166778899", "jalvarez@mail.com", "28999888", "Jose C. Paz"));

		Mascota lola = mascotaService.guardar(new Mascota("Lola", LocalDate.of(2021, 5, 10), "PERRO", "Caniche", 6.5, ana));
		Mascota michi = mascotaService.guardar(new Mascota("Michi", LocalDate.of(2020, 3, 3), "GATO", "Europeo comun", 4.1, carlos));

		turnoService.guardar(new Turno(LocalDateTime.now().plusDays(1), "CONTROL", lola, "Control general"));
		turnoService.guardar(new Turno(LocalDateTime.now().plusDays(2), "VACUNACION", michi, "Refuerzo anual"));
	}
}
