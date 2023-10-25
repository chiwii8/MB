/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package App;

import LecturaFicheros.DocumentFileBean;
import LecturaFicheros.QuerySolr;
import LecturaFicheros.ReadFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.Http2SolrClient;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

/**
 *
 * @author alejandro
 */
public class miClienteSoljr {

    public  static String pathCorpus = "C:\\Users\\aleja\\git\\MB\\CISI.ALL";
    public static String pathQuery = "C:\\Users\\aleja\\git\\MB\\CISI.QRY";
    private static final String collectionSolr = "micoleccion";
    private static final int numWordsQuery = 5;
    

    public static void main(String[] args) throws SolrServerException, IOException {
        SolrClient client = new Http2SolrClient.Builder("http://localhost:8983/solr").build();
        //Nueva forma de indexación de los documentos
        List<DocumentFileBean> listofDocument;
        List<QuerySolr> listOfQueries = null;
        ReadFile r = new ReadFile();
        int opt;

        try {
            do {
                opt = Menu();
                switch (opt) {
                    case 1 -> {                                             ///Carga los valores en el corpus
                        listofDocument = r.readCorpus(pathCorpus);
                        client.addBeans(collectionSolr, listofDocument);
                        client.commit(collectionSolr);
                    }
                    case 2 -> {                                             ///Cargar consultas
                        listOfQueries = r.readConsultas(pathQuery);
                        System.out.println("Se han leido las consultas");
                    }
                    case 3 -> {                                             ///Lanzar la primera consulta
                        if (listOfQueries!=null) {
                            SolrQuery query = new SolrQuery();
                            for (int j = 0; j < listOfQueries.size();j++) {
                                System.out.println("Consulta " + j +":");
                                QuerySolr example = listOfQueries.get(j);
                                query.setQuery(example.getQueryNWordsText(numWordsQuery));
                                QueryResponse rsp = client.query(collectionSolr, query);
                                SolrDocumentList docs = rsp.getResults();
                                for (int i = 0; i < docs.size(); i++) {
                                    System.out.println(docs.get(i));
                                }
                            }

                        } else {
                            System.out.println("La lista de consultas está vacía");
                        }
                    }
                    case 0 -> {                                             ///Fin programa
                        System.out.println("Fin del programa");
                    }
                    default ->
                        System.out.println("Opción no válida");
                }

            } while (opt != 0);

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

    public static int Menu() {
        int option;
        Scanner in = new Scanner(System.in);
        System.out.println("\t\t--- Menu ---");
        System.out.println("1. Cargar Corpus");
        System.out.println("2. Cargar Consultas");
        System.out.println("3. Lanzar Consulta");
        System.out.println("0. Salir");
        System.out.println("---------------------");
        System.out.print("Selecciona una opcion: ");
        option = in.nextInt();

        while (option < 0 || option > 3) {
            System.out.println("Opcion no válida");
            System.out.print("Selecciona otra opcion: ");
            option = in.nextInt();
        }

        return option;
    }
}
