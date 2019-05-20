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
public class UserActivityManager extends UserManager{
    
    public UserList idleUsers= new UserList();
    public UserList busyUsers= new UserList();


  
    public void LogInUser(int userID) {
        User c = allUsers.FindUserByUserID(userID);
        onlineUsers.AddUser(c);
        idleUsers.AddUser(c);
    }
    
    public void LogoutUser(int userID) {
        // removing from these three 
        onlineUsers.RemoveUser(userID);
        idleUsers.RemoveUser(userID);
        busyUsers.RemoveUser(userID);
    }    

    
    public User GetIdleUser(int userID) {
        return (User) idleUsers.Get(userID);
    }
        
    public User GetActiveUser(int userID) {
        return (User) busyUsers.Get(userID);
    }

    public void AddActiveUser(User c) {
        busyUsers.AddUser(c);
    }

    public void AddIdleUser(User c) {
        idleUsers.AddUser(c);
    }

    public void SetIdleUserActive(int userID) {
   
        busyUsers.AddUser(idleUsers.RemoveUser(userID));
    }

    public void RemoveActiveUser(int userID) {
        //User c = (User)idleUsers.Remove();
        busyUsers.RemoveUser(userID);
    }
    

        
}
