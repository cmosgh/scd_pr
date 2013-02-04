/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlTrafic;

/**
 *
 * @author cmos
 */
public class ApplicationController {
    public static void main(String[] args){
        //aceasta este clasa de baza care va controla lansarea clientilor
        ControlerGara cg1 = new ControlerGara(1);
        cg1.run();
        ControlerGara cg2 = new ControlerGara(2);
        cg2.run();
        ControlerGara cg3 = new ControlerGara(3);
        cg3.run();
        
    }
}
