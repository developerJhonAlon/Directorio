package modelo;

import java.io.Serializable;

public class VistaProveedor implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long identidad;
	private String idResponsable;
	private String sedeNomb;
	private String unidadNomb;
	private String abreviat;
	private String areaNomb;
	private String trato;
	private String nombreProvee;
	private String telefonoNomb;
	private String extensionNomb;
	private long idAsignacion;
	
	public VistaProveedor(){}
	
	public VistaProveedor(long identidad, String idResponsable,
			String sedeNomb, String unidadNomb, String abreviat,
			String areaNomb, String trato, String nombreProvee,
			String telefonoNomb, String extensionNomb, long idAsignacion) {
		super();
		this.identidad = identidad;
		this.idResponsable = idResponsable;
		this.sedeNomb = sedeNomb;
		this.unidadNomb = unidadNomb;
		this.abreviat = abreviat;
		this.areaNomb = areaNomb;
		this.trato = trato;
		this.nombreProvee = nombreProvee;
		this.telefonoNomb = telefonoNomb;
		this.extensionNomb = extensionNomb;
		this.idAsignacion = idAsignacion;
	}
	public long getIdentidad() {
		return identidad;
	}
	public void setIdentidad(long identidad) {
		this.identidad = identidad;
	}
	public String getIdResponsable() {
		return idResponsable;
	}
	public void setIdResponsable(String idResponsable) {
		this.idResponsable = idResponsable;
	}
	public String getSedeNomb() {
		return sedeNomb;
	}
	public void setSedeNomb(String sedeNomb) {
		this.sedeNomb = sedeNomb;
	}
	public String getUnidadNomb() {
		return unidadNomb;
	}
	public void setUnidadNomb(String unidadNomb) {
		this.unidadNomb = unidadNomb;
	}
	public String getAbreviat() {
		return abreviat;
	}
	public void setAbreviat(String abreviat) {
		this.abreviat = abreviat;
	}
	public String getAreaNomb() {
		return areaNomb;
	}
	public void setAreaNomb(String areaNomb) {
		this.areaNomb = areaNomb;
	}
	public String getTrato() {
		return trato;
	}
	public void setTrato(String trato) {
		this.trato = trato;
	}
	public String getNombreProvee() {
		return nombreProvee;
	}
	public void setNombreProvee(String nombreProvee) {
		this.nombreProvee = nombreProvee;
	}
	public String getTelefonoNomb() {
		return telefonoNomb;
	}
	public void setTelefonoNomb(String telefonoNomb) {
		this.telefonoNomb = telefonoNomb;
	}
	public String getExtensionNomb() {
		return extensionNomb;
	}
	public void setExtensionNomb(String extensionNomb) {
		this.extensionNomb = extensionNomb;
	}
	public long getIdAsignacion() {
		return idAsignacion;
	}
	public void setIdAsignacion(long idAsignacion) {
		this.idAsignacion = idAsignacion;
	}
	
	
}
