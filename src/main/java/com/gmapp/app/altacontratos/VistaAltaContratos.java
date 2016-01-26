/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.app.altacontratos;

import com.gmapp.app.gmapp.MenuPrincipal;
import com.gmapp.utilities.Funciones;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class VistaAltaContratos extends JPanel{
    
    private JButton botonLlamante;
    private MenuPrincipal menu;
    private Integer iVentanasAbiertas = 0;
    private ControladorAltaContratos controlador;
    final double[][]mDoubleHorasHorario = new double[8][7];
    private DefaultTableModel tablaHorarioModelo;
    
    public VistaAltaContratos(ModeloAltaContratos modelo) {

        initComponents();
        CargaFechaHoraHoy();
 //       CargarTablaHorario();
        controlador = new ControladorAltaContratos(modelo, this); 
        controlador.cambiadoTrabajador();
        
        tablaHorarioModelo = (DefaultTableModel) tablaHorario.getModel();
        tablaHorario.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD,11));
        tablaHorario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        for (int iCont = 0; iCont < tablaHorario.getRowCount(); iCont++)
            for (int jCont = 0; jCont < tablaHorario.getColumnCount(); jCont++)
            {
                tablaHorario.setValueAt(" ", iCont, jCont);
                mDoubleHorasHorario [iCont][jCont] = 0.00;  
            }
        
        MiRenderFilaTablaVistaContratos myRenderFilaTabla = new MiRenderFilaTablaVistaContratos();
         for (int i = 0; i < tablaHorario.getColumnCount(); i++)
            tablaHorario.getColumnModel().getColumn(i).setCellRenderer(myRenderFilaTabla);
    }
    
    private void CargaFechaHoraHoy(){
        Date fechaHoy = new Date();
        String sFechaHoy;
        String sHoraAhora;
            
        SimpleDateFormat diaHoy = new SimpleDateFormat("dd");
        SimpleDateFormat mesHoy = new SimpleDateFormat("MM");
        SimpleDateFormat annoHoy = new SimpleDateFormat("yyyy"); 
        SimpleDateFormat horaHoy = new SimpleDateFormat("HH"); 
        SimpleDateFormat minutoHoy = new SimpleDateFormat("mm"); 
        
        sFechaHoy = diaHoy.format(fechaHoy) + "-" + mesHoy.format(fechaHoy) + "-" + annoHoy.format(fechaHoy);
        tfFechaNotif.setText(sFechaHoy);
        
        sHoraAhora = horaHoy.format(fechaHoy) + ":" + minutoHoy.format(fechaHoy);
        tfHoraNotif.setText(sHoraAhora);
    }
    
     private void CargarTablaHorario(){
     tablaHorarioModelo = (DefaultTableModel) tablaHorario.getModel();
     Object[] datosFila = new Object[7]; 
     for (int iCont = 0; iCont < 7; iCont++)
     {
        datosFila[0] = "";
        datosFila[1] = "";
        datosFila[2] = "";
        datosFila[3] = "";
        datosFila[4] = "";
        datosFila[5] = "";
        datosFila[6] = "";
        tablaHorarioModelo.addRow(datosFila);
     } 
  }
    
    private double formatHoraToDouble(String sHora){
      if (sHora.trim().length() != 5)
          return 0.00;
      double hora = Double.parseDouble(sHora.substring(0,2));
      String sMinutos = sHora.substring(3,5); 
      hora = hora + Double.parseDouble(sMinutos)/60.00;
      return hora;
    }
    
    private void formateaTablaHorario()
    {
        Funciones funcion = new Funciones();
        NumberFormat fmtHora = new DecimalFormat("#0.00");
        String sCelda = "";
        int fil = 0;
        int col = 0;
        for (fil = 0; fil < 7; fil++)
        {
            for(col = 0; col < tablaHorario.getColumnCount(); col++)
            {     
                if (col == 1)    
                {
                    if (tablaHorario.getValueAt(fil, col).toString().trim().length() != 10  &&   // dd-MM-yyyy
                         tablaHorario.getValueAt(fil, col).toString().trim().length() != 8 ) //ddMMyyyy
                      tablaHorario.setValueAt("", fil, col);
                    else
                        if(funcion.validaFechaMascara(tablaHorario.getValueAt(fil, col).toString().trim(), "dd-MM-yyyy") ||
                             funcion.validaFechaMascara(tablaHorario.getValueAt(fil, col).toString().trim(), "ddMMyyyy") )
                        {
                            if (tablaHorario.getValueAt(fil, col).toString().trim().length() == 8)
                            {
                                String sFecha = tablaHorario.getValueAt(fil, col).toString().trim();
                                sCelda = sFecha.substring(0, 2) + "-" + sFecha.substring(2, 4) + "-" + sFecha.substring(4, 8);
                                tablaHorario.setValueAt(sCelda, fil, col);
                            }
                            else
                            {
                              // Entrada correcta, ya formateada dd-MM-yyyy
                            }
                        }
                        else
                            tablaHorario.setValueAt("", fil, col);
                }
                else if(col >= 2 && col < 6)
                {
                    if (tablaHorario.getValueAt(fil, col).toString().trim().length() == 5 &
                        tablaHorario.getValueAt(fil, col).toString().contains(":"))
                    {
                        continue;
                    }
                    else if (tablaHorario.getValueAt(fil, col).toString().trim().length() == 4)                   
                    {
                        String sHora =  tablaHorario.getValueAt(fil, col).toString().trim().substring(0, 2);
                        String sMinuto = tablaHorario.getValueAt(fil, col).toString().trim().substring(2, 4);
                        sCelda = sHora + ":" + sMinuto;
                        tablaHorario.setValueAt(sCelda, fil, col);
                        mDoubleHorasHorario[fil][col] = formatHoraToDouble(sCelda);
                    }
                    else
                    {
                        tablaHorario.setValueAt("", fil, col);
                        mDoubleHorasHorario[fil][col] = 0.00;
                    }
                }
                else // col = 6
                {
                    mDoubleHorasHorario[fil][6] = mDoubleHorasHorario[fil][3] - mDoubleHorasHorario[fil][2] +
                    mDoubleHorasHorario[fil][5] - mDoubleHorasHorario[fil][4];
                    sCelda = fmtHora.format(mDoubleHorasHorario[fil][6]) + " ";
                    if (mDoubleHorasHorario[fil][6] == 0)
                        sCelda = "";
                    tablaHorario.setValueAt(sCelda, fil, 6);                                  
                }
            }
        }
    }
    
    public void cargaComboClientes(List<String> listaClientes){
        for (String nombre: listaClientes)
            comboCliente.addItem(nombre);
    }
    
    public void cargaComboTrabajadores(List<String> lista){
        for (Object nombre: lista)
            comboTrabajador.addItem(nombre.toString());
    }
    
    public void cargaComboTiposContratos(List<String> lista){
        for (Object nombre: lista)
            comboTiposContrato.addItem(nombre.toString());
    }
    
    public void cargaDatosPersonalesTrabajador(List<String> lista){

        etqNIF.setText(lista.get(0));
        etqNASS.setText(lista.get(1));
        etqFNacim.setText(lista.get(2));
        etqEstadoCivil.setText(lista.get(3));
        etqNacion.setText(lista.get(4));
        etqDireccion.setText(lista.get(5));
        etqNivEst.setText(lista.get(6));       
    }
    
    public void cargaComboClienteCCC(List<String> lista){
        
        comboClienteCCCremoveAllItem();
        if(lista.size() > 1)
            this.comboClienteCCC.addItem("Seleccionar CCC ...");
        for (Object ccc: lista)
                this.comboClienteCCC.addItem(ccc.toString());
    }
    
    
    public void muestraInfo(String mensaje){
         showMessageDialog(null, mensaje,"Información del programa",INFORMATION_MESSAGE);
    }
    
    public void muestraError(String mensaje){
         showMessageDialog(null, mensaje,"Errores detectados",WARNING_MESSAGE);
    }
    
    public int muestraPregunta(String cabecera, String mensaje){
        int respuesta = JOptionPane.showConfirmDialog(null, mensaje, cabecera,JOptionPane.YES_NO_OPTION);
        
        return respuesta;    
    }
    
    public void setBotonMenuPrincipal(JButton botonOrigen){
        botonLlamante = botonOrigen;
    }    
    
    public void cambiadoCliente(){
        controlador.cambiadoCliente();
    }
    
    public void cambiadoTrabajador(){
        controlador.cambiadoTrabajador();
    }
    
    public void cambiadoTipoContrato(){
        controlador.cambiadoTipoContrato();
    }
    
    public void cambiadoDuracionContrato(){
        controlador.cambiadoDuracionContrato();
    }
    
    public void cambiadoJornada(){
        controlador.cambiadoJornada();
    }
    
    public void cambiadoFechaInicioContrato(){
        controlador.cambiadoFechaInicioContrato();
    }
    
    public void cambiadoFechaFinContrato(){
        controlador.cambiadoFechaFinContrato();
    }
    
    public Boolean hayDatosEnTablaHorario(){
        
        Boolean datosEnTablaHorario = false;
        int camposConDatos = 0;
        
        for(int fil = 0; fil < tablaHorario.getRowCount(); fil++)
        {
            for (int col = 0; col < tablaHorario.getColumnCount(); col++)
            {
                if(!tablaHorario.getValueAt(fil, col).toString().trim().isEmpty())
                   camposConDatos++; 
            }
        }
        if (camposConDatos >= 3)
            datosEnTablaHorario = true;
        
        return datosEnTablaHorario;
    }
    
    public void verificaHorasSemana(){
        controlador.verificaHorasSemana();
    }
    
    public void setVentanasAbiertas(MenuPrincipal menuP){
        this.menu = menuP;
    }
    
    public void comboClienteCCCremoveAllItem(){
        
        this.comboClienteCCC.removeAllItems();
    }
   
    public void setEtqNIF(String nif) {
        etqNIF.setText(nif);
    }
    
    
    public void setEtqNASS(String nass) {
        etqNASS.setText(nass);
    }
    
   
    public void setEtqFechaNacim(String fNacim) {
        etqFNacim.setText(fNacim);
    }
    
   
    public void setEtqEstadoCivil(String estadoCivil) {
        etqEstadoCivil.setText(estadoCivil);
    }
    
   
    public void setEtqNacionalidad(String nacion) {
        etqNacion.setText(nacion);
    }


    public void setEtqDireccion(String direccion) {
        etqDireccion.setText(direccion);
    }
    
   
    public void setEtqNivelEstudios(String nivEst) {
        etqNivEst.setText(nivEst);
    }
        
   
    public void setTipoContratoOtros(String texto){
        tfTipoContratoOtros.setText(texto);
    }
    
   
    public void setFechaDesde(String texto){
        tfFechaDesde.setText(texto);
    }
    
   
    public void setFechaHasta(String texto){
        tfFechaHasta.setText(texto);
    }
    
   
    public void setDiasContrato(String texto){
        etqDuracionContrato.setText(texto);
    }
    
   
    public void setHorasSemana(String texto){
        tfHorasSemana.setText(texto);
    }

    
    public void setEtqStringContrato(String texto){
        etqStringContrato.setText(texto);
    }
    
    
    public void settfFechaHastaEnabled(Boolean  bol){
        tfFechaHasta.setEnabled(bol);
    }

    
    public void setBotonAceptarEnabled(Boolean bol){
        botonAceptar.setEnabled(bol);
    }
    
   
    public void settfTipoContratoOtrosEnabled(Boolean bol){
        tfTipoContratoOtros.setEnabled(bol);
    }
    
    
    public void settfHorasSemanaEnabled(Boolean bol){
        tfHorasSemana.setEnabled(bol);
    }
    
    
    public JComboBox<String> getComboClienteCCC(){
        return comboClienteCCC;
    }
    
    
    public String getFechaNotificacion() {
            return tfFechaNotif.getText();
    }
   
    
    public String getHoraNotificacion() {
        return tfHoraNotif.getText();
    }
    
    
    public JComboBox<String> getComboCliente(){
        return comboCliente;
    }
    
    public String getSelectedClientName(){
        return comboCliente.getSelectedItem().toString();
    }
    
    
    public JComboBox<String> getComboTrabajador(){
        return comboTrabajador;
    }
    
   public String getSelectedEmployeeName(){
        return comboTrabajador.getSelectedItem().toString();
    }

    
    public String  getTrabajadorNIF(){
        return etqNIF.getText();
    }
   
    
    public String getTrabajadorNASS(){
        return etqNASS.getText();
    }
    
    
    public String getTrabajadorFNacim(){
        return etqFNacim.getText();
    }
   
    
    public String getTrabajadorEstadoCivil(){
        return etqEstadoCivil.getText();
    }
    
    
    public String getTrabajadorNacionalidad(){
        return etqNacion.getText();
    }
    
    
    public String getTrabajadorDireccion(){
        return etqDireccion.getText();
    }
    
   
    public String getTrabajadorNivEst(){
        return etqNivEst.getText();
    }
    
    
    public JComboBox<String> getComboTiposContrato(){
        return comboTiposContrato;
    }    

    
    public String getTipoContratoOtros(){
        return tfTipoContratoOtros.getText();
    }
    
   
    public String getEtqDuracionContrato() {
        return etqDuracionContrato.getText();
    }

   
    public JComboBox<String> getComboDuracionContrato() {
        return comboDuracionContrato;
    }    
   
    public String getFechaInicioContrato() {
        return this.tfFechaDesde.getText();
    }

   
    public String getFechaFinContrato() {
       return this.tfFechaHasta.getText();
    }
    
    public JComboBox<String> getComboJornada() {
        return comboJornada;
    }    
   
    public String getHorasSemana(){
        return this.tfHorasSemana.getText();
    }

   
    public List getDiasSemana() {
        
        List lista = new ArrayList();
        
        if (chbLunes.isSelected())
            lista.add("1");
        else
            lista.add("0");
        
        if (chbMartes.isSelected())
            lista.add("1");
       else
            lista.add("0");
        
        if (chbMiercoles.isSelected())
            lista.add("1");
        else
            lista.add("0");
        
        if (chbJueves.isSelected())
            lista.add("1");
        else
            lista.add("0");
        
        if (chbViernes.isSelected())
            lista.add("1");
       else
            lista.add("0");
        
        if (chbSabado.isSelected())
            lista.add("1");
       else
            lista.add("0");
        
        if (chbDomingo.isSelected())
            lista.add("1");
       else
            lista.add("0");

        return lista;
    }
    
    
    public JTable getTablaHorario(){
        return tablaHorario;
    }  
   
    
    public String getAreaGestor(){
        return this.taNotasGestor.getText();
    }
    
       
    public String getAreaPrivada(){
        return this.taNotasPrivadas.getText();
    }

   
    public String getCategoria(){
        return this.taCategoria.getText();
    }

    public JButton getBotonAceptar(){
        return botonAceptar;
    }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        tfFechaNotif = new javax.swing.JTextField();
        tfHoraNotif = new javax.swing.JTextField();
        PanelCliente = new javax.swing.JPanel();
        comboCliente = new javax.swing.JComboBox<>();
        PanelTrabajador = new javax.swing.JPanel();
        comboTrabajador = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        etqEstadoCivil = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        etqFNacim = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        etqNivEst = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        etqNASS = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        etqNacion = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        etqNIF = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        etqDireccion = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        taCategoria = new javax.swing.JTextArea();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taNotasPrivadas = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        comboTiposContrato = new javax.swing.JComboBox<>();
        tfTipoContratoOtros = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        comboJornada = new javax.swing.JComboBox<>();
        tfHorasSemana = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        chbLunes = new javax.swing.JCheckBox();
        chbMartes = new javax.swing.JCheckBox();
        chbMiercoles = new javax.swing.JCheckBox();
        chbJueves = new javax.swing.JCheckBox();
        chbViernes = new javax.swing.JCheckBox();
        chbSabado = new javax.swing.JCheckBox();
        chbDomingo = new javax.swing.JCheckBox();
        jPanel14 = new javax.swing.JPanel();
        comboDuracionContrato = new javax.swing.JComboBox<>();
        tfFechaDesde = new javax.swing.JTextField();
        tfFechaHasta = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        etqDuracionContrato = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        taNotasGestor = new javax.swing.JTextArea();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaHorario = new javax.swing.JTable();
        panelClienteCCC = new javax.swing.JPanel();
        comboClienteCCC = new javax.swing.JComboBox<>();
        jPanel21 = new javax.swing.JPanel();
        etqStringContrato = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        botonAceptar = new javax.swing.JButton();
        botonSalir = new javax.swing.JButton();

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pic/GMapp_PNG_64x64.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Gestoría MOLDES");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Elena González Moldes");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Alta de contratos de trabajo");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel5.setText("Félix Soage, 3 -1º");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fecha y hora notificación", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 13), new java.awt.Color(0, 0, 204))); // NOI18N

        tfFechaNotif.setBackground(new java.awt.Color(255, 255, 204));
        tfFechaNotif.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        tfFechaNotif.setForeground(new java.awt.Color(0, 0, 204));
        tfFechaNotif.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfFechaNotif.setText("24-12-2015");

        tfHoraNotif.setBackground(new java.awt.Color(255, 255, 204));
        tfHoraNotif.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        tfHoraNotif.setForeground(new java.awt.Color(0, 0, 204));
        tfHoraNotif.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfHoraNotif.setText("14:56");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tfFechaNotif, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfHoraNotif)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfFechaNotif, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfHoraNotif, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cliente con CCC disponible", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 13), new java.awt.Color(0, 0, 204))); // NOI18N
        PanelCliente.setForeground(new java.awt.Color(0, 0, 204));

        comboCliente.setBackground(new java.awt.Color(255, 255, 204));
        comboCliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        comboCliente.setForeground(new java.awt.Color(0, 0, 204));
        comboCliente.setMaximumRowCount(15);
        comboCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar cliente ..." }));
        comboCliente.setMaximumSize(new java.awt.Dimension(320, 25));
        comboCliente.setMinimumSize(new java.awt.Dimension(320, 25));
        comboCliente.setPreferredSize(new java.awt.Dimension(320, 25));
        comboCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelClienteLayout = new javax.swing.GroupLayout(PanelCliente);
        PanelCliente.setLayout(PanelClienteLayout);
        PanelClienteLayout.setHorizontalGroup(
            PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelClienteLayout.setVerticalGroup(
            PanelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelTrabajador.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Trabajador", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 13), new java.awt.Color(0, 0, 204))); // NOI18N
        PanelTrabajador.setForeground(new java.awt.Color(0, 0, 204));
        PanelTrabajador.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        comboTrabajador.setBackground(new java.awt.Color(255, 255, 204));
        comboTrabajador.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        comboTrabajador.setForeground(new java.awt.Color(0, 0, 204));
        comboTrabajador.setMaximumRowCount(15);
        comboTrabajador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar trabajador ..." }));
        comboTrabajador.setMaximumSize(new java.awt.Dimension(320, 25));
        comboTrabajador.setMinimumSize(new java.awt.Dimension(320, 25));
        comboTrabajador.setPreferredSize(new java.awt.Dimension(320, 25));
        comboTrabajador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTrabajadorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelTrabajadorLayout = new javax.swing.GroupLayout(PanelTrabajador);
        PanelTrabajador.setLayout(PanelTrabajadorLayout);
        PanelTrabajadorLayout.setHorizontalGroup(
            PanelTrabajadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelTrabajadorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboTrabajador, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelTrabajadorLayout.setVerticalGroup(
            PanelTrabajadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTrabajadorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboTrabajador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos personales del trabajador", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 13), new java.awt.Color(153, 0, 0))); // NOI18N

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Estado civil", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(153, 0, 0))); // NOI18N
        jPanel10.setMaximumSize(new java.awt.Dimension(120, 50));
        jPanel10.setMinimumSize(new java.awt.Dimension(120, 50));
        jPanel10.setPreferredSize(new java.awt.Dimension(120, 50));

        etqEstadoCivil.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etqEstadoCivil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etqEstadoCivil.setText("#Separado Jud#");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etqEstadoCivil, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etqEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "F. Nacimiento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(153, 0, 0))); // NOI18N
        jPanel9.setMaximumSize(new java.awt.Dimension(100, 50));
        jPanel9.setMinimumSize(new java.awt.Dimension(100, 50));
        jPanel9.setPreferredSize(new java.awt.Dimension(100, 50));

        etqFNacim.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etqFNacim.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etqFNacim.setText("#19-06-1957#");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etqFNacim, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etqFNacim, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Estudios completados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(153, 0, 0))); // NOI18N

        etqNivEst.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etqNivEst.setText("Licenciatura universitaria");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etqNivEst, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(etqNivEst, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "NASS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(153, 0, 0))); // NOI18N
        jPanel8.setMaximumSize(new java.awt.Dimension(120, 50));
        jPanel8.setMinimumSize(new java.awt.Dimension(120, 50));
        jPanel8.setPreferredSize(new java.awt.Dimension(120, 50));

        etqNASS.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etqNASS.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etqNASS.setText("123456789012");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etqNASS, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etqNASS, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nacionalidad", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(153, 0, 0))); // NOI18N
        jPanel11.setMaximumSize(new java.awt.Dimension(120, 50));
        jPanel11.setMinimumSize(new java.awt.Dimension(120, 50));
        jPanel11.setPreferredSize(new java.awt.Dimension(120, 50));

        etqNacion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etqNacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etqNacion.setText("#Española#");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etqNacion, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                .addGap(33, 33, 33))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etqNacion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "NIF", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(153, 0, 0))); // NOI18N
        jPanel7.setMaximumSize(new java.awt.Dimension(120, 50));
        jPanel7.setMinimumSize(new java.awt.Dimension(120, 50));
        jPanel7.setPreferredSize(new java.awt.Dimension(120, 50));

        etqNIF.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etqNIF.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etqNIF.setText("#X-36.019.653-C#");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etqNIF, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etqNIF, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dirección completa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(153, 0, 0))); // NOI18N

        etqDireccion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etqDireccion.setText("Avenida de Bueu, 32 - 3º A (Edificio Alejandría)  36940  Cangas");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etqDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(etqDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del contrato", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Categoría", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        taCategoria.setBackground(new java.awt.Color(255, 255, 204));
        taCategoria.setColumns(16);
        taCategoria.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        taCategoria.setForeground(new java.awt.Color(0, 0, 204));
        taCategoria.setLineWrap(true);
        taCategoria.setRows(5);
        taCategoria.setMaximumSize(new java.awt.Dimension(100, 16));
        taCategoria.setPreferredSize(new java.awt.Dimension(100, 16));
        jScrollPane4.setViewportView(taCategoria);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Notas privadas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        taNotasPrivadas.setBackground(new java.awt.Color(255, 255, 204));
        taNotasPrivadas.setColumns(20);
        taNotasPrivadas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        taNotasPrivadas.setForeground(new java.awt.Color(0, 0, 204));
        taNotasPrivadas.setRows(5);
        jScrollPane2.setViewportView(taNotasPrivadas);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tipo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        comboTiposContrato.setBackground(new java.awt.Color(255, 255, 204));
        comboTiposContrato.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        comboTiposContrato.setForeground(new java.awt.Color(0, 0, 204));
        comboTiposContrato.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar tipo de contrato ..." }));
        comboTiposContrato.setMaximumSize(new java.awt.Dimension(288, 25));
        comboTiposContrato.setMinimumSize(new java.awt.Dimension(288, 25));
        comboTiposContrato.setPreferredSize(new java.awt.Dimension(288, 25));
        comboTiposContrato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTiposContratoActionPerformed(evt);
            }
        });

        tfTipoContratoOtros.setBackground(new java.awt.Color(255, 255, 204));
        tfTipoContratoOtros.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tfTipoContratoOtros.setForeground(new java.awt.Color(0, 0, 204));
        tfTipoContratoOtros.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tfTipoContratoOtros.setEnabled(false);
        tfTipoContratoOtros.setMaximumSize(new java.awt.Dimension(263, 25));
        tfTipoContratoOtros.setMinimumSize(new java.awt.Dimension(263, 25));
        tfTipoContratoOtros.setPreferredSize(new java.awt.Dimension(263, 25));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboTiposContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tfTipoContratoOtros, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboTiposContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTipoContratoOtros, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Jornada", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        comboJornada.setBackground(new java.awt.Color(255, 255, 204));
        comboJornada.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        comboJornada.setForeground(new java.awt.Color(0, 0, 204));
        comboJornada.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar jornada ...", "Jornada completa", "Tiempo parcial" }));
        comboJornada.setMaximumSize(new java.awt.Dimension(148, 25));
        comboJornada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboJornadaActionPerformed(evt);
            }
        });

        tfHorasSemana.setBackground(new java.awt.Color(255, 255, 204));
        tfHorasSemana.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        tfHorasSemana.setForeground(new java.awt.Color(0, 0, 204));
        tfHorasSemana.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfHorasSemana.setEnabled(false);
        tfHorasSemana.setMaximumSize(new java.awt.Dimension(56, 25));
        tfHorasSemana.setMinimumSize(new java.awt.Dimension(56, 25));
        tfHorasSemana.setPreferredSize(new java.awt.Dimension(56, 25));
        tfHorasSemana.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfHorasSemanaFocusLost(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Horas/semana");

        chbLunes.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        chbLunes.setText("lun");

        chbMartes.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        chbMartes.setText("mar");

        chbMiercoles.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        chbMiercoles.setText("mié");

        chbJueves.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        chbJueves.setText("jue");

        chbViernes.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        chbViernes.setText("vie");

        chbSabado.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        chbSabado.setText("sáb");

        chbDomingo.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        chbDomingo.setText("dom");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(chbLunes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chbMartes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chbMiercoles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chbJueves)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chbViernes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chbSabado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chbDomingo)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(chbLunes)
                .addComponent(chbMartes)
                .addComponent(chbMiercoles)
                .addComponent(chbJueves)
                .addComponent(chbViernes)
                .addComponent(chbSabado)
                .addComponent(chbDomingo))
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(comboJornada, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfHorasSemana, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfHorasSemana, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(comboJornada, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Duración", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        comboDuracionContrato.setBackground(new java.awt.Color(255, 255, 204));
        comboDuracionContrato.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        comboDuracionContrato.setForeground(new java.awt.Color(0, 0, 204));
        comboDuracionContrato.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar duración del contrato ...", "Indefinido", "Temporal" }));
        comboDuracionContrato.setMaximumSize(new java.awt.Dimension(226, 25));
        comboDuracionContrato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDuracionContratoActionPerformed(evt);
            }
        });

        tfFechaDesde.setBackground(new java.awt.Color(255, 255, 204));
        tfFechaDesde.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        tfFechaDesde.setForeground(new java.awt.Color(0, 0, 204));
        tfFechaDesde.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfFechaDesde.setMaximumSize(new java.awt.Dimension(80, 25));
        tfFechaDesde.setMinimumSize(new java.awt.Dimension(80, 25));
        tfFechaDesde.setPreferredSize(new java.awt.Dimension(80, 25));
        tfFechaDesde.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfFechaDesdeFocusLost(evt);
            }
        });

        tfFechaHasta.setBackground(new java.awt.Color(255, 255, 204));
        tfFechaHasta.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        tfFechaHasta.setForeground(new java.awt.Color(0, 0, 204));
        tfFechaHasta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfFechaHasta.setMaximumSize(new java.awt.Dimension(80, 25));
        tfFechaHasta.setMinimumSize(new java.awt.Dimension(80, 25));
        tfFechaHasta.setPreferredSize(new java.awt.Dimension(80, 25));
        tfFechaHasta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfFechaHastaFocusLost(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Desde ");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Hasta");

        etqDuracionContrato.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        etqDuracionContrato.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboDuracionContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel13)
                .addGap(6, 6, 6)
                .addComponent(tfFechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(etqDuracionContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboDuracionContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfFechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel13)
                    .addComponent(etqDuracionContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel23Layout.createSequentialGroup()
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Notas para el gestor laboral", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        taNotasGestor.setBackground(new java.awt.Color(255, 255, 204));
        taNotasGestor.setColumns(20);
        taNotasGestor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        taNotasGestor.setForeground(new java.awt.Color(0, 0, 204));
        taNotasGestor.setRows(5);
        jScrollPane3.setViewportView(taNotasGestor);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Períodos, jornadas y horarios", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        tablaHorario.setForeground(new java.awt.Color(0, 0, 204));
        tablaHorario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "DiaSemana", "Fecha", "AM De:", "AM A:", "PM De:", "PM A:", "Horas"
            }
        ));
        tablaHorario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tablaHorarioKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(tablaHorario);
        if (tablaHorario.getColumnModel().getColumnCount() > 0) {
            tablaHorario.getColumnModel().getColumn(0).setResizable(false);
            tablaHorario.getColumnModel().getColumn(1).setResizable(false);
            tablaHorario.getColumnModel().getColumn(1).setPreferredWidth(100);
            tablaHorario.getColumnModel().getColumn(2).setResizable(false);
            tablaHorario.getColumnModel().getColumn(3).setResizable(false);
            tablaHorario.getColumnModel().getColumn(4).setResizable(false);
            tablaHorario.getColumnModel().getColumn(5).setResizable(false);
            tablaHorario.getColumnModel().getColumn(6).setResizable(false);
        }

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        panelClienteCCC.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Código Cuenta Cotización", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 13), new java.awt.Color(0, 0, 204))); // NOI18N
        panelClienteCCC.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        panelClienteCCC.setPreferredSize(new java.awt.Dimension(213, 74));

        comboClienteCCC.setBackground(new java.awt.Color(255, 255, 204));
        comboClienteCCC.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        comboClienteCCC.setForeground(new java.awt.Color(0, 0, 204));
        comboClienteCCC.setToolTipText("");
        comboClienteCCC.setMaximumSize(new java.awt.Dimension(150, 25));
        comboClienteCCC.setMinimumSize(new java.awt.Dimension(150, 25));
        comboClienteCCC.setPreferredSize(new java.awt.Dimension(150, 25));

        javax.swing.GroupLayout panelClienteCCCLayout = new javax.swing.GroupLayout(panelClienteCCC);
        panelClienteCCC.setLayout(panelClienteCCCLayout);
        panelClienteCCCLayout.setHorizontalGroup(
            panelClienteCCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClienteCCCLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(comboClienteCCC, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        panelClienteCCCLayout.setVerticalGroup(
            panelClienteCCCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClienteCCCLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboClienteCCC, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelClienteCCC, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(PanelCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PanelTrabajador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PanelCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelClienteCCC, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                            .addComponent(PanelTrabajador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etqStringContrato)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(etqStringContrato)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        botonAceptar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        botonAceptar.setForeground(new java.awt.Color(51, 153, 0));
        botonAceptar.setText("Aceptar");
        botonAceptar.setEnabled(false);
        botonAceptar.setMaximumSize(new java.awt.Dimension(90, 45));
        botonAceptar.setMinimumSize(new java.awt.Dimension(90, 45));
        botonAceptar.setPreferredSize(new java.awt.Dimension(90, 45));
        botonAceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonAceptarMouseClicked(evt);
            }
        });

        botonSalir.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        botonSalir.setForeground(new java.awt.Color(154, 0, 0));
        botonSalir.setText("Salir");
        botonSalir.setMaximumSize(new java.awt.Dimension(90, 45));
        botonSalir.setMinimumSize(new java.awt.Dimension(90, 45));
        botonSalir.setPreferredSize(new java.awt.Dimension(90, 45));
        botonSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonSalirMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(botonAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void comboTrabajadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTrabajadorActionPerformed
        cambiadoTrabajador();
    }//GEN-LAST:event_comboTrabajadorActionPerformed

    private void botonSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonSalirMouseClicked
        botonLlamante.setEnabled(true);
        menu.setVentanasAbiertas(-1);
        JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(this);
        dialog.dispose();
    }//GEN-LAST:event_botonSalirMouseClicked

    private void comboClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboClienteActionPerformed
        cambiadoCliente();
    }//GEN-LAST:event_comboClienteActionPerformed

    private void botonAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonAceptarMouseClicked
        controlador.botonAceptarMouseClicked();
    }//GEN-LAST:event_botonAceptarMouseClicked

    private void tablaHorarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaHorarioKeyTyped
        formateaTablaHorario();
    }//GEN-LAST:event_tablaHorarioKeyTyped

    private void comboTiposContratoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTiposContratoActionPerformed
        cambiadoTipoContrato();
    }//GEN-LAST:event_comboTiposContratoActionPerformed

    private void comboDuracionContratoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDuracionContratoActionPerformed
        cambiadoDuracionContrato();
    }//GEN-LAST:event_comboDuracionContratoActionPerformed

    private void comboJornadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboJornadaActionPerformed
        cambiadoJornada();
    }//GEN-LAST:event_comboJornadaActionPerformed

    private void tfFechaDesdeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfFechaDesdeFocusLost
        cambiadoFechaInicioContrato();
    }//GEN-LAST:event_tfFechaDesdeFocusLost

    private void tfFechaHastaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfFechaHastaFocusLost
        cambiadoFechaFinContrato();
    }//GEN-LAST:event_tfFechaHastaFocusLost

    private void tfHorasSemanaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfHorasSemanaFocusLost
        if (!this.tfHorasSemana.getText().isEmpty())
            verificaHorasSemana();
    }//GEN-LAST:event_tfHorasSemanaFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelCliente;
    private javax.swing.JPanel PanelTrabajador;
    private javax.swing.JButton botonAceptar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JCheckBox chbDomingo;
    private javax.swing.JCheckBox chbJueves;
    private javax.swing.JCheckBox chbLunes;
    private javax.swing.JCheckBox chbMartes;
    private javax.swing.JCheckBox chbMiercoles;
    private javax.swing.JCheckBox chbSabado;
    private javax.swing.JCheckBox chbViernes;
    private javax.swing.JComboBox<String> comboCliente;
    private javax.swing.JComboBox<String> comboClienteCCC;
    private javax.swing.JComboBox<String> comboDuracionContrato;
    private javax.swing.JComboBox<String> comboJornada;
    private javax.swing.JComboBox<String> comboTiposContrato;
    private javax.swing.JComboBox<String> comboTrabajador;
    private javax.swing.JLabel etqDireccion;
    private javax.swing.JLabel etqDuracionContrato;
    private javax.swing.JLabel etqEstadoCivil;
    private javax.swing.JLabel etqFNacim;
    private javax.swing.JLabel etqNASS;
    private javax.swing.JLabel etqNIF;
    private javax.swing.JLabel etqNacion;
    private javax.swing.JLabel etqNivEst;
    private javax.swing.JLabel etqStringContrato;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPanel panelClienteCCC;
    private javax.swing.JTextArea taCategoria;
    private javax.swing.JTextArea taNotasGestor;
    private javax.swing.JTextArea taNotasPrivadas;
    private javax.swing.JTable tablaHorario;
    private javax.swing.JTextField tfFechaDesde;
    private javax.swing.JTextField tfFechaHasta;
    private javax.swing.JTextField tfFechaNotif;
    private javax.swing.JTextField tfHoraNotif;
    private javax.swing.JTextField tfHorasSemana;
    private javax.swing.JTextField tfTipoContratoOtros;
    // End of variables declaration//GEN-END:variables

class MiRenderFilaTablaVistaContratos extends DefaultTableCellRenderer
{
   
    public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {      
        java.awt.Color colorGrisFondoClaro = new java.awt.Color(245,245,245);
        java.awt.Color colorGrisFondoOscuro = new java.awt.Color(235,235,235);
        java.awt.Color miNaranja = new java.awt.Color(255, 240, 152);
        java.awt.Color miVerde = new java.awt.Color(0,140,0);
        java.awt.Color miRojo = new java.awt.Color(204,0,0);
        java.awt.Color miAzul = new java.awt.Color(0,0,204);
        java.awt.Color miLila = new java.awt.Color(125,125,255);
        java.awt.Color colorGrisFuenteLetra = new java.awt.Color(76,83,92);
        java.awt.Component celda = super.getTableCellRendererComponent (table, value, isSelected, hasFocus, row, column);
        
        if (column > 0 && column <= 5)
        { 
            this.setHorizontalAlignment(SwingConstants.CENTER); 
        }
        else if (column == 6)
             this.setHorizontalAlignment(SwingConstants.RIGHT); 
        else 
            this.setHorizontalAlignment(SwingConstants.LEFT);
        
//        if (table.getValueAt(row,5).toString().equals("@"))
//                    sCelda.setForeground(miVerde);   
       if (row < table.getRowCount())
       {
            if (row % 2 == 0)
                celda.setBackground(colorGrisFondoClaro);
            else celda.setBackground(colorGrisFondoOscuro);
       }
       
       if (table.getValueAt(row,6).toString().contains("-"))
                celda.setForeground(Color.RED);   
       else
                celda.setForeground(Color.BLUE);
       
       if (isSelected)
       {
            celda.setBackground(miNaranja);  
       }
       
       celda.setFont(new Font("Segoe UI", Font.PLAIN, 13));

       return celda;
    }
}

}
