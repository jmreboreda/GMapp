/*
 * RBJM
 * Aplicación desarrollada por José M. Reboreda Barcia
 * para uso propio en Gestoría MOLDES.
 */
package com.gmapp.app.variacioncontratos;

import com.gmapp.dao.ClienteDAO;
import com.gmapp.vo.ClienteVO;
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
    
}
