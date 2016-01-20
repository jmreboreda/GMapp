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
public class HorarioContrlabVO {
    
    private Integer id;
    private Integer idcontrlab;
    private Integer numlinea;
    private String diasemana;
    private Date fecha;
    private String amdesde;
    private String amhasta;
    private String pmdesde;
    private String pmhasta;
    private String horas;   

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdcontrlab() {
        return idcontrlab;
    }

    public void setIdcontrlab(Integer idcontrlab) {
        this.idcontrlab = idcontrlab;
    }

    public Integer getNumlinea() {
        return numlinea;
    }

    public void setNumlinea(Integer numlinea) {
        this.numlinea = numlinea;
    }

    public String getDiasemana() {
        return diasemana;
    }

    public void setDiasemana(String diasemana) {
        this.diasemana = diasemana;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getAmdesde() {
        return amdesde;
    }

    public void setAmdesde(String amdesde) {
        this.amdesde = amdesde;
    }

    public String getAmhasta() {
        return amhasta;
    }

    public void setAmhasta(String amhasta) {
        this.amhasta = amhasta;
    }

    public String getPmdesde() {
        return pmdesde;
    }

    public void setPmdesde(String pmdesde) {
        this.pmdesde = pmdesde;
    }

    public String getPmhasta() {
        return pmhasta;
    }

    public void setPmhasta(String pmhasta) {
        this.pmhasta = pmhasta;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }
}

