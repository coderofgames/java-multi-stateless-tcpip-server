/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author CHUWI
 */
public class JobManager {
    public JobList activeJobs = new JobList();
    public JobList completeJobs = new JobList();
    
    public JobList jobsTodo = new JobList();
    
    public Job CreateJob(Customer c, Supplier s, Order o, DataCenter dataCenter){
        Job job = new Job(c,s,o,dataCenter);
        jobsTodo.Put(job.hashCode(), job);
        o.SetJobID(job.hashCode());
        return job; 
    }
    
    public Job SetJobActive(int jobID){
        Job job = jobsTodo.Remove(jobID);
        activeJobs.Put(jobID, job);
        return job;
    }
    
    public ArrayList<Order> GetJobsTodo(ArrayList<Order> sL){
        Set<Map.Entry<Integer,Job>> eSet = jobsTodo.GetEntrySet();
        Iterator it = eSet.iterator();
        while(it.hasNext()){
            Map.Entry<Integer,Job> e = (Map.Entry)it.next();
            Order o = e.getValue().order;
            sL.add(o);
            //sL.add(  ((Customer)e.getValue() ).getUserID()+": "+( (Customer)e.getValue() ).getFirstName() + ", " + ((Customer)e.getValue() ).getSecondName());
        }
        return sL;
    }  
    
    public ArrayList<JobInfo> GetActiveJobs(ArrayList<JobInfo> sL){
        Set<Map.Entry<Integer,Job>> eSet = activeJobs.GetEntrySet();
        Iterator it = eSet.iterator();
        while(it.hasNext()){
            Map.Entry<Integer,Job> e = (Map.Entry)it.next();
            
            sL.add(e.getValue().GetJobInfo());
            //sL.add(  ((Customer)e.getValue() ).getUserID()+": "+( (Customer)e.getValue() ).getFirstName() + ", " + ((Customer)e.getValue() ).getSecondName());
        }
        return sL;
    }     
    
}
