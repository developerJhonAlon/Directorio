package controlador;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Busqueda;
import modelo.Personal;
import modelo.VistaBusqueda;
import conexion.Conexion;
import conexion.ConexionLocal;

public class AgregarServicio implements Serializable {

	/* *
	 */
	private static final long serialVersionUID = 1L;

	public AgregarServicio() {
	}

	/* *
	 * Metodo para obtener los datos del personal del Banner en relacion a
	 */
	public List<Personal> buscarPersonal(String textoIngresado,
			List<String> sedesCodigos) {

		Conexion cn = new Conexion();

		ResultSet consultaIDM = null;

		ResultSet consultaDatos = null;

		List<String> pidm = new ArrayList<String>();
		List<Personal> personal = new ArrayList<Personal>();

		for (String sedecodigo : sedesCodigos) {

			consultaIDM = cn.consultaPorNombre(textoIngresado, sedecodigo);

			// Obtencion de todos los IDM del Banner en relacion a su Apellido.
			try {
				while (consultaIDM.next()) {
					pidm.add(consultaIDM.getString("IDM"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error SQL IDM de Ban:" + e.getCause());
				e.printStackTrace();
			}
		}

		for (String pidm2 : pidm) {
			consultaDatos = cn.consultaDatosDePersonal(Integer.parseInt(pidm2));
			// Obtencion de todos los Datos en relacion al IDM
			if (consultaDatos == null) {
				System.out.println("Error No Hay Datos, IDM Ban:");
			} else {
				try {
					while (consultaDatos.next()) {
						personal.add(new Personal(consultaDatos
								.getString("UZGTPERSON_NOMBRE"), consultaDatos
								.getString("UZGTPERSON_CEDULA"), consultaDatos
								.getString("UZGTPERSON_ID_PERSONA"), consultaDatos
								.getString("UZGTPERSON_PUEST"), consultaDatos
								.getString("UZGTPERSON_UNIDAD"), consultaDatos
								.getString("UZGTPERSON_SEDE"), consultaDatos
								.getString("UZGTPERSON_SEDE_CODE"), consultaDatos
								.getString("UZGTPERSON_DIREC"), consultaDatos
								.getString("UZGTPERSON_CIUDAD"), consultaDatos
								.getString("UZGTPERSON_CORR")));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Error obtener Datos Personal Ban:"
							+ e.getCause());
					e.printStackTrace();
				}

			}
		}

		return personal;

	}

	/*
	 * Metodo para recuperar datos de la BDD en relacion a los criterios de
	 * busqueda.
	 * 
	 * @param codeSede Codigo de la Sede Seleccinada.
	 * 
	 * @param codeUnidad Codigo de la Unidad Seleccionada.
	 */
	public List<VistaBusqueda> obtenerUnidadConExtension(String codeSede,
			String codeUnidad) {
		ConexionLocal cn = new ConexionLocal();
		ResultSet res = null;

		res = cn.consultaUnidadesConExtension(codeSede, codeUnidad);

		List<VistaBusqueda> vistaBusqueda = new ArrayList<VistaBusqueda>();
		if (res == null) {
			System.out.println("Error No Hay Datos");
		} else {
			try {
				while (res.next()) {
					vistaBusqueda.add(new VistaBusqueda(res
							.getLong("UZGTPERSON_ID"), res
							.getString("UZGTPERSON_ID_PERSONA"), res
							.getString("UZGTPERSON_UNIDAD"), res
							.getString("UZGTPERSON_SEDE"), res
							.getString("UZGTPERSON_SEDE_CODE"), res
							.getString("UZGTPERSON_PUEST"), res
							.getString("UZGTPERSON_NOMBRE"), res
							.getString("UZGTEXTE_NUM_EXTENSION"), res
							.getString("UZGTTELE_NUM_TELEFONO"), res
							.getString("UZGTPERSON_CORR"), res
							.getLong("UZGTEXTE_ID")));
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}
		return vistaBusqueda;
	}

	/* *
	 * Metodo para guardar la informacion de un Personal si este Existe o No.
	 */
	public boolean guardarInformacion(Personal personal, String codeSede,
			String nombreSede, String codeUnidad, String fono, String exte) {

		ConexionLocal cn = new ConexionLocal();
                Conexion cnn = new Conexion();
                
		ResultSet lastRegistro = null;
		long lastIdTemp = 0;
                long pidmTemp = 0;

		// consultar si el Personal ya esta en la BDD de la Guia para guardar
		// sus datos o pasar a guardar la Extensión.
		lastRegistro = cn.consultaFindPersonal(personal, codeSede, codeUnidad);
		try {
			if (lastRegistro.next()) {
				System.out.println("AgregarExtension: El registro ya existe");
				return false;
			} else {
				lastRegistro = cn.consultaFindRegistroAuxiliar(personal);
				if (lastRegistro.next()) {
					lastRegistro = cn.consultaFindRegistroAuxiliar(personal);
					lastRegistro.next();
					lastIdTemp = lastRegistro.getLong("IDENTIDAD");
                                        
                                        pidmTemp = lastRegistro.getLong("PIDM");
					guardarDataGuia(lastIdTemp, fono, exte, cn);
                                        //Actualizo datos en banner | by CA
                                        actualizarBanner(pidmTemp, fono, exte, cnn);
					return true;
				} else {
					int confirma = cn.guardarPersonal(personal, codeSede, nombreSede, codeUnidad);

					if (confirma != 1) {
						System.out.println("AgregarExtension: Error Dato de Personal no guardado");
					} else {
						ResultSet lastRegistro2 = cn.consultaLastPersonal();
						lastRegistro2.next();
						lastIdTemp = lastRegistro2.getLong("IDREGISTRO");
						guardarDataGuia(lastIdTemp, fono, exte, cn);
						return true;
					}
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return false;

	}

	/* *
	 * Metodo para guardar la Historia de cambio sobre un registro.
	 */
	public boolean guardarHistorico(String idPersona, String nombre,
			String unidad, String sede, String fono, String exte, String fecha, String idRespo, String respo) {

		ConexionLocal cn = new ConexionLocal();
		cn.guardarHistorico( idPersona,  nombre,
					 unidad,  sede,  fono,  exte,  fecha,  idRespo,  respo);

		
		return true;

	}

	/* *
	 * Metodo para editar la informacion de un Usuario con extension.
	 */
	public List<VistaBusqueda> editarInformacion(VistaBusqueda vista,
			String codeSede, String nombreSede, String codeUnidad, String fono,
			String extension) {

		ConexionLocal cn = new ConexionLocal();
                    Conexion cnn = new Conexion();
		ResultSet lastRegistro = null;
                    ResultSet consultaIDM = null;
                    List<String> pidm = new ArrayList<String>();

		// consultar si el Personal ya esta en la BDD de la Guia para guardar
		// sus datos o pasar a guardar la Extensión.
		lastRegistro = cn.consultaFindPersonalEdicion(vista, codeSede, codeUnidad);
		try {
			if (lastRegistro.next()) {
				System.out.println("AgregarExtension: El registro ya existe");
				return null;
			} else {
				cn.modificarPersonal(vista.getIdentidad(), codeSede,
						nombreSede, codeUnidad);

				cn.modificarTelefono(vista.getIdAsignacion(), fono);
				int confirma = cn.modificarExtension(vista.getIdAsignacion(),
						extension);
                                consultaIDM = cnn.consultaPorID(vista.getIdPersonal());
                                
                                int rowcount = 0;
                                // Obtencion de todos los IDM del Banner en relacion a su Apellido.
                                try {
                                        while (consultaIDM.next()) {
                                                rowcount++;
                                                pidm.add(consultaIDM.getString("IDM"));
                                        }
                                        System.out.println(rowcount);
                                } catch (SQLException e) {
                                        // TODO Auto-generated catch block
                                        System.out.println("Error SQL IDM de Ban:" + e.getCause());
                                        e.printStackTrace();
                                }
                                
                                for (String pidm2 : pidm) {
                                    actualizarBanner(Integer.parseInt(pidm2), fono, extension, cnn);
                                }
                                        
				if (confirma != 1) {
					System.out.println("AgregarExtension: Error Dato de Personal no guardado");
				} else {
					List<VistaBusqueda> vistaBusqueda = new ArrayList<VistaBusqueda>();
					lastRegistro = cn.consultaPorId(vista.getIdentidad());

					while (lastRegistro.next()) {
						vistaBusqueda.add(new VistaBusqueda(lastRegistro.getLong("UZGTPERSON_ID"),
                                                        lastRegistro.getString("UZGTPERSON_ID_PERSONA"),
                                                        lastRegistro.getString("UZGTPERSON_UNIDAD"),
                                                        lastRegistro.getString("UZGTPERSON_SEDE"),
                                                        lastRegistro.getString("UZGTPERSON_SEDE_CODE"),
                                                        lastRegistro.getString("UZGTPERSON_PUEST"),
                                                        lastRegistro.getString("UZGTPERSON_NOMBRE"),
                                                        lastRegistro.getString("UZGTEXTE_NUM_EXTENSION"),
                                                        lastRegistro.getString("UZGTTELE_NUM_TELEFONO"),
                                                        lastRegistro.getString("UZGTPERSON_CORR"),
                                                        lastRegistro.getLong("UZGTEXTE_ID")));
					}

					return vistaBusqueda;
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/* *
	 * Metodo para guardar la informacion de la Relacion del Personal y la
	 * Extension que se guardara.
         * BD: LOCAL
	 */
	private void guardarDataGuia(long idRegistro, String fono, String exte, ConexionLocal cn) {
		long lastIdTemp = 0;
		ResultSet lastRegistro = null;

		cn.guardarTelefono(fono);

		//consultar el ultimo registro guardado del Telefono para obtener el Secuencial, y luego relacionarlo con la Extension.
		lastRegistro = cn.consultaLastTelefono();
                
		if (lastRegistro == null) {
			System.out.println("Error ultimo registro no Encontrado:");
		} else {
			try {
				while (lastRegistro.next()) {
					lastIdTemp = lastRegistro.getLong("ID_TELE");
				}
				cn.guardarExtension(exte, lastIdTemp);

				// consultar el ultimo registro guardado de la Extension, para
				// luego relacionarlo con el Personal que tendra esa Extensión.
				lastRegistro = cn.consultaLastExte();
				while (lastRegistro.next()) {
					lastIdTemp = lastRegistro.getLong("ID_EXTE");
				}
                                //Relacionamos el registro de la persona con el registro de la extensión
				cn.guardarRelPersonaExt(idRegistro, lastIdTemp);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Error Guardar Personal Extension: "
						+ e.getCause());
				e.printStackTrace();
			}

		}
	}

	/*
	 * Metodo para buscar las Unidades en relacion a la Sede Seleccionada dentro
	 * del Banner.
	 */
	public List<Busqueda> obtenerUnidadesPorSede(String sedeCode) {

		Conexion cn = new Conexion();
		ResultSet res = cn.consultarSedesUnidades(sedeCode);
		List<Busqueda> unidadesAll = new ArrayList<Busqueda>();

		if (res == null) {
			System.out.println("Error No Hay Datos");
		} else {
			try {
				while (res.next()) {
					unidadesAll.add(new Busqueda(
							res.getString("UNIDAD_NOMBRE"), res
									.getString("UNIDAD_NOMBRE")));
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}

		return unidadesAll;
	}

	/*
	 * Metodo para buscar las Unidades en relacion a la Sede Seleccionada dentro
	 * de la base Guia.
	 */
	public List<Busqueda> obtenerUnidadesPorSedeEditar(String sedeCode) {

		ConexionLocal cn = new ConexionLocal();
		ResultSet res = cn.consultarSedesUnidades(sedeCode);
		List<Busqueda> unidadesAll = new ArrayList<Busqueda>();

		if (res == null) {
			System.out.println("Error No Hay Datos");
		} else {
			try {
				while (res.next()) {
					unidadesAll.add(new Busqueda(
							res.getString("CODIGO_UNIDAD"), res
									.getString("CODIGO_UNIDAD")));
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}

		return unidadesAll;
	}

        /* *
	 * Metodo para guardar la informacion de la Relacion del Personal y la
	 * Extension que se guardara.
	 */
	private void actualizarBanner(long pidm, String fono, String exte, Conexion cn) {
                try {
                        cn.actualizarExtensionBanner(pidm, fono, exte);
                } catch (Exception e) {
                        // TODO Auto-generated catch block
                        System.out.println("Error actualizar datos en banner: "+ e.getCause());
                        e.printStackTrace();
                }
	}
}
