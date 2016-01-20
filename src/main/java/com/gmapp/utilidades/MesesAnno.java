/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.utilidades;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jmrb
 */
public class MesesAnno {
    
    private List<String> nombresMesesAnno;
    private List<Integer> numerosMesesAnno;

    public MesesAnno() {
        
        nombresMesesAnno = new ArrayList();
        nombresMesesAnno.add("enero");
        nombresMesesAnno.add("febrero");        
        nombresMesesAnno.add("marzo");        
        nombresMesesAnno.add("abril");        
        nombresMesesAnno.add("mayo");        
        nombresMesesAnno.add("junio");        
        nombresMesesAnno.add("julio");        
        nombresMesesAnno.add("agosto");        
        nombresMesesAnno.add("septiembre");        
        nombresMesesAnno.add("octubre");        
        nombresMesesAnno.add("noviembre");        
        nombresMesesAnno.add("diciembre");
        
        numerosMesesAnno = new ArrayList();
        numerosMesesAnno.add(1);
        numerosMesesAnno.add(2);
        numerosMesesAnno.add(3);
        numerosMesesAnno.add(4);
        numerosMesesAnno.add(5);
        numerosMesesAnno.add(6);
        numerosMesesAnno.add(7);
        numerosMesesAnno.add(8);
        numerosMesesAnno.add(9);
        numerosMesesAnno.add(10);
        numerosMesesAnno.add(11);
        numerosMesesAnno.add(12);        

    }

    public List<String> getNombresMesesAnno() {
        return nombresMesesAnno;
    }

    public List<Integer> getNumerosMesesAnno() {
        return numerosMesesAnno;
    }
}
