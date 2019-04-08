package controlador;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Busqueda;
import modelo.Personal;
import modelo.VistaBusqueda;
import modelo.VistaProveedor;
import conexion.ConexionLocal;

public class ProveedorServicio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProveedorServicio() {
	}

	/*
	 * Metodo para buscar las Sedes Externas en relacion a un Administrador
	 * ingresado al Sistema.
	 */
	public List<Busqueda> obtenerListaSedeExterna(String codigoAdministrador) {

		ConexionLocal cn = new ConexionLocal();
		ResultSet res = cn.consultaListaSedeExterna(codigoAdministrador);
		List<Busqueda> sedesAll = new ArrayList<Busqueda>();

		try {
			while (res.next()) {
				sedesAll.add(new Busqueda(res.getString("CODIGO_SEDE_EXTE"),
						res.getString("CODIGO_SEDE_EXTE")));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return sedesAll;
	}

	/*
	 * Metodo para buscar las Unidades Externas en relacion a la Sede
	 * Seleccionada.
	 */
	public List<Busqueda> obtenerListaUnidadExterna(String codigoSede) {

		ConexionLocal cn = new ConexionLocal();
		ResultSet res = cn.consultaListaUnidadExterna(codigoSede);
		List<Busqueda> sedesAll = new ArrayList<Busqueda>();

		try {
			while (res.next()) {
				sedesAll.add(new Busqueda(res.getString("CODIGO_UNIDAD_EXTE"),
						res.getString("CODIGO_UNIDAD_EXTE")));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return sedesAll;
	}
	
	
	/*
	 * Metodo para recuperar datos de Extensiones de Proveedores por medio de la
	 * Unidad Seleccionada.
	 */
	public List<VistaProveedor> obtenerExtensionesExternasPorUnidad(
			String codigoSede, String codigoUnidad, String responsable) {
		ConexionLocal cn = new ConexionLocal();
		ResultSet res = null;

		res = cn.consultaExtensionesExternasPorUnidad(codigoSede, codigoUnidad,responsable);

		List<VistaProveedor> vistaProveedor = new ArrayList<VistaProveedor>();

		try {
			while (res.next()) {
				vistaProveedor.add(new VistaProveedor(
						res.getLong("UZGTPRO_ID"), res
								.getString("UZGTPRO_RESPONSABLE"), res
								.getString("UZGTPRO_CAMPUS"), res
								.getString("UZGTPRO_UNIDAD"), res
								.getString("UZGTPRO_ABRE"), res
								.getString("UZGTPRO_AREA"), res
								.getString("UZGTPRO_TRATO"), res
								.getString("UZGTPRO_NOMBRES"), res
								.getString("UZGTTELE_NUM_TELEFONO"), res
								.getString("UZGTEXTE_NUM_EXTENSION"), res
								.getLong("UZGTEXTE_ID")));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return vistaProveedor;
	}

	public List<VistaProveedor> obtenerProveedorExterno(String codeSede,
			String valorRecibido) {

		ConexionLocal cn = new ConexionLocal();

		ResultSet res = cn.consultaExtensionExternaPorSede(codeSede,
				 valorRecibido);

		List<VistaProveedor> vistaProveedor = new ArrayList<VistaProveedor>();

		try {
			while (res.next()) {
				vistaProveedor.add(new VistaProveedor(
						res.getLong("UZGTPRO_ID"), res
								.getString("UZGTPRO_RESPONSABLE"), res
								.getString("UZGTPRO_CAMPUS"), res
								.getString("UZGTPRO_UNIDAD"), res
								.getString("UZGTPRO_ABRE"), res
								.getString("UZGTPRO_AREA"), res
								.getString("UZGTPRO_TRATO"), res
								.getString("UZGTPRO_NOMBRES"), res
								.getString("UZGTTELE_NUM_TELEFONO"), res
								.getString("UZGTEXTE_NUM_EXTENSION"), res
								.getLong("UZGTEXTE_ID")));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return vistaProveedor;
	}

	/* *
	 * Metodo para guardar la informacion de un Proveedor Externo.
	 */
	public boolean guardarExtensionExtena(VistaProveedor vistaProveedor,
			String responsable) {

		ConexionLocal cn = new ConexionLocal();
		ResultSet lastRegistro = null;

		// consultar si el Personal ya esta en la BDD de la Guia para guardar
		// sus datos o pasar a guardar la Extensión.
		lastRegistro = cn.consultaProveedor(vistaProveedor);
		try {
			if (lastRegistro.next()) {
				System.out
						.println("AgregarExtensionProveedor: El registro ya existe");
				return false;
			} else {

				guardarDataGuia(vistaProveedor, responsable, cn);
				return true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return false;

	}

	/* *
	 * Metodo para guardar la informacion de la Relacion del Proveedor y la
	 * Extension que se guardara.
	 */
	private void guardarDataGuia(VistaProveedor vistaProveedor,
			String responsable, ConexionLocal cn) {
		long lastIdTemp = 0;
		ResultSet lastRegistro = null;

		cn.guardarTelefono(vistaProveedor.getTelefonoNomb());

		// consultar el ultimo registro guardado del Telefono para obtener el
		// Secuencial, y luego relacionarlo con la Extension.
		lastRegistro = cn.consultaLastTelefono();
		if (lastRegistro == null) {
			System.out.println("Error ultimo registro no Encontrado:");
		} else {
			try {
				while (lastRegistro.next()) {
					lastIdTemp = lastRegistro.getLong("ID_TELE");
				}
				cn.guardarExtension(vistaProveedor.getExtensionNomb(),
						lastIdTemp);

				// consultar el ultimo registro guardado de la Extension, para
				// luego relacionarlo con el Personal que tendra esa Extensión.
				lastRegistro = cn.consultaLastExte();
				while (lastRegistro.next()) {
					lastIdTemp = lastRegistro.getLong("ID_EXTE");
				}
				cn.guardarRelProvedorExtension(vistaProveedor, responsable,
						lastIdTemp);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error Guardar Personal Extension: "
						+ e.getCause());
				e.printStackTrace();
			}

		}
	}

	/* *
	 * Metodo para eliminar la informacion de un Proveedor.
	 */
	public boolean eliminaRegistro(VistaProveedor datoDelete) {

		ConexionLocal cn = new ConexionLocal();

		cn.eliminarRelacionExtesionExterno(datoDelete);
		cn.eliminarExtension(datoDelete.getIdAsignacion());
		cn.eliminarTelefono(datoDelete.getIdAsignacion());
		return true;

	}

	/* *
	 * Metodo para editar la informacion de una Extension Externo.
	 */
	public List<VistaProveedor> editarInformacionExterno(
			VistaProveedor vistaProveedor) {

		ConexionLocal cn = new ConexionLocal();
		ResultSet res = null;

		// consultar si el Personal ya esta en la BDD de la Guia para guardar
		// sus datos o pasar a guardar la Extensión.
		res = cn.consultaFindExternoEdicion(vistaProveedor);
		try {
			if (res.next()) {
				System.out
						.println("AgregarExtensionExterno: El registro ya existe");
				return null;
			} else {

				cn.modificarExtensionExterno(vistaProveedor);

				cn.modificarTelefono(vistaProveedor.getIdAsignacion(),
						vistaProveedor.getTelefonoNomb());
				int confirma = cn.modificarExtension(
						vistaProveedor.getIdAsignacion(),
						vistaProveedor.getExtensionNomb());

				if (confirma != 1) {
					System.out
							.println("AgregarExtension: Error Dato de Personal no guardado");

				} else {
					List<VistaProveedor> vistaProveedorModificado = new ArrayList<VistaProveedor>();
					res = cn.consultaPorIdExterno(vistaProveedor.getIdentidad());

					while (res.next()) {

						vistaProveedorModificado.add(new VistaProveedor(res
								.getLong("UZGTPRO_ID"), res
								.getString("UZGTPRO_RESPONSABLE"), res
								.getString("UZGTPRO_CAMPUS"), res
								.getString("UZGTPRO_UNIDAD"), res
								.getString("UZGTPRO_ABRE"), res
								.getString("UZGTPRO_AREA"), res
								.getString("UZGTPRO_TRATO"), res
								.getString("UZGTPRO_NOMBRES"), res
								.getString("UZGTTELE_NUM_TELEFONO"), res
								.getString("UZGTEXTE_NUM_EXTENSION"), res
								.getLong("UZGTEXTE_ID")));
					}

					return vistaProveedorModificado;

				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return null;

	}

}
