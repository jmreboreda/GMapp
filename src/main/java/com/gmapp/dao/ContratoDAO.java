/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.dao;

import com.gmapp.utilidades.BaseDeDatos;
import com.gmapp.vo.ContratoVO;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jmrb
 */
public class ContratoDAO {

    public ContratoDAO() {
    }

    
    public int createContrato(List datosContrato) {
       
        Integer contrato = null;
        
        Integer numcontrato = Integer.parseInt(datosContrato.get(0).toString());
        Integer numvariacion = Integer.parseInt(datosContrato.get(1).toString());
        Integer tipovariacion = Integer.parseInt(datosContrato.get(2).toString());
        Integer idcliente_gm = Integer.parseInt(datosContrato.get(3).toString());        
        String clientegm_name = datosContrato.get(4).toString();
        String contrato_ccc = datosContrato.get(5).toString();
        Integer idtrabajador = Integer.parseInt(datosContrato.get(6).toString());
        String trabajador_name = datosContrato.get(7).toString();
        String categoria = datosContrato.get(8).toString();
        String jor_trab = datosContrato.get(9).toString();
        String jor_trab_dias = datosContrato.get(10).toString();
        String jor_tipo = datosContrato.get(11).toString();
        String tipoctto = datosContrato.get(12).toString();
        String fechadesde = datosContrato.get(13).toString();
        String fechahasta = datosContrato.get(14).toString();
        String id_ctto_inem = datosContrato.get(15).toString();
        String envigor = datosContrato.get(16).toString();
        String notas_gestor = datosContrato.get(17).toString();
        String notas_privadas = datosContrato.get(18).toString();
        String duracion = datosContrato.get(19).toString();
        
        
        StringBuilder query = new StringBuilder();
        
        query.append("INSERT INTO contratoshistorico (numcontrato,numvariacion,tipovariacion,idcliente_gm,"
                + "clientegm_name,contrato_ccc,idtrabajador,trabajador_name,categoria,jor_trab,"
                + "jor_trab_dias,jor_tipo,tipoctto,f_desde,f_hasta,id_ctto_inem,envigor,notas_gestor,"
                + "notas_privadas, duracion) VALUES (");
        query.append(numcontrato).append(",");
        query.append(numvariacion).append(",");
        query.append(tipovariacion).append(",");
        query.append(idcliente_gm).append(",");
        query.append("'").append(clientegm_name).append("'").append(",");
        query.append("'").append(contrato_ccc).append("'").append(",");
        query.append(idtrabajador).append(",");
        query.append("'").append(trabajador_name).append("'").append(",");
        query.append("'").append(categoria).append("'").append(",");
        query.append("'").append(jor_trab).append("'").append(",");
        query.append("'").append(jor_trab_dias).append("'").append(",");
        query.append("'").append(jor_tipo).append("'").append(",");
        query.append("'").append(tipoctto).append("'").append(",");
        
        if(fechadesde.equals("null"))
            query.append("null").append(",");
            
        else
            query.append("'").append(fechadesde).append("'").append(",");
        
        if(fechahasta.equals("null"))
            query.append("null").append(",");
            
        else
            query.append("'").append(fechahasta).append("'").append(",");
        
        query.append("'").append(id_ctto_inem).append("'").append(",");
        query.append(envigor).append(",");
        
        if(notas_gestor.equals("null"))
            query.append("null").append(",");
            
        else
            query.append("'").append(notas_gestor).append("'").append(",");
        
        if(notas_privadas.equals("null"))
            query.append("null").append(",");
            
        else
            query.append("'").append(notas_privadas).append("'").append(",");
        
        query.append("'").append(duracion).append("'");   
        query.append(");");

//        System.out.println("StringBuilder \"query\":\n" + query.toString());
        BaseDeDatos gmoldes = new BaseDeDatos();
        
        try
        {
            gmoldes.estableceConexion();

            contrato = gmoldes.actualizarDatosTabla(query.toString());
        }
        catch (Exception e){
        }
        
        // Recuperar el ID del contrato
        query.delete(0, query.length());

        query.append("SELECT numcontrato FROM contratoshistorico WHERE ");
        query.append("idcliente_gm = ").append("'").append(idcliente_gm).append("'").append(" AND ");
        query.append("idtrabajador = ").append("'").append(idtrabajador).append("'").append(" AND ");
        query.append("f_desde = ").append("'").append(fechadesde).append("'").append(" AND ");           
        if(fechahasta.equals("null"))    
            query.append("f_hasta is null").append(" AND ");
        else
            query.append("f_hasta = ").append("'").append(fechahasta).append("'").append(" AND ");           
        query.append("notas_privadas = ").append("'").append(notas_privadas).append("'").append(";");  
        
        System.out.println(query.toString());
        try{
            ResultSet rs = gmoldes.seleccionarDatosTabla(query.toString());
            while (rs.next()){
                contrato = rs.getInt("numcontrato");
            }
        }catch (Exception e){
            
        }
        
        gmoldes.cierraConexion();
        
        return contrato;
    }
    
    
    public List<ContratoVO> readContrato(int numcontrato) {
        
        String sqlQuery = "SELECT * FROM contratoshistorico WHERE numcontrato = " + numcontrato + ";";
        ArrayList<ContratoVO> lista = new ArrayList<>();
        
        BaseDeDatos gmoldes = new BaseDeDatos();
        
        try
        {
            gmoldes.estableceConexion();
            ResultSet rs = gmoldes.seleccionarDatosTabla(sqlQuery);
            if (rs.next()){
                ContratoVO contrato = new ContratoVO();
                contrato.setNumcontrato(rs.getInt("numcontrato"));
                contrato.setNumvariacion(rs.getInt("numvariacion"));
                contrato.setTipovariacion(rs.getInt("tipovariacion"));
                contrato.setIdcliente_gm(rs.getInt("idcliente_gm"));
                contrato.setClientegm_name(rs.getString("clientegm_name"));
                contrato.setContrato_ccc(rs.getString("contrato_ccc"));
                contrato.setIdtrabajador(rs.getInt("idtrabajador"));
                contrato.setTrabajador_name(rs.getString("trabajador_name"));
                contrato.setCategoria(rs.getString("categoria"));
                contrato.setJor_trab(rs.getString("jor_trab"));
                contrato.setJor_trab_dias(rs.getString("jor_trab_dias"));
                contrato.setJor_tipo(rs.getString("jor_tipo"));
                contrato.setTipoctto(rs.getString("tipoctto"));
                contrato.setF_desde(rs.getDate("f_desde"));
                contrato.setF_hasta(rs.getDate("f_hasta"));
                contrato.setId_ctto_inem(rs.getString("id_ctto_inem"));
                contrato.setEnvigor(rs.getBoolean("envigor"));
                contrato.setNotas_gestor(rs.getString("notas_gestor"));
                contrato.setNotas_privadas(rs.getString("notas_privadas"));
                contrato.setDuracion(rs.getString("duracion"));
                contrato.setSubrogacion(rs.getInt("subrogacion"));             
                lista.add(contrato);
            }
        }
        catch (Exception e){
        }
        
        gmoldes.cierraConexion();
        
        return lista;
    }
    
        public List<ContratoVO> readContratoVariacion(int numcontrato, int numvariacion) {
        
        String sqlQuery = "SELECT * FROM contratoshistorico WHERE numcontrato = " + numcontrato
                + " AND numvariacion = " + numvariacion + ";";
        ArrayList<ContratoVO> lista = new ArrayList<>();
        
        BaseDeDatos gmoldes = new BaseDeDatos();
        
        try
        {
            gmoldes.estableceConexion();
            ResultSet rs = gmoldes.seleccionarDatosTabla(sqlQuery);
            if (rs.next()){
                ContratoVO contrato = new ContratoVO();
                contrato.setNumcontrato(rs.getInt("numcontrato"));
                contrato.setNumvariacion(rs.getInt("numvariacion"));
                contrato.setTipovariacion(rs.getInt("tipovariacion"));
                contrato.setIdcliente_gm(rs.getInt("idcliente_gm"));
                contrato.setClientegm_name(rs.getString("clientegm_name"));
                contrato.setContrato_ccc(rs.getString("contrato_ccc"));
                contrato.setIdtrabajador(rs.getInt("idtrabajador"));
                contrato.setTrabajador_name(rs.getString("trabajador_name"));
                contrato.setCategoria(rs.getString("categoria"));
                contrato.setJor_trab(rs.getString("jor_trab"));
                contrato.setJor_trab_dias(rs.getString("jor_trab_dias"));
                contrato.setJor_tipo(rs.getString("jor_tipo"));
                contrato.setTipoctto(rs.getString("tipoctto"));
                contrato.setF_desde(rs.getDate("f_desde"));
                contrato.setF_hasta(rs.getDate("f_hasta"));
                contrato.setId_ctto_inem(rs.getString("id_ctto_inem"));
                contrato.setEnvigor(rs.getBoolean("envigor"));
                contrato.setNotas_gestor(rs.getString("notas_gestor"));
                contrato.setNotas_privadas(rs.getString("notas_privadas"));
                contrato.setDuracion(rs.getString("duracion"));
                contrato.setSubrogacion(rs.getInt("subrogacion"));             
                lista.add(contrato);
            }
        }
        catch (Exception e){
        }
        
        gmoldes.cierraConexion();
        
        return lista;
    }
    
    public Integer readUltimoNumContrato(){
        
        Integer ultimoNumContrato =  null;
        
        String sqlQuery = "SELECT max(numcontrato) as numcontrato FROM contratoshistorico;";

        BaseDeDatos gmoldes = new BaseDeDatos();
        
        try
        {
            gmoldes.estableceConexion();
            ResultSet rs = gmoldes.seleccionarDatosTabla(sqlQuery);
            if (rs.next()){
                ultimoNumContrato = rs.getInt("numcontrato");
            }
        }
        catch (Exception e){
        }
        
        gmoldes.cierraConexion();
        
        return ultimoNumContrato;
        
    }
    
    public List<ContratoVO> readClientesConContratosEnVigor(){
        
        String sqlQuery = "SELECT DISTINCT clientegm_name, idcliente_gm FROM contratoshistorico WHERE envigor = TRUE ORDER BY 1;";
    
        List <ContratoVO> lista = new ArrayList<>();
        
            BaseDeDatos gmoldes = new BaseDeDatos();
        
        try
        {
            gmoldes.estableceConexion();
            ResultSet rs = gmoldes.seleccionarDatosTabla(sqlQuery);
            while (rs.next()){
                ContratoVO contrato = new ContratoVO();
                contrato.setIdcliente_gm(rs.getInt("idcliente_gm"));
                contrato.setClientegm_name(rs.getString("clientegm_name"));
                lista.add(contrato);
            }
        }
        catch (Exception e){
        }
        
        gmoldes.cierraConexion();
        
        return lista;
    
    }
    
    public ArrayList<ContratoVO> readAllContratosEnVigor(){
        
        String sqlQuery = "SELECT * FROM contratoshistorico WHERE envigor = TRUE;";
        
        ArrayList<ContratoVO> lista = new ArrayList<>();
        BaseDeDatos gmoldes = new BaseDeDatos();
        
        try
        {
            gmoldes.estableceConexion();
            ResultSet rs = gmoldes.seleccionarDatosTabla(sqlQuery);
            while (rs.next()){
                ContratoVO contrato = new ContratoVO();
                contrato.setNumcontrato(rs.getInt("numcontrato"));
                contrato.setNumvariacion(rs.getInt("numvariacion"));
                contrato.setTipovariacion(rs.getInt("tipovariacion"));
                contrato.setIdcliente_gm(rs.getInt("idcliente_gm"));
                contrato.setClientegm_name(rs.getString("clientegm_name"));
                contrato.setContrato_ccc(rs.getString("contrato_ccc"));
                contrato.setIdtrabajador(rs.getInt("idtrabajador"));
                contrato.setTrabajador_name(rs.getString("trabajador_name"));
                contrato.setCategoria(rs.getString("categoria"));
                contrato.setJor_trab(rs.getString("jor_trab"));
                contrato.setJor_trab_dias(rs.getString("jor_trab_dias"));
                contrato.setJor_tipo(rs.getString("jor_tipo"));
                contrato.setTipoctto(rs.getString("tipoctto"));
                contrato.setF_desde(rs.getDate("f_desde"));
                contrato.setF_hasta(rs.getDate("f_hasta"));
                contrato.setId_ctto_inem(rs.getString("id_ctto_inem"));
                contrato.setEnvigor(rs.getBoolean("envigor"));
                contrato.setNotas_gestor(rs.getString("notas_gestor"));
                contrato.setNotas_privadas(rs.getString("notas_privadas"));
                contrato.setDuracion(rs.getString("duracion"));
                contrato.setSubrogacion(rs.getInt("subrogacion"));                
                lista.add(contrato);
            }
        }
        catch (Exception e){
        }
        
        gmoldes.cierraConexion();
        
        return lista;
    }
    
    
    
    
    public ArrayList<ContratoVO> readAllContratosEnVigorCliente(Integer idcliente){
        
        String sqlQuery = "SELECT * FROM contratoshistorico WHERE envigor = TRUE and idcliente_gm = " + idcliente + ";";
        
        ArrayList<ContratoVO> lista = new ArrayList<>();
        BaseDeDatos gmoldes = new BaseDeDatos();
        
        try
        {
            gmoldes.estableceConexion();
            ResultSet rs = gmoldes.seleccionarDatosTabla(sqlQuery);
            while (rs.next()){
                ContratoVO contrato = new ContratoVO();
                contrato.setNumcontrato(rs.getInt("numcontrato"));
                contrato.setNumvariacion(rs.getInt("numvariacion"));
                contrato.setTipovariacion(rs.getInt("tipovariacion"));
                contrato.setIdcliente_gm(rs.getInt("idcliente_gm"));
                contrato.setClientegm_name(rs.getString("clientegm_name"));
                contrato.setContrato_ccc(rs.getString("contrato_ccc"));
                contrato.setIdtrabajador(rs.getInt("idtrabajador"));
                contrato.setTrabajador_name(rs.getString("trabajador_name"));
                contrato.setCategoria(rs.getString("categoria"));
                contrato.setJor_trab(rs.getString("jor_trab"));
                contrato.setJor_trab_dias(rs.getString("jor_trab_dias"));
                contrato.setJor_tipo(rs.getString("jor_tipo"));
                contrato.setTipoctto(rs.getString("tipoctto"));
                contrato.setF_desde(rs.getDate("f_desde"));
                contrato.setF_hasta(rs.getDate("f_hasta"));
                contrato.setId_ctto_inem(rs.getString("id_ctto_inem"));
                contrato.setEnvigor(rs.getBoolean("envigor"));
                contrato.setNotas_gestor(rs.getString("notas_gestor"));
                contrato.setNotas_privadas(rs.getString("notas_privadas"));
                contrato.setDuracion(rs.getString("duracion"));
                contrato.setSubrogacion(rs.getInt("subrogacion"));                
                lista.add(contrato);
            }
        }
        catch (Exception e){
        }
        
        gmoldes.cierraConexion();
        
        return lista;
    }
    
    public void updateContrato(int idcontrato, List datosContrato) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public void deleteContrato(int idcontrato) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public ArrayList<ContratoVO> readAllContratosCliente(Integer idcliente){
        
        String sqlQuery = "SELECT * FROM contratoshistorico WHERE idcliente_gm = " + idcliente
                + " ORDER BY numcontrato, numvariacion;";
        
        ArrayList<ContratoVO> lista = new ArrayList<>();
        BaseDeDatos gmoldes = new BaseDeDatos();
        
        try
        {
            gmoldes.estableceConexion();
            ResultSet rs = gmoldes.seleccionarDatosTabla(sqlQuery);
            while (rs.next()){
                ContratoVO contrato = new ContratoVO();
                contrato.setNumcontrato(rs.getInt("numcontrato"));
                contrato.setNumvariacion(rs.getInt("numvariacion"));
                contrato.setTipovariacion(rs.getInt("tipovariacion"));
                contrato.setIdcliente_gm(rs.getInt("idcliente_gm"));
                contrato.setClientegm_name(rs.getString("clientegm_name"));
                contrato.setContrato_ccc(rs.getString("contrato_ccc"));
                contrato.setIdtrabajador(rs.getInt("idtrabajador"));
                contrato.setTrabajador_name(rs.getString("trabajador_name"));
                contrato.setCategoria(rs.getString("categoria"));
                contrato.setJor_trab(rs.getString("jor_trab"));
                contrato.setJor_trab_dias(rs.getString("jor_trab_dias"));
                contrato.setJor_tipo(rs.getString("jor_tipo"));
                contrato.setTipoctto(rs.getString("tipoctto"));
                contrato.setF_desde(rs.getDate("f_desde"));
                contrato.setF_hasta(rs.getDate("f_hasta"));
                contrato.setId_ctto_inem(rs.getString("id_ctto_inem"));
                contrato.setEnvigor(rs.getBoolean("envigor"));
                contrato.setNotas_gestor(rs.getString("notas_gestor"));
                contrato.setNotas_privadas(rs.getString("notas_privadas"));
                contrato.setDuracion(rs.getString("duracion"));
                contrato.setSubrogacion(rs.getInt("subrogacion"));                
                lista.add(contrato);
            }
        }
        catch (Exception e){
        }
        
        gmoldes.cierraConexion();
        
        return lista;
    }
}
