/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.dao;

import com.gmapp.utilidades.BaseDeDatos;
import com.gmapp.vo.ClienteVO;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jmrb
 */
public class ClienteDAO {
    
    public ClienteDAO() {

    }
    
    public List<ClienteVO> listAllActivos(){
        
        List <ClienteVO> lista = new ArrayList<>();
        String sqlQuery = "SELECT * FROM clientes WHERE fhasta IS NULL ORDER BY 5 ASC ;";  // Ordenado por nom_rzsoc

        BaseDeDatos gmoldes = new BaseDeDatos();
        
        try
        {
            gmoldes.estableceConexion();
            ResultSet rs = gmoldes.seleccionarDatosTabla(sqlQuery);
            while (rs.next()){
                ClienteVO cliente = new ClienteVO();
                cliente.setId(rs.getInt("id"));
                cliente.setIdcliente(rs.getInt("idcliente"));
                cliente.setNifcif(rs.getString("nifcif"));
                cliente.setNifcif_dup(rs.getShort("nifcif_dup"));
                cliente.setNom_rzsoc(rs.getString("nom_rzsoc"));
                cliente.setNumvez(rs.getShort("numvez"));
                cliente.setCltsg21(rs.getString("cltsg21"));
                cliente.setFdesde(rs.getDate("fdesde"));
                cliente.setFhasta(rs.getDate("fhasta"));

                lista.add(cliente);
            }
        }
        catch (Exception e){
        }
        
        gmoldes.cierraConexion();        
        
        return lista;
    }
    
    public List<ClienteVO> listAllWithCCC(){
        
        List <ClienteVO> lista = new ArrayList<>();
        
        StringBuilder query = new StringBuilder();
        
        query.append("SELECT DISTINCT").append(" ");
        query.append("clientes_ccc_inss.idcliente as idcliente").append(",");
        query.append("clientes.id as id").append(",");
        query.append("nifcif").append(",");
        query.append("nifcif_dup").append(",");
        query.append("clientes.nom_rzsoc as nom_rzsoc").append(",");
        query.append("numvez").append(",");
        query.append("cltsg21").append(",");
        query.append("fdesde").append(",");
        query.append("fhasta").append(" ");
        query.append("FROM clientes, clientes_ccc_inss").append(" ");
        query.append("WHERE").append(" ");
        query.append("clientes.fhasta IS NULL").append(" AND ");
        query.append("clientes_ccc_inss IS NOT NULL").append(" AND ");
        query.append("clientes_ccc_inss.idcliente = clientes.id").append(" ");
        query.append("ORDER BY 5").append(";");

        BaseDeDatos gmoldes = new BaseDeDatos();
        
        try
        {
            gmoldes.estableceConexion();
            ResultSet rs = gmoldes.seleccionarDatosTabla(query.toString());
            while (rs.next()){
                ClienteVO cliente = new ClienteVO();
                cliente.setId(rs.getInt("id"));
                cliente.setIdcliente(rs.getInt("idcliente"));
                cliente.setNifcif(rs.getString("nifcif"));
                cliente.setNifcif_dup(rs.getShort("nifcif_dup"));
                cliente.setNom_rzsoc(rs.getString("nom_rzsoc"));
                cliente.setNumvez(rs.getShort("numvez"));
                cliente.setCltsg21(rs.getString("cltsg21"));
                cliente.setFdesde(rs.getDate("fdesde"));
                cliente.setFhasta(rs.getDate("fhasta"));
                lista.add(cliente);
            }
        }
        catch (Exception e){
        }
        
        gmoldes.cierraConexion();        
        
        return lista;
    }
    
    public List<ClienteVO> readCliente(int idcliente) {
        
        List<ClienteVO> lista = new ArrayList<>();
        String sqlQuery = "SELECT * FROM clientes WHERE idcliente = " + idcliente + ";";
        BaseDeDatos gmoldes = new BaseDeDatos();
        
        try
        {
            gmoldes.estableceConexion();

            ResultSet rs = gmoldes.seleccionarDatosTabla(sqlQuery);
            if (rs.next()){
                ClienteVO cliente = new ClienteVO();
                cliente.setId(rs.getInt("id"));
                cliente.setIdcliente(rs.getInt("idcliente"));
                cliente.setNifcif(rs.getString("nifcif"));
                cliente.setNifcif_dup(rs.getShort("nifcif_dup"));
                cliente.setNom_rzsoc(rs.getString("nom_rzsoc"));
                cliente.setNumvez(rs.getShort("numvez"));
                cliente.setCltsg21(rs.getString("cltsg21"));
                cliente.setFdesde(rs.getDate("fdesde"));
                cliente.setFhasta(rs.getDate("fhasta"));
                lista.add(cliente);
            }
        }
        catch (Exception e){
        }
        
        gmoldes.cierraConexion();
        
        return lista;
    }

    
    public void create(List datosCliente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public void update(int idcliente, List datosCliente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public void delete(int idcliente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
