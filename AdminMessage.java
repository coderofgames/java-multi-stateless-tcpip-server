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
public class AdminMessage extends Message {

    public static class AdminMessageType extends MessageType {

        public static final int REQUEST_COURIER_LIST = 25, COURIER_LIST = 26, REQUEST_COURIER_INFO = 27,
                COURIER_INFO = 28, REQUEST_CUSTOMER_LIST = 29, CUSTOMER_LIST = 30, REQUEST_CUSTOMER_INFO = 31,
                CUSTOMER_INFO = 32, REQUEST_JOB_LIST = 33, JOB_LIST = 34, REQUEST_JOB_INFO = 35,
                JOB_INFO = 36, ACTIVE_JOB_LIST_REQUEST = 37, ACTIVE_JOB_LIST = 38, LIST_REQUEST = 39;
    }

    public class CourierListRequest extends GetMessage {

        public CourierListRequest(int _userID) {
            this.userID = _userID;
        }

        public int GetMessageType() {
            return AdminMessageType.REQUEST_COURIER_LIST;
        }
    }

    // GET
    public void CourierListRequest(int _userID, int _connectionID) {
        this.msg = new CourierListRequest(_userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }

    public class CourierList extends TransferMessage {

        public ArrayList<String> suppList;

        public CourierList(int _userID) {
            userID = _userID;
        }

        public void AddSupplier(String sname) {
            suppList.add(sname);
        }

        public int GetMessageType() {
            return MessageType.SUPPLIER_LIST;
        }
    }

    // TRANSFER
    public void CourierList(ArrayList<String> topLevelInfo,
            int _userID, int _connectionID) {
        this.msg = new CourierList(_userID);
        ((CourierList) this.msg).suppList = new ArrayList(topLevelInfo);
        this.messageType = AdminMessageType.COURIER_LIST;
        this.connectionID = _connectionID;
        this.baseProt = this.msg.GetBaseProtocol();
    }

    public class CourierInfoRequest extends GetMessage {

        public int supplierID;

        public CourierInfoRequest(int _supplierID, int _userID) {
            userID = _userID;
            supplierID = _supplierID;
        }

        public int GetMessageType() {
            return AdminMessageType.REQUEST_COURIER_INFO;
        }
    }

    // GET
    public void CourierInfoRequest(int _supplierID, int _userID, int _connectionID) {
        this.msg = new CourierInfoRequest(_supplierID, _userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }

    public class CourierInfo extends TransferMessage {

        public ArrayList<Product> products;
        public int supplierID;
        public String name;

        public CourierInfo(String _name, ArrayList<Product> p, int _supplierID, int _userID) {
            supplierID = _supplierID;
            userID = _userID;
            products = p;
            name = _name;
        }

        public int GetMessageType() {
            return AdminMessageType.COURIER_INFO;
        }
    }

    // TRANSFER
    public void CourierInfo(String name, ArrayList<Product> p,
            int _supplierID, int _userID, int _connectionID) {
        this.msg = new CourierInfo(name, p, _supplierID, _userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }

    public class ListRequest extends GetMessage {

        public LIST_TYPE m_type;

        ListRequest(LIST_TYPE type, int _userID) {

        }

        @Override
        public int GetMessageType() {
            return AdminMessageType.LIST_REQUEST;
        }
    }

    public class CustomerListRequest extends GetMessage {

        public CustomerListRequest(int _userID) {
            this.userID = _userID;
        }

        public int GetMessageType() {
            return AdminMessageType.REQUEST_CUSTOMER_LIST;
        }
    }

    // GET
    public void CustomerListRequest(int _userID, int _connectionID) {
        //this.msg = new ListRequest(LIST_TYPE.CUSTOMER, _userID);
        this.msg = new CustomerListRequest(_userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }

    public class CustomerList extends TransferMessage {

        public ArrayList<String> custList;

        public CustomerList(int _userID) {
            userID = _userID;
        }

        public int GetMessageType() {
            return AdminMessageType.CUSTOMER_LIST;
        }
    }

    // TRANSFER
    public void CustomerList(ArrayList<String> topLevelInfo,
            int _userID, int _connectionID) {
        this.msg = new CustomerList(_userID);
        ((CustomerList) this.msg).custList = new ArrayList(topLevelInfo);
        this.messageType = AdminMessageType.CUSTOMER_LIST;
        this.connectionID = _connectionID;
        this.baseProt = this.msg.GetBaseProtocol();
    }

    public class CustomerInfoRequest extends GetMessage {

        public int customerID;

        public CustomerInfoRequest(int _customerID, int _userID) {
            userID = _userID;
            customerID = _customerID;
        }

        public int GetMessageType() {
            return AdminMessageType.REQUEST_CUSTOMER_INFO;
        }
    }

    // GET
    public void CustomerInfoRequest(int customerID, int _userID, int _connectionID) {
        this.msg = new CustomerInfoRequest(customerID, _userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }

    public class CustomerInfo extends TransferMessage {

        public ArrayList<Product> products;
        public int supplierID;
        public String name;

        public CustomerInfo(String _name, ArrayList<Product> p, int _supplierID, int _userID) {
            supplierID = _supplierID;
            userID = _userID;
            products = p;
            name = _name;
        }

        public int GetMessageType() {
            return AdminMessageType.CUSTOMER_INFO;
        }
    }

    // TRANSFER
    public void CustomerInfo(String name, ArrayList<Product> p,
            int _supplierID, int _userID, int _connectionID) {
        this.msg = new CustomerInfo(name, p, _supplierID, _userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }

    public class JobListRequest extends GetMessage {

        public JobListRequest(int _userID) {
            this.userID = _userID;
        }

        public int GetMessageType() {
            return AdminMessageType.REQUEST_JOB_LIST;
        }
    }

    // GET
    public void JobListRequest(int _userID, int _connectionID) {
        this.msg = new JobListRequest(_userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }

    public class JobList extends TransferMessage {

        public ArrayList<Order> j2do;

        public JobList(ArrayList<Order> jobs, int _userID) {
            userID = _userID;
            j2do = jobs;
        }

        public int GetMessageType() {
            return AdminMessageType.JOB_LIST;
        }
    }

    // TRANSFER
    public void JobList(ArrayList<Order> jobs,
            int _userID, int _connectionID) {
        this.msg = new JobList(jobs, _userID);

        this.messageType = AdminMessageType.JOB_LIST;
        this.connectionID = _connectionID;
        this.baseProt = this.msg.GetBaseProtocol();
    }

    public class JobInfoRequest extends GetMessage {

        public int supplierID;

        public JobInfoRequest(int _supplierID, int _userID) {
            userID = _userID;
            supplierID = _supplierID;
        }

        public int GetMessageType() {
            return AdminMessageType.REQUEST_JOB_INFO;
        }
    }

    // GET
    public void JobInfoRequest(int _supplierID, int _userID, int _connectionID) {
        this.msg = new JobInfoRequest(_supplierID, _userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }

    public class JobInfoMessage extends TransferMessage {

        public ArrayList<Product> products;
        public int supplierID;
        public String name;

        public JobInfoMessage(String _name, ArrayList<Product> p, int _supplierID, int _userID) {
            supplierID = _supplierID;
            userID = _userID;
            products = p;
            name = _name;
        }

        public int GetMessageType() {
            return AdminMessageType.JOB_INFO;
        }
    }

    // TRANSFER
    public void JobInfoMessage(String name, ArrayList<Product> p,
            int _supplierID, int _userID, int _connectionID) {
        this.msg = new JobInfoMessage(name, p, _supplierID, _userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }

    public class ActiveJobListRequest extends GetMessage {

        public ActiveJobListRequest(int _userID) {
            this.userID = _userID;
        }

        public int GetMessageType() {
            return AdminMessageType.ACTIVE_JOB_LIST_REQUEST;
        }
    }

    // GET
    public void ActiveJobListRequest(int _userID, int _connectionID) {
        this.msg = new ActiveJobListRequest(_userID);
        this.connectionID = _connectionID;
        this.messageType = this.msg.GetMessageType();
        this.baseProt = this.msg.GetBaseProtocol();
    }

    public class ActiveJobList extends TransferMessage {

        public ArrayList<JobInfo> j2do;

        public ActiveJobList(ArrayList<JobInfo> jobs, int _userID) {
            userID = _userID;
            j2do = jobs;
        }

        public int GetMessageType() {
            return AdminMessageType.ACTIVE_JOB_LIST;
        }
    }

    // TRANSFER
    public void ActiveJobList(ArrayList<JobInfo> jobs,
            int _userID, int _connectionID) {
        this.msg = new ActiveJobList(jobs, _userID);

        this.messageType = AdminMessageType.ACTIVE_JOB_LIST;
        this.connectionID = _connectionID;
        this.baseProt = this.msg.GetBaseProtocol();
    }

}
