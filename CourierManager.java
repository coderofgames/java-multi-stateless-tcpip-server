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
public class CourierManager extends UserActivityManager {



    public CourierManager() {
        super();
    }
    
    public ArrayList<String> GetActiveCourierTopLevelInfo(ArrayList<String> sL){
        Set<Map.Entry<Integer,User>> eSet = onlineUsers.GetEntrySet();
        Iterator it = eSet.iterator();
        while(it.hasNext()){
            Map.Entry<Integer,User> e = (Map.Entry)it.next();
            sL.add( ( (Courier)e.getValue() ).getFirstName() + ", " + ( (Courier)e.getValue() ).getUserID() );
        }
        return sL;
    }



}
