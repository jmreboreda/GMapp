/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.comun;

import java.io.IOException;
/**
 *
 * @author jmrb
 */
public class CreacionPDFWithLibreOffice {
public String SysOp = System.getProperty("os.name");
public String userName = System.getProperty("user.name");    
 
    
    public CreacionPDFWithLibreOffice(String libroGuardado) {
        String programa = "";
        // Determinamos Path a LO
        try {
        if (SysOp.equals("Linux"))
            programa = "/usr/lib/libreoffice/program/soffice  --headless --invisible --convert-to pdf " + libroGuardado + " --outdir /tmp";
        else
            programa = "\"C:\\Program Files (x86)\\LibreOffice 5\\program\\soffice.exe\" --headless --invisible " +
                    "-convert-to pdf  --outdir " + "\"C:\\Users\\" + userName + "\\Mis Documentos\\Borrame\" " + "\"" + libroGuardado + "\"";
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
            System.out.println( ex.getMessage() );
        }
    }
}