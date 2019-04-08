package modelo;

import java.io.Serializable;


/**
 * Modelo para implementar los tipos de busquedas en los combos
 */
public class Busqueda implements Serializable{

	/**
	 * Esta clase es un auxiliar para la persistir los valores de los combos.
	 */
	private static final long serialVersionUID = 1L;
	private String valor;
	private String descripcion;
	
	public Busqueda(){}

	
	public Busqueda(String valor, String descripcion) {
		super();
		this.valor = valor;
		this.descripcion = descripcion;
	}


	public String getValor() {
		return valor;
	}
	
	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
