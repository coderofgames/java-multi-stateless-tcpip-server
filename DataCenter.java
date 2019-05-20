/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author CHUWI
 */
public class DataCenter {

    public CustomerManager customerManager = new CustomerManager();
    public CourierManager courierManager = new CourierManager();
    public SupplierManager supplierManager = new SupplierManager();
    public JobManager jobManager = new JobManager();
    public OrderManager orderManager = new OrderManager();
    public PaymentManager paymentManager = new PaymentManager(this);
    public AdminManager adminManager = new AdminManager();

    public DataCenter() {

        LoginDetail customer = new LoginDetail();
        customer.setUserName("Dave");
        customer.setPassword("power2W");
        customerManager.AddLogin(customer);

        DataPopulator populator = new DataPopulator(this);
        // populator.PopulateWithOrders();

    }

    public void AddCustomerAccount(LoginDetail login) {
        customerManager.AddLogin(login);
    }

    public void AddCourierAccount(LoginDetail login) {
        courierManager.AddLogin(login);
    }

    public void AddSupplierAccount(LoginDetail login) {
        supplierManager.AddLogin(login);
    }

    public void AddAdminAccount(LoginDetail login) {
        adminManager.AddLogin(login);
    }

    public Boolean CreateJob(Order o, Customer c) {
        Supplier s = (Supplier) supplierManager.GetOnlineUser(o.GetSupplierID());
        if (s == null) {
            return false; // ?
        }
        customerManager.AddIdleUser(c);

        Job job = jobManager.CreateJob(c, s, o, this);
        if (job == null) {
            return false;
        }

        c.SetActiveJob(job);
        
        return true;
    }

    public Boolean PlaceOrder(Order o, Customer c) {

        try {
            //if (this.paymentManager.Validate(o.transactionRecord)) {
            if (true) {

                if (c == null) {
                    return false;
                }
                Supplier s = (Supplier) supplierManager.GetOnlineUser(o.GetSupplierID());
                if (s == null) {
                    return false; // ?
                }
                customerManager.AddIdleUser(c);

                Job job = jobManager.CreateJob(c, s, o, this);
                if (job == null) {
                    return false;
                }

                c.SetActiveJob(job);

                return true;

            } else {

                // orders todo will be checked by the payment transaction thread.
                orderManager.AddOrderTodo(o);

                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public int AddCourier(Courier c) {
        c.setConnectionID((int) (Math.random() * 10000));
        courierManager.AddUser(c);
        courierManager.LogInUser(c.getUserID());
        return c.getConnectionID();
    }

    public void LoginCourier(int userID) {

        courierManager.LogInUser(userID);
    }

    private LoginDetail CreateLoginDetail(String username, String password, int userID) {
        LoginDetail cL = new LoginDetail();
        cL.setUserName(username);
        cL.setPassword(password);
        cL.setUserID(userID);

        return cL;
    }

    private int AddToManager(UserManager uMan, User u) {
        u.setConnectionID((int) (Math.random() * 10000));
        uMan.AddUser(u);
        uMan.LogInUser(u.getUserID());
        return u.getConnectionID();
    }

    public int CreateCustomer(String username, String password, int userID) {

        AddCustomerAccount(CreateLoginDetail(username, password, userID));

        Customer customer = new Customer(this, username, password, userID);

        return AddToManager(customerManager, customer);
    }

    public int CreateCourier(String username, String password, int userID) {
        AddCourierAccount(CreateLoginDetail(username, password, userID));

        Courier courier = new Courier(this, username, password, userID);

        return AddToManager(courierManager, courier);
    }

    public int CreateSupplier(String username, String password, int userID) {
        AddSupplierAccount(CreateLoginDetail(username, password, userID));

        Supplier supp = new Supplier(this, username, password, userID);

        return AddToManager(supplierManager, supp);
    }

    public int CreateAdmin(String username, String password, int userID) {
        AddAdminAccount(CreateLoginDetail(username, password, userID));

        Admin admin = new Admin(this, username, password, userID);

        return AddToManager(adminManager, admin);
    }

    public Boolean CustomerExists(String username, String password) {
        return customerManager.UserExists(username, password);
    }

    public Boolean CourierExists(String username, String password) {
        return courierManager.UserExists(username, password);
    }

    public Boolean SupplierExists(String username, String password) {
        return supplierManager.UserExists(username, password);
    }

    public Boolean AdminExists(String username, String password) {
        return adminManager.UserExists(username, password);
    }

    public Customer GetActiveCustomer(int customerID) {
        return (Customer) customerManager.GetActiveUser(customerID);
    }

    public Customer GetOnlineCustomer(int userID) {
        return (Customer) customerManager.GetOnlineUser(userID);
    }

    public Supplier GetOnlineSupplier(int userID) {
        return (Supplier) supplierManager.GetOnlineUser(userID);
    }

    public Customer GetOnlineCustomerByConnectionID(int connectionID) {
        return (Customer) customerManager.GetOnlineUserByConnectionID(connectionID);
    }

    public Courier GetOnlineCourierByConnectionID(int connectionID) {
        return (Courier) courierManager.GetOnlineUserByConnectionID(connectionID);
    }

    public Supplier GetOnlineSupplierByConnectionID(int connectionID) {
        return (Supplier) supplierManager.GetOnlineUserByConnectionID(connectionID);
    }

    public Admin GetOnlineAdminByConnectionID(int connectionID) {
        return (Admin) adminManager.GetOnlineUserByConnectionID(connectionID);
    }

    public void AddActive(Customer c) {
        customerManager.AddActiveUser(c);
    }

    public Customer GetCustomer(String username, String password) {
        return (Customer) customerManager.GetUser(username, password);
    }

    public Courier GetCourier(String username, String password) {
        return (Courier) courierManager.GetUser(username, password);
    }

    public Supplier GetSupplier(String username, String password) {
        return (Supplier) supplierManager.GetUser(username, password);
    }

    public Admin GetAdmin(String username, String password) {
        return (Admin) adminManager.GetUser(username, password);
    }

    public Supplier GetSupplier(int userID) {
        return (Supplier) supplierManager.GetOnlineUser(userID);
    }

    public Courier GetCourier(int userID) {
        return (Courier) courierManager.GetUser(userID);
    }

    public int NumOnlineSuppliers() {
        return supplierManager.onlineUsers.table.size();
    }

    public int NumOnlineCouriers() {
        return courierManager.onlineUsers.table.size();
    }

    public int NumOnlineCustomers() {
        return customerManager.onlineUsers.table.size();
    }

}
