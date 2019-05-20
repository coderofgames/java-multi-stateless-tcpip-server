/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerProcess;

import Common.Admin;
import Common.AdminSharedData;
import Common.Courier;
import Common.CourierSharedData;
import Common.Customer;
import Common.CustomerSharedData;
import Common.DataCenter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.io.*;
import Common.Order;
import Common.Supplier;
import Common.SupplierSharedData;
import Common.User;
import Common.UserTypes;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

public class Server extends Thread {

    ServerSocket myServerSocket;

    boolean ServerOn = true;
    UserTypes userTypes;
    DataCenter dC = null;

    public Server(int port, UserTypes _userTypes, DataCenter dataCenter) {
        dC = dataCenter;
        try {
            myServerSocket = new ServerSocket(port);

            userTypes = _userTypes;

        } catch (IOException ioe) {
            System.out.println("Could not create server socket on port 1234. Quitting.");
            System.exit(-1);
        }
    }

    public void run() {

        Calendar now = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(
                "E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        System.out.println("It is now : " + formatter.format(now.getTime()));
        
        while (ServerOn) {
            try {
                Socket clientSocket = myServerSocket.accept();
                switch (userTypes) {
                    case CUSTOMER: {
                        CustomerServiceThread cliThread = new CustomerServiceThread(this, clientSocket, dC);
                        cliThread.start();
                        break;
                    }
                    case SUPPLIER: {
                        SupplierServiceThread cliThread = new SupplierServiceThread(this, clientSocket, dC);
                        cliThread.start();
                        break;
                    }
                    case COURIER: {
                        CourierServiceThread cliThread = new CourierServiceThread(this, clientSocket, dC);
                        cliThread.start();
                        break;
                    }
                    case ADMIN: {
                        AdminServiceThread cliThread = new AdminServiceThread(this, clientSocket, dC);
                        cliThread.start();
                        break;
                    }
                }

            } catch (IOException ioe) {
                System.out.println("Exception found on accept. Ignoring. Stack Trace :");
                ioe.printStackTrace();
            }
        }
        try {
            myServerSocket.close();
            System.out.println("Server Stopped");
        } catch (Exception ioe) {
            System.out.println("Error Found stopping server socket");
            System.exit(-1);
        }
    }

    static Server[] s = {null, null, null, null};
    

    public static void main(String[] args) {
        DataCenter dC = new DataCenter();
       s[0] = new Server( CustomerSharedData.CUSTOMER_PORT, UserTypes.CUSTOMER, dC);
        s[0].start();
        s[1] = new Server( SupplierSharedData.SUPPLIER_PORT, UserTypes.SUPPLIER, dC);
        s[1].start();
        s[2] = new Server( CourierSharedData.COURIER_PORT, UserTypes.COURIER , dC);
        s[2].start();
        s[3] = new Server( AdminSharedData.ADMIN_PORT, UserTypes.ADMIN, dC);
        s[3].start();
    }

    /**
     *
     */
}
