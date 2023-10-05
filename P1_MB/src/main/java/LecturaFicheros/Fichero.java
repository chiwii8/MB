/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LecturaFicheros;

import java.util.ArrayList;

/**
 *
 * @author alejandro
 */

public class Fichero {
    private String id;
    private String title;
    private ArrayList<String> Authors;
    private String Text;

    public Fichero(){
        Authors = new ArrayList<>();
    }
    public Fichero(String id, ArrayList<String> Authors, String Text){
        this.id = id;
        this.Authors = Authors;
        this.Text = Text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getAuthors() {
        return Authors;
    }

    public void setAuthors(ArrayList<String> Authors) {
        this.Authors = Authors;
    }
    
    public void setnewAuthor(String Author){
        Authors.add(Author);
    }

    public String getText() {
        return Text;
    }

    public void setText(String Text) {
        this.Text = Text;
    }

    
    
    
    
    @Override
    public String toString(){
        return "";
    }
}
