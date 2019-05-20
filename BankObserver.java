/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CHUWI
 */
public class BankObserver {

    public DataCenter dC;

    public BankObserver(DataCenter dataCenter) {
        dC = dataCenter;
    }

    public void OnPayment(BankTransaction payment) {
        Boolean orderNotPlaced = true;
        while (orderNotPlaced) {
            try {
                sleep(200);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                //Logger.getLogger(BankObserver.class.getName()).log(Level.SEVERE, null, ex);
            }

            Order o = dC.orderManager.GetOrderByCustomerID(payment.GetCustomerID());

            if (o != null) {
                dC.CreateJob(o, dC.GetOnlineCustomer(payment.GetCustomerID()));
                orderNotPlaced = false;
            }
        }
    }

}
