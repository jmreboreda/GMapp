/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.app.altacontratos;


import com.gmapp.app.registrohorario.RegistroHorario;
import com.gmapp.dao.EstudiosDAO;
import com.gmapp.utils.ContractUtils;
import com.gmapp.utils.Funciones;
import com.gmapp.vo.ClienteVO;
import com.gmapp.vo.ContratoVO;
import com.gmapp.vo.EstudiosVO;
import com.gmapp.vo.PersonaVO;
import com.gmapp.vo.TipoContratoVO;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;


public class ControladorAltaContratos {

    private ModeloAltaContratos modeloAltaContratos;
    private VistaAltaContratos vistaAltaContratos;

    private String INDEFINIDO;
    private List<String> listaItemsNombresClientes = new ArrayList<>();
    private List <String> listaItemsNombresTrabajadores = new ArrayList<>();    
    private List <String> listaNombresTiposContrato = new ArrayList<>();
    private Map<String,Integer> clientesNomId;
    private Map<String, Integer> trabajadoresNomId;
    private Map<String, Integer> tiposContratosTipoId;
    
    private Boolean tablaHorarioVaciaEsOK = false;
    private Boolean emisionRegistroHorario = false;

    public ControladorAltaContratos(ModeloAltaContratos modelo, VistaAltaContratos vista) {
        
        this.modeloAltaContratos = modelo;
        this.vistaAltaContratos = vista;
        // *******************************************************
        // Pasa a la vistaAltaContratos los items del combo de clientes con CCC
        // *******************************************************
        clientesNomId = new HashMap<>();
        trabajadoresNomId = new HashMap<>();
        ClienteVO miClienteConCCC;
        List <ClienteVO> listaClientes = modeloAltaContratos.getAllClientesWithCCC();
        if (listaClientes.size() > 0){
            for(ClienteVO cliente : listaClientes){
                miClienteConCCC = cliente;
                listaItemsNombresClientes.add(miClienteConCCC.getNom_rzsoc());
                clientesNomId.put(miClienteConCCC.getNom_rzsoc(), miClienteConCCC.getIdcliente());
            }
        }
        else{
            System.out.println("No se ha podido cargar el comboBox de Clientes");
        }
        vistaAltaContratos.clientsSelectorDisabled();
        vistaAltaContratos.loadClientsSelector(listaItemsNombresClientes);
        vistaAltaContratos.clientsSelectorEnabled();
        // ****************************************************
        // Pasa a la vistaAltaContratos los items del combo de trabajadores.
        // ****************************************************
        PersonaVO miTrabajador;
        List <PersonaVO> listaTrabajadores = modeloAltaContratos.getAllPersonas();
        if(listaTrabajadores.size() > 0){
             for (int i = 0; i < listaTrabajadores.size(); i++){
                 miTrabajador = listaTrabajadores.get(i);
                 if (miTrabajador.getApellidos().contains("PNF"))
                     continue;
                 listaItemsNombresTrabajadores.add(miTrabajador.getApellidos() + ", "
                 + miTrabajador.getNom_rzsoc());
                 trabajadoresNomId.put(miTrabajador.getApellidos() + ", "
                 + miTrabajador.getNom_rzsoc(), miTrabajador.getIdpersona());
             }
        }
         else{
            System.out.println("No se ha podido cargar el comboBox de Trabajadores");
        }
        vistaAltaContratos.employeesSelectorDisabled();
        vistaAltaContratos.loadEmployeesSelector(listaItemsNombresTrabajadores);
        vistaAltaContratos.employeesSelectorEnabled();
        // *********************************************************
        // Pasa a la vistaAltaContratos los items del combo de tipos de contratos.
        // *********************************************************
        tiposContratosTipoId = new HashMap<>();
        TipoContratoVO miTipoContrato;
        List <TipoContratoVO> listaTiposContrato = modeloAltaContratos.getAllTiposContratos();
        if(listaTiposContrato.size() > 0){
            for(TipoContratoVO tipo : listaTiposContrato){
                 miTipoContrato = tipo;
                 listaNombresTiposContrato.add(miTipoContrato.getDescripcttoTipoContrato());
                 tiposContratosTipoId.put(miTipoContrato.getDescripcttoTipoContrato(), miTipoContrato.getIdTipoContrato());
             }
        }
         else{
            System.out.println("No se ha podido cargar el comboBox de Tipos de contratos");
        }
        vistaAltaContratos.contractTypeSelectorDisabled();
        vistaAltaContratos.loadContractTypeSelector(listaNombresTiposContrato);
        vistaAltaContratos.contractTypeSelectorEnabled();
    }

    public void clientChanged(){
              
        if (vistaAltaContratos.getClientsSelector().isEnabled() == false)
            return;
        
        int indexSelected = vistaAltaContratos.getClientsSelector().getSelectedIndex();
        
         if(indexSelected == 0)
         {
            vistaAltaContratos.comboClienteCCCremoveAllItem();
            vistaAltaContratos.setBotonAceptarEnabled(false);
            return;
         }
        
        List<ClienteVO> cccEncontrados;
        ClienteVO miCCCVO = null;
        List listaCCC = new ArrayList();
        
        int idCliente = clientesNomId.get(vistaAltaContratos.getClientName());
        
        cccEncontrados = modeloAltaContratos.getClienteCCC(idCliente);
        if (cccEncontrados.size() > 0 && cccEncontrados.get(0).getCcc_inss() != null)
        {
            for(ClienteVO ccc : cccEncontrados){
                miCCCVO = ccc;
                listaCCC.add(miCCCVO.getCcc_inss());
            }
            
            vistaAltaContratos.cargaComboClienteCCC(listaCCC);
        }
        else
        {   
            vistaAltaContratos.comboClienteCCCremoveAllItem();
            String mensaje = "No se ha encontrado ningún CCC para este cliente";
            showMessageDialog(null, mensaje,"Errores detectados",WARNING_MESSAGE);

        }
        
        if(vistaAltaContratos.getComboTrabajador().getSelectedIndex() != 0)
            vistaAltaContratos.setBotonAceptarEnabled(true);
    }

    public void employeeChanged() {

        if (vistaAltaContratos.getEmployeesSelector().isEnabled() == false)
            return;
        
        if(vistaAltaContratos.getEmployeesSelector().getSelectedIndex() == 0)
        {
            vistaAltaContratos.setBotonAceptarEnabled(false);
            limpiarDatosTrabajador();
            return;
        }

        int idTrabajador = trabajadoresNomId.get(vistaAltaContratos.getEmployeeName());

        List<PersonaVO> personaEncontrada;
        PersonaVO miTrabajador;
        
        Funciones funcion = new Funciones();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
        
        personaEncontrada = modeloAltaContratos.getPersona(idTrabajador);
        if(personaEncontrada.size() > 0){
            for(PersonaVO persona : personaEncontrada){
                miTrabajador = persona;
            
                vistaAltaContratos.setEtqNIF(funcion.formatoNIF(miTrabajador.getNifcif()));
                vistaAltaContratos.setEtqNASS(miTrabajador.getNumafss());
                if(miTrabajador.getFechanacim() != null)
                    vistaAltaContratos.setEtqFechaNacim(formatoFecha.format(miTrabajador.getFechanacim()));
                else
                    vistaAltaContratos.setEtqFechaNacim("");
                vistaAltaContratos.setEtqEstadoCivil(miTrabajador.getEstciv());
                vistaAltaContratos.setEtqNacionalidad(miTrabajador.getNacionalidad());
                StringBuilder direccionCompleta = new StringBuilder();
                if (miTrabajador.getDireccion() == null)
                    vistaAltaContratos.setEtqDireccion("");
                else
                {
                    direccionCompleta.append(miTrabajador.getDireccion()).append("  ");
                    direccionCompleta.append(miTrabajador.getCodpostal()).append("  ");
                    direccionCompleta.append(miTrabajador.getLocalidad());                   
                    vistaAltaContratos.setEtqDireccion(direccionCompleta.toString());
                }
                EstudiosDAO estudios = new EstudiosDAO();
                ArrayList<EstudiosVO> estudiosVO = new ArrayList<>();
                EstudiosVO misEstudiosVO = new EstudiosVO();
           
                estudiosVO = estudios.readEstudios(Integer.parseInt(miTrabajador.getNivestud().trim()));
                if(estudiosVO.size() > 0){
                    for (EstudiosVO myestudios: estudiosVO)
                        misEstudiosVO = myestudios;
                    
                    vistaAltaContratos.setEtqNivelEstudios(misEstudiosVO.getDescripEstudios());    
                }
                else
                {
                vistaAltaContratos.muestraError("No se han encontrado estudios para persona con idpersona = " + idTrabajador);
                }
            }
        }
        else{
            vistaAltaContratos.muestraError("No se ha encontrado ninguna persona con idpersona = " + idTrabajador);
        }
        
        if(vistaAltaContratos.getComboCliente().getSelectedIndex() != 0)
             vistaAltaContratos.setBotonAceptarEnabled(true);
    }
    
    public void cambiadoTipoContrato(){
        if(vistaAltaContratos.getComboTiposContrato().getSelectedIndex() == 0)
        {
            vistaAltaContratos.setTipoContratoOtros("");
            vistaAltaContratos.settfTipoContratoOtrosEnabled(false);
        }
        
        if(vistaAltaContratos.getComboTiposContrato().getSelectedItem().toString().equals("Otros tipos"))
            vistaAltaContratos.settfTipoContratoOtrosEnabled(true);
        else
        {
            vistaAltaContratos.settfTipoContratoOtrosEnabled(false);
            vistaAltaContratos.setTipoContratoOtros("");
        } 
    }
    
    public void cambiadoDuracionContrato(){

        INDEFINIDO = ContractUtils.getContractUtil(ContractUtils.INDEFINIDO);
        if(vistaAltaContratos.getComboDuracionContrato().getSelectedIndex() == 0)
        {
            cambiadoTipoContrato();
            vistaAltaContratos.setFechaDesde("");
            vistaAltaContratos.setFechaHasta("");
            vistaAltaContratos.setDiasContrato("");
        }
        else if (vistaAltaContratos.getContractPermanentOrTemporal().equals(INDEFINIDO))
            vistaAltaContratos.settfFechaHastaEnabled(false);
        else
            vistaAltaContratos.settfFechaHastaEnabled(true);
    }
    
    public void cambiadoFechaInicioContrato(){
        
        Funciones funcion = new Funciones();
        String sFecha = null;
        if (funcion.validaFechaMascara(vistaAltaContratos.getContractStartDate().trim(), "ddMMyyyy") ||
                funcion.validaFechaMascara(vistaAltaContratos.getContractStartDate().trim(), "dd-MM-yyyy"))
        {
            if (vistaAltaContratos.getContractStartDate().trim().length() == 8)
                {
                    sFecha = vistaAltaContratos.getContractStartDate().trim();
                    String sFechaFormated = sFecha.substring(0, 2) + "-" + sFecha.substring(2, 4) + "-" + sFecha.substring(4, 8);
                    vistaAltaContratos.setFechaDesde(sFechaFormated);
                } 
        }
        else
        {
           vistaAltaContratos.setFechaDesde("");
           vistaAltaContratos.setDiasContrato("");
        }
        
        diasDuracionContrato();
    }
    
    public void cambiadoFechaFinContrato(){
        
        Funciones funcion = new Funciones();
        String sFecha = "";
        if (funcion.validaFechaMascara(vistaAltaContratos.getContractTerminationDate().trim(), "ddMMyyyy") ||
                funcion.validaFechaMascara(vistaAltaContratos.getContractTerminationDate().trim(), "dd-MM-yyyy"))
        {
            if (vistaAltaContratos.getContractTerminationDate().trim().length() == 8)
                {
                    sFecha = vistaAltaContratos.getContractTerminationDate().trim();
                    String sFechaFormated = sFecha.substring(0, 2) + "-" + sFecha.substring(2, 4) + "-" + sFecha.substring(4, 8);
                    vistaAltaContratos.setFechaHasta(sFechaFormated);
                } 
        }
        else
        {
           vistaAltaContratos.setFechaHasta("");
           vistaAltaContratos.setDiasContrato("");
        }
        
        diasDuracionContrato();
    }
    
    public void diasDuracionContrato(){
        
        if(vistaAltaContratos.getContractStartDate().trim().isEmpty() ||
               vistaAltaContratos.getContractTerminationDate().trim().isEmpty())
                return;
        
        Funciones funcion = new Funciones();

        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día        

        String sFechaDesde = "";
        String sFechaHasta = "";
        String sFechaUS = "";
        int iFechaDesde = 0;
        int iFechaHasta = 0;

        Calendar dFechaD = new GregorianCalendar(Integer.parseInt(vistaAltaContratos.getContractStartDate().substring(6, 10)),
                Integer.parseInt(vistaAltaContratos.getContractStartDate().substring(3, 5)) -1,
                Integer.parseInt(vistaAltaContratos.getContractStartDate().substring(0, 2))); 
        Calendar dFechaH = new GregorianCalendar(Integer.parseInt(vistaAltaContratos.getContractTerminationDate().substring(6, 10)),
                Integer.parseInt(vistaAltaContratos.getContractTerminationDate().substring(3, 5)) -1,
                Integer.parseInt(vistaAltaContratos.getContractTerminationDate().substring(0, 2))); 
        
        // Comprobamos que FechaHasta >= FechaDesde
        sFechaDesde = vistaAltaContratos.getContractStartDate();
        sFechaUS = funcion.formatoFecha_us(sFechaDesde);
        iFechaDesde = Integer.parseInt(sFechaUS.replace("-",""));
        
        sFechaHasta = vistaAltaContratos.getContractTerminationDate();
        sFechaUS = funcion.formatoFecha_us(sFechaHasta);
        iFechaHasta = Integer.parseInt(sFechaUS.replace("-",""));
        
        if(iFechaHasta < iFechaDesde)
            vistaAltaContratos.setFechaDesde("");
        
        vistaAltaContratos.setDiasContrato("[ " +(dFechaH.getTimeInMillis() - dFechaD.getTimeInMillis() + MILLSECS_PER_DAY)/ MILLSECS_PER_DAY + " días ]");
    }
    
    public void verificaHorasSemana(){
        
        NumberFormat fmtHora = new DecimalFormat("#0.00");
        Double dNumHoras = 0D;
        
        try{
            dNumHoras = Double.parseDouble(vistaAltaContratos.getWeekHours());
        }
        catch(Exception e){
            vistaAltaContratos.setHorasSemana("");
            return;
        }
        
        if(dNumHoras >= 39D)
            vistaAltaContratos.setHorasSemana("");
        else
            vistaAltaContratos.setHorasSemana(fmtHora.format(dNumHoras));
    }
    
    public void cambiadoJornada(){
        if(vistaAltaContratos.getComboJornada().getSelectedIndex() == 0 ||
                vistaAltaContratos.getComboJornada().getSelectedItem().toString().trim().equals("Jornada completa"))
        {
            vistaAltaContratos.setHorasSemana("");
            vistaAltaContratos.settfHorasSemanaEnabled(false);
        }
        else
            vistaAltaContratos.settfHorasSemanaEnabled(true);
    }
        
    private void limpiarDatosTrabajador(){
        vistaAltaContratos.setEtqNIF("");
        vistaAltaContratos.setEtqNASS("");
        vistaAltaContratos.setEtqFechaNacim("");
        vistaAltaContratos.setEtqEstadoCivil("");
        vistaAltaContratos.setEtqNacionalidad("");
        vistaAltaContratos.setEtqDireccion("");
        vistaAltaContratos.setEtqNivelEstudios("");
    }
        
    private boolean comprobarDatosContrato(){
        
        Boolean comprobadoOK = true;
        DatosVistaContratos comprobacion = new DatosVistaContratos();
        comprobadoOK = comprobacion.ComprobarDatosVistaContratos(vistaAltaContratos);
        
        return comprobadoOK;
    }
    
    public void grabarDatosContrato(){
        
        ContratoVO contratoVO = new ContratoVO();
        SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");

        int lastContractNumber = modeloAltaContratos.getLastContractNumber();
        contratoVO.setNumcontrato(lastContractNumber + 1);
        // Número de variación: cero, al ser contrato inicial
        contratoVO.setNumvariacion(0);
        // Tipo variacion: tipo contrato al ser número de variación = 0
        int idTipoContrato =  tiposContratosTipoId.get(vistaAltaContratos.getContractType());
        contratoVO.setTipovariacion(idTipoContrato);
        // Idcliente GM        
        contratoVO.setIdcliente_gm(clientesNomId.get(vistaAltaContratos.getClientName()));
        // ClienteGM Nombre
        contratoVO.setClientegm_name(vistaAltaContratos.getClientName());        
        // Cliente CCC
        contratoVO.setContrato_ccc(vistaAltaContratos.getCCCclient());
        // Id y nombre trabajador
        contratoVO.setIdtrabajador(trabajadoresNomId.get(vistaAltaContratos.getEmployeeName()));
        contratoVO.setTrabajador_name(vistaAltaContratos.getEmployeeName());
        // Categoria
        contratoVO.setCategoria(vistaAltaContratos.getCategoria());
        // Jornada
        if(vistaAltaContratos.getComboJornada().getSelectedItem().toString().equals("Jornada completa"))
            contratoVO.setJor_trab("Jornada completa");
        else
            contratoVO.setJor_trab(vistaAltaContratos.getWeekHours() + " horas/semana");
        // Jornada, días
        String sDiasSemana = "";
        for (int i = 0; i < vistaAltaContratos.getDiasSemana().size(); i++)
            sDiasSemana = sDiasSemana + vistaAltaContratos.getDiasSemana().get(i).toString();
        contratoVO.setJor_trab_dias(sDiasSemana);
        // Jornada, tipo
        if(vistaAltaContratos.getComboJornada().getSelectedItem().toString().contains("completa"))
            contratoVO.setJor_tipo("Completa"); 
        else
        {
            contratoVO.setJor_tipo("Parcial");
//            emisionRegistroHorario = true;
        }
        // Tipo contrato
        contratoVO.setTipoctto(vistaAltaContratos.getComboTiposContrato().getSelectedItem().toString());
        
        try {
            contratoVO.setF_desde(fecha.parse(vistaAltaContratos.getContractStartDate()));
        } catch (ParseException ex) {
            Logger.getLogger(ControladorAltaContratos.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(vistaAltaContratos.getContractTerminationDate().isEmpty())   // Es un contrato Indefinido
            contratoVO.setF_hasta(null);
        else
            try {
                contratoVO.setF_hasta(fecha.parse(vistaAltaContratos.getContractTerminationDate()));
        } catch (ParseException ex) {
            Logger.getLogger(ControladorAltaContratos.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Número contrato INEM
        contratoVO.setId_ctto_inem("Pendiente");
        // En vigor
        contratoVO.setEnvigor(true);
        // Notas gestor
        contratoVO.setNotas_gestor(vistaAltaContratos.getAreaGestor());
        String notificacion = "[Notificación cliente: " + vistaAltaContratos.getNotificationDate() +
                " a las " + vistaAltaContratos.getNotificationHour() + "]\\n";
        // Notas privadas
        contratoVO.setNotas_privadas(notificacion + vistaAltaContratos.getAreaPrivada());
        // Duración
        if(vistaAltaContratos.getContractTerminationDate().isEmpty())   // Es un contrato Indefinido
            contratoVO.setDuracion("I");
        else
            contratoVO.setDuracion("T");
        // 
        //  Grabamos el Contrato
        //
        int numcontrato = modeloAltaContratos.saveContrato(contratoVO);
        if(numcontrato > 0)
            vistaAltaContratos.muestraInfo("El contrato se ha guardado con el número " + numcontrato);
        else {
            vistaAltaContratos.muestraError("ERROR: No se ha guardado el contrato");
            return;
        }
        // Grabamos el horario, en su caso
        HorarioContrato horario = new HorarioContrato(numcontrato, vistaAltaContratos);
        //
        // Imprimimos la Portada Expediente Contrato
        //
        PortadaExpedienteContrato pec = new PortadaExpedienteContrato(vistaAltaContratos);
        //************************************
        // Control de emisión Registro Horario
        //************************************
        
        SimpleDateFormat nombreMes = new SimpleDateFormat("MMMM");
        SimpleDateFormat fechaCompleta = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat annoInFecha = new SimpleDateFormat("yyyy");

        Date fechaInicioCtto = new Date();
        try{
            fechaInicioCtto = fechaCompleta.parse(vistaAltaContratos.getContractStartDate());
        }
        catch(Exception e){

        }
        String mesRH = nombreMes.format(fechaInicioCtto);
        String annoRH = annoInFecha.format(fechaInicioCtto);
        String clienteGM = vistaAltaContratos.getClientName();
        String CCC = vistaAltaContratos.getClientCCC();
        String nomEmpleado = vistaAltaContratos.getEmployeeName();
        String nifEmpleado = vistaAltaContratos.getTrabajadorNIF();
        String jornada = vistaAltaContratos.getComboJornada().getSelectedItem().toString();
        
        RegistroHorario comprobarRH = new RegistroHorario(mesRH, annoRH, clienteGM,
            CCC, nomEmpleado, nifEmpleado, jornada);
        emisionRegistroHorario = comprobarRH.comprobarEmision(numcontrato,0);
        if(emisionRegistroHorario){
            // Creamos el PDF del Registro Horario
            RegistroHorario reghor = new RegistroHorario(mesRH, annoRH, 
                    vistaAltaContratos.getComboCliente().getSelectedItem().toString(),
                    vistaAltaContratos.getComboClienteCCC().getSelectedItem().toString(),vistaAltaContratos.getComboTrabajador().getSelectedItem().toString(),
                    vistaAltaContratos.getTrabajadorNIF(), vistaAltaContratos.getWeekHours() + " horas/semana");
            String pathFile = reghor.guardarRegistoHorarioParaPDF();
            reghor.RHtoPDF(pathFile);

            // Preguntamos sobre la impresión del Registro Horario
            int respuesta = JOptionPane.showConfirmDialog(null,
            "Se ha creado el PDF del Registro Horario de " + mesRH + "-" + annoRH + " para "
                    + vistaAltaContratos.getComboTrabajador().getSelectedItem().toString() + " en su carpeta \"Borrame\"\n"
                    + "¿Desea imprimir el Registro Horario en papel?",
            "Emisión Registro Horario",
            JOptionPane.YES_NO_OPTION);

            if(respuesta == JOptionPane.YES_OPTION){
                pathFile = reghor.guardarRegistroHorarioParaImprimir();
                reghor.RegistroHorarioToPrinterWithLibreOffice(pathFile);
                showMessageDialog(null, "El Registro Horario se ha enviado a la impresora","Emisión Registro Horario",INFORMATION_MESSAGE);
           }
        }
        //********************************************************
        // Imprimir la carpetilla A3 de control del gestor laboral
        //********************************************************
        CarpetaA3ControlGestor a3 = new CarpetaA3ControlGestor(vistaAltaContratos);

        showMessageDialog(null, "El registro en la base de datos y la emisión de documentación\n"
            + "del nuevo contrato se han realizado correctamente.","Nuevo contrato de trabajo"
                ,INFORMATION_MESSAGE);
    }
    
    public void botonAceptarMouseClicked(){
        if (vistaAltaContratos.getBotonAceptar().isEnabled())
            if(comprobarDatosContrato())
            {
                vistaAltaContratos.getBotonAceptar().setEnabled(false);
                grabarDatosContrato();
            }
    }
}
