package beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import controlador.LoginServicio;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

//import beans.MCrypt;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String identificador = "";
    private String identificadorMaster = "";
    private LoginServicio loginServicio = new LoginServicio();

    public LoginBean() {
    }

    public String getIdentificadorMaster() {
        return identificadorMaster;
    }

    public void setIdentificadorMaster(String identificadorMaster) {
        MCrypt encriptador = new MCrypt();
        
        try {
            this.identificadorMaster = new String(encriptador.decrypt(identificadorMaster));
        } catch (Exception ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public LoginServicio getLoginServicio() {
        return loginServicio;
    }

    public void setLoginServicio(LoginServicio loginServicio) {
        this.loginServicio = loginServicio;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        MCrypt encriptador = new MCrypt();
        
        try {
            this.identificador = new String(encriptador.decrypt(identificador));
        } catch (Exception ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void botonLogin(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        
        boolean existencia;
        existencia = loginServicio.comprobarAdmin(this.identificador);
        
        FacesContext fContext = FacesContext.getCurrentInstance();
        ExternalContext extContext = fContext.getExternalContext();
        String ruta = "agregarExtension.jsf?faces-redirect=true";
        
        if (existencia) {
            try {
                context.addCallbackParam("ruta", ruta);
                extContext.redirect(ruta);
            } catch (IOException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            // return "asignarAdministrador?faces-redirect=true";
        }/* else {
            return "error?faces-redirect=true";
        }*/
    }

    public void botonLoginMaster(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        
        boolean existencia = loginServicio
                .comprobarAdminMaster(this.identificadorMaster);
        
        FacesContext fContext = FacesContext.getCurrentInstance();
        ExternalContext extContext = fContext.getExternalContext();
        String ruta = "asignarAdministrador.jsf";
        String error = "error?faces-redirect=true";
        
        if (existencia) {
            try {
                context.addCallbackParam("ruta", ruta);
                extContext.redirect(ruta);
            } catch (IOException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                context.addCallbackParam("ruta", error);
                extContext.redirect(error);
            } catch (IOException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
