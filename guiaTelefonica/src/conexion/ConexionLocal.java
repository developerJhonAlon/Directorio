package conexion;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modelo.Busqueda;
import modelo.Personal;
import modelo.VistaBusqueda;
import modelo.VistaProveedor;

public class ConexionLocal {
    private Connection cnn;
    private Statement state;
    private ResultSet res;
    private int conf = 0;

	/* 
	 * Clase que se conecta a la BDD sobre la cual realiza el CRUD de la GuiaTelefonica.
	 */
	public ConexionLocal() {
	}

        /* 
	 * Clase para iniciar la conexión
	 */
        public void iniciarConexion() throws SQLException {
            try{
                Context ctx = new InitialContext();
                DataSource ds = (DataSource) ctx.lookup("jdbc/GUIAT");
                cnn = ds.getConnection();
                
            } catch (NamingException | SQLException ex) {
                System.err.println(ex);
            }
        }
        
        /* 
	 * Clase para cerrar la conexión
	 */
	public void closeConexion() throws SQLException {
		cnn.close();
	}

        /*
	 * Metodo SQL para obtener los datos de persona mediante la extensión para usuarios externos
	 */
	public ResultSet consultaPorExtensionExterno(String valorTexto){
                String query = "SELECT * "
                        + " FROM UZGVEXTEPRO "
                        + " WHERE UZGTEXTE_NUM_EXTENSION LIKE'%"+valorTexto+"%' "
                        + " ORDER BY UZGTPRO_NOMBRES ASC";
                
		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
        
	/*
	 * Metodo SQL para obtener un Personal con su Id Unico.
	 */
	public ResultSet consultaPorId(long identificador) {
		String query = "SELECT * "
                        + " FROM UZGVEXTEPERSON "
                        + " WHERE UZGTPERSON_ID = "+ identificador + "";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodo SQL para obtener un Personal con su Id Unico.
	 */
	public ResultSet consultaPorIdExterno(long identificador) {
		String query = "SELECT * "
                        + "FROM UZGVEXTEPRO "
                        + "WHERE UZGTPRO_ID = "+ identificador;

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodo SQL para obtener datos de la persona mediante el nombre para usuarios externos
	 */
	public ResultSet consultaPorNombreExterno(String valorTexto) {
		String query = "SELECT UPPER(UZGTPRO_NOMBRES) AS UZGTPRO_NOMBRES, UPPER(UZGTPRO_AREA) AS UZGTPRO_AREA, "
				+ " UPPER(UZGTPRO_UNIDAD) AS UZGTPRO_UNIDAD, UPPER(UZGTPRO_CAMPUS) AS UZGTPRO_CAMPUS, "
                                + " UZGTPRO_ID, UZGTEXTE_NUM_EXTENSION, UZGTTELE_NUM_TELEFONO, UZGTEXTE_ID "
                                + " FROM UTIC.UZGVEXTEPRO "
                                + " WHERE UZGTPRO_NOMBRES LIKE UPPER('%"+valorTexto+"%') "
                                + " ORDER BY UZGTPRO_NOMBRES ASC";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	/*
	 * Metodo SQL para obtener los datos de persona con sedes mediante el ID
	 */
	public ResultSet consultaAdminConSedes(String valorTexto) {
		String query = "SELECT UZGTPERSON_ID_PERSONA AS IDADMIN, UZGTPERSON_NOMBRE AS NOMBADMIN, "
                                + " UZGTSEDE_NOMBRE AS NOMBSEDE, UZGTSEDE_ID AS IDSEDE "
                                + " FROM UZGVADMINSEDE "
                                + " WHERE UZGTPERSON_ID_PERSONA = '"+valorTexto+"'";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
		
        /*
	 * Metodo SQL para obtener los datos de persona mediante id propio del aplicativo
	 */
	public ResultSet consultaPorIdAsignado(VistaBusqueda datoDelete) {
		String query = "SELECT * "
                        + "FROM UZGVEXTEPERSON "
                        + "WHERE UZGTEXTE_ID = "+datoDelete.getIdAsignacion();

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
                
        /*
	 * Metodo SQL para obtener los datos de persona mediante la extensión para usuarios internos
	 */
	public ResultSet consultaPorExtension(String valorTexto) {
                String query = "SELECT PEBEMPL_BCAT_CODE, UZGTPERSON_CEDULA, UZGTPERSON_ID_PERSONA, "
                        + " UZGTPERSON_NOMBRE, UZGTPERSON_PUEST, UZGTPERSON_UNIDAD, UZGTPERSON_SEDE, "
                        + " UZGTPERSON_SEDE_CODE, UZGTPERSON_DIREC, UZGTPERSON_CIUDAD, UZGTPERSON_CORR, "
                        + " UZGTTELE_NUM_TELEFONO, UZGTEXTE_NUM_EXTENSION, UZGTPERSON_ID, 0 as UZGTEXTE_ID "
                        + " FROM BANINST1.BZSVGUIAT "
                        + " WHERE UZGTEXTE_NUM_EXTENSION LIKE '%"+valorTexto+ "%' "
                        + " ORDER BY UZGTPERSON_NOMBRE ASC";
                
		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
        
        /*
	 * Metodo SQL para obtener los datos de persona mediante el cargo
	 */
        public ResultSet consultaPorCargo(String valorTexto){
                String query = "SELECT PEBEMPL_BCAT_CODE, UZGTPERSON_CEDULA, UZGTPERSON_ID_PERSONA, "
                        + " UZGTPERSON_NOMBRE, UZGTPERSON_PUEST, UZGTPERSON_UNIDAD, UZGTPERSON_SEDE, "
                        + " UZGTPERSON_SEDE_CODE, UZGTPERSON_DIREC, UZGTPERSON_CIUDAD, UZGTPERSON_CORR, "
                        + " UZGTTELE_NUM_TELEFONO, UZGTEXTE_NUM_EXTENSION, UZGTPERSON_ID, 2 as UZGTEXTE_ID "
                        + " FROM BANINST1.BZSVGUIAT "
                        + " WHERE UPPER(UZGTPERSON_PUEST) LIKE '%"+valorTexto.toUpperCase()+"%' "
                        + " ORDER BY UZGTPERSON_NOMBRE ASC";
                
                try{
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
                }catch(SQLException e){
                    // TODO Auto-generated catch block
			e.printStackTrace();
                }
                return res;
        }
        
        /*
	 * Metodo SQL para obtener datos mediante el nombre para usuarios internos
	 */
	public ResultSet consultaPorNombre(String valorTexto) {
                String query = "SELECT UPPER(UZGTPERSON_NOMBRE) AS UZGTPERSON_NOMBRE, "
                                + " UPPER(UZGTPERSON_PUEST) AS UZGTPERSON_PUEST, "
				+ " UPPER(UZGTPERSON_UNIDAD) AS UZGTPERSON_UNIDAD, "
                                + " UPPER(UZGTPERSON_SEDE) AS UZGTPERSON_SEDE, "
				+ " UPPER(UZGTPERSON_SEDE_CODE) AS UZGTPERSON_SEDE_CODE, "
                                + " UPPER(UZGTPERSON_DIREC) AS UZGTPERSON_DIREC, "
				+ " UPPER(UZGTPERSON_CIUDAD) AS UZGTPERSON_CIUDAD, UZGTPERSON_CORR, UZGTPERSON_ID, "
				+ " UZGTEXTE_NUM_EXTENSION, UZGTTELE_NUM_TELEFONO, "
                                + " 1 as UZGTEXTE_ESTADO, 0 as UZGTEXTE_ID "
                                + " FROM BANINST1.BZSVGUIAT "
                                + " WHERE UZGTPERSON_NOMBRE LIKE UPPER('%"+valorTexto+"%') "
                                + " ORDER BY TRIM(UZGTPERSON_NOMBRE) ASC";
                
		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
        
        /*
	 * Metodo SQL para obtener las Sedes.
	 */
	public ResultSet consultaSedes() {
                String query = "SELECT PTRJBLN_CODE AS CODIGO_SEDE, PTRJBLN_DESC AS NOMBRE_SEDE "
                        + "FROM PTRJBLN "
                        + "WHERE NOT PTRJBLN_CODE LIKE '%CA%'";
                
		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	/*
	 * Metodo SQL para obtener las Centros de Apoyo.
	 */
	public ResultSet consultaCentros() {
		String query = "SELECT PTRJBLN_CODE AS CODIGO_SEDE, PTRJBLN_DESC AS NOMBRE_SEDE "
                        + "FROM PTRJBLN "
                        + "WHERE PTRJBLN_CODE LIKE '%CA%'";
                
		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
        
	/*
	 * Metodo SQL para obtener las Unidades a partir de la Sede Selecciona.
	 */
	public ResultSet consultaUnidades(String sedeCode) {
                String query = "SELECT DISTINCT UPPER(UZGTPERSON_UNIDAD) AS UNIDAD "
                        + " FROM BANINST1.BZSVGUIAT "
                        + " WHERE UZGTPERSON_SEDE_CODE = '"+ sedeCode + "' "
                        + " ORDER BY UNIDAD ASC";
                
		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
        
	/*
	 * Metodo SQL para obtener las personas que tienen Extension en Relacion a las Unidades.
	 */
	public ResultSet consultaUnidadesConExtension(String codeSede, String codeUnidad) {
		String query = "SELECT PEBEMPL_BCAT_CODE, UZGTPERSON_CEDULA, UZGTPERSON_ID_PERSONA, "
                        + " UZGTPERSON_NOMBRE, UZGTPERSON_PUEST, UZGTPERSON_UNIDAD, UZGTPERSON_SEDE, "
                        + " UZGTPERSON_SEDE_CODE, UZGTPERSON_DIREC, UZGTPERSON_CIUDAD, UZGTPERSON_CORR, "
                        + " UZGTTELE_NUM_TELEFONO, UZGTEXTE_NUM_EXTENSION, UZGTPERSON_ID, 0 as UZGTEXTE_ID "
                        + " FROM BANINST1.BZSVGUIAT "
                        + " WHERE UZGTPERSON_SEDE_CODE = '" + codeSede + "' "
                        + " AND UZGTPERSON_UNIDAD = '" + codeUnidad + "' "
                        + " ORDER BY UZGTEXTE_NUM_EXTENSION ASC";
                
		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodo SQL para obtener las Extensiones por medio de la Unidad y Sede seleccionada.
	 */
	public ResultSet consultaExtensionesExternasPorUnidad(String codigoSede, String codigoUnidad, String responsable) {
		String query = "SELECT * "
                        + " FROM UZGVEXTEPRO "
                        + " WHERE UZGTPRO_CAMPUS = '" + codigoSede + "' "
                        + " AND UZGTPRO_UNIDAD= '" + codigoUnidad + "' "
                        + " AND UZGTPRO_RESPONSABLE = '" + responsable + "' "
                        + " ORDER BY UZGTEXTE_NUM_EXTENSION ASC";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodo Sql para obtener las Unidades con la seleccion de una Sede.
	 */
	public ResultSet consultarSedesUnidades(String codigoSede) {
		String query = "SELECT DISTINCT(UZGTPERSON_UNIDAD) AS CODIGO_UNIDAD "
                        + " FROM UZGTPERSON "
                        + " WHERE UZGTPERSON_SEDE_CODE = '" + codigoSede + "' "
                        + " ORDER BY CODIGO_UNIDAD ASC";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodo Sql para obtener las Sedes de los Proveedores en relacion a el Id del Administrador.
	 */
	public ResultSet consultaListaSedeExterna(String codigoAdministrador) {
		String query = "SELECT DISTINCT(UZGTPRO_CAMPUS) AS CODIGO_SEDE_EXTE "
                        + " FROM UZGTPRO "
                        + " WHERE UZGTPRO_RESPONSABLE = '" + codigoAdministrador + "' "
                        + " ORDER BY CODIGO_SEDE_EXTE ASC";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodo Sql para obtener las Unidades de los Proveedores en relacion a el Id de la Sede.
	 */
	public ResultSet consultaListaUnidadExterna(String codigoSede) {
		String query = "SELECT DISTINCT(UZGTPRO_UNIDAD) AS CODIGO_UNIDAD_EXTE "
                        + " FROM UZGTPRO "
                        + " WHERE UZGTPRO_CAMPUS = '" + codigoSede + "' "
                        + " ORDER BY CODIGO_UNIDAD_EXTE ASC";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

        /*
	 * Metodo Sql para obtener los datos de las personas mediante Sede/Unidad
	 */
	public ResultSet consultaPorSedeUnidad(String codeSede, String codeUnidad, String valorTexto) {
                String query = "";
            
                if(codeUnidad.isEmpty() && valorTexto.isEmpty())
                        query = "SELECT PEBEMPL_BCAT_CODE, UZGTPERSON_CEDULA, UZGTPERSON_ID_PERSONA, "
                        + " UZGTPERSON_NOMBRE, UZGTPERSON_PUEST, UZGTPERSON_UNIDAD, UZGTPERSON_SEDE, "
                        + " UZGTPERSON_SEDE_CODE, UZGTPERSON_DIREC, UZGTPERSON_CIUDAD, UZGTPERSON_CORR, "
                        + " UZGTTELE_NUM_TELEFONO, UZGTEXTE_NUM_EXTENSION, UZGTPERSON_ID, 0 as UZGTEXTE_ID "
                        + " FROM BANINST1.BZSVGUIAT "
                        + " WHERE UZGTPERSON_SEDE_CODE = '" + codeSede + "' "
                        + " ORDER BY UZGTPERSON_NOMBRE ASC";
                else if(valorTexto.isEmpty() && !codeUnidad.isEmpty())
                        query = "SELECT PEBEMPL_BCAT_CODE, UZGTPERSON_CEDULA, UZGTPERSON_ID_PERSONA, "
                        + " UZGTPERSON_NOMBRE, UZGTPERSON_PUEST, UZGTPERSON_UNIDAD, UZGTPERSON_SEDE, "
                        + " UZGTPERSON_SEDE_CODE, UZGTPERSON_DIREC, UZGTPERSON_CIUDAD, UZGTPERSON_CORR, "
                        + " UZGTTELE_NUM_TELEFONO, UZGTEXTE_NUM_EXTENSION, UZGTPERSON_ID, 0 as UZGTEXTE_ID "
                        + " FROM BANINST1.BZSVGUIAT "
                        + " WHERE UZGTPERSON_SEDE_CODE = '" + codeSede + "' "
                        + " AND UZGTPERSON_UNIDAD = '" + codeUnidad + "' "
                        + " ORDER BY UZGTPERSON_NOMBRE ASC";
		else if(!valorTexto.isEmpty() && codeUnidad.isEmpty())
                        query = "SELECT PEBEMPL_BCAT_CODE, UZGTPERSON_CEDULA, UZGTPERSON_ID_PERSONA, "
                        + " UZGTPERSON_NOMBRE, UZGTPERSON_PUEST, UZGTPERSON_UNIDAD, UZGTPERSON_SEDE, "
                        + " UZGTPERSON_SEDE_CODE, UZGTPERSON_DIREC, UZGTPERSON_CIUDAD, UZGTPERSON_CORR, "
                        + " UZGTTELE_NUM_TELEFONO, UZGTEXTE_NUM_EXTENSION, UZGTPERSON_ID, 0 as UZGTEXTE_ID "
                        + " FROM BANINST1.BZSVGUIAT "
                        + " WHERE UZGTPERSON_SEDE_CODE = '" + codeSede + "' "
                        + " AND UZGTPERSON_NOMBRE LIKE UPPER('%" + valorTexto + "%') "
                        + " ORDER BY UZGTPERSON_NOMBRE ASC";
                else
                        query = "SELECT PEBEMPL_BCAT_CODE, UZGTPERSON_CEDULA, UZGTPERSON_ID_PERSONA, "
                        + " UZGTPERSON_NOMBRE, UZGTPERSON_PUEST, UZGTPERSON_UNIDAD, UZGTPERSON_SEDE, "
                        + " UZGTPERSON_SEDE_CODE, UZGTPERSON_DIREC, UZGTPERSON_CIUDAD, UZGTPERSON_CORR, "
                        + " UZGTTELE_NUM_TELEFONO, UZGTEXTE_NUM_EXTENSION, UZGTPERSON_ID, 0 as UZGTEXTE_ID "
                        + " FROM BANINST1.BZSVGUIAT "
                        + " WHERE UZGTPERSON_SEDE_CODE = '" + codeSede + "' "
                        + " AND UZGTPERSON_UNIDAD = '" + codeUnidad + "' "
                        + " AND UZGTPERSON_NOMBRE LIKE UPPER('%" + valorTexto + "%') "
                        + " ORDER BY UZGTPERSON_NOMBRE ASC";
                    
		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodos para consultar Todos los miembros de una Sede con extensiones.
	 */
	public ResultSet consultaPorSedeTodos(String codeSede, String valorTexto) {
		String query = "SELECT PEBEMPL_BCAT_CODE, UZGTPERSON_CEDULA, UZGTPERSON_ID_PERSONA, "
                        + " UZGTPERSON_NOMBRE, UZGTPERSON_PUEST, UZGTPERSON_UNIDAD, UZGTPERSON_SEDE, "
                        + " UZGTPERSON_SEDE_CODE, UZGTPERSON_DIREC, UZGTPERSON_CIUDAD, UZGTPERSON_CORR, "
                        + " UZGTTELE_NUM_TELEFONO, UZGTEXTE_NUM_EXTENSION, UZGTPERSON_ID, 0 as UZGTEXTE_ID "
                        + " FROM BANINST1.BZSVGUIAT "
                        + " WHERE UZGTPERSON_SEDE_CODE = '" + codeSede + "' "
                        + " AND UZGTPERSON_NOMBRE LIKE UPPER('%" + valorTexto + "%') "
                        + " ORDER BY UZGTEXTE_NUM_EXTENSION ASC";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodos para consultar el Personal con Extension de una Sede Determinada.
	 */
	public ResultSet consultaUsuarioExtensionPorSede(String valorTexto, String codeSede) {
		String query = "SELECT PEBEMPL_BCAT_CODE, UZGTPERSON_CEDULA, UZGTPERSON_ID_PERSONA, "
                        + " UZGTPERSON_NOMBRE, UZGTPERSON_PUEST, UZGTPERSON_UNIDAD, UZGTPERSON_SEDE, "
                        + " UZGTPERSON_SEDE_CODE, UZGTPERSON_DIREC, UZGTPERSON_CIUDAD, UZGTPERSON_CORR, "
                        + " UZGTTELE_NUM_TELEFONO, UZGTEXTE_NUM_EXTENSION, UZGTPERSON_ID, 0 as UZGTEXTE_ID "
                        + " FROM BANINST1.BZSVGUIAT "
                        + " WHERE UZGTPERSON_SEDE_CODE = '" + codeSede + "' "
                        + " AND UZGTPERSON_NOMBRE LIKE UPPER ('%"+ valorTexto.toUpperCase() + "%') "
                        + " ORDER BY UZGTEXTE_NUM_EXTENSION ASC";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodos para consultar un usuario Externo con Extension de una Sede Determinada.
	 */
	public ResultSet consultaExtensionExternaPorSede(String codeSede, String valorTexto) {
		String query = "SELECT * "
                        + " FROM UZGVEXTEPRO "
                        + " WHERE UZGTPRO_CAMPUS='"+codeSede+"' "
                        + " AND UZGTPRO_NOMBRES LIKE UPPER ('%"+valorTexto+"%') "
                        + " ORDER BY UZGTEXTE_NUM_EXTENSION ASC";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodo para consultar el ultimo registro de Extensiones.
	 */
	public ResultSet consultaLastExte() {
		String query = "SELECT max(UZGTEXTE_ID) AS ID_EXTE "
                        + " FROM UZGTEXTE";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodo para consultar la Relacion entre Administrador y Sedes.
	 */
	public ResultSet consultaFindRelaAdminSede(long codigAdmin, String codigSede) {
		String query = "SELECT UZGTPERSON_ID AS CODADMIN, UZGTSEDE_ID AS CODSEDE "
                        + " FROM UZGTADMINSEDE "
                        + " WHERE UZGTPERSON_ID = " + codigAdmin 
                        + " AND UZGTSEDE_ID = '" + codigSede + "'";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodo para consultar registros de Sedes.
	 */
	public ResultSet consultaFindSede(String codeSede) {
		String query = "SELECT UZGTSEDE_ID AS CODIGO, UZGTSEDE_NOMBRE AS NOMBRE "
                        + "FROM UZGTSEDE "
                        + "WHERE UZGTSEDE_ID = '" + codeSede + "'";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodos para consultar el ultimo registro de Telefonos.
	 */
	public ResultSet consultaLastTelefono() {
		String query = "SELECT MAX(UZGTTELE_ID) AS ID_TELE "
                        + " FROM UZGTTELE";

		try {
                    this.iniciarConexion();
			state = cnn.createStatement();
			res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodos para consultar la existencia de un Admininistrador por ID del Registro.
	 */
	public ResultSet consultaFindAdministrador(long identificador) {
		String query = "SELECT UZGTPERSON_ID AS ID_ADMIN "
                        + " FROM UZGTADMIN "
                        + "WHERE UZGTPERSON_ID = " + identificador;

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodos para consultar la existencia de un Admininistrador.
	 */
	public ResultSet consultarAdministradorFind(String identificador) {
		String query = "SELECT UZGTPERSON_ID_PERSONA AS ID_ADMIN "
                        + " FROM UZGVADMINSEDE "
                        + " WHERE UZGTPERSON_ID_PERSONA= '" + identificador + "'";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodo para consultar la existencia de un ID de Personal dentro de Tabla Vista.
	 */
	public ResultSet consultaFindPersonal(Personal personal, String codeSede, String codeUnidad) {
		String query = "SELECT UZGTPERSON_ID AS IDENTIDAD "
                        + " FROM UZGVEXTEPERSON "
                        + " WHERE UZGTPERSON_ID_PERSONA = '" + personal.getIdDocente() + "' "
                        + " AND UZGTPERSON_SEDE_CODE = '" + codeSede + "' "
                        + " AND UZGTPERSON_UNIDAD = '" + codeUnidad + "'";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodo para consultar la existencia de un Proveedor Exteno dentro de Tabla Vista.
	 */
	public ResultSet consultaProveedor(VistaProveedor vistaProveedor) {
		String query = "SELECT UZGTPRO_ID AS IDENTIDAD "
                        + " FROM UZGVEXTEPRO "
                        + " WHERE UZGTPRO_NOMBRES LIKE UPPER('" + vistaProveedor.getNombreProvee() + "') "
                        + " AND UZGTPRO_CAMPUS = '" + vistaProveedor.getSedeNomb() + "' "
                        + " AND UZGTPRO_UNIDAD = '" + vistaProveedor.getUnidadNomb() + "'";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodo para consultar la existencia de un registro de Personal dentro de Tabla Personal.
	 */
	public ResultSet consultaFindRegistroAuxiliar(Personal personal) {
		String query = "SELECT UZGTPERSON_ID AS IDENTIDAD "
                        + " FROM UZGVEXTEPERSON "
                        + " WHERE UZGTPERSON_ID_PERSONA = '" + personal.getIdDocente() + "' "
                        + " AND UZGTPERSON_SEDE_CODE = '" + personal.getSedeCode() + "' "
                        + " AND UZGTPERSON_UNIDAD = '" + personal.getUnidad() + "'";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodo para consultar la existencia de un ID de Personal en la Tabla para la Edicion.
	 */
	public ResultSet consultaFindPersonalEdicion(VistaBusqueda vista, String codeSede, String codeUnidad) {
		String query = "SELECT UZGTPERSON_ID AS IDENTIDAD "
                        + " FROM UZGVEXTEPERSON "
                        + " WHERE UZGTPERSON_ID_PERSONA = '" + vista.getIdPersonal() + "' "
                        + " AND UZGTPERSON_SEDE_CODE = '" + codeSede + "' "
                        + " AND UZGTPERSON_UNIDAD = '" + codeUnidad + "' "
                        + " AND UZGTPERSON_ID <> " + vista.getIdentidad();

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodo para consultar la existencia de un ID de Personal en la Tabla para la Edicion.
	 */
	public ResultSet consultaFindExternoEdicion(VistaProveedor vistaProveedor) {
		String query = "SELECT UZGTPRO_ID AS IDENTIDAD "
                        + " FROM UZGVEXTEPRO "
                        + " WHERE UZGTPRO_NOMBRES = '" + vistaProveedor.getNombreProvee() + "' "
                        + " AND UZGTPRO_CAMPUS = '" + vistaProveedor.getSedeNomb() + "' "
                        + " AND UZGTPRO_UNIDAD = '" + vistaProveedor.getUnidadNomb() + "' "
                        + " AND UZGTPRO_ID <> " + vistaProveedor.getIdentidad();

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodo para consultar la existencia de un ID de Personal para la Administracion.
	 */
	public ResultSet consultaFindPersonalAdmin(Personal personal) {
		String query = "SELECT UZGTPERSON_ID AS IDENTIDAD "
                        + "FROM UZGTPERSON "
                        + "WHERE UZGTPERSON_ID_PERSONA = '" + personal.getIdDocente() + "'";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodo para consultar la existencia de un ID de Personal para la Administracion.
	 */
	public ResultSet consultaExiteciaAdmin(Personal personal) {
		String query = "SELECT UZGTPERSON_ID_PERSONA "
                        + " FROM UZGVADMINSEDE "
                        + " WHERE UZGTPERSON_ID_PERSONA = '" + personal.getIdDocente() + "'";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodos para consultar el ultimo registro de una Persona.
	 */
	public ResultSet consultaLastPersonal() {
		String query = "SELECT MAX(UZGTPERSON_ID) AS IDREGISTRO "
                        + " FROM UZGTPERSON";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodos para consultar el ultimo registro de un Proveedor Externo.
	 */
	public ResultSet consultaLastProveedor() {
		String query = "SELECT MAX(UZGTPERSON_ID) AS IDREGISTRO "
                        + " FROM UZGTPERSON";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodo para consultar la existencia de un ID en la relacion con una Extension.
	 */
	public ResultSet consultaFindRelacion(VistaBusqueda personal) {
		String query = "SELECT UZGTPERSON_ID AS IDRELACION "
                        + " FROM UZGTEXTEPERSON "
                        + " WHERE UZGTPERSON_ID = '"+personal.getIdPersonal()+"'";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodo para consultar la existencia de Administrador como parte de este
	 * ID para no eliminar la referencia del ID.
	 */
	public ResultSet consultaRelaAdminPersonal(VistaBusqueda personal) {
		String query = "SELECT UZGTPERSON_ID_PERSONA AS ID_ADMIN "
                        + " FROM UZGVADMINSEDE "
                        + " WHERE UZGTPERSON_ID_PERSONA = '" + personal.getIdPersonal() + "'";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

        /*
	 * Metodo para consultar los tratos y desplegar como un combo
	 */
	public ResultSet consultaListaTrato() {
		String query = "SELECT distinct(UZGTTITULO_DESCRIPCION) AS TRATO, UZGTTITULO_ID "
                        + " FROM UZGTTITULO "
                        + " ORDER BY UZGTTITULO_ID ASC";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
        
        /*
	 * Metodo para consultar los tratos y desplegar como un combo
	 */
	public ResultSet consultaListaInstruccion() {
		String query = "SELECT UZGTINSTRUCCIONES_ID AS CODIGO, UZGTINSTRUCCIONES_TEXTO AS INSTRUCCION "
                        + " FROM UZGTINSTRUCCIONES "
                        + " ORDER BY UZGTINSTRUCCIONES_ID ASC";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
        
	/*
	 * Metodos para modificar un Telefono.
	 */
	public int modificarPersonal(long identidad, String codeSede, String nombreSede, String codeUnidad) {
		String query = "UPDATE UZGTPERSON "
                        + " SET UZGTPERSON_SEDE_CODE = '" + codeSede + "' "
                        + " , UZGTPERSON_SEDE = '" + nombreSede + "' "
                        + " , UZGTPERSON_UNIDAD = '" + codeUnidad + "' "
                        + " WHERE UZGTPERSON_ID = " + identidad;

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}

	/*
	 * Metodos para modificar un Extension Exteno.
	 */
	public int modificarExtensionExterno(VistaProveedor vistaProveedor) {
		String query = "UPDATE UZGTPRO "
                        + " SET UZGTPRO_CAMPUS = '" + vistaProveedor.getSedeNomb() + "' "
                        + " , UZGTPRO_UNIDAD = '" + vistaProveedor.getUnidadNomb() + "' "
                        + " , UZGTPRO_ABRE = '" + vistaProveedor.getAbreviat() + "' "
                        + " , UZGTPRO_AREA = '" + vistaProveedor.getAreaNomb() + "' "
                        + " , UZGTPRO_TRATO = '" + vistaProveedor.getTrato() + "' "
                        + " , UZGTPRO_NOMBRES = '" + vistaProveedor.getNombreProvee() + "' "
                        + " WHERE UZGTPRO_ID = " + vistaProveedor.getIdentidad();

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}

	/*
	 * Metodo para guardar una nueva extension.
	 */
	public int guardarPersonal(Personal personal, String codeSede, String nombreSede, String codeUnidad) {
		String query = "INSERT INTO UZGTPERSON(UZGTPERSON_ID,UZGTPERSON_ID_PERSONA,UZGTPERSON_NOMBRE, "
                        + " UZGTPERSON_PUEST,UZGTPERSON_UNIDAD, UZGTPERSON_DIREC, UZGTPERSON_CIUDAD, "
                        + " UZGTPERSON_CORR, UZGTPERSON_SEDE,UZGTPERSON_SEDE_CODE) "
                        + " VALUES (UZGSPERSON.nextval,'"
                        + personal.getIdDocente()
                        + "', '"
                        + personal.getNombres()
                        + "', '"
                        + personal.getPuesto()
                        + "', '"
                        + codeUnidad
                        + "', '"
                        + personal.getDirecInstitu()
                        + "', '"
                        + personal.getCiudadLabora()
                        + "', '"
                        + personal.getCorreo()
                        + "', '"
                        + nombreSede
                        + "', '"
                        + codeSede
                        + "')";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}

	/*
	 * Metodo para guardar el Historico de Cambios.
	 */
	public int guardarHistorico(String idPersona, String nombre, String unidad, String sede, String fono, 
                String exte, String fecha, String idRespo, String respo) {
		String query = "INSERT INTO UZGTREPORT(UZGTREPORT_ID_REPORT, UZGTREPORT_ID_PERSONA, "
                        + " UZGTREPORT_NOMBRE, UZGTREPORT_UNIDAD, UZGTREPORT_SEDE, UZGTREPORT_TELEFONO, "
                        + " UZGTREPORT_EXTENSION, UZGTREPORT_FECHA_MOD, UZGTREPORT_ID_RESPONSABLE, "
                        + " UZGTREPORT_RESPONSABLE) "
                        + " VALUES (UZGSREPORT.nextval, '"
                        + idPersona
                        + "', '"
                        + nombre
                        + "', '"
                        + unidad
                        + "', '"
                        + sede
                        + "', '"
                        + fono
                        + "', '"
                        + exte
                        + "', "
                        + "TO_DATE('"+fecha+"', 'mm/dd/yyyy')"
                        + ", '"
                        + idRespo
                        + "', '"
                        + respo
                        + "')";
                
		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}
	
	/*
	 * Metodo para guardar una nuevo administrador.
	 */
	public int guardarPersonalAdmin(Personal personal) {
		String query = "INSERT INTO UZGTPERSON(UZGTPERSON_ID,UZGTPERSON_ID_PERSONA,UZGTPERSON_NOMBRE,"
                        + " UZGTPERSON_PUEST, UZGTPERSON_UNIDAD, UZGTPERSON_DIREC, UZGTPERSON_CIUDAD, "
                        + " UZGTPERSON_CORR, UZGTPERSON_SEDE, UZGTPERSON_SEDE_CODE) "
                        + " VALUES (UZGSPERSON.nextval, '"
                        + personal.getIdDocente()
                        + "', '"
                        + personal.getNombres()
                        + "', '"
                        + personal.getPuesto()
                        + "', '"
                        + personal.getUnidad()
                        + "', '"
                        + personal.getDirecInstitu()
                        + "', '"
                        + personal.getCiudadLabora()
                        + "', '"
                        + personal.getCorreo()
                        + "', '"
                        + personal.getSede()
                        + "', '"
                        + personal.getSedeCode()
                        + "')";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}

	/*
	 * Metodo para guardar un Administrador.
	 */
	public int guardarAdministrador(long idRegistro) {
		String query = "INSERT INTO UZGTADMIN(UZGTPERSON_ID,UZGTADMIN_ESTADO) "
				+ "VALUES(" + idRegistro + ",1)";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}

	/*
	 * Metodo para guardar Sedes Administradas.
	 */
	public int guardarSedes(Busqueda sedes) {
		String query = "INSERT INTO UZGTSEDE(UZGTSEDE_ID,UZGTSEDE_NOMBRE) "
                        + " VALUES ('" + sedes.getValor() + "', "
                        + "' " + sedes.getDescripcion() + "')";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}

	/*
	 * Metodo para guardar la Relacion de Sede - Administrador.
	 */
	public int guardarRelAdminSede(long admin, Busqueda sedes) {
		String query = "INSERT INTO UZGTADMINSEDE(UZGTPERSON_ID,UZGTSEDE_ID) "
			+ " VALUES(" + admin + ",'" + sedes.getValor() + "')";
		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}

        /*
	 * Metodo para guardar teléfono
	 */
	public int guardarTelefono(String telefono) {
		String query = "INSERT INTO UZGTTELE(UZGTTELE_ID,UZGTTELE_NUM_TELEFONO) "
                        + "VALUES (UZGSTELE.nextval,'" + telefono + "')";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}

        /*
	 * Metodo para guardar Extensión
	 */
	public int guardarExtension(String exte, long idTelefono) {
		String query = "INSERT INTO UZGTEXTE(UZGTEXTE_ID, UZGTTELE_ID, UZGTEXTE_NUM_EXTENSION, UZGTEXTE_ESTADO) "
                        + " VALUES (UZGSEXTE.nextval, "+ idTelefono 
                        + ",'" + exte + "',1)";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}

        /*
         * Metodo para unir el registro de la persona con el de la extensión
         */
	public int guardarRelPersonaExt(long idRegistro, long exte) {
		String query = "INSERT INTO UZGTEXTEPERSON(UZGTPERSON_ID, UZGTEXTE_ID) "
                        + "VALUES ('"+ idRegistro + "'," + exte + ")";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}

        /*
         * Metodo para unir el registro del provedor a la extensión
         */
	public int guardarRelProvedorExtension(VistaProveedor proveedor, String responsable, long exte) {
		String query = "INSERT INTO UZGTPRO(UZGTPRO_ID,UZGTEXTE_ID,UZGTPRO_CAMPUS,UZGTPRO_UNIDAD, "
                        + " UZGTPRO_ABRE, UZGTPRO_AREA,UZGTPRO_TRATO,UZGTPRO_NOMBRES,UZGTPRO_RESPONSABLE) "
                        + " VALUES (UZGSPRO.nextval,"
                        + exte
                        + ", '"
                        + proveedor.getSedeNomb()
                        + "', '"
                        + proveedor.getUnidadNomb()
                        + "', '"
                        + proveedor.getAbreviat()
                        + "', '"
                        + proveedor.getAreaNomb()
                        + "', '"
                        + proveedor.getTrato()
                        + "', '"
                        + proveedor.getNombreProvee()
                        + "', '"
                        + responsable
                        + "')";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}

	/*
	 * Metodos para modificar un Telefono.
	 */
	public int modificarTelefono(long idAsignado, String nuevoFono) {
		String query = "UPDATE UZGTTELE "
                        + " SET UZGTTELE_NUM_TELEFONO = '" + nuevoFono + "' "
                        + " WHERE UZGTTELE_ID = " + idAsignado;

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}

	/*
	 * Metodos para modificar una Extension
	 */
	public int modificarExtension(long idAsignado, String nuevaExten) {
		String query = "UPDATE UZGTEXTE "
                        + " SET UZGTEXTE_NUM_EXTENSION = '" + nuevaExten + "' "
                        + " WHERE UZGTEXTE_ID = " + idAsignado;

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}

        /*
	 * Metodos para eliminar la relación entre la persona y la extensión
         *
         * Las llamadas para eliminar relacion de telefonos internos se han eliminado
         *
	 */
	public int eliminarRelacion(VistaBusqueda eliminar) {
		String query = "DELETE FROM UZGTEXTEPERSON WHERE UZGTPERSON_ID = " + eliminar.getIdentidad();

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}

        /*
	 * Metodos para eliminar la relación entre la persona y la extensión externa
	 */
	public int eliminarRelacionExtesionExterno(VistaProveedor vistaProveedor) {
		String query = "DELETE FROM UZGTPRO WHERE UZGTPRO_ID = "+vistaProveedor.getIdentidad();

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}

        /*
	 * Metodos para eliminar la extensión
         *
         * Se utiliza para eliminar registros de extensiones externos
         * Las llamadas para eliminar extensiones internas se han eliminado
	 */
	public int eliminarExtension(long identificador) {
		String query = "DELETE FROM UZGTEXTE WHERE UZGTEXTE_ID = " + identificador;

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}

        /*
	 * Metodos para eliminar el teléfono
         *
         * Se utiliza para eliminar registros de teléfonos externos
         * Las llamadas para eliminar telefonos internos se han eliminado
	 */
	public int eliminarTelefono(long identificador) {
		String query = "DELETE FROM UZGTTELE WHERE UZGTTELE_ID = "+identificador;

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}

        /*
	 * Metodos para eliminar a la persona
         *
         * En virtud que las tablas son propias de banner, no se puede eliminar datos
         * Las llamadas a esta función se eliminaron
	 */
	public int eliminarIdPersonal(VistaBusqueda eliminar) {
		String query = "DELETE FROM UZGTPERSON WHERE UZGTPERSON_ID = "+eliminar.getIdentidad();

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}

	// ************************************ METODOS MODIFICAR ADMINISTRADORES****************************************
        /*
	 * Metodos para consultar Administradores
	 */
	public ResultSet consultaAdministradores() {
		String query = "SELECT DISTINCT UZGTPERSON_NOMBRE AS NOMBREADMIN, UZGTPERSON_ID_PERSONA AS IDADMIN "
                        + " FROM UZGVADMINSEDE";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

        /*
	 * Metodos para consultar un administrador mediante el ID
	 */
	public ResultSet consultaUnAdministrador(String valor) {
		String query = "SELECT UZGTSEDE_NOMBRE, UZGTSEDE_ID "
                        + " FROM UZGVADMINSEDE "
                        + " WHERE UZGTPERSON_ID_PERSONA = '" + valor + "'";
		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

        /*
	 * Metodos para consultar el ID de registro en las propias tablas del sistema
	 */
	public ResultSet consultaIdRegistro(String valor) {
		String query = "SELECT UZGTPERSON_ID AS IDREGISTRO "
                        + " FROM UZGVADMINSEDE "
                        + " WHERE UZGTPERSON_ID_PERSONA = '" + valor + "'";
                
		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

        /*
	 * Metodos para eliminar la relación entre la sede y un administrador
	 */
	public int eliminarRelacionSedeAdmin(long codigo) {
		String query = "DELETE FROM UZGTADMINSEDE WHERE UZGTPERSON_ID = " + codigo;

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}

        /*
	 * Metodos para guardar la relación entre un admini y la sede
	 */
	public int guardarRelacionSedeAdmin(long idRegistro, String idSede) {
		String query = "INSERT INTO UZGTADMINSEDE VALUES (" + idRegistro + ", '" + idSede + "')";
                
		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}

        /*
	 * Metodos para eliminar un Admin
	 */
	public int eliminarAdmin(long codigo) {
		String query = "DELETE FROM UZGTADMIN WHERE UZGTPERSON_ID = " + codigo;

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}

	/*
	 * Metodos para consultar datos del Master
	 */
	public ResultSet consultarMaster(String identificador) {
		String query = "SELECT UZGTPERSON_ID, UZGTPERSON_NOMBRE "
                        + " FROM UZGTPERSON "
                        + " WHERE UZGTPERSON_ID_PERSONA = '" + identificador + "' "
                        + " ORDER BY UZGTPERSON_ID ASC";
                
		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodos para consultar existencia de Admininistrador Master
	 */
	public ResultSet consultarMasterExis(long val) {
		String query = "SELECT UZGTROL_ID FROM UZGTPERSONROL WHERE UZGTPERSON_ID = " + val;
                
		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * Metodos para consultar Nombre de Admininistrador Master.
	 */
	public ResultSet consultarNomMaster(String identificador) {
		String query = "SELECT UZGTPERSON_NOMBRE "
                        + " FROM UZGTPERSON "
                        + " WHERE UZGTPERSON_ID_PERSONA = '" + identificador + "'";
                
		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
                
        /*
         * Metodos para consultar la Extensión por Sede/Unidad
         */
        public ResultSet consultaExtPorSedeUnidad(String codeSede, String codeUnidad,
			String valorTexto) {
            String query = "";
            
                if(codeUnidad.isEmpty() && valorTexto.isEmpty())
                    query = "SELECT * "
                            + " FROM UZGVEXTEPERSON "
                            + " WHERE UZGTPERSON_SEDE_CODE = '" + codeSede + "' "
                            + " ORDER BY UZGTPERSON_NOMBRE ASC";
                else if(valorTexto.isEmpty() && !codeUnidad.isEmpty())
                    query = "SELECT * "
                            + " FROM UZGVEXTEPERSON "
                            + " WHERE UZGTPERSON_SEDE_CODE = '" + codeSede + "' "
                            + " AND UZGTPERSON_UNIDAD = '" + codeUnidad + "' "
                            + " ORDER BY UZGTPERSON_NOMBRE ASC";
		else if(!valorTexto.isEmpty() && codeUnidad.isEmpty())
                    query = "SELECT * "
                            + " FROM UZGVEXTEPERSON "
                            + " WHERE UZGTPERSON_SEDE_CODE = '" + codeSede + "' "
                            + " AND UZGTPERSON_NOMBRE LIKE UPPER('%" + valorTexto + "%') "
                            + " ORDER BY UZGTPERSON_NOMBRE ASC";
                else
                    query = "SELECT * "
                            + " FROM UZGVEXTEPERSON "
                            + " WHERE UZGTPERSON_SEDE_CODE = '" + codeSede + "' "
                            + " AND UZGTPERSON_UNIDAD = '" + codeUnidad + "' "
                            + " AND UZGTPERSON_NOMBRE LIKE UPPER('%" + valorTexto + "%') "
                            + " ORDER BY UZGTPERSON_NOMBRE ASC";
                    
		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
        
        /*
         * Metodos para consultar un Título
         */
        public ResultSet consultaUnTitulo(String valor) {
		String query = "SELECT uzgttitulo_descripcion, uzgttitulo_id "
                        + " FROM uzgttitulo "
                        + " WHERE uzgttitulo_id = '" + valor + "'";
		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
        
        /*
         * Metodos para modificar una instrucción de patanlla de inicio
         */
        public int modificarInstruccion(String idInstruccion, String instruccion) {
		String query = "UPDATE UZGTINSTRUCCIONES "
                        + " SET UZGTINSTRUCCIONES_TEXTO = '" + instruccion + "' "
                        + " WHERE UZGTINSTRUCCIONES_ID = " + idInstruccion;

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}
        
        /*
         * Metodos para modificar un Título
         */
        public int modificarTitulo(String idTitulo, String titulo) {
		String query = "UPDATE UZGTTITULO "
                        + " SET UZGTTITULO_DESCRIPCION = '" + titulo + "' "
                        + " WHERE UZGTTITULO_ID = " + idTitulo + "";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}
        
        /*
         * Metodos para insertar un Título
         */
        public int insertarTitulo(String titulo) {
		String query = "INSERT INTO UZGTTITULO(UZGTTITULO_ID,UZGTTITULO_DESCRIPCION) "
                        + " VALUES (UZGTTITULOID.nextval, '" + titulo + "')";

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}
        
        /*
         * Metodos para eliminar un Título
         */
        public int eliminarTitulo(String codigo) {
		String query = "DELETE FROM UZGTTITULO WHERE UZGTTITULO_ID = " + codigo;

		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    conf = state.executeUpdate(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conf;
	}
        
        /*
	 * Metodo SQL para obtener las Instrucciones de pantalla de inicio
	 */
	public ResultSet consultaInstrucciones() {
		String query = "SELECT UZGTINSTRUCCIONES_ID AS CODIGO, UZGTINSTRUCCIONES_TEXTO AS INSTRUCCION "
                        + " FROM UZGTINSTRUCCIONES "
                        + " ORDER BY CODIGO";
		
                try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
        
        /*
         * Metodos para consultar una instrucción de pantalla de inicio
         */
        public ResultSet consultaUnaInstruccion(String valor) {
		String query = "SELECT uzgtinstrucciones_texto, uzgtinstrucciones_id "
                        + " FROM uzgtinstrucciones "
                        + " WHERE uzgtinstrucciones_id = '" + valor + "'";
                
		try {
                    this.iniciarConexion();
                    state = cnn.createStatement();
                    res = state.executeQuery(query);
                    this.closeConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
}
