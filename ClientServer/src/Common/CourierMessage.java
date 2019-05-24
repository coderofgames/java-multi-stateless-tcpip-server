/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.util.ArrayList;

/**
 *
 * @author CHUWI
 */
public class CourierMessage extends Message {

    public static class CourierMessageType extends MessageType {

        public static final int COURIER_REQUEST_JOB_OFFER = 14, 
                    COURIER_JOB_OFFER = 15, COURIER_ACCEPT_JOB = 16,
                    COURIER_REJECT_JOB = 17, COURIER_ARRIVE_AT_SUPPLIER = 18, 
                    COURIER_COLLECT_ORDER = 19, COURIER_ARRIVE_AT_CUSTOMER=21, 
                    COURIER_DELIVER_ORDER=22;
    }

    public class CourierRequestJobOffer extends GetMessage {

        public CourierRequestJobOffer(int _userID) {
            userID = _userID;
        }

        public int GetMessageType() {
            return CourierMessageType.COURIER_REQUEST_JOB_OFFER;
        }
    }

    // GET / POLL
    public void CourierRequestJobOffer(int _userID, int _connectionID) {
        this.msg = new CourierRequestJobOffer(_userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }

    public class CourierAcceptJob extends YesNoMessage {

        public int jobID;

        public CourierAcceptJob(int _jobID, int _userID) {
            jobID = _jobID;
            userID = _userID;
        }

        public int GetMessageType() {
            return CourierMessageType.COURIER_ACCEPT_JOB;
        }
    }

    // YES/NO
    public void CourierAcceptJob(int _jobID, int _userID, int _connectionID) {
        this.msg = new CourierAcceptJob(_jobID, _userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }

    public class CourierRejectJob extends YesNoMessage {

        public int jobID;

        public CourierRejectJob(int _jobID, int _userID) {
            jobID = _jobID;
            userID = _userID;
        }

        public int GetMessageType() {
            return CourierMessageType.COURIER_REJECT_JOB;
        }
    }

    // YES/NO
    public void CourierRejectJob(int _jobID, int _userID, int _connectionID) {
        this.msg = new CourierRejectJob(_jobID, _userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }

    public class JobOfferToCourier extends TransferMessage {

        public ArrayList<Order> jobsTodo;

        public JobOfferToCourier(ArrayList<Order> j2do, int _jobID, int _userID) {

            userID = _userID;
            jobsTodo = j2do;
        }

        public int GetMessageType() {
            return CourierMessageType.COURIER_JOB_OFFER;
        }
    }

    // TRANSFER
    public void JobOfferToCourier(ArrayList<Order> j2do, int _jobID, int _userID, int _connectionID) {
        this.msg = new JobOfferToCourier(j2do, _jobID, _userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }

    public class CourierArriveAtSupplier extends SignalStateMessage {

        public int jobID;

        public CourierArriveAtSupplier(int _jobID, int _userID) {
            jobID = _jobID;
            userID = _userID;
        }

        public int GetMessageType() {
            return CourierMessageType.COURIER_ARRIVE_AT_SUPPLIER;
        }
    }

    // SIGNAL STATE / SET
    public void CourierArriveAtSupplier(int _jobID, int _userID, int _connectionID) {
        this.msg = new CourierArriveAtSupplier(_jobID, _userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }

    public class CourierCollectOrder extends SignalStateMessage {

        public int jobID;

        public CourierCollectOrder(int _jobID, int _userID) {
            jobID = _jobID;
            userID = _userID;
        }

        public int GetMessageType() {
            return CourierMessageType.COURIER_COLLECT_ORDER;
        }
    }

    // SIGNAL STATE / SET
    public void CourierCollectOrder(int _jobID, int _userID, int _connectionID) {
        this.msg = new CourierCollectOrder(_jobID, _userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }

    public class CourierArriveAtCustomer extends SignalStateMessage {

        public int jobID;

        public CourierArriveAtCustomer(int _jobID, int _userID) {
            jobID = _jobID;
            userID = _userID;
        }

        public int GetMessageType() {
            return CourierMessageType.COURIER_ARRIVE_AT_CUSTOMER;
        }
    }

    // SIGNAL STATE / SET
    public void CourierArriveAtCustomer(int _jobID, int _userID, int _connectionID) {
        this.msg = new CourierArriveAtCustomer(_jobID, _userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }

    public class CourierDeliverOrder extends SignalStateMessage {

        public int jobID;

        public CourierDeliverOrder(int _jobID, int _userID) {
            jobID = _jobID;
            userID = _userID;
        }

        public int GetMessageType() {
            return CourierMessageType.COURIER_DELIVER_ORDER;
        }
    }

    // SIGNAL STATE / SET
    public void CourierDeliverOrder(int _jobID, int _userID, int _connectionID) {
        this.msg = new CourierDeliverOrder(_jobID, _userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }
}
