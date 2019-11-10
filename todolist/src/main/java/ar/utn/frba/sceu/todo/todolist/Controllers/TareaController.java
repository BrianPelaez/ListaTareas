package ar.utn.frba.sceu.todo.todolist.Controllers;

import java.util.Date;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import ar.utn.frba.sceu.todo.todolist.DTO.TareaDTO;
import ar.utn.frba.sceu.todo.todolist.Models.Persona;
import ar.utn.frba.sceu.todo.todolist.Models.Tarea;
import ar.utn.frba.sceu.todo.todolist.Repositories.PersonaRepository;
import ar.utn.frba.sceu.todo.todolist.Repositories.TareaRepository;

@RestController
@RequestMapping("api")
public class TareaController {

	@Autowired
	TareaRepository tareaRepository;
	@Autowired
	PersonaRepository personaRepository;

	@GetMapping("/")
	public Iterable<Tarea> listarTodo() {
		return tareaRepository.findAll();
	}

	@GetMapping("/{id}")
	public Tarea listarID(@PathVariable Integer id) {
		Tarea tarea;
		if (tareaRepository.count() > 0) {
			tarea = tareaRepository.findById(id).orElse(null);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay datos en la tabla");
		}

		if (tarea == null) {

			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encuentra la tarea");

		} else {
			return tarea;
		}

	}

	@PostMapping("/{id}")
	public boolean crearTarea(@RequestBody Tarea body, @PathVariable Integer id) {
		Persona persona;
		persona = personaRepository.findById(id).orElse(null);
		if (!body.getDetalle().isEmpty() && persona != null) {
			body.setAsignado(persona.getNombre() + " " + persona.getApellido());
			tareaRepository.save(body);
			return true;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Se produjo un error al crear la tarea");
		}
	}

	@DeleteMapping("/{id}")
	public boolean borrarTarea(@PathVariable Integer id) {
		if (tareaRepository.existsById(id)) {
			tareaRepository.deleteById(id);
			return true;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encuentra la tarea");
		}
	}

	@PutMapping("/{id}")
	public boolean modificar(@RequestBody TareaDTO body, @PathVariable Integer id) {

		Tarea tarea = tareaRepository.findById(id).orElse(null);
		tarea.setFecha_vencimiento(sumarDiasAFecha(tarea.getFecha_inicio(),10));
		tareaRepository.save(tarea);
		if (!body.getDetalle().isEmpty()) {
			// tareaRepository
			return true;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Se produjo un error al modificar la tarea");
		}
	}
	
	public Date sumarDiasAFecha(Date fecha, int dias){
	      if (dias==0) return fecha;
	      Calendar calendar = Calendar.getInstance();
	      calendar.setTime(fecha); 
	      calendar.add(Calendar.DAY_OF_YEAR, dias);  
	      return calendar.getTime();
	}
}
