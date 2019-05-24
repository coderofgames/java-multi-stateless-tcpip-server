/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author CHUWI
 */
public class UserList extends ObjectList<User>{
    
    public ConcurrentHashMap<Integer, User> userConnections= new ConcurrentHashMap();;
    
    public UserList(){
        super();
   
    }
    
    public User FindUserByConnectionID(Integer connectionID){
        return userConnections.get(connectionID);
    }
    
    public User FindUserByUserID(Integer userID){
        return super.Get(userID);
    }
    
    public void AddUser(User user){
        userConnections.put(user.getConnectionID(), user);
        super.Put(user.getUserID(), user);
    }
    
    public User RemoveUser(int userID){
        User user = super.Remove(userID);
        userConnections.remove(user);
        
        return user;
    }
    
    Set<Map.Entry<Integer,User>> GetEntrySet(){
        return table.entrySet();
    }
    
}
