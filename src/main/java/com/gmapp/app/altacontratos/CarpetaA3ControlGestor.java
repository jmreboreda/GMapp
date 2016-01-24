/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.app.altacontratos;

import com.gmapp.common.LibreOfficePDFService;
import com.gmapp.common.LibreOfficePrintService;
import com.gmapp.common.ReadPathFromXML;
import com.gmapp.utilities.CodeParaEAN13;
import com.gmapp.utilities.DocODFUtils;
import com.gmapp.utilities.Funciones;
import com.gmapp.utilities.StringUtils;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Table;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;


public class CarpetaA3ControlGestor {
    
    VistaAltaContratos vistaAC;
    private SpreadsheetDocument libroCalcActual;
    private InputStream archivoODF;
    private String SysOp = System.getProperty("os.name");
    private String userName = System.getProperty("user.name");
    private String userHome = System.getProperty("user.home");     
    private String nomFileSaveToPrint = "";
    private String nomFileSaveToPDF = "";

    public CarpetaA3ControlGestor(VistaAltaContratos vistaOrigen) {
        
        vistaAC = vistaOrigen;
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
   
    private void rellenarDGM_003(SpreadsheetDocument libro)
    {   
        String sNuevoContrato = StringUtils.getString(StringUtils.NUEVO_CONTRATO);
        String sTiempoParcial = StringUtils.getString(StringUtils.TIEMPO_PARCIAL);
        String sFormacion = StringUtils.getString(StringUtils.FORMACION);
        
        Table calcNC = libro.getSheetByIndex(0);
        calcNC.getCellByPosition("B7").setStringValue(sNuevoContrato);
        calcNC.getCellByPosition("B12").setStringValue(vistaAC.getClienteName());
        calcNC.getCellByPosition("B17").setStringValue(vistaAC.getComboClienteCCCSelectedItem()); 
        calcNC.getCellByPosition("B21").setStringValue(vistaAC.getFechaNotificacion());                     // Día aviso
        calcNC.getCellByPosition("E21").setStringValue(vistaAC.getHoraNotificacion());                        // Hora aviso
        calcNC.getCellByPosition("G6").setStringValue(vistaAC.getTrabajadorName());
        calcNC.getCellByPosition("G9").setStringValue(vistaAC.getTrabajadorNIF());                             // NIF trabajador
        calcNC.getCellByPosition("I9").setStringValue(vistaAC.getTrabajadorNASS());                           // NASS trabajador
        calcNC.getCellByPosition("G12").setStringValue(vistaAC.getTrabajadorFNacim());                     // Fecha nacim trabajador
        calcNC.getCellByPosition("I12").setStringValue(vistaAC.getTrabajadorEstadoCivil());                         // Est Civ trabajador
        calcNC.getCellByPosition("G15").setStringValue(vistaAC.getTrabajadorNacionalidad());                   // Nacionalidad trabajador
        calcNC.getCellByPosition("G18").setStringValue(vistaAC.getTrabajadorDireccion());                      // Direcc trabajador
        calcNC.getCellByPosition("G23").setStringValue(vistaAC.getTrabajadorNivEst());

        String tipoCtto = vistaAC.getTipoContrato();
        tipoCtto = tipoCtto + ", " + vistaAC.getComboDuracionContratoSelectedItem().toString();
        tipoCtto = tipoCtto + ", " + vistaAC.getComboJornadaSelectedItem().toString();
        
        if(vistaAC.getComboJornadaSelectedItem().toString().equals(sTiempoParcial))
            tipoCtto = tipoCtto + " [" + vistaAC.getHorasSemana() + " horas/semana ]";

        calcNC.getCellByPosition("B29").setStringValue(tipoCtto); 
        calcNC.getCellByPosition("H29").setStringValue(vistaAC.getFechaInicioContrato());
        calcNC.getCellByPosition("I29").setStringValue(vistaAC.getFechaFinContrato()); 
        String sNumDiasContrato = vistaAC.getEtqDuracionContrato().replace("[","");
        sNumDiasContrato = sNumDiasContrato.replace("]","");
        calcNC.getCellByPosition("J29").setStringValue(sNumDiasContrato);       

        List dias = vistaAC.getDiasSemana();
        calcNC.getCellByPosition("C32").setStringValue(dias.get(0).toString());
        calcNC.getCellByPosition("D32").setStringValue(dias.get(1).toString());        
        calcNC.getCellByPosition("E32").setStringValue(dias.get(2).toString());            
        calcNC.getCellByPosition("F32").setStringValue(dias.get(3).toString());
        calcNC.getCellByPosition("G32").setStringValue(dias.get(4).toString());
        calcNC.getCellByPosition("H32").setStringValue(dias.get(5).toString());
        calcNC.getCellByPosition("I32").setStringValue(dias.get(6).toString());

        calcNC.getCellByPosition(1,37).setStringValue(vistaAC.getTablaHorarioValueAt(0, 0).toString().trim());
        calcNC.getCellByPosition(2,37).setStringValue(vistaAC.getTablaHorarioValueAt(0, 1).toString().trim());
        calcNC.getCellByPosition(4,38).setStringValue(vistaAC.getTablaHorarioValueAt(0, 2).toString().trim());
        calcNC.getCellByPosition(5,38).setStringValue(vistaAC.getTablaHorarioValueAt(0, 3).toString().trim());     
        calcNC.getCellByPosition(7,38).setStringValue(vistaAC.getTablaHorarioValueAt(0, 4).toString().trim()); 
        calcNC.getCellByPosition(8,38).setStringValue(vistaAC.getTablaHorarioValueAt(0, 5).toString().trim());
        calcNC.getCellByPosition(9,37).setStringValue(vistaAC.getTablaHorarioValueAt(0, 6).toString().trim()); 

        calcNC.getCellByPosition(1,40).setStringValue(vistaAC.getTablaHorarioValueAt(1, 0).toString().trim());
        calcNC.getCellByPosition(2,40).setStringValue(vistaAC.getTablaHorarioValueAt(1, 1).toString().trim());
        calcNC.getCellByPosition(4,41).setStringValue(vistaAC.getTablaHorarioValueAt(1, 2).toString().trim());
        calcNC.getCellByPosition(5,41).setStringValue(vistaAC.getTablaHorarioValueAt(1, 3).toString().trim());     
        calcNC.getCellByPosition(7,41).setStringValue(vistaAC.getTablaHorarioValueAt(1, 4).toString().trim()); 
        calcNC.getCellByPosition(8,41).setStringValue(vistaAC.getTablaHorarioValueAt(1, 5).toString().trim());
        calcNC.getCellByPosition(9,40).setStringValue(vistaAC.getTablaHorarioValueAt(1, 6).toString().trim());         

        calcNC.getCellByPosition(1,43).setStringValue(vistaAC.getTablaHorarioValueAt(2, 0).toString().trim());
        calcNC.getCellByPosition(2,43).setStringValue(vistaAC.getTablaHorarioValueAt(2, 1).toString().trim());
        calcNC.getCellByPosition(4,44).setStringValue(vistaAC.getTablaHorarioValueAt(2, 2).toString().trim());
        calcNC.getCellByPosition(5,44).setStringValue(vistaAC.getTablaHorarioValueAt(2, 3).toString().trim());     
        calcNC.getCellByPosition(7,44).setStringValue(vistaAC.getTablaHorarioValueAt(2, 4).toString().trim()); 
        calcNC.getCellByPosition(8,44).setStringValue(vistaAC.getTablaHorarioValueAt(2, 5).toString().trim());
        calcNC.getCellByPosition(9,43).setStringValue(vistaAC.getTablaHorarioValueAt(2, 6).toString().trim());        
        
        calcNC.getCellByPosition(1,46).setStringValue(vistaAC.getTablaHorarioValueAt(3, 0).toString().trim());
        calcNC.getCellByPosition(2,46).setStringValue(vistaAC.getTablaHorarioValueAt(3, 1).toString().trim());
        calcNC.getCellByPosition(4,47).setStringValue(vistaAC.getTablaHorarioValueAt(3, 2).toString().trim());
        calcNC.getCellByPosition(5,47).setStringValue(vistaAC.getTablaHorarioValueAt(3, 3).toString().trim());     
        calcNC.getCellByPosition(7,47).setStringValue(vistaAC.getTablaHorarioValueAt(3, 4).toString().trim()); 
        calcNC.getCellByPosition(8,47).setStringValue(vistaAC.getTablaHorarioValueAt(3, 5).toString().trim());
        calcNC.getCellByPosition(9,46).setStringValue(vistaAC.getTablaHorarioValueAt(3, 6).toString().trim());           
        
        calcNC.getCellByPosition(1,49).setStringValue(vistaAC.getTablaHorarioValueAt(4, 0).toString().trim());
        calcNC.getCellByPosition(2,49).setStringValue(vistaAC.getTablaHorarioValueAt(4, 1).toString().trim());
        calcNC.getCellByPosition(4,50).setStringValue(vistaAC.getTablaHorarioValueAt(4, 2).toString().trim());
        calcNC.getCellByPosition(5,50).setStringValue(vistaAC.getTablaHorarioValueAt(4, 3).toString().trim());     
        calcNC.getCellByPosition(7,50).setStringValue(vistaAC.getTablaHorarioValueAt(4, 4).toString().trim()); 
        calcNC.getCellByPosition(8,50).setStringValue(vistaAC.getTablaHorarioValueAt(4, 5).toString().trim());
        calcNC.getCellByPosition(9,49).setStringValue(vistaAC.getTablaHorarioValueAt(4, 6).toString().trim());         
        
        calcNC.getCellByPosition(1,52).setStringValue(vistaAC.getTablaHorarioValueAt(5, 0).toString().trim());
        calcNC.getCellByPosition(2,52).setStringValue(vistaAC.getTablaHorarioValueAt(5, 1).toString().trim());
        calcNC.getCellByPosition(4,53).setStringValue(vistaAC.getTablaHorarioValueAt(5, 2).toString().trim());
        calcNC.getCellByPosition(5,53).setStringValue(vistaAC.getTablaHorarioValueAt(5, 3).toString().trim());     
        calcNC.getCellByPosition(7,53).setStringValue(vistaAC.getTablaHorarioValueAt(5, 4).toString().trim()); 
        calcNC.getCellByPosition(8,53).setStringValue(vistaAC.getTablaHorarioValueAt(5, 5).toString().trim());
        calcNC.getCellByPosition(9,52).setStringValue(vistaAC.getTablaHorarioValueAt(5, 6).toString().trim());       
        
        calcNC.getCellByPosition(1,55).setStringValue(vistaAC.getTablaHorarioValueAt(6, 0).toString().trim());
        calcNC.getCellByPosition(2,55).setStringValue(vistaAC.getTablaHorarioValueAt(6, 1).toString().trim());
        calcNC.getCellByPosition(4,56).setStringValue(vistaAC.getTablaHorarioValueAt(6, 2).toString().trim());
        calcNC.getCellByPosition(5,56).setStringValue(vistaAC.getTablaHorarioValueAt(6, 3).toString().trim());     
        calcNC.getCellByPosition(7,56).setStringValue(vistaAC.getTablaHorarioValueAt(6, 4).toString().trim()); 
        calcNC.getCellByPosition(8,56).setStringValue(vistaAC.getTablaHorarioValueAt(6, 5).toString().trim());
        calcNC.getCellByPosition(9,55).setStringValue(vistaAC.getTablaHorarioValueAt(6, 6).toString().trim());      
        //
        calcNC.getCellByPosition("B60").setStringValue(vistaAC.getAreaGestor());
        calcNC.getCellByPosition("B67").setStringValue(vistaAC.getCategoria());
        
        // EAN 13
        String milisecTime = String.valueOf(System.currentTimeMillis());
        String last12MilisecTime = milisecTime.substring(1, 13);
        
        Funciones funcion = new Funciones();
        int iEAN13dc = funcion.ENA13ControlCodeCalculator(last12MilisecTime);
        String sEAN13 = last12MilisecTime + iEAN13dc;
        
        CodeParaEAN13 ean13 = new CodeParaEAN13();
        String sEAN13code = ean13.CodeParaEAN13(sEAN13);
        
        calcNC.getCellByPosition("I66").setStringValue(sEAN13code);             // EAN 13
        
        // Registro Horario: registro emitido y fecha de emisión
        
        if(vistaAC.getComboJornadaSelectedItem().toString().contains(sTiempoParcial) ||
                vistaAC.getTipoContrato().contains(sFormacion))
        {
            SimpleDateFormat fechaCompleta = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat nombreMes = new SimpleDateFormat("MMMM");
            SimpleDateFormat annoInFecha = new SimpleDateFormat("yyyy");
            
            Date fechaInicioCtto = new Date();
            try{
                fechaInicioCtto = fechaCompleta.parse(vistaAC.getFechaInicioContrato());
            }
            catch(Exception e){   
            }
            String mesRH = nombreMes.format(fechaInicioCtto);
            String annoRH = annoInFecha.format(fechaInicioCtto);
            
            calcNC.getCellByPosition("B127").setStringValue(" Registro Horario [emitido para " + 
                    mesRH + "-" + annoRH  + "]");
            calcNC.getCellByPosition("G126").setStringValue(vistaAC.getFechaNotificacion());
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
                if (SysOp.equals("Linux"))
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
        String sCliente = vistaAC.getClienteName();
        sCliente = sCliente.replace(".","");
        sCliente = sCliente.replace(", ","_");
        sCliente = sCliente.replace(" ", "_");
        String sTrabajador = vistaAC.getTrabajadorName();
        sTrabajador = sTrabajador.replace(".","");
        sTrabajador = sTrabajador.replace(", ","_");
        sTrabajador = sTrabajador.replace(" ", "_");
        
        ReadPathFromXML Path = new ReadPathFromXML();
        String myPathToTemp = Path.cargarXml("PathToTemp");
        
        try {
            if (SysOp.equals("Linux"))
                nomFileSaveToPDF = myPathToTemp + sCliente + "_Nuevo_Contrato_" + vistaAC.getFechaInicioContrato() + "_" + sTrabajador + ".ods";
            else
                nomFileSaveToPDF = userHome + myPathToTemp + sCliente + "_Nuevo_Contrato_"
                        + vistaAC.getFechaInicioContrato() + "_" + sTrabajador + ".ods";
                
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
