/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
/**
 *
 * Date: 24-08-2017 11:28am
 * 
 * @author Slayers20
 */
public class Instruccion implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private double idInstruccion;
    private String instruccion;
    
    public Instruccion() {
    }
    
    public Instruccion(double idInstruccion, String instruccion){
        super();
        this.idInstruccion = idInstruccion ;
        this.instruccion = instruccion;
    }
    
    public String getIdInstruccion(){
        return String.valueOf(idInstruccion);
    }
    
    public void setIdInstruccion(double idInstruccion){
        this.idInstruccion = idInstruccion;
    }
    
    public String getInstruccion(){
        return instruccion;
    }
    
    public void setInstruccion(String instruccion){
        this.instruccion = instruccion;
    }
    
}
