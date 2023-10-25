/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LecturaFicheros;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alejandro
 */
public class QuerySolr {

    private String index;
    private String title;
    private List<String> authors;
    private String text;
    private String boletin;

    public QuerySolr() {
        title = null;
        authors = new ArrayList<>();
        text = null;
        boletin = null;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public void setnewAuthor(String Author) {
        authors.add(Author);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBoletin() {
        return boletin;
    }

    public void setBoletin(String Boletin) {
        this.boletin = Boletin;
    }

    public String getQueryNWordsText(int nWords) {
        StringBuilder result = new StringBuilder("text_book:");
        String[] aux = text.split(" ");
        if (aux.length > nWords) {
            for (int i = 0; i < nWords; i++) {
                result.append(aux[i]).append(" ");
            }
        } else {
            result.append(text);
        }

        return result.toString();
    }

    public String getQuery() {
        StringBuilder query = new StringBuilder();

        if (title != null) {                                                  ///No nos fijamos en los titulos que no sean el pasado por parámetro
            query.append("title:(").append(title).append(")");
        }
        if (!authors.isEmpty()) {
            query.append(" authors:").append(authors.toArray());
        }
        if (text != null) {
            query.append(" text_book:").append(text).append("\n");
        }
        return query.toString();
    }

}