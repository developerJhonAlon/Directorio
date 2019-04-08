package modelo;

import java.io.Serializable;

public class Personal implements Serializable{

	
	/**
	 * A
	 */
	private static final long serialVersionUID = 1L;
	private String nombres;
	private String cedula;
	private String idDocente;
	private String puesto;
	private String unidad;
	private String sede;
	private String sedeCode;
	private String direcInstitu;
	private String ciudadLabora;
	private String correo;
	
	public Personal(){
	}


	public Personal(String nombres, String cedula, String idDocente,
			String puesto, String unidad, String sede, String sedeCode,
			String direcInstitu, String ciudadLabora, String correo) {
		super();
		this.nombres = nombres;
		this.cedula = cedula;
		this.idDocente = idDocente;
		this.puesto = puesto;
		this.unidad = unidad;
		this.sede = sede;
		this.sedeCode = sedeCode;
		this.direcInstitu = direcInstitu;
		this.ciudadLabora = ciudadLabora;
		this.correo = correo;
	}




	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getIdDocente() {
		return idDocente;
	}

	public void setIdDocente(String idDocente) {
		this.idDocente = idDocente;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public String getDirecInstitu() {
		return direcInstitu;
	}

	public void setDirecInstitu(String direcInstitu) {
		this.direcInstitu = direcInstitu;
	}

	public String getCiudadLabora() {
		return ciudadLabora;
	}

	public void setCiudadLabora(String ciudadLabora) {
		this.ciudadLabora = ciudadLabora;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}




	public String getSedeCode() {
		return sedeCode;
	}




	public void setSedeCode(String sedeCode) {
		this.sedeCode = sedeCode;
	}

	
	
	


	
	
}
