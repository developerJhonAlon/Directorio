package modelo;

import java.io.Serializable;

/**
 * Modelo para implementar la vista presentada en las busquedas
 */
public class VistaBusqueda implements Serializable {

	/**
	 * A
	 */
	private static final long serialVersionUID = 1L;
	private long identidad;
	private String idPersonal;
	private String unidadNomb;
	private String sedeNomeb;
	private String sedeCodigo;
	private String cargoNomb;
	private String personalNomb;
	private String extensionNomb;
	private String telefonoNomb;
	private String correo;
	private long idAsignacion;

	public VistaBusqueda() {
	}


	public VistaBusqueda(long identidad, String idPersonal, String unidadNomb,
			String sedeNomeb, String sedeCodigo, String cargoNomb,
			String personalNomb, String extensionNomb, String telefonoNomb,
			String correo, long idAsignacion) {
		super();
		this.identidad = identidad;
		this.idPersonal = idPersonal;
		this.unidadNomb = unidadNomb;
		this.sedeNomeb = sedeNomeb;
		this.sedeCodigo = sedeCodigo;
		this.cargoNomb = cargoNomb;
		this.personalNomb = personalNomb;
		this.extensionNomb = extensionNomb;
		this.telefonoNomb = telefonoNomb;
		this.correo = correo;
		this.idAsignacion = idAsignacion;
	}




	public VistaBusqueda(String idPersonal, String unidadNomb,
			String sedeNomeb, String cargoNomb, String personalNomb,
			String extensionNomb, String telefonoNomb, String correo,
			long idAsignacion) {
		super();
		this.idPersonal = idPersonal;
		this.unidadNomb = unidadNomb;
		this.sedeNomeb = sedeNomeb;
		this.cargoNomb = cargoNomb;
		this.personalNomb = personalNomb;
		this.extensionNomb = extensionNomb;
		this.telefonoNomb = telefonoNomb;
		this.correo = correo;
		this.idAsignacion = idAsignacion;
	}

	

	public String getSedeCodigo() {
		return sedeCodigo;
	}




	public void setSedeCodigo(String sedeCodigo) {
		this.sedeCodigo = sedeCodigo;
	}




	public long getIdentidad() {
		return identidad;
	}



	public void setIdentidad(long identidad) {
		this.identidad = identidad;
	}



	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public long getIdAsignacion() {
		return idAsignacion;
	}

	public void setIdAsignacion(long idAsignacion) {
		this.idAsignacion = idAsignacion;
	}

	public String getIdPersonal() {
		return idPersonal;
	}

	public void setIdPersonal(String idPersonal) {
		this.idPersonal = idPersonal;
	}

	public String getUnidadNomb() {
		return unidadNomb;
	}

	public void setUnidadNomb(String unidadNomb) {
		this.unidadNomb = unidadNomb;
	}

	public String getSedeNomeb() {
		return sedeNomeb;
	}

	public void setSedeNomeb(String sedeNomeb) {
		this.sedeNomeb = sedeNomeb;
	}

	public String getCargoNomb() {
		return cargoNomb;
	}

	public void setCargoNomb(String cargoNomb) {
		this.cargoNomb = cargoNomb;
	}

	public String getPersonalNomb() {
		return personalNomb;
	}

	public void setPersonalNomb(String personalNomb) {
		this.personalNomb = personalNomb;
	}

	public String getExtensionNomb() {
		return extensionNomb;
	}

	public void setExtensionNomb(String extensionNomb) {
		this.extensionNomb = extensionNomb;
	}

	public String getTelefonoNomb() {
		return telefonoNomb;
	}

	public void setTelefonoNomb(String telefonoNomb) {
		this.telefonoNomb = telefonoNomb;
	}

}
