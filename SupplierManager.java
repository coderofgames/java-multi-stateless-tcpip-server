/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author CHUWI
 */
public class SupplierManager extends UserManager {
    
    public SupplierManager(){
        super();
    }
    
    public ArrayList<String> GetActiveSupplierTopLevelInfo(ArrayList<String> sL){
        Set<Entry<Integer,User>> eSet = onlineUsers.GetEntrySet();
        Iterator it = eSet.iterator();
        while(it.hasNext()){
            Entry<Integer,User> e = (Entry)it.next();
            sL.add( ( (Supplier)e.getValue() ).getName() );
        }
        return sL;
    }
}
