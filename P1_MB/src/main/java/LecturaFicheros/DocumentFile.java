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
    private List<String> authors;
    private String text;

    public DocumentFile(){
        authors = new ArrayList<>();
    }
    public DocumentFile(String id, ArrayList<String> Authors, String Text){
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
        return authors;
    }

    public void setAuthors(List<String> Authors) {
        this.authors = Authors;
    }
    
    public void setnewAuthor(String Author){
        authors.add(Author);
    }

    public String getText() {
        return text;
    }

    public void setText(String Text) {
        this.text = Text;
    }   
    
    public Element getElement(Element root){
        
        Element newElement = root.addElement("DocumentFile");
        Element elId = newElement.addElement("identificador").addText(id);
        Element elTitle = newElement.addElement("title").addText(title);
        authors.stream().map(author->newElement.addElement("author").addText(author));
        Element elText = newElement.addElement("text").addText(text);
        
        return newElement;
        /*Element newElement;
            newElement.
                root.addElement("DocumentFile")
                .addElement("identificador")
                .addAttribute("identificador", id)
                .addAttribute("titulo", title);
   
        for(String author: Authors)
            newElement.addAttribute("autor", author);
   
        newElement.addAttribute("texto",Text);
        
        return newElement;*/        /*Element newElement;
            newElement.
                root.addElement("DocumentFile")
                .addElement("identificador")
                .addAttribute("identificador", id)
                .addAttribute("titulo", title);
   
        for(String author: Authors)
            newElement.addAttribute("autor", author);
   
        newElement.addAttribute("texto",Text);
        
        return newElement;*/
    }
}
