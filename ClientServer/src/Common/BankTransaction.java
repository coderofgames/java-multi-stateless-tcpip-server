/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

/**
 *
 * @author CHUWI
 */
public class BankTransaction implements java.io.Serializable{

    public String transactionReciept="";
    public int transactionNumber=0;
    
    public int customerID = 0;
    
    public synchronized Boolean IsEqual(BankTransaction payment){
        return payment.GetReciept().equalsIgnoreCase(transactionReciept) &&
                payment.GetNumber() == transactionNumber;
    }
    
    public synchronized void SetCustomerID(int _customerID){
        customerID = _customerID;
    }
    
    public synchronized int GetCustomerID(){
        return customerID;
    }
    
    public synchronized void SetTransactionReciept(String s){
        transactionReciept = s;
    }
    
    public synchronized void SetTransactionNumber(int n){
        transactionNumber = n;
    }
    
    public synchronized String GetReciept(){
        return transactionReciept;
    }
    
    public synchronized int GetNumber(){
        return transactionNumber;
    }
}
