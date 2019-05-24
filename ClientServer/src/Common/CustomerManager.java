/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author CHUWI
 */
public class CustomerManager extends UserActivityManager {

    public ArrayList<String> GetActiveCustomerTopLevelInfo(ArrayList<String> sL){
        Set<Map.Entry<Integer,User>> eSet = onlineUsers.GetEntrySet();
        Iterator it = eSet.iterator();
        while(it.hasNext()){
            Map.Entry<Integer,User> e = (Map.Entry)it.next();
            sL.add(  ((Customer)e.getValue() ).getUserID()+": "+( (Customer)e.getValue() ).getFirstName() + ", " + ((Customer)e.getValue() ).getSecondName());
        }
        return sL;
    }






}
