/*
 * RBJM
 * Aplicación desarrollada por José M. Reboreda Barcia
 * para uso propio en Gestoría MOLDES.
 */
package com.gmapp.app.variacioncontratos;

import com.gmapp.dao.ClienteWithCCCDAO;
import com.gmapp.vo.ClienteWithCCCVO;
import java.util.List;

/**
 *
 * @author jmrb
 */
public class ModeloVC {
    
    
    
    
    
    public List<ClienteWithCCCVO> getAllClientesWithCCC(){
        
        ClienteWithCCCDAO cliente = new ClienteWithCCCDAO();
        List <ClienteWithCCCVO> listaClientes = cliente.listAllWithCCC();
        
        return listaClientes;
    }
    
}
