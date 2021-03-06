/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.app.altacontratos;

import com.gmapp.dao.ClienteDAO;
import com.gmapp.dao.ContratoDAO;
import com.gmapp.dao.PersonaDAO;
import com.gmapp.dao.TipoContratoDAO;
import com.gmapp.vo.ClienteVO;
import com.gmapp.vo.ContratoVO;
import com.gmapp.vo.PersonaVO;
import com.gmapp.vo.TipoContratoVO;
import java.util.ArrayList;
import java.util.List;

public class ModeloAltaContratos {

    private List<String> datosCliente;
    private List<String> datosTrabajador;

    public ModeloAltaContratos() {
        this.datosTrabajador = new ArrayList();
        this.datosCliente = new ArrayList();
    }
    
    public List<ClienteVO> getAllClientes(){
        
        ClienteDAO cliente = new ClienteDAO();
        List <ClienteVO> listaClientes = cliente.listAllActivos();
        
        return listaClientes;
    }
    
    public List<ClienteVO> getAllClientesWithCCC(){
        
        ClienteDAO cliente = new ClienteDAO();
        List <ClienteVO> listaClientes = cliente.listAllWithCCC();
        
        return listaClientes;
    }
    
    public List<ClienteVO> getCliente(int idCliente){
        
        List<ClienteVO> lista;
        ClienteDAO cliente = new ClienteDAO();
        lista = cliente.readCliente(idCliente);
        
        return lista;
    }
    
    public List <PersonaVO> getAllPersonas(){
        
        PersonaDAO persona = new PersonaDAO();
        List <PersonaVO> listaPersonas = persona.listAll();
        
        return listaPersonas;
    }
    
    public List<PersonaVO> getPersona(int idPersona){
        
        List<PersonaVO> lista;
        PersonaDAO persona = new PersonaDAO();
        lista = persona.readPersona(idPersona);
        
        return lista;
    }
    
    public List<ClienteVO> getClienteCCC(int idcliente){
        
        List<ClienteVO> lista;
        ClienteDAO ccc = new ClienteDAO();
        lista = ccc.readClienteCCC(idcliente);
        return lista;
    }
    
    public List<TipoContratoVO> getAllTiposContratos(){
        
        TipoContratoDAO tipoContrato = new TipoContratoDAO();
        List <TipoContratoVO> lista;
        lista = tipoContrato.listAll();
        
        return lista;
    }
    
    public Integer getLastContractNumber(){
        
        ContratoDAO contrato = new ContratoDAO();
        
        Integer ultimoNumeroContrato = contrato.readUltimoNumContrato();
        
        return ultimoNumeroContrato;
    }
    
    public int saveContrato(ContratoVO datosContrato){
        
        int idcontrato = 0;
        ContratoDAO contrato = new ContratoDAO();
        
        idcontrato = contrato.createContrato(datosContrato);
        
        return idcontrato;
    }
}
