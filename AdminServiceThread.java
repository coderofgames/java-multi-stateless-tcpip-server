/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerProcess;

import Common.Admin;
import Common.Courier;
import Common.Customer;
import Common.DataCenter;
import Common.Message;
import Common.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author CHUWI
 */
class AdminServiceThread extends UserServiceThread {



    public AdminServiceThread(Server s) {
        super(s);

    }

    AdminServiceThread(Server _server, Socket s, DataCenter dataCenter) {
        super(_server, s, dataCenter);
    }

    int STATE = 0;
    
    public User GetUserType(String username, String password){
        return (User)dC.GetAdmin(username, password);
    }


    
    public int CreateUserType(String username, String password,int userID){
        return dC.CreateAdmin(username, password,
                userID);
    }


    
    public User GetOnlineUserByConnectionID(int connectionID){
        return (User)dC.GetOnlineAdminByConnectionID(connectionID);
    }
    
    public void ResumeExistingSession(Message m) {
        Admin c = (Admin)GetOnlineUserByConnectionID(m.connectionID);

        if (c != null) {

            c.HandleMessages(m, ous);
        } else {

            //out.println("CONNECTION REFUSED");
        }
    }


    public Boolean UserExists(String username, String password){
        return dC.CustomerExists(username, password);
    }

}
