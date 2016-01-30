/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gmapp.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jmrb
 */
public class Funciones
{
    public String formatoFecha_es(String fechaUS)
    {
        String fechaFormat = null;

        String diaFecha = fechaUS.substring(8,10);
        String mesFecha = fechaUS.substring(5,7);
        String annoFecha = fechaUS.substring(0,4);

        fechaFormat = diaFecha + "-" + mesFecha + "-" + annoFecha;

        return fechaFormat;
    }

       public String formatoFecha_us(String fechaES)
    {
        String fechaFormat = null;

        String annoFecha = fechaES.substring(6,10);
        String mesFecha = fechaES.substring(3,5);
        String diaFecha = fechaES.substring(0,2);

        fechaFormat = annoFecha + "-" + mesFecha + "-" + diaFecha;

        return fechaFormat;
    }
       
     public String formatoNIF(String dni)
    {
        String nifFormat = null;
        int i;
       
         String letraUnicaInicial[] = {"A","B","C","D","E","F","G","H","J","V"};
         String letraInicialyFinal[] = {"X","Y","Z","N","P","Q","R","S","W"};

         for (i = 0; i < letraUnicaInicial.length - 1; i++)
         {
            if (dni.substring(0, 1).equals(letraUnicaInicial[i]))
            {
                nifFormat = dni.substring(0,1)+"-"+dni.substring(1, dni.length());
                return nifFormat;
            }
         }
          for (i = 0; i < letraInicialyFinal.length - 1; i++)
          {
              if (dni.substring(0, 1).equals(letraInicialyFinal[i]))               
                   if (dni.length() == 9)
                   {
                   // Letra + 7 + letra
                    nifFormat = dni.substring(0,1)+"-"+dni.substring(1,2)+"."+dni.substring(2,5)+"."+dni.substring(5,8)+"-"+dni.substring(8,9);
                    return nifFormat;
                   }
                   else
                   {
                    nifFormat = dni.substring(0,1)+"-"+ dni.substring(1, dni.length()-2) + dni.substring(dni.length()-2,dni.length()-1 );  
                    return nifFormat;
                   }
           }
          // NIF: 8 + letra
            if (dni.length() == 9)
                nifFormat = dni.substring(0,2)+"."+dni.substring(2,5)+"."+dni.substring(5,8)+"-"+dni.substring(8,9);
            else
                nifFormat = dni.substring(0,1)+"."+dni.substring(1,4)+"."+dni.substring(4,7)+"-"+dni.substring(7,8);
         
        return nifFormat;
    }
    
    
    public boolean validaFechaMascara(String sFecha, String sMascara)
    {
        boolean retorno = false;
        try {
            //Convertir la fecha al Calendar
            java.util.Locale locInstancia = new java.util.Locale("es","ES");
            java.text.DateFormat dfInstancia;
          
            java.util.Date dInstancia;
            dfInstancia = new java.text.SimpleDateFormat(sMascara,locInstancia);
            dfInstancia.setLenient(false);
            dInstancia = (java.util.Date)dfInstancia.parse(sFecha);
            java.util.Calendar cal = java.util.Calendar.getInstance(locInstancia);
            cal.setTime(dInstancia); //setear la fecha en cuestion al calendario 
            
            retorno = true;
        } catch (java.text.ParseException excep) {
            retorno = false; //unparseable date
        } finally {
            return retorno;
        }
    }
    
    public int ENA13ControlCodeCalculator(String firstTwelveDigits)
{
     char[] charDigits = firstTwelveDigits.toCharArray();
     int[] ean13 =
     {
        1, 3
     };
     int sum = 0;
     for(int i = 0; i < charDigits.length; i++)
     {
         sum += Character.getNumericValue(charDigits[i]) * ean13[i % 2];
     }
     int checksum = 10 - sum % 10;

     if(checksum == 10)
         checksum = 0;

     return checksum;
}
}

