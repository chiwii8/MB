/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miCliente;
import java.io.IOException;
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
    public static void main(String[] args) throws SolrServerException, IOException{
        final SolrClient client = new Http2SolrClient.Builder("http://localhost:8983/solr").build();
        final SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", UUID.randomUUID().toString());
        doc.addField("name", "Amazon Kindle Paperwhite");
        doc.addField("price", 99.0);
        final UpdateResponse updateResponse = client.add("micoleccion", doc);
        // Indexed documents must be committed
        client.commit("micoleccion");
        }    
}
