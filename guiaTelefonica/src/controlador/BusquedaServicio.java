package controlador;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Busqueda;
import modelo.VistaBusqueda;
import conexion.ConexionLocal;

public class BusquedaServicio implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * Metodo asignar filtros de busqueda
	 */
	public List<Busqueda> obtenerCriterios() {
		List<Busqueda> criterios = new ArrayList<Busqueda>();

		// criterios.add(new Busqueda("1","Todos"));
		criterios.add(new Busqueda("2", "Por Nombre y/o Apellido"));
                criterios.add(new Busqueda("3", "Por Cargo"));
		criterios.add(new Busqueda("4", "Por Extensión"));
		criterios.add(new Busqueda("5", "Por Sede"));
		criterios.add(new Busqueda("6", "Por Centro de Apoyo"));
                
		return criterios;
	}

	/*
	 * Metodo para recuperar datos de la BDD en relacion a los criterios de
	 * busqueda.
	 * 
	 * @param valorCriterio Codigo del criterio de busqueda.
	 * 
	 * @param valorTexto Dato sobre el cual se filtrara.
	 */
	public List<VistaBusqueda> buscarExtensiones(String valorCriterio, String valorTexto, String codeSede, String codeUnidad) {
		ConexionLocal cn = new ConexionLocal();
		ResultSet registroInterno = null;
		ResultSet registroExterno = null;
                
		if (valorCriterio.equals("2")) {
                    registroInterno = cn.consultaPorNombre(valorTexto);
                    registroExterno = cn.consultaPorNombreExterno(valorTexto);
		} else if (valorCriterio.equals("3")) {
                    registroInterno = cn.consultaPorCargo(valorTexto);
                    registroExterno = cn.consultaPorExtensionExterno(valorTexto);
		} else if (valorCriterio.equals("4")) {
                    registroInterno = cn.consultaPorExtension(valorTexto);
                    registroExterno = cn.consultaPorExtensionExterno(valorTexto);
		} else if (valorCriterio.equals("5") || valorCriterio.equals("6")) {
                    if (codeUnidad.equals("0"))
                        registroInterno = cn.consultaPorSedeTodos(codeSede, valorTexto);
                    else
                        registroInterno = cn.consultaPorSedeUnidad(codeSede, codeUnidad, valorTexto);
		} else
                    return null;

		List<VistaBusqueda> vistaBusqueda = new ArrayList<VistaBusqueda>();

		try {
                    while (registroInterno.next()) {
                        vistaBusqueda.add(new VistaBusqueda(registroInterno
                                        .getString("UZGTPERSON_ID"), registroInterno
                                        .getString("UZGTPERSON_UNIDAD"), registroInterno
                                        .getString("UZGTPERSON_SEDE"), registroInterno
                                        .getString("UZGTPERSON_PUEST"), registroInterno
                                        .getString("UZGTPERSON_NOMBRE"), registroInterno
                                        .getString("UZGTEXTE_NUM_EXTENSION"), registroInterno
                                        .getString("UZGTTELE_NUM_TELEFONO"), registroInterno
                                        .getString("UZGTPERSON_CORR"), registroInterno
                                        .getLong("UZGTEXTE_ID")));
                    }

                    if (!(registroExterno == null)) {
                        while (registroExterno.next()) {
                            vistaBusqueda.add(new VistaBusqueda(registroExterno
                                            .getString("UZGTPRO_ID"), registroExterno
                                            .getString("UZGTPRO_UNIDAD"), registroExterno
                                            .getString("UZGTPRO_CAMPUS"), registroExterno
                                            .getString("UZGTPRO_AREA"), registroExterno
                                            .getString("UZGTPRO_NOMBRES"), registroExterno
                                            .getString("UZGTEXTE_NUM_EXTENSION"),
                                            registroExterno.getString("UZGTTELE_NUM_TELEFONO"),
                                            "", registroExterno.getLong("UZGTEXTE_ID")));
                        }
                    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vistaBusqueda;
	}

	/*
	 * Metodo para buscar las Sedes con extensiones Telefonicas.
	 */
	public List<Busqueda> obtenerSedes() {
		ConexionLocal cn = new ConexionLocal();
		ResultSet res = cn.consultaSedes();
		List<Busqueda> sedes = new ArrayList<Busqueda>();

		if (res == null) {
			System.out.println("Error No Hay Datos");
		} else {
			try {
				while (res.next()) {
					sedes.add(new Busqueda(res.getString("CODIGO_SEDE"), res.getString("NOMBRE_SEDE")));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sedes;
	}

	/*
	 * Metodo para buscar los Centros de Apoyo con extensiones Telefonicas.
	 */
	public List<Busqueda> obtenerCentros() {
		ConexionLocal cn = new ConexionLocal();
		ResultSet res = cn.consultaCentros();
		List<Busqueda> centros = new ArrayList<Busqueda>();

		if (res == null) {
			System.out.println("Error No Hay Datos");
		} else {
			try {
				while (res.next()) {
					centros.add(new Busqueda(res.getString("CODIGO_SEDE"), res.getString("NOMBRE_SEDE")));
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}

		return centros;
	}

	/*
	 * Metodo para buscar las Unidades en relacion a la Sede Seleccionada.
	 */
	public List<Busqueda> obtenerUnidades(String sedeCode) {

		ConexionLocal cn = new ConexionLocal();
		ResultSet res = cn.consultaUnidades(sedeCode);
		List<Busqueda> unidades = new ArrayList<Busqueda>();

		if (res == null) {
			System.out.println("Error No Hay Datos");
		} else {
			try {
				while (res.next()) {
					unidades.add(new Busqueda(res.getString("UNIDAD"), res.getString("UNIDAD")));
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}

		return unidades;
	}
        
        
        /*
	 * Metodo para buscar las Instrucciones de la pantalla de bienvenida
	 */
	public List<Busqueda> obtenerInstrucciones() {
		ConexionLocal cn = new ConexionLocal();
		ResultSet res = cn.consultaInstrucciones();
		List<Busqueda> instrucciones = new ArrayList<Busqueda>();

		if (res == null) {
			System.out.println("Error No Hay Datos");
		} else {
			try {
				while (res.next()) {
					instrucciones.add(new Busqueda(res.getString("CODIGO"), res.getString("INSTRUCCION")));
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}

		return instrucciones;
	}

}