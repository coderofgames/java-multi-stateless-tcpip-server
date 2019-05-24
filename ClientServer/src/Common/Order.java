/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author CHUWI
 */
public class Order implements java.io.Serializable {

    public int connectionType = 0;

    public int messageType = 0;
    public int customerID = 0;
    public Address deliverAddress;
    public Address collectionAddress;
    public int supplierID = 0;
    //public transient ArrayList products = null;
    public ArrayList<Product> products = new ArrayList();
    public String message = "";
    public DateTime deliveryTime = new DateTime();
    public BankTransaction transactionRecord;
    public int jobID = 0;

    public Order() {
    }

    public Order(Order o) {
        connectionType = o.GetConnectionType();
        messageType = o.GetMessageType();
        customerID = o.GetCustomerID();
        deliverAddress = new Address(o.GetDeliveryAddress());
        collectionAddress = new Address(o.GetCollectionAddress());
        supplierID = o.GetSupplierID();
        message = o.GetMessage();

        synchronized (o.products) {
            products.addAll(o.products);
        }

        synchronized (o.deliveryTime.date) {
            deliveryTime.date.dayOfMonth = o.deliveryTime.date.dayOfMonth;
            deliveryTime.date.monthOfYear = o.deliveryTime.date.monthOfYear;
        }
        synchronized (o.deliveryTime.time) {
            deliveryTime.time.hourOfDay = o.deliveryTime.time.hourOfDay;
            deliveryTime.time.minuteOfHour = o.deliveryTime.time.minuteOfHour;
        }
        
        /*synchronized(o.transactionRecord){
            transactionRecord.transactionNumber = o.transactionRecord.transactionNumber;
            transactionRecord.transactionReciept = o.transactionRecord.transactionReciept;
        }*/
        
        transactionRecord.transactionNumber = 0;
        transactionRecord.transactionReciept = "COMPLETE";
    }

    public synchronized void SetJobID(int _jobID) {
        jobID = _jobID;
    }

    public synchronized int GetJobID() {
        return jobID;
    }

    public synchronized void SetconnectionType(int type) {
        connectionType = type;
    }

    public synchronized int GetConnectionType() {
        return connectionType;
    }

    public synchronized void SetMessageType(int type) {
        messageType = type;
    }

    public synchronized int GetMessageType() {
        return messageType;
    }

    public synchronized void SetCustomerID(int id) {
        customerID = id;
    }

    public synchronized int GetCustomerID() {
        return customerID;
    }

    public void SetDeliveryAddress(Address addr) {
        synchronized (deliverAddress) {
            deliverAddress = addr;
        }
    }

    public synchronized Address GetDeliveryAddress() {
        return deliverAddress;
    }

    public void SetCollectionAddress(Address addr) {
        synchronized (collectionAddress) {
            collectionAddress = addr;
        }
    }

    public synchronized Address GetCollectionAddress() {
        return collectionAddress;
    }

    public synchronized void SetSupplierID(int id) {
        supplierID = id;
    }

    public synchronized int GetSupplierID() {
        return supplierID;
    }

    public synchronized void SetProducts(ArrayList<Product> ps) {
        synchronized (products) {
            products = ps;
        }
    }

    public synchronized ArrayList<Product> GetProducts() {
        return products;
    }

    public void SetMessage(String s) {
        synchronized (message) {
            message = s;
        }
    }

    public synchronized String GetMessage() {

        return message;
    }

    public void SetDeliveryTime(DateTime d) {
        synchronized (deliveryTime) {
            deliveryTime = d;
        }
    }

    public synchronized DateTime GetDeliveryTime() {
        return deliveryTime;
    }

    public synchronized void SetBankTransaction(BankTransaction pay) {
        synchronized (transactionRecord) {
            transactionRecord = pay;
        }
    }

    public synchronized BankTransaction GetBankTransaction() {
        return transactionRecord;
    }



    public void AddProduct(Product p) {
        products.add(p);
    }

    /*public void ConverrtProductListToString() {
        for (int i = 0; i < products.size(); i++) {
            
        }
    }*/
    public float ComputeCost(HashMap productCosts) {
        float cost = 0.0f;
        /*   for (int i = 0; i < products.size(); i++) {
            cost += (float) productCosts.get(products.get(i));
        }*/
        return cost;
    }

}
