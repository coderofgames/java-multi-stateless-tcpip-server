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
public class Job {
    
    public Customer customer = null;
    public Supplier supplier = null;
    public Courier courier = null;
    public Order order = null;
    public DataCenter dC = null;
    

    
    public Job(Customer c, Supplier s, Order o, DataCenter dataCenter){
        customer = c;
        supplier = s;
        order = o;
        courier = null;
        dC = dataCenter;
    }
    
    public synchronized void AssignCourier(Courier c){
        courier = c;
        
    }
    
    public JobInfo GetJobInfo(){
        return new JobInfo(this);
    }
    
    
}
