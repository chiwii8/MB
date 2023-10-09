/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LecturaFicheros;

import java.util.ArrayList;
import java.util.List;
import org.dom4j.Element;


/**
 *
 * @author alejandro
 */

public class DocumentFile {
    private String id;
    private String title;
    private List<String> Authors;
    private String Text;

    public DocumentFile(){
        Authors = new ArrayList<>();
    }
    public DocumentFile(String id, ArrayList<String> Authors, String Text){
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

    public List<String> getAuthors() {
        return Authors;
    }

    public void setAuthors(List<String> Authors) {
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
    
    public Element getElement(Element root){
        Element newElement = root.addElement(DocumentFile.class.toString())
                .addAttribute("id", id)
                .addAttribute("title", title);
   
        for(String author: Authors)
            newElement.addAttribute("author", author);
   
        newElement.addText(Text);
        
        return newElement;
    }
}
