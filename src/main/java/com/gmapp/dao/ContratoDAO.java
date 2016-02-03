/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.dao;

import com.gmapp.utilities.BaseDeDatos;
import com.gmapp.vo.ContratoVO;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class ContratoDAO {

    public ContratoDAO() {
    }

    
    public int createContrato(ContratoVO datosContrato) {
        
        int contrato = 0;
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
        
        Integer numcontrato = datosContrato.getNumcontrato();
        Integer numvariacion = datosContrato.getNumvariacion();
        Integer tipovariacion = datosContrato.getTipovariacion();
        Integer idcliente_gm = datosContrato.getIdcliente_gm();        
        String clientegm_name = datosContrato.getClientegm_name();
        String contrato_ccc = datosContrato.getContrato_ccc();
        Integer idtrabajador = datosContrato.getIdtrabajador();
        String trabajador_name = datosContrato.getTrabajador_name();
        String categoria = datosContrato.getCategoria();
        String jor_trab = datosContrato.getJor_trab();
        String jor_trab_dias = datosContrato.getJor_trab_dias();
        String jor_tipo = datosContrato.getJor_tipo();
        String tipoctto = datosContrato.getTipoctto();
        String fechadesde = fecha.format(datosContrato.getF_desde());
        String fechahasta = fecha.format(datosContrato.getF_hasta());
        String id_ctto_inem = datosContrato.getId_ctto_inem();
        String envigor = datosContrato.getEnvigor().toString();
        String notas_gestor = datosContrato.getNotas_gestor();
        String notas_privadas = datosContrato.getNotas_privadas();
        String duracion = datosContrato.getDuracion();
            
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
        
        String sqlQuery = "SELECT * FROM contratoshistorico WHERE envigor = TRUE AND"
                + " idcliente_gm = " + idcliente + " ORDER BY trabajador_name;";
        
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
    
    public List<ContratoVO> readContratcInForceEmployee(int idclient, int idemployee){
         String sqlQuery = "SELECT * FROM contratoshistorico WHERE envigor = TRUE AND"
                + " idtrabajador = " + idemployee + " AND"
                + " idcliente_gm = " + idclient + ";";
        
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
