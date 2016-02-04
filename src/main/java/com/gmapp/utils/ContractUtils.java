/*
 * RBJM
 * Aplicación desarrollada por José M. Reboreda Barcia
 * para uso propio en Gestoría MOLDES.
 */
package com.gmapp.utils;

/**
 *
 * @author jmrb
 */
public enum ContractUtils {
    
    NORMAL("Normal"),
    INDEFINIDO_BONIFICADO("Indefinido bonificado"),
    EVENTUAL("Eventual por circunstancias de la producción"),
    OBRA_O_SERVICIO("Por obra o servicio determinado"),
    FORMACION ("De formación"),
    PRACTICAS ("De prácticas"),
    SUBROGACION("Subrogación de otro empleador"),
    SOCIO_TRABAJADOR("Socio trabajador"),
    SUSTITUCION_EXCEDENCIA("De sustitución por excedencia"),
    DE_RELEVO("De relevo"),
    
    INDEFINIDO("Indefinido"),
    TEMPORAL("Temporal"),
    
    J_COMPLETA("Jornada completa"),
    J_PARCIAL("Parcial");
    
    private final String contratcName;
    
    ContractUtils(String contratcName){
        this.contratcName = contratcName;
    }
    
    public static String getContractUtil(ContractUtils type){
        return type.name();
    }
}
