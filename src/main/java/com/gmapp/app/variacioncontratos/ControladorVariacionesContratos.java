/*
 * RBJM
 * Aplicación desarrollada por José M. Reboreda Barcia
 * para uso propio en Gestoría MOLDES.
 */
package com.gmapp.app.variacioncontratos;

import com.gmapp.vo.ContratoVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jmrb
 */
public class ControladorVariacionesContratos {
    
    private ModeloVariacionesContratos modeloVC;
    private VistaVariacionesContratos vistaVC;
    
    private List<String> clientsNamesList;
    private Map<String,Integer> clientsNameId;
    private List <String> employeesNamesList;  
    private Map<String, Integer> employeesNameId;
    
    public ControladorVariacionesContratos(ModeloVariacionesContratos modelo, VistaVariacionesContratos vista) {
        
        modeloVC = modelo;
        vistaVC = vista;
        
        clientsNameId = new HashMap<>();
        employeesNameId = new HashMap<>();
        clientsNamesList = new ArrayList<>();
        employeesNamesList = new ArrayList<>();    

        ContratoVO miContrato;
        List <ContratoVO> listaContratos = modeloVC.getClientesConContratosEnVigor();
        if (listaContratos.size() > 0){
            for(ContratoVO contrato: listaContratos){
                miContrato = contrato;
                clientsNamesList.add(miContrato.getClientegm_name());
                clientsNameId.put(miContrato.getClientegm_name(), miContrato.getIdcliente_gm());
            }
        }
        else{
            System.out.println("No se ha podido cargar el comboBox de Clientes");
        }
        vistaVC.clientComboLoad(clientsNamesList);
        vistaVC.getComboCliente().setEnabled(true);
    }
    
    public void clientChanged(){
        if(vistaVC.getComboCliente().getSelectedIndex() == 0){
            vistaVC.getComboTrabajador().removeAllItems();
            vistaVC.componentsClear();
            return;
        }
        employeesNamesList.clear();
        int idSelectedClient = clientsNameId.get(vistaVC.getClientName());
        ContratoVO miContrato;
        List<ContratoVO> listaContratos = modeloVC.getContratosEnVigorCliente(idSelectedClient);
        if(listaContratos.size() > 0){
            if(listaContratos.size() > 1)
                employeesNamesList.add("Seleccionar trabajador ...");
            for (ContratoVO contrato: listaContratos){
                 miContrato = contrato;
                 employeesNamesList.add(miContrato.getTrabajador_name());
                 employeesNameId.put(miContrato.getTrabajador_name(), miContrato.getIdtrabajador());
             }
        }
         else{
            System.out.println("No se han encontrado contratos en vigor");
        }
        vistaVC.getComboTrabajador().setEnabled(false);
        vistaVC.employeesComboLoad(employeesNamesList);
        vistaVC.getComboTrabajador().setEnabled(true);       
    }
    
    public void employeeChanged(){
        
        if(vistaVC.getComboTrabajador().getSelectedIndex() == 0){
            vistaVC.componentsClear();
            return;
        }
    }
    
    public void typeContractChange(){
        
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
