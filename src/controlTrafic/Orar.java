/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlTrafic;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author cmos
 */
public class Orar implements Serializable{
    private Gara pornire, sosire;
    private Date tpornire, tsosire;
    
    Orar (Gara pornire, Gara sosire, Date tpornire, Date tsosire) {
        this.pornire = pornire;
        this.sosire = sosire;
        this.tpornire = tpornire;
        this.tsosire = tsosire;
    }
    /**
     *
     * @return
     */
    @Override
    public String toString(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
        return "Pornim din gara "+pornire.getNume()+"la ora "+df.format(tpornire)+ " si sosim in "+sosire.getNume()+ " la ora "+df.format(tsosire);
    }
    
}
