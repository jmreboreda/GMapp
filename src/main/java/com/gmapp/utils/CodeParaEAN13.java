/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.utilities;

/**
 *
 * @author jmrb
 */
public class CodeParaEAN13 {
    
    public String codeEAN13 = "";
    public int iFirst = 0;
    public Boolean tableA;
    
    public String CodeParaEAN13(String treceDigitos){
    
        codeEAN13 = treceDigitos.substring(0,1) + (char)(65 + Integer.parseInt(treceDigitos.substring(1,2)));
        iFirst = Integer.parseInt(treceDigitos.substring(0,1));
        for (int i = 3 ; i <= 7; i++)
        {
            tableA = false;
            switch(i)
            {
                case 3:
                    switch(iFirst)
                    {
                        case 0: case 1: case 2: case 3:
                            tableA = true;
                            break;
                    }
                    break;
                case 4:
                {
                    switch(iFirst)
                    {
                        case 0: case 4: case 7: case 8:
                            tableA = true;
                            break;
                    }
                    break;
                }
                case 5:
                {
                    switch(iFirst)
                    {
                        case 0: case 1: case 4: case 5: case 9:
                        {
                            tableA = true;
                            break;
                        }
                    }
                    break;
                }
                case 6:
                {
                    switch(iFirst)
                    {
                        case 0: case 2: case 5: case 6: case 7:
                            tableA = true;
                            break;
                    }
                    break;
                }
                case 7:
                {
                   switch(iFirst)
                    {
                        case 0: case 3: case 6: case 8: case 9:
                            tableA = true;
                            break;
                    }
                   break;
                }
            }
            
            if(tableA)
                codeEAN13 = codeEAN13 + (char)(65 + Integer.parseInt(treceDigitos.substring(i-1, i)));
            else
                codeEAN13 = codeEAN13 + (char)(75 + Integer.parseInt(treceDigitos.substring(i-1,i)));
        }
        
        codeEAN13 = codeEAN13 + "*";
        
        for (int i = 8; i <= 13; i++)
        {
            codeEAN13 = codeEAN13 + (char)(97 + Integer.parseInt(treceDigitos.substring(i-1,i)));
        }
        codeEAN13 = codeEAN13 + "+";
        
        return codeEAN13;
    }
}


