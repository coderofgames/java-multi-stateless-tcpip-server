/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 *
 * @author CHUWI
 */
public class Supplier extends User {


    public String name = "";
    public CopyOnWriteArrayList<Product> products;

    public CopyOnWriteArrayList<Integer> activeProducts;

    public Supplier(DataCenter dataCenter) {
        super(dataCenter);
        products = new CopyOnWriteArrayList();
        activeProducts = new CopyOnWriteArrayList();
    }

    public Supplier(DataCenter dataCenter, String _username,
            String _password, int _userID) {
        super(dataCenter, _username, _password, _userID);
        products = new CopyOnWriteArrayList();
        activeProducts = new CopyOnWriteArrayList();
    }

    public ArrayList<Product> GetProducts(ArrayList<Product> p) {
        //for (int j = 0; j < activeProducts.size(); j++) {
        //    p.add(products.get(activeProducts.get(j)));
       // }
       for( int k = 0; k < products.size(); k++)
           p.add(products.get(k));
        return p;
    }

    public UserTypes GetUserType() {
        return UserTypes.SUPPLIER;
    }

    public void AddProduct(Product p) {
        products.add(p);
        activeProducts.add(products.size() - 1);
    }

    public String getName() {
        synchronized (name) {
            return name;
        }
    }

    public void setName(String _name) {
        synchronized (name) {
            name = _name;
        }
    }

    public void HandleJobRequest(Message m, ObjectOutputStream ous) {

        System.out.println("Customer requests Supplier List");
        //out.write("JOB OFFER");
        //out.write(5); // JobID

    }

    public void HandleAcceptJob(Message m, ObjectOutputStream ous) {

        System.out.println("Customer requests Supplier ddetails");
        
        
        try {

            //int supplierID = Integer.parseInt(in.readLine());
            //Supplier s = dC.GetSupplier(supplierID);
            
            //out.write(s.name);
            //out.write(s.address);
            //out.write(s.activeProducts.size());
            //out.write(s.products);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void HandleCourierArrival(Message m, ObjectOutputStream ous) {

        System.out.println("Customer requests Place ORder");
        // read object using 
        // ObjectInputStream ...
        // use DataCenter.placeOrder
    }

    public void HandleMessages(Message m, ObjectOutputStream ous) {

        switch (m.GetType()) {
            case SupplierMessage.SupplierMessageType.SUPPLIER_REQUEST_JOB: {
                HandleJobRequest(m, ous);
                break;
            }
       
            case SupplierMessage.SupplierMessageType.SUPPLIER_JOB: {
                HandleAcceptJob(m, ous);
                break;
            }
            case SupplierMessage.SupplierMessageType.SUPPLIER_COURIER_COLLECTION_CONFIRMATION: {
                HandleCourierArrival(m, ous);
                break;
            }

        }

    }

}
