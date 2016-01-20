/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.vo;

import java.util.Date;

/**
 *
 * @author jmrb
 */
public class PersonaVO {
    
    private Integer idpersonaPersona;
    private String apellidosPersona;
    private String nom_rzsocPersona;
    private String nifcifPersona;
    private String nifcifdupPersona;
    private String numafssPersona;
    private Date fechanacimPersona;
    private String estcivPersona;
    private String direccionPersona;
    private String localidadPersona;
    private String codpostalPersona;
    private String nivestudPersona;
    private String nacionalidadPersona; 

    public Integer getIdpersona() {
        return idpersonaPersona;
    }

    public void setIdpersona(Integer idpersona) {
        this.idpersonaPersona = idpersona;
    }

    public String getApellidos() {
        return apellidosPersona;
    }

    public void setApellidos(String apellidos) {
        this.apellidosPersona = apellidos;
    }

    public String getNom_rzsoc() {
        return nom_rzsocPersona;
    }

    public void setNom_rzsoc(String nom_rzsoc) {
        this.nom_rzsocPersona = nom_rzsoc;
    }

    public String getNifcif() {
        return nifcifPersona;
    }

    public void setNifcif(String nifcif) {
        this.nifcifPersona = nifcif;
    }

    public String getNifcifdup() {
        return nifcifdupPersona;
    }

    public void setNifcifdup(String nifcifdup) {
        this.nifcifdupPersona = nifcifdup;
    }

    public String getNumafss() {
        return numafssPersona;
    }

    public void setNumafss(String numafss) {
        this.numafssPersona = numafss;
    }

    public Date getFechanacim() {
        return fechanacimPersona;
    }

    public void setFechanacim(Date fechanacim) {
        this.fechanacimPersona = fechanacim;
    }

    public String getEstciv() {
        return estcivPersona;
    }

    public void setEstciv(String estciv) {
        this.estcivPersona = estciv;
    }

    public String getDireccion() {
        return direccionPersona;
    }

    public void setDireccion(String direccion) {
        this.direccionPersona = direccion;
    }

    public String getLocalidad() {
        return localidadPersona;
    }

    public void setLocalidad(String localidad) {
        this.localidadPersona = localidad;
    }

    public String getCodpostal() {
        return codpostalPersona;
    }

    public void setCodpostal(String codpostal) {
        this.codpostalPersona = codpostal;
    }

    public String getNivestud() {
        return nivestudPersona;
    }

    public void setNivestud(String nivestud) {
        this.nivestudPersona = nivestud;
    }

    public String getNacionalidad() {
        return nacionalidadPersona;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidadPersona = nacionalidad;
    }
}
