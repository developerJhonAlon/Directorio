package modelo;

import java.io.Serializable;

public class VistaAdministradores  implements Serializable{

	private static final long serialVersionUID = 1L;
	private String nombre;
	private String sede;
	private String id_sede;
	private String id_persona;
	
	public VistaAdministradores(){}
	
	public VistaAdministradores(String nombre, String sede, String id_sede,
			String id_persona) {
		super();
		this.nombre = nombre;
		this.sede = sede;
		this.id_sede = id_sede;
		this.id_persona = id_persona;
	}		

	public VistaAdministradores(String nombre, String id_persona) {
		super();
		this.nombre = nombre;
		this.id_persona = id_persona;
	}

	public VistaAdministradores(String sede, String id_sede,String a) {
		super();
		this.sede = sede;
		this.id_sede = id_sede;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSede() {
		return sede;
	}
	public void setSede(String sede) {
		this.sede = sede;
	}
	
	public String getId_sede() {
		return id_sede;
	}
	public void setId_sede(String id_sede) {
		this.id_sede = id_sede;
	}
	public String getId_persona() {
		return id_persona;
	}
	public void setId_persona(String id_persona) {
		this.id_persona = id_persona;
	}
	
	

}
