/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.app.altacontratos;

import com.gmapp.common.LibreOfficePDFService;
import com.gmapp.common.LibreOfficePrintService;
import com.gmapp.common.ReadPathFromXML;
import com.gmapp.utils.CodeParaEAN13;
import com.gmapp.utils.DocODFUtils;
import com.gmapp.utils.Funciones;
import com.gmapp.utils.StringUtils;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;


public class CarpetaA3ControlGestor {
    
    VistaAltaContratos vistaAltaContratos;
    private SpreadsheetDocument libroCalcActual;
    private InputStream archivoODF;
    private String SysOper;
    private String userName;
    private String userHome ;     
    private String nomFileSaveToPrint = "";
    private String nomFileSaveToPDF = "";
    private String NUEVO_CONTRATO;
    private String TIEMPO_PARCIAL;
    private String FORMACION;

    public CarpetaA3ControlGestor(VistaAltaContratos vistaOrigen) {
        
        vistaAltaContratos = vistaOrigen;
        
        SysOper = System.getProperty("os.name");
        userName = System.getProperty("user.name");
        userHome = System.getProperty("user.home");    
        
        NUEVO_CONTRATO = StringUtils.getString(StringUtils.NUEVO_CONTRATO);
        TIEMPO_PARCIAL = StringUtils.getString(StringUtils.TIEMPO_PARCIAL);
        FORMACION = StringUtils.getString(StringUtils.FORMACION);
        
        libroCalcActual = cargaPlantilla();
        rellenarDGM_003(libroCalcActual);
        String nomFileToPrint = guardarDGM_003toPrint();
        LibreOfficePrintService print = new LibreOfficePrintService(nomFileToPrint);
        showMessageDialog(null, "Documento A3 para carpetilla de control gestor\n"
                    + "se ha enviado a la impresora","Impresión de documentos",INFORMATION_MESSAGE);   
        
        String nomFileToPDF = guardarDGM_003toPDFparaGestor();
        LibreOfficePDFService pdf = new LibreOfficePDFService(nomFileToPDF);
        showMessageDialog(null, "PDF de notificación al gestor del nuevo contrato\n"
                    + "se ha guardado en su directorio \"Borrame\"","Creación de PDF",INFORMATION_MESSAGE);   
    }
    
    private SpreadsheetDocument cargaPlantilla(){

        String hoja = DocODFUtils.getODFdoc(DocODFUtils.ODF_DGM003);
        archivoODF = CarpetaA3ControlGestor.class.getResourceAsStream(hoja);      
        try {
            libroCalcActual = (SpreadsheetDocument) SpreadsheetDocument.loadDocument(archivoODF);
        } catch (Exception e) {
            System.err.println("ERROR: No se ha cargado el archivo "
                    + "\"DGM_003_Datos_Alta_o_Cambio_Contrato_Trabajo_A3_LO.ods\"");
        }
        
        return libroCalcActual;
    }  
   
    private void rellenarDGM_003(SpreadsheetDocument libro){
        
        Table calcNC = libro.getSheetByIndex(0);
        calcNC.getCellByPosition("B7").setStringValue(NUEVO_CONTRATO);
        calcNC.getCellByPosition("B12").setStringValue(vistaAltaContratos.getClientName());
        calcNC.getCellByPosition("B17").setStringValue(vistaAltaContratos.getCCCclient()); 
        calcNC.getCellByPosition("B21").setStringValue(vistaAltaContratos.getNotificationDate());
        calcNC.getCellByPosition("E21").setStringValue(vistaAltaContratos.getNotificationHour());
        calcNC.getCellByPosition("G6").setStringValue(vistaAltaContratos.getEmployeeName());
        calcNC.getCellByPosition("G9").setStringValue(vistaAltaContratos.getTrabajadorNIF());
        calcNC.getCellByPosition("I9").setStringValue(vistaAltaContratos.getTrabajadorNASS());
        calcNC.getCellByPosition("G12").setStringValue(vistaAltaContratos.getTrabajadorFNacim());
        calcNC.getCellByPosition("I12").setStringValue(vistaAltaContratos.getTrabajadorEstadoCivil());
        calcNC.getCellByPosition("G15").setStringValue(vistaAltaContratos.getTrabajadorNacionalidad());
        calcNC.getCellByPosition("G18").setStringValue(vistaAltaContratos.getTrabajadorDireccion());
        calcNC.getCellByPosition("G23").setStringValue(vistaAltaContratos.getTrabajadorNivEst());

        String tipoCtto = vistaAltaContratos.getComboTiposContrato().getSelectedItem().toString();
        tipoCtto = tipoCtto + ", " + vistaAltaContratos.getContractPermanentOrTemporal();
        tipoCtto = tipoCtto + ", " + vistaAltaContratos.getComboJornada().getSelectedItem().toString();
        
        if(vistaAltaContratos.getComboJornada().getSelectedItem().toString().equals(TIEMPO_PARCIAL))
            tipoCtto = tipoCtto + " [" + vistaAltaContratos.getWeekHours() + " horas/semana ]";

        calcNC.getCellByPosition("B29").setStringValue(tipoCtto); 
        calcNC.getCellByPosition("H29").setStringValue(vistaAltaContratos.getContractStartDate());
        calcNC.getCellByPosition("I29").setStringValue(vistaAltaContratos.getContractTerminationDate()); 
        String sNumDiasContrato = vistaAltaContratos.getEtqDiasDuracionContrato().replace("[","");
        sNumDiasContrato = sNumDiasContrato.replace("]","");
        calcNC.getCellByPosition("J29").setStringValue(sNumDiasContrato);       

        List dias = vistaAltaContratos.getDiasSemana();
        calcNC.getCellByPosition("C32").setStringValue(dias.get(0).toString());
        calcNC.getCellByPosition("D32").setStringValue(dias.get(1).toString());        
        calcNC.getCellByPosition("E32").setStringValue(dias.get(2).toString());            
        calcNC.getCellByPosition("F32").setStringValue(dias.get(3).toString());
        calcNC.getCellByPosition("G32").setStringValue(dias.get(4).toString());
        calcNC.getCellByPosition("H32").setStringValue(dias.get(5).toString());
        calcNC.getCellByPosition("I32").setStringValue(dias.get(6).toString());

        calcNC.getCellByPosition(1,37).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(0, 0).toString().trim());
        calcNC.getCellByPosition(2,37).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(0, 1).toString().trim());
        calcNC.getCellByPosition(4,38).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(0, 2).toString().trim());
        calcNC.getCellByPosition(5,38).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(0, 3).toString().trim());     
        calcNC.getCellByPosition(7,38).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(0, 4).toString().trim()); 
        calcNC.getCellByPosition(8,38).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(0, 5).toString().trim());
        calcNC.getCellByPosition(9,37).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(0, 6).toString().trim()); 

        calcNC.getCellByPosition(1,40).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(1, 0).toString().trim());
        calcNC.getCellByPosition(2,40).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(1, 1).toString().trim());
        calcNC.getCellByPosition(4,41).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(1, 2).toString().trim());
        calcNC.getCellByPosition(5,41).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(1, 3).toString().trim());     
        calcNC.getCellByPosition(7,41).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(1, 4).toString().trim()); 
        calcNC.getCellByPosition(8,41).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(1, 5).toString().trim());
        calcNC.getCellByPosition(9,40).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(1, 6).toString().trim());         

        calcNC.getCellByPosition(1,43).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(2, 0).toString().trim());
        calcNC.getCellByPosition(2,43).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(2, 1).toString().trim());
        calcNC.getCellByPosition(4,44).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(2, 2).toString().trim());
        calcNC.getCellByPosition(5,44).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(2, 3).toString().trim());     
        calcNC.getCellByPosition(7,44).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(2, 4).toString().trim()); 
        calcNC.getCellByPosition(8,44).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(2, 5).toString().trim());
        calcNC.getCellByPosition(9,43).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(2, 6).toString().trim());        
        
        calcNC.getCellByPosition(1,46).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(3, 0).toString().trim());
        calcNC.getCellByPosition(2,46).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(3, 1).toString().trim());
        calcNC.getCellByPosition(4,47).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(3, 2).toString().trim());
        calcNC.getCellByPosition(5,47).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(3, 3).toString().trim());     
        calcNC.getCellByPosition(7,47).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(3, 4).toString().trim()); 
        calcNC.getCellByPosition(8,47).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(3, 5).toString().trim());
        calcNC.getCellByPosition(9,46).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(3, 6).toString().trim());           
        
        calcNC.getCellByPosition(1,49).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(4, 0).toString().trim());
        calcNC.getCellByPosition(2,49).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(4, 1).toString().trim());
        calcNC.getCellByPosition(4,50).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(4, 2).toString().trim());
        calcNC.getCellByPosition(5,50).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(4, 3).toString().trim());     
        calcNC.getCellByPosition(7,50).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(4, 4).toString().trim()); 
        calcNC.getCellByPosition(8,50).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(4, 5).toString().trim());
        calcNC.getCellByPosition(9,49).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(4, 6).toString().trim());         
        
        calcNC.getCellByPosition(1,52).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(5, 0).toString().trim());
        calcNC.getCellByPosition(2,52).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(5, 1).toString().trim());
        calcNC.getCellByPosition(4,53).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(5, 2).toString().trim());
        calcNC.getCellByPosition(5,53).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(5, 3).toString().trim());     
        calcNC.getCellByPosition(7,53).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(5, 4).toString().trim()); 
        calcNC.getCellByPosition(8,53).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(5, 5).toString().trim());
        calcNC.getCellByPosition(9,52).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(5, 6).toString().trim());       
        
        calcNC.getCellByPosition(1,55).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(6, 0).toString().trim());
        calcNC.getCellByPosition(2,55).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(6, 1).toString().trim());
        calcNC.getCellByPosition(4,56).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(6, 2).toString().trim());
        calcNC.getCellByPosition(5,56).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(6, 3).toString().trim());     
        calcNC.getCellByPosition(7,56).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(6, 4).toString().trim()); 
        calcNC.getCellByPosition(8,56).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(6, 5).toString().trim());
        calcNC.getCellByPosition(9,55).setStringValue(vistaAltaContratos.getTablaHorario().getValueAt(6, 6).toString().trim());      
        //
        calcNC.getCellByPosition("B60").setStringValue(vistaAltaContratos.getAreaGestor());
        calcNC.getCellByPosition("B67").setStringValue(vistaAltaContratos.getCategoria());
        
        // EAN 13
        String milisecTime = String.valueOf(System.currentTimeMillis());
        String last12MilisecTime = milisecTime.substring(1, 13);
        
        Funciones funcion = new Funciones();
        int iEAN13dc = funcion.ENA13ControlCodeCalculator(last12MilisecTime);
        String sEAN13 = last12MilisecTime + iEAN13dc;
        
        CodeParaEAN13 ean13 = new CodeParaEAN13();
        String sEAN13code = ean13.CodeParaEAN13(sEAN13);
        
        calcNC.getCellByPosition("I66").setStringValue(sEAN13code);
        
        // Registro Horario: registro emitido y fecha de emisión
        
        if(vistaAltaContratos.getComboJornada().getSelectedItem().toString().contains(TIEMPO_PARCIAL) ||
                vistaAltaContratos.getComboTiposContrato().getSelectedItem().toString().contains(FORMACION))
        {
            SimpleDateFormat fechaCompleta = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat nombreMes = new SimpleDateFormat("MMMM");
            SimpleDateFormat annoInFecha = new SimpleDateFormat("yyyy");
            
            Date fechaInicioCtto = new Date();
            try{
                fechaInicioCtto = fechaCompleta.parse(vistaAltaContratos.getContractStartDate());
            }
            catch(Exception e){   
            }
            String mesRH = nombreMes.format(fechaInicioCtto);
            String annoRH = annoInFecha.format(fechaInicioCtto);
            
            calcNC.getCellByPosition("B127").setStringValue(" Registro Horario [emitido para " + 
                    mesRH + "-" + annoRH  + "]");
            calcNC.getCellByPosition("G126").setStringValue(vistaAltaContratos.getNotificationDate());
        }
    }
    
    private String guardarDGM_003toPrint(){
            
        if (nomFileSaveToPrint.trim().length() == 0)
        {
            Date fecha = new Date();
            DateFormat formatoHora = new SimpleDateFormat("HHmmss");
            String horaActual = formatoHora.format(fecha);
            
            ReadPathFromXML Path = new ReadPathFromXML();
            String myPathToTemp = Path.cargarXml("PathToTemp");
            
            try {
                if (SysOper.equals("Linux"))
                nomFileSaveToPrint = myPathToTemp + "ODFtk_DGM_003_Nuevo_Contrato_" + horaActual + "_LO.ods";
                else
                nomFileSaveToPrint = userHome + myPathToTemp + "ODFtk_DGM_003_Nuevo_Contrato_" + horaActual + "_LO.ods";
                
            } catch (Exception e) {
                System.err.println("ERROR: No se ha guardado el archivo.");
            }
        }
        try{
            libroCalcActual.save(nomFileSaveToPrint);
        }
        catch (Exception e){  
        }
        
        return nomFileSaveToPrint;
    }
    
    private String guardarDGM_003toPDFparaGestor(){
          
        libroCalcActual.getSheetByIndex(0).removeRowsByIndex(71, 200);
        
        Date fecha = new Date();
        DateFormat formatoHora = new SimpleDateFormat("HHmmss");
        String horaActual = formatoHora.format(fecha);
        String sCliente = vistaAltaContratos.getComboCliente().getSelectedItem().toString();
        sCliente = sCliente.replace(".","");
        sCliente = sCliente.replace(", ","_");
        sCliente = sCliente.replace(" ", "_");
        String sTrabajador = vistaAltaContratos.getComboTrabajador().getSelectedItem().toString();
        sTrabajador = sTrabajador.replace(".","");
        sTrabajador = sTrabajador.replace(", ","_");
        sTrabajador = sTrabajador.replace(" ", "_");
        
        ReadPathFromXML Path = new ReadPathFromXML();
        String myPathToTemp = Path.cargarXml("PathToTemp");
        
        try {
            if (SysOper.equals("Linux"))
                nomFileSaveToPDF = myPathToTemp + sCliente + "_Nuevo_Contrato_" + vistaAltaContratos.getContractStartDate() + "_" + sTrabajador + ".ods";
            else
                nomFileSaveToPDF = userHome + myPathToTemp + sCliente + "_Nuevo_Contrato_"
                        + vistaAltaContratos.getContractStartDate() + "_" + sTrabajador + ".ods";
                
        } catch (Exception e) {
                System.err.println("ERROR: No se ha guardado el archivo.");
        }
        try{
            libroCalcActual.save(nomFileSaveToPDF);
            System.out.println("Archivo ODS para conversión en PDF: " + nomFileSaveToPDF);
        }
        catch(Exception e){
            
        } 
        
        return nomFileSaveToPDF;
    }
}
