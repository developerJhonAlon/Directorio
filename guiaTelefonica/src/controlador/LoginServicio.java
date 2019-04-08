package controlador;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Administrador;
import modelo.Busqueda;
import conexion.ConexionLocal;

public class LoginServicio implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginServicio() {
	}

	/* *
	 * Metodo para comprobar la Existencia de un Administrador
	 */
	public boolean comprobarAdmin(String idAdministrador) {

		ConexionLocal cn = new ConexionLocal();
		ResultSet lastRegistro = null;

		lastRegistro = cn.consultarAdministradorFind(idAdministrador);
		try {
			if (lastRegistro.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public List<Administrador> obtenerAdminConSedes(String idAdministrador) {
		ConexionLocal cn = new ConexionLocal();
                List<Administrador> adminSedes = new ArrayList<Administrador>();
                
		ResultSet res = cn.consultaAdminConSedes(idAdministrador);
                
		if (res == null) {
			System.out.println("Error No Hay Datos");
		} else {
			try {
				while (res.next()) {
					adminSedes.add(new Administrador(res.getString("IDADMIN"),
                                                                         res.getString("NOMBADMIN"), 
                                                                         res.getString("NOMBSEDE"), 
                                                                         res.getString("IDSEDE")));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return adminSedes;
	}

	public boolean comprobarAdminMaster(String idAdministrador) {

		ConexionLocal cn = new ConexionLocal();
		ResultSet lastRegistro = null;
		ResultSet otro = null;

		long v = 0;

		lastRegistro = cn.consultarMaster(idAdministrador);
		try {
			if (lastRegistro.next()) {
				System.out.println(lastRegistro.getLong("UZGTPERSON_ID"));
				v = lastRegistro.getLong("UZGTPERSON_ID");

				otro = cn.consultarMasterExis(v);
				if (otro.next()) {
					System.out.println(otro.getLong("UZGTROL_ID"));
					if (otro.getLong("UZGTROL_ID") == 2) {
						return true;
					}
				} else
					return false;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public String nombreMaster(String idAdministrador) {
		ConexionLocal cn = new ConexionLocal();
		ResultSet lastRegistro = null;
		String nom = "";
		try {
			lastRegistro = cn.consultarNomMaster(idAdministrador);
			if (lastRegistro.next()) {
				System.out.println(lastRegistro.getString("UZGTPERSON_NOMBRE"));
				nom = lastRegistro.getString("UZGTPERSON_NOMBRE");
				return nom;
			}

		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return "";
	}

        //-------------------------------- Cambios a partir de 27-07-2017 by CA ----------------------------------------
        
        public List<Busqueda> obtenerTratos() {
		ConexionLocal cn = new ConexionLocal();
		ResultSet res = cn.consultaListaTrato();

		List<Busqueda> tratos = new ArrayList<Busqueda>();
		if (res == null) {
			System.out.println("Error No Hay Datos");
		} else {
			try {
				while (res.next()) {
                                        tratos.add(new Busqueda(res.getString("TRATO"), res.getString("TRATO")));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tratos;
	}
        
        
}