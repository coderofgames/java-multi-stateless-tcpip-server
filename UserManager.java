/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author CHUWI
 */
public class UserManager {
    
   public UserList allUsers= new UserList();
   public CopyOnWriteArrayList<LoginDetail> logins= new CopyOnWriteArrayList();
    
   public UserList onlineUsers = new UserList();
   
    public User GetUser(int userID) {
        return allUsers.Get(userID);
    }    

    public void AddUser(User c) {
        allUsers.AddUser(c);
    }
    
   public User GetUser(String username, String password) {
        for (int i = 0; i < logins.size(); i++) {
            LoginDetail login = logins.get(i);

            if (login.getUserName().equalsIgnoreCase(username)
                    && login.getPassword().equalsIgnoreCase(password)) {
                return  allUsers.FindUserByUserID(login.getUserID());
            }

        }
        return null;
    }    
    
    public void AddLogin(LoginDetail login) {
        logins.add(login);
    }   

    public Boolean UserExists(String username, String password) {
        for (int i = 0; i < logins.size(); i++) {
            LoginDetail login = logins.get(i);

            if (login.getUserName().equalsIgnoreCase(username)
                    && login.getPassword().equalsIgnoreCase(password)) {
                return true;
            }

        }
        return false;
    } 
    
    Set<Entry<Integer,User>> GetEntrySet(){
        return onlineUsers.GetEntrySet();
    }
    
    public void LogInUser(int userID) {
        User c = allUsers.FindUserByUserID(userID);
        onlineUsers.AddUser(c);
    }
    
    public User GetOnlineUserByConnectionID(int connectionID) {
        return onlineUsers.FindUserByConnectionID(connectionID);
    }    
    
    public User GetOnlineUser(int userID){
        return onlineUsers.Get(userID);
    }
    
    public void AddOnlineUser(int userID){
        onlineUsers.Put(userID, allUsers.Get(userID) );
    }
    
    public User RemoveOnlineUser(int userID){
        return onlineUsers.Remove(userID);
    }        
}
