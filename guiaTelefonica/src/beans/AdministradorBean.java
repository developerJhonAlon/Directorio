package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import modelo.Busqueda;
import modelo.Personal;
import modelo.VistaAdministradores;
import modelo.Titulo;
import modelo.VistaTitulo;
import modelo.Instruccion;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import controlador.AsignarAdministradorServicio;
import controlador.ModificarAdmin;
import controlador.LoginServicio;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean
@ViewScoped
public class AdministradorBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<VistaAdministradores> vistaAdmin;
        //28-07-2017 by CA
        private List<VistaTitulo> vistaTitulo;
        private List<Titulo> vistaUnTitulo;
        private List<Instruccion> vistaInstruccion;
        private List<Instruccion> vistaUnaInstruccion;
        
	private List<Busqueda> vistaUnAdmin;
	private ModificarAdmin modificarAdmin = new ModificarAdmin();
	private AsignarAdministradorServicio asignarServicio = new AsignarAdministradorServicio();
	private String nombreAdministrador = "";
	private String valor;
	private boolean desplegarInf = true;
	private VistaAdministradores selectedPersona;
        //28-07-2017 by CA
        private VistaTitulo selectedTitulo;
        private VistaTitulo tituloModal;
        private boolean desplegarInfT = true;
        private Instruccion instruccionModal;
        private boolean desplegarInfI = true;
        private Instruccion selectedInstruccion;
        
	private VistaAdministradores adminModal;
	private List<Busqueda> listaSedes;
	private List<Busqueda> todasSedes;
	private String textoBusqueda;
	private List<Personal> personal;
	private String administrado;
	private LoginServicio loginServicio = new LoginServicio();
	private String master;

	private Personal administrador;
	private Personal administradorModal;

	private String[] selectSedes;
	List<String> todas = new ArrayList<String>();

	public AdministradorBean() {
	}

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;

	public String getAdministrado() {
		return administrado;
	}

	public void setAdministrado(String administrado) {
		this.administrado = administrado;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public VistaAdministradores getAdminModal() {
		return adminModal;
	}

	public void setAdminModal(VistaAdministradores adminModal) {
		this.adminModal = adminModal;
	}

        //28-07-2017 by CA
        public VistaTitulo getTituloModal(){
            return tituloModal;
        }
        
        public void setTituloModal(VistaTitulo tituloModal){
            this.tituloModal = tituloModal;
        }
        
        public Instruccion getInstruccionModal(){
            return instruccionModal;
        }
        
        public void setInstruccionModal(Instruccion instruccionModal){
            this.instruccionModal = instruccionModal;
        }
        
	public Personal getAdministradorModal() {
		return administradorModal;
	}

	public void setAdministradorModal(Personal administradorModal) {
		this.administradorModal = administradorModal;
	}

	public LoginServicio getLoginServicio() {
		return loginServicio;
	}

	public void setLoginServicio(LoginServicio loginServicio) {
		this.loginServicio = loginServicio;
	}

	public String getNombreAdministrador() {
		return nombreAdministrador;
	}

	public void setNombreAdministrador(String nombreAdministrador) {
		this.nombreAdministrador = nombreAdministrador;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public String getTextoBusqueda() {
		return textoBusqueda;
	}

	public void setTextoBusqueda(String textoBusqueda) {
		this.textoBusqueda = textoBusqueda;
	}

	public List<Personal> getPersonal() {
		return personal;
	}

	public void setPersonal(List<Personal> personal) {
		this.personal = personal;
	}

	public Personal getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Personal administrador) {
		this.administrador = administrador;
	}

	public String[] getSelectSedes() {
		return selectSedes;
	}

	public void setSelectSedes(String[] selectSedes) {
		this.selectSedes = selectSedes;
	}

	public List<String> getTodas() {
		return todas;
	}

	public void setTodas(List<String> todas) {
		this.todas = todas;
	}

	public ModificarAdmin getModificarAdmin() {
		return modificarAdmin;
	}

	public void setModificarAdmin(ModificarAdmin modificarAdmin) {
		this.modificarAdmin = modificarAdmin;
	}

	public AsignarAdministradorServicio getAsignarServicio() {
		return asignarServicio;
	}

	public void setAsignarServicio(AsignarAdministradorServicio asignarServicio) {
		this.asignarServicio = asignarServicio;
	}

	public List<Busqueda> getTodasSedes() {
		return todasSedes;
	}

	public void setTodasSedes(List<Busqueda> todasSedes) {
		this.todasSedes = todasSedes;
	}

	public List<Busqueda> getListaSedes() {
		return listaSedes;
	}

	public void setListaSedes(List<Busqueda> listaSedes) {
		this.listaSedes = listaSedes;
	}

	public VistaAdministradores getSelectedPersona() {
		return selectedPersona;
	}

	public void setSelectedPersona(VistaAdministradores selectedPersona) {
		this.selectedPersona = selectedPersona;
	}
        
        //28-07-2017 by CA
        public VistaTitulo getSelectedTitulo(){
            return selectedTitulo;
        }
        
        public void setSelectedTitulo(VistaTitulo selectedTitulo){
            this.selectedTitulo = selectedTitulo;
        }

        public Instruccion getSelectedInstruccion(){
            return selectedInstruccion;
        }
        
        public void setSelectedInstruccion(Instruccion selectedInstruccion){
            this.selectedInstruccion = selectedInstruccion;
        }
        
	public boolean isDesplegarInf() {
		return desplegarInf;
	}

	public void setDesplegarInf(boolean desplegarInf) {
		this.desplegarInf = desplegarInf;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public void consultarUno() {
		//System.out.println("OBTENER UN ADMINISTRADOR --->>");
		this.vistaUnAdmin = modificarAdmin.obtenerUnAdmin(this.valor);
	}
        
	public List<VistaAdministradores> getVistaAdmin() {
		return vistaAdmin;
	}

	public void setVistaAdmin(List<VistaAdministradores> vistaAdmin) {
		this.vistaAdmin = vistaAdmin;
	}

	public List<Busqueda> getVistaUnAdmin() {
		return vistaUnAdmin;
	}

	public void setVistaUnAdmin(List<Busqueda> vistaUnAdmin) {
		this.vistaUnAdmin = vistaUnAdmin;
	}

        //28-07-2017 by CA
        public List<VistaTitulo> getVistaTitulo(){
            return vistaTitulo;
        }
        
        public void setVistaTitulo(List<VistaTitulo> vistaTitulo){
            this.vistaTitulo = vistaTitulo;
        }
        
        public List<Titulo> getVistaUnTitulo(){
            return vistaUnTitulo;
        }
        
        public void setVistaUnTitulo(List<Titulo> vistaUnTitulo){
            this.vistaUnTitulo = vistaUnTitulo;
        }
        
        public List<Instruccion> getVistaInstruccion(){
            return vistaInstruccion;
        }
        
        public void setVistaInstruccion(List<Instruccion> vistaInstruccion){
            this.vistaInstruccion = vistaInstruccion;
        }
        
        public boolean isDesplegarInfT() {
		return desplegarInfT;
	}

	public void setDesplegarInfT(boolean desplegarInfT) {
		this.desplegarInfT = desplegarInfT;
	}
        
        public boolean isDesplegarInfI() {
		return desplegarInfI;
	}

	public void setDesplegarInfI(boolean desplegarInfI) {
		this.desplegarInfI = desplegarInfI;
	}
        //Fin de los get/set
        
	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

        //Al seleccionar un elemento del grid de administradores
	public void onRowSelect(SelectEvent event) {
		//System.out.println("Seleccion Row");
		this.adminModal = this.selectedPersona;
		this.todas.clear();
		this.vistaUnAdmin = this.modificarAdmin
				.obtenerUnAdmin(this.selectedPersona.getId_persona());
		for (Busqueda busqueda : vistaUnAdmin) {
			this.todas.add(busqueda.getValor());
		}
	}

	public void onRowUnselect(UnselectEvent event) {
		//System.out.println("Seleccion Row ");
		this.todas.clear();
	}

        public void onRowSelect2(SelectEvent event) {
		this.administradorModal = this.administrador;
		this.todas.clear();
	}

	public void onRowUnselect2(UnselectEvent event) {
		this.administradorModal = null;
		this.todas.clear();
	}
        
        //28-07-2017 by CA
        //Al seleccionar un elemento del grid de títulos
        public void onRowSelect3(SelectEvent event) {
		this.tituloModal = this.selectedTitulo;
		this.todas.clear();
                this.vistaUnTitulo = this.modificarAdmin
				.obtenerUnTitulo(this.selectedTitulo.getIdTitulo());
		for (Titulo titulo : vistaUnTitulo) {
			this.todas.add(titulo.getIdTitulo());
		}
	}

	public void onRowUnselect3(UnselectEvent event) {
		//this.tituloModal = null;
		this.todas.clear();
	}
        
        //Al seleccionar un elemento del grid de títulos
        public void onRowSelect4(SelectEvent event) {
		this.instruccionModal = this.selectedInstruccion;
		this.todas.clear();
                this.vistaUnaInstruccion = this.modificarAdmin
				.obtenerUnaInstruccion(this.selectedInstruccion.getIdInstruccion());
		for (Instruccion instruccion : vistaUnaInstruccion) {
			this.todas.add(instruccion.getIdInstruccion());
		}
	}
        
        public void onRowUnselect4(UnselectEvent event) {
		this.instruccionModal = null;
		this.todas.clear();
	}
        
        public void ModificarInstruccion() {
		modificarAdmin.modificarInstruccion(this.selectedInstruccion);
		RequestContext context = RequestContext.getCurrentInstance();
		addMessage("Registro Modificado...");
		context.execute("PF('dlg4').hide()");
	}
        
        public void ModificarTitulo() {
		modificarAdmin.modificarTitulo(this.selectedTitulo);
		RequestContext context = RequestContext.getCurrentInstance();
		addMessage("Registro Modificado...");
		context.execute("PF('dlg4').hide()");
	}
        
        public void InsertarTitulo() {
		modificarAdmin.insertarTitulo(this.tituloModal.getTitulo());
		RequestContext context = RequestContext.getCurrentInstance();
		addMessage("Registro Insertado...");
		context.execute("PF('dlg5').hide()");
                this.vistaTitulo = modificarAdmin.obtenerTitulos();
	}
        
        public void botonAgregarTitulo() {
		this.todas.clear();
                this.tituloModal = new VistaTitulo();
	}
        
        public void EliminarTitulo() {
		modificarAdmin.eliminarTitulo(this.selectedTitulo);
		addMessage("Registro Eliminado...");
		this.vistaTitulo = modificarAdmin.obtenerTitulos();
	}
        
	public void Modificar() {
		modificarAdmin.modificar(this.selectedPersona, todas);
		RequestContext context = RequestContext.getCurrentInstance();
		addMessage("Registro Modificado...");
		context.execute("PF('dlg3').hide()");
	}

	public void Eliminar() {
		modificarAdmin.eliminar(this.selectedPersona);
		addMessage("Registro Eliminado...");
		this.vistaAdmin = modificarAdmin.obtenerAdmin();
	}

	public void botonAsignar() {
		//System.out.println("ASIGNAR ADMINISTRADOR --->>");
		if (this.asignarServicio.guardarAdministrador(this.administrador,
				this.todas) == true) {
			RequestContext context = RequestContext.getCurrentInstance();
			addMessage("Registro Guardado !!");
			// Actualizar la Lista de Administradores.
			this.vistaAdmin = modificarAdmin.obtenerAdmin();
			context.execute("PF('dlg2').hide()");
			this.todas.clear();

		} else if (this.asignarServicio.guardarAdministrador(
				this.administrador, this.todas) == false) {
			addMessage("Registro ya existente !!");
		}
	}

	public void cerrar() {
		System.out.println("Cerrar");

	}

	public void botonAgregar() {
		this.todas.clear();
	}

	public void botonEditar() {
		System.out.println("Botón editar");
		this.desplegarInf = false;
		this.todas.clear();
		this.vistaUnAdmin = this.modificarAdmin
				.obtenerUnAdmin(this.selectedPersona.getId_persona());
		for (Busqueda busqueda : vistaUnAdmin) {
			this.todas.add(busqueda.getValor());
		}
	}

        //28-07-2017 by CA
        public void botonEditar2() {
                this.desplegarInfT = false;
                this.todas.clear();
                //this.vistaTitulo = this.modificarAdmin.obtenerUnTitulo(this.selectedTitulo.getIdTitulo());
                for(Titulo titulo : vistaUnTitulo){
                    this.todas.add(titulo.getIdTitulo());
                }
        }
        
        public void botonEditar3() {
                this.desplegarInfI = false;
                this.todas.clear();
                for(Instruccion instruccion : vistaUnaInstruccion){
                    this.todas.add(String.valueOf(instruccion.getIdInstruccion()));
                }
        }
        
	public void botonVer() {
		System.out.println("Boton Ver");
		this.desplegarInf = true;
		this.todas.clear();
		this.vistaUnAdmin = this.modificarAdmin
				.obtenerUnAdmin(this.selectedPersona.getId_persona());
		for (Busqueda busqueda : vistaUnAdmin) {
			this.todas.add(busqueda.getValor());
		}
	}
	
	public void botonBuscar() {
		addMessage("Buscando Información");
		System.out.println("BUSQUEDA DE PERSONAL BANNER --->>");
		this.personal = this.asignarServicio.buscarPersonal(this.textoBusqueda);
	}

        public void llamarReporte() {
            FacesContext fc = FacesContext.getCurrentInstance();
            try {
                fc.getExternalContext().redirect("http://pentaho.espe.edu.ec:8080/pentaho/api/repos/%3Apublic%3AReportes%3ATransporte%3ALOTAIP_V7.prpt/viewer?userid=consulta&password=consulta");
            } catch (IOException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            fc.responseComplete();

            }
        
	@PostConstruct
	public void inicializar() {

		if (this.loginBean.getIdentificadorMaster().equals("")) {
			System.out.println("ERROR DE LOGIN");
		} else {
			// loginServicio.obtenerAdminMaster(this.loginBean.getIdentificadorMaster());
			// System.out.println("MASTER:"+administrado);
			this.listaSedes = this.asignarServicio.obtenerSedes();
			//System.out.println("LLENADO DE COMBO DE SEDES");
			//System.out.println("OBTENER ADMINISTRADORES --->>");
			this.vistaAdmin = modificarAdmin.obtenerAdmin();
			this.master = loginServicio.nombreMaster(loginBean
					.getIdentificadorMaster());
                        //28-07-2017 by CA
                        this.vistaTitulo = this.modificarAdmin.obtenerTitulos();
                        this.vistaInstruccion = this.modificarAdmin.obtenerInstrucciones();
		}
	}

}