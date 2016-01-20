/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.app.registrohorario;

import com.gmapp.app.gmappv2.MenuPrincipal;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jmrb
 */
public class VistaRegistroHorario extends JPanel {

    JButton botonLlamante;
    MenuPrincipal menu;
    private ControladorRH controlador;
    final double[][]mDoubleHorasHorario = new double[8][7];
    private DefaultTableModel tablaContratosModelo;
 
    public VistaRegistroHorario(ModeloRH modelo) {
        initComponents();
        etqCabecera.setText("<html><p ALIGN=center>Impresión de Registros Horarios</p><p> para contratos de trabajo a tiempo parcial y de formación</p></html>");
        controlador = new ControladorRH(modelo, this); 
        
        tablaContratosModelo = (DefaultTableModel) tablaContratos.getModel();
        tablaContratos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD,11));
        tablaContratos.setFont(new Font("Segoe UI", Font.PLAIN, 12)); 
        tablaContratos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    public void cambiadoCliente(){
        controlador.cambiadoCliente();
    }
    
    public void cambiadoMes(){
        controlador.cambiadoMes();        
    }
    
    public int getComboClientesSelectedIndex(){
        return this.comboClientes.getSelectedIndex();
    }
    
    public Object getComboClientesSelectedItem(){
        return this.comboClientes.getSelectedItem();
    }
    
    public Object getComboMesesSelectedItem(){
        return this.comboMeses.getSelectedItem();
    }
    
    public int getComboMesesSelectedIndex(){
        return this.comboMeses.getSelectedIndex();
    }
    
    public Object getTablaContratosValueAt(int row, int column){
        return this.tablaContratos.getValueAt(row, column);
    }
    
    public String getAnno(){
        return this.tfAnnoImp.getText();
    }
    
    public int getTablaContratosSelectedRow(){
        return this.tablaContratos.getSelectedRow();
    }
    
    public void setBotonMenuPrincipal(JButton botonOrigen){
        botonLlamante = botonOrigen;
    }

    public void setBotonImprimirEnabled(Boolean bol){
        this.botonImprimir.setEnabled(bol);
    }
    
    public void setBotonPDFEnabled(Boolean bol){
        this.botonPDF.setEnabled(bol);
    }
    
    public void setVentanasAbiertas(MenuPrincipal menuP){
        this.menu = menuP;
    }
 
    public void comboMesesAddItem(String item) {
        this.comboMeses.addItem(item);
    }    
     
    public void comboClientesAddItem(String item) {
        this.comboClientes.addItem(item);
    }
    
    public void comboMesesSetSelectedIndex(Integer index){
        this.comboMeses.setSelectedIndex(index);
    }
    
    public void setAnno(String anno){
        this.tfAnnoImp.setText(anno);
    }
    
    public void tablaContratosAddRow(Object[] datosRow){
        this.tablaContratosModelo.addRow(datosRow);
    }
    
    public void limpiarTablaContratos(){
        while (tablaContratosModelo.getRowCount() > 0)
            tablaContratosModelo.removeRow(0);
        setBotonImprimirEnabled(false);
        setBotonPDFEnabled(false);
    } 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        comboMeses = new javax.swing.JComboBox<>();
        tfAnnoImp = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        comboClientes = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaContratos = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        botonImprimir = new javax.swing.JButton();
        botonSalir = new javax.swing.JButton();
        botonPDF = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        etqCabecera = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mes y año", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        comboMeses.setBackground(new java.awt.Color(255, 255, 204));
        comboMeses.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        comboMeses.setForeground(new java.awt.Color(0, 0, 204));
        comboMeses.setMaximumRowCount(12);
        comboMeses.setMaximumSize(new java.awt.Dimension(125, 25));
        comboMeses.setMinimumSize(new java.awt.Dimension(125, 25));
        comboMeses.setPreferredSize(new java.awt.Dimension(125, 25));
        comboMeses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMesesActionPerformed(evt);
            }
        });

        tfAnnoImp.setBackground(new java.awt.Color(255, 255, 204));
        tfAnnoImp.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        tfAnnoImp.setForeground(new java.awt.Color(0, 0, 204));
        tfAnnoImp.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfAnnoImp.setText("2016");
        tfAnnoImp.setMaximumSize(new java.awt.Dimension(70, 25));
        tfAnnoImp.setMinimumSize(new java.awt.Dimension(70, 25));
        tfAnnoImp.setPreferredSize(new java.awt.Dimension(70, 25));
        tfAnnoImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfAnnoImpFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboMeses, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tfAnnoImp, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboMeses, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfAnnoImp, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        comboClientes.setBackground(new java.awt.Color(255, 255, 204));
        comboClientes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        comboClientes.setForeground(new java.awt.Color(0, 0, 204));
        comboClientes.setMaximumRowCount(15);
        comboClientes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar cliente ..." }));
        comboClientes.setMaximumSize(new java.awt.Dimension(300, 25));
        comboClientes.setMinimumSize(new java.awt.Dimension(300, 25));
        comboClientes.setPreferredSize(new java.awt.Dimension(300, 25));
        comboClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboClientesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contratos para Registro Horario", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        tablaContratos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Apellidos y nombre", "Jornada", "Tipo jornada", "Tipo contrato", "Fecha desde", "Fecha hasta", "idTrabajador"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaContratos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaContratosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaContratos);
        if (tablaContratos.getColumnModel().getColumnCount() > 0) {
            tablaContratos.getColumnModel().getColumn(0).setMinWidth(0);
            tablaContratos.getColumnModel().getColumn(0).setPreferredWidth(0);
            tablaContratos.getColumnModel().getColumn(0).setMaxWidth(0);
            tablaContratos.getColumnModel().getColumn(1).setMinWidth(230);
            tablaContratos.getColumnModel().getColumn(1).setPreferredWidth(230);
            tablaContratos.getColumnModel().getColumn(1).setMaxWidth(230);
            tablaContratos.getColumnModel().getColumn(3).setMinWidth(80);
            tablaContratos.getColumnModel().getColumn(3).setPreferredWidth(80);
            tablaContratos.getColumnModel().getColumn(3).setMaxWidth(80);
            tablaContratos.getColumnModel().getColumn(5).setMinWidth(75);
            tablaContratos.getColumnModel().getColumn(5).setPreferredWidth(75);
            tablaContratos.getColumnModel().getColumn(5).setMaxWidth(75);
            tablaContratos.getColumnModel().getColumn(6).setMinWidth(75);
            tablaContratos.getColumnModel().getColumn(6).setPreferredWidth(75);
            tablaContratos.getColumnModel().getColumn(6).setMaxWidth(75);
            tablaContratos.getColumnModel().getColumn(7).setMinWidth(0);
            tablaContratos.getColumnModel().getColumn(7).setPreferredWidth(0);
            tablaContratos.getColumnModel().getColumn(7).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 172, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        botonImprimir.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        botonImprimir.setForeground(new java.awt.Color(0, 0, 204));
        botonImprimir.setText("Imprimir");
        botonImprimir.setEnabled(false);
        botonImprimir.setMaximumSize(new java.awt.Dimension(90, 45));
        botonImprimir.setMinimumSize(new java.awt.Dimension(90, 45));
        botonImprimir.setPreferredSize(new java.awt.Dimension(90, 45));
        botonImprimir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonImprimirMouseClicked(evt);
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

        botonPDF.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        botonPDF.setForeground(new java.awt.Color(0, 0, 204));
        botonPDF.setText("Crear PDF");
        botonPDF.setEnabled(false);
        botonPDF.setMaximumSize(new java.awt.Dimension(90, 45));
        botonPDF.setMinimumSize(new java.awt.Dimension(90, 45));
        botonPDF.setPreferredSize(new java.awt.Dimension(90, 45));
        botonPDF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonPDFMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        etqCabecera.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        etqCabecera.setForeground(new java.awt.Color(154, 0, 0));
        etqCabecera.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        etqCabecera.setText("Impresión de Registros Horarios para contratos de trabajo a tiempo parcial y de formación");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pic/GMapp_PNG_64x64.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Gestoría MOLDES");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel4.setText("Félix Soage, 3 - 1º");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Elena González Moldes");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4))
                    .addComponent(jLabel1))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                    .addContainerGap(250, Short.MAX_VALUE)
                    .addComponent(etqCabecera, javax.swing.GroupLayout.PREFERRED_SIZE, 729, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(etqCabecera, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 685, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botonImprimirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonImprimirMouseClicked
        controlador.botonImprimirClicked();
    }//GEN-LAST:event_botonImprimirMouseClicked

    private void botonSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonSalirMouseClicked
        botonLlamante.setEnabled(true);
        menu.setVentanasAbiertas(-1);
        JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(this);
        dialog.dispose();
    }//GEN-LAST:event_botonSalirMouseClicked

    private void botonPDFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonPDFMouseClicked
        controlador.botonPDFclicked();
    }//GEN-LAST:event_botonPDFMouseClicked

    private void comboClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboClientesActionPerformed
        cambiadoCliente();
    }//GEN-LAST:event_comboClientesActionPerformed

    private void tablaContratosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaContratosMouseClicked
        if(tablaContratosModelo.getRowCount() == 0) {
            setBotonImprimirEnabled(false);
            setBotonPDFEnabled(false);
        }
        else{
            for (int i = 0; i < tablaContratosModelo.getRowCount(); i++)
                if(tablaContratos.isRowSelected(i)){
                    setBotonImprimirEnabled(true);
                    setBotonPDFEnabled(true);
                }
        }
    }//GEN-LAST:event_tablaContratosMouseClicked

    private void comboMesesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMesesActionPerformed
        if (comboClientes.getItemCount() > 1) cambiadoMes();
    }//GEN-LAST:event_comboMesesActionPerformed

    private void tfAnnoImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfAnnoImpFocusLost
        if (comboClientes.getItemCount() > 1) cambiadoMes();
    }//GEN-LAST:event_tfAnnoImpFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonImprimir;
    private javax.swing.JButton botonPDF;
    private javax.swing.JButton botonSalir;
    private javax.swing.JComboBox<String> comboClientes;
    private javax.swing.JComboBox<String> comboMeses;
    private javax.swing.JLabel etqCabecera;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaContratos;
    private javax.swing.JTextField tfAnnoImp;
    // End of variables declaration//GEN-END:variables

}
