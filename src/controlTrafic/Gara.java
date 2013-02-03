/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlTrafic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cmos
 */
public class Gara {
    private int macaz=-1;//macazul nu este setat cand gara nu are linii
    private String nume, serverIP;
    private int id;
    private int serverSocket;
    private int nrLiniiPeron;
    private List<Linie> liniiPeron = new ArrayList<Linie>();
    private List<Linie> liniiIesire = new ArrayList<Linie>();

    Gara(int id) {
        this.id = id;
    }

    Gara(String nume, int id) {
        this.nume = nume;
        this.id = id;
        //@TODO ia restul informatiilor din baza de date
    }

    Gara(String nume, int id, int serverSocket, String serverIP) {
        this.nume = nume;
        this.id = id;
        this.serverSocket = serverSocket;
        this.serverIP = serverIP;
    }

    Gara(String nume, int id, int nrLiniiPeron, int nrLiniiIO) {
        this.nume = nume;
        this.id = id;
        //@TODO ia restul informatiilor din baza de date - server si celelalte
        for (int i = 0; i < nrLiniiPeron; i++) {
            this.liniiPeron.add(new Linie(i));
        }
        for (int i = 0; i < nrLiniiIO; i++) {
            this.liniiIesire.add(new Linie(i));
        }
    }
    
     public int getNrLiniiPeron() {
        return nrLiniiPeron;
    }

    public void setNrLiniiPeron(int nrLiniiPeron) {
        this.nrLiniiPeron = nrLiniiPeron;
    }

    public int getId() {
        return id;
    }
    
    public void creeazaLiniiPeron(int nrLinii){
        //macaz=0;//initial macazul e la linia 0. ramane de implementat intr-o varianta mai dezvoltata
        for (int i = 0; i < nrLinii; i++) {
           this.liniiPeron.add(new Linie(i)); 
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

    public List<Linie> getLiniiPeron() {
        return liniiPeron;
    }

    public List<Linie> getLiniiIesire() {
        return liniiIesire;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLiniiPeron(List<Linie> liniiPeron) {
        this.liniiPeron = liniiPeron;
    }

    public void setLiniiIesire(List<Linie> liniiIesire) {
        this.liniiIesire = liniiIesire;
    }

    /**
     *
     * @return
     */
//    @Override
//    public String toString() {
//        return "";
//    }

    public String getNume() {
        return nume;
    }

    public String getServerIP() {
        return serverIP;
    }

    public int getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(int serverSocket) {
        this.serverSocket = serverSocket;
    }
    
// separam partea de citire de date din db pentru server deci nu mai ave nevoie de codul de mai jos
//    private void getDbData(int gid) {
//        try {
//            //1. incarcare driver
//            Class.forName("org.apache.derby.jdbc.ClientDriver");
//            //2. conectare la baza de date
//            Connection con =
//                    DriverManager.getConnection("jdbc:derby://localhost:1527/scd", "app", "app");
//
//            System.out.println("Connectare la DB OK!");
//            Statement s = con.createStatement();
//            ResultSet rs = s.executeQuery("select * from APP.GARI where GID = " + gid);
//            while (rs.next()) {
//                String n = rs.getString("NUME");
//                this.nume = n;
//                this.serverIP = rs.getString("SERVERIP");
//                this.serverSocket = rs.getInt("SERVERPORT");
//                System.out.println("am initializat Gara " + id + " cu: \n Nume: " + nume
//                        + "\n ServerIP: " + serverIP
//                        + "\n ServerSocket: " + serverSocket);
//            }
//            rs.close();
//            con.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public int getMacaz() {
        return macaz;
    }

    public void setMacaz(int macaz) {
        this.macaz = macaz;
    }

}
