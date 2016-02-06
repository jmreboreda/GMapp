/*
 * RBJM
 * Aplicación desarrollada por José M. Reboreda Barcia
 * para uso propio en Gestoría MOLDES.
 */
package com.gmapp.app.registrohorario;

import com.gmapp.dao.ContratoDAO;
import com.gmapp.utils.StringUtils;
import com.gmapp.vo.ContratoVO;
import java.text.SimpleDateFormat;
import java.util.List;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author jmrb
 */
public class EmisionRegistroHorario {
    
    private final String FORMACION = StringUtils.getString(StringUtils.FORMACION);
    private final String PARCIAL = StringUtils.getString(StringUtils.PARCIAL);
    private final String ILT = StringUtils.getString(StringUtils.TIPOVARIACION_300_ILT_INICIO);
    private final String EXCEDENCIA = StringUtils.getString(StringUtils.TIPOVARIACION_600_EXCEDENCIA_INICIO);
    private final String MATERNIDAD = StringUtils.getString(StringUtils.TIPOVARIACION_700_MATERNIDAD_INICIO);

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
                if(miContrato.getTipoctto().contains(FORMACION) ||
                    miContrato.getJor_tipo().contains(PARCIAL) &&
                    miContrato.getTipovariacion() != Integer.parseInt(ILT) &&
                    miContrato.getTipovariacion() != Integer.parseInt(EXCEDENCIA) &&
                    miContrato.getTipovariacion() != Integer.parseInt(MATERNIDAD))
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
               if(miContrato.getTipoctto().contains(FORMACION) ||
                   miContrato.getJor_tipo().contains(PARCIAL) &&
                   miContrato.getTipovariacion() != Integer.parseInt(ILT) &&
                   miContrato.getTipovariacion() != Integer.parseInt(EXCEDENCIA) &&
                   miContrato.getTipovariacion() != Integer.parseInt(MATERNIDAD))
               {
                    if(miContrato.getF_desde() == null){
                        String mensaje = "Compruebe las fechas de inicio del contrato número " + numcontrato;
                        showMessageDialog(null, mensaje,"Registro Horario - Errores detectados",WARNING_MESSAGE);
                    }
                    else
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
