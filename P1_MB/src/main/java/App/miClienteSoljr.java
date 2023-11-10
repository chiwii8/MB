/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package App;

import Modelos.Document;
import OperacionFicheros.ReadFile;
import OperacionFicheros.WriteFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.Http2SolrClient;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

/**
 *
 * @author alejandro
 */
public class miClienteSoljr {

    public static String pathCorpus = "..\\CISI.ALL";
    public static String pathQuery = "..\\CISI.QRY";
    private static final String COLLECTION_SOLR_NAME_DEFAULT = "corpus";
    private static final int NUMBER_OF_WORDS_SELECTED_TO_QUERIES = 5;

    public static void main(String[] args) throws SolrServerException, IOException {
        SolrClient client = new Http2SolrClient.Builder("http://localhost:8983/solr").build();
        //Nueva forma de indexación de los documentos
        List<Document> listofDocument;
        List<Document> listOfQueries = null;
        ReadFile r = new ReadFile();
        int opt;
        try {
            do {
                opt = Menu();
                switch (opt) {
                    case 1 -> {                                             ///Carga los valores en el corpus
                        listofDocument = r.readDocuments(pathCorpus);
                        client.addBeans(COLLECTION_SOLR_NAME_DEFAULT, listofDocument);
                        client.commit(COLLECTION_SOLR_NAME_DEFAULT);
                    }
                    case 2 -> {                                             ///Cargar consultas
                        listOfQueries = r.readDocuments(pathQuery);
                        System.out.println("Se han leido las consultas");
                    }
                    case 3 -> {                                             ///Lanzar todas las consultas
                        if (listOfQueries != null) {
                            System.out.println("Se han lanzado las consultas");
                            doQuery(client, listOfQueries);
                        } else {
                            System.out.println("La lista de consultas está vacía");
                        }

                    }
                    case 4 -> {                                          ///Generar Fichero TREC_EVAL
                        if (listOfQueries != null) {
                            generateTrecEval(client, listOfQueries);
                        } else {
                            System.out.println("Es necesario cargar las queries");
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
        System.out.println("4. Generar fichero TREC-EVAL");
        System.out.println("0. Salir");
        System.out.println("---------------------");
        System.out.print("Selecciona una opcion: ");
        option = in.nextInt();

        while (option < 0 || option > 4) {
            System.out.println("Opcion no válida");
            System.out.print("Selecciona otra opcion: ");
            option = in.nextInt();
        }

        return option;
    }

    public static SolrQuery configureQueryTrecEval() {
        SolrQuery newquery = new SolrQuery();
        newquery.set("fl", "index,score");
        return newquery;
    }

    public static void doQuery(SolrClient client, List<Document> queries) throws SolrServerException, IOException {

        LinkedList<SolrDocumentList> listOfDocument = new LinkedList<>();
        for (Document q : queries) {
            SolrQuery query = new SolrQuery(q.getQueryNWordsText(NUMBER_OF_WORDS_SELECTED_TO_QUERIES));
            QueryResponse rsp = client.query(COLLECTION_SOLR_NAME_DEFAULT, query);

            SolrDocumentList docs = rsp.getResults();
            listOfDocument.add(docs);
        }

        while (!listOfDocument.isEmpty()) {
            SolrDocumentList docs = listOfDocument.poll();
            for (SolrDocument doc : docs) {
                System.out.println(doc.toString());
            }

        }
    }

    public static void generateTrecEval(SolrClient client, List<Document> queries) throws SolrServerException, Exception {
        List<SolrDocumentList> docs = new ArrayList<>();

        SolrQuery query = configureQueryTrecEval();

        for (int i = 0; i < queries.size(); i++) {
            Document example = queries.get(i);
            query.setQuery(example
                    .getQueryNWordsText(-1));

            QueryResponse rsp = client.query(COLLECTION_SOLR_NAME_DEFAULT, query);
            docs.add(rsp.getResults());
        }
        WriteFile.generateTREC_EVAL(docs);
    }
}
