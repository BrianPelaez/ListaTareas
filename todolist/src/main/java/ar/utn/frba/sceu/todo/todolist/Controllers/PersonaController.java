package ar.utn.frba.sceu.todo.todolist.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import ar.utn.frba.sceu.todo.todolist.Models.Persona;
import ar.utn.frba.sceu.todo.todolist.Models.Tarea;
import ar.utn.frba.sceu.todo.todolist.Repositories.PersonaRepository;

@RestController
@RequestMapping("api")
public class PersonaController {
	
	@Autowired
	PersonaRepository personaRepository;
	
	@GetMapping("/persona")
	public Iterable<Persona> listarTodo() {
		return personaRepository.findAll();
	}
	
	@PostMapping("/persona")
	public boolean guardarPersona(@RequestBody Persona body) {
		if (body != null) {
			personaRepository.save(body);
			return true;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hubo un error al cargar la Persona");
		}
		
	}
	
}
