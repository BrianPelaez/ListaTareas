package ar.utn.frba.sceu.todo.todolist.Controllers;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import ar.utn.frba.sceu.todo.todolist.DTO.PersonaDTO;
import ar.utn.frba.sceu.todo.todolist.Models.Persona;

import ar.utn.frba.sceu.todo.todolist.Repositories.PersonaRepository;

@RestController
@RequestMapping("api")
public class PersonaController {

	@Autowired
	PersonaRepository personaRepository;

	// BUSCAR TODO
	@GetMapping("/persona")
	public Iterable<PersonaDTO> listarTodo() {
		//return personaRepository.findAll();
	
		 Persona unaPersona; 
		 ArrayList<PersonaDTO> respuesta = new ArrayList<PersonaDTO>();
		 Iterable<Persona> listaPersonas = personaRepository.findAll();
		 /*
		 Iterator<Persona> it = listaPersonas.iterator();
		 
		 while(it.hasNext()){
		 	unaPersona = it.next();
		    Persona personaDTO = new PersonaDTO();
		 
		 	personaDTO.setApellido(unaPersona.getApellido());
		 	personaDTO.setNombre(unaPersona.getNombre());
		 	personaDTO.setEdad(unaPersona.getEdad());
		 	personaDTO.setMail(unaPersona.getMail());
		 	respuesta.add(personaDTO);
		 }
		 return respuesta;*/
		 listaPersonas.forEach(x ->{
			 PersonaDTO personaDTO = new PersonaDTO();
			 personaDTO.setApellido(x.getApellido());
			 personaDTO.setNombre(x.getNombre());
			 personaDTO.setEdad(x.getEdad());
			 personaDTO.setMail(x.getMail());
			 respuesta.add(personaDTO);
		 });
		return respuesta;
	}

	/*
	 * BUSCAR POR ID
	 * 
	 * @GetMapping("/persona/{id}") public Persona listarID(@PathVariable Integer
	 * id) { return personaRepository.findById(id).orElseThrow(null); }
	 */

	// BUSCAR POR NOMBRE
	@GetMapping("/persona/{nombre}")
	public ArrayList<Persona> listarID(@PathVariable String nombre) {
		Iterable<Persona> personas = personaRepository.findAll();
		ArrayList<Persona> respuesta = new ArrayList<Persona>();
		// SE LE ASIGNA A 'x' CADA VALOR DENTRO DE LA BASE DE DATOS DE PERSONAS	
		personas.forEach(x -> {
			if (x.getApellido().equalsIgnoreCase(nombre)) {
				respuesta.add(x);
			}
		});
		if (respuesta.size() != 0) {
			return respuesta;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encuentra la persona");
		}
	}

	// AGREGAR PERSONA
	@PostMapping("/persona")
	public String guardarPersona(@RequestBody Persona body) {
		if (body != null) {
			personaRepository.save(body);
			return "Persona Agregada";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hubo un error al cargar la Persona");
		}

	}

	// MODIFICAR PERSONA
	@PutMapping("/persona/{id}")
	public String modificarPersona(@RequestBody Persona body, @PathVariable Integer id) {
		Persona persona = personaRepository.findById(id).orElse(null);
		if (persona != null) {
			persona.setApellido(body.getApellido().isEmpty() ? persona.getApellido() : body.getApellido());
			persona.setNombre(body.getNombre().isEmpty() ? persona.getNombre() : body.getNombre());
			persona.setEdad(body.getEdad() == null ? persona.getEdad() : body.getEdad());
			persona.setMail(body.getMail().isEmpty() ? persona.getMail() : body.getMail());
			personaRepository.save(persona);
			return "Modificado";
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encuentra a la persona");

	}

	// BORRAR PERSONA
	@DeleteMapping("/persona/{id}")
	public boolean borrarTarea(@PathVariable Integer id) {
		if (personaRepository.existsById(id)) {
			personaRepository.deleteById(id);
			return true;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encuentra la persona");
		}
	}

}
