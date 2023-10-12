/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package App;

import LecturaFicheros.DocumentFileBean;
import LecturaFicheros.ReadFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.Http2SolrClient;

/**
 *
 * @author alejandro
 */
public class miClienteSoljr {

    public final static String path = "C:\\Users\\aleja\\git\\MB\\CISI.ALL";

    public static void main(String[] args) throws SolrServerException, IOException {
        final SolrClient client = new Http2SolrClient.Builder("http://localhost:8983/solr").build();
        //Nueva forma de indexación de los documentos
        List<DocumentFileBean> listofDocument;
        ReadFile r = new ReadFile();
        try {
            listofDocument = r.readCorpus(path);
            client.addBeans("micoleccion", listofDocument);
            client.commit("micoleccion");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SolrServerException e) {
            System.out.println("Error con el servidor de solr");
        } catch (IOException e) {
            System.out.println("Error Con el cliente de solr");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            client.close();
        }
    }
}
