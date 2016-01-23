/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.app.gmapp;

import javax.swing.UIManager;

/**
 *
 * @author jmrb
 */
public class GMapp {

 
    /**
     * Crea modelo, vista y visualiza una ventana principal con todo.
     * 
     * @param args
     */
    public static void main(String[] args) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
        
            @Override
            public void run()
            {
                try
                {
//                   UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceOfficeSilver2007LookAndFeel");
                   UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceOfficeSilver2007LookAndFeel");

                }
                catch (Exception e)
                {
                    System.out.println("Look and Feel falló al iniciarse. " + e);
                }
//                JFrame v = new JFrame();
                MenuPrincipal menu = new MenuPrincipal();
                menu.setLocationRelativeTo(null);
                menu.setVisible(true);
            }
        });
    }
}
