package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import modelo.Administrador;
import modelo.Busqueda;
import modelo.Personal;
import modelo.VistaBusqueda;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import controlador.AgregarServicio;
import controlador.AsignarAdministradorServicio;
import controlador.BusquedaServicio;
import controlador.LoginServicio;
import controlador.ModificarServicio;
import java.util.Arrays;

@ManagedBean
@ViewScoped
public class AgregarBean implements Serializable {

	/**
	 * Esta clase sirve para agregar nuevas extensiones
	 */
	private static final long serialVersionUID = 1L;

	private AgregarServicio admiSedeServicio = new AgregarServicio();
	private LoginServicio loginServicio = new LoginServicio();
	private BusquedaServicio busquedaServicio = new BusquedaServicio();
	private AsignarAdministradorServicio asigAdminServicio = new AsignarAdministradorServicio();
	private ModificarServicio modificarServicio = new ModificarServicio();
	private List<VistaBusqueda> listaUnidadExtension;
	private VistaBusqueda extensionSelectModal;
	private VistaBusqueda unidadExtensionSelect;
	private List<Administrador> administrado;
        //27/07/2017 by CA
        private List<Busqueda> tratos;
                
	private String nombreAdministrador = "";
        
        private String idAdministrador = "";
        
	private List<Busqueda> listaUnidades;
	private List<Busqueda> listaUnidadesGuadar;
	private List<Busqueda> listaUnidadesGuadar2;
	private List<Busqueda> listaSedeExtension2;
	private String unidadSeleccionada;
	private String unidadSeleccionadaGuardar;
	private String sedeSeleccionada;
	private String sedeSeleccionadaGuardar;

	private List<Personal> personal;
	private Personal selectPersonal;
	private Personal personalSelectModal;
	private String textoBuscado;
	private String valorBusquedaLocal;
	private String telefono;
	private String extension;

	private boolean validacionDatos = false;

        private List<Busqueda> sedesAdministrado;
        
	public AgregarBean() {
	}

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public boolean isValidacionDatos() {
		return validacionDatos;
	}

	public String getValorBusquedaLocal() {
		return valorBusquedaLocal;
	}

	public void setValorBusquedaLocal(String valorBusquedaLocal) {
		this.valorBusquedaLocal = valorBusquedaLocal;
	}

	public void setValidacionDatos(boolean validacionDatos) {
		this.validacionDatos = validacionDatos;
	}

	public ModificarServicio getModificarServicio() {
		return modificarServicio;
	}

	public void setModificarServicio(ModificarServicio modificarServicio) {
		this.modificarServicio = modificarServicio;
	}

	public Personal getPersonalSelectModal() {
		return personalSelectModal;
	}

	public void setPersonalSelectModal(Personal personalSelectModal) {
		this.personalSelectModal = personalSelectModal;
	}

	public List<Busqueda> getListaSedeExtension2() {
		return listaSedeExtension2;
	}

	public void setListaSedeExtension2(List<Busqueda> listaSedeExtension2) {
		this.listaSedeExtension2 = listaSedeExtension2;
	}

	public VistaBusqueda getExtensionSelectModal() {
		return extensionSelectModal;
	}

	public void setExtensionSelectModal(VistaBusqueda extensionSelectModal) {
		this.extensionSelectModal = extensionSelectModal;
	}

	public AsignarAdministradorServicio getAsigAdminServicio() {
		return asigAdminServicio;
	}

	public void setAsigAdminServicio(
			AsignarAdministradorServicio asigAdminServicio) {
		this.asigAdminServicio = asigAdminServicio;
	}

	public List<Busqueda> getListaUnidadesGuadar2() {
		return listaUnidadesGuadar2;
	}

	public void setListaUnidadesGuadar2(List<Busqueda> listaUnidadesGuadar2) {
		this.listaUnidadesGuadar2 = listaUnidadesGuadar2;
	}

	public VistaBusqueda getUnidadExtensionSelect() {
		return unidadExtensionSelect;
	}

	public String getUnidadSeleccionadaGuardar() {
		return unidadSeleccionadaGuardar;
	}

	public void setUnidadSeleccionadaGuardar(String unidadSeleccionadaGuardar) {
		this.unidadSeleccionadaGuardar = unidadSeleccionadaGuardar;
	}

	public List<Busqueda> getListaUnidadesGuadar() {
		return listaUnidadesGuadar;
	}

	public void setListaUnidadesGuadar(List<Busqueda> listaUnidadesGuadar) {
		this.listaUnidadesGuadar = listaUnidadesGuadar;
	}

	public void setUnidadExtensionSelect(VistaBusqueda unidadExtensionSelect) {
		this.unidadExtensionSelect = unidadExtensionSelect;
	}

	public String getSedeSeleccionadaGuardar() {
		return sedeSeleccionadaGuardar;
	}

	public void setSedeSeleccionadaGuardar(String sedeSeleccionadaGuardar) {
		this.sedeSeleccionadaGuardar = sedeSeleccionadaGuardar;
	}

	public List<VistaBusqueda> getListaUnidadExtension() {
		return listaUnidadExtension;
	}

	public void setListaUnidadExtension(List<VistaBusqueda> listaUnidadExtension) {
		this.listaUnidadExtension = listaUnidadExtension;
	}

	public String getUnidadSeleccionada() {
		return unidadSeleccionada;
	}

	public void setUnidadSeleccionada(String unidadSeleccionada) {
		this.unidadSeleccionada = unidadSeleccionada;
	}

	public String getSedeSeleccionada() {
		return sedeSeleccionada;
	}

	public void setSedeSeleccionada(String sedeSeleccionada) {
		this.sedeSeleccionada = sedeSeleccionada;
	}

	public List<Busqueda> getListaUnidades() {
		return listaUnidades;
	}

	public void setListaUnidades(List<Busqueda> listaUnidades) {
		this.listaUnidades = listaUnidades;
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

        /*25-06-2017 by CA*/
        public String getIdAdministrador() {
		return idAdministrador;
	}

	public void setIdAdministrador(String idAdministrador) {
		this.idAdministrador = idAdministrador;
	}
        
        
	public List<Administrador> getAdministrado() {
		return administrado;
	}

	public void setAdministrado(List<Administrador> administrado) {
		this.administrado = administrado;
	}

        //27/07/2017 by CA
        public List<Busqueda> getTratos() {
		return tratos;
	}
        
        public void setTratos(List<Busqueda> tratos) {
		this.tratos = tratos;
	}
        
        public List<Busqueda> getSedesAdministrado() {
		return sedesAdministrado;
	}
        
        public void setSedesAdministrado(List<Busqueda> sedesAdministrado) {
		this.sedesAdministrado = sedesAdministrado;
	}
        
	public BusquedaServicio getBusquedaServicio() {
		return busquedaServicio;
	}

	public void setBusquedaServicio(BusquedaServicio busquedaServicio) {
		this.busquedaServicio = busquedaServicio;
	}

	public AgregarServicio getAdmiSedeServicio() {
		return admiSedeServicio;
	}

	public void setAdmiSedeServicio(AgregarServicio admiSedeServicio) {
		this.admiSedeServicio = admiSedeServicio;
	}

	public List<Personal> getPersonal() {
		return personal;
	}

	public void setPersonal(List<Personal> personal) {
		this.personal = personal;
	}

	public Personal getSelectPersonal() {
		return selectPersonal;
	}

	public void setSelectPersonal(Personal selectPersonal) {
		this.selectPersonal = selectPersonal;
	}

	public String getTextoBuscado() {
		return textoBuscado;
	}

	public void setTextoBuscado(String textoBuscado) {
		this.textoBuscado = textoBuscado;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	/* *
	 * Obtener el ID del login y obtener todos los datos del admin, para la
	 * interfaz y las consultas.
	 */
	@PostConstruct
	public void inicializar() {
		//System.out.println("ID ADMINISTRADOR AGREGARBEAN: " + loginBean.getIdentificador());
		if (this.loginBean.getIdentificador().equals("")) {
			System.out.println("ERROR DE LOGIN");
		} else {
                    this.administrado = loginServicio.obtenerAdminConSedes(this.loginBean.getIdentificador());
                    this.nombreAdministrador = this.administrado.get(0).getNombAdmin();
                    this.idAdministrador = this.administrado.get(0).getIdAdministrador();
                    //27/07/2017 by CA
                    this.tratos = loginServicio.obtenerTratos();
		}
	}

	public void botonBusqueda() {
		//System.out.println("BUSQUEDA DE PERSONAL EN BANNER --->>");
		//this.personal = this.asigAdminServicio.buscarPersonal(this.textoBuscado);
                
                String sedes="";
                for (Administrador objetoSacado : this.administrado) {
                    sedes += "'"+objetoSacado.getNombSede()+"',";
                }
                int m = Math.max(0, sedes.length() - 1); 
                sedes = sedes.subSequence(0, m).toString();
                System.out.println(sedes);
                
                this.personal = this.asigAdminServicio
				.buscarPersonalExtension(this.textoBuscado, sedes);
                
		String mensaje = (this.personal.size() > 0) ? "Información Encontrada"
				: "Información no Encontrada";
                
		addMessage(mensaje);
	}

	public void busquedaLocal() {
		//System.out.println("BUSQUEDA LOCAL SOBRE GUIA --->>");
		//this.listaUnidadExtension = this.modificarServicio.obtenerExtension(this.valorBusquedaLocal, this.sedeSeleccionada);
                this.listaUnidadExtension = this.modificarServicio.obtenerExtensionSedeUnidad(this.valorBusquedaLocal, this.sedeSeleccionada, this.unidadSeleccionada);

		String mensaje = (this.listaUnidadExtension.size() > 0) ? "Información Encontrada"
				: "Información no Encontrada";
                
		addMessage(mensaje);
	}

	public void botonGuardar() {
		String nombreSede = "";
		String mensaje = "";

		for (Administrador administrador : administrado) {
			if (administrador.getCodigoSede().equals(this.selectPersonal.getSedeCode())){
                            nombreSede = administrador.getNombSede();
                            break;
			}
		}

		boolean confirmado = this.admiSedeServicio.guardarInformacion(
                        this.selectPersonal, this.selectPersonal.getSedeCode(),
                        nombreSede, this.selectPersonal.getUnidad(), this.telefono,
                        this.extension);

		if (confirmado) {
			RequestContext context = RequestContext.getCurrentInstance();
			mensaje = "Informacion Guardada !!";
			// Actualizacion de Lista en de combo de las Unidades, en la vista presente.
			this.listaUnidades = this.busquedaServicio
					.obtenerUnidades(this.sedeSeleccionada);

			// Actualizacion de Tabla de las Personas con Extensiones, en la vista presente.
			this.listaUnidadExtension = admiSedeServicio
					.obtenerUnidadConExtension(this.sedeSeleccionada,
							this.unidadSeleccionada);
			context.execute("PF('dlg2').hide()");
			setTelefono("");
			setExtension("");
		} else {
			mensaje = "Este Registro ya Existe !!";
		}
                
		addMessage(mensaje);
	}

	public void botonEliminar() {
		System.out.println("ELIIMINAR EXTENSION --->>");
		String mensaje = "";

		if (this.modificarServicio.eliminaRegistro(this.unidadExtensionSelect)) {
			mensaje = "Registro Eliminado !!";
			//Actualizar la lista de Sedes Externas.
			this.listaUnidades = this.busquedaServicio
					.obtenerUnidades(this.sedeSeleccionada);
			// Para observar la eliminar del registro.
			this.listaUnidadExtension.remove(this.unidadExtensionSelect);
		} else {
			mensaje = "Error no se puedo Eliminar !!";
		}

		addMessage(mensaje);

	}

	public void botonEditar() {
		String nombreSede = "";
		List<VistaBusqueda> auxiliar = new ArrayList<VistaBusqueda>();
		String mensaje = "";

		for (Administrador administrador : administrado) {
			if (administrador.getCodigoSede().equals(
					this.unidadExtensionSelect.getSedeCodigo())) {
				nombreSede = administrador.getNombSede();
				break;
			}

		}

		auxiliar = this.admiSedeServicio.editarInformacion(
				this.unidadExtensionSelect,
				this.unidadExtensionSelect.getSedeCodigo(), nombreSede,
				this.unidadExtensionSelect.getUnidadNomb(),
				this.unidadExtensionSelect.getTelefonoNomb(),
				this.unidadExtensionSelect.getExtensionNomb());
		
		

		if (auxiliar.size() < 0) {
			mensaje = "Este Registro ya Existe !!";
		} else {
			RequestContext context = RequestContext.getCurrentInstance();

			this.listaUnidadExtension = auxiliar;
			context.execute("PF('dlg3').hide()");
			mensaje = "Información Modificada !!";
			
			Calendar cal1 = Calendar.getInstance();
			String fecha = cal1.get(Calendar.MONTH)+"/"+cal1.get(Calendar.DATE)+"/"+cal1.get(Calendar.YEAR);
			
			this.admiSedeServicio.guardarHistorico(this.unidadExtensionSelect.getIdPersonal(),
					this.unidadExtensionSelect.getPersonalNomb(),
					this.unidadExtensionSelect.getUnidadNomb(),nombreSede,
					this.unidadExtensionSelect.getTelefonoNomb(),this.unidadExtensionSelect.getExtensionNomb(),fecha,this.administrado.get(0).getIdAdministrador(),this.administrado.get(0).getNombAdmin());

		}

		addMessage(mensaje);

	}

	public void actualizarUnidades() {
		this.listaUnidades = this.busquedaServicio
				.obtenerUnidades(this.sedeSeleccionada);
	}

	public void actualizarUnidadesGuardar() {
		this.listaUnidadesGuadar = this.admiSedeServicio
				.obtenerUnidadesPorSede(this.personalSelectModal.getSedeCode());
	}

	public void actualizarUnidadesGuardar2() {
		this.listaUnidadesGuadar2 = this.admiSedeServicio
				.obtenerUnidadesPorSedeEditar(this.extensionSelectModal
						.getSedeCodigo());
	}

	public void actualizarExtensiones() {
		this.listaUnidadExtension = admiSedeServicio.obtenerUnidadConExtension(
				this.sedeSeleccionada, this.unidadSeleccionada);
	}

	public void onRowSelect(SelectEvent event) {
		System.out.println("Seleccion Row New");

		// personalSelectModal = new Personal();
		this.personalSelectModal = this.selectPersonal;
		setTelefono("");
		setExtension("");
		this.listaUnidadesGuadar = this.admiSedeServicio
				.obtenerUnidadesPorSede(this.personalSelectModal.getSedeCode());

	}

	public void onRowUnselect(UnselectEvent event) {
		System.out.println("Seleccion Row Not");
		// this.personalSelectModal = null;
		setTelefono("");
		setExtension("");
	}

	public void cerrar() {
		System.out.println("Seleccion Row Not");
		// this.personalSelectModal = null;
		setTelefono("");
		setExtension("");
		// this.personalSelectModal = null;
	}

	public void cerrar2() {
		System.out.println("Seleccion Row Not");
		// this.personalSelectModal = null;

		// this.personalSelectModal = null;
	}

	public void onRowSelect2(SelectEvent event) {
		System.out.println("Seleccion Row Edit");

		this.extensionSelectModal = this.unidadExtensionSelect;

		List<Busqueda> auxiliar = new ArrayList<Busqueda>();
		for (Administrador administrador : this.administrado) {
			auxiliar.add(new Busqueda(administrador.getCodigoSede(),
					administrador.getNombSede()));
		}

		this.listaSedeExtension2 = auxiliar;
		this.extensionSelectModal.setSedeCodigo(this.unidadExtensionSelect
				.getSedeCodigo());

		this.listaUnidadesGuadar2 = this.admiSedeServicio
				.obtenerUnidadesPorSedeEditar(this.extensionSelectModal
						.getSedeCodigo());

	}

	public void onRowUnselect2(UnselectEvent event) {
		System.out.println("Seleccion Row Not ");
		this.listaUnidadesGuadar2 = this.admiSedeServicio
				.obtenerUnidadesPorSedeEditar(this.unidadExtensionSelect
						.getSedeCodigo());

	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
