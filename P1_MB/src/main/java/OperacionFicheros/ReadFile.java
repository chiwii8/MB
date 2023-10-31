/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package OperacionFicheros;

import Modelos.QuerySolr;
import Modelos.DocumentFileBean;
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

    private final String idRegex = "^\\.I\\s*\\d+$";
    private final String titleRegex = "^\\.T\\s*$";
    private final String authorRegex = "^\\.A\\s*$";
    private final String textRegex = "^\\.W\\s*$";
    private final String dataRegex = "^\\.X\\s*$";
    private final String boleRegex = "^\\.B\\s*$";
    private final String markRegex = "^\\.\\w?\\s*\\d*\\s*$";

    /**
     * Lee un fichero del corpus y almacena los datos recopilados en un array
     *
     * @param path es el camino al archivo destino
     * @return devuelve un array con los datos del corpus recopilados en un
     * array
     *
     * @throws java.lang.Exception Fallo al localizar una parte del fichero a
     * parsear
     *
     * @throws java.io.IOException
     * @throws java.io.FileNotFoundException
     */
    public List<DocumentFileBean> readCorpus(String path) throws Exception, IOException, FileNotFoundException {
        List<DocumentFileBean> Data = new ArrayList<>();
        int count = 1;
        BufferedReader bf = readDocument(path);
        String textLine = bf.readLine();

        while (textLine != null) {
            DocumentFileBean newFichero = new DocumentFileBean();

            if (textLine.matches(idRegex)) {                                  ///ID
                String index = textLine.split(" ")[1];
                newFichero.setIndex(index);
                textLine = bf.readLine();
            }

            if (textLine.matches(titleRegex)) {                               ///Title
                StringBuilder title = new StringBuilder();
                textLine = bf.readLine();                                   ///Saltamos de .T -> al título
                while (!textLine.matches(authorRegex)) {
                    title.append(textLine).append(" ");
                    textLine = bf.readLine();
                }
                newFichero.setTitle(title.toString());
            }

            if (textLine.matches(authorRegex)) {                              ///Authors
                textLine = bf.readLine();
                while (!textLine.matches(textRegex)) {
                    String author = textLine;
                    newFichero.setnewAuthor(author);
                    textLine = bf.readLine();
                }
            }

            if (textLine.matches(textRegex)) {
                textLine = bf.readLine();
                StringBuilder text = new StringBuilder();
                while (!textLine.matches(dataRegex)) {
                    text.append(textLine).append(" ");
                    textLine = bf.readLine();
                }
                newFichero.setText(text.toString());
            }

            if (textLine.matches(dataRegex)) {
                while (textLine != null && !textLine.matches(idRegex)) {
                    textLine = bf.readLine();
                }
            }

            count++;
            Data.add(newFichero);
            //System.out.println(newFichero.toString());
        }
        bf.close();
        return Data;
    }

    /**
     * Version de prueba que nos permite saber si podemos emplear el caso
     * anterior de ReadCorpus
     *
     * @param path Realizar para la versión 0.2
     * @return devuelve una lista con todas las queries que tiene que hacer con
     * su correspondientes datos
     * @throws java.io.FileNotFoundException
     */
    public List<QuerySolr> readConsultas(String path) throws FileNotFoundException, IOException {
        List<QuerySolr> Data = new ArrayList<>();
        
        BufferedReader bf = readDocument(path);
        String textLine = bf.readLine();

        while (textLine != null) {
            QuerySolr newQuery = new QuerySolr();
            if (textLine.matches(idRegex)) {                                  ///ID
                String index = textLine.split(" ")[1];
                newQuery.setIndex(index);
                textLine = bf.readLine();
            }

            if (textLine.matches(titleRegex)) {                               ///Title
                StringBuilder title = new StringBuilder();
                textLine = bf.readLine();                                   ///Saltamos de .T -> al título
                while (!textLine.matches(markRegex)) {
                    title.append(textLine).append(" ");
                    textLine = bf.readLine();
                }
                newQuery.setTitle(title.toString());
            }

            if (textLine.matches(authorRegex)) {                              ///Authors
                textLine = bf.readLine();
                while (!textLine.matches(textRegex)) {
                    String author = textLine;
                    newQuery.setnewAuthor(author);
                    textLine = bf.readLine();
                }
            }

            if (textLine.matches(textRegex)) {                                ///Text
                StringBuilder str = new StringBuilder();
                textLine = bf.readLine();
                while (textLine != null && !textLine.matches(markRegex)) {
                    str.append(textLine);
                    textLine = bf.readLine();
                }
                newQuery.setText(str.toString());
            }

            if (textLine!=null && textLine.matches(boleRegex)) {
                while (textLine != null && !textLine.matches(idRegex)) {
                    textLine = bf.readLine();
                }
            }

            Data.add(newQuery);
        }
        bf.close();
        return Data;
    }

    /**
     * Método que devuelve un fichero abierto y listo para leer
     *
     * @param path Dirección donde se encuentra el fichero
     * @return Devuelve el fichero abierto y listo para leer
     * @throws FileNotFoundException Indica que el fichero no existe en ese
     * directorio
     */
    public BufferedReader readDocument(String path) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException("El fichero que intentas leer no existe.");
        }
        FileReader filereader = new FileReader(path);

        return new BufferedReader(filereader);
    }
}
