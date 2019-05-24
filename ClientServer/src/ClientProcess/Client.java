/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientProcess;

import BankService.BankMessage;
import Common.CustomerMessage;
import Common.CustomerSharedData;
import Common.Message;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.*;
import java.net.*;

import Common.Order;
import Common.Product;
import Common.Supplier;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CHUWI
 */
public class Client {

    public enum CustomerStates {
        CONNECT, REGISTER, LOGOUT, SUPPLIER_LIST, SUPPLIER_INFO, ADD_TO_CART,
        SHOPPING_CART, SETTINGS, PLACE_ORDER, WAIT_FOR_ORDER,
    }

    public CustomerStates STATE = CustomerStates.CONNECT;

    int customerID;
    int connectionID;
    String username;
    String password;

    Boolean loggedIn = false;
    int state;

    int numSuppliersCounter = 0;
    int numSuppliers = 0;

    ArrayList<Order> shoppingCart = new ArrayList();

    ArrayList<String> currentProducts = new ArrayList();

    ArrayList<Product> suppInfoProducts = null;

    int currentSupplierID = 0;

    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

    public Client() {

        customerID = (int) (Math.random() * 100000);
    }

    public void HandleLoginResponse(Message response) {
        switch (response.messageType) {
            case Message.MessageType.ACCEPT_LOGIN: {

                this.loggedIn = true;
                this.connectionID = response.connectionID;

                STATE = CustomerStates.SUPPLIER_LIST;
                break;
            }
            case Message.MessageType.REJECT_LOGIN: {

                STATE = CustomerStates.REGISTER;

                // create register message
                break;
            }
        }
    }

    public void HandleSupplierList(Message response) {
        if (response.messageType == Message.MessageType.SUPPLIER_LIST) {
            ArrayList<String> suppList = ((Message.SupplierList) response.msg).suppList;
            numSuppliers = suppList.size();
            for (int i = 0; i < numSuppliers; i++) {
                System.out.println((i + 1) + ": " + suppList.get(i));
            }

            suppList = null; // store this
        }
        STATE = CustomerStates.SUPPLIER_INFO;
    }

    public void HandleSupplierInfo(Message response) {

        if (response.messageType == Message.MessageType.SUPPLIER_INFO) {

            System.out.println("");
            System.out.println("Recieved Supplier Info!");
            Message.SupplierInfo suppInf = (Message.SupplierInfo) response.msg;
            System.out.println("Supplier Name: " + suppInf.name);
            System.out.println("Product List: ");

            // this should not be public
            // an example where it could matter
            currentSupplierID = suppInf.supplierID;

            for (int i = 0; i < suppInf.products.size(); i++) {
                Product prod = suppInf.products.get(i);
                System.out.println((i + 1) + ": " + prod.name + ", " + prod.price);
            }

            suppInfoProducts = suppInf.products;

            suppInf = null;
            STATE = CustomerStates.ADD_TO_CART;

        } else {

            System.out.println("");

            for (int i = 0; i < suppInfoProducts.size(); i++) {
                Product prod = suppInfoProducts.get(i);
                System.out.println((i + 1) + ": " + prod.name + ", " + prod.price);
            }

        }
    }

    public class UseInputResponse {

        public int val = 101;
        public UserResponses ret;

    }

    public enum UserResponses {
        QUIT, BACK, PLACE_ORDER, NUMBER, SKIP, YES, NO
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

                userInputResponse.ret = UserResponses.QUIT;
            } else if (userInput.equalsIgnoreCase("b")) {

                userInputResponse.ret = UserResponses.BACK;

            } else if (userInput.equalsIgnoreCase("p")) {

                userInputResponse.ret = UserResponses.PLACE_ORDER;

            } else if (userInput.equalsIgnoreCase("y")) {

                userInputResponse.ret = UserResponses.YES;

            } else if (userInput.equalsIgnoreCase("n")) {

                userInputResponse.ret = UserResponses.NO;

            } else {
                userInputResponse.val = Integer.parseInt(userInput);

                if (userInputResponse.val > maxVal
                        || userInputResponse.val < minVal) {

                    System.out.println("number out of range.");
                    userInputResponse.ret = UserResponses.SKIP;

                } else {

                    userInputResponse.ret = UserResponses.NUMBER;
                }
            }
        }
        return userInputResponse;
    }

    public int HandleSupplierInfo(String[] args, CustomerMessage msg) {
        System.out.println("");
        System.out.println("Enter the number to recieve Supplier Info");
        System.out.println("(q) to quit");

        UseInputResponse R = GetInput(1, numSuppliers);

        if (R.ret == UserResponses.QUIT) {
            msg = null;
            return -1;
        }

        if (R.ret == UserResponses.NUMBER) {
            msg.SupplierInfoRequest(R.val, customerID, connectionID);

            Message response = SendMessage(args, msg);

            if (response.baseProt == Message.BaseProtocol.TRANSFER) {

                HandleSupplierInfo(response);
            }

            response = null; // gc         
        }

        msg = null;

        return 0;
    }

    public int HandleAddToCart(String[] args, CustomerMessage msg) {
        System.out.println("");
        System.out.println("Enter the item to add to cart");
        System.out.println("(p) for place order, (q) to quit, (b) for back");

        UseInputResponse R = GetInput(1, suppInfoProducts.size());

        if (R.ret == UserResponses.QUIT) {

            msg = null;
            return -1;

        } else if (R.ret == UserResponses.BACK) {

            msg = null;
            STATE = CustomerStates.SUPPLIER_LIST;
            return 0;

        } else if (R.ret == UserResponses.PLACE_ORDER) {

            msg = null;
            STATE = CustomerStates.PLACE_ORDER;
            return 0;

        } else if (R.ret == UserResponses.NUMBER) {

            Order o = null;

            for (int i = 0; i < shoppingCart.size(); i++) {
                if (shoppingCart.get(i).supplierID == currentSupplierID) {
                    o = shoppingCart.get(i);
                }
            }

            if (o == null) {
                o = new Order();
                o.supplierID = currentSupplierID;
                o.customerID = this.customerID;

                shoppingCart.add(o);
            }

            System.out.println("Added product: " + R.val);

            o.AddProduct(suppInfoProducts.get(R.val - 1));
        }

        msg = null;

        return 0;
    }

    public void PrintOrder(Order o, int orderID) {

        System.out.println("Order Number: " + orderID + ", " + o.supplierID);
        float totalPrice = 0.0f;

        for (int j = 0; j < o.products.size(); j++) {

            Product p = o.products.get(j);
            System.out.println("Product: " + p.name + ", Price: " + p.price);
            totalPrice += p.price;

        }
        System.out.println("Total Price: " + totalPrice);
        System.out.println("");
    }

    public int HandlePlaceOrder(String[] args, CustomerMessage msg) {

        System.out.println("");

        if (shoppingCart.size() > 1) {

            System.out.println("Printing Order List");

            for (int i = 0; i < shoppingCart.size(); i++) {
                Order o = shoppingCart.get(i);
                PrintOrder(o, i + 1);
            }

            System.out.println("");
            System.out.println("Choose which order to place ");
            System.out.println("(q) to quit, (b) for back");

            UseInputResponse R = GetInput(1, shoppingCart.size() + 1);

            if (R.ret == UserResponses.QUIT) {

                msg = null;
                return -1;

            } else if (R.ret == UserResponses.NUMBER) {

                Order o2 = shoppingCart.remove(R.val - 1);

                /*int nonce = 0;
                {
                    CustomerMessage cmsg = new CustomerMessage();
                    cmsg.TokenRequest(customerID, connectionID);
                    Message res = SendMessage(args, cmsg);
                    if (res.messageType == CustomerMessage.CustomerMessageType.TOKEN) {
                        nonce = Integer.parseInt(BankMessage.MessageBank("CUSTOMER_NONCE_REQUEST",
                                ((CustomerMessage.Token) res.msg).token));

                    }

                }

                o2.transactionRecord.transactionNumber = nonce;*/

                msg.PlaceOrder(o2, customerID, connectionID);

                Message response = SendMessage(args, msg);

                if (response.baseProt == Message.BaseProtocol.ACKNOWLEDGE) {

                    System.out.println("Order Placed!");
                    STATE = CustomerStates.SUPPLIER_LIST;
                }
                response = null; // gc                     

            } else if (R.ret == UserResponses.BACK) {

                STATE = CustomerStates.SUPPLIER_INFO;
            }

        } else {

            if (shoppingCart.size() != 0) {
                Order o = shoppingCart.get(0);

                System.out.println("Place Order ...");
                PrintOrder(o, 1);

                System.out.println("Place Order Yes (y) or No (no) ");
                System.out.println("(q) to quit, (b) for back");

                UseInputResponse R = GetInput(1, suppInfoProducts.size());

                if (R.ret == UserResponses.QUIT) {

                    msg = null;
                    return -1;

                } else if (R.ret == UserResponses.YES) {

                    /*int nonce = 0;
                    {
                        CustomerMessage cmsg = new CustomerMessage();
                        cmsg.TokenRequest(customerID, connectionID);
                        Message res = SendMessage(args, cmsg);
                        if (res.messageType == CustomerMessage.CustomerMessageType.TOKEN) {
                            System.out.println("Recieved Token");
                            nonce = Integer.parseInt(BankMessage.MessageBank("CUSTOMER_NONCE_REQUEST",
                                    ((CustomerMessage.Token) res.msg).token));

                            System.out.println("Recieved Token");

                        }

                    }
                    o.transactionRecord.transactionNumber = nonce;*/

                    msg.PlaceOrder(o, customerID, connectionID);

                    Message response = SendMessage(args, msg);

                    if (response.baseProt == Message.BaseProtocol.ACKNOWLEDGE) {

                        //HandleSupplierInfo(response);
                        System.out.println("Order Placed!");
                        STATE = CustomerStates.SUPPLIER_LIST;

                    } else {

                        System.out.println("Something went wrong");
                        STATE = CustomerStates.SUPPLIER_INFO;
                    }
                    response = null; // gc  

                } else if (R.ret == UserResponses.NO) {

                    System.out.println("Not placing order ... returning to supplier list");
                    STATE = CustomerStates.SUPPLIER_LIST;

                    msg = null;

                } else if (R.ret == UserResponses.BACK) {

                    STATE = CustomerStates.SUPPLIER_INFO;

                    msg = null;
                }

            }
        }

        return 0;
    }

    public int ViewController(String[] args) {
        CustomerMessage msg = new CustomerMessage();

        switch (STATE) {
            case CONNECT: {
                msg.LoginMessage(username, password, customerID, connectionID);

                Message response = SendMessage(args, msg);

                if (response.baseProt == Message.BaseProtocol.YES_NO) {
                    HandleLoginResponse(response);
                }

                response = null; // gc

                break;
            }
            case SUPPLIER_LIST: {
                System.out.println("customerID, connectionID: " + customerID + ", " + connectionID);
                msg.SupplierListRequest(customerID, connectionID);
                Message response = SendMessage(args, msg);

                if (response.baseProt == Message.BaseProtocol.TRANSFER) {
                    HandleSupplierList(response);
                }

                response = null; // gc

                break;
            }
            case SUPPLIER_INFO: {
                int res = HandleSupplierInfo(args, msg);
                if (res == -1) {
                    return res;
                }
                break;
            }

            case ADD_TO_CART: {
                int res = HandleAddToCart(args, msg);
                if (res == -1) {
                    return res;
                }

                break;
            }
            case PLACE_ORDER: {

                int res = HandlePlaceOrder(args, msg);
                if (res == -1) {
                    return res;
                }
                break;
            }
            case SHOPPING_CART: {

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
                port = CustomerSharedData.CUSTOMER_PORT;
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

        Client c = new Client();
        c.username = username;
        c.password = password;
        c.customerID = customerID;
        System.out.println("username: " + username + ", password: " + password);

        System.out.println("Connection ID: " + c.connectionID + ", CustomerID: " + c.customerID);

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


/*           switch (STATE) {
                case 0: {
                    out.println("LOGIN");
                    out.println(username);
                    out.println(password);
                    out.println(this.customerID);
                    if (in.readLine().equals("ACCEPT")) {
                        this.loggedIn = true;
                        try {
                            this.connectionID = (int) Integer.parseInt(in.readLine());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        STATE = 1;

                    }
                    break;
                }
                case 1: {
                    currentProducts.clear();
                    out.println(this.connectionID);

                    System.out.println("Enter (1) to view supplier list");

                    String userInput;
                    if ((userInput = stdIn.readLine()) != null) {

                        if (userInput.equalsIgnoreCase("q")) {
                            sock.close();
                            return -1;
                        } else if (userInput.equalsIgnoreCase("1")) {
                            out.println("SUPPLIER_LIST");
                        } else if (userInput.equalsIgnoreCase("2")) {
                            out.println("SUPPLIER_INFO");
                        } else if (userInput.equalsIgnoreCase("3")) {
                            out.println("PLACE_ORDER");

                        }

                    }

                    String response = in.readLine();

                    if (response == null || response.equalsIgnoreCase("Server Response: Server has already stopped")) {
                        bRunThread = false;
                    } else if (response.equalsIgnoreCase("SUPPLIER_LIST")) {
                        System.out.println("");
                        System.out.println(">> Printing List ...");
                        int loopSize = (int) Integer.parseInt(in.readLine());
                        int i = 0;
                        while (i < loopSize) {
                            System.out.println(in.readLine());
                            i++;
                        }
                        System.out.println("");
                        System.out.println("");
                        STATE = 2;
                        numSuppliersCounter = i;
                        break;
                    } else {
                        System.out.println("Server Response: " + response);
                    }
                }

                

                case 2: {
                    currentProducts.clear();
                    o.supplierID = -1;
                    System.out.println("Enter the number to recieve Supplier Info");
                    System.out.println("(q) to quit or (b) for back");
                    String userInput;
                    if ((userInput = stdIn.readLine()) != null) {
                        int val = 0;
                        if (userInput.equalsIgnoreCase("q")) {
                            sock.close();
                            return -1;
                        } else if (userInput.equalsIgnoreCase("b")) {
                            STATE = 1;
                            break;
                        } else {
                            val = Integer.parseInt(userInput);
                            
                        }
                        
                        out.println(this.connectionID); // should not happen if we break with b
                        out.println("SUPPLIER_DETAILS");
                        if (val > 0 && val < this.numSuppliersCounter) {
                            out.println(val);
                            
                        }
                    }
                    String response = in.readLine();

                    if (response == null || response.equalsIgnoreCase("Server Response: Server has already stopped")) {
                        bRunThread = false;
                    } else if (response.equalsIgnoreCase("SUPPLIER_DETAILS")) {
                       
                        System.out.println(in.readLine());
                        
                        o.supplierID = Integer.parseInt(in.readLine());
                        System.out.println(o.supplierID);
                        
                        System.out.println("  >> Printing Product List ...");
                        int loopSize = (int) Integer.parseInt(in.readLine());
                        int i = 0;
                        while (i < loopSize) {
                            String prod = in.readLine();
                            System.out.println("    " + prod);
                            currentProducts.add(prod);
                            i++;
                        }
                        System.out.println("");
                        System.out.println("");
                        STATE = 3;
                        numSuppliersCounter = i;
                    } else {
                        System.out.println("Server Response: " + response);
                    }



                    break;
                }
                case 3:
                {
                    System.out.println("Enter the number to add to cart");
                    System.out.println("(q) to quit, (b) for back, (c) to view cart ");
 
                    String userInput;
                    if ((userInput = stdIn.readLine()) != null) {
                        int val = 0;
                        if (userInput.equalsIgnoreCase("q")) {
                            sock.close();
                            return -1;
                        } else if (userInput.equalsIgnoreCase("b")) {
                            
                            STATE = 1;
                            break;
                        } else if (userInput.equalsIgnoreCase("c")) {
                            System.out.println(o.productsString);
                            STATE = 4;
                            break;
                        } else {
                            val = Integer.parseInt(userInput);
                            
                        }
                        
  
                        o.customerID = this.customerID;
                        o.productsString +=currentProducts.get(val) + ", ";
                        
                    }                   
                    break;
                }
                case 4:
                {
                    
                    System.out.println("(1) to view place order");
                    System.out.println(" (q) to quit, (b) for back, (c) to empty cart ");
                              String userInput;
                    if ((userInput = stdIn.readLine()) != null) {
                        int val = 0;
                        if (userInput.equalsIgnoreCase("q")) {
                            sock.close();
                            return -1;
                        } else if (userInput.equalsIgnoreCase("b")) {
                            STATE = 1;
                            break;
                        } else if (userInput.equalsIgnoreCase("c")) {
                            STATE = 4;
                            break;
                        } else {
                            val = Integer.parseInt(userInput);
                            
                        }
                        
                        if( val == 1){
                             out.println(this.connectionID); // should not happen if we break with b
                            out.println("PLACE_ORDER");
                            
                                out.println(o.customerID);
                                out.println(o.supplierID);
                                out.println(o.productsString);
                                out.println( 1);
                                out.println( 8);
                                out.println( 16);
                                out.println( 15);
                                
                                out.println("DELIVER_TO_HOME");
                                                 
                        }
                    }     
                }

                default:
                    break;
            }
 */
