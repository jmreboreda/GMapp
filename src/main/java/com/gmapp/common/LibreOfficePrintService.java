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
public class LibreOfficePrintService {
    
    private String SysOper;
    private String userName;    
 
    public LibreOfficePrintService(String libroGuardado) {
        
        SysOper = System.getProperty("os.name");
        userName = System.getProperty("user.name");    
        
        String programa = "";
        try {
        if (SysOper.equals("Linux"))
            programa = StringUtils.getString(StringUtils.LIBREOFFICE_LINUX_PRINT) + libroGuardado;
        else
            programa = StringUtils.getString(StringUtils.LIBREOFFICE_WINDOWS_PRINT) + libroGuardado;
        } catch (Exception e) {
            System.err.println("ERROR: No se ha localizado la instalación de LibreOffice.");
        }

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