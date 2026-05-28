package ar.edu.unpaz.veterinaria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador MVC simple para servir la pagina principal.
 */
@Controller
public class HomeController {

	@GetMapping("/")
	public String index() {
		return "forward:/index.html";
	}
}
