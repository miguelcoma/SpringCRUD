package com.sistemacrud.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
	}

	//Ruta a la pagina principal
	@GetMapping("/")
	public String mensaje(@RequestParam(value = "Saludo", defaultValue = "Soy un sistema CRUD") String saludo) {
		return String.format("<h1>Hola %s ! </h1>", saludo);
	}
}
