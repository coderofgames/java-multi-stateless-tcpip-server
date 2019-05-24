/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import BankService.BankMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CHUWI
 */
public class Customer extends User {

    public Job activeJob = null;
    public int token = 0;

    public synchronized void SetActiveJob(Job job) {
        activeJob = job;
    }

    public Customer(DataCenter dataCenter) {
        super(dataCenter);
    }

    public Customer(DataCenter dataCenter, String _username,
            String _password, int _userID) {
        super(dataCenter, _username, _password, _userID);

    }

    public UserTypes GetUserType() {
        return UserTypes.CUSTOMER;
    }

    public void HandleSupplierList(Message m, ObjectOutputStream ous) {

        //Message.SupplierListRequest msg = (Message.SupplierListRequest)m.msg;
        Message response = new Message();
        ArrayList<String> sL = new ArrayList();
        response.SupplierList(dC.supplierManager.GetActiveSupplierTopLevelInfo(sL),
                this.getUserID(), this.getConnectionID());

        try {
            ous.writeObject(response);
            System.out.println("Supplier List Message Send");
        } catch (IOException ex) {
            System.out.println("Supplier List Message failed");
            ex.printStackTrace();
            //Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        sL = null;
    }

    public void HandleSupplierDetails(Message m, ObjectOutputStream ous) {

        int loopStop = ((Message.SupplierInfoRequest) m.msg).supplierID;

        Message response = new Message();
        System.out.println("Customer requests Supplier ddetails");
        try {
            ////out.println("SUPPLIER_DETAILS");
            ////int val = Integer.parseInt(in.readLine());
            Set<Entry<Integer, User>> supplierSet = dC.supplierManager.GetEntrySet();
            Iterator it = supplierSet.iterator();
            int i = 1;
            while (it.hasNext()) {
                Entry<Integer, User> e = (Entry) it.next();
                if (loopStop == i++) {
                    System.out.println("Supplier Found");
                    Supplier s = (Supplier) e.getValue();
                    System.out.println("Supplier Name: " + s.getName());
                    ArrayList<Product> p = new ArrayList();
                    response.SupplierInfo(s.getName(), s.GetProducts(p), s.getUserID(), this.getUserID(), this.getConnectionID());

                    ous.writeObject(response);
                    p = null;
                    break;
                }
                //out.println(i++ + ". " + ((Supplier) e.getValue()).getName() + ", ");
            }
            //ous.writeObject(dC.GetOnlineSupplier(supplierID));

            //out.write(s.products);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response = null;
    }

    public void HandlePlaceOrder(Message m, ObjectOutputStream ous) {

        System.out.println("Customer requests Place ORder");

        CustomerMessage.PlaceOrder pO = (CustomerMessage.PlaceOrder) m.msg;

        if (dC.PlaceOrder(pO.order, this) == false) {

            System.out.println("Placing order failed");
        } else {
            Message response = new Message();
            response.GeneralAck(this.getUserID(), this.getConnectionID());

            try {
                ous.writeObject(response);
            } catch (IOException ex) {
                Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void HandleCustomerDeliveryConfirm(Message m, ObjectOutputStream ous) {
        CustomerMessage.CustomerOrderDeliveryConfirmation cODC
                = (CustomerMessage.CustomerOrderDeliveryConfirmation) m.msg;

        System.out.println(cODC.userID);
    }

    public void HandleMessages(Message m, ObjectOutputStream ous) {
        switch (m.GetType()) {
            case Message.MessageType.REQUEST_SUPPLIER_LIST: {
                HandleSupplierList(m, ous);
                break;
            }

            case Message.MessageType.REQUEST_SUPPLIER_INFO: {
                HandleSupplierDetails(m, ous);
                break;
            }
            /*case CustomerMessage.CustomerMessageType.TOKEN_REQUEST: {
                System.out.println("Token Request");
                CustomerMessage response = new CustomerMessage();

                response.Token(this.hashCode(), this.getUserID(), this.getConnectionID());
                try {
                    ous.writeObject(response);
                } catch (IOException ex) {
                    Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            }*/

            case CustomerMessage.CustomerMessageType.PLACE_ORDER: {
                HandlePlaceOrder(m, ous);
                break;
            }
            case CustomerMessage.CustomerMessageType.CUSTOMER_DELIVERY_CONFIRMATION: {
                HandleCustomerDeliveryConfirm(m, ous);
                break;
            }

        }

    }

}
