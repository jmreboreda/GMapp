
package com.gmapp.utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class BaseDeDatos
{
    private String hostRun = "";
    private String user ="";
    private String password = "";

    /** La conexion con la base de datos */
    private Connection conexion = null;

    /** Se establece la conexion con la base de datos */
    public Connection estableceConexion()
    {
        String OSrun = System.getProperty("os.name");

        if (OSrun.equals("Linux"))
        {
            hostRun = "localhost";
            user = "rbjm";
            password = "L100mhdSL";
        }
        else
        {
            hostRun = "192.168.10.200";
            user = "rbjm";
            password = "L100mhdSL";
        }

        if (conexion != null){

            return conexion;}

        try
        {
            DriverManager.registerDriver(new org.postgresql.Driver());
            String dbURL = "jdbc:postgresql://"+ hostRun + "/gmoldesnew";
            String usuario = user;
            String pass = password;
            conexion = DriverManager.getConnection(dbURL, usuario, pass);  

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (conexion != null)
                System.out.println("");
        
        return conexion;
    }

    public ResultSet seleccionarDatosTabla(String tbQuery)
    {
        ResultSet rs = null;
        try
        {
            Statement s = conexion.createStatement();
            rs = s.executeQuery(tbQuery);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return rs;
    }

        public int actualizarDatosTabla(String tbQuery)
    {
        int numUpdateRow = 0;
        try
        {
            Statement s = conexion.createStatement();
            numUpdateRow = s.executeUpdate(tbQuery);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return numUpdateRow;
    }

    public void cierraConexion()
    {
        try
        {
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
