/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LecturaFicheros;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 *
 * @author alejandro
 */
public class DocumentToXML {

    public Document createDocument(String rootName) throws IOException {
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement(rootName);

        return doc;
    }

    public Document parseCorpusDocument(List<Node> Components) {
        return null;
    }

    public void writeoutDocument(String path, Document doc) {
        File file = new File(path);

        try {
            if (!file.exists()) {
                System.out.println("El documento no existe así que se generará");
                if(file.createNewFile())
                    System.out.println("Se ha creado un fichero con éxito");
                else{
                    throw new Exception("Se ha producido un error al crear el fichero");
                }
            }
            FileWriter out = new FileWriter(file);
            doc.write(out);
        } catch (IOException e) {

            System.out.println("Se ha producido un error a la hora de crear el XML");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

}
