/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientProcess;


import Common.Message;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.*;
import java.net.*;

import Common.Order;
import Common.SupplierMessage;

import Common.SupplierSharedData;
import static java.lang.Thread.sleep;




/**
 *
 * @author CHUWI
 */
public class SupplierClient {

     public enum SupplierState {
        CONNECT, REGISTER, LOGOUT, REQUEST_JOB, VIEW_JOB, ACK_COURIER_ACCEPT_JOB
    }

    public SupplierState STATE = SupplierState.CONNECT;

    int supplierID;
    int connectionID;
    String username;
    String password;

    Boolean loggedIn = false;
    int state;

    int numSuppliersCounter = 0;
    int numJobs = 0;

    ArrayList<Order> shoppingCart = new ArrayList();

    ArrayList<String> currentProducts = new ArrayList();



    int currentSupplierID = 0;

    int currentJobID = 0;

    HashMap<Integer, Integer> jobID_List = new HashMap();

    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

    public SupplierClient() {

        supplierID = (int) (Math.random() * 100000);
    }

    public void HandleLoginResponse(Message response) {
        switch (response.messageType) {
            case Message.MessageType.ACCEPT_LOGIN: {

                this.loggedIn = true;
                this.connectionID = response.connectionID;

                STATE = SupplierState.REQUEST_JOB;
                break;
            }
            case Message.MessageType.REJECT_LOGIN: {

                STATE = SupplierState.REGISTER;

                // create register message
                break;
            }
        }
    }

    public int HandleViewJobs(String[] args, SupplierMessage msg) {
        msg.SupplierRequestJob(supplierID, connectionID);

        Message response = SendMessage(args, msg);

        if (response.baseProt == Message.BaseProtocol.TRANSFER) {

            if (response.messageType == SupplierMessage.SupplierMessageType.SUPPLIER_JOB) {

                Order o = ((SupplierMessage.SupplierJobOffer) response.msg).order;
                
                    // pretend like they're sorted by travel time
                    
                    for( int i = 0; i < o.products.size(); i++){
                        System.out.println("product: " + o.products.get(i).name +
                                    ", price: " + o.products.get(i).price);        
                    }
                
                

                o = null; 

            }
        }
        STATE = SupplierState.ACK_COURIER_ACCEPT_JOB;
        response = null; // gc
        return 0;
    }





    public int ViewController(String[] args) {
        SupplierMessage msg = new SupplierMessage();

        switch (STATE) {
            case CONNECT: {
                msg.LoginMessage(username, password, supplierID, connectionID);

                Message response = SendMessage(args, msg);

                if (response.baseProt == Message.BaseProtocol.YES_NO) {
                    HandleLoginResponse(response);
                }

                response = null; // gc

                break;
            }
            case REQUEST_JOB: {
                System.out.println("supplierID, connectionID: " + supplierID + ", " + connectionID);
                msg.SupplierRequestJob(supplierID, connectionID);
                SupplierMessage response = (SupplierMessage)SendMessage(args, msg);

                if (response.baseProt == Message.BaseProtocol.TRANSFER) {
                    HandleViewJobs(args, response);
                }

                response = null; // gc

                break;
            }

            case VIEW_JOB: {
                int res = HandleViewJobs(args, msg);
                if (res == -1) {
                    return res;
                }
                break;
            }

         
            default: {

            }
            break;
        }

        msg = null; // gc

        return 0;
    }

    public int run(String[] args) {

        int retVal = ViewController(args);

        System.gc();

        return retVal;
    }

    public Message SendMessage(String[] args, Message msg) {
        try {
            InetAddress addr;
            Socket sock;
            String host = "";
            int port = 0;
            if (args.length == 0) {
                host = "localhost";
                port = SupplierSharedData.SUPPLIER_PORT;
            } else if (args.length == 2) {
                host = args[0];
                port = Integer.decode(args[1]);
            }

            sock = new Socket(host, port);

            addr = sock.getInetAddress();
            System.out.println("Connected to " + addr);

            //PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            //BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            boolean bRunThread = true;

            boolean firstTime = true;

            ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());

            ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());

            System.out.println("STATE=0");

            Message response = null;

            try {

                oos.writeObject(msg);

                response = (Message) ois.readObject();

            } catch (Exception e) {

                e.printStackTrace();
            }

            //Message m = new Message();
            sock.close();

            oos = null;
            ois = null;
            sock = null;

            return response;

        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.out.println("Can't connect  ");
            System.out.println(e);
        }

        return null;

    }

    public static void main(String[] args) {
        String [] usernames = {"dave","mark", "john", "andy", "steve"};
        String [] passwords = {"psksks", "asdas", "asdff", "fkjdkd", "mkfdmkd"};
        int [] customerIDs = {329293,32992392,29392932,32823,299329};
        int randChoice = (int)(Math.random()*5);
        
        //BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String username = usernames[randChoice];
        String password = passwords[randChoice];
        int customerID = customerIDs[randChoice];
        

        SupplierClient c = new SupplierClient();
        c.username = username;
        c.password = password;
        c.supplierID = customerID;
        System.out.println("username: " + username + ", password: " + password);
                                        
        System.out.println("Connection ID: " + c.connectionID + ", SupplierID: " + c.supplierID);
                                   
      
        Boolean bRunning = true;
        while (bRunning) {
            if( c.run(args) == -1)
                break;
            try {
                sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

