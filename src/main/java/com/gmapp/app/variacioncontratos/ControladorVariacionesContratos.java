/*
 * RBJM
 * Aplicación desarrollada por José M. Reboreda Barcia
 * para uso propio en Gestoría MOLDES.
 */
package com.gmapp.app.variacioncontratos;

import com.gmapp.vo.ContratoVO;
import java.text.SimpleDateFormat;
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
    
    private ContratoVO miContrato;
    private List<String> clientsNamesList;
    private Map<String,Integer> clientsNameId;
    private List <String> employeesNamesList;  
    private Map<String, Integer> employeesNameId;
    private SimpleDateFormat fecha;
    
    public ControladorVariacionesContratos(ModeloVariacionesContratos modelo, VistaVariacionesContratos vista) {
        
        modeloVC = modelo;
        vistaVC = vista;
        
        clientsNameId = new HashMap<>();
        employeesNameId = new HashMap<>();
        clientsNamesList = new ArrayList<>();
        employeesNamesList = new ArrayList<>();
        fecha = new SimpleDateFormat("dd-MM-yyyy");

        List <ContratoVO> listaContratos = modeloVC.getClientesConContratosEnVigor();
        if (listaContratos.size() > 0){
            for(ContratoVO contrato: listaContratos){
                miContrato = contrato;
                clientsNamesList.add(miContrato.getClientegm_name());
                clientsNameId.put(miContrato.getClientegm_name(), miContrato.getIdcliente_gm());
            }
            
        vistaVC.clientComboLoad(clientsNamesList);
        vistaVC.getComboCliente().setEnabled(true);
        
        }
        else{
            System.out.println("No se ha podido cargar el comboBox de Clientes");
        }
    }
    
    public void clientChanged(){
        
        if(vistaVC.getComboCliente().getSelectedIndex() == 0){
            vistaVC.getComboTrabajador().setEnabled(false);
            vistaVC.getComboTrabajador().removeAllItems();
            vistaVC.getComboTrabajador().setEnabled(true);
            vistaVC.componentsClear();
            return;
        }

        int employeesNumber = employeesLoad();
        
        if(employeesNumber == 1)
            dataContractShow();
        else
            if (employeesNumber == 0)
                System.out.println("No se han encontrado contratos en vigor");
    }
    
    public void employeeChanged(){
        
        if(vistaVC.getComboTrabajador().getSelectedIndex() == 0){
            vistaVC.componentsClear();
            return;
        }
        else{
            int idSelectedEmployee = employeesNameId.get(vistaVC.getEmployeeName());
            int idSelectedClient = clientsNameId.get(vistaVC.getClientName());
            List<ContratoVO> contratoinforce = modeloVC.getContractInForceEmployee(idSelectedClient, idSelectedEmployee);
            for(ContratoVO contrato: contratoinforce)
                miContrato = contrato;
            dataContractShow();
        }
        
    }
    
    public int employeesLoad(){
        
        employeesNamesList.clear();
        int idSelectedClient = clientsNameId.get(vistaVC.getClientName());
        List<ContratoVO> listaContratos = modeloVC.getContratosEnVigorCliente(idSelectedClient);
        if(listaContratos.size() > 0){
            if(listaContratos.size() > 1)
                employeesNamesList.add("Seleccionar trabajador ...");
            for (ContratoVO contrato: listaContratos){
                miContrato = contrato;
                employeesNamesList.add(miContrato.getTrabajador_name());
                employeesNameId.put(miContrato.getTrabajador_name(), miContrato.getIdtrabajador());
                }
            vistaVC.getComboTrabajador().setEnabled(false);
            vistaVC.employeesComboLoad(employeesNamesList);
            vistaVC.getComboTrabajador().setEnabled(true);
        }
        
        return listaContratos.size();
    }
    
    public void botonAceptarMouseClicked(){
        
    }
    
    public void botonSalirMouseClicked(){
        
    }
    
    public void dataContractShow(){
        
        vistaVC.getLabelNumContrato().setText(miContrato.getNumcontrato() + " - " + miContrato.getNumvariacion());
        vistaVC.getLabelTipoContrato().setText(miContrato.getTipoctto());
        vistaVC.getLabelFechaDesde().setText(fecha.format(miContrato.getF_desde()));
        if(miContrato.getF_hasta() == null)
            vistaVC.getLabelFechaHasta().setText("Indefinido");
        else
            vistaVC.getLabelFechaHasta().setText(fecha.format(miContrato.getF_hasta()));
        vistaVC.getLabelCategoria().setText(miContrato.getCategoria());
        vistaVC.getLabelNumContratoINEM().setText(miContrato.getId_ctto_inem());
        
    }
}
