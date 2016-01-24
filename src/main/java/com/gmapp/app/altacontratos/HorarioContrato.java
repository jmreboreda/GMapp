/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.app.altacontratos;

import com.gmapp.dao.HorarioContratoDAO;
import com.gmapp.utilidades.Funciones;
import com.gmapp.vo.HorarioContrlabVO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class HorarioContrato {

    public HorarioContrato(int idcontrato, VistaAltaContratos vista) {
 
        ArrayList<HorarioContrlabVO> lista = new ArrayList<>(); 
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        Funciones funcion = new Funciones();

        for (int fila = 0; fila < vista.getTablaHorarioRowCount(); fila++)
        {
            int largoNotas = vista.getTablaHorarioValueAt(fila, 6).toString().length();
            if(largoNotas > 3)
            {    
                HorarioContrlabVO datos = new HorarioContrlabVO();
                
                datos.setIdcontrlab(idcontrato);
                datos.setNumlinea(fila + 1);    // numlinea
                datos.setDiasemana(vista.getTablaHorarioValueAt(fila, 0).toString().trim()); // diasemana
                
                if (vista.getTablaHorarioValueAt(fila, 1).toString().trim().length() == 10)  //fecha
                    try{
                        datos.setFecha(formatoFecha.parse(funcion.formatoFecha_us(vista.getTablaHorarioValueAt(fila, 1).toString().trim())));
                        //System.out.println("HorarioContrato.Fecha guardada: " + formatoFecha.parse(funcion.formatoFecha_us(vista.getTablaHorarioValueAt(fila, 1).toString().trim())).toString());
                    }
                    catch(Exception e){
                    }
                else datos.setFecha(null);
                
                if(vista.getTablaHorarioValueAt(fila, 2).toString().trim().isEmpty()) //amdesde
                    datos.setAmdesde("00:00");
                else datos.setAmdesde(vista.getTablaHorarioValueAt(fila, 2).toString().trim());
                    
                if(vista.getTablaHorarioValueAt(fila, 3).toString().trim().isEmpty())
                    datos.setAmhasta("00:00");
                else datos.setAmhasta(vista.getTablaHorarioValueAt(fila, 3).toString().trim());
                
                if(vista.getTablaHorarioValueAt(fila, 4).toString().trim().isEmpty())
                    datos.setPmdesde("00:00");
                else datos.setPmdesde(vista.getTablaHorarioValueAt(fila, 4).toString().trim());
                
                if(vista.getTablaHorarioValueAt(fila, 5).toString().trim().isEmpty())
                    datos.setPmhasta("00:00");
                else datos.setPmhasta(vista.getTablaHorarioValueAt(fila, 5).toString().trim());
                
                datos.setHoras(vista.getTablaHorarioValueAt(fila, 6).toString().trim()); // notas
                
                lista.add(datos);
            }
        }
        
        HorarioContratoDAO horario = new HorarioContratoDAO();
        horario.createHorario(lista);
    }
    
}
