/*
 * RBJM
 * Aplicación desarrollada por José M. Reboreda Barcia
 * para uso propio en Gestoría MOLDES.
 */
package com.gmapp.app.variacioncontratos;

import com.gmapp.vo.ContratoVO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jmrb
 */
public class ControladorVariacionesContratos {
    
    private ModeloVariacionesContratos modeloVC;
    private VistaVariacionesContratos vistaVC;
    
    private List<String> listaNombresClientes;
    private List <Integer> listaIDClientes;
    private List <String> listaNombresTrabajadores;    
    private List <Integer> listaIDTrabajadores ;
    
    public ControladorVariacionesContratos(ModeloVariacionesContratos modelo, VistaVariacionesContratos vista) {
        
        modeloVC = modelo;
        vistaVC = vista;
        
        listaNombresClientes = new ArrayList<>();
        listaIDClientes = new ArrayList();
        listaNombresTrabajadores = new ArrayList<>();    
        listaIDTrabajadores = new ArrayList();

        ContratoVO miContrato;
        List <ContratoVO> listaContratos = modeloVC.getClientesConContratosEnVigor();
        if (listaContratos.size() > 0){
            for (int i = 0; i < listaContratos.size(); i++){
                miContrato = listaContratos.get(i);
                listaNombresClientes.add(miContrato.getClientegm_name());
                listaIDClientes.add(miContrato.getIdcliente_gm());
            }
        }
        else{
            System.out.println("No se ha podido cargar el comboBox de Clientes");
        }
        vistaVC.cargaComboClientes(listaNombresClientes);
        vistaVC.comboClienteSetEnabled(true);
        
    }
    
    public void cambiadoCliente(){
        if(vistaVC.getComboCliente().getSelectedIndex() == 0){
            vistaVC.limpiarDatosContrato();
            return;
        }
        listaNombresTrabajadores.clear();
        int indexClienteSeleccionado = vistaVC.getComboCliente().getSelectedIndex();
        int idClienteSeleccionado = listaIDClientes.get(indexClienteSeleccionado - 1);
        
        ContratoVO miContrato;
        List<ContratoVO> listaContratos = modeloVC.getContratosEnVigorCliente(idClienteSeleccionado);
        if(listaContratos.size() > 0){
            if(listaContratos.size() > 1)
                listaNombresTrabajadores.add("Seleccionar trabajador ...");
            for (int i = 0; i < listaContratos.size(); i++){
                 miContrato = listaContratos.get(i);
                 listaNombresTrabajadores.add(miContrato.getTrabajador_name());
                 listaIDTrabajadores.add(miContrato.getIdtrabajador());
             }
        }
         else{
            System.out.println("No se han encontrado contratos en vigor");
        }
        vistaVC.cargaComboTrabajadores(listaNombresTrabajadores);
        
        
        
         
        
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
