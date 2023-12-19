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
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Esta clase se encarga de la lectura de ficheros
 *
 * @author alejandro
 */
public class ReadFile {

    private final String idRegex = "^\\.I\\s*\\d+$";
    private final String titleRegex = "^\\.T\\s*$";
    private final String authorRegex = "^\\.A\\s*$";
    private final String textRegex = "^\\.W\\s*$";
    private final String boleRegex = "^\\.B\\s*$";
    private final String markRegex = "^\\.\\w?\\s*\\d*\\s*$";
    private final String noRelevantRegex = "^\\.[^ITAWB]?\\s*\\d*\\s*$";
    private final String relevantRegex = "^\\.[ITAWB]?\\s*\\d*\\s*$";
    private final static HashMap<String, String> unExcapedHTMLCharacter;

    static {
        unExcapedHTMLCharacter = new HashMap<>();
        unExcapedHTMLCharacter.put("&lt;", "<");
        unExcapedHTMLCharacter.put("&gt;", ">");
        unExcapedHTMLCharacter.put("&amp;", "&");
        unExcapedHTMLCharacter.put("&quot;", "\"");
        unExcapedHTMLCharacter.put("&apos;", "\'");
        unExcapedHTMLCharacter.put("&nbsp;", " ");
    }

    /**
     * Lee los ficheros del corpus y las consultas
     *
     * @param path es el camino al archivo destino
     * @return devuelve un array con los datos del corpus recopilados en un
     * array
     *
     * @throws java.io.IOException
     * @throws java.io.FileNotFoundException
     */
    public List<Document> readDocuments(String path) throws FileNotFoundException, IOException {
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

                if (textLine != null && textLine.matches(titleRegex)) {
                    StringBuilder title = new StringBuilder("");
                    while ((textLine = bf.readLine()) != null && !textLine.matches(markRegex)) {
                        title.append(textLine).append(" ");

                    }
                    newDocument.setTitle(title.toString().strip());
                }

                if (textLine != null && textLine.matches(authorRegex)) {
                    while ((textLine = bf.readLine()) != null && !textLine.matches(markRegex)) {
                        String parsePerson = parseXMLtoString(textLine);
                        newDocument.setnewAuthor(parsePerson);
                        newDocument.setnewPerson(parsePerson);
                    }
                }

                if (textLine != null && textLine.matches(boleRegex)) {
                    StringBuilder boletin = new StringBuilder("");

                    while ((textLine = bf.readLine()) != null && !textLine.matches(markRegex)) {
                        boletin.append(textLine).append(" ");
                    }
                    newDocument.setBoletin(boletin.toString().strip());
                }

                if (textLine != null && textLine.matches(textRegex)) {
                    StringBuilder text = new StringBuilder("");
                    while ((textLine = bf.readLine()) != null && !textLine.matches(markRegex)) {
                        text.append(textLine).append(" ");
                    }
                    newDocument.setText(text.toString().strip());
                }

                if (textLine != null && textLine.matches(noRelevantRegex)) {
                    while ((textLine = bf.readLine()) != null && !textLine.matches(relevantRegex)) {
                    }
                }

            } while (textLine != null && !textLine.matches(idRegex));

            Document parseDocument = parseXMLDocumentToBean(newDocument);
            System.out.println(parseDocument.toString());
            documents.add(parseDocument);
        }
        bf.close();

        ///Procesamos los documentos en un formato válido
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
        System.out.println(path);
        if (!file.exists()) {
            throw new FileNotFoundException("El fichero que intentas leer no existe.");
        }
        FileReader filereader = new FileReader(path);

        return new BufferedReader(filereader);
    }

    /**
     * Nos permite parsear el documento semi-XML a un bean, realizando una
     * extracción de las etiquetas XML. La información recopilada está dirigida
     * por las etiquetas del documento
     *
     * @param parseDocument documento a parsear
     * @return devuelve un nuevo documento con sus atributos parseados
     */
    private Document parseXMLDocumentToBean(Document parseDocument) {
        ///Mapa que almacena lista de datos
        Map<String, Set<String>> docData = new HashMap<>();
        Document newDocument = new Document();

        ///El indice se mantiene igual
        newDocument.setIndex(parseDocument.getIndex());

        ///Array con los campos del documento
        ///Equivalen a la key del map
        String[] keyData = Document.getDataFields();

        ///Parseamos el texto primero ya que tanto las queries como el corpus lo tienen con total seguridad
        String text = parseDocument.getText();
        if (text != null) {
            Map<String, Set<String>> Aux_text = dataExtractFromXML(text);
            for (String key : keyData) {
                Set<String> set_aux = Aux_text.get(key);
                docData.put(key, set_aux);
            }
            String parsetext = parseXMLtoString(text);
            newDocument.setText(parsetext);
        }

        ///Parseamos el título
        String title = parseDocument.getTitle();
        if (title != null) {
            Map<String, Set<String>> Aux_title = dataExtractFromXML(title);
            for (String key : keyData) {
                ///Conjunto recolectado y conjunto actual si no vacío
                Set<String> set_aux = Aux_title.get(key);
                Set<String> set_actual = docData.get(key);
                if (set_actual != null && !set_actual.isEmpty()) {
                    set_actual.addAll(set_aux);
                    docData.put(key, set_actual);
                } else {
                    docData.put(key, set_aux);
                }
                docData.put(key, set_aux);
            }
            String parsedTitle = parseXMLtoString(title);
            newDocument.setTitle(parsedTitle);
        }

        ///TODO revisar el resultado
        ///Parseamos el boletín si existe
        String boletin = parseDocument.getBoletin();
        if (boletin != null) {
            Map<String, Set<String>> Aux_boletin = dataExtractFromXML(boletin);
            for (String key : keyData) {
                ///Conjunto recolectado y conjunto actual si no vacío
                Set<String> set_aux = Aux_boletin.get(key);
                Set<String> set_actual = docData.get(key);
                if (set_actual != null && !set_actual.isEmpty()) {
                    set_actual.addAll(set_aux);
                    docData.put(key, set_actual);
                } else {
                    docData.put(key, set_aux);
                }
                docData.put(key, set_aux);
            }
            ///No lo pasamos el boletin porque no lo indexamos
            //String parsedBoletin = parseXMLtoString(boletin);
            //newDocument.setBoletin(parsedBoletin);
            
        }

        ///No se requiere parsear las personas,o mejor dicho los autores
        ///Se parsean en el momento que son leídos
        newDocument.setAuthors(parseDocument.getAuthors());
        newDocument.addAllPersons(parseDocument.getPersons());

        ///Cargamos los datos en el nuevo documento
        newDocument.addAllPersons(docData.get(keyData[0]));
        newDocument.addAllOrganitations(docData.get(keyData[1]));
        newDocument.addAllLocations(docData.get(keyData[2]));
        newDocument.addAllDates(docData.get(keyData[3]));

        return newDocument;
    }

    /**
     * Nos permite extraer la informacion del string en XML, La información
     * extraida es suministrada por los documentos
     *
     * @param XMLString String del que extrae la información
     * @return Mapa con la información relevante asociado a su nombre
     */
    private Map<String, Set<String>> dataExtractFromXML(String XMLString) {
        Map<String, Set<String>> result = new HashMap<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parsear el texto XML
            ///Como no es considerado totalmente XML lo insertamos en una etiqueta root
            InputSource is = new InputSource(new StringReader("<root>" + XMLString + "</root>"));
            org.w3c.dom.Document doc = builder.parse(is);

            ///Array con los campos de datos a extraer
            String[] keyData = Document.getDataFields();
            for (String key : keyData) {
                ///Inicializamos el conjunto de datos
                Set<String> listofData = new HashSet();

                ///Extraemos los nodos que coincidan con el campo
                NodeList listNodes = doc.getElementsByTagName(key);

                ///Recorremos el array, sacando todos los coincidentes
                ///Además los ponemos todos en minúscula en caso de que haya algunos 
                for (int i = 0; i < listNodes.getLength(); i++) {
                    Node node = listNodes.item(i);
                    listofData.add(node.getTextContent().toLowerCase());
                }

                ///Añadimos un nuevo mapa con key = nombre del campo     valor = conjunto de datos coincidentes
                result.put(key, listofData);
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            result = null;
        }
        return result;
    }

    /**
     * Parseamos el String de las etiquetas XML y los caracteres no deseados
     *
     * @param parse String a modificar
     * @return String sin etiquetas ni caracteres especiales de XML &lt
     */
    private String parseXMLtoString(String parse) {
        String result = parse.replaceAll("<.\\w*>", "");
        result = replaceunExcapedCharacter(result);
        return result;
    }

    /**
     * Reemplaza los caracteres salientes no identificados del XML como &lt y
     * demás
     *
     * @param source String a modificar
     * @return devuelve el string sin los caracteres especiales
     */
    private String replaceunExcapedCharacter(String source) {
        Set<String> Aux = unExcapedHTMLCharacter.keySet();
        Iterator ite = Aux.iterator();
        while (ite.hasNext()) {
            String s = (String) ite.next();
            source = source.replace(s, unExcapedHTMLCharacter.get(s));
        }

        return source;
    }
}
