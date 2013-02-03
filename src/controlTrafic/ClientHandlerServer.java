/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlTrafic;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
/**
 *
 * @author evo
 */
public class ClientHandlerServer extends Thread{
    
    Socket s;
    ServerDeDate sd;
    
    ClientHandlerServer(Socket s, ServerDeDate sd){
        this.s = s;
        this.sd = sd;
    }
    
    private Properties CerereInformatiiGara(Cerere cs){
        Properties parametriiPrimiti = cs.getParametrii();
        int gid =Integer.parseInt(parametriiPrimiti.getProperty("id"));
        Properties param = new Properties();
        try {
            Connection con = sd.getCon();
            System.out.println("Connectare la DB OK!");
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from APP.GARI where GID = " + gid);
            while (rs.next()) {
                param.put("id", gid+"");
                param.put("nume",rs.getString("NUME"));
                param.put("serverIp", rs.getString("SERVERIP"));
                int  serverport = rs.getInt("SERVERPORT");
                param.put("serverSocket", serverport+"");
                param.put("nrLiniiPeron", rs.getInt("LINIIPERON")+"");
                System.out.println("Client handler server CerereInformatiiGara trimis \n Gara " + gid + " cu: \n Nume: " + param.getProperty("nume")
                        + "\n ServerIP: " + param.getProperty("serverIp")
                        + "\n ServerSocket: " + param.getProperty("serverSocket")+ 
                        " nrLiniiPeron: "+ param.getProperty("nrLiniiPeron"));
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return param;
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
                        Properties infoGara = this.CerereInformatiiGara(cs);
                        oos.writeObject(infoGara);
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
