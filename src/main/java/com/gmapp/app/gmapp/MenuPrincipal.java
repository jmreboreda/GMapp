/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.app.gmapp;

//import RegistroHorario.frmImpRegHor;
//import AltaContratos.AltaContratos;
import com.gmapp.app.altacontratos.ModeloAC;
import com.gmapp.app.altacontratos.VistaAC;
import com.gmapp.app.registrohorario.ModeloRH;
import com.gmapp.app.registrohorario.VistaRH;
import com.gmapp.app.variacioncontratos.ModeloVC;
import com.gmapp.app.variacioncontratos.VistaVariacionContrato;
import com.gmapp.comun.LeerPathFromXML;
import com.gmapp.utilidades.BaseDeDatos;
import com.gmapp.utilidades.PictureUtils;
import java.io.File;
import javax.swing.JDialog;
import javax.swing.JFrame;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showMessageDialog;
/**
 *
 * @author jmrb
 */
public class MenuPrincipal extends javax.swing.JFrame {
    
    final String SysOp;

    public int ImpRegHor = 0;
    public Integer iVentanasAbiertas;
    final String classPath;
    final String userName;
    final String userHome;
    
    public MenuPrincipal() {
        
        SysOp = System.getProperty("os.name");
        classPath = System.getProperty("java.class.path");
        userName = System.getProperty("user.name");
        userHome = System.getProperty("user.home"); 
        initComponents();
        setIconImage(PictureUtils.getPicture(PictureUtils.MAIN_ICON));
        gmoldesActualizaEnVigorDB();
        limpiarTemporales();
        iVentanasAbiertas = 0;
    }
    
    
    public javax.swing.JButton getbtImpRegHor(){
       return  btImpRegHor;
    }
    
    public Integer getVentanasAbiertas(){
        return this.iVentanasAbiertas;
    }
    
    public void setVentanasAbiertas(Integer numero){
        this.iVentanasAbiertas = this.iVentanasAbiertas + numero;
    }
    
    private void gmoldesActualizaEnVigorDB(){
        BaseDeDatos gmoldes = new BaseDeDatos();
        // Actualizar contratos en vigor
        String sqlQuery1 = "UPDATE contratoshistorico SET envigor = FALSE where envigor = TRUE and (f_desde > now() or contratoshistorico.f_hasta < now());";
        String sqlQuery2 = "UPDATE contratoshistorico SET envigor = TRUE where envigor = FALSE and (contratoshistorico.f_desde < now() and contratoshistorico.f_hasta > now());";
        try
        {
            gmoldes.estableceConexion();
            int numFilas1 = gmoldes.actualizarDatosTabla(sqlQuery1); 
            int numFilas2 = gmoldes.actualizarDatosTabla(sqlQuery2);  
            System.out.println("Filas contratoshistorico pasadas a envigor(false): " + numFilas1);
            System.out.println("Filas contratoshistorico pasadas a envigor(true) : " + numFilas2);
            gmoldes.cierraConexion();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        //
    }
    
    private void limpiarTemporales(){
        LeerPathFromXML Path = new LeerPathFromXML();
        String myPathToTemp = Path.cargarXml("PathToTemp");
        String dir = "";
        
        if(SysOp.equals("Linux"))
            dir = myPathToTemp;
        else
            dir = userHome + myPathToTemp;
        
        File file;

        System.out.println("Buscando archivos temporales en " + dir);

        File directorio = new File(dir);
        String[] files = directorio.list();
        String nameFile;
        for (String archivo : files) {
//            System.out.println(dir + File.separator + archivo);
             file = new File(dir + File.separator + archivo);
             nameFile = file.getName();
             if (nameFile.startsWith("ODFtk_") ||
                     nameFile.contains("Nuevo_Contrato") ||
                     nameFile.contains("Registro_Horario")){
                 file.delete();
                 System.out.println("File borrado --> " + nameFile);}
        }
        System.out.println("Limpieza de archivos temporales finalizada.");
    }
            
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane3 = new javax.swing.JLayeredPane();
        btImpRegHor = new javax.swing.JButton();
        btNewCtto = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btVariacionCtto = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        btSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Gestoría MOLDES app - Menú principal");
        setResizable(false);

        btImpRegHor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btImpRegHor.setText("Impresión de Registros Horarios");
        btImpRegHor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btImpRegHor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btImpRegHor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btImpRegHorMouseClicked(evt);
            }
        });

        btNewCtto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btNewCtto.setText("Nuevo contrato");
        btNewCtto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btNewCtto.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btNewCtto.setMaximumSize(new java.awt.Dimension(230, 30));
        btNewCtto.setMinimumSize(new java.awt.Dimension(230, 30));
        btNewCtto.setPreferredSize(new java.awt.Dimension(230, 30));
        btNewCtto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btNewCttoMouseClicked(evt);
            }
        });
        btNewCtto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNewCttoActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pic/GMapp_PNG_64x64.png"))); // NOI18N

        btVariacionCtto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btVariacionCtto.setText("Variación en contrato existente");
        btVariacionCtto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btVariacionCtto.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btVariacionCtto.setMaximumSize(new java.awt.Dimension(230, 30));
        btVariacionCtto.setMinimumSize(new java.awt.Dimension(230, 30));
        btVariacionCtto.setPreferredSize(new java.awt.Dimension(230, 30));
        btVariacionCtto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btVariacionCttoMouseClicked(evt);
            }
        });

        jLayeredPane3.setLayer(btImpRegHor, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(btNewCtto, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(btVariacionCtto, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane3Layout = new javax.swing.GroupLayout(jLayeredPane3);
        jLayeredPane3.setLayout(jLayeredPane3Layout);
        jLayeredPane3Layout.setHorizontalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btImpRegHor, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btNewCtto, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btVariacionCtto, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jLayeredPane3Layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jLayeredPane3Layout.setVerticalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(btImpRegHor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btNewCtto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btVariacionCtto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(317, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        btSalir.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btSalir.setForeground(new java.awt.Color(153, 0, 51));
        btSalir.setText("Salir");
        btSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btSalir.setMaximumSize(new java.awt.Dimension(90, 45));
        btSalir.setMinimumSize(new java.awt.Dimension(90, 45));
        btSalir.setPreferredSize(new java.awt.Dimension(90, 45));
        btSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btSalirMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(197, 197, 197)
                .addComponent(btSalir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSalir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btSalirMouseClicked
        if (iVentanasAbiertas == 0)
            System.exit(0);
        else
        {
            showMessageDialog(null, "Hay " + iVentanasAbiertas +  " ventana(s) abierta(s). Ciérrela(s) primero.","Error: Ventanas abiertas",WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btSalirMouseClicked

    private void btNewCttoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNewCttoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btNewCttoActionPerformed

    private void btNewCttoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btNewCttoMouseClicked
        if (btNewCtto.isEnabled())
        {
            btNewCtto.setEnabled(false);
            iVentanasAbiertas = iVentanasAbiertas + 1;
            ModeloAC modeloAC = new ModeloAC();
            VistaAC vista = new VistaAC(modeloAC);
            try{
                JDialog v = new JDialog();
                v.setTitle("Gestoría MOLDES - Alta de Nuevo Contrato de Trabajo");
                v.getContentPane().add(vista);
                v.pack();
                vista.setBotonMenuPrincipal(btNewCtto);
                vista.setVentanasAbiertas(this);
                v.setResizable(false);
                v.setLocationRelativeTo(null);
                //v.setIconImage (new ImageIcon(getClass().getResource("GMapp_PNG_64x64.png")).getImage());
                v.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                v.setVisible(true);
            }catch (Exception e) {

            }
        }
    }//GEN-LAST:event_btNewCttoMouseClicked

    private void btImpRegHorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btImpRegHorMouseClicked
        if (btImpRegHor.isEnabled())
        {
            btImpRegHor.setEnabled(false);
            iVentanasAbiertas = iVentanasAbiertas + 1;
            ModeloRH modeloRH = new ModeloRH();
            VistaRH vistaRH = new VistaRH(modeloRH);
            try{
                JDialog v = new JDialog();
                v.setTitle("Gestoría MOLDES - Impresión de Registros Horarios");
                v.getContentPane().add(vistaRH);
                v.pack();
                vistaRH.setBotonMenuPrincipal(btImpRegHor);
                vistaRH.setVentanasAbiertas(this);
                v.setResizable(false);
                v.setLocationRelativeTo(null);
                //v.setIconImage (new ImageIcon(getClass().getResource("/com/gmapp/miscelanea/GMapp_GIF_64x64.gif")).getImage());
                v.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                v.setVisible(true);
            }catch (Exception e) {

            }
        }
    }//GEN-LAST:event_btImpRegHorMouseClicked

    private void btVariacionCttoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btVariacionCttoMouseClicked
        if (btVariacionCtto.isEnabled())
        {
            btVariacionCtto.setEnabled(false);
            //iVentanasAbiertas = iVentanasAbiertas + 1;
            ModeloVC modeloVC = new ModeloVC();
            VistaVariacionContrato vista = new VistaVariacionContrato(modeloVC);
            try{
                JDialog v = new JDialog();
                v.setTitle("Gestoría MOLDES - Variación de contratos");
                v.getContentPane().add(vista);
                v.pack();
                vista.setBotonMenuPrincipal(btVariacionCtto);
                vista.setVentanasAbiertas(this);
                v.setResizable(false);
                v.setLocationRelativeTo(null);
                //v.setIconImage (new ImageIcon(getClass().getResource("/com/gmapp/miscelanea/GMapp_GIF_64x64.gif")).getImage());
                //v.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                v.setVisible(true);
            }catch (Exception e) {

            }
        }
    }//GEN-LAST:event_btVariacionCttoMouseClicked

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btImpRegHor;
    public static javax.swing.JButton btNewCtto;
    private javax.swing.JButton btSalir;
    public static javax.swing.JButton btVariacionCtto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane3;
    // End of variables declaration//GEN-END:variables
}
