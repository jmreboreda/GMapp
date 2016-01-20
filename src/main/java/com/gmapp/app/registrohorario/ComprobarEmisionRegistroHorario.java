/*
 * RBJM
 * Aplicación desarrollada por José M. Reboreda Barcia
 * para uso propio en Gestoría MOLDES.
 */
package com.gmapp.app.registrohorario;

import com.gmapp.dao.ContratoDAO;
import com.gmapp.vo.ContratoVO;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author jmrb
 */
public class ComprobarEmisionRegistroHorario {
    
    public Boolean Emision(int numcontrato, int numvariacion){
        
        Boolean emisionRH = false;
        
        ContratoDAO contrato = new ContratoDAO();
        ContratoVO miContrato;
        List <ContratoVO> listaContrato = contrato.readContrato(numcontrato);
        if(listaContrato.size() > 0){
            for (int i = 0; i < listaContrato.size(); i++){
                 miContrato = listaContrato.get(i);
                 if (miContrato.getNumvariacion() == numvariacion)
                 {
                     if(miContrato.getTipoctto().contains("Formación") ||
                        miContrato.getJor_tipo().contains("Parcial") &&
                        miContrato.getTipovariacion() != 300 &&
                        miContrato.getTipovariacion() != 600 &&
                        miContrato.getTipovariacion() != 700)
                         emisionRH = true;
                 }
            }
        }   
        else
        {
//            String mensaje = "No se ha encontrado ningún contrato con número " + numcontrato;
//            showMessageDialog(null, mensaje,"Registro Horario - Errores detectados",WARNING_MESSAGE);
        }
        
        return emisionRH;
    }
    
    public Boolean EmisionAtAnnoMes(int numcontrato, int numvariacion, int annoMes){
        
        Boolean emisionRH = false;
        SimpleDateFormat fecha = new SimpleDateFormat("yyyyMM");
        int annoMesDesde = 0;
        int annoMesHasta = 0;
        
        ContratoDAO contrato = new ContratoDAO();
        ContratoVO miContrato;
        List <ContratoVO> listaContrato = contrato.readContratoVariacion(numcontrato, numvariacion);
        if(listaContrato.size() > 0)
        {
            miContrato = listaContrato.get(0);
            if (miContrato.getNumvariacion() == numvariacion)
            {
               if(miContrato.getTipoctto().contains("Formación") ||
                   miContrato.getJor_tipo().contains("Parcial") &&
                   miContrato.getTipovariacion() != 300 &&
                   miContrato.getTipovariacion() != 600 &&
                   miContrato.getTipovariacion() != 700)
               {
                    annoMesDesde = Integer.parseInt(fecha.format(miContrato.getF_desde()));
                    if(miContrato.getF_hasta() == null)
                        annoMesHasta = 999912;
                    else
                        annoMesHasta = Integer.parseInt(fecha.format(miContrato.getF_hasta()));

                    if(annoMes >= annoMesDesde && annoMes <= annoMesHasta)
                        emisionRH = true;
               }
           }
        }   
        else
        {
//            String mensaje = "No se ha encontrado ningún contrato con número " + numcontrato;
//            showMessageDialog(null, mensaje,"Registro Horario - Errores detectados",WARNING_MESSAGE);
        }
        
        return emisionRH;
    }
    
}
