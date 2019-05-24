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
public class SupplierMessage extends Message {
    
    public static class SupplierMessageType extends MessageType {
        public static final int SUPPLIER_REQUEST_JOB=9, SUPPLIER_JOB=10, 
                SUPPLIER_ACCEPT_JOB=11, SUPPLIER_REJECT_JOB=12,
                SUPPLIER_COURIER_COLLECTION_CONFIRMATION=20;
    }
    
    
    
   public class SupplierRequestJob extends GetMessage {
        
        public SupplierRequestJob(int _userID){
            userID = _userID;
        }
        public int GetMessageType(){
            return SupplierMessageType.SUPPLIER_REQUEST_JOB;
        }
    }
    
    // GET / POLL
    public void SupplierRequestJob(int _userID, int _connectionID){
        this.msg = new SupplierRequestJob(_userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }    



    public class SupplierJobOffer extends TransferMessage {
    
        public Order order;
        public SupplierJobOffer(Order o, int _userID){
            userID = _userID;
            order = new Order(o);
        }
        public int GetMessageType(){
            return SupplierMessageType.SUPPLIER_JOB;
        }
    }
    
    // GET / POLL
    public void SupplierJobOffer(Order o, int _userID, int _connectionID){
        this.msg = new SupplierJobOffer( o, _userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }            
    
    public class SupplierAcceptJob extends YesNoMessage {
        
        public int jobID;
        
        public SupplierAcceptJob(int _jobID, int _userID){
            userID = _userID;
            jobID = _jobID;
        }
        
        public int GetMessageType(){
            return SupplierMessageType.SUPPLIER_ACCEPT_JOB;
        }
    }    
    
    // YES/NO
    public void SupplierAcceptJob(int _jobID, int _userID, int _connectionID){
        this.msg = new SupplierAcceptJob(_jobID, _userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }            
    
    public class SupplierRejectJob extends YesNoMessage {
        public int jobID;
        public SupplierRejectJob(int _jobID, int _userID){
            jobID = _jobID;
            userID = _userID;
        }
        public int GetMessageType(){
            return SupplierMessageType.SUPPLIER_REJECT_JOB;
        }
    }    
    
    // YES/NO
    public void SupplierRejectJob(int _jobID, int _userID, int _connectionID){
        this.msg = new SupplierRejectJob(_jobID, _userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }                

 
      
    
    public class SupplierOrderCollectionConfirmation extends SignalStateMessage {
        public int jobID;
        public SupplierOrderCollectionConfirmation(int _jobID, int _userID){
            jobID = _jobID;
            userID = _userID;
        }             
        public int GetMessageType(){
            return SupplierMessageType.SUPPLIER_COURIER_COLLECTION_CONFIRMATION;
        }
    }       
    
    // SIGNAL STATE / SET
    public void SupplierOrderCollectionConfirmation(int _jobID, int _userID, int _connectionID){
        this.msg = new SupplierOrderCollectionConfirmation(_jobID, _userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }  

 
        
}
