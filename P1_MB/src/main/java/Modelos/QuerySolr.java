/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.io.UnsupportedEncodingException;
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
      
    private final String REGEX_SPECIAL_CHARACTERS = "[+\\-\\&|\\(\\)\\{\\}\\[\\]\\^\"\'\\~\\*\\?\\:\\!\\/]";
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
        
        
        String text_aux = text.replaceAll(REGEX_SPECIAL_CHARACTERS,"");
        String[] aux = text_aux.split(" ");
        
        if (nWords>0 && aux.length > nWords){
            for (int i = 0; i < nWords; i++) {
                result.append(aux[i]).append(" ");
            }
        } else {
            result.append(text_aux);
        }
        //System.out.println(result.toString());
        String returned = result.toString().replaceAll(" ", "+");
        return returned;
    }
    
    
    public String getQuery() {
        StringBuilder query = new StringBuilder();

        if (title != null) {                                               ///No nos fijamos en los titulos que no sean el pasado por parámetro
            String title_aux = title.replaceAll(REGEX_SPECIAL_CHARACTERS, "");
            query.append("title:(").append(title_aux).append(")\n");
        }
        if (!authors.isEmpty()){                                                    ///TODO Poner que los autores  estén cualquiera de los mencionados
            String author_aux = authors.toString().replaceAll("[\\[\\]]", "");      ///Mirar los resultados obtenidos son mejores
            query.append("authors: ").append(author_aux).append("\n");
        }
        if (text != null){
            String text_aux = text.replaceAll(REGEX_SPECIAL_CHARACTERS, "");
            query.append("text_book:").append(getQueryNWordsText(-1)).append("\n");
        }
        
        //System.out.println(query.toString());
        return query.toString();
    }
    
    
    private String getformatString(String stringFormat){
        String result = stringFormat.replaceAll(REGEX_SPECIAL_CHARACTERS, "");
        return result;
    }
    
}