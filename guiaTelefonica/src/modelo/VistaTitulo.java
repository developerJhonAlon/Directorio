/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;

/**
 *
 * 28-07-2017
 * 
 * @author Slayers20
 */
public class VistaTitulo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private String idTitulo;
    private String Titulo;
    
    public VistaTitulo(){}
    
    public VistaTitulo(String idTitutlo, String Titulo){
        super();
        this.idTitulo = idTitutlo;
        this.Titulo = Titulo;
    }
    
    public String getTitulo(){
        return Titulo;
    }
    
    public void setTitulo(String Titulo){
        this.Titulo = Titulo;
    }
    
    public String getIdTitulo(){
        return idTitulo;
    }
    
    public void setIdTitulo(String idTitulo){
        this.idTitulo = idTitulo;
    }
}
