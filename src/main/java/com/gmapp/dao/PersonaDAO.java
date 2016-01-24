/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.dao;

import com.gmapp.utilities.BaseDeDatos;
import com.gmapp.vo.PersonaVO;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jmrb
 */
public class PersonaDAO {

    public PersonaDAO() {
    }

    
    public void create(List datosPersona) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public ArrayList<PersonaVO> listAll() {

        ArrayList<PersonaVO> misPersonas = new ArrayList<>();            
        String sqlQuery = "SELECT * FROM personas ORDER BY 2;";
        BaseDeDatos gmoldes = new BaseDeDatos();
        try
        { 
            gmoldes.estableceConexion();     
            ResultSet rs = gmoldes.seleccionarDatosTabla(sqlQuery);
            while (rs.next())
            {
                PersonaVO persona = new PersonaVO();
                persona.setIdpersona(rs.getInt("idpersona"));
                persona.setApellidos(rs.getString("apellidos"));
                persona.setNom_rzsoc(rs.getString("nom_rzsoc"));
                persona.setNifcif(rs.getString("nifcif"));
                persona.setNumafss(rs.getString("numafss"));
                persona.setFechanacim(rs.getDate("fechanacim"));
                persona.setEstciv(rs.getString("estciv"));
                persona.setNacionalidad(rs.getString("nacionalidad"));
                persona.setNivestud(rs.getString("nivestud"));
                persona.setDireccion(rs.getString("direccion"));
                persona.setCodpostal(rs.getString("codpostal"));
                persona.setLocalidad(rs.getString("localidad"));
                misPersonas.add(persona);
            }
            gmoldes.cierraConexion();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } 

        return misPersonas;
    }
    
        
    public ArrayList<PersonaVO> readPersona(int idpersona) {
        
        ArrayList<PersonaVO> miTrabajador = new ArrayList<PersonaVO>();
        String sqlQuery = "SELECT * FROM personas WHERE idpersona = " + idpersona + ";";
        BaseDeDatos gmoldes = new BaseDeDatos();
        try{
            gmoldes.estableceConexion();

            ResultSet rs = gmoldes.seleccionarDatosTabla(sqlQuery);
            if (rs.next()){
                PersonaVO persona = new PersonaVO();
                persona.setIdpersona(Integer.parseInt(rs.getString("idpersona")));
                persona.setApellidos(rs.getString("apellidos"));
                persona.setNom_rzsoc(rs.getString("nom_rzsoc"));
                persona.setNifcif(rs.getString("nifcif"));
                persona.setNumafss(rs.getString("numafss"));
                persona.setFechanacim(rs.getDate("fechanacim"));
                persona.setEstciv(rs.getString("estciv"));
                persona.setNacionalidad(rs.getString("nacionalidad"));
                persona.setNivestud(rs.getString("nivestud"));
                persona.setDireccion(rs.getString("direccion"));
                persona.setCodpostal(rs.getString("codpostal"));
                persona.setLocalidad(rs.getString("localidad"));
                miTrabajador.add(persona);
            }
            gmoldes.cierraConexion();
        }
        catch (Exception e){
        }
        
        return miTrabajador;
    }

    
    public void update(int idpersona, List datosPersona) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public void delete(int idpersona) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
