/*
 * RBJM
 * Aplicación desarrollada por José M. Reboreda Barcia
 * para uso propio en Gestoría MOLDES.
 */
package com.gmapp.app.registrohorario;

import com.gmapp.dao.ContratoDAO;
import com.gmapp.utilidades.StringUtils;
import com.gmapp.vo.ContratoVO;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author jmrb
 */
public class EmisionRegistroHorario {
    
        private String sFormacion;
        private String sParcial;
        private String sEnILT;
        private String sEnExcedencia;
        private String sMaternidad;
    
        public Boolean Emision(int numcontrato, int numvariacion){
        
        Boolean emisionRH = false;
        sFormacion = StringUtils.getString(StringUtils.FORMACION);
        sParcial = StringUtils.getString(StringUtils.PARCIAL);
        sEnILT = StringUtils.getString(StringUtils.TIPOVARIACION_300_ILT_INICIO);
        sEnExcedencia = StringUtils.getString(StringUtils.TIPOVARIACION_600_EXCEDENCIA_INICIO);
        sMaternidad = StringUtils.getString(StringUtils.TIPOVARIACION_700_MATERNIDAD_INICIO);
        
        ContratoDAO contrato = new ContratoDAO();
        ContratoVO miContrato;
        List <ContratoVO> listaContrato = contrato.readContrato(numcontrato);
        if(listaContrato.size() > 0){
            for (int i = 0; i < listaContrato.size(); i++){
                miContrato = listaContrato.get(i);
                if (miContrato.getNumvariacion() == numvariacion)
                {
                    if(miContrato.getTipoctto().contains(sFormacion) ||
                        miContrato.getJor_tipo().contains(sParcial) &&
                        miContrato.getTipovariacion() != Integer.parseInt(sEnILT) &&
                        miContrato.getTipovariacion() != Integer.parseInt(sEnExcedencia) &&
                        miContrato.getTipovariacion() != Integer.parseInt(sMaternidad))
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
               if(miContrato.getTipoctto().contains(sFormacion) ||
                   miContrato.getJor_tipo().contains(sParcial) &&
                   miContrato.getTipovariacion() != Integer.parseInt(sEnILT) &&
                   miContrato.getTipovariacion() != Integer.parseInt(sEnExcedencia) &&
                   miContrato.getTipovariacion() != Integer.parseInt(sMaternidad))
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
