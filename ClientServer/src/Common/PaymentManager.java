/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author CHUWI
 */
public class PaymentManager {
    public PaymentList payments= new PaymentList();;
    public DataCenter dC;
    public BankObserver o;
    
    public PaymentManager(DataCenter dataCenter){
        dC = dataCenter;
        o = new BankObserver(dC);
    }
    
    public Boolean Validate(BankTransaction payment){
        return payments.Validate(payment);
    }
    
    public void AddPayment(BankTransaction payment){
        payments.AddBankTransaction(payment);
        
        // now ... try checking over the order list for  
        // any orders that did not find a valid payment reciept when they 
        // were initially placed. 
        o.OnPayment(payment);
    }
}
