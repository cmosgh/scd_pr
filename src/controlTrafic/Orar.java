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
    private int gidOrigine, gidDestinatie;
    private int  idTren;
    private int tpornire, tsosire;
    
    Orar (int gidOrigine, int gidDestinatie, int tpornire, int tsosire, int idTren) {
        this.gidOrigine = gidOrigine;
        this.gidDestinatie = gidDestinatie;
        this.tpornire = tpornire;
        this.tsosire = tsosire;
        this.idTren = idTren;
    }
    /**
     *
     * @return
     */
    @Override
    public String toString(){
        //@TODO sa preia legatura intre baza de date si informatia
        return "Pornim din gara "+gidOrigine+"la ora "+tpornire+ " si sosim in "+gidDestinatie+ " la ora "+tsosire;
    }
}
