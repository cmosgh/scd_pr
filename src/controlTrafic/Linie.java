/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlTrafic;

/**
 *
 * aceasta clasa va fi cea care are contine trenurile din gari
 */
public class Linie {

    private Tren t;
    private boolean ocupata;

    Linie() {
        this.ocupata = false;
    }
    
    /*
     *  adauga tren se foloseste la adaugarea unui tren pe linie
     *  
     *  @args t - de tip tren este trenul care va fi adaugat pe linia respectiva
     *  
     *  returneaza true in caz de succes sau false in cazul in care linia este deja ocupata
     *   in mod normal ar fi trebuit adaugate niste exceptii dar nu stiu daca este relevant pentru acest proiect
     */
    public boolean adaugaTren(Tren t) {
        if (!this.ocupata) {
            this.t = t;
            this.ocupata = true;
            return true;
        } else {
            return false;
        }
    }
    
    /*
     *  stergeTren se foloseste la stergerea trenului de pe linie
     *  
     *  returneaza true in caz de succes sau false in cazul in care linia este goala
     *  in mod normal ar fi trebuit adaugate niste exceptii dar nu stiu daca este relevant pentru acest proiect
     */
    public boolean stergeTren() {
        if (this.ocupata) {
            this.t = null;
            this.ocupata = false;
            return true;
        } else {
            return false;
        }
    }

    public boolean iaStatus() {
        return this.ocupata;
    }

    public Tren iaTren() {
        return this.t;
    }
}
