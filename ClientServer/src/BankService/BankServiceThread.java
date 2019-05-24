/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BankService;




import ServerProcess.Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author CHUWI
 */
public class BankServiceThread extends Thread   {
    
    public Socket myClientSocket;
    public boolean m_bRunThread = true;
    public BufferedReader in = null;
    public PrintWriter out = null;
    public ObjectInputStream ois = null;
    public ObjectOutputStream ous = null;


    public Server server;
    int STATE = 0;
    
    public ConcurrentHashMap<Integer, Integer> customerNonces = new ConcurrentHashMap();
    public ConcurrentHashMap<Integer, String> customerTransactions = new ConcurrentHashMap();

    public BankServiceThread(Server s) {
        super();
        server = s;
    }

    BankServiceThread(Server _server, Socket s) {
        myClientSocket = s;
        server = _server;
    }

    /*public abstract User GetUserType(String username, String password);

    public abstract int CreateUserType(String username, String password, int userID);

    public abstract User GetOnlineUserByConnectionID(int connectionID);

    public abstract Boolean UserExists(String username, String password);*/

    public void run() {

        if (!server.ServerOn) {
            System.out.print("Server has already stopped");
            m_bRunThread = false;
            try {
                myClientSocket.close();
                return;
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        System.out.println(
                "Accepted Client Address - " + myClientSocket.getPort());//getInetAddress().getHostName()
        try {
            in = new BufferedReader(
                    new InputStreamReader(myClientSocket.getInputStream()));

            out = new PrintWriter(
                    new OutputStreamWriter(myClientSocket.getOutputStream()));


            System.out.println("State = 0");
            
            String clientCommand = in.readLine();
           
            if(clientCommand.equalsIgnoreCase("CUSTOMER_NONCE_REQUEST")){
                                            System.out.println("Nonce Request");
                int bank_customerID = Integer.parseInt(in.readLine());
                
                int nonce = (int)(Math.random()*10000);
                customerNonces.put(nonce, bank_customerID);
                out.println(nonce);
                
            } else if(clientCommand.equalsIgnoreCase("CUSTOMER_TRANSACTION")){
                int bank_customerID = Integer.parseInt(in.readLine());
                //String transaction = in.readLine();
                String decision = ((Math.random()>0.7) ? "DECLINED": "ACCEPTED");

                
                customerTransactions.put(bank_customerID, decision);
                out.println(decision);
                
            } else if( clientCommand.equalsIgnoreCase("ORG_NONCE")) {
                
                int nonce = Integer.parseInt(in.readLine());
                
                int bank_customerID = customerNonces.get(nonce);
                String decision = customerTransactions.get(bank_customerID);
                if( decision != null )
                    out.println(decision);
                else out.println("nothing");
            }
            

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.println("Server Says : Stopping!");
                myClientSocket.close();
                in.close();
                out.close();

                System.out.println("...Stopped");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }


}
