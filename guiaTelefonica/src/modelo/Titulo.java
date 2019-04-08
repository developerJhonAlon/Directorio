/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
/**
 *
 * Date: 28-07-2017 11:28am
 * 
 * @author Slayers20
 */
public class Titulo implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private String idTitulo;
    private String Titulo;
    
    public Titulo(){
    }
    
    public Titulo(String idTitulo, String Titulo){
        super();
        this.idTitulo = idTitulo;
        this.Titulo = Titulo;
    }
    
    public String getIdTitulo(){
        return idTitulo;
    }
    
    public void setIdTitulo(String idTitulo){
        this.idTitulo = idTitulo;
    }
    
    public String getTitulo(){
        return Titulo;
    }
    
    public void setTitulo(String Titulo){
        this.Titulo = Titulo;
    }
    
}
