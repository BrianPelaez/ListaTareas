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
	
	@GetMapping("/persona/{id}")
	public Persona listarID(@PathVariable Integer id) {
		return personaRepository.findById(id).orElseThrow();
	}
	
	@PostMapping("/persona")
	public String guardarPersona(@RequestBody Persona body) {
		if (body != null) {
			personaRepository.save(body);
			return "Persona Agregada";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hubo un error al cargar la Persona");
		}
		
	}
	
	@PutMapping("/persona/{id}")
	public String modificarPersona(@RequestBody Persona body, @PathVariable Integer id) {
		Persona persona = personaRepository.findById(id).orElse(null);
		if(persona != null) {
			persona.setApellido(body.getApellido().isEmpty() ? persona.getApellido() : body.getApellido());
			persona.setNombre(body.getNombre().isEmpty() ? persona.getNombre() : body.getNombre());
			persona.setEdad(body.getEdad() == null ? persona.getEdad() : body.getEdad());
			personaRepository.save(persona);
			return "Modificado";
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encuentra a la persona");
		
	}
	
}
