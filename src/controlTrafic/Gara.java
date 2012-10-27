/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlTrafic;

import java.util.List;

/**
 *
 * @author cmos
 */
public class Gara {

    private String nume, serverIP;
    private int id;
    private int serverSocket, clientSocket;
    private List<Linie> liniiPeron, liniiIesire;
    private int nrLiniiPeron, nrLiniiIO;

    Gara(String nume, int id) {
        this.nume = nume;
        this.id = id;
    }

    Gara(String nume, int id, int serverSocket, String serverIP, int clientSocket) {
        this.nume = nume;
        this.id = id;
        this.serverSocket = serverSocket;
        this.clientSocket = clientSocket;
        this.serverIP = serverIP;
    }

    Gara(String nume, int id, int nrLiniiPeron, int nrLiniiIO) {
        this.nume = nume;
        this.id = id;
        for (int i = 0; i < nrLiniiPeron; i++) {
            this.liniiPeron.add(new Linie(i));
        }
        for (int i = 0; i < nrLiniiIO; i++) {
            this.liniiIesire.add(new Linie(i));
        }
    }

    public List<Linie> getLinii(String tip) {
        if (tip.equals("IO")) {
            return this.liniiIesire;
        } else {
            return this.liniiPeron;
        }
    }
    /*
     * returneaza prima linie libera intalnita
     */

    public Linie getLinieLibera(String tip) {
        if (tip.equals("IO")) {
            int IOsize = this.liniiIesire.size();
            List<Linie> lIO = this.liniiIesire;
            Linie l = new Linie();
            for (int i = 0; i < IOsize; i++) {
                //la prima linie libera returnam valoarea
                l = lIO.get(i);
                if (l.getStatus() == false) {
                    break;
                }
            }
            return l;
        } else {
            int IOsize = this.liniiPeron.size();
            List<Linie> lIO = this.liniiPeron;
            Linie l = new Linie();
            for (int i = 0; i < IOsize; i++) {
                //la prima linie libera returnam valoarea
                l = lIO.get(i);
                if (l.getStatus() == false) {
                    break;
                }
            }
            return l;
        }
    }
    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "";
    }

    public String getNume() {
        return this.nume;
    }

    public String getServerIP() {
        return serverIP;
    }

    public int getServerSocket() {
        return serverSocket;
    }
}
