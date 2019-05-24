/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author CHUWI
 */
public class ObjectList<T> {
    public ConcurrentHashMap<Integer, T> table;
    
    public ObjectList(){
        table = new ConcurrentHashMap();
    }    
    
    public void Put(int hash, T obj){
        table.put(hash, obj);
    }
    
    public T Get(int hash){
        return table.get(hash);
    }
    
    public T Remove(int hash){
        return table.remove(hash);
    }
}
