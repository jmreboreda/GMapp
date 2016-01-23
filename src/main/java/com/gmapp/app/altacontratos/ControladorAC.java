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
import com.gmapp.vo.ClienteVO;
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

    private ModeloAC modeloAC;
    private VistaAC vistaAC;

    private boolean cargandoClientes = false;
    private boolean cargandoTrabajadores = false;

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
        
        this.modeloAC = modelo;
        this.vistaAC = vista;
        // *******************************************************
        // Pasa a la vistaAC los items del combo de clientes con CCC
        // *******************************************************
        cargandoClientes = true;

        ClienteVO miClienteConCCC;
        List <ClienteVO> listaClientes = modelo.getAllClientesWithCCC();
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
        vistaAC.cargaComboClientes(listaNombresClientes);
        cargandoClientes = false;
        // ****************************************************
        // Pasa a la vistaAC los items del combo de trabajadores.
        // ****************************************************
        cargandoTrabajadores = true;
        PersonaVO miTrabajador;
        List <PersonaVO> listaTrabajadores = modelo.getAllPersonas();
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
        // Pasa a la vistaAC los items del combo de tipos de contratos.
        // *********************************************************
        TipoContratoVO miTipoContrato;
        List <TipoContratoVO> listaTiposContrato = modelo.getAllTiposContratos();
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
        vistaAC.cargaComboTiposContratos(listaNombresTiposContrato);
    }

    public void cambiadoCliente(){
              
        if (cargandoClientes)
            return;
        
        int indexSelected = vistaAC.getComboClienteSelectedIndex();
        
         if(indexSelected == 0)
         {
            vistaAC.comboClienteCCCremoveAllItem();
            vistaAC.setBotonAceptarEnabled(false);
            return;
         }
        
        int idCliente =  listaIDClientes.get(indexSelected -1);
        
        List<ClienteVO> cccEncontrados;
        ClienteVO miCCCVO = null;
        List listaCCC = new Vector();
        
        cccEncontrados = modeloAC.getClienteCCC(idCliente);
        if (cccEncontrados.size() > 0 && cccEncontrados.get(0).getCcc_inss() != null)
        {
            for (int i = 0; i < cccEncontrados.size(); i++){
                
                miCCCVO = cccEncontrados.get(i);
                listaCCC.add(miCCCVO.getCcc_inss());
            }
            
            vistaAC.cargaComboClienteCCC(listaCCC);
        }
        else
        {   
            vistaAC.comboClienteCCCremoveAllItem();
            String mensaje = "No se ha encontrado ningún CCC para este cliente";
            showMessageDialog(null, mensaje,"Errores detectados",WARNING_MESSAGE);

        }
        
        if(vistaAC.getComboTrabajadorSelectedIndex() != 0)
            vistaAC.setBotonAceptarEnabled(true);
    }

    public void cambiadoTrabajador() {

        if (cargandoTrabajadores)
            return;
       
        int indexSelected = vistaAC.getComboTrabajadorSelectedIndex();
        
        if(indexSelected == 0)
        {
            vistaAC.setBotonAceptarEnabled(false);
            limpiarDatosTrabajador();
            return;
        }
        
        int idTrabajador =  listaIDTrabajadores.get(indexSelected -1);

        List<PersonaVO> personaEncontrada;
        PersonaVO miTrabajador;
        
        Funciones funcion = new Funciones();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
        
        personaEncontrada = modeloAC.getPersona(idTrabajador);
        if(personaEncontrada.size() > 0){
            for (int i = 0; i < personaEncontrada.size(); i++){
                miTrabajador = personaEncontrada.get(i);
            
                vistaAC.setEtqNIF(funcion.formatoNIF(miTrabajador.getNifcif()));
                vistaAC.setEtqNASS(miTrabajador.getNumafss());
                if(miTrabajador.getFechanacim() != null)
                    vistaAC.setEtqFechaNacim(formatoFecha.format(miTrabajador.getFechanacim()));
                else
                    vistaAC.setEtqFechaNacim("");
                vistaAC.setEtqEstadoCivil(miTrabajador.getEstciv());
                vistaAC.setEtqNacionalidad(miTrabajador.getNacionalidad());
                StringBuilder direccionCompleta = new StringBuilder();
                if (miTrabajador.getDireccion() == null)
                    vistaAC.setEtqDireccion("");
                else
                {
                    direccionCompleta.append(miTrabajador.getDireccion()).append("  ");
                    direccionCompleta.append(miTrabajador.getCodpostal()).append("  ");
                    direccionCompleta.append(miTrabajador.getLocalidad());                   
                    vistaAC.setEtqDireccion(direccionCompleta.toString());
                }
                EstudiosDAO estudios = new EstudiosDAO();
                ArrayList<EstudiosVO> estudiosVO = new ArrayList<>();
                EstudiosVO misEstudiosVO = new EstudiosVO();
           
                estudiosVO = estudios.readEstudios(Integer.parseInt(miTrabajador.getNivestud().trim()));
                if(estudiosVO.size() > 0){
                    for (i = 0; i < estudiosVO.size(); i++)
                        misEstudiosVO = estudiosVO.get(i);
                    
                    vistaAC.setEtqNivelEstudios(misEstudiosVO.getDescripEstudios());    
                }
                else
                {
                vistaAC.muestraError("No se han encontrado estudios para persona con idpersona = " + idTrabajador);
                }
            }
        }
        else{
            vistaAC.muestraError("No se ha encontrado ninguna persona con idpersona = " + idTrabajador);
        }
        
        if(vistaAC.getComboClienteSelectedIndex() != 0)
             vistaAC.setBotonAceptarEnabled(true);
    }
    
    public void cambiadoTipoContrato(){
        if(vistaAC.getComboTipoContratoSelectedIndex() == 0)
        {
            vistaAC.setTipoContratoOtros("");
            vistaAC.settfTipoContratoOtrosEnabled(false);
        }
        
        if(vistaAC.getComboTipoContratoSelectedItem().toString().equals("Otros tipos"))
            vistaAC.settfTipoContratoOtrosEnabled(true);
        else
        {
            vistaAC.settfTipoContratoOtrosEnabled(false);
            vistaAC.setTipoContratoOtros("");
        } 
    }
    
    public void cambiadoDuracionContrato(){
        if(vistaAC.getComboDuracionContratoSelectedIndex() == 0)
        {
            cambiadoTipoContrato();
            vistaAC.setFechaDesde("");
            vistaAC.setFechaHasta("");
            vistaAC.setDiasContrato("");
        }
        else if (vistaAC.getComboDuracionContratoSelectedItem().toString().equals("Indefinido"))
            vistaAC.settfFechaHastaEnabled(false);
        else
            vistaAC.settfFechaHastaEnabled(true);
    }
    
    public void cambiadoFechaInicioContrato(){
        
        Funciones funcion = new Funciones();
        String sFecha = null;
        if (funcion.validaFechaMascara(vistaAC.getFechaInicioContrato().trim(), "ddMMyyyy") ||
                funcion.validaFechaMascara(vistaAC.getFechaInicioContrato().trim(), "dd-MM-yyyy"))
        {
            if (vistaAC.getFechaInicioContrato().trim().length() == 8)
                {
                    sFecha = vistaAC.getFechaInicioContrato().trim();
                    String sFechaFormated = sFecha.substring(0, 2) + "-" + sFecha.substring(2, 4) + "-" + sFecha.substring(4, 8);
                    vistaAC.setFechaDesde(sFechaFormated);
                } 
        }
        else
        {
           vistaAC.setFechaDesde("");
           vistaAC.setDiasContrato("");
        }
        
        diasDuracionContrato();
    }
    
    public void cambiadoFechaFinContrato(){
        
        Funciones funcion = new Funciones();
        String sFecha = "";
        if (funcion.validaFechaMascara(vistaAC.getFechaFinContrato().trim(), "ddMMyyyy") ||
                funcion.validaFechaMascara(vistaAC.getFechaFinContrato().trim(), "dd-MM-yyyy"))
        {
            if (vistaAC.getFechaFinContrato().trim().length() == 8)
                {
                    sFecha = vistaAC.getFechaFinContrato().trim();
                    String sFechaFormated = sFecha.substring(0, 2) + "-" + sFecha.substring(2, 4) + "-" + sFecha.substring(4, 8);
                    vistaAC.setFechaHasta(sFechaFormated);
                } 
        }
        else
        {
           vistaAC.setFechaHasta("");
           vistaAC.setDiasContrato("");
        }
        
        diasDuracionContrato();
    }
    
    public void diasDuracionContrato(){
        
        if(vistaAC.getFechaInicioContrato().trim().isEmpty() ||
               vistaAC.getFechaFinContrato().trim().isEmpty())
                return;
        
        Funciones funcion = new Funciones();

        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día        

        String sFechaDesde = "";
        String sFechaHasta = "";
        String sFechaUS = "";
        int iFechaDesde = 0;
        int iFechaHasta = 0;

        Calendar dFechaD = new GregorianCalendar(Integer.parseInt(vistaAC.getFechaInicioContrato().substring(6, 10)),
                Integer.parseInt(vistaAC.getFechaInicioContrato().substring(3, 5)) -1,
                Integer.parseInt(vistaAC.getFechaInicioContrato().substring(0, 2))); 
        Calendar dFechaH = new GregorianCalendar(Integer.parseInt(vistaAC.getFechaFinContrato().substring(6, 10)),
                Integer.parseInt(vistaAC.getFechaFinContrato().substring(3, 5)) -1,
                Integer.parseInt(vistaAC.getFechaFinContrato().substring(0, 2))); 
        
        // Comprobamos que FechaHasta >= FechaDesde
        sFechaDesde = vistaAC.getFechaInicioContrato();
        sFechaUS = funcion.formatoFecha_us(sFechaDesde);
        iFechaDesde = Integer.parseInt(sFechaUS.replace("-",""));
        
        sFechaHasta = vistaAC.getFechaFinContrato();
        sFechaUS = funcion.formatoFecha_us(sFechaHasta);
        iFechaHasta = Integer.parseInt(sFechaUS.replace("-",""));
        
        if(iFechaHasta < iFechaDesde)
            vistaAC.setFechaDesde("");
        
        vistaAC.setDiasContrato("[ " +(dFechaH.getTimeInMillis() - dFechaD.getTimeInMillis() + MILLSECS_PER_DAY)/ MILLSECS_PER_DAY + " días ]");
    }
    
    public void verificaHorasSemana(){
        
        NumberFormat fmtHora = new DecimalFormat("#0.00");
        Double dNumHoras = 0D;
        
        try{
            dNumHoras = Double.parseDouble(vistaAC.getHorasSemana());
        }
        catch(Exception e){
            vistaAC.setHorasSemana("");
            return;
        }
        
        if(dNumHoras >= 39D)
            vistaAC.setHorasSemana("");
        else
            vistaAC.setHorasSemana(fmtHora.format(dNumHoras));
    }
    
    public void cambiadoJornada(){
        if(vistaAC.getComboJornadaSelectedIndex() == 0 ||
                vistaAC.getComboJornadaSelectedItem().toString().trim().equals("Jornada completa"))
        {
            vistaAC.setHorasSemana("");
            vistaAC.settfHorasSemanaEnabled(false);
        }
        else
            vistaAC.settfHorasSemanaEnabled(true);
    }
        
    private void limpiarDatosTrabajador(){
        vistaAC.setEtqNIF("");
        vistaAC.setEtqNASS("");
        vistaAC.setEtqFechaNacim("");
        vistaAC.setEtqEstadoCivil("");
        vistaAC.setEtqNacionalidad("");
        vistaAC.setEtqDireccion("");
        vistaAC.setEtqNivelEstudios("");
    }
        
    private boolean comprobarDatosContrato(){
        
        Boolean comprobadoOK = true;
        ComprobarDatosVistaContratos comprobacion = new ComprobarDatosVistaContratos();
        comprobadoOK = comprobacion.ComprobarDatosVistaContratos(vistaAC);
        
        return comprobadoOK;
    }
    
    public void grabarDatosContrato(){
        
        List datosContrato = new ArrayList();
        Funciones funcion = new Funciones();

        int ultimoNumeroContrato = modeloAC.getUltimoNumeroContrato();
        datosContrato.add(ultimoNumeroContrato + 1);
        // Número de variación: cero, al ser contrato inicial
        datosContrato.add(0);
        // Tipo variacion: tipo contrato al ser número de variación = 0
        int indexTipoContratoSelected = vistaAC.getComboTipoContratoSelectedIndex();
        int idTipoContrato =  listaIDTiposContratos.get(indexTipoContratoSelected -1);
        datosContrato.add(idTipoContrato);
        // Idcliente GM        
        datosContrato.add(listaIDClientes.get(vistaAC.getComboClienteSelectedIndex()-1));
        // ClienteGM Nombre
        datosContrato.add(vistaAC.getClienteName());        
        // Cliente CCC
        datosContrato.add(vistaAC.getComboClienteCCCSelectedItem());
        // Id y nombre trabajador
        datosContrato.add(listaIDTrabajadores.get(vistaAC.getComboTrabajadorSelectedIndex()-1));
        datosContrato.add(vistaAC.getTrabajadorName());
        // Categoria
        datosContrato.add(vistaAC.getCategoria());
        // Jornada
        if(vistaAC.getComboJornadaSelectedItem().toString().equals("Jornada completa"))
            datosContrato.add("Jornada completa");
        else
            datosContrato.add(vistaAC.getHorasSemana() + " horas/semana");
        // Jornada, días
        String sDiasSemana = "";
        for (int i = 0; i < vistaAC.getDiasSemana().size(); i++)
            sDiasSemana = sDiasSemana + vistaAC.getDiasSemana().get(i).toString();
        datosContrato.add(sDiasSemana);
        // Jornada, tipo
        if(vistaAC.getComboJornadaSelectedItem().toString().contains("completa"))
            datosContrato.add("Completa"); 
        else
        {
            datosContrato.add("Parcial");
//            emisionRegistroHorario = true;
        }
        // Tipo contrato
        datosContrato.add(vistaAC.getTipoContrato());
        
//        if(vistaAC.getTipoContrato().contains("Formación"))
//            emisionRegistroHorario = true;
        // Fecha inicio contrato
        datosContrato.add(funcion.formatoFecha_us(vistaAC.getFechaInicioContrato()));
        // Fecha fin contrato
        if(vistaAC.getFechaFinContrato().isEmpty())   // Es un contrato Indefinido
            datosContrato.add("null");
        else
            datosContrato.add(funcion.formatoFecha_us(vistaAC.getFechaFinContrato()));
        // Número contrato INEM
        datosContrato.add("Pendiente");
        // En vigor
        datosContrato.add("TRUE");
        // Notas gestor
        datosContrato.add(vistaAC.getAreaGestor());
        String notificacion = "[Notificación cliente: " + vistaAC.getFechaNotificacion() +
                " a las " + vistaAC.getHoraNotificacion() + "]\\n";
        // Notas privadas
        datosContrato.add(notificacion + vistaAC.getAreaPrivada());
        // Duración
        if(vistaAC.getFechaFinContrato().isEmpty())   // Es un contrato Indefinido
            datosContrato.add("I");
        else
            datosContrato.add("T");
        // 
        //  Grabamos el Contrato
        //
        int numcontrato = modeloAC.saveContrato(datosContrato);
        if(numcontrato > 0)
            vistaAC.muestraInfo("El contrato se ha guardado con el número " + numcontrato);
        else {
            vistaAC.muestraError("ERROR: No se ha guardado el contrato");
            return;
        }
        // Grabamos el horario, en su caso
        GrabarHorarioContrato horario = new GrabarHorarioContrato(numcontrato, vistaAC);
        //
        // Imprimimos la Portada Expediente Contrato
        //
        PortadaExpedienteContrato pec = new PortadaExpedienteContrato(vistaAC);
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
                fechaInicioCtto = fechaCompleta.parse(vistaAC.getFechaInicioContrato());
            }
            catch(Exception e){

            }
            String mesRH = nombreMes.format(fechaInicioCtto);
            String annoRH = annoInFecha.format(fechaInicioCtto);
            // Creamos el PDF del Registro Horario
            RegistroHorario reghor = new RegistroHorario(mesRH, annoRH, 
                    vistaAC.getClienteName(),
                    vistaAC.getComboClienteCCCSelectedItem(),vistaAC.getTrabajadorName(),
                    vistaAC.getTrabajadorNIF(), vistaAC.getHorasSemana() + " horas/semana");
            String pathFile = reghor.guardarRegistoHorarioParaPDF();
            reghor.RHtoPDF(pathFile);

            // Preguntamos sobre la impresión del Registro Horario
            int respuesta = JOptionPane.showConfirmDialog(null,
            "Se ha creado el PDF del Registro Horario de " + mesRH + "-" + annoRH + " para "
                    + vistaAC.getTrabajadorName() + " en su carpeta \"Borrame\"\n"
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
        CarpetaA3ControlGestor a3 = new CarpetaA3ControlGestor(vistaAC);

        showMessageDialog(null, "El registro en la base de datos y la emisión de documentación\n"
            + "del nuevo contrato se han realizado correctamente.","Nuevo contrato de trabajo"
                ,INFORMATION_MESSAGE);
    }
    
    public void botonAceptarMouseClicked(){
        if (vistaAC.botonAceptarIsEnabled())
            if(comprobarDatosContrato())
            {
                vistaAC.setBotonAceptarEnabled(false);
                grabarDatosContrato();
            }
    }
}
