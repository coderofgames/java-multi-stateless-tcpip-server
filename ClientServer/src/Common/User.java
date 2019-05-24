/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

/**
 *
 * @author CHUWI
 */
public abstract class User {

    public UserInfo info = new UserInfo();

    UserTypes type;
    
    
    
    int customerSTATE = 0;
    
    public DataCenter dC;
    
    public User(DataCenter dataCenter){
        dC = dataCenter;
        
    }
    
    public User(DataCenter dataCenter, String _username, 
               String _password, int _userID) {
        dC = dataCenter;
        info.username = _username;
        info.password = _password;
        info.userID = _userID;
        
    }    
    
    public abstract UserTypes GetUserType();
    
    public synchronized Address copyAddress(){
        Address newAddress = new Address();
        for( int i = 0; i < info.address.addressLines.length; i++){
            newAddress.addressLines[i] = info.address.addressLines[i];
            
        }
        newAddress.postcode = info.address.postcode;
        
        return newAddress;
    }
    
    public synchronized void SetPostCode(String code){
        info.address.SetPostcode(code);
    }
    public synchronized String GetPostCode(){
        return info.address.postcode;
    }
    
    public synchronized Address GetAddress(){
        return info.address;
    }
    
    public synchronized String addAddressLine0(){
        return info.address.GetAddressLine0();
    } 
    public synchronized String addAddressLine1(){
        return info.address.GetAddressLine1();
    } 
    public synchronized String addAddressLine2(){
        return info.address.GetAddressLine2();
    } 
    public synchronized String addAddressLine3(){
        return info.address.GetAddressLine3();
    }     
    
    public synchronized void SetAddressLine0(String line){
        info.address.SetAddressLine0(line);
    } 
    public synchronized void SetAddressLine1(String line){
        info.address.SetAddressLine1(line);
    } 
    public synchronized void SetAddressLine2(String line){
        info.address.SetAddressLine2(line);
    } 
    public synchronized void SetAddressLine3(String line){
        info.address.SetAddressLine3(line);
    } 
    
    public UserInfo getInfo(){
        UserInfo newInfo = new UserInfo(this);
        return newInfo;
    }
    
    public synchronized void setInfo(UserInfo _info){
        info = _info;
    }
    
     public synchronized String getFirstName() {

            return info.personName.firstName;
        
    }
     
     
    public synchronized void setFirstName(String _name) {

            info.personName.firstName = _name;
        
    }   
    

     public synchronized String getSecondName() {

            return info.personName.lastName;
        
    }
    public synchronized void setSecondName(String _name) {

            info.personName.lastName = _name;
        
    }
    
    public synchronized PersonName getPersonName(){
        return info.personName;
    }
    
    public synchronized void SetPersonName(PersonName p){
        info.personName = p;
    }
    
    public synchronized String getUserName() {

            return info.username;
        
    }
    public synchronized void setUserName(String _username) {

            info.username = _username;
        
    }
    
    public synchronized String getPassword() {

            return info.password;
        
    }
    public synchronized void setPassword(String _password) {
        
            info.password = _password;
        
    }    
    
    public synchronized Integer getConnectionID() {
        
            return info.connectionID;
        
    }
    public synchronized void setConnectionID(Integer _connectionID) {
        
            info.connectionID = _connectionID;
        
    }    

    public synchronized Integer getUserID() {
        
            return info.userID;
        
    }
    public synchronized void setUserID(Integer _userID) {
        
            info.userID = _userID;
        
    } 
    
    public abstract void HandleMessages(Message m, ObjectOutputStream ous);
 
}
