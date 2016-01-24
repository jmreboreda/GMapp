/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.common;

import com.gmapp.utilities.StringUtils;
import java.io.IOException;
/**
 *
 * @author jmrb
 */
public class PrintWithLibreOffice {
public String SysOper = System.getProperty("os.name");
public String userName = System.getProperty("user.name");    
 
    
    public PrintWithLibreOffice(String libroGuardado) {
        
        String programa = "";
        try {
        if (SysOper.equals("Linux"))
            programa = StringUtils.getString(StringUtils.LIBREOFFICE_LINUX_PRINT) + libroGuardado;
        else
            programa = StringUtils.getString(StringUtils.LIBREOFFICE_WINDOWS_PRINT) + libroGuardado;
        } catch (Exception e) {
            System.err.println("ERROR: No se ha localizado la instalación de LibreOffice.");
        }
        // Ejecutamos LO
        System.out.println(programa);
        try
        {
            Runtime app = Runtime.getRuntime();
            app.exec(programa);
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}