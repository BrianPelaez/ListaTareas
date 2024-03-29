package ar.utn.frba.sceu.todo.todolist.Models;

import javax.persistence.*;

@Entity
@Table(name = "persona")
public class Persona {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	String nombre, apellido, mail;
	Integer edad;

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Persona [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", edad=" + edad + "]";
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

}
