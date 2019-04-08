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

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import modelo.Administrador;
import modelo.Busqueda;
import modelo.VistaBusqueda;
import modelo.VistaProveedor;
import controlador.LoginServicio;
import controlador.ProveedorServicio;

@ManagedBean
@ViewScoped
public class ProveedorBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ProveedorServicio proveedorServicio = new ProveedorServicio();
	private LoginServicio loginServicio = new LoginServicio();
	private List<Administrador> administrado;
	private List<Busqueda> listaSedeExterna;
	private List<Busqueda> listaUnidadExterna;
	private List<VistaProveedor> listaExtensiones;
	private VistaProveedor proveedorSeleccionado;
	private VistaProveedor proveedorSeleccionadoModal;
	private String sedeSeleccionada;
	private String unidadSeleccionada;
	private String valorBusquedaExterno;

	public ProveedorBean() {
	}

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public VistaProveedor getProveedorSeleccionadoModal() {
		return proveedorSeleccionadoModal;
	}

	public void setProveedorSeleccionadoModal(
			VistaProveedor proveedorSeleccionadoModal) {
		this.proveedorSeleccionadoModal = proveedorSeleccionadoModal;
	}

	public String getValorBusquedaExterno() {
		return valorBusquedaExterno;
	}

	public void setValorBusquedaExterno(String valorBusquedaExterno) {
		this.valorBusquedaExterno = valorBusquedaExterno;
	}

	public VistaProveedor getProveedorSeleccionado() {
		return proveedorSeleccionado;
	}

	public void setProveedorSeleccionado(VistaProveedor proveedorSeleccionado) {
		this.proveedorSeleccionado = proveedorSeleccionado;
	}

	public List<VistaProveedor> getListaExtensiones() {
		return listaExtensiones;
	}

	public void setListaExtensiones(List<VistaProveedor> listaExtensiones) {
		this.listaExtensiones = listaExtensiones;
	}

	public String getSedeSeleccionada() {
		return sedeSeleccionada;
	}

	public void setSedeSeleccionada(String sedeSeleccionada) {
		this.sedeSeleccionada = sedeSeleccionada;
	}

	public String getUnidadSeleccionada() {
		return unidadSeleccionada;
	}

	public void setUnidadSeleccionada(String unidadSeleccionada) {
		this.unidadSeleccionada = unidadSeleccionada;
	}

	public List<Administrador> getAdministrado() {
		return administrado;
	}

	public void setAdministrado(List<Administrador> administrado) {
		this.administrado = administrado;
	}

	public List<Busqueda> getListaSedeExterna() {
		return listaSedeExterna;
	}

	public void setListaSedeExterna(List<Busqueda> listaSedeExterna) {
		this.listaSedeExterna = listaSedeExterna;
	}

	public List<Busqueda> getListaUnidadExterna() {
		return listaUnidadExterna;
	}

	public void setListaUnidadExterna(List<Busqueda> listaUnidadExterna) {
		this.listaUnidadExterna = listaUnidadExterna;
	}

	public LoginServicio getLoginServicio() {
		return loginServicio;
	}

	public void setLoginServicio(LoginServicio loginServicio) {
		this.loginServicio = loginServicio;
	}

	public ProveedorServicio getProveedorServicio() {
		return proveedorServicio;
	}

	public void setProveedorServicio(ProveedorServicio proveedorServicio) {
		this.proveedorServicio = proveedorServicio;
	}

	@PostConstruct
	public void Inicializar() {
		//System.out.println("ID ADMINISTRADOR PROVEEDORBEAN: "
		//		+ loginBean.getIdentificador());
		if (this.loginBean.getIdentificador().equals("")) {
			System.out.println("ERROR DE LOGIN");
		} else {
			this.administrado = loginServicio
					.obtenerAdminConSedes(this.loginBean.getIdentificador());
			this.listaSedeExterna = proveedorServicio
					.obtenerListaSedeExterna(administrado.get(0)
							.getIdAdministrador());

		}
	}
	
	public void botonEliminar() {
		System.out.println("ELIIMINAR EXTENSION PROVEEDOR--->>");
		String mensaje = "";

		if (this.proveedorServicio.eliminaRegistro(this.proveedorSeleccionado)) {
			mensaje = "Registro Eliminado !!";
			//Actualizar la lista de Sedes Externas.
			this.listaSedeExterna = proveedorServicio
					.obtenerListaSedeExterna(administrado.get(0)
							.getIdAdministrador());
			//Actualizar la lista de Unidades Externas.
			this.listaUnidadExterna = this.proveedorServicio
					.obtenerListaUnidadExterna(this.sedeSeleccionada);
			// Para observar la eliminar del registro.
			this.listaExtensiones.remove(this.proveedorSeleccionado);
		} else {
			mensaje = "Error no se puedo Eliminar !!";
		}

		addMessage(mensaje);

	}
	

	public void botonAgregar() {

		System.out.println("GUADAR EXTENSION EXTERNO --->>");
		this.proveedorSeleccionadoModal = new VistaProveedor();

	}

	public void botonEditar() {

		System.out.println("GUADAR EXTENSION EXTERNO --->>");
		this.proveedorSeleccionadoModal = this.proveedorSeleccionado;

	}

	public void botonAceptar() {

		System.out.println("GUADAR EXTENSION EXTERNO --->>");

		String mensaje = "";

		if (this.proveedorSeleccionadoModal.getIdentidad() > 0) {
			List<VistaProveedor> auxiliar = new ArrayList<VistaProveedor>();

			auxiliar = this.proveedorServicio
					.editarInformacionExterno(this.proveedorSeleccionadoModal);

			if (auxiliar.size() < 0) {
				mensaje = "Este Registro ya Existe !!";
			} else {
				RequestContext context = RequestContext.getCurrentInstance();
			
				this.listaExtensiones = auxiliar;
				//Actualizar la lista de Sedes Externas.
				this.listaSedeExterna = proveedorServicio
						.obtenerListaSedeExterna(administrado.get(0)
								.getIdAdministrador());
				context.execute("PF('dlg4').hide()");
				mensaje = "Información Modificada !!";

			}

			addMessage(mensaje);

		} else {
			boolean confirmado = this.proveedorServicio.guardarExtensionExtena(
					this.proveedorSeleccionadoModal, this.administrado.get(0)
							.getIdAdministrador());

			if (confirmado) {
				RequestContext context = RequestContext.getCurrentInstance();
				mensaje = "Informacion Guardada !!";
				
				//Actualizar la lista de Sedes Externas.
				this.listaSedeExterna = proveedorServicio
						.obtenerListaSedeExterna(administrado.get(0)
								.getIdAdministrador());
				// Actualizacion de Lista en de combo de las Unidades, en la
				// vista
				// presente.
				this.listaUnidadExterna = this.proveedorServicio
						.obtenerListaUnidadExterna(this.sedeSeleccionada);

				// Actualizacion de Tabla de las Personas con Extensiones, en la
				// vista
				// presente.
				this.listaExtensiones = proveedorServicio
						.obtenerExtensionesExternasPorUnidad(
								this.sedeSeleccionada, this.unidadSeleccionada, administrado.get(0)
								.getIdAdministrador());
				context.execute("PF('dlg4').hide()");

			} else {
				mensaje = "Este Registro ya Existe !!";

			}
			addMessage(mensaje);

		}

	}

	public void busquedaProveedorExterno() {

		System.out.println("BUSQUEDA DE PROVEEDOR EXTERNO --->>");

		this.listaExtensiones = this.proveedorServicio.obtenerProveedorExterno(
				this.sedeSeleccionada, this.valorBusquedaExterno);

		String mensaje = (this.listaExtensiones.size() > 0) ? "Información Encontrada"
				: "Información no Encontrada";
		addMessage(mensaje);
	}

	public void cerrar() {
		System.out.println("Seleccion Row Not");
		// this.personalSelectModal = null;

		// this.personalSelectModal = null;
	}

	public void onRowSelect(SelectEvent event) {
		System.out.println("Seleccion Row Edit");

		this.proveedorSeleccionadoModal = this.proveedorSeleccionado;

	}

	public void onRowUnselect(UnselectEvent event) {
		System.out.println("Seleccion Row Not ");

	}

	public void actualizarUnidades() {
		this.listaUnidadExterna = this.proveedorServicio
				.obtenerListaUnidadExterna(this.sedeSeleccionada);
	}

	public void actualizarExtensiones() {
		this.listaExtensiones = proveedorServicio
				.obtenerExtensionesExternasPorUnidad(this.sedeSeleccionada,
						this.unidadSeleccionada,administrado.get(0)
						.getIdAdministrador());
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
