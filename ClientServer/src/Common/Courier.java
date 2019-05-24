/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CHUWI
 */
public class Courier extends User {

    public CourierSharedData.VehicleType thisVehicle = CourierSharedData.VehicleType.BICYCLE;

    public int jobID = 0;
    public int mapEdge;
    public Location loc;

    public Courier(DataCenter dataCenter) {
        super(dataCenter);
    }

    public Courier(DataCenter dataCenter, String _username,
            String _password, int _userID) {
        super(dataCenter, _username, _password, _userID);
    }

    public UserTypes GetUserType() {
        return UserTypes.COURIER;
    }

    public synchronized Location GetLocation() {
        return loc;
    }

    public void SetLocation(float X, float Y) {
        synchronized (loc) {
            loc.X = X;
            loc.Y = Y;
        }
    }

    public synchronized void SetJobID(int _jobID) {
        jobID = _jobID;
    }

    public synchronized int GetJobID() {
        return jobID;
    }

    public synchronized void SetVehicleType(CourierSharedData.VehicleType type) {
        thisVehicle = type;
    }

    public synchronized CourierSharedData.VehicleType GetVehicleType() {
        return thisVehicle;
    }

    public void HandleJobRequest(Message m, ObjectOutputStream ous) {
        System.out.println("Courier requests Jobs");
        //out.println("JOB_OFFER");
        //out.println(5); // JobID

        CourierMessage response = new CourierMessage();
        ArrayList<Order> sL = new ArrayList();
        response.JobOfferToCourier(dC.jobManager.GetJobsTodo(sL), 0, this.getUserID(), this.getConnectionID());

        try {
            System.out.println("Writing job list to Courier");
            ous.writeObject(response);
        } catch (IOException ex) {
            System.out.println("Exception");
            ex.printStackTrace();
            // Logger.getLogger(Courier.class.getName()).log(Level.SEVERE, null, ex);
        }

        sL = null;
    }

    public void HandleAcceptJob(Message m, ObjectOutputStream ous) {
        System.out.println("Handling Courier Job Acceptance");
        CourierMessage.CourierAcceptJob aj = (CourierMessage.CourierAcceptJob) m.msg;
        //dC.jobManager.jobsTodo.Get(aj.jobID);

        Job job = dC.jobManager.SetJobActive(aj.jobID);
        job.AssignCourier(this);
        this.SetJobID(aj.jobID);

        CourierMessage response = new CourierMessage();
        response.GeneralAck(this.getUserID(), this.getConnectionID());

        try {
            ous.writeObject(response);
            //int supplierID = Integer.parseInt(in.readLine());
            //Supplier s = dC.GetSupplier(supplierID);
            //out.println(s.name);
            // out.write(s.address);
            //out.println(s.activeProducts.size());
            //out.write(s.products);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void HandleArriveAtSupplier(Message m, ObjectOutputStream ous) {
        System.out.println("Courier Arrived At Supplier");
        CourierMessage.CourierArriveAtSupplier aj = (CourierMessage.CourierArriveAtSupplier) m.msg;
        //dC.jobManager.jobsTodo.Get(aj.jobID);

        Job job = dC.jobManager.activeJobs.Get(this.GetJobID());

        //Job job = dC.jobManager.SetJobActive(aj.jobID);
//        job.AssignCourier(this);
        //this.SetJobID(aj.jobID);
        CourierMessage response = new CourierMessage();
        response.GeneralAck(this.getUserID(), this.getConnectionID());
        try {
            ous.writeObject(response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void HandleOrderCollected(Message m, ObjectOutputStream ous) {
        System.out.println("Courier Collects the order");
        
        CourierMessage response = new CourierMessage();
        response.GeneralAck(this.getUserID(), this.getConnectionID());
        try {
            ous.writeObject(response);

        } catch (Exception e) {
            e.printStackTrace();
        }        
    }

    public void HandleArriveAtCustomer(Message m, ObjectOutputStream ous) {
        System.out.println("Courier Arrives at Customer");
        
        CourierMessage response = new CourierMessage();
        response.GeneralAck(this.getUserID(), this.getConnectionID());
        try {
            ous.writeObject(response);

        } catch (Exception e) {
            e.printStackTrace();
        }        
    }

    public void HandleOrderDelivered(Message m, ObjectOutputStream ous) {
        System.out.println("Courier Delivers Order");
        
        
        CourierMessage response = new CourierMessage();
        response.GeneralAck(this.getUserID(), this.getConnectionID());
        try {
            ous.writeObject(response);

        } catch (Exception e) {
            e.printStackTrace();
        }        
    }

    public void HandleMessages(Message m, ObjectOutputStream ous) {

        switch (m.GetType()) {
            case CourierMessage.CourierMessageType.COURIER_REQUEST_JOB_OFFER: {
                HandleJobRequest(m, ous);
                break;
            }
            case CourierMessage.CourierMessageType.COURIER_ACCEPT_JOB: {
                HandleAcceptJob(m, ous);
                break;
            }
            case CourierMessage.CourierMessageType.COURIER_ARRIVE_AT_SUPPLIER: {
                HandleArriveAtSupplier(m, ous);
                break;
            }
            case CourierMessage.CourierMessageType.COURIER_COLLECT_ORDER: {
                HandleOrderCollected(m, ous);
                break;
            }
            case CourierMessage.CourierMessageType.COURIER_ARRIVE_AT_CUSTOMER: {
                HandleArriveAtCustomer(m, ous);
                break;
            }
            case CourierMessage.CourierMessageType.COURIER_DELIVER_ORDER: {
                HandleOrderDelivered(m, ous);
                break;
            }
        }

    }

}
