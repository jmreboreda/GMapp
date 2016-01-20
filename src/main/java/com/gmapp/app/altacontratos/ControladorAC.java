/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.app.altacontratos;


import com.gmapp.app.registrohorario.ComprobarEmisionRegistroHorario;
import com.gmapp.app.registrohorario.RegistroHorario;
import com.gmapp.dao.EstudiosDAO;
import com.gmapp.utilidades.Funciones;
import com.gmapp.vo.ClienteWithCCCVO;
import com.gmapp.vo.EstudiosVO;
import com.gmapp.vo.PersonaVO;
import com.gmapp.vo.TipoContratoVO;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;


public class ControladorAC {

    private ModeloAC modelo;
    private VistaAC vista;

    private boolean cargandoClientes = false;
    private boolean cargandoTrabajadores = false;
    
    //private ArrayList <ClienteVO> lista = new ArrayList();
    private List<String> listaNombresClientes = new ArrayList<>();
    private List <Integer> listaIDClientes = new ArrayList();
    private List <String> listaNombresTrabajadores = new ArrayList<>();    
    private List <Integer> listaIDTrabajadores = new ArrayList();
    private List <String> listaNombresTiposContrato = new ArrayList<>();
    private List <Integer> listaIDTiposContratos = new ArrayList();
    private String mensajeAviso;
    
    private Boolean tablaHorarioVaciaEsOK = false;
    private Boolean emisionRegistroHorario = false;

    public ControladorAC(ModeloAC modelo, VistaAC vista) {
        
        this.modelo = modelo;
        this.vista = vista;
        // *******************************************************
        // Pasa a la vista los items del combo de clientes con CCC
        // *******************************************************
        cargandoClientes = true;

        ClienteWithCCCVO miClienteConCCC;
        ArrayList <ClienteWithCCCVO> listaClientes = modelo.getAllClientesWithCCC();
        if (listaClientes.size() > 0){
            for (int i = 0; i < listaClientes.size(); i++){
                miClienteConCCC = listaClientes.get(i);
                listaNombresClientes.add(miClienteConCCC.getNom_rzsoc());
                listaIDClientes.add(miClienteConCCC.getIdcliente());
            }
        }
        else{
            System.out.println("No se ha podido cargar el comboBox de Clientes");
        }
        vista.cargaComboClientes(listaNombresClientes);
        cargandoClientes = false;
        // ****************************************************
        // Pasa a la vista los items del combo de trabajadores.
        // ****************************************************
        cargandoTrabajadores = true;
        PersonaVO miTrabajador;
        ArrayList <PersonaVO> listaTrabajadores = modelo.getAllPersonas();
        if(listaTrabajadores.size() > 0){
             for (int i = 0; i < listaTrabajadores.size(); i++){
                 miTrabajador = listaTrabajadores.get(i);
                 if (miTrabajador.getApellidos().contains("PNF"))
                     continue;
                 listaNombresTrabajadores.add(miTrabajador.getApellidos() + ", "
                 + miTrabajador.getNom_rzsoc());
                 listaIDTrabajadores.add(miTrabajador.getIdpersona());
             }
        }
         else{
            System.out.println("No se ha podido cargar el comboBox de Trabajadores");
        }
        vista.cargaComboTrabajadores(listaNombresTrabajadores);
        cargandoTrabajadores = false;
        // *********************************************************
        // Pasa a la vista los items del combo de tipos de contratos.
        // *********************************************************
        TipoContratoVO miTipoContrato;
        ArrayList <TipoContratoVO> listaTiposContrato = modelo.getAllTiposContratos();
        if(listaTiposContrato.size() > 0){
             for (int i = 0; i < listaTiposContrato.size(); i++){
                 miTipoContrato = listaTiposContrato.get(i);
                 listaNombresTiposContrato.add(miTipoContrato.getDescripcttoTipoContrato());
                 listaIDTiposContratos.add(miTipoContrato.getIdTipoContrato());
             }
        }
         else{
            System.out.println("No se ha podido cargar el comboBox de Tipos de contratos");
        }
        vista.cargaComboTiposContratos(listaNombresTiposContrato);
    }

    public void cambiadoCliente(){
              
        if (cargandoClientes)
            return;
        
        int indexSelected = vista.getComboClienteSelectedIndex();
        
         if(indexSelected == 0)
         {
            vista.comboClienteCCCremoveAllItem();
            vista.setBotonAceptarEnabled(false);
            return;
         }
        
        int idCliente =  listaIDClientes.get(indexSelected -1);
        
        ArrayList<ClienteWithCCCVO> cccEncontrados;
        ClienteWithCCCVO miCCCVO = null;
        List listaCCC = new Vector();
        
        cccEncontrados = modelo.getClienteCCC(idCliente);
        if (cccEncontrados.size() > 0 && cccEncontrados.get(0).getCcc_inss() != null)
        {
            for (int i = 0; i < cccEncontrados.size(); i++){
                
                miCCCVO = cccEncontrados.get(i);
                listaCCC.add(miCCCVO.getCcc_inss());
            }
            
            vista.cargaComboClienteCCC(listaCCC);
        }
        else
        {   
            vista.comboClienteCCCremoveAllItem();
            String mensaje = "No se ha encontrado ningún CCC para este cliente";
            showMessageDialog(null, mensaje,"Errores detectados",WARNING_MESSAGE);

        }
        
        if(vista.getComboTrabajadorSelectedIndex() != 0)
            vista.setBotonAceptarEnabled(true);
    }

    public void cambiadoTrabajador() {

        if (cargandoTrabajadores)
            return;
       
        int indexSelected = vista.getComboTrabajadorSelectedIndex();
        
        if(indexSelected == 0)
        {
            vista.setBotonAceptarEnabled(false);
            limpiarDatosTrabajador();
            return;
        }
        
        int idTrabajador =  listaIDTrabajadores.get(indexSelected -1);

        ArrayList<PersonaVO> personaEncontrada;
        PersonaVO miTrabajador;
        
        Funciones funcion = new Funciones();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
        
        personaEncontrada = modelo.getPersona(idTrabajador);
        if(personaEncontrada.size() > 0){
            for (int i = 0; i < personaEncontrada.size(); i++){
                miTrabajador = personaEncontrada.get(i);
            
                vista.setEtqNIF(funcion.formatoNIF(miTrabajador.getNifcif()));
                vista.setEtqNASS(miTrabajador.getNumafss());
                if(miTrabajador.getFechanacim() != null)
                    vista.setEtqFechaNacim(formatoFecha.format(miTrabajador.getFechanacim()));
                else
                    vista.setEtqFechaNacim("");
                vista.setEtqEstadoCivil(miTrabajador.getEstciv());
                vista.setEtqNacionalidad(miTrabajador.getNacionalidad());
                StringBuilder direccionCompleta = new StringBuilder();
                if (miTrabajador.getDireccion() == null)
                    vista.setEtqDireccion("");
                else
                {
                    direccionCompleta.append(miTrabajador.getDireccion()).append("  ");
                    direccionCompleta.append(miTrabajador.getCodpostal()).append("  ");
                    direccionCompleta.append(miTrabajador.getLocalidad());                   
                    vista.setEtqDireccion(direccionCompleta.toString());
                }
                EstudiosDAO estudios = new EstudiosDAO();
                ArrayList<EstudiosVO> estudiosVO = new ArrayList<>();
                EstudiosVO misEstudiosVO = new EstudiosVO();
           
                estudiosVO = estudios.readEstudios(Integer.parseInt(miTrabajador.getNivestud().trim()));
                if(estudiosVO.size() > 0){
                    for (i = 0; i < estudiosVO.size(); i++)
                        misEstudiosVO = estudiosVO.get(i);
                    
                    vista.setEtqNivelEstudios(misEstudiosVO.getDescripEstudios());    
                }
                else
                {
                vista.muestraError("No se han encontrado estudios para persona con idpersona = " + idTrabajador);
                }
            }
        }
        else{
            vista.muestraError("No se ha encontrado ninguna persona con idpersona = " + idTrabajador);
        }
        
        if(vista.getComboClienteSelectedIndex() != 0)
             vista.setBotonAceptarEnabled(true);
    }
    
    public void cambiadoTipoContrato(){
        if(vista.getComboTipoContratoSelectedIndex() == 0)
        {
            vista.setTipoContratoOtros("");
            vista.settfTipoContratoOtrosEnabled(false);
        }
        
        if(vista.getComboTipoContratoSelectedItem().toString().equals("Otros tipos"))
            vista.settfTipoContratoOtrosEnabled(true);
        else
        {
            vista.settfTipoContratoOtrosEnabled(false);
            vista.setTipoContratoOtros("");
        } 
    }
    
    public void cambiadoDuracionContrato(){
        if(vista.getComboDuracionContratoSelectedIndex() == 0)
        {
            cambiadoTipoContrato();
            vista.setFechaDesde("");
            vista.setFechaHasta("");
            vista.setDiasContrato("");
        }
        else if (vista.getComboDuracionContratoSelectedItem().toString().equals("Indefinido"))
            vista.settfFechaHastaEnabled(false);
        else
            vista.settfFechaHastaEnabled(true);
    }
    
    public void cambiadoFechaInicioContrato(){
        
        Funciones funcion = new Funciones();
        String sFecha = null;
        if (funcion.validaFechaMascara(vista.getFechaInicioContrato().trim(), "ddMMyyyy") ||
                funcion.validaFechaMascara(vista.getFechaInicioContrato().trim(), "dd-MM-yyyy"))
        {
            if (vista.getFechaInicioContrato().trim().length() == 8)
                {
                    sFecha = vista.getFechaInicioContrato().trim();
                    String sFechaFormated = sFecha.substring(0, 2) + "-" + sFecha.substring(2, 4) + "-" + sFecha.substring(4, 8);
                    vista.setFechaDesde(sFechaFormated);
                } 
        }
        else
        {
           vista.setFechaDesde("");
           vista.setDiasContrato("");
        }
        
        diasDuracionContrato();
    }
    
    public void cambiadoFechaFinContrato(){
        
        Funciones funcion = new Funciones();
        String sFecha = "";
        if (funcion.validaFechaMascara(vista.getFechaFinContrato().trim(), "ddMMyyyy") ||
                funcion.validaFechaMascara(vista.getFechaFinContrato().trim(), "dd-MM-yyyy"))
        {
            if (vista.getFechaFinContrato().trim().length() == 8)
                {
                    sFecha = vista.getFechaFinContrato().trim();
                    String sFechaFormated = sFecha.substring(0, 2) + "-" + sFecha.substring(2, 4) + "-" + sFecha.substring(4, 8);
                    vista.setFechaHasta(sFechaFormated);
                } 
        }
        else
        {
           vista.setFechaHasta("");
           vista.setDiasContrato("");
        }
        
        diasDuracionContrato();
    }
    
    public void diasDuracionContrato(){
        
        if(vista.getFechaInicioContrato().trim().isEmpty() ||
               vista.getFechaFinContrato().trim().isEmpty())
                return;
        
        Funciones funcion = new Funciones();

        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día        

        String sFechaDesde = "";
        String sFechaHasta = "";
        String sFechaUS = "";
        int iFechaDesde = 0;
        int iFechaHasta = 0;

        Calendar dFechaD = new GregorianCalendar(Integer.parseInt(vista.getFechaInicioContrato().substring(6, 10)),
                Integer.parseInt(vista.getFechaInicioContrato().substring(3, 5)) -1,
                Integer.parseInt(vista.getFechaInicioContrato().substring(0, 2))); 
        Calendar dFechaH = new GregorianCalendar(Integer.parseInt(vista.getFechaFinContrato().substring(6, 10)),
                Integer.parseInt(vista.getFechaFinContrato().substring(3, 5)) -1,
                Integer.parseInt(vista.getFechaFinContrato().substring(0, 2))); 
        
        // Comprobamos que FechaHasta >= FechaDesde
        sFechaDesde = vista.getFechaInicioContrato();
        sFechaUS = funcion.formatoFecha_us(sFechaDesde);
        iFechaDesde = Integer.parseInt(sFechaUS.replace("-",""));
        
        sFechaHasta = vista.getFechaFinContrato();
        sFechaUS = funcion.formatoFecha_us(sFechaHasta);
        iFechaHasta = Integer.parseInt(sFechaUS.replace("-",""));
        
        if(iFechaHasta < iFechaDesde)
            vista.setFechaDesde("");
        
        vista.setDiasContrato("[ " +(dFechaH.getTimeInMillis() - dFechaD.getTimeInMillis() + MILLSECS_PER_DAY)/ MILLSECS_PER_DAY + " días ]");
    }
    
    public void verificaHorasSemana(){
        
        NumberFormat fmtHora = new DecimalFormat("#0.00");
        Double dNumHoras = 0D;
        
        try{
            dNumHoras = Double.parseDouble(vista.getHorasSemana());
        }
        catch(Exception e){
            vista.setHorasSemana("");
            return;
        }
        
        if(dNumHoras >= 39D)
            vista.setHorasSemana("");
        else
            vista.setHorasSemana(fmtHora.format(dNumHoras));
    }
    
    public void cambiadoJornada(){
        if(vista.getComboJornadaSelectedIndex() == 0 ||
                vista.getComboJornadaSelectedItem().toString().trim().equals("Jornada completa"))
        {
            vista.setHorasSemana("");
            vista.settfHorasSemanaEnabled(false);
        }
        else
            vista.settfHorasSemanaEnabled(true);
    }
        
    private void limpiarDatosTrabajador(){
        vista.setEtqNIF("");
        vista.setEtqNASS("");
        vista.setEtqFechaNacim("");
        vista.setEtqEstadoCivil("");
        vista.setEtqNacionalidad("");
        vista.setEtqDireccion("");
        vista.setEtqNivelEstudios("");
    }
        
    private boolean comprobarDatosContrato(){
        
        Boolean comprobadoOK = true;
        ComprobarDatosVistaContratos comprobacion = new ComprobarDatosVistaContratos();
        comprobadoOK = comprobacion.ComprobarDatosVistaContratos(vista);
        
        return comprobadoOK;
    }
    
    public void grabarDatosContrato(){
        
        List datosContrato = new ArrayList();
        Funciones funcion = new Funciones();

        int ultimoNumeroContrato = modelo.getUltimoNumeroContrato();
        datosContrato.add(ultimoNumeroContrato + 1);
        // Número de variación: cero, al ser contrato inicial
        datosContrato.add(0);
        // Tipo variacion: tipo contrato al ser número de variación = 0
        int indexTipoContratoSelected = vista.getComboTipoContratoSelectedIndex();
        int idTipoContrato =  listaIDTiposContratos.get(indexTipoContratoSelected -1);
        datosContrato.add(idTipoContrato);
        // Idcliente GM        
        datosContrato.add(listaIDClientes.get(vista.getComboClienteSelectedIndex()-1));
        // ClienteGM Nombre
        datosContrato.add(vista.getClienteName());        
        // Cliente CCC
        datosContrato.add(vista.getComboClienteCCCSelectedItem());
        // Id y nombre trabajador
        datosContrato.add(listaIDTrabajadores.get(vista.getComboTrabajadorSelectedIndex()-1));
        datosContrato.add(vista.getTrabajadorName());
        // Categoria
        datosContrato.add(vista.getCategoria());
        // Jornada
        if(vista.getComboJornadaSelectedItem().toString().equals("Jornada completa"))
            datosContrato.add("Jornada completa");
        else
            datosContrato.add(vista.getHorasSemana() + " horas/semana");
        // Jornada, días
        String sDiasSemana = "";
        for (int i = 0; i < vista.getDiasSemana().size(); i++)
            sDiasSemana = sDiasSemana + vista.getDiasSemana().get(i).toString();
        datosContrato.add(sDiasSemana);
        // Jornada, tipo
        if(vista.getComboJornadaSelectedItem().toString().contains("completa"))
            datosContrato.add("Completa"); 
        else
        {
            datosContrato.add("Parcial");
//            emisionRegistroHorario = true;
        }
        // Tipo contrato
        datosContrato.add(vista.getTipoContrato());
        
//        if(vista.getTipoContrato().contains("Formación"))
//            emisionRegistroHorario = true;
        // Fecha inicio contrato
        datosContrato.add(funcion.formatoFecha_us(vista.getFechaInicioContrato()));
        // Fecha fin contrato
        if(vista.getFechaFinContrato().isEmpty())   // Es un contrato Indefinido
            datosContrato.add("null");
        else
            datosContrato.add(funcion.formatoFecha_us(vista.getFechaFinContrato()));
        // Número contrato INEM
        datosContrato.add("Pendiente");
        // En vigor
        datosContrato.add("TRUE");
        // Notas gestor
        datosContrato.add(vista.getAreaGestor());
        String notificacion = "[Notificación cliente: " + vista.getFechaNotificacion() +
                " a las " + vista.getHoraNotificacion() + "]\\n";
        // Notas privadas
        datosContrato.add(notificacion + vista.getAreaPrivada());
        // Duración
        if(vista.getFechaFinContrato().isEmpty())   // Es un contrato Indefinido
            datosContrato.add("I");
        else
            datosContrato.add("T");
        // 
        //  Grabamos el Contrato
        //
        int numcontrato = modelo.saveContrato(datosContrato);
        if(numcontrato > 0)
            vista.muestraInfo("El contrato se ha guardado con el número " + numcontrato);
        else {
            vista.muestraError("ERROR: No se ha guardado el contrato");
            return;
        }
        // Grabamos el horario, en su caso
        GrabarHorarioContrato horario = new GrabarHorarioContrato(numcontrato, vista);
        //
        // Imprimimos la Portada Expediente Contrato
        //
        PortadaExpedienteContrato pec = new PortadaExpedienteContrato(vista);
        //************************************
        // Control de emisión Registro Horario
        //************************************
        ComprobarEmisionRegistroHorario comprobarRH = new ComprobarEmisionRegistroHorario();
        emisionRegistroHorario = comprobarRH.Emision(numcontrato,0);
        if(emisionRegistroHorario){
        


            SimpleDateFormat nombreMes = new SimpleDateFormat("MMMM");
            SimpleDateFormat fechaCompleta = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat annoInFecha = new SimpleDateFormat("yyyy");

            Date fechaInicioCtto = new Date();
            try{
                fechaInicioCtto = fechaCompleta.parse(vista.getFechaInicioContrato());
            }
            catch(Exception e){

            }
            String mesRH = nombreMes.format(fechaInicioCtto);
            String annoRH = annoInFecha.format(fechaInicioCtto);
            // Creamos el PDF del Registro Horario
            RegistroHorario reghor = new RegistroHorario(mesRH, annoRH, 
                    vista.getClienteName(),
                    vista.getComboClienteCCCSelectedItem(),vista.getTrabajadorName(),
                    vista.getTrabajadorNIF(), vista.getHorasSemana() + " horas/semana");
            String pathFile = reghor.guardarRegistoHorarioParaPDF();
            reghor.RHtoPDF(pathFile);

            // Preguntamos sobre la impresión del Registro Horario
            int respuesta = JOptionPane.showConfirmDialog(
            null,
            "Se ha creado el PDF del Registro Horario de " + mesRH + "-" + annoRH + " para "
                    + vista.getTrabajadorName() + " en su carpeta \"Borrame\"\n"
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
        CarpetaA3ControlGestor a3 = new CarpetaA3ControlGestor(vista);

        showMessageDialog(null, "El registro en la base de datos y la emisión de documentación\n"
            + "del nuevo contrato se han realizado correctamente.","Nuevo contrato de trabajo"
                ,INFORMATION_MESSAGE);
    }
    
    public void botonAceptarMouseClicked(){
        if (vista.botonAceptarIsEnabled())
            if(comprobarDatosContrato())
            {
                vista.setBotonAceptarEnabled(false);
                grabarDatosContrato();
            }
    }
}
