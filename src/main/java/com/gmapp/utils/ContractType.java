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
public enum ContractType {
    
    NORMAL("Normal"),
    INDEFINIDO_BONIFICADO("Indefinido bonificado"),
    EVENTUAL("Eventual por circunstancias de la producción"),
    OBRA_O_SERVICIO("Por obra o servicio determinado"),
    FORMACION ("De formación"),
    PRACTICAS ("De prácticas"),
    SUBROGACION("Subrogación de otro empleador"),
    SOCIO_TRABAJADOR("Socio trabajador"),
    SUSTITUCION_EXCEDENCIA("De sustitución por excedencia"),
    DE_RELEVO("De relevo");
    
    private final String contratcName;
    
    ContractType(String contratcName){
        this.contratcName = contratcName;
    }
    
    public static String getContractType(ContractType type){
        return type.name();
    }
}
