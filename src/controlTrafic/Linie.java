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
    private int id;

    Linie() {
        this.id = 0;//default 0
        this.ocupata = false;
        this.t = null;
    }
    //pentru cazul in care deja punem si id-ul. aceasta probabil va fi cea mai folosita
    Linie(int id){
        this.id = id;
        this.ocupata = false;
        this.t = null;
    }
    //pentru cazul in care in momentul adaugarii liniei deja vrem sa punem tren pe linie
    Linie(int id, Tren t){
        this.id = id;
        this.ocupata = true;
        this.t = t;
    }
    
    Linie(Tren t, boolean ocupata, int id) {
        this.t = t;
        this.ocupata = ocupata;
        this.id = id;
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
    /**
     *
     * @return
     */
    @Override
    public String toString(){
        String este=this.ocupata?" se afla trenul "+this.t.getId():" nu se afla niciun tren";
        return "Pe linia "+this.id+este;
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
    /*
     * Returneaza starea liniei
     */
    public boolean getStatus() {
        return this.ocupata;
    }
    /*
     * returneaza trenul care se afla pe linie
     */
    public Tren getTren() {
        return this.t;
    }
    /*
     * returneaza id-ul liniei
     */
    public int getId() {
        return this.id;
    }
    
    public void setId(int id){
        this.id = id;
    }
}
