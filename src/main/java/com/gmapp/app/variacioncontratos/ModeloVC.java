/*
 * RBJM
 * Aplicación desarrollada por José M. Reboreda Barcia
 * para uso propio en Gestoría MOLDES.
 */
package com.gmapp.app.variacioncontratos;

import com.gmapp.dao.ClienteDAO;
import com.gmapp.dao.ContratoDAO;
import com.gmapp.vo.ClienteVO;
import com.gmapp.vo.ContratoVO;
import java.util.List;

/**
 *
 * @author jmrb
 */
public class ModeloVC {
   
    
    public List<ClienteVO> getAllClientesWithCCC(){
        
        ClienteDAO cliente = new ClienteDAO();
        List <ClienteVO> listaClientes = cliente.listAllWithCCC();
        
        return listaClientes;
    }
    
    public List<ContratoVO> getClientesConContratosEnVigor(){
        
        ContratoDAO contrato = new ContratoDAO();
        List <ContratoVO> listaContratos = contrato.readClientesConContratosEnVigor();
        
        return listaContratos;
    }
    
    public List<ContratoVO> getContratosEnVigorCliente(int idcliente){
        
        ContratoDAO contrato = new ContratoDAO();
        List <ContratoVO> listaContratos = contrato.readAllContratosEnVigorCliente(idcliente);
        
        return listaContratos;
        
    }
    
}
