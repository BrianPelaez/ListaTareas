package ar.utn.frba.sceu.todo.todolist.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import ar.utn.frba.sceu.todo.todolist.DTO.TareaDTO;
import ar.utn.frba.sceu.todo.todolist.Models.Tarea;
import ar.utn.frba.sceu.todo.todolist.Repositories.TareaRepository;

@RestController
@RequestMapping("api")
public class TareaController {

	TareaRepository tareaRepository;

	@GetMapping("/")
	public Iterable<Tarea> listarTodo() {
		return tareaRepository.findAll();
	}

	@GetMapping("/{id}")
	public Tarea listarID(@PathVariable Integer id) {
		Tarea tarea;
		if(tareaRepository.count()>0) {
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

	@PostMapping("/")
	public boolean crearTarea(@RequestBody Tarea body) {
		/*
		 * Tarea tarea = new Tarea(); tarea.setDetalle(body.getDetalle());
		 * tarea.setFechaInicio(body.getFechaInicio());
		 * tarea.setAsignado(body.getAsignado());
		 */
		if (!body.getDetalle().isEmpty()) {
			tareaRepository.save(body);
			return true;
		} else {
			return false;
		}
	}
}
