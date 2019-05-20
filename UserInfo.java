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
public class UserInfo {
    public Integer userID =0;
    public Integer connectionID=0;
    public String username="";
    public String password="";
    public Address address = new Address();
    public PersonName personName = new PersonName();    
    
    public UserInfo(){
        
    }
    public UserInfo(User u){
        userID = u.getUserID();
        connectionID = u.getConnectionID();
        username = u.getUserName();
        password = u.getPassword();
        address = u.copyAddress();
        personName.firstName = u.getFirstName();
        personName.lastName = u.getSecondName();
    }
    
    
}
