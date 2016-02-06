/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.app.altacontratos;

import com.gmapp.dao.HorarioContratoDAO;
import com.gmapp.utils.Funciones;
import com.gmapp.vo.HorarioContrlabVO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class HorarioContrato {

    public HorarioContrato(int idcontrato, VistaAltaContratos vista) {
 
        ArrayList<HorarioContrlabVO> lista = new ArrayList<>(); 
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        Funciones funcion = new Funciones();

        for (int fila = 0; fila < vista.getTablaHorario().getRowCount(); fila++)
        {
            int largoNotas = vista.getTablaHorario().getValueAt(fila, 6).toString().length();
            if(largoNotas > 3)
            {    
                HorarioContrlabVO datos = new HorarioContrlabVO();
                
                datos.setIdcontrlab(idcontrato);
                datos.setNumlinea(fila + 1);    // numlinea
                datos.setDiasemana(vista.getTablaHorario().getValueAt(fila, 0).toString().trim()); // diasemana
                
                if (vista.getTablaHorario().getValueAt(fila, 1).toString().trim().length() == 10)  //fecha
                    try{
                        datos.setFecha(formatoFecha.parse(funcion.formatoFecha_us(vista.getTablaHorario().getValueAt(fila, 1).toString().trim())));
                    }
                    catch(Exception e){
                    }
                else datos.setFecha(null);
                
                if(vista.getTablaHorario().getValueAt(fila, 2).toString().trim().isEmpty()) //amdesde
                    datos.setAmdesde("00:00");
                else datos.setAmdesde(vista.getTablaHorario().getValueAt(fila, 2).toString().trim());
                    
                if(vista.getTablaHorario().getValueAt(fila, 3).toString().trim().isEmpty())
                    datos.setAmhasta("00:00");
                else datos.setAmhasta(vista.getTablaHorario().getValueAt(fila, 3).toString().trim());
                
                if(vista.getTablaHorario().getValueAt(fila, 4).toString().trim().isEmpty())
                    datos.setPmdesde("00:00");
                else datos.setPmdesde(vista.getTablaHorario().getValueAt(fila, 4).toString().trim());
                
                if(vista.getTablaHorario().getValueAt(fila, 5).toString().trim().isEmpty())
                    datos.setPmhasta("00:00");
                else datos.setPmhasta(vista.getTablaHorario().getValueAt(fila, 5).toString().trim());
                
                datos.setHoras(vista.getTablaHorario().getValueAt(fila, 6).toString().trim()); // notas
                
                lista.add(datos);
            }
        }
        
        HorarioContratoDAO horario = new HorarioContratoDAO();
        horario.createHorario(lista);
    }
    
}
