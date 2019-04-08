package modelo;

import java.io.Serializable;

public class Administrador implements Serializable{

	/**
	 * A
	 */
	private static final long serialVersionUID = 1L;
	private String idAdministrador;
	private String nombAdmin;
	private String nombSede;
	private String codigoSede;
	
	public Administrador(){}

	public Administrador(String idAdministrador, String nombAdmin,
			String nombSede, String codigoSede) {
		super();
		this.idAdministrador = idAdministrador;
		this.nombAdmin = nombAdmin;
		this.nombSede = nombSede;
		this.codigoSede = codigoSede;
	}



	public String getIdAdministrador() {
		return idAdministrador;
	}

	public void setIdAdministrador(String idAdministrador) {
		this.idAdministrador = idAdministrador;
	}

	public String getNombAdmin() {
		return nombAdmin;
	}

	public void setNombAdmin(String nombAdmin) {
		this.nombAdmin = nombAdmin;
	}

	public String getNombSede() {
		return nombSede;
	}

	public void setNombSede(String nombSede) {
		this.nombSede = nombSede;
	}

	public String getCodigoSede() {
		return codigoSede;
	}

	public void setCodigoSede(String codigoSede) {
		this.codigoSede = codigoSede;
	}

	
}
