/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.app.registrohorario;


import com.gmapp.utilidades.Funciones;
import com.gmapp.utilidades.MesesAnno;
import com.gmapp.vo.ClienteVO;
import com.gmapp.vo.ContratoVO;
import com.gmapp.vo.PersonaVO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;


public class ControladorRH {

    private ModeloRH modeloRH;

    private VistaRegistroHorario vistaRH;

    private Boolean cargandoMeses = false;
    private boolean cargandoClientes = false;

    private List <Integer> listaIDClientes = new ArrayList();
    

    public ControladorRH(ModeloRH modelo, VistaRegistroHorario vista) {
        
        this.modeloRH = modelo;
        this.vistaRH = vista;
        // ************************************************
        // Pasa a la vistaRH los items del combo de meses
        // y fija el combo en mes y año determinados
        // *************************************************
        cargandoMeses = true;
        MesesAnno mesesAnno = new MesesAnno();
        List mesesDelAnno = mesesAnno.getNombresMesesAnno();
            for (int i = 0; i < mesesDelAnno.size(); i++){
                String mes = mesesDelAnno.get(i).toString();
                vista.comboMesesAddItem(mes);
            }
       
        Date fechaHoy = new Date();

        SimpleDateFormat mesHoy = new SimpleDateFormat("MM");
        SimpleDateFormat annoHoy = new SimpleDateFormat("yyyy");  
          
        int numeroMesHoy = Integer.parseInt(mesHoy.format(fechaHoy));
        int numeroAnnoHoy = Integer.parseInt(annoHoy.format(fechaHoy));            
        
        if(numeroMesHoy == 12){
            vista.comboMesesSetSelectedIndex(0);    // enero -> index = 0
            vista.setAnno(Integer.toString(numeroAnnoHoy + 1));
        }
        else
        {
            vista.comboMesesSetSelectedIndex(numeroMesHoy); 
            vista.setAnno(Integer.toString(numeroAnnoHoy));
        }
        cargandoMeses = false;
        // ****************************************************
        // Pasa a la vistaRH los items del combo de clientes.
        // ****************************************************
        cargandoClientes = true;
        ClienteVO miCliente;
        ArrayList <ClienteVO> listaClientes = modelo.getAllClientesWithCCC();
        if(listaClientes.size() > 0){
             for (int i = 0; i < listaClientes.size(); i++){
                miCliente = listaClientes.get(i);
                vista.comboClientesAddItem(miCliente.getNom_rzsoc());
                listaIDClientes.add(miCliente.getIdcliente()); 
             }
        }
         else{
            System.out.println("No se ha podido cargar el comboBox de Clientes");
        }
        cargandoClientes = false;
    }
    
    public void cambiadoMes(){
        
        if(cargandoMeses)
            return;

        cambiadoCliente();

    }

    public void cambiadoCliente(){
              
        if(cargandoClientes)
            return;
        
        int indexSelected = vistaRH.getComboClientesSelectedIndex();
        
        if(indexSelected == 0){
            
            vistaRH.limpiarTablaContratos();
            return;
        }
        
        vistaRH.limpiarTablaContratos();
        
        int idCliente =  listaIDClientes.get(indexSelected -1);
        

        ArrayList<ContratoVO> contratosEncontrados;
        ContratoVO miContrato = null;
        
        int iMesRH = vistaRH.getComboMesesSelectedIndex() + 1;
        String sMesRH = Integer.toString(iMesRH);
        if (sMesRH.length() == 1)
            sMesRH = "0" + sMesRH;
        String sAnnoRH = vistaRH.getAnno();
        int annoMesRH = Integer.parseInt(sAnnoRH + sMesRH);
        
        contratosEncontrados = modeloRH.getAllContratosCliente(idCliente);
        if (contratosEncontrados.size() > 0)
        {
            Funciones funcion = new Funciones();
            
            for (int i = 0; i < contratosEncontrados.size(); i++){
                
                miContrato = contratosEncontrados.get(i);
                ComprobarEmisionRegistroHorario comprobar = new ComprobarEmisionRegistroHorario();

                if(comprobar.EmisionAtAnnoMes(miContrato.getNumcontrato(),miContrato.getNumvariacion(),
                        annoMesRH)){
                    String[] datosRow = new String[8]; 
                    // Apellidos y nombre del trabajador
                    ArrayList<PersonaVO> personaEncontrada;
                    PersonaVO persona = new PersonaVO();
                    personaEncontrada = modeloRH.getPersona(miContrato.getIdtrabajador());
                    for(int j = 0; j < personaEncontrada.size(); j++)
                        persona =personaEncontrada.get(j);
                    datosRow[0] = miContrato.getContrato_ccc();
                    datosRow[1] = persona.getApellidos() + ", " + persona.getNom_rzsoc();
                    datosRow[2] = miContrato.getJor_trab();
                    datosRow[3] = miContrato.getJor_tipo();
                    datosRow[4] = miContrato.getTipoctto();
                    datosRow[5] = funcion.formatoFecha_es(miContrato.getF_desde().toString());
                    if (miContrato.getF_hasta() == null || miContrato.getF_hasta().toString().contains("9999-12-31"))
                        datosRow[6] = "Indefinido";
                    else
                        datosRow[6] = funcion.formatoFecha_es(miContrato.getF_hasta().toString());
                    datosRow[7] = miContrato.getIdtrabajador().toString();                   
                    vistaRH.tablaContratosAddRow(datosRow);
                }
            }
        }
        else
        {
            String nomCliente = vistaRH.getComboClientesSelectedItem().toString();
            String mensaje = "No se ha encontrado ningún contrato de " + nomCliente;
            showMessageDialog(null, mensaje,"Errores detectados",WARNING_MESSAGE);
        }
    }
    
    public void botonPDFclicked(){
        
        RegistroHorario reghor = crearRegistroHorario();
        String pathFile = reghor.guardarRegistoHorarioParaPDF();
        reghor.RHtoPDF(pathFile);
        String mensaje = " PDF del Registro Horario guardado en su carpeta \"Borrame\" ";
        showMessageDialog(null, mensaje,"Creación de PDF",INFORMATION_MESSAGE);
    }

    public void botonImprimirClicked(){
        
        RegistroHorario reghor = crearRegistroHorario();
        String pathFile = reghor.guardarRegistroHorarioParaImprimir();
        reghor.RegistroHorarioToPrinterWithLibreOffice(pathFile);
        String mensaje = " Registro Horario enviado a la impresora ";
        showMessageDialog(null, mensaje,"Impresión de documentos",INFORMATION_MESSAGE);
    }

    public RegistroHorario crearRegistroHorario(){
        
        Funciones funcion = new Funciones();
        
        String mesRH = vistaRH.getComboMesesSelectedItem().toString();
        String annoRH = vistaRH.getAnno();
        String clienteGM = vistaRH.getComboClientesSelectedItem().toString();
        // CCC
        int indexSelected = vistaRH.getComboClientesSelectedIndex();
        String CCC = vistaRH.getTablaContratosValueAt(vistaRH.getTablaContratosSelectedRow(), 0).toString();
        
        String nomEmpleado = vistaRH.getTablaContratosValueAt(vistaRH.getTablaContratosSelectedRow(), 1).toString();
        // NIF empleado
        String NIFEmpleado = null;
        ArrayList<PersonaVO> lista = new ArrayList<>();
        PersonaVO persona = null;
        int idPersona = Integer.parseInt(vistaRH.getTablaContratosValueAt(vistaRH.getTablaContratosSelectedRow(), 7).toString());
        lista = modeloRH.getPersona(idPersona);
        for (int i = 0; i < lista.size(); i++)
            persona = lista.get(i);
        NIFEmpleado = funcion.formatoNIF(persona.getNifcif());

        String jornada = vistaRH.getTablaContratosValueAt(vistaRH.getTablaContratosSelectedRow(), 2).toString();
        
        RegistroHorario reghor = new RegistroHorario(mesRH, annoRH,clienteGM,CCC,nomEmpleado,NIFEmpleado, jornada);
        
        return reghor;
    }
}
