/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.dao;

import com.gmapp.utilidades.BaseDeDatos;
import com.gmapp.vo.PersonaVO;
import com.gmapp.vo.TipoContratoVO;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author jmrb
 */
public class TipoContratoDAO {
    
      public ArrayList<TipoContratoVO> listAll() {

        ArrayList<TipoContratoVO> misTiposContrato = new ArrayList<>();            
        String sqlQuery = "SELECT * FROM tiposcontratos order by 1;";
        BaseDeDatos gmoldes = new BaseDeDatos();
        
        try
        { 
            gmoldes.estableceConexion();     
            ResultSet rs = gmoldes.seleccionarDatosTabla(sqlQuery);
            while (rs.next())
            {
                TipoContratoVO contrato = new TipoContratoVO();
                contrato.setIdTipoContrato(rs.getInt("id"));
                contrato.setDescripcttoTipoContrato(rs.getString("descripctto"));
                misTiposContrato.add(contrato);
                
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } 
        
        return misTiposContrato;
    }
}
