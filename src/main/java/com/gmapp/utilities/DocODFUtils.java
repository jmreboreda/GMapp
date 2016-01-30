/*
 * RBJM
 * Aplicación desarrollada por José M. Reboreda Barcia
 * para uso propio en Gestoría MOLDES.
 */
package com.gmapp.utilities;

/**
 *
 * @author jmrb
 */
public class DocODFUtils {
    
    private static String ROOT_PATH = "/odfdoc/";
    public static String ODF_DGM002 = "DGM_002_Registro_Horario_Tiempo_Parcial_LO.ods";
    public static String ODF_DGM003 = "DGM_003_Datos_Alta_o_Cambio_Contrato_Trabajo_A3_LO.ods";
    public static String ODF_DGM006 =  "DGM_006_Portada_Expediente_Contrato_Trabajo.ods";
 
    public static String getODFdoc(String document){
       
        return  (ROOT_PATH + document);
    }
    
}
