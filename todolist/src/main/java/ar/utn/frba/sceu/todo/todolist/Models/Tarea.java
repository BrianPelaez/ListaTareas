package ar.utn.frba.sceu.todo.todolist.Models;

import javax.persistence.*;

@Entity
@Table(name = "tarea")
public class Tarea {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	String detalle, fecha_inicio, fecha_fin, asignado, fecha_vencimiento;
	Boolean realizada;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getFechaInicio() {
		return fecha_inicio;
	}

	public void setFechaInicio(String fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public String getFechaFin() {
		return fecha_fin;
	}

	public void setFechaFin(String fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	public String getAsignado() {
		return asignado;
	}

	public void setAsignado(String asignado) {
		this.asignado = asignado;
	}

	public String getFechaVencimiento() {
		return fecha_vencimiento;
	}

	public void setFechaVencimiento(String fecha_vencimiento) {
		this.fecha_vencimiento = fecha_vencimiento;
	}

	public Boolean getRealizada() {
		return realizada;
	}

	public void setRealizada(Boolean realizada) {
		this.realizada = realizada;
	}

}
