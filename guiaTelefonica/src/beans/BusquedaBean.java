package beans;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import modelo.Busqueda;
import modelo.VistaBusqueda;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import controlador.BusquedaServicio;
import java.util.ArrayList;

@ManagedBean
@ViewScoped
public class BusquedaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/* *
	 * En esta variable invoca a logica de negocio.
	 */
	private BusquedaServicio busquedaServicio = new BusquedaServicio();

	/* *
	 * En esta variable se enlista los criterios de busqueda.
	 */
	private List<Busqueda> busqueda;

	/* *
	 * En esta variable se obtiene la lista de la busqueda realizada.
	 */
	private List<VistaBusqueda> vistaBusqueda ;

	/* *
	 * En esta variable se almacena el codigo del criterio de busqueda.
	 */
	private String valorBusqueda;

	/* *
	 * En esta variable se almacena la informacion capturada para la busqueda.
	 */
	private String textoBuscado;

	/* *
	 * En esta variable se almacena el Objecto de la fila seleccionada.
	 */
	private VistaBusqueda selectedPersona;

	/* *
	 * En esta variable se verifica la visualizacion de las Sedes.
	 */
	private Boolean desplegarSedes = false;

	/* *
	 * En esta variable visualiza departamentos.
	 */
	private Boolean desplegarDepart = false;

	/* *
	 * En esta variable permite visualizar la información seleccionada desde el
	 * grid
	 */
	private Boolean desplegarInf = false;

	private List<Busqueda> listaSedes;

	private List<Busqueda> listaUnida;

	private String sedeSelecciona = "";

	private String unidadSelecciona = "";

	/* *
	 * En esta variable permite validar si se podra realizar una busqueda
	 * general por Sede.
	 */
	private Boolean validarGeneral = true;
	/* *
	 * En esta variable permite visualizar la caja de texto para ingresar
	 * información.
	 */
	private Boolean desplegarEntrada = false;
	/* *
	 * En esta variable permite observar mensaje dentro de la caja de texto como
	 * informativo.
	 */
	private String msgInformativo = "";
	/* *
	 * En esta variable permite realizar la validación de ingreso de busqueda
	 * dentro de la caja de busqueda.
	 */
	private int campoValidador;
	/* *
	 * En esta variable permite determinar el mensaje de la etiqueta de combo
	 * por Sede o Centros de Apoyo.
	 */
	private String etiqueta = "";
        
        /* *
	 * En esta variable se listan las intrucciones que se muestran en pantalla de bienvenida
	 * By CA - 24/08/2017
	 */
        private List<Busqueda> listaInstrucciones;
        
        

	public BusquedaBean() {
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public int getCampoValidador() {
		return campoValidador;
	}

	public Boolean getValidarGeneral() {
		return validarGeneral;
	}

	public void setValidarGeneral(Boolean validarGeneral) {
		this.validarGeneral = validarGeneral;
	}

	public void setCampoValidador(int campoValidador) {
		this.campoValidador = campoValidador;
	}

	public String getMsgInformativo() {
		return msgInformativo;
	}

	public void setMsgInformativo(String msgInformativo) {
		this.msgInformativo = msgInformativo;
	}

	public Boolean getDesplegarEntrada() {
		return desplegarEntrada;
	}

	public void setDesplegarEntrada(Boolean desplegarEntrada) {
		this.desplegarEntrada = desplegarEntrada;
	}

	public List<Busqueda> getListaUnida() {
		return listaUnida;
	}

	public Boolean getDesplegarInf() {
		return desplegarInf;
	}

	public void setDesplegarInf(Boolean desplegarInf) {
		this.desplegarInf = desplegarInf;
	}

	public void setListaUnida(List<Busqueda> listaUnida) {
		this.listaUnida = listaUnida;
	}

	public String getUnidadSelecciona() {
		return unidadSelecciona;
	}

	public void setUnidadSelecciona(String unidadSelecciona) {
		this.unidadSelecciona = unidadSelecciona;
	}

	public List<Busqueda> getListaSedes() {
		return listaSedes;
	}

	public void setListaSedes(List<Busqueda> listaSedes) {
		this.listaSedes = listaSedes;
	}

	public String getSedeSelecciona() {
		return sedeSelecciona;
	}

	public void setSedeSelecciona(String sedeSelecciona) {
		this.sedeSelecciona = sedeSelecciona;
	}

	public Boolean getDesplegarSedes() {
		return desplegarSedes;
	}

	public void setDesplegarSedes(Boolean desplegarSedes) {
		this.desplegarSedes = desplegarSedes;
	}

	public BusquedaServicio getBusquedaServicio() {
		return busquedaServicio;
	}

	public void setBusquedaServicio(BusquedaServicio busquedaServicio) {
		this.busquedaServicio = busquedaServicio;
	}

	public List<Busqueda> getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(List<Busqueda> busqueda) {
		this.busqueda = busqueda;
	}

	public String getValorBusqueda() {
		return valorBusqueda;
	}

	public void setValorBusqueda(String valorBusqueda) {
		this.valorBusqueda = valorBusqueda;
	}

	public String getTextoBuscado() {
		return textoBuscado;
	}

	public void setTextoBuscado(String textoBuscado) {
		this.textoBuscado = textoBuscado;
	}

	public List<VistaBusqueda> getVistaBusqueda() {
		return vistaBusqueda;
	}

	public void setVistaBusqueda(List<VistaBusqueda> vistaBusqueda) {
		this.vistaBusqueda = vistaBusqueda;
	}

	public VistaBusqueda getSelectedPersona() {
		return selectedPersona;
	}

	public void setSelectedPersona(VistaBusqueda selectedPersona) {
		this.selectedPersona = selectedPersona;
	}

	public Boolean getDesplegarDepart() {
		return desplegarDepart;
	}

	public void setDesplegarDepart(Boolean desplegarDepart) {
		this.desplegarDepart = desplegarDepart;
	}

        public List<Busqueda> getListaInstrucciones() {
		return listaInstrucciones;
	}

	public void setListaInstrucciones(List<Busqueda> listaInstrucciones) {
		this.listaInstrucciones = listaInstrucciones;
	}
        
        //Fin de los GET/SET
        
	@PostConstruct
	public void inicializar() {
		this.busqueda = this.busquedaServicio.obtenerCriterios();
                this.listaInstrucciones = this.busquedaServicio.obtenerInstrucciones();
	}

	/*
	 * Metodo para realizar la actualizacion y la busqueda.
	 */
	public void buttonAction() {
		Pattern pattern2;
		Matcher matcher;
                
                //Por Nombre y/o Apellido
		if (this.campoValidador == 2) {
			pattern2 = Pattern.compile("[^0-9]*");
			matcher = pattern2.matcher(this.textoBuscado);

			if (matcher.matches() || (this.textoBuscado == ""))
                            obtenerDatos();
			else 
                            addMessage("Error: Ingrese solo letras");
		//Por Cargo
		} else if (this.campoValidador == 3) {
			pattern2 = Pattern.compile("[^0-9]*");
			matcher = pattern2.matcher(this.textoBuscado);

			if (matcher.matches() || (this.textoBuscado == ""))
                            obtenerDatos();
			else 
                            addMessage("Error: Ingrese solo letras");
		//Por Extensión
		} else if (this.campoValidador == 4) {
			pattern2 = Pattern.compile("[0-9]{3,4}");
			matcher = pattern2.matcher(this.textoBuscado);

			if (matcher.matches())
                            obtenerDatos();
			else 
                            addMessage("Error: Ejm: 1234");
		}
	}

        //Para cargar datos en la buqueda en pantalla principal
	private void obtenerDatos() {
		this.vistaBusqueda = busquedaServicio.buscarExtensiones(
                    this.valorBusqueda, this.textoBuscado.replace(' ', '%'), this.sedeSelecciona, this.unidadSelecciona);
		this.desplegarInf = true;
                
                /*for (VistaBusqueda busqueda1 : vistaBusqueda) {
                    System.out.println(busqueda1.getIdAsignacion());
                }*/
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void onRowSelect(SelectEvent event) {
		System.out.println("Seleccion Row Resultados Busqueda");
		this.desplegarInf = true;
                //System.out.print(selectedPersona.toString());
                System.out.print(((VistaBusqueda) event.getObject()).getIdPersonal());
                selectedPersona = (VistaBusqueda) event.getObject();

		/*FacesMessage msg = new FacesMessage("Información Selecionada",
				((VistaBusqueda) event.getObject()).getIdPersonal());
		FacesContext.getCurrentInstance().addMessage(null, msg);*/
	}

	public void onRowUnselect(UnselectEvent event) {
                this.selectedPersona = null;
		System.out.println("UnSeleccion Row ");
                
		/*this.desplegarInf = false;
		FacesMessage msg = new FacesMessage("Información Selecionada",
				((VistaBusqueda) event.getObject()).getIdPersonal());
		FacesContext.getCurrentInstance().addMessage(null, msg);*/
	}

	public void verSedes() {

		System.out.println("Visualizar Sedes");

		if (this.valorBusqueda.equals("2")) {
			this.textoBuscado = "";
			this.msgInformativo = "Ingrese el nombre";
			this.desplegarEntrada = true;
			this.desplegarSedes = false;
			this.desplegarDepart = false;
			this.campoValidador = 2;
			this.vistaBusqueda = null;
			this.desplegarInf = false;
			this.sedeSelecciona = null;
			this.validarGeneral = true;

		} else if (this.valorBusqueda.equals("3")) {
			this.textoBuscado = "";
			this.msgInformativo = "Ingrese el cargo";
			this.campoValidador = 3;
			this.desplegarEntrada = true;
			this.desplegarSedes = false;
			this.desplegarDepart = false;
			this.vistaBusqueda = null;
			this.desplegarInf = false;
			this.sedeSelecciona = null;
			this.validarGeneral = false;

		} else if (this.valorBusqueda.equals("4")) {
			this.textoBuscado = "";
			this.msgInformativo = "Ingrese la extensión";
			this.campoValidador = 4;
			this.desplegarEntrada = true;
			this.desplegarSedes = false;
			this.desplegarDepart = false;
			this.vistaBusqueda = null;
			this.desplegarInf = false;
			this.sedeSelecciona = null;
			this.validarGeneral = false;
		}

		else if (this.valorBusqueda.equals("5")) {
			this.etiqueta = "Sede:";
			this.desplegarEntrada = false;
			this.campoValidador = 2;
			this.listaSedes = this.busquedaServicio.obtenerSedes();
			this.desplegarSedes = true;
			this.msgInformativo = "Ingrese el nombre";
			this.vistaBusqueda = null;
			this.desplegarInf = false;
			this.desplegarDepart = false;
			this.textoBuscado = "";
			this.unidadSelecciona = null;
			this.validarGeneral = false;

		} else if (this.valorBusqueda.equals("6")) {
			this.etiqueta = "Centro de Apoyo:";
			this.textoBuscado = "";
			this.msgInformativo = "Ingrese el nombre";
			this.listaSedes = this.busquedaServicio.obtenerCentros();
			this.desplegarEntrada = false;
			this.desplegarSedes = true;
			this.desplegarDepart = false;
			this.campoValidador = 2;
			this.vistaBusqueda = null;
			this.desplegarInf = false;
			this.sedeSelecciona = null;
			this.unidadSelecciona = null;
			this.validarGeneral = false;

		} else {
			this.desplegarEntrada = false;
			this.desplegarSedes = false;
			this.vistaBusqueda = null;
			this.desplegarInf = false;
			this.desplegarDepart = false;
			this.sedeSelecciona = null;
			this.validarGeneral = true;

		}

	}

	/* *
	 * Metodo para realizar la busqueda de departamentos en relacion a la Sede.
	 */
	public void verDepartamento() {
		this.unidadSelecciona = null;
		this.desplegarDepart = true;
		this.desplegarEntrada = false;
		this.textoBuscado = "";
		this.listaUnida = this.busquedaServicio
				.obtenerUnidades(this.sedeSelecciona);
		System.out.println("Visualizar Departamento");

	}

	public void verUnidadSede() {
		this.desplegarEntrada = true;

	}
        
        public void verInstrucciones() {
            this.listaInstrucciones = this.busquedaServicio.obtenerInstrucciones();
        }
}