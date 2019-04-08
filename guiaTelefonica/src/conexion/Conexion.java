package conexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Conexion {
    private Connection cnn;
    private Statement state;
    private ResultSet res;
	
	/* 
	 * Clase que conecta a la BDD para la consulta sobre el Banner.
	 */
	public Conexion(){
	}
	
        /*
         * Clase para cerrar la conexión
         */
	public void iniciarConexion() throws SQLException{
		try{
                    Context ctx = new InitialContext();
                    DataSource dsd = (DataSource) ctx.lookup("jdbc/GUIAT");
                    cnn = dsd.getConnection();
                    
                } catch (NamingException ex) {
                    System.err.println(ex);
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
	}
        
        /*
         * Clase para cerrar la conexión
         */
	public void closeConexion() throws SQLException{
		cnn.close();
	}
	
	/* 
	 * Metodo Sql para obtener los datos del Personal en Banner.
	 */
	public ResultSet consultaDatosDePersonal(long IDM){
		String query = "SELECT DISTINCT PEBEMPL.PEBEMPL_BCAT_CODE, "
                    + " (SELECT (SPRIDEN_FIRST_NAME||' ' ||SPRIDEN_LAST_NAME) "
                    + " FROM SPRIDEN WHERE SPRIDEN_PIDM = PEBEMPL.PEBEMPL_PIDM AND SPRIDEN_CHANGE_IND IS NULL) AS NOMBRES, "
                    + " (SELECT SPBPERS_SSN FROM SPBPERS WHERE SPBPERS_PIDM = PEBEMPL.PEBEMPL_PIDM) AS CEDULA, "
                    + " SUBSTR(F_GETSPRIDENID(PEBEMPL_PIDM),1,12) AS ID_DOCENTE, "
                    + " (SELECT NBBPOSN.NBBPOSN_TITLE FROM NBRBJOB, NBBPOSN WHERE NBRBJOB_PIDM = PEBEMPL.PEBEMPL_PIDM "
                    + " AND NBRBJOB.NBRBJOB_POSN = NBBPOSN_POSN AND NBRBJOB.NBRBJOB_CONTRACT_TYPE = 'P' "
                    + " AND NBRBJOB_END_DATE IS NULL) AS PUESTO, "
                    + " (SELECT FTVORGN_TITLE FROM FTVORGN WHERE FTVORGN_ORGN_CODE = PEBEMPL.PEBEMPL_ORGN_CODE_HOME) AS UNIDAD, "
                    + " (SELECT DISTINCT PTRJBLN_DESC FROM PTRJBLN WHERE PTRJBLN_CODE = PEBEMPL_JBLN_CODE) AS SEDE_EMPL, "
                    + " (SELECT DISTINCT PTRJBLN_CODE FROM PTRJBLN WHERE PTRJBLN_CODE = PEBEMPL_JBLN_CODE) AS CODE_SEDE, "
                    + " (SELECT PTRJBLN_ADDRESS1 FROM PTRJBLN WHERE PTRJBLN_CODE = PEBEMPL_JBLN_CODE) AS DIR_INST, "
                    + " (SELECT PTRJBLN_CITY FROM PTRJBLN WHERE PTRJBLN_CODE = PEBEMPL_JBLN_CODE) AS CIU_LAB, "
                    + " (SELECT GOREMAL_EMAIL_ADDRESS FROM GOREMAL WHERE GOREMAL_PIDM = " + IDM + " "
                    + " AND GOREMAL_EMAL_CODE = 'STAN' AND GOREMAL_STATUS_IND = 'A' AND GOREMAL_PREFERRED_IND = 'A') AS CORREO_INST "
                    + " FROM PEBEMPL WHERE PEBEMPL_EMPL_STATUS = 'A' AND PEBEMPL.PEBEMPL_PIDM = " + IDM;

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
	 * Metodo Sql para obtener el ID en relacion al Apellido y una Sede determinada en Banner.
	 */
	public ResultSet consultaPorNombre(String textoIngresado, String codigoSede){
		String query = "SELECT SPRIDEN.SPRIDEN_PIDM AS IDM "
                        + " FROM SPRIDEN, PEBEMPL "
                        + " WHERE PEBEMPL_JBLN_CODE = '" + codigoSede + "' "
                        + " AND SPRIDEN.SPRIDEN_PIDM = PEBEMPL.PEBEMPL_PIDM "
                        + " AND SPRIDEN_LAST_NAME LIKE UPPER('%" + textoIngresado + "%') "
                        + " AND SPRIDEN_CHANGE_IND IS NULL" ;
		
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
	 * Metodo Sql para obtener las Unidades con la seleccion de una Sede dentro del Banner.
	 */
	public ResultSet consultarSedesUnidades(String codigoSede){
		String query = "SELECT DISTINCT (FTVORGN_TITLE) AS UNIDAD_NOMBRE, PEBEMPL_JBLN_CODE "
                        + " FROM PEBEMPL, FTVORGN "
                        + " WHERE PEBEMPL_JBLN_CODE = '" + codigoSede + "' "
                        + " AND SUBSTR(FTVORGN_ORGN_CODE,1,4) = SUBSTR(PEBEMPL.PEBEMPL_ORGN_CODE_HOME,1,4) "
                        + " AND LENGTH(FTVORGN_ORGN_CODE) = 4 "
                        + " ORDER BY UNIDAD_NOMBRE ASC";
		
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
	 * Metodo Sql para obtener el ID en relacion al Apellido en Banner.
         * Se utiliza en la consulta de personas para agregar extensión en (agregarExtensión.jsf)
         */
	public ResultSet consultaAllPorNombre(String textoIngresado){
                textoIngresado = textoIngresado.replace(' ','%');
            
		String query = "SELECT SPRIDEN.SPRIDEN_PIDM AS IDM "
                        + " FROM SPRIDEN, PEBEMPL "
                        + " WHERE SPRIDEN.SPRIDEN_PIDM = PEBEMPL.PEBEMPL_PIDM "
                        + " AND SPRIDEN_FIRST_NAME || ' ' || SPRIDEN_LAST_NAME LIKE UPPER ('%"+textoIngresado+"%')"
                        + " AND SPRIDEN_CHANGE_IND IS NULL";
		
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
	 * Metodo Sql para obtener todas las sedes en Banner.
         */
	public ResultSet consultaSedes(){
		String query = "SELECT PTRJBLN_CODE AS CODESEDE, PTRJBLN_DESC AS DESCRIP "
                        + " FROM PTRJBLN "
                        + " WHERE NOT PTRJBLN_CODE LIKE '%CA%'";
                
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
	 * Metodo Sql para consultar los centros de apoyo
	 */
	public ResultSet consultaCentrosApoyo(){
		String query = "SELECT PTRJBLN_CODE AS CODECENTRO, PTRJBLN_DESC AS DESCRIPCENTRO "
                        + " FROM PTRJBLN "
                        + " WHERE PTRJBLN_CODE LIKE'%CA%'";
                
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
	 * Metodo Sql para consultar la sede por id de sede
	 */
	public ResultSet consultaSedePorId(String codeSede){
		String query = "SELECT PTRJBLN_CODE AS CODESEDE, PTRJBLN_DESC AS DESCRIP "
                        + " FROM PTRJBLN "
                        + " WHERE NOT PTRJBLN_CODE LIKE'%CA%' "
                        + " AND PTRJBLN_CODE = '" + codeSede + "'";
                
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
	 * Metodo Sql para actualizar extensión en banner
	 */
	public ResultSet actualizarExtensionBanner(long PIDM, String fono, String extension){
                //Para obtener la fecha del sistema
		Calendar calendario = Calendar.getInstance();
                String fecha = calendario.get(Calendar.DATE)+"/"+calendario.get(Calendar.MONTH)+"/"+calendario.get(Calendar.YEAR);
		
                String query = "MERGE INTO SATURN.SPRTELE s" +
                               " USING (SELECT "+PIDM+" as SPRTELE_PIDM, 'SC' AS SPRTELE_TELE_CODE FROM dual) z" +
                               " ON (s.SPRTELE_PIDM = z.SPRTELE_PIDM AND s.SPRTELE_TELE_CODE = z.SPRTELE_TELE_CODE)" +
                               " WHEN MATCHED THEN UPDATE SET SPRTELE_PHONE_EXT = '" + extension + "'" +
                               " , SPRTELE_PHONE_NUMBER = '" + fono.replace("-", "") + "'" + 
                               " , SPRTELE_USER_ID = 'MJBALDEON'" +
                               " , SPRTELE_ACTIVITY_DATE = TO_DATE('" + fecha + "', 'dd/mm/yyyy')" +
                               " , SPRTELE_DATA_ORIGIN = 'GUIAT'" +
                               " WHERE SPRTELE_PIDM = " + PIDM +
                               " WHEN NOT MATCHED THEN INSERT " +
                               " (SPRTELE_PIDM, SPRTELE_PHONE_EXT, SPRTELE_PHONE_NUMBER, SPRTELE_USER_ID," +
                               " SPRTELE_ACTIVITY_DATE, SPRTELE_DATA_ORIGIN, SPRTELE_TELE_CODE, SPRTELE_SEQNO)" +
                               " VALUES ("+PIDM+",'"+ extension+ "','"+fono.replace("-", "")+ "'," +
                               " 'MJBALDEON',TO_DATE('" + fecha + "', 'dd/mm/yyyy'),'GUIAT', 'SC', 1)";
                
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
	 * Metodo Sql para obtener el IDM en relacion al ID en Banner.
         * Se utiliza en la consulta de personas para agregar extensión en (agregarExtensión.jsf)
         */
	public ResultSet consultaPorID(String ext_user){
		String query = "SELECT spriden.spriden_pidm AS IDM " +
                               " FROM spriden " +
                               " WHERE spriden_change_ind IS NULL " +
                               " AND SPRIDEN_ID = '" + ext_user + "'";
		
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
	 * Metodo Sql para obtener los datos del Personal en Banner.
	 */
	public ResultSet consultaDatosDePersonalExtension(long IDM, String codeSede){
		String query = "SELECT DISTINCT PEBEMPL.PEBEMPL_BCAT_CODE, "
                    + " (SELECT (SPRIDEN_FIRST_NAME||' ' ||SPRIDEN_LAST_NAME) FROM SPRIDEN "
                    + " WHERE SPRIDEN_PIDM = PEBEMPL.PEBEMPL_PIDM AND SPRIDEN_CHANGE_IND IS NULL) AS NOMBRES, "
                    + " (SELECT SPBPERS_SSN FROM SPBPERS WHERE SPBPERS_PIDM = PEBEMPL.PEBEMPL_PIDM) AS CEDULA, "
                    + " SUBSTR(F_GETSPRIDENID(PEBEMPL_PIDM),1,12) AS ID_DOCENTE, "
                    + " (SELECT NBBPOSN.NBBPOSN_TITLE FROM NBRBJOB, NBBPOSN WHERE NBRBJOB_PIDM = PEBEMPL.PEBEMPL_PIDM "
                    + " AND NBRBJOB.NBRBJOB_POSN = NBBPOSN_POSN AND NBRBJOB.NBRBJOB_CONTRACT_TYPE = 'P' AND NBRBJOB_END_DATE IS NULL) AS PUESTO, "
                    + " (SELECT FTVORGN_TITLE FROM FTVORGN WHERE FTVORGN_ORGN_CODE = PEBEMPL.PEBEMPL_ORGN_CODE_HOME) AS UNIDAD, "
                    + " (SELECT DISTINCT PTRJBLN_DESC FROM PTRJBLN WHERE PTRJBLN_CODE = PEBEMPL_JBLN_CODE) AS SEDE_EMPL, "
                    + " (SELECT DISTINCT PTRJBLN_CODE FROM PTRJBLN WHERE PTRJBLN_CODE = PEBEMPL_JBLN_CODE) AS CODE_SEDE, "
                    + " (SELECT PTRJBLN_ADDRESS1 FROM PTRJBLN WHERE PTRJBLN_CODE = PEBEMPL_JBLN_CODE) AS DIR_INST, "
                    + " (SELECT PTRJBLN_CITY FROM PTRJBLN WHERE PTRJBLN_CODE = PEBEMPL_JBLN_CODE) AS CIU_LAB, "
                    + " (SELECT GOREMAL_EMAIL_ADDRESS FROM GOREMAL WHERE GOREMAL_PIDM = " + IDM
                    + " AND GOREMAL_EMAL_CODE = 'STAN' AND GOREMAL_STATUS_IND = 'A' AND GOREMAL_PREFERRED_IND = 'A') AS CORREO_INST "
                    + " FROM PEBEMPL, PTRJBLN "
                    + " WHERE PEBEMPL.PEBEMPL_JBLN_CODE = PTRJBLN.PTRJBLN_CODE "
                    + " AND PEBEMPL_EMPL_STATUS = 'A' "
                    + " AND PEBEMPL.PEBEMPL_PIDM = " + IDM
                    + " AND PTRJBLN_DESC IN (" + codeSede + ")";
                
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
