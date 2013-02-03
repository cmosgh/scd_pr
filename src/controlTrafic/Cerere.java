/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlTrafic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author cmos
 * 
 * Clasa pentru transmiterea comenzilor
 * id reprezinta com care va trebui executata
 * folosim id pentru ca JAVA nu a descoperit o metoda sa verifice stringuri in switch
 * 
 */
public class Cerere implements Serializable {
    private int idComanda;
    private Properties parametrii;

    public Cerere(int idComanda, Properties parametrii) {
        this.idComanda = idComanda;
        this.parametrii = parametrii;
    }

    public int getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(int idComanda) {
        this.idComanda = idComanda;
    }

    public Properties getParametrii() {
        return parametrii;
    }

    public void setParametrii(Properties parametrii) {
        this.parametrii = parametrii;
    }
    
    
    
    @Override
    public String toString(){
        Set params = parametrii.keySet();
        Iterator i = params.iterator();
        String com ="id: "+idComanda+" parametrii: "; 
        while(i.hasNext()){
            String str = (String)i.next();
            com+=parametrii.getProperty(str)+" ";
        }
        return com;
    }
    
    
}
