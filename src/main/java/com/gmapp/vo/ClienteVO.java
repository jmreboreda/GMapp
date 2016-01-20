/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.vo;

import java.util.Date;

public class ClienteVO {
    
    private Integer id;
    private Integer idcliente;
    private String nifcif;
    private Short nifcif_dup;
    private String nom_rzsoc;
    private Short numvez;
    private String cltsg21;
    private Date fdesde;
    private Date fhasta;
    
    private String ccc_inss;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public String getNifcif() {
        return nifcif;
    }

    public void setNifcif(String nifcif) {
        this.nifcif = nifcif;
    }

    public Short getNifcif_dup() {
        return nifcif_dup;
    }

    public void setNifcif_dup(Short nifcif_dup) {
        this.nifcif_dup = nifcif_dup;
    }

    public String getNom_rzsoc() {
        return nom_rzsoc;
    }

    public void setNom_rzsoc(String nom_rzsoc) {
        this.nom_rzsoc = nom_rzsoc;
    }

    public Short getNumvez() {
        return numvez;
    }

    public void setNumvez(Short numvez) {
        this.numvez = numvez;
    }

    public String getCltsg21() {
        return cltsg21;
    }

    public void setCltsg21(String cltsg21) {
        this.cltsg21 = cltsg21;
    }

    public Date getFdesde() {
        return fdesde;
    }

    public void setFdesde(Date fdesde) {
        this.fdesde = fdesde;
    }

    public Date getFhasta() {
        return fhasta;
    }

    public void setFhasta(Date fhasta) {
        this.fhasta = fhasta;
    }

    public String getCcc_inss() {
        return ccc_inss;
    }

    public void setCcc_inss(String ccc_inss) {
        this.ccc_inss = ccc_inss;
    }


}
