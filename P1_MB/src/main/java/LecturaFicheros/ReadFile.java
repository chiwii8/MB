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
public class ReadFile {

    private final String idRegex = "^\\.I\\s*\\d+";
    private final String titleRegex = "^\\.T\\s*";
    private final String authorRegex = "^\\.A\\s*";
    private final String textRegex = "^\\.W\\s*";
    private final String dataRegex = "^\\.X\\s*";

    /**
     * Lee un fichero del corpus y almacena los datos recopilados en un array
     *
     * @param path es el camino al archivo destino
     * @return devuelve un array con los datos del corpus recopilados en un
     * array
     * @throws java.lang.Exception Fallo al localizar una parte del fichero a
     * parsear
     * @throws java.io.IOException
     * @throws java.io.FileNotFoundException En caso de que el fichero no exista
     * o esté mal configurada la ruta
     */
    public List<DocumentFile> readCorpus(String path) throws Exception, IOException, FileNotFoundException {   ///TO_DO testear el método con el corpus
        List<DocumentFile> Data = new ArrayList<>();
        File file = new File(path);
        int count = 1;
        if (!file.exists()) {
            throw new FileNotFoundException("El fichero que intentas leer no existe.");
        }

        FileReader filereader = new FileReader(path);
        BufferedReader bf = new BufferedReader(filereader);
        String textLine = bf.readLine();

        while (textLine != null) {
            DocumentFile newFichero = new DocumentFile();
            System.out.println("Documento " + count);
            if (textLine.matches(idRegex)) {                          ///ID
                String id = textLine.replaceFirst(idRegex, "");
                newFichero.setId(id);
                textLine = bf.readLine();
            }

            if (textLine.matches(titleRegex)) {                     ///Title
                StringBuilder str = new StringBuilder();
                while (!textLine.matches(authorRegex)) {                      
                    textLine = bf.readLine();
                    str.append(textLine).append("\n");
                }
                newFichero.setTitle(str.toString());
                
            }
  
            if(textLine.matches(authorRegex)){///Entra              ///author
                while(!textLine.matches(textRegex)){
                    textLine = bf.readLine();
                    newFichero.setnewAuthor(textLine);
                }
            }
            
            if (textLine.matches(textRegex)) {
                StringBuilder str = new StringBuilder();
                textLine = bf.readLine();
                while (!textLine.matches(dataRegex)) {
                    str.append(textLine).append("\n");
                    textLine = bf.readLine();
                }
                newFichero.setText(str.toString());
            } else {  ///No se debería de dar nunca
                throw new Exception("Se ha encontrado un fallo al localizar el texto del documento " + count);
            }
           
            textLine = bf.readLine();   // --> saltamos esta linea .X 
            while (textLine != null  && !textLine.matches(idRegex)) {
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
