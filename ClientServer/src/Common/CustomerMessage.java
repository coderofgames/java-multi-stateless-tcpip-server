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
public class CustomerMessage extends Message{
    
    public static class CustomerMessageType extends MessageType {
    public static final int PLACE_ORDER=8,CUSTOMER_DELIVERY_CONFIRMATION=23,
            REJECT_JOB_TO_CUSTOMER=13;
    //, SEND_NONCE = 144, TOKEN_REQUEST = 1555, TOKEN=120;
    }
    
    /*
    public class TokenRequest extends SignalStateMessage {
        public TokenRequest(int _userID){
            userID = _userID;
        }           
        public int GetMessageType(){
            return CustomerMessageType.TOKEN_REQUEST;
        }        
    }
    
     public void TokenRequest( int _userID, int _connectionID){
        this.msg = new TokenRequest(_userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }    
     
    public class Token extends TransferMessage {
        public int token;
        public Token(int _token, int _userID){
            userID = _userID;
            token = _token;
        }           
        public int GetMessageType(){
            return CustomerMessageType.TOKEN;
        }        
    }
    
     public void Token( int _token, int _userID, int _connectionID){
        this.msg = new Token(_token, _userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }        
    
    public class SendNonce extends TransferMessage {
        int nonce;
        public SendNonce(int _nonce, int _userID){
            nonce = _nonce;
        }
        public int GetMessageType(){
            return CustomerMessageType.SEND_NONCE;
        }
    }
    
    // TRANSFER
    public void SendNonce(int nonce, int _userID, int _connectionID){
        this.msg = new SendNonce(nonce, _userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }  */      
     
    public class PlaceOrder extends TransferMessage {
        Order order;
        public PlaceOrder(Order _order, int _userID){
            order = _order;
            userID = _userID;
        }
        public int GetMessageType(){
            return CustomerMessageType.PLACE_ORDER;
        }
    }
    
    // TRANSFER
    public void PlaceOrder(Order _order, int _userID, int _connectionID){
        this.msg = new PlaceOrder(_order, _userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }    
    
 
    public class CustomerOrderDeliveryConfirmation extends SignalStateMessage {
        public int jobID;
        public CustomerOrderDeliveryConfirmation(int _jobID, int _userID){
            jobID = _jobID;
            userID = _userID;
        }           
        public int GetMessageType(){
            return CustomerMessageType.CUSTOMER_DELIVERY_CONFIRMATION;
        }
    }            
    
    // SIGNAL STATE / SET
    public void CustomerOrderDeliveryConfirmation(int _jobID, int _userID, int _connectionID){
        this.msg = new CustomerOrderDeliveryConfirmation(_jobID, _userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }        
        
       
}
