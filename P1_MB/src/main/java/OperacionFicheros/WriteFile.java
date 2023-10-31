/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package OperacionFicheros;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

/**
 *
 * @author alejandro
 */
public class WriteFile {
    
    private static String phase = "Q0";
    private static String team = "etsi";
    private static final String NAME_INDEX_ATTRIBUTE = "index";
    private static final String NAME_SCORE_ATTRIBUTE = "score";

    /**
     * *
     * Crea un fichero en formato 'TREC_TOP_EVAL'
     *
     * @param path dirección absoluta o relativa del fichero que quiere crear
     * @param documentResults Los resultados de las consultas de solr para
     * Evaluar
     * @throws java.io.IOException
     */
    public static void writeTREC_EVAL(String path, List<SolrDocumentList> documentResults) throws IOException {
        BufferedWriter writer = writeDocument(path);
        if (writer != null) {
            
        } else {
            System.out.println("Error: El fichero que intentas crear ya existe");
        }
    }

    /**
     * Crea un documento de texto si no existe y lo abre
     *
     * @param path dirección absoluta o relativa del fichero que quiere leer o
     * crear
     * @return devuelve el fichero listo para escribir
     */
    private static BufferedWriter writeDocument(String path) throws IOException {
        File newFile = new File(path);

        if (!newFile.exists()) {
            newFile.createNewFile();
        }

        FileWriter writer = new FileWriter(newFile);
        return new BufferedWriter(writer);
    }

    /**
     * Formatea un SolrDocument de la siguiente forma
     *
     * nºConsulta Fase nºdocumento ranking score equipo
     * 
     * número de documento: Es el índice del documento que tiene desigando por defecto
     * score: Es una puntuación realizada por solr para calificar el resultado obtenido
     * equipo:Nombre del equipo
     * @param nConsult Número de consulta
     * @param ranking rango obtenido en la búsqueda
     * @param documentToFormat documento que se va ha formatear
     * @return
     */
    private static String FormatSolrDocumentToTrecEval(SolrDocument documentToFormat, int nConsult, int ranking) {
        StringBuilder result = new StringBuilder();

        String index = documentToFormat
                .getFieldValue(NAME_INDEX_ATTRIBUTE)
                .toString();

        String score = documentToFormat
                .getFieldValue(NAME_SCORE_ATTRIBUTE)
                .toString();

        result.append(nConsult).append(" ")
                .append(phase).append(" ")
                .append(index).append(" ")
                .append(ranking).append(" ")
                .append(score).append(" ")
                .append(team);

        return result.toString();

    }

    public static String getPhase() {
        return phase;
    }

    public static void setPhase(String phase) {
        WriteFile.phase = phase;
    }

    public static String getTeam() {
        return team;
    }

    public static void setTeam(String team) {
        WriteFile.team = team;
    }
    
    

}
