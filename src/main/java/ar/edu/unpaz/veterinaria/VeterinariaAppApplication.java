package ar.edu.unpaz.veterinaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la aplicacion.
 *
 * La anotacion {@code @SpringBootApplication} habilita la configuracion
 * automatica y el escaneo de componentes desde el paquete raiz
 * {@code ar.edu.unpaz.veterinaria}.
 */
@SpringBootApplication
public class VeterinariaAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(VeterinariaAppApplication.class, args);
	}
}
