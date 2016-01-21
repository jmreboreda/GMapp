/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.comun;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.odftoolkit.simple.SpreadsheetDocument;
/**
 *
 * @author jmrb
 */
public class SaveDocLibreOfficeToTemp{
    
    private String SysOper;
    private String userHome;
    private String pathFile;
    
    public SaveDocLibreOfficeToTemp(SpreadsheetDocument Document, String nomFile){
        
        SysOper = System.getProperty("os.name");
        userHome = System.getProperty("user.home");
        
        
        LeerPathFromXML path = new LeerPathFromXML();
        pathFile = path.cargarXml("PathToTemp");
        
        if(SysOper.equals("Linux"))  
            pathFile = pathFile + nomFile; 
        else
           pathFile = userHome + pathFile + nomFile;  
        
        System.out.println("nomFileSaveToTemp: " + pathFile);
        
        try {
            
            Document.save(pathFile);
            
        } catch (Exception ex) {
            Logger.getLogger(SaveDocLibreOfficeToTemp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getPathFile() {
        return pathFile;
    }
    
    
}
