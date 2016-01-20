/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.app.registrohorario;

import com.gmapp.dao.ClienteDAO;
import com.gmapp.dao.ContratoDAO;
import com.gmapp.dao.PersonaDAO;
import com.gmapp.vo.ClienteVO;
import com.gmapp.vo.ContratoVO;
import com.gmapp.vo.PersonaVO;
import java.util.ArrayList;
import java.util.List;

public class ModeloRH {

    private List<String> datosCliente;
    private List<String> datosTrabajador;

    public ModeloRH() {
        this.datosTrabajador = new ArrayList();
        this.datosCliente = new ArrayList();
    }
    
    public ArrayList<ClienteVO> getAllClientes(){
        
        ClienteDAO cliente = new ClienteDAO();
        ArrayList <ClienteVO> listaClientes = cliente.listAllActivos();
        
        return listaClientes;
    }
    
    public ArrayList<ClienteVO> getAllClientesWithCCC(){
        
        ClienteDAO cliente = new ClienteDAO();
        ArrayList <ClienteVO> listaClientes = cliente.listAllWithCCC();
        
        return listaClientes;
    }
    
    public ArrayList<ContratoVO> getAllContratosCliente(int idCliente){
        
        ArrayList<ContratoVO> lista;
        ContratoDAO contrato = new ContratoDAO();
        lista = contrato.readAllContratosCliente(idCliente);
        
        return lista;
    }
    
    public ArrayList<ContratoVO> getAllContratosEnVigorCliente(int idCliente){
        
        ArrayList<ContratoVO> lista;
        ContratoDAO contrato = new ContratoDAO();
        lista = contrato.readAllContratosEnVigorCliente(idCliente);
        
        return lista;
    }
    
    public ArrayList <PersonaVO> getAllPersonas(){
        
        PersonaDAO persona = new PersonaDAO();
        ArrayList <PersonaVO> listaPersonas = persona.listAll();
        
        return listaPersonas;
    }
    
    public ArrayList<PersonaVO> getPersona(int idPersona){
        
        ArrayList<PersonaVO> lista;
        PersonaDAO trabajador = new PersonaDAO();
        lista = trabajador.readPersona(idPersona);
        
        return lista;
    }
}
