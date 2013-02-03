/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlTrafic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author cmos
 */
public class ServerDeDate extends Thread {
    private int timpStart;
    private int timpStop;
    private int timpCurent = 0;
    private Connection con;
    private boolean serverActiv;
//    private List<ClientHandler> handlerList;

    public Connection getCon() {
        return con;
    }
    
    
    
    ServerDeDate(int timpStart, int timpStop) {
        this.timpStart = timpStart;
        this.timpStop = timpStop;
    }

    private void ConecteazaDb() {
        try {
            //1. incarcare driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            //2. conectare la baza de date
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/scd", "app", "app");
            System.out.println("Connectare la DB OK!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //ne conectam la baza de date
        ConecteazaDb();

        timpCurent = timpStart;
        while (timpCurent <= timpStop) {
            try {
                citesteOrar(timpCurent);
                sleep(1000);
                timpCurent++;
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerDeDate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            System.out.println("Inchid conexiunea la DB");
            con.close();



        } catch (SQLException ex) {
            Logger.getLogger(ServerDeDate.class.getName()).log(Level.SEVERE, null, ex);
        }
//        Iterator<ClientHandler> i = this.handlerList.iterator();
//        System.out.println("Inchidem toti clientii");
//        while (i.hasNext()) {
//            i.next().interrupt();
//        }
//        din pacare nu imi dau seama de ce nu pot opri toti clientii
        this.interrupt();

    }

    private void citesteOrar(int timp) {
        System.out.println("Citesc inregistrarea de la timpul: " + timp);
        //pregatim cererea
        try {
            Statement s = con.createStatement();
            String query ="select * from APP.ORAR where ORAPORNIRE="+timp; 
            ResultSet rs = s.executeQuery(query);
            int i = 0;
            while (rs.next()) {
                i++;
                int gidp = rs.getInt("GIDPORNIRE");
                int gido = rs.getInt("GIDOPRIRE");
                int trid = rs.getInt("TRID");
                System.out.println("Am citit GIDPORNIRE:" + gidp+
                        " GIDOPRIRE:"+gido+
                        " TRID:"+trid);
                ClientGara cg = new ClientGara(gidp, this);
                cg.start();
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServerDeDate sd = new ServerDeDate(0, 3600);
        sd.start();
        try {
            ServerSocket ss = new ServerSocket(1999);
            while (true) {
                System.out.println("Asteptam comenzi...");
                Socket s = ss.accept();

                SocketAddress addr = s.getRemoteSocketAddress();
                System.out.println(addr);
                ClientHandlerServer handler = new ClientHandlerServer(s, sd);
//              sd.handlerList.add(handler);
                handler.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
class ClientGara extends Thread{
    private int gid;
    private String adresaGara;
    private String numeGara;
    private int portGara;
    private ServerDeDate sd;
    
    public ClientGara(int gid, ServerDeDate sd) {
        this.gid = gid;
        this.sd=sd;//avem nevoie de serverul de date ca sa nu mai deschidem o noua conexiune la baza de date :)
        Connection con = sd.getCon();
        try {
            //luam detaliile legate de gara (socket)
            Statement s = con.createStatement();
            String query ="select * from APP.GARI where GID="+this.gid; 
            ResultSet rs = s.executeQuery(query);
            while (rs.next()){
                adresaGara = rs.getString("SERVERIP");
                portGara = rs.getInt("SERVERPORT");
                numeGara = rs.getString("NUME");
            }
            rs.close();
             System.out.println(this.toString());
        } catch (SQLException ex) {
            Logger.getLogger(ClientGara.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String toString(){
        String parsed="Trimitem cerere la gara "+numeGara+" cu id:"+gid+
                " cu ip:"+ adresaGara + " cu port:"+portGara;
        return parsed;
    }
    
    public void run(){
         try {
            System.out.println("Server Ne conectam la gara "+gid);
            Socket sclient = new Socket(InetAddress.getByName(adresaGara),portGara);
            System.out.println("Connection OK!");
            sclient.close();
            return;
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
}