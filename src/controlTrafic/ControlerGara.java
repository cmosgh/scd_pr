/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlTrafic;

import java.awt.Component;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


/**
 *
 * @author cmos
 */
public class ControlerGara extends Thread{
    private Gara gara;
    private Socket socket;
    private GaraUI interfataGrafica;

    public Gara getGara() {
        return gara;
    }

    public Socket getSocket() {
        return socket;
    }
    
    public GaraUI getInterfataGrafica(){
        return interfataGrafica;
    }
    
    ControlerGara(int gid) {
        Globals globals = new Globals();
        this.gara = new Gara(gid);
        this.interfataGrafica = new GaraUI(this);
        
        
        //pornim interfata in lazy mode
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                interfataGrafica.setVisible(true);
                try {
                    sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ControlerGara.class.getName()).log(Level.SEVERE, null, ex);
                }
                interfataGrafica.schimbaNumeGara(gara.getNume());

            }
        });
        
        //facem clientul pentru serverul de date
        try {
            this.socket = new Socket(InetAddress.getByName(globals.ipServer),globals.portServer);//adresa si portul serverului este constanta
            cereDetaliiGara(gid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void run(){
        ServerGara sg = new ServerGara(this);
        sg.start();
    }
    
    public int getLinieLibera(){
        int id=-1;
        //returnam -1 in cazul in care nu este nicio linie libera
        id = gara.getLinieLibera("peron").getId();
        return id;
    }

    public void puneTrenPeLinie(Tren t, int idLinieLibera) {
       gara.puneTrenPeLinie(idLinieLibera, t);
       interfataGrafica.puneTrenPeLinie(idLinieLibera, t);
       interfataGrafica.scrieLog("Punem un tren pe linia"+idLinieLibera);
    }
    
    public void iaTrenDePeLinie (int idLinie){
        Tren t = gara.iaTrenDePeLinie(idLinie);
        //trebuie sa salvam situatia in baza de date de pe server
        Properties param = new Properties();
        interfataGrafica.iaTrenDePeLinie(idLinie);
        interfataGrafica.scrieLog("Trenul"+t.getId()+" a fost pus in depou.");
        param.put("trid", t.getId()+"");
        param.put("gid", gara.getId()+"");
        Cerere cerere = new Cerere(2,param);// 2 este id-ul pentru punere tren la o anumita gara
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(cerere);
            oos.flush();
            
            //citim datele primite de la server
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Properties pr = (Properties)ois.readObject();
            
            String success = pr.getProperty("success");
            if (success.equals("true")) interfataGrafica.scrieLog("Serverul confirma punerea in depou.");
            else interfataGrafica.scrieLog("Serverul infirma punerea in depou.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
    }
    
    private void cereDetaliiGara(int gid){
        ObjectOutputStream oos = null;
        try {
            
            Properties param = new Properties();
            param.put("id",gid+"");
            Cerere cs = new Cerere(1,param);
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(cs);
            oos.flush();
            //citim datele primite de la server
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Properties pr = (Properties)ois.readObject();
            //rescriem proprietatile garii
            gara.setId(Integer.parseInt(pr.getProperty("id")));
            gara.setNume(pr.getProperty("nume"));
            gara.setServerIP(pr.getProperty("serverIp"));
            gara.setServerSocket(Integer.parseInt(pr.getProperty("serverSocket")));
            int nrLiniiPeron = Integer.parseInt(pr.getProperty("nrLiniiPeron"));
            gara.creeazaLiniiPeron(nrLiniiPeron);
            System.out.println("Gara "+gid+" a primit \nNume:"+gara.getNume()+ "\nserverIp"+gara.getServerIP()+"\nserverSocket:"+gara.getServerSocket()+
                    "\nnrLiniiPeron:"+nrLiniiPeron+"\n");
            
//            gara.setServerSocket(Integer.parseInt(pr.getProperty("serverSocket")));
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
    }
    
}
class ServerGara extends Thread{
    ControlerGara cg;
    ServerGara(ControlerGara cg){
        this.cg=cg;
    } 
    public void run(){
        try {
            ServerSocket ss = new ServerSocket(cg.getGara().getServerSocket());
            while (true) {
                System.out.println("Gara "+cg.getGara().getNume()+" asteapta cereri...");
                Socket s = ss.accept();
                SocketAddress addr = s.getRemoteSocketAddress();
                System.out.println(addr);
                ClientHandlerGara handler = new ClientHandlerGara(s, cg);
//              sd.handlerList.add(handler);
                handler.start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}