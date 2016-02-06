/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.utils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jmrb
 */
public class MesesAnno {
    
    private Map<Integer, String> mesesAnno;

    public MesesAnno() {
        
        mesesAnno = new HashMap();
        mesesAnno.put(1, "enero");
        mesesAnno.put(2, "febrero");        
        mesesAnno.put(3, "marzo");        
        mesesAnno.put(4, "abril");        
        mesesAnno.put(5, "mayo");        
        mesesAnno.put(6, "junio");        
        mesesAnno.put(7, "julio");        
        mesesAnno.put(8, "agosto");        
        mesesAnno.put(9, "septiembre");        
        mesesAnno.put(10, "octubre");        
        mesesAnno.put(11, "noviembre");        
        mesesAnno.put(12, "diciembre");

    }
    
    public Map getMesesAnno(){
        return mesesAnno;
    }
}
