/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlTrafic;

import java.io.Serializable;

/**
 *
 * aceasta clasa are o structura simpla
 * deocamdata nu retinem numai Id-ul trenului si tipul trenului
 * 
 */
public class Tren implements Serializable{
    private String tip; 
    private int id;
    
    Tren(int id ){
        this.id = id;
    }
    
    Tren(){
        this.id = 0;//default valoarea 0
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public int getId(){
        return id;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
    
}
