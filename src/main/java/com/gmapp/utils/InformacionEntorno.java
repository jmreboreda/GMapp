/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.utilities;

import java.net.URISyntaxException;

/**
 *
 * @author jmrb
 */
public class InformacionEntorno {
    
    String SysOper = System.getProperty("os.name"); 
    String userName = System.getProperty("user.name");    
    String userHome = System.getProperty("user.home"); 
    String fileSeparator = System.getProperty("file.separator");
    String classPath = System.getProperty("java.class.path");
    
    public void InformacionEntorno(){
 
        System.out.println("SysOper: " + SysOper + "\n"
                + "userName: " + userName + "\n"
                + "userHome: " + userHome + "\n"
                + "ClassPath: " + classPath + "\n"
                + "Separator: " + fileSeparator + "\n");
    }

}
