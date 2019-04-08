package controlador;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Busqueda;
import modelo.Personal;
import modelo.VistaAdministradores;
import conexion.Conexion;
import conexion.ConexionLocal;
import modelo.Instruccion;
//28-07-2017 by CA
import modelo.Titulo;
import modelo.VistaTitulo;

public class ModificarAdmin implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> nombres = new ArrayList<String>();

	public ModificarAdmin() {
	}

	public List<String> getNombres() {
		return nombres;
	}

	public void setNombres(List<String> nombres) {
		this.nombres = nombres;
	}

	public List<VistaAdministradores> obtenerAdmin() {

		ConexionLocal cn = new ConexionLocal();
		ResultSet res = cn.consultaAdministradores();
		List<VistaAdministradores> vistaAdmin = new ArrayList<VistaAdministradores>();

		if (res == null) {
			System.out.println("Error No Hay Datos");
		} else {
			try {
				while (res.next()) {
					vistaAdmin
							.add(new VistaAdministradores(res
									.getString("NOMBREADMIN"), res
									.getString("IDADMIN")));
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return vistaAdmin;
	}

	public List<Busqueda> obtenerUnAdmin(String valor) {

		ConexionLocal cn = new ConexionLocal();
		ResultSet res = cn.consultaUnAdministrador(valor);
		List<Busqueda> vistaAdmin = new ArrayList<Busqueda>();
		if (res == null) {
			System.out.println("Error No Hay Datos");
		} else {
			try {
				while (res.next()) {
					vistaAdmin.add(new Busqueda(res.getString("UZGTSEDE_ID"),
							res.getString("UZGTSEDE_NOMBRE")));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return vistaAdmin;
	}

	public void modificar(VistaAdministradores admin, List<String> sedes) {
		ConexionLocal cn = new ConexionLocal();
		Conexion cnn = new Conexion();
		String nombreSede = "";
		ResultSet registros = null;
		boolean adminMatriz = false;
		long idRegistro = 0;
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
				registros = cnn.consultaCentrosApoyo();

				while (registros.next()) {
					listaDeSedes.add(new Busqueda(registros
							.getString("CODECENTRO"), registros
							.getString("DESCRIPCENTRO")));
				}
			}
			for (String string : sedes) {

				registros = cnn.consultaSedePorId(string);

				while (registros.next()) {
					listaDeSedes.add(new Busqueda(registros
							.getString("CODESEDE"), registros
							.getString("DESCRIP")));
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		
		registros = cn.consultaIdRegistro(admin.getId_persona());
		try {
			registros.next();
			idRegistro = registros.getLong("IDREGISTRO");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cn.eliminarRelacionSedeAdmin(idRegistro);
		for (Busqueda unaSede : listaDeSedes) {
			cn.guardarRelacionSedeAdmin(idRegistro, unaSede.getValor());
		}
	}

	public void eliminar(VistaAdministradores admin) {
		ConexionLocal cn = new ConexionLocal();
		ResultSet s = cn.consultaIdRegistro(admin.getId_persona());
		long idRegistro = 0;
		try {
			s.next();
			idRegistro = s.getLong("IDREGISTRO");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		cn.eliminarRelacionSedeAdmin(idRegistro);
		cn.eliminarAdmin(idRegistro);
	}
        
        
        //28-07-2017 by CA -----------------------------------------------------
        
        public List<VistaTitulo> obtenerTitulos() {

		ConexionLocal cn = new ConexionLocal();
		ResultSet res = cn.consultaListaTrato();
		List<VistaTitulo> vistaTitulo = new ArrayList<VistaTitulo>();

		if (res == null) {
			System.out.println("Error No Hay Datos");
		} else {
			try {
				while (res.next()) {
					vistaTitulo.add(new VistaTitulo(res.getString("UZGTTITULO_ID"), res.getString("TRATO")));
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return vistaTitulo;
	}
        
        public List<Titulo> obtenerUnTitulo(String valor) {

		ConexionLocal cn = new ConexionLocal();
		ResultSet res = cn.consultaUnTitulo(valor);
		List<Titulo>  vistaTitulo = new ArrayList<Titulo>();
                
		if (res == null) {
			System.out.println("Error No Hay Datos");
		} else {
			try {
				while (res.next()) {
					vistaTitulo.add(new Titulo(res.getString("UZGTTITULO_ID"),
							res.getString("UZGTTITULO_DESCRIPCION")));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return vistaTitulo;
	}
        
        public void modificarInstruccion(Instruccion instruccion) {
		ConexionLocal cn = new ConexionLocal();
                
		cn.modificarInstruccion(instruccion.getIdInstruccion(), instruccion.getInstruccion());
	}
        
        public void modificarTitulo(VistaTitulo titulo) {
		ConexionLocal cn = new ConexionLocal();
                
		cn.modificarTitulo(titulo.getIdTitulo(), titulo.getTitulo());
	}
        
        public void insertarTitulo(String titulo) {
		ConexionLocal cn = new ConexionLocal();
                
		cn.insertarTitulo(titulo);
	}
        
        public void eliminarTitulo(VistaTitulo titulo) {
		ConexionLocal cn = new ConexionLocal();

		cn.eliminarTitulo(titulo.getIdTitulo());
	}
        
        public List<Instruccion> obtenerInstrucciones() {
		ConexionLocal cn = new ConexionLocal();
		ResultSet res = cn.consultaListaInstruccion();
		List<Instruccion> vistaInstruccion = new ArrayList<Instruccion>();

		if (res == null) {
			System.out.println("Error No Hay Datos");
		} else {
			try {
				while (res.next()) {
					vistaInstruccion.add(new Instruccion(res.getDouble("CODIGO"), res.getString("INSTRUCCION")));
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return vistaInstruccion;
	}
        
        public List<Instruccion> obtenerUnaInstruccion(String valor) {
		ConexionLocal cn = new ConexionLocal();
		ResultSet res = cn.consultaUnaInstruccion(valor);
		List<Instruccion>  vistaInstruccion = new ArrayList<Instruccion>();
                
		if (res == null) {
			System.out.println("Error No Hay Datos");
		} else {
			try {
				while (res.next()) {
					vistaInstruccion.add(new Instruccion(res.getDouble("UZGTINSTRUCCIONES_ID"),
							res.getString("UZGTINSTRUCCIONES_TEXTO")));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return vistaInstruccion;
	}
}