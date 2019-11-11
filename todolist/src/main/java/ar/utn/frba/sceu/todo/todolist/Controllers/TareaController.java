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

	// **ATRIBUTOS**
	@Autowired
	TareaRepository tareaRepository;
	@Autowired
	PersonaRepository personaRepository;

	// **METODOS**
	
	// BUSCAR TODO
	@GetMapping("/")
	public Iterable<Tarea> listarTodo() {
		return tareaRepository.findAll();
	}

	// BUSCAR POR ID
	@GetMapping("/{id}")
	public Tarea listarID(@PathVariable Integer id) {
		/*
		 * Tarea tarea; if (tareaRepository.count() > 0) { tarea =
		 * tareaRepository.findById(id).orElse(null); } else { throw new
		 * ResponseStatusException(HttpStatus.NOT_FOUND, "No hay datos en la tabla"); }
		 * 
		 * if (tarea == null) { throw new ResponseStatusException(HttpStatus.NOT_FOUND,
		 * "No se encuentra la tarea"); } else { return tarea; }
		 */
		return tareaRepository.findById(id).orElseThrow(null);
	}

	// AGREGAR TAREA + ASIGNAR PERSONA
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

	// BORRAR TAREA
	@DeleteMapping("/{id}")
	public boolean borrarTarea(@PathVariable Integer id) {
		if (tareaRepository.existsById(id)) {
			tareaRepository.deleteById(id);
			return true;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encuentra la tarea");
		}
	}

	@PutMapping("/{id_tarea}/{id_persona}")
	public boolean modificar(@RequestBody TareaDTO body, @PathVariable Integer id_tarea,
			@PathVariable Integer id_persona) {

		Tarea tarea = tareaRepository.findById(id_tarea).orElse(null);
		Persona persona = personaRepository.findById(id_persona).orElse(null);

		tareaRepository.save(tarea);
		if (!body.getDetalle().isEmpty()) {
			tarea.setFecha_vencimiento(sumarDiasAFecha(tarea.getFecha_inicio(), 1));
			tarea.setDetalle(body.getDetalle().isEmpty() ? tarea.getDetalle() : body.getDetalle());
			tarea.setFecha_inicio(
					(body.getFecha_inicio().equals(null) ? tarea.getFecha_inicio() : body.getFecha_inicio()));
			tarea.setRealizada(body.getRealizada() == null ? tarea.getRealizada() : body.getRealizada());
			tarea.setAsignado(persona.getNombre() + " " + persona.getApellido());
			tareaRepository.save(tarea);
			return true;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Se produjo un error al modificar la tarea");
		}
	}

	public Date sumarDiasAFecha(Date fecha, int dias) {
		if (dias == 0)
			return fecha;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_YEAR, dias);
		return calendar.getTime();
	}
}
