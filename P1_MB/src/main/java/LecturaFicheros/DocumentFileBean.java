/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LecturaFicheros;

import java.util.ArrayList;
import java.util.List;
import org.apache.solr.client.solrj.beans.Field;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 *
 * @author alejandro
 */

public class DocumentFileBean {
    private String id;
    private String title;
    private List<String> authors;
    private String text;

    public DocumentFileBean(){
        authors = new ArrayList<>();
    }
    public DocumentFileBean(String id, ArrayList<String> Authors, String Text){
        this.id = id;
        this.authors = Authors;
        this.text = Text;
    }

    public static String getNameClass(){
        return "DocumentFile";
    }
    
    
    public String getId() {
        return id;
    }
    
    
    @Field("id")
    public void setId(String id) {
        this.id = id;
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
    
    public void setnewAuthor(String Author){
        authors.add(Author);
    }

    
    public String getText() {
        return text;
    }

    @Field("text")
    public void setText(String Text) {
        this.text = Text;
    }   
    
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder("Id: ").append(id).append("\nTitle: ").append(title);
        for (String author : authors) {
            str.append("author: ").append(author);
        }
        
        str.append("\nText: ").append(text).append("\n\n");
        
        return str.toString();
    }
    
}