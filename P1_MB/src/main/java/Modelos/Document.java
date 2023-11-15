/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.solr.client.solrj.beans.Field;

/**
 *
 * @author alejandro
 */
public class Document {

    ///Constantes
    private final String REGEX_SPECIAL_CHARACTERS = "[+\\-\\&|\\(\\)\\{\\}\\[\\]\\^\"\'\\~\\*\\?\\:\\!\\/]";

    ///Variables
    private String index;
    private String title;
    private List<String> authors;
    private String boletin;
    private String text;
    private String typeInformation; ///desconozco su funcionalidad          ///Revisar e identificar su valor
    private String value;   ///desconozco su funcionalidad

    public Document() {
        index = null;
        title = null;
        authors = new ArrayList<>();
        boletin = null;

        text = null;
        typeInformation = null;
        value = null;
    }

    public Document(String index, ArrayList<String> Authors, String Text) {
        this.index = index;
        this.authors = Authors;
        this.text = Text;
    }

    public String getIndex() {
        return index;
    }

    @Field("index")
    public void setIndex(String index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    @Field("title")
    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    @Field("authors")
    public void setAuthors(List<String> Authors) {
        this.authors = Authors;
    }

    public void setnewAuthor(String Author) {
        authors.add(Author);
    }

    public String getText() {
        return text;
    }

    @Field("text_book")
    public void setText(String Text) {
        this.text = Text;
    }

    public String getBoletin() {
        return boletin;
    }

    public void setBoletin(String boletin) {
        this.boletin = boletin;
    }

    public String getTypeInformation() {
        return typeInformation;
    }

    public void setTypeInformation(String typeInformation) {
        this.typeInformation = typeInformation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    ///Métodos propios de la clase
    /**
     * Crea una consultada adecuada para solr con solo el texto a través del
     * número de palabras
     *
     * Si el número de palabras deseadas es mayor al total de palabras o , se
     * coge todo el texto
     *
     * @param nWords número de palabras seleccionadas del texto
     * @return query para solr
     */
    public String getQueryNWordsText(int nWords) {
        StringBuilder result = new StringBuilder("");

        String[] aux = text.split(" ");

        if (nWords > 0 && aux.length > nWords) {
            for (int i = 0; i < nWords; i++) {
                result.append(aux[i]).append(" ");
            }

        } else {
            result.append(text);
        }

        //System.out.println(result.toString());
        return "text_book:".concat(getformatString(result.toString()));
    }

    public String getQueryAllTextWords() {
        StringBuilder result = new StringBuilder("text_book:(").append(getformatString(text));
        if (title != null) {
            result.append(" OR text_book:").append(getformatString(title));
        }

        result.append(") ");
        return result.toString();
    }

    /**
     * Realiza una query para solr con todos los atributos posibles
     *
     * @return query para solr
     */
    public String getQuery() {
        StringBuilder query = new StringBuilder();

        //String text_aux = getformatString(text);
        //query.append("text_book:").append(text_aux).append("\n");
        query.append(getQueryAllTextWords());

        if (title != null) {
            String title_aux = getformatString(title);
            query.append("title:").append(title_aux).append("\n");
        }
        /*if (!authors.isEmpty()) {                                                   
            String author_aux = authors.toString().replaceAll("[\\[\\]]", "");      
            query.append("authors: ").append(author_aux).append("\n");
        }*/

        //System.out.println(query.toString());
        return query.toString();
    }

    /**
     * Formatea el string deseado eliminando los carácteres especiales no
     * aceptados por solr para las consultas y añadimos una secuencialidad de
     * las palabras para obtener resultados más fiables
     *
     * @param stringFormat string a formatear
     * @return string formateado
     */
    private String getformatString(String stringFormat) {

        try {
            String formatted = URLEncoder.encode(stringFormat, "UTF-8");
            return formatted;
        } catch (UnsupportedEncodingException ex) {
            return stringFormat.replaceAll(REGEX_SPECIAL_CHARACTERS, "")
                    .strip()
                    .replaceAll("\\s+", "+");
        }

    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Index: ").append(index);

        if (title != null) {
            str.append("\nTitle: ").append(title);
        }
        if (!authors.isEmpty()) {
            for (String author : authors) {
                str.append("\nauthor: ").append(author);
            }
        }

        if (boletin != null) {
            str.append("\nBoletin: ").append(boletin);
        }

        str.append("\nText: ").append(text).append("\n\n");

        return str.toString();
    }

}
