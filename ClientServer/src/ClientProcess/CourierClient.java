/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientProcess;

import Common.CourierSharedData;
import Common.CourierMessage;

import Common.Message;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.*;
import java.net.*;

import Common.Order;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CHUWI
 */
public class CourierClient {

    public enum CourierStates {
        CONNECT, REGISTER, LOGOUT, REQUEST_JOBS, VIEW_JOB, TRAVEL_TO_SUPPLIER,
        ARRIVE_AT_SUPPLIER, COLLECT_ORDER, TRAVEL_TO_CUSTOMER, ARRIVE_AT_CUSTOMER,
        DELIVER_ORDER
    }

    public CourierStates STATE = CourierStates.CONNECT;

    int courierID;
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

    public CourierClient() {

        courierID = (int) (Math.random() * 100000);
    }

    public void HandleLoginResponse(Message response) {
        switch (response.messageType) {
            case Message.MessageType.ACCEPT_LOGIN: {

                this.loggedIn = true;
                this.connectionID = response.connectionID;

                STATE = CourierStates.REQUEST_JOBS;
                break;
            }
            case Message.MessageType.REJECT_LOGIN: {

                STATE = CourierStates.REGISTER;

                // create register message
                break;
            }
        }
    }

    public int HandleViewJobs(String[] args, CourierMessage msg) {
        msg.CourierRequestJobOffer(courierID, connectionID);

        Message response = SendMessage(args, msg);

        if (response.baseProt == Message.BaseProtocol.TRANSFER) {

            if (response.messageType == CourierMessage.CourierMessageType.COURIER_JOB_OFFER) {

                ArrayList<Order> orderList = ((CourierMessage.JobOfferToCourier) response.msg).jobsTodo;
                numJobs = orderList.size();
                for (int i = 0; i < numJobs; i++) {

                    // pretend like they're sorted by travel time
                    System.out.println((i + 1) + ": " + orderList.get(i).jobID);
                    jobID_List.put((i + 1), orderList.get(i).jobID);
                }

                orderList = null; // store this

            }
        }
        STATE = CourierStates.TRAVEL_TO_SUPPLIER;
        response = null; // gc
        return 0;
    }

    public class UseInputResponse {

        public int val = 101;
        public int ret;

    }

    public UseInputResponse GetInput(int minVal, int maxVal) {
        String userInput = null;

        try {
            userInput = stdIn.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }

        UseInputResponse userInputResponse = new UseInputResponse();

        if (userInput != null) {
            if (userInput.equalsIgnoreCase("q")) {

                userInputResponse.ret = -1;

            } else {
                userInputResponse.val = Integer.parseInt(userInput);

                if (userInputResponse.val > maxVal
                        || userInputResponse.val < minVal) {

                    System.out.println("number out of range.");
                    userInputResponse.ret = 0;

                } else {

                    userInputResponse.ret = 1;
                }
            }
        }
        return userInputResponse;
    }

    public int HandleTravelToSupplier(String[] args, CourierMessage msg) {

        System.out.println("Enter the Job you want to Deliver");
        System.out.println("(q) to quit");

        UseInputResponse R = GetInput(1, numJobs);

        if (R.ret == -1) {
            msg = null;
            return -1;
        }

        if (R.ret == 1) {

            int jobID = jobID_List.get(R.val);

            msg.CourierAcceptJob(jobID, courierID, connectionID);

            Message response = SendMessage(args, msg);

            if (response.baseProt == Message.BaseProtocol.ACKNOWLEDGE) {

                //HandleSupplierInfo(response);
            }
            response = null; // gc            

        }

        msg = null;

        STATE = CourierStates.ARRIVE_AT_SUPPLIER;
        return 0;
    }

    public int HandleArriveAtSupplier(String[] args, CourierMessage msg) {

        msg.CourierArriveAtSupplier(currentJobID, courierID, connectionID);

        Message response = SendMessage(args, msg);

        if (response.baseProt == Message.BaseProtocol.ACKNOWLEDGE) {

            STATE = CourierStates.COLLECT_ORDER;
        }
        response = null; // gc        */        
        return 0;
    }

    public int HandleCollectOrder(String[] args, CourierMessage msg) {
        try {
            sleep((int) (Math.random() * 1000));
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            //Logger.getLogger(CourierClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        msg.CourierCollectOrder(currentJobID, courierID, connectionID);

        CourierMessage response = (CourierMessage)SendMessage(args, msg);

        if (response.baseProt == Message.BaseProtocol.ACKNOWLEDGE) {

            STATE = CourierStates.TRAVEL_TO_CUSTOMER;
        }
        response = null; // gc        */             
        return 0;
    }

    public int HandleTravelToCustomer(String[] args, Message msg) {
        try {
            sleep((int) (Math.random() * 1000));
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            //Logger.getLogger(CourierClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*msg.CourierCollectOrder(currentJobID, courierID, connectionID);
        
        Message response = SendMessage(args, msg);

        if (response.baseProt == Message.BaseProtocol.ACKNOWLEDGE) {

            STATE = CourierStates.TRAVEL_TO_CUSTOMER;
        }
        response = null; // gc        */
        STATE = CourierStates.ARRIVE_AT_CUSTOMER;

        return 0;
    }

    public int HandleArriveAtCustomer(String[] args, CourierMessage msg) {
        msg.CourierArriveAtCustomer(currentJobID, courierID, connectionID);

        Message response = SendMessage(args, msg);

        if (response.baseProt == Message.BaseProtocol.ACKNOWLEDGE) {

            STATE = CourierStates.DELIVER_ORDER;
        }
        response = null; // gc        */         

        return 0;
    }

    public int HandleDeliverOrder(String[] args, CourierMessage msg) {
        try {
            sleep((int) (Math.random() * 100));
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            //Logger.getLogger(CourierClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        msg.CourierDeliverOrder(currentJobID, courierID, connectionID);

        Message response = SendMessage(args, msg);

        if (response.baseProt == Message.BaseProtocol.ACKNOWLEDGE) {

            STATE = CourierStates.DELIVER_ORDER;
        }
        response = null; // gc        */         
        return 0;
    }

    public void HandleSupplierList(Message response) {

        STATE = CourierStates.VIEW_JOB;
    }

    public void HandleSupplierInfo(Message response) {
        if (response.messageType == Message.MessageType.SUPPLIER_INFO) {
            System.out.println("Recieved Supplier Info!");
            Message.SupplierInfo suppInf = (Message.SupplierInfo) response.msg;
            System.out.println("Supplier Name: " + suppInf.name);
            System.out.println("Product List: ");
            // this should not be public
            // an example where it could matter
            currentSupplierID = suppInf.supplierID;

            for (int i = 0; i < suppInf.products.size(); i++) {
//                Product prod = suppInf.products.get(i);
//                System.out.println((i + 1) + ": " + prod.name + ", " + prod.price);
            }

            //suppInfoProducts = suppInf.products;
            suppInf = null;
            STATE = CourierStates.ARRIVE_AT_CUSTOMER;
        }
    }

    public int ViewController(String[] args) {
        CourierMessage msg = new CourierMessage();

        switch (STATE) {
            case CONNECT: {
                msg.LoginMessage(username, password, courierID, connectionID);

                Message response = SendMessage(args, msg);

                if (response.baseProt == Message.BaseProtocol.YES_NO) {
                    HandleLoginResponse(response);
                }

                response = null; // gc

                break;
            }
            case REQUEST_JOBS: {
                System.out.println("courierID, connectionID: " + courierID + ", " + connectionID);
                msg.CourierRequestJobOffer(courierID, connectionID);
                Message response = SendMessage(args, msg);

                if (response.baseProt == Message.BaseProtocol.TRANSFER) {
                    HandleSupplierList(response);
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

            case TRAVEL_TO_SUPPLIER: {
                int res = HandleTravelToSupplier(args, msg);
                if (res == -1) {
                    return res;
                }

                break;
            }
            case ARRIVE_AT_SUPPLIER: {

                int res = HandleArriveAtSupplier(args, msg);
                if (res == -1) {
                    return res;
                }
                break;
            }
            case COLLECT_ORDER: {

                int res = HandleCollectOrder(args, msg);
                if (res == -1) {
                    return res;
                }
                break;
            }

            case TRAVEL_TO_CUSTOMER: {
                int res = HandleTravelToCustomer(args, msg);
                if (res == -1) {
                    return res;
                }
            }
            break;

            case ARRIVE_AT_CUSTOMER: {
                int res = HandleArriveAtCustomer(args, msg);
                if (res == -1) {
                    return res;
                }
            }
            break;
            case DELIVER_ORDER: {

                int res = HandleDeliverOrder(args, msg);
                if (res == -1) {
                    return res;
                }
            }
            break;
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
                port = CourierSharedData.COURIER_PORT;
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
        String[] usernames = {"dave", "mark", "john", "andy", "steve"};
        String[] passwords = {"psksks", "asdas", "asdff", "fkjdkd", "mkfdmkd"};
        int[] customerIDs = {329293, 32992392, 29392932, 32823, 299329};
        int randChoice = (int) (Math.random() * 5);

        String username = usernames[randChoice];
        String password = passwords[randChoice];
        int customerID = customerIDs[randChoice];

        CourierClient c = new CourierClient();
        c.username = username;
        c.password = password;
        c.courierID = customerID;
        System.out.println("username: " + username + ", password: " + password);

        System.out.println("Connection ID: " + c.connectionID + ", CustomerID: " + c.courierID);

        Boolean bRunning = true;
        while (bRunning) {
            if (c.run(args) == -1) {
                break;
            }
            try {
                sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
