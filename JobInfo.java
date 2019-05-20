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
public class JobInfo implements java.io.Serializable {

    public int customerID;
    public int supplierID;
    public int courierID;
    public int orderNo;

    public JobInfo(Job job) {
        customerID = job.customer.getUserID();
        supplierID = job.supplier.getUserID();
        courierID = job.courier.getUserID();
        //customerID = job.order.getUserID();
    }
}
