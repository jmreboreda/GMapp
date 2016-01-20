/*
 * RBJM
 * Aplicación desarrollada por José M. Reboreda Barcia
 * para uso propio en Gestoría MOLDES.
 */
package com.gmapp.utilidades;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author jmrb
 */
public class PictureUtils {
    
    private static String ROOT_PATH = "pic/";
    public static String MAIN_ICON = "GMapp_GIF_64x64.gif";
    
    public static Image getPicture(String picture){
        ImageIcon icon = new ImageIcon(ROOT_PATH + picture);
        return icon.getImage();
    }
}
