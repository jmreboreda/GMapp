/*
 * RBJM
 * Aplicación desarrollada por José M. Reboreda Barcia
 * para uso propio en Gestoría MOLDES.
 */
package com.gmapp.common;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

/**
 *
 * @author jmrb
 */
public class SystemPrintServices {
    
    public PrintService[] printServices;

    public SystemPrintServices() {
        
        printServices = PrintServiceLookup.lookupPrintServices(null, null);
        System.out.println("Número de servicios de impresión: " + printServices.length);

        for (PrintService printer : printServices)
            System.out.println("Impresora: " + printer.getName()); 
    } 
}
