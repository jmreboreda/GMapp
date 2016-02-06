/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.dao;

import com.gmapp.utils.BaseDeDatos;
import com.gmapp.utils.Funciones;
import com.gmapp.vo.HorarioContrlabVO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author jmrb
 */
public class HorarioContratoDAO {
    
    public void createHorario (ArrayList<HorarioContrlabVO> datosHorario){
        
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        Funciones funcion = new Funciones();
        
        for (int i = 0; i < datosHorario.size(); i++)
        {
            HorarioContrlabVO datos;
            
            datos = datosHorario.get(i);
            
            int idcontrato = datos.getIdcontrlab();
            int numlinea = datos.getNumlinea();
            String diasemana = datos.getDiasemana();
            
            String sFecha = null;
            if (datos.getFecha() != null){
                    Date fecha = new Date(datos.getFecha().getTime());
                    sFecha = "'" + formatoFecha.format(fecha) + "'";
            }
            String amdesde = datos.getAmdesde();
            String amhasta = datos.getAmhasta();
            String pmdesde = datos.getPmdesde();
            String pmhasta = datos.getPmhasta();
            String notas = datos.getHoras();

            String sqlQuery = "INSERT INTO horario_contrlab (idcontrlab,numlinea,diasemana,amdesde,amhasta,"
                    + " pmdesde,pmhasta,notas,fecha) VALUES (" + idcontrato + "," + numlinea + ",'"
                        + diasemana + "','" + amdesde + "','" + amhasta + "','" + pmdesde + "','"+ pmhasta + "',"
                        + "'" + notas + "'," + sFecha + ");";

            BaseDeDatos gmoldes = new BaseDeDatos();

            try
            {
                gmoldes.estableceConexion();
                System.out.println(sqlQuery);
                gmoldes.actualizarDatosTabla(sqlQuery);
            }
            catch (Exception e){
            }
            gmoldes.cierraConexion();
        }
    }
}
