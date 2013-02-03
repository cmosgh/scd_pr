/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlTrafic;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;

/**
 *
 * @author cmos
 */
public class ClientHandlerGara extends Thread{
    Socket s;
    ControlerGara cg;
    
    ClientHandlerGara(Socket s, ControlerGara cg){
        this.s = s;
        this.cg = cg;
    }
    
    public Properties statusLinii(){
        //aceasta metoda va returna tot timpul prima linie de peron libera
        Properties raspuns= new Properties();
        int idLinieLibera = cg.getLinieLibera();
        String exista = cg.getLinieLibera()>-1 ? "true":"false";
        raspuns.put("exista",exista);
        raspuns.put("idLinieLibera",idLinieLibera+"");
        return raspuns;
    }
    
    public Properties primescTren(Cerere c){
        Properties raspuns = new Properties();
        
        return raspuns;
    }
    
    private Properties puneTrenDinDepou(Cerere cs) {
        Properties raspuns = new Properties();
        
        return raspuns;
    }
    
     public void run(){
            
        try{
        
            System.out.println("Client connected!");
            boolean active = true;
            while(active){//daca serverul cade ar trebui sa inchidem socketul
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                Cerere cs = (Cerere)ois.readObject();
                //System.out.println("Am primit comanda "+ cs.toString());
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                switch (cs.getIdComanda()){
                    case 1:
                        Properties raspunsLinii = statusLinii();
                        oos.writeObject(raspunsLinii);
                        break;
                    case 2:
                        Properties raspunsPrimescTren = primescTren(cs);
                        oos.writeObject(raspunsPrimescTren);
                        break;
                    case 3:
                        Properties raspunsPuneTrenDinDepou = puneTrenDinDepou(cs);
                        oos.writeObject(raspunsPuneTrenDinDepou);
                        break;
                    default:
                        Properties p = new Properties();
                        p.put("mesaj","Comanda necunoscuta");
                        oos.writeObject(p);
                        break;
                }
                oos.flush();
                
            }
            
            s.close();
            return;
        }catch(Exception e){
            //e.printStackTrace();
        }
 
    }

    
}
