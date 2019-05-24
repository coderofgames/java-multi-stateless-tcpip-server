/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author CHUWI
 */
public class Admin extends User {

    
    public Admin(DataCenter dataCenter){
        super(dataCenter);
    } 
     public Admin(DataCenter dataCenter, String _username, 
                   String _password, int _userID){
        super(dataCenter, _username, _password, _userID);
      
    } 
     
    public UserTypes GetUserType()
    {
        return UserTypes.ADMIN;
    } 

    public void HandleSupplierDetails(Message m, ObjectOutputStream ous) {


        
        int loopStop = ((Message.SupplierInfoRequest)m.msg ).supplierID;
        
        Message response = new Message();
        System.out.println("Customer requests Supplier ddetails");
        try {
            ////out.println("SUPPLIER_DETAILS");
            ////int val = Integer.parseInt(in.readLine());
            Set<Map.Entry<Integer, User>> supplierSet = dC.supplierManager.GetEntrySet();
            Iterator it = supplierSet.iterator();
            int i = 1;
            while (it.hasNext()) {
                Map.Entry<Integer, User> e = (Map.Entry) it.next();
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
    
    
    public void HandleCourierList(Message m, ObjectOutputStream ous) {
        
        //Message.SupplierListRequest msg = (Message.SupplierListRequest)m.msg;
        AdminMessage response = new AdminMessage();
        ArrayList<String> sL = new ArrayList();
        response.CourierList(dC.courierManager.GetActiveCourierTopLevelInfo(sL), 
                                this.getUserID(), this.getConnectionID());
        
        
        try {
            ous.writeObject(response);
            System.out.println("Courier List Message Send");
        } catch (IOException ex) {
            System.out.println("Courier List Message failed");
            ex.printStackTrace();
            //Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        sL = null;
    }
    
    public void HandleCustomerList(Message m, ObjectOutputStream ous) {
        
        //Message.SupplierListRequest msg = (Message.SupplierListRequest)m.msg;
        AdminMessage response = new AdminMessage();
        ArrayList<String> sL = new ArrayList();
        response.CustomerList(dC.customerManager.GetActiveCustomerTopLevelInfo(sL), 
                                this.getUserID(), this.getConnectionID());
        
        
        try {
            ous.writeObject(response);
            System.out.println("Courier List Message Send");
        } catch (IOException ex) {
            System.out.println("Courier List Message failed");
            ex.printStackTrace();
            //Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        sL = null;
    }    
   
       
    public void HandleJobList(Message m, ObjectOutputStream ous) {
        
        //Message.SupplierListRequest msg = (Message.SupplierListRequest)m.msg;
        AdminMessage response = new AdminMessage();
        ArrayList<Order> sL = new ArrayList();
        response.JobList(dC.jobManager.GetJobsTodo(sL), 
                                this.getUserID(), this.getConnectionID());
        
        
        try {
            ous.writeObject(response);
            System.out.println("Courier List Message Send");
        } catch (IOException ex) {
            System.out.println("Courier List Message failed");
            ex.printStackTrace();
            //Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        sL = null;
    }    
    
    public void HandleActiveJobListRequest(Message m, ObjectOutputStream ous) {
        //Message.SupplierListRequest msg = (Message.SupplierListRequest)m.msg;
        AdminMessage response = new AdminMessage();
        ArrayList<JobInfo> sL = new ArrayList();
        response.ActiveJobList(dC.jobManager.GetActiveJobs(sL), 
                                this.getUserID(), this.getConnectionID());
        
        
        try {
            ous.writeObject(response);
            System.out.println("Courier List Message Send");
        } catch (IOException ex) {
            System.out.println("Courier List Message failed");
            ex.printStackTrace();
            //Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        sL = null;
    }
   
    public void HandleMessages(Message m,ObjectOutputStream ous){
          switch (m.GetType()) {
            case Message.MessageType.REQUEST_SUPPLIER_LIST: {
                HandleSupplierList(m, ous);
                break;
            }
            case Message.MessageType.REQUEST_SUPPLIER_INFO: {
                HandleSupplierDetails(m, ous);
                break;
            }
            case AdminMessage.AdminMessageType.REQUEST_COURIER_LIST: {
                HandleCourierList(m, ous);
                break;
            }
            case AdminMessage.AdminMessageType.REQUEST_COURIER_INFO:{
            break;
            }
            case AdminMessage.AdminMessageType.REQUEST_CUSTOMER_LIST:{
                HandleCustomerList(m, ous);
            break;
            }
            case AdminMessage.AdminMessageType.REQUEST_CUSTOMER_INFO:{
            break;
            }
            case AdminMessage.AdminMessageType.REQUEST_JOB_LIST:{
                HandleJobList(m, ous);
            break;
            }
            case AdminMessage.AdminMessageType.ACTIVE_JOB_LIST_REQUEST:{
                HandleActiveJobListRequest(m, ous);
            break;
            }
            /*
            case CUSTOMER_DELIVERY_CONFIRMATION: {
                HandleCustomerDeliveryConfirm(m, ous);
                break;
            }*/

        }      

    }    
}
