/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gmapp.comun;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;



/**
 *
 * @author jmrb
 */
public class LeerPathFromXML {
    
    final String SysOper = System.getProperty("os.name");
    final String fileSeparator = System.getProperty("file.separator");
    final String classPath = System.getProperty("java.class.path")  + fileSeparator;

    public LeerPathFromXML(){
        
    }
    
    public String cargarXml(String tipoPathBuscado)
    {
        //System.out.println(classPath);
        //System.out.println("Buscando path para \"" + tipoPathBuscado + "\" ...");
        String pathBuscado = "";
        //Se crea un SAXBuilder para poder parsear el archivo
        SAXBuilder builder = new SAXBuilder();
        
        InputStream xmlFile = LeerPathFromXML.class.getResourceAsStream("/xml/pathFiles.xml");
        
        try
        {     
            
            //Se crea el documento a traves del archivo
            Document document = (Document) builder.build(xmlFile);
 
            //Se obtiene la raiz 'Sistemas'
            Element rootNode = document.getRootElement();
 
            //Se obtiene la lista de hijos de la raiz 'Sistemas'
            List list = rootNode.getChildren("Sistema");
 
            //Se recorre la lista de hijos de 'Sistemas'
            for ( int i = 0; i < list.size(); i++ )
            {
                //Se obtiene el elemento 'Sistema'
                Element sistema = (Element) list.get(i);
 
                //Se obtiene el atributo 'nombre' que esta en el tag 'Sistema'
                String nombreSistema = sistema.getAttributeValue("nombre");
                if (SysOper.contains(nombreSistema)){
                    //System.out.println("Sistema: " + nombreSistema );
                    //Se obtiene la lista de hijos del tag 'Sistema~nombre '
                    List lista_registros = sistema.getChildren();
                    //Se recorre la lista de campos
                    for ( int j = 0; j < lista_registros.size(); j++ )
                    {
                        //Se obtiene el elemento 'campo'
                        Element campo = (Element)lista_registros.get( j );
                        //Se obtienen los valores que estan entre los tags '<campo></campo>'
                        //Se obtiene el valor que esta entre los tags '<nombre></nombre>'
                        String nombre = campo.getChildTextTrim("nombrePath");
                        if(nombre.equals(tipoPathBuscado)){
                            //Se obtiene el valor que esta entre los tags '<valor></valor>'
                            pathBuscado = campo.getChildTextTrim("path");
 
                        //System.out.println( "\t"+nombre+"\t"+pathBuscado);
                    }
                }
            }
        }
    }
    catch (IOException io)
    {
            System.out.println(io.getMessage());
    }
        catch (JDOMException jdomex)
        {
            System.out.println(jdomex.getMessage());
        }
    
    return pathBuscado;
    }
    
    
}
