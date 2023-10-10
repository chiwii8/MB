/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package App;
import LecturaFicheros.DocumentFile;
import LecturaFicheros.DocumentToXML;
import LecturaFicheros.ReadFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
/**
 *
 * @author alejandro
 */
public class miClienteSoljr {
    public final static String path = "C:\\Users\\aleja\\git\\MB\\CISI.ALL";
    public static void main(String[] args) throws SolrServerException, IOException{
        //final SolrClient client = new Http2SolrClient.Builder("http://localhost:8983/solr").build();
        //final SolrInputDocument doc = new SolrInputDocument();
        //doc.addField("id", UUID.randomUUID().toString());
        //doc.addField("name", "Amazon Kindle Paperwhite");
        //doc.addField("price", 99.0);
        //final UpdateResponse updateResponse = client.add("micoleccion", doc);
        // Indexed documents must be committed
        //client.commit("micoleccion");
        
        System.out.println("Muestra algo");
        //Nueva forma de indexación de los documentos
        List<DocumentFile> listofDocument;
        ReadFile r = new ReadFile();
        try{
            listofDocument = r.readCorpus(path);
            System.out.println("Se ha Leido correctamente el fichero");
            DocumentToXML.parseCorpusDocument(listofDocument, "./Prueba.xml");
            
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }catch(IOException e){
            System.out.println("Error En IO");
            System.out.println(e.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        /*String mat = ".I 123";
        if(mat.matches("^\\.I\\s*\\d+")){
            System.out.println("el pattern es correcto");
        }else{
            System.out.println("FAllo en el pattern");
        }*/
        
        
        
        }    
}
