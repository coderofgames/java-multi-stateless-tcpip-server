/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.util.ArrayList;

/**
 *
 * @author DAVE
 * 
 *      1. CONNECT
 *      2. DISCONNECT
 *      3. GET
 *      4. YES/NO
 *      5. ACKNOWLEDGE
 *      6. TRANSFER
 *      7. SET STATE / SIGNAL
 *      8. BROADCAST USER GROUP
 */
public class Message implements java.io.Serializable {

    public enum BaseProtocol {
        CONNECT, DISCONNECT, GET, YES_NO, ACKNOWLEDGE, TRANSFER,
        SIGNAL_STATE, BROADCAST_TO_USERS
    }
    
    /*public static class MessageType{
        public static int REGISTER=-1;
        public static int LOGIN=0;
        public static int ACCEPT_LOGIN=1;
        public static int REJECT_LOGIN=2;
        public static int LOGOUT=3;
        
        public static int REQUEST_SUPPLIER_LIST=4;
        public static int SUPPLIER_LIST=5;
        public static int REQUEST_SUPPLIER_INFO=6;
        
        public static int SUPPLIER_INFO=7;
        public static int PLACE_ORDER=8;
                public static int SUPPLIER_REQUEST_JOB=9, SUPPLIER_JOB=10; 
        public static int SUPPLIER_ACCEPT_JOB=11;
                        public static int SUPPLIER_REJECT_JOB=12;
                        public static int REJECT_JOB_TO_CUSTOMER=13; 
        public static int COURIER_REQUEST_JOB_OFFER=14;
        public static int COURIER_JOB_OFFER=15;
        public static int COURIER_ACCEPT_JOB=16; 
        public static int COURIER_REJECT_JOB=17;
        public static int COURIER_ARRIVE_AT_SUPPLIER=18;
        public static int COURIER_COLLECT_ORDER=19; 
        public static int SUPPLIER_COURIER_COLLECTION_CONFIRMATION=20;
        public static int COURIER_ARRIVE_AT_CUSTOMER=21; 
        public static int COURIER_DELIVER_ORDER=22;
                public static int CUSTOMER_DELIVERY_CONFIRMATION=23;
                public static int ACCEPTED=24;
        public static int REQUEST_COURIER_LIST=25; 
                        public static int COURIER_LIST=26; 
                                public static int REQUEST_COURIER_INFO=27;
        public static int COURIER_INFO=28;
        public static int REQUEST_CUSTOMER_LIST=29;
        public static int CUSTOMER_LIST=30; 
        public static int REQUEST_CUSTOMER_INFO=31;
        public static int CUSTOMER_INFO=32;
        public static int REQUEST_JOB_LIST=33;
        public static int JOB_LIST=34;
        public static int REQUEST_JOB_INFO=35;
        public static int JOB_INFO=36;
        public static int ACTIVE_JOB_LIST_REQUEST=37;
        public static int ACTIVE_JOB_LIST=38;
        public static int LIST_REQUEST=39;
    }*/
        
    public static class MessageType{
        public static final int REGISTER=-1, LOGIN=0, ACCEPT_LOGIN=1, REJECT_LOGIN=2, LOGOUT=3, 
        REQUEST_SUPPLIER_LIST=4, SUPPLIER_LIST=5, REQUEST_SUPPLIER_INFO=6,
        SUPPLIER_INFO=7,    ACCEPTED=24;
        
    }
    /*public enum MessageType {
        REGISTER, LOGIN, ACCEPT_LOGIN, REJECT_LOGIN, LOGOUT, 
        REQUEST_SUPPLIER_LIST, SUPPLIER_LIST, REQUEST_SUPPLIER_INFO,
        SUPPLIER_INFO, PLACE_ORDER,SUPPLIER_REQUEST_JOB, SUPPLIER_JOB, 
        SUPPLIER_ACCEPT_JOB, SUPPLIER_REJECT_JOB, REJECT_JOB_TO_CUSTOMER, 
        COURIER_REQUEST_JOB_OFFER, COURIER_JOB_OFFER, COURIER_ACCEPT_JOB, 
        COURIER_REJECT_JOB, COURIER_ARRIVE_AT_SUPPLIER, COURIER_COLLECT_ORDER, 
        SUPPLIER_COURIER_COLLECTION_CONFIRMATION, COURIER_ARRIVE_AT_CUSTOMER, 
        COURIER_DELIVER_ORDER, CUSTOMER_DELIVERY_CONFIRMATION, ACCEPTED,
        REQUEST_COURIER_LIST, COURIER_LIST, REQUEST_COURIER_INFO,
        COURIER_INFO, REQUEST_CUSTOMER_LIST, CUSTOMER_LIST, REQUEST_CUSTOMER_INFO,
        CUSTOMER_INFO, REQUEST_JOB_LIST, JOB_LIST, REQUEST_JOB_INFO,
        JOB_INFO, ACTIVE_JOB_LIST_REQUEST, ACTIVE_JOB_LIST, LIST_REQUEST,
    }*/
    
    public enum LIST_TYPE {CUSTOMER,COURIER,SUPPLIER,JOB};
    
    public abstract class MessageContent implements java.io.Serializable{

        public int userID;
        public BaseProtocol baseProtocol;
        public abstract int GetMessageType();
        public abstract BaseProtocol GetBaseProtocol();
    }
    
    public abstract class ConnectMessage extends MessageContent {
        
        public BaseProtocol GetBaseProtocol(){
            return BaseProtocol.CONNECT;
        }
    }
    
    public abstract class DisconnectMessage extends MessageContent {
        
        public BaseProtocol GetBaseProtocol(){
            return BaseProtocol.DISCONNECT;
        }
    }
    
    public abstract class GetMessage extends MessageContent {
        
        
        public BaseProtocol GetBaseProtocol(){
            return BaseProtocol.GET;
        }
    }   

     public abstract class YesNoMessage extends MessageContent {
        
        
        public BaseProtocol GetBaseProtocol(){
            return BaseProtocol.YES_NO;
        }
    }
     
    public abstract class AcknowledgeMessage extends MessageContent {
        
        public BaseProtocol GetBaseProtocol(){
            return BaseProtocol.ACKNOWLEDGE;
        }
    }   
    
    public abstract class TransferMessage extends MessageContent {
        
        public BaseProtocol GetBaseProtocol(){
            return BaseProtocol.TRANSFER;
        }
    }    
    
    public abstract class SignalStateMessage extends MessageContent {
        
        public BaseProtocol GetBaseProtocol(){
            return BaseProtocol.SIGNAL_STATE;
        }
    }   

    public abstract class BroadcastToUsersMessage extends MessageContent {
        
        public BaseProtocol GetBaseProtocol(){
            return BaseProtocol.BROADCAST_TO_USERS;
        }
    }      
    
    public class GeneralAck extends AcknowledgeMessage {
        public GeneralAck(int _userID){
            userID=_userID;
        }
        
        public int GetMessageType(){
            return MessageType.ACCEPTED;
        }
    }
    public void GeneralAck(int _userID, int _connectionID){
        this.msg = new GeneralAck( _userID);
        this.messageType = this.msg.GetMessageType();
        this.connectionID = _connectionID;
        this.baseProt = this.msg.GetBaseProtocol();
    }    
    
    
    public class LoginMessage extends ConnectMessage{
        public String username;
        public String password;

        public LoginMessage(String _username, String _password, int _userID){
            username = _username;
            password = _password;
            userID = _userID;
        }
        
        public int GetMessageType(){
            return MessageType.LOGIN;
        }
    }
    
    // CONNECT message
    public void LoginMessage(String _username, String _password, 
                                int _userID, int _connectionID){
        this.msg = new LoginMessage(_username, _password, _userID);
        this.messageType = this.msg.GetMessageType();
        this.connectionID = _connectionID;
        this.baseProt = this.msg.GetBaseProtocol();
    }
    
    public class LogoutMessage extends DisconnectMessage{

        public LogoutMessage(int _userID){
            
            userID = _userID;
            
        }
        public int GetMessageType(){
            return MessageType.LOGOUT;
        }
    }
    
    // DISCONNECT message
    public void LogoutMessage(int _userID, int _connectionID){
        this.msg = new LogoutMessage(_userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }

    public class SupplierListRequest extends GetMessage {
        
        
        public SupplierListRequest(int _userID){
            this.userID = _userID;
        }
        public int GetMessageType(){
            return MessageType.REQUEST_SUPPLIER_LIST;
        }  
    }
    
    // GET
    public void SupplierListRequest(int _userID, int _connectionID){
        this.msg = new SupplierListRequest(_userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }
    
    public class SupplierList extends TransferMessage {
        
        public ArrayList<String> suppList;
        
        public SupplierList(int _userID){
            userID = _userID;
        }
        
        public void AddSupplier(String sname){
            suppList.add(sname);
        }
        
        public int GetMessageType(){
            return MessageType.SUPPLIER_LIST;
        }  
    }    
    
    // TRANSFER
    public void SupplierList(ArrayList<String> topLevelInfo, 
                                int _userID, int _connectionID){
        this.msg = new SupplierList(_userID);
        ( (SupplierList)this.msg ).suppList = new ArrayList(topLevelInfo);
        this.messageType = MessageType.SUPPLIER_LIST;
        this.connectionID = _connectionID;
        this.baseProt = this.msg.GetBaseProtocol();
    }
    
    
    public class SupplierInfoRequest extends GetMessage {
        
        public int supplierID;
        public SupplierInfoRequest(int _supplierID, int _userID){
            userID = _userID;
            supplierID = _supplierID;
        }
        
        public int GetMessageType(){
            return MessageType.REQUEST_SUPPLIER_INFO;
        }  
    }
    
    // GET
    public void SupplierInfoRequest(int _supplierID, int _userID, int _connectionID){
        this.msg = new SupplierInfoRequest(_supplierID, _userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }
    
    public class SupplierInfo extends TransferMessage {
        
        public ArrayList<Product> products;
        public int supplierID;
        public String name;
        public SupplierInfo(String _name, ArrayList<Product> p, int _supplierID, int _userID){
            supplierID = _supplierID;
            userID = _userID;
            products = p;
            name = _name;
        }
        public int GetMessageType(){
            return MessageType.SUPPLIER_INFO;
        }  
    }   
    
    // TRANSFER
    public void SupplierInfo(String name, ArrayList<Product> p,
            int _supplierID, int _userID, int _connectionID){
        this.msg = new SupplierInfo(name, p, _supplierID, _userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }    
    
    
    
    

    
    
 
    
    
 
   
    
    public void SetMessage(MessageContent msgContent, int _connectionID){
        messageType = msgContent.GetMessageType();
        connectionID = _connectionID;
        
        this.msg = msgContent;
    }
    
    

    
    public int GetType(){
        return messageType;
    }
    
    
    public BaseProtocol baseProt;
    public int messageType;
    public MessageContent msg = null;
    public int connectionID;
    
 
    
    
     
    public Message(){
    }

    public Message(MessageContent message) {
        msg = message;
        messageType = msg.GetMessageType();
    }

}
