/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.dao;

import com.gmapp.utils.BaseDeDatos;
import com.gmapp.vo.EstudiosVO;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author jmrb
 */
public class EstudiosDAO {

    public void crearEstudios(){
        
    }
    
    public ArrayList<EstudiosVO> readEstudios(Integer idestudio){
        
        EstudiosVO misEstudios = null;
        ArrayList<EstudiosVO> estudios = new ArrayList<>();
        String sqlQuery = "SELECT * FROM estudios WHERE idestudio = " + idestudio + ";";
        BaseDeDatos gmoldes = new BaseDeDatos();
        
        try{
            gmoldes.estableceConexion();
            ResultSet rs = gmoldes.seleccionarDatosTabla(sqlQuery);
            if(rs.next()){
                misEstudios = new EstudiosVO();
                misEstudios.setIdEstudios(rs.getInt("idestudio"));
                misEstudios.setDescripEstudios(rs.getString("descripest"));
                estudios.add(misEstudios);
            }
            
        }catch(Exception e){
            
        }
        
        return estudios;
    }
    
}
