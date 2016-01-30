/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.app.altacontratos;

import com.gmapp.common.LibreOfficePrintService;
import com.gmapp.common.ReadPathFromXML;
import com.gmapp.utilities.DocODFUtils;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;

/**
 *
 * @author jmrb
 */
public class PortadaExpedienteContrato {
    
    private String SysOp;
    private String userName;
    private String userHome; 
    
    private SpreadsheetDocument libroCalc = null;
    private InputStream archivoODF;
    private Table hojaPEC = null;
    private VistaAltaContratos vistaAC;
    
    PortadaExpedienteContrato(VistaAltaContratos vista){   
        
        this.vistaAC = vista;
        
        SysOp = System.getProperty("os.name");
        userName = System.getProperty("user.name");
        userHome = System.getProperty("user.home"); 
        
        libroCalc = cargaLibroCalcPortadaExpedienteContrato();
        if (libroCalc != null){
            rellenarPortadaExpedienteContrato();
            String pathFile = guardarExpedienteContratoParaImprimir();
            if (pathFile != null){
                ImprimirExpedienteContrato(pathFile);
                vistaAC.muestraInfo("Se ha enviado a la impresora el documento:\n"
                    + "\"DGM_006_Portada_Expediente_Contrato_Trabajo\"");
            }
        }
    }
    
    private SpreadsheetDocument cargaLibroCalcPortadaExpedienteContrato(){

        String hoja = DocODFUtils.getODFdoc(DocODFUtils.ODF_DGM006);
        archivoODF = PortadaExpedienteContrato.class.getResourceAsStream(hoja);      
        try {
            libroCalc = (SpreadsheetDocument) SpreadsheetDocument.loadDocument(archivoODF);
            } 
        catch (Exception e) {
                System.err.println("ERROR: No se ha cargado el archivo " +
                    "\"DGM_006_Portada_Expediente_Contrato_Trabajo.ots\"");
                libroCalc = null;
            }
        return libroCalc;
    }
    
    private void rellenarPortadaExpedienteContrato(){
        
        hojaPEC = libroCalc.getSheetByIndex(0);
        
        hojaPEC.getCellByPosition("B7").setStringValue(vistaAC.getComboCliente().getSelectedItem().toString());
        hojaPEC.getCellByPosition("B15").setStringValue(vistaAC.getComboClienteCCC().getSelectedItem().toString());
        hojaPEC.getCellByPosition("J5").setStringValue(vistaAC.getComboTrabajador().getSelectedItem().toString());
        hojaPEC.getCellByPosition("L7").setStringValue(vistaAC.getTrabajadorNIF());
        hojaPEC.getCellByPosition("O7").setStringValue(vistaAC.getTrabajadorNASS());
        hojaPEC.getCellByPosition("L9").setStringValue(vistaAC.getTrabajadorFNacim());
        hojaPEC.getCellByPosition("O9").setStringValue(vistaAC.getTrabajadorEstadoCivil());
        hojaPEC.getCellByPosition("L11").setStringValue(vistaAC.getTrabajadorNacionalidad());
        hojaPEC.getCellByPosition("L13").setStringValue(vistaAC.getTrabajadorDireccion());
        hojaPEC.getCellByPosition("L16").setStringValue(vistaAC.getTrabajadorNivEst());
        hojaPEC.getCellByPosition("D20").setStringValue(vistaAC.getFechaInicioContrato());
        
        String sDuracionContrato = vistaAC.getEtqDuracionContrato().replace("[", "");
        sDuracionContrato = sDuracionContrato.replace("]","").trim();
        hojaPEC.getCellByPosition("P20").setStringValue(sDuracionContrato); // Duración contrato
        
        String sTipoContrato = vistaAC.getComboTiposContrato().getSelectedItem().toString();
        if(sTipoContrato.contains("["))
            sTipoContrato = sTipoContrato.substring(0,7);
        else if (sTipoContrato.contains("Otros"))
                sTipoContrato = "Otros contratos: " + vistaAC.getTipoContratoOtros();
        else
            sTipoContrato = vistaAC.getComboTiposContrato().getSelectedItem().toString();
        
        if(vistaAC.getComboDuracionContrato().getSelectedItem().equals("Temporal"))
            sTipoContrato = sTipoContrato + ", " + vistaAC.getComboDuracionContrato().getSelectedItem() + " [ hasta "
                    + vistaAC.getFechaFinContrato() + " ]";
        else
            sTipoContrato = sTipoContrato + ", Indefinido";
        
        sTipoContrato = sTipoContrato + ", " + vistaAC.getComboJornada().getSelectedItem();
        hojaPEC.getCellByPosition("D22").setStringValue(sTipoContrato); // Celda Tipo de contrato
        
        hojaPEC.getCellByPosition("D23").setStringValue(vistaAC.getCategoria()); // Categoría laboral
        hojaPEC.getCellByPosition("L23").setStringValue(vistaAC.getHorasSemana() + " horas/semana"); // Horas/semana
        hojaPEC.getCellByPosition("O23").setStringValue("Faltan días jornada"); // Jornada
    }
    
      private String guardarExpedienteContratoParaImprimir(){
        Date fecha = new Date();
        DateFormat formatoHora = new SimpleDateFormat("HHmmss");
        String nomFileSave = "";
        String horaActual = formatoHora.format(fecha);
               
        ReadPathFromXML Path = new ReadPathFromXML();
        String myPath = Path.cargarXml("PathToPrint");

        if (SysOp.equals("Linux"))
            nomFileSave = myPath + "ODFtk_Portada_Expediente_Contrato_" + horaActual + "_LO.ods";
        else
            nomFileSave = userHome + myPath + "ODFtk_Portada_Expediente_Contrato_" + horaActual + "_LO.ods";

        try{
            libroCalc.save(nomFileSave);
        }
        catch(Exception e){
            System.err.println("ERROR: No se ha podido guardar el archivo para imprimir");
            nomFileSave = null;
        }
        return nomFileSave;
    }
     
    private void ImprimirExpedienteContrato(String pathFile) {    
        LibreOfficePrintService print = new LibreOfficePrintService(pathFile);
    }
}
