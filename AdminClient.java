/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientProcess;

import Common.Admin;
import Common.AdminMessage;
import Common.AdminSharedData;

import Common.JobInfo;
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

/**
 *
 * @author CHUWI
 */
public class AdminClient {

    public enum AdminStates {
        OPTIONS, LOGIN, VIEW_CUSTOMERS, CUSTOMER_INFO, SUPPLIER_LIST, SUPPLIER_INFO,
        VIEW_COURIERS, COURIER_INFO, VIEW_ORDERS, VIEW_JOBS, ACTIVE_JOBS, COMPLETED_JOBS,
        ACTIVE_JOB_INFO, COMPLETED_JOB_INFO,
    }

    public AdminStates STATE = AdminStates.LOGIN;

    int adminID;
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

    public AdminClient() {

        adminID = 0;//(int) (Math.random() * 100000);
    }

    public void HandleLoginResponse(Message response) {
        switch (response.messageType) {
            case Message.MessageType.ACCEPT_LOGIN: {

                this.loggedIn = true;
                this.connectionID = response.connectionID;

                STATE = AdminStates.OPTIONS;
                break;
            }
            case Message.MessageType.REJECT_LOGIN: {

                //STATE = AdminStates.REGISTER;
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
        STATE = AdminStates.SUPPLIER_INFO;
    }

    public void HandleCourierList(Message response) {
        if (response.messageType == AdminMessage.AdminMessageType.COURIER_LIST) {
            ArrayList<String> courList = ((AdminMessage.CourierList) response.msg).suppList;
            numSuppliers = courList.size();
            for (int i = 0; i < numSuppliers; i++) {
                System.out.println((i + 1) + ": " + courList.get(i));
            }

            courList = null; // store this
        }
        STATE = AdminStates.OPTIONS;
    }

    public void HandleCustomerList(Message response) {
        if (response.messageType == AdminMessage.AdminMessageType.CUSTOMER_LIST) {
            ArrayList<String> custList = ((AdminMessage.CustomerList) response.msg).custList;
            numSuppliers = custList.size();
            for (int i = 0; i < numSuppliers; i++) {
                System.out.println((i + 1) + ": " + custList.get(i));
            }

            custList = null; // store this
        }
        STATE = AdminStates.OPTIONS;
    }

    public void HandleJobList(Message response) {
        if (response.messageType == AdminMessage.AdminMessageType.JOB_LIST) {
            ArrayList<Order> jobList = ((AdminMessage.JobList) response.msg).j2do;
            numSuppliers = jobList.size();
            for (int i = 0; i < numSuppliers; i++) {
                //System.out.println((i + 1) + ": " + custList.get(i));
                Order o = jobList.get(i);
                System.out.println("JobID: " + o.jobID);
            }

            jobList = null; // store this
        }
        STATE = AdminStates.OPTIONS;
    }

    public void HandleActiveJobs(Message response) {
        if (response.messageType == AdminMessage.AdminMessageType.ACTIVE_JOB_LIST) {
            ArrayList<JobInfo> jobList = ((AdminMessage.ActiveJobList) response.msg).j2do;
            numSuppliers = jobList.size();
            for (int i = 0; i < numSuppliers; i++) {
                //System.out.println((i + 1) + ": " + custList.get(i));
                JobInfo jI = jobList.get(i);
                System.out.print("JobID: " + i);
                System.out.print(", courierID: " + jI.courierID);
                System.out.print(", customerID: " + jI.customerID);
                System.out.print(", supplierID: " + jI.supplierID + "\n");
            }

            jobList = null; // store this
        }
        STATE = AdminStates.OPTIONS;
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
                Product prod = suppInf.products.get(i);
                System.out.println((i + 1) + ": " + prod.name + ", " + prod.price);
            }

            suppInfoProducts = suppInf.products;

            suppInf = null;
            STATE = AdminStates.OPTIONS;
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

    public int HandleSupplierInfo(String[] args, Message msg) {
        System.out.println("Enter the number to recieve Supplier Info");
        System.out.println("(q) to quit");

        UseInputResponse R = GetInput(1, numSuppliers);

        if (R.ret == UserResponses.QUIT) {
            msg = null;

            return -1;

        } else if (R.ret == UserResponses.NUMBER) {

            msg.SupplierInfoRequest(R.val, adminID, connectionID);

            Message response = SendMessage(args, msg);

            if (response.baseProt == Message.BaseProtocol.TRANSFER) {

                HandleSupplierInfo(response);
            }
            response = null; // gc           
        }

        return 0;
    }

    public void HandleCourierInfo(Message response) {
        if (response.messageType == AdminMessage.AdminMessageType.COURIER_INFO) {
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
            STATE = AdminStates.OPTIONS;
        }
    }

    public int HandleCourierInfo(String[] args, AdminMessage msg) {
        System.out.println("Enter the number to recieve Courier Info");
        System.out.println("(q) to quit");

        UseInputResponse R = GetInput(1, numSuppliers);

        if (R.ret == UserResponses.QUIT) {
            msg = null;

            return -1;

        } else if (R.ret == UserResponses.NUMBER) {

            msg.CourierInfoRequest(R.val, adminID, connectionID);

            Message response = SendMessage(args, msg);

            if (response.baseProt == Message.BaseProtocol.TRANSFER) {

                HandleSupplierInfo(response);
            }
            response = null; // gc          

        }

        return 0;
    }

    public void HandleCustomerInfo(AdminMessage response) {
        if (response.messageType == AdminMessage.AdminMessageType.CUSTOMER_INFO) {
            System.out.println("Recieved Supplier Info!");
            AdminMessage.CustomerInfo suppInf = (AdminMessage.CustomerInfo) response.msg;
            System.out.println("Supplier Name: " + suppInf.name);
            //System.out.println("Product List: ");
            // this should not be public
            // an example where it could matter
            //currentSupplierID = suppInf.supplierID;

            /*for (int i = 0; i < suppInf.products.size(); i++) {
                Product prod = suppInf.products.get(i);
                System.out.println((i + 1) + ": " + prod.name + ", " + prod.price);
            }*/
            suppInfoProducts = suppInf.products;

            suppInf = null;
            STATE = AdminStates.SUPPLIER_LIST;
        }
    }

    public int HandleCustomerInfo(String[] args, AdminMessage msg) {
        System.out.println("Enter the number to recieve Customer Info");
        System.out.println("(q) to quit");

        UseInputResponse R = GetInput(1, numSuppliers);

        if (R.ret == UserResponses.QUIT) {
            msg = null;

            return -1;

        } else if (R.ret == UserResponses.NUMBER) {

            msg.CustomerInfoRequest(R.val, adminID, connectionID);

            AdminMessage response = (AdminMessage) SendMessage(args, msg);

            if (response.baseProt == Message.BaseProtocol.TRANSFER) {

                HandleCustomerInfo(response);
            }
            response = null; // gc       

        }

        return 0;
    }

    public void HandleJobInfo(Message response) {
        if (response.messageType == AdminMessage.AdminMessageType.JOB_INFO) {
            System.out.println("Recieved Supplier Info!");
            AdminMessage.JobInfoMessage suppInf = (AdminMessage.JobInfoMessage) response.msg;
            System.out.println("Supplier Name: " + suppInf.name);
            System.out.println("Product List: ");
            // this should not be public
            // an example where it could matter
            //currentSupplierID = suppInf.supplierID;

            /*for (int i = 0; i < suppInf.products.size(); i++) {
                Product prod = suppInf.products.get(i);
                System.out.println((i + 1) + ": " + prod.name + ", " + prod.price);
            }*/
            //suppInfoProducts = suppInf.products;
            suppInf = null;
            STATE = AdminStates.OPTIONS;
        }
    }

    public int HandleJobInfo(String[] args, AdminMessage msg) {
        System.out.println("Enter the number to recieve Supplier Info");
        System.out.println("(q) to quit");

        UseInputResponse R = GetInput(1, numSuppliers);

        if (R.ret == UserResponses.QUIT) {
            msg = null;

            return -1;

        } else if (R.ret == UserResponses.NUMBER) {

            msg.JobInfoRequest(R.val, adminID, connectionID);

            Message response = SendMessage(args, msg);

            if (response.baseProt == Message.BaseProtocol.TRANSFER) {

                HandleJobInfo(response);
            }
            response = null; // gc   

        }

        return 0;
    }

    public int HandleOptions(String[] args, Message msg) {
        System.out.println("Option 1: Supplier List");
        System.out.println("Option 2: View Customers");
        System.out.println("Option 3: View Couriers");
        System.out.println("Option 4: View Jobs");
        System.out.println("Option 5: View Active Jobs");
        System.out.println("Enter the number for the Option");
        System.out.println("(q) to quit");

        UseInputResponse R = GetInput(1, 5);

        if (R.ret == UserResponses.QUIT) {
            msg = null;

            return -1;

        } else if (R.ret == UserResponses.NUMBER) {

            if (R.val == 1) {
                STATE = AdminStates.SUPPLIER_LIST;
            }
            if (R.val == 2) {
                STATE = AdminStates.VIEW_CUSTOMERS;
            }
            if (R.val == 3) {
                STATE = AdminStates.VIEW_COURIERS;
            }
            if (R.val == 4) {
                STATE = AdminStates.VIEW_JOBS;
            }
            if (R.val == 5) {
                STATE = AdminStates.ACTIVE_JOBS;
            }

        }

        return 0;

    }

    public int ViewController(String[] args) {
        AdminMessage msg = new AdminMessage();

        switch (STATE) {
            case LOGIN: {
                msg.LoginMessage(username, password, adminID, connectionID);

                Message response = SendMessage(args, msg);

                if (response.baseProt == Message.BaseProtocol.YES_NO) {
                    HandleLoginResponse(response);
                }

                response = null; // gc

                break;
            }
            case SUPPLIER_LIST: {
                System.out.println("adminID, connectionID: " + adminID + ", " + connectionID);
                msg.SupplierListRequest(adminID, connectionID);
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
            case OPTIONS: {
                int res = this.HandleOptions(args, msg);
                if (res == -1) {
                    return res;
                }
                break;
            }

            case VIEW_CUSTOMERS: {
                System.out.println("adminID, connectionID: " + adminID + ", " + connectionID);
                msg.CustomerListRequest(adminID, connectionID);
                Message response = SendMessage(args, msg);

                if (response.baseProt == Message.BaseProtocol.TRANSFER) {
                    HandleCustomerList(response);
                }

                response = null; // gc

                break;
            }
            case CUSTOMER_INFO: {
                int res = HandleSupplierInfo(args, msg);
                if (res == -1) {
                    return res;
                }
                break;
            }

            case VIEW_COURIERS: {
                System.out.println("adminID, connectionID: " + adminID + ", " + connectionID);
                msg.CourierListRequest(adminID, connectionID);
                Message response = SendMessage(args, msg);

                if (response.baseProt == Message.BaseProtocol.TRANSFER) {
                    HandleCourierList(response);
                }

                response = null; // gc

                break;
            }
            case COURIER_INFO: {
                int res = HandleSupplierInfo(args, msg);
                if (res == -1) {
                    return res;
                }
                break;
            }

            case VIEW_JOBS: {
                System.out.println("adminID, connectionID: " + adminID + ", " + connectionID);
                msg.JobListRequest(adminID, connectionID);
                Message response = SendMessage(args, msg);

                if (response.baseProt == Message.BaseProtocol.TRANSFER) {
                    HandleJobList(response);
                }

                response = null; // gc

                break;
            }
            case ACTIVE_JOBS: {
                msg.ActiveJobListRequest(adminID, connectionID);
                Message response = SendMessage(args, msg);

                if (response.baseProt == Message.BaseProtocol.TRANSFER) {
                    HandleActiveJobs(response);
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
                port = AdminSharedData.ADMIN_PORT;
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

        int randChoice = (int) (Math.random() * 5);

        String username = "Dave";//usernames[randChoice];
        String password = "1234";//passwords[randChoice];
        int userID = 1234;

        AdminClient c = new AdminClient();
        c.username = username;
        c.password = password;
        c.adminID = userID;
        System.out.println("username: " + username + ", password: " + password);

        System.out.println("Connection ID: " + c.connectionID + ", AdminID: " + c.adminID);

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
