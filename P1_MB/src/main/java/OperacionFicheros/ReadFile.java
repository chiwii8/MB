/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package OperacionFicheros;

import Modelos.Document;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
    private final String typeinfoRegex = "^\\.K\\s*$";
    private final String valueRegex = "^\\.C\\s*$";
    private final String markRegex = "^\\.\\w?\\s*\\d*\\s*$";
    private final String specialCharacterRegex = "[+\\-\\&|\\(\\)\"\'\\~\\*\\?\\:]";

    /**
     * Lee los ficheros del corpus y las consultas
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
    public List<Document> readDocuments(String path) throws Exception {
        List<Document> documents = new ArrayList<>();

        BufferedReader bf = openDocument(path);
        String textLine = bf.readLine();

        while (textLine != null) {
            Document newDocument = new Document();
            do {

                if (textLine.matches(idRegex)) {
                    newDocument.setIndex(textLine.split("\\s+")[1]);
                    textLine = bf.readLine();
                }

                if (textLine!=null && textLine.matches(titleRegex)) {
                    StringBuilder title = new StringBuilder("");
                    while ((textLine = bf.readLine()) != null && !textLine.matches(markRegex)) {
                        title.append(textLine).append(" ");
                        
                    }
                    newDocument.setTitle(title.toString().strip());
                }

                if (textLine !=null && textLine.matches(authorRegex)) {
                    while ((textLine = bf.readLine()) != null && !textLine.matches(markRegex)) {
                        newDocument.setnewAuthor(textLine);
                    }
                }

                if (textLine!= null && textLine.matches(boleRegex)) {
                    StringBuilder boletin = new StringBuilder("");

                    while ((textLine = bf.readLine()) != null && !textLine.matches(markRegex)) {
                        boletin.append(textLine).append(" ");
                    }
                    newDocument.setBoletin(boletin.toString().strip());
                }

                if (textLine!= null && textLine.matches(textRegex)) {
                    StringBuilder text = new StringBuilder("");
                    while ((textLine = bf.readLine()) != null && !textLine.matches(markRegex)) {
                        text.append(textLine).append(" ");
                    }
                    newDocument.setText(text.toString().strip());
                }

                if (textLine!= null &&textLine.matches(typeinfoRegex)) {
                    StringBuilder info = new StringBuilder("");
                    while ((textLine = bf.readLine()) != null && !textLine.matches(markRegex)) {
                        info.append(textLine).append(" ");
                    }
                    newDocument.setTypeInformation(info.toString().strip());
                }

                if (textLine!= null && textLine.matches(valueRegex)) {
                    StringBuilder value = new StringBuilder("");
                    while ((textLine = bf.readLine()) != null && !textLine.matches(markRegex)) {
                        value.append(textLine).append(" ");
                    }
                    newDocument.setValue(value.toString().strip());
                }

                if (textLine!= null && textLine.matches(dataRegex)) {
                    while ((textLine = bf.readLine()) != null && !textLine.matches(markRegex)) {
                    }
                }

            } while (textLine != null && !textLine.matches(idRegex));

            //System.out.println(newDocument);
            documents.add(newDocument);
        }
        bf.close();
        return documents;
    }

    /**
     * Método que devuelve un fichero abierto y listo para leer
     *
     * @param path Dirección donde se encuentra el fichero
     * @return Devuelve el fichero abierto y listo para leer
     * @throws FileNotFoundException Indica que el fichero no existe en ese
     * directorio
     */
    private BufferedReader openDocument(String path) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException("El fichero que intentas leer no existe.");
        }
        FileReader filereader = new FileReader(path);

        return new BufferedReader(filereader);
    }

}
