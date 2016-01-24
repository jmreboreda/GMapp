/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.app.registrohorario;

import com.gmapp.common.LibreOfficePDFService;
import com.gmapp.common.LibreOfficePrintService;
import com.gmapp.common.ReadPathFromXML;
import com.gmapp.common.SaveDocLibreOfficeToTemp;
import com.gmapp.utilities.DocODFUtils;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.style.StyleTypeDefinitions;
import org.odftoolkit.simple.table.Table;

/**
 *
 * @author jmrb
 */
public final class RegistroHorario{
    
    private String SysOper;
    private String userHome;
    private SpreadsheetDocument libroRH;
    private Table hojaRH;
    private String nomFileSave;
    private String pathToSave;
    private InputStream archivoODF;
    
    public RegistroHorario(String mes, String anno, String clienteGM,
            String CCC, String nomEmpleado, String nifEmpleado, String jornada){
        
        SysOper = System.getProperty("os.name");
        userHome = System.getProperty("user.home");
        
        loadRegistroHorario();
        getHojaRH();
        rellenarRegistroHorarioRH(mes, anno, clienteGM, CCC, nomEmpleado, nifEmpleado, jornada); 
    }
    
    public SpreadsheetDocument loadRegistroHorario() {
   
        String hoja = DocODFUtils.getODFdoc(DocODFUtils.ODF_DGM002);
        archivoODF = RegistroHorario.class.getResourceAsStream(hoja);      
        try {
            libroRH = (SpreadsheetDocument) SpreadsheetDocument.loadDocument(archivoODF);
        } catch (Exception e) {
            System.err.println("ERROR: No se ha cargado el archivo "
                    + "\"DGM_002_Registro_Horario_Tiempo_Parcial_LO.ods\"");
        }
        return libroRH;
   }
    
    public Table getHojaRH(){
        
        hojaRH = libroRH.getSheetByIndex(0);
        return hojaRH;
    }
    
    public void rellenarRegistroHorarioRH(String mes, String anno, String clienteGM,
            String CCC, String nomEmpleado, String nifEmpleado, String jornada){
        
        hojaRH.getCellByPosition("L5").setStringValue(mes);
        hojaRH.getCellByPosition("N5").setStringValue(anno);
        hojaRH.getCellByPosition("L10").setStringValue(clienteGM); // Cliente GM
        if(clienteGM.length() > 35)       // Comprobar largo nombre cliente GM
        {
            org.odftoolkit.simple.style.Font fuenteMenor = new  org.odftoolkit.simple.style.Font("Arial",
                    StyleTypeDefinitions.FontStyle.BOLD, 8D);
            hojaRH.getCellByPosition("L10").setFont(fuenteMenor);
        }
        hojaRH.getCellByPosition("L12").setStringValue(CCC); // CCC cliente GM
        hojaRH.getCellByPosition("L16").setStringValue(nomEmpleado); // Nombre empleado
        hojaRH.getCellByPosition("L18").setStringValue(nifEmpleado); // NIF empleado
        hojaRH.getCellByPosition("L20").setStringValue(jornada); // Jornada
        hojaRH.getCellByPosition("L25").setStringValue("Firmado: " + clienteGM); // Cliente GM
        hojaRH.getCellByPosition("L33").setStringValue("Firmado: " + nomEmpleado);
    }    
    
    public String guardarRegistoHorarioParaPDF() {
        
        String sClienteGM = hojaRH.getCellByPosition("L10").getStringValue();
        sClienteGM = sClienteGM.replace(". ","");
        sClienteGM = sClienteGM.replace(", ","_");
        sClienteGM = sClienteGM.replace(" ","_");
         
        String sNombreTrabajador = hojaRH.getCellByPosition("L16").getStringValue();
        sNombreTrabajador = sNombreTrabajador.replace(", ","_");
        sNombreTrabajador = sNombreTrabajador.replace(" ","_");
        
        String sNombreMes = hojaRH.getCellByPosition("L5").getStringValue();
        String sAnno = hojaRH.getCellByPosition("N5").getStringValue();

        nomFileSave = "";
        ReadPathFromXML path = new ReadPathFromXML();
        pathToSave = path.cargarXml("PathToTemp");
        String fileName = sClienteGM + "_Registro_Horario_" + sNombreMes + "_" +
                      sAnno + "_" +  sNombreTrabajador + ".ods";
        
        try {
            if (SysOper.equals("Linux"))
              nomFileSave = pathToSave + fileName;
            else
                nomFileSave = userHome + pathToSave + fileName;
                
        } catch (Exception e) {
                System.err.println("ERROR: No se ha guardado el archivo.");
        }
        
        try{
            
            libroRH.save(nomFileSave);
        }
        catch (Exception e){
            
        }

        return nomFileSave;
    }
    
      public String guardarRegistroHorarioParaImprimir(){
        Date fecha = new Date();
        DateFormat formatoHora = new SimpleDateFormat("HHmmss");
        String horaActual = formatoHora.format(fecha);
        
        SaveDocLibreOfficeToTemp fileTemp = new SaveDocLibreOfficeToTemp(libroRH,
                "ODFtk_Registro_Horario_Tiempo_Parcial_" + horaActual + "_LO.ods");
        
        return fileTemp.getPathFile();
    }
      
    public void RegistroHorarioToPrinterWithLibreOffice(String pathFile){    
        LibreOfficePrintService print = new LibreOfficePrintService(pathFile);
    }
    
    public void RHtoPDF(String pathFile){    
        LibreOfficePDFService pdf = new LibreOfficePDFService(pathFile);
    }
}
