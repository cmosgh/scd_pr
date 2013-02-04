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
    private String nume;
    private int gid;
    
    
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

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }
    
    @Override
    public String toString(){
        return "Trenul id:"+id+" de tip:"+tip;
    }
    
}
