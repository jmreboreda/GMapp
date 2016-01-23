/*
 * RBJM
 * Aplicación desarrollada por José M. Reboreda Barcia
 * para uso propio en Gestoría MOLDES.
 */
package com.gmapp.app.variacioncontratos;

import com.gmapp.vo.ClienteVO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jmrb
 */
public class ControladorVC {
    
    private ModeloVC modeloVC;
    private VistaVC vistaVC;
    
    private List<String> listaNombresClientes = new ArrayList<>();
    private List <Integer> listaIDClientes = new ArrayList();
    private List <String> listaNombresTrabajadores = new ArrayList<>();    
    private List <Integer> listaIDTrabajadores = new ArrayList();
    
    public ControladorVC(ModeloVC modelo, VistaVC vista) {
        
        this.modeloVC = modelo;
        this.vistaVC = vista;
      
        // *******************************************************
        // Pasa a la vista los items del combo de clientes con CCC
        // *******************************************************
        ClienteVO miCliente;
        List <ClienteVO> listaClientes = modeloVC.getAllClientesWithCCC();
        if (listaClientes.size() > 0){
            for (int i = 0; i < listaClientes.size(); i++){
                miCliente = listaClientes.get(i);
                listaNombresClientes.add(miCliente.getNom_rzsoc());
                listaIDClientes.add(miCliente.getIdcliente());
            }
        }
        else{
            System.out.println("No se ha podido cargar el comboBox de Clientes");
        }
        vistaVC.cargaComboClientes(listaNombresClientes);    
        
    }
    
    public void cambiadoCliente(){
        
    }
    
    public void cambiadoTrabajador(){
        
    }
    
    public void cambiadoTipoContrato(){
        
    }
    
    public void cambiadoDuracionContrato(){
        
    }
    
    public void cambiadoJornada(){
        
    }
    
    public void cambiadoFechaInicioContrato(){
        
    }
    
    public void cambiadoFechaFinContrato(){
        
    }
    
    public void verificaHorasSemana(){

    }
    
    public void botonAceptarMouseClicked(){
        
    }
    
    public void botonSalirMouseClicked(){
        
    }
}
