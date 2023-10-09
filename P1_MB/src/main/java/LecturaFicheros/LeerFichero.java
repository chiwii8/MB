/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LecturaFicheros;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author alejandro
 */
public class LeerFichero {
    private final String idRegex = "/^.I\\s*/";
    private final String titleRegex = "/^.T\\s*/";
    private final String authorRegex = "/^.A\\s*/";
    private final String textRegex = "/^.W\\s/";
    private final String dataRegex = "/^.X\\s/";
    /**
     * Lee un fichero del corpus y almacena los datos recopilados en un array
     *
     * @param path es el camino al archivo destino
     * @return devuelve un array con los datos del corpus recopilados en un array
     * @throws java.lang.Exception  En caso de que el fichero no exista
     * @throws java.io.IOException
     * @throws java.io.FileNotFoundException
     */
    public List<DocumentFile> readCorpus(String path) throws Exception,IOException,FileNotFoundException{   ///TO_DO testear el método con el corpus
        List<DocumentFile> Data = new ArrayList<>();
        File file = new File(path);
        int count = 1;
        if(!file.exists()){
            throw new Exception("El fichero que intentas leer no existe.");
        }
        
        FileReader filereader = new FileReader(path);
        BufferedReader bf = new BufferedReader(filereader);
        String textLine = bf.readLine();
        
        while(textLine != null){
            DocumentFile newFichero = new DocumentFile();
          
            if(textLine.matches(idRegex)){
                String id = textLine.replaceFirst(idRegex, "");
                newFichero.setId(id);
                textLine = bf.readLine(); 
            }
            
            if(textLine.matches(titleRegex)){
                String title = bf.readLine();
                newFichero.setTitle(title);
                textLine = bf.readLine();
            }
            
            while(textLine.matches(authorRegex)){
                String Author = bf.readLine();
                newFichero.setnewAuthor(Author);
                textLine = bf.readLine();
            }
            
            if(textLine.matches(textRegex)){
                StringBuilder str = new StringBuilder();    ///TO_DO revisar que se aplica fin de linea, sino añadir append("\n")
                textLine = bf.readLine();
                while(!textLine.matches(dataRegex)){
                    str.append(textLine);
                    textLine = bf.readLine();  
                }
                newFichero.setText(str.toString());
            }else{  ///No se debería de dar nunca
                throw new Exception("Se ha encontrado un fallo al localizar el texto del documento " + count);
            }
            
            textLine = bf.readLine();   // --> saltamos esta linea .X 
            while(!textLine.matches(idRegex)){
                textLine = bf.readLine();
            }
            
            
            count++;
            Data.add(newFichero);
        }
        
        return Data;
    }

    /**
     *
     * @param path Realizar para la versión 0.2
     */
    public void readConsultas(String path) {

    }
}
