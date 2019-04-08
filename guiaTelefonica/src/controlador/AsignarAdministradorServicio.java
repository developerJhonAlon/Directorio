package controlador;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Busqueda;
import modelo.Personal;
import conexion.Conexion;
import conexion.ConexionLocal;

public class AsignarAdministradorServicio implements Serializable {

	/**
	 * 7A
	 */
	private static final long serialVersionUID = 1L;

	public AsignarAdministradorServicio() {
	}

	/*
	 * Metodo para asignar Administradores.
	 */

	public boolean guardarAdministrador(Personal administrador, List<String> sedes) {

		ConexionLocal cn = new ConexionLocal();

		ResultSet existeRegistro = cn.consultaExiteciaAdmin(administrador);
		try {

			if (existeRegistro.next()) {
				return false;
			} else {
				ResultSet lastRegistro = cn
						.consultaFindPersonalAdmin(administrador);
				long idRegistro = 0;

				if (lastRegistro.next()) {
					idRegistro = lastRegistro.getLong("IDENTIDAD");
					guardarAdminSedes(idRegistro, sedes, cn);
					return true;
				} else {

					int confirma = cn.guardarPersonalAdmin(administrador);

					if (confirma != 1) {
						System.out
								.println("Error Dato de Personal no guardado");

					} else {
						lastRegistro = cn
								.consultaFindPersonalAdmin(administrador);

						if (lastRegistro.next()) {
							idRegistro = lastRegistro.getLong("IDENTIDAD");
							guardarAdminSedes(idRegistro, sedes, cn);
							return true;
						}
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
	 * Metodo para guardar la informacion para la Guia.
	 */
	private void guardarAdminSedes(long identificador, List<String> sedes,
			ConexionLocal cn) {

		ResultSet lastRegistro = cn.consultaFindAdministrador(identificador);

		try {
			if (lastRegistro.next()) {

				guardarRelAdminSedes(identificador, sedes, cn);
				System.out.println("Administrador ya esta registrado");
			} else {
				int actividad = cn.guardarAdministrador(identificador);

				if (actividad != 1) {

					System.out.println("Error de Guardar Administrador");
				} else {
					guardarRelAdminSedes(identificador, sedes, cn);
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void guardarRelAdminSedes(long idRegistro, List<String> sedes,
			ConexionLocal cnLocal) {

		Conexion cnRemoto = new Conexion();
		ResultSet registroSede = null;
		boolean adminMatriz = false;
		List<Busqueda> listaDeSedes = new ArrayList<Busqueda>();

		for (String busqueda : sedes) {
			if (busqueda.equals("ESPE-M")) {
				adminMatriz = true;
				break;
			}

		}

		try {
			if (adminMatriz) {
				// Obtener los centros de apoyo
				registroSede = cnRemoto.consultaCentrosApoyo();

				while (registroSede.next()) {
					listaDeSedes.add(new Busqueda(registroSede
							.getString("CODECENTRO"), registroSede
							.getString("DESCRIPCENTRO")));
				}
			}
			for (String string : sedes) {

				registroSede = cnRemoto.consultaSedePorId(string);

				while (registroSede.next()) {
					listaDeSedes.add(new Busqueda(registroSede
							.getString("CODESEDE"), registroSede
							.getString("DESCRIP")));
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		// Guardar las Sedes en la Base Local si esta no exite.
		for (Busqueda busqueda : listaDeSedes) {
			registroSede = cnLocal.consultaFindSede(busqueda.getValor());

			try {
				if (registroSede.next()) {
					System.out.println("Sede Ya existe");

				} else {
					cnLocal.guardarSedes(busqueda);

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// Guardar la relacion entre Administradores y Sedes.
		for (Busqueda busqueda : listaDeSedes) {
			registroSede = cnLocal.consultaFindRelaAdminSede(idRegistro,
					busqueda.getValor());

			try {
				if (registroSede.next()) {

					System.out.println("Sede_Admin Ya existe");
				} else {
					cnLocal.guardarRelAdminSede(idRegistro, busqueda);

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/*
	 * Metodo para obtener Sedes registrados en Banner.
	 */
	public List<Busqueda> obtenerSedes() {

		Conexion cn = new Conexion();
		ResultSet res = cn.consultaSedes();
		List<Busqueda> sedes = new ArrayList<Busqueda>();

		if (res == null) {
			System.out.println("Error No Hay Datos");
		} else {

			try {
				while (res.next()) {

					sedes.add(new Busqueda(res.getString("CODESEDE"), res
							.getString("DESCRIP")));
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}

		return sedes;
	}

	/* *
	 * Metodo para obtener los datos del personal del Banner
	 */
	public List<Personal> buscarPersonal(String textoIngresado) {
		Conexion cn = new Conexion();

		ResultSet consultaIDM = null;

		ResultSet consultaDatos = null;

		List<String> pidm = new ArrayList<String>();
		List<Personal> personal = new ArrayList<Personal>();

		consultaIDM = cn.consultaAllPorNombre(textoIngresado);

		// Obtencion de todos los IDM del Banner en relacion a su Apellido.
		try {
			while (consultaIDM.next()) {
				pidm.add(consultaIDM.getString("IDM"));}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error SQL IDM de Ban:" + e.getCause());
			e.printStackTrace();
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
								.getString("NOMBRES"), consultaDatos
								.getString("CEDULA"), consultaDatos
								.getString("ID_DOCENTE"), consultaDatos
								.getString("PUESTO"), consultaDatos
								.getString("UNIDAD"), consultaDatos
								.getString("SEDE_EMPL"), consultaDatos
								.getString("CODE_SEDE"), consultaDatos
								.getString("DIR_INST"), consultaDatos
								.getString("CIU_LAB"), consultaDatos
								.getString("CORREO_INST")));
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

        //by CA
        
        /* *
	 * Metodo para obtener los datos del personal del Banner
	 */
	public List<Personal> buscarPersonalExtension(String textoIngresado, String codeSede) {
		Conexion cn = new Conexion();

		ResultSet consultaIDM = null;

		ResultSet consultaDatos = null;

		List<String> pidm = new ArrayList<String>();
		List<Personal> personal = new ArrayList<Personal>();

		consultaIDM = cn.consultaAllPorNombre(textoIngresado);

		// Obtencion de todos los IDM del Banner en relacion a su Apellido.
		try {
			while (consultaIDM.next()) {
				pidm.add(consultaIDM.getString("IDM"));}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error SQL IDM de Ban:" + e.getCause());
			e.printStackTrace();
		}

		for (String pidm2 : pidm) {
			consultaDatos = cn.consultaDatosDePersonalExtension(Integer.parseInt(pidm2), codeSede);
			// Obtencion de todos los Datos en relacion al IDM
			if (consultaDatos == null) {
				System.out.println("Error No Hay Datos, IDM Ban:");
			} else {
				try {
					while (consultaDatos.next()) {
						personal.add(new Personal(consultaDatos
								.getString("NOMBRES"), consultaDatos
								.getString("CEDULA"), consultaDatos
								.getString("ID_DOCENTE"), consultaDatos
								.getString("PUESTO"), consultaDatos
								.getString("UNIDAD"), consultaDatos
								.getString("SEDE_EMPL"), consultaDatos
								.getString("CODE_SEDE"), consultaDatos
								.getString("DIR_INST"), consultaDatos
								.getString("CIU_LAB"), consultaDatos
								.getString("CORREO_INST")));
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
        
}
