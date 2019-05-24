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

import Common.Customer;
import Common.DataCenter;
import Common.Message;
import Common.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CHUWI
 */
public abstract class UserServiceThread extends Thread {

    public Socket myClientSocket;
    public boolean m_bRunThread = true;
    public BufferedReader in = null;
    public PrintWriter out = null;
    public ObjectInputStream ois = null;
    public ObjectOutputStream ous = null;

    public DataCenter dC;
    public Server server;
    int STATE = 0;

    public UserServiceThread(Server s) {
        super();
        server = s;
    }

    UserServiceThread(Server _server, Socket s, DataCenter dataCenter) {
        myClientSocket = s;
        dC = dataCenter;
        server = _server;
    }

    public abstract User GetUserType(String username, String password);

    public abstract int CreateUserType(String username, String password, int userID);

    public abstract User GetOnlineUserByConnectionID(int connectionID);

    public abstract Boolean UserExists(String username, String password);

    public void run() {

        if (!server.ServerOn) {
            System.out.print("Server has already stopped");
            m_bRunThread = false;
            try {
                myClientSocket.close();
                return;
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        System.out.println(
                "Accepted Client Address - " + myClientSocket.getPort());//getInetAddress().getHostName()
        try {
            in = new BufferedReader(
                    new InputStreamReader(myClientSocket.getInputStream()));

            out = new PrintWriter(
                    new OutputStreamWriter(myClientSocket.getOutputStream()));

            ois = new ObjectInputStream(myClientSocket.getInputStream());

            ous = new ObjectOutputStream(myClientSocket.getOutputStream());

            System.out.println("State = 0");

            Message m = (Message) ois.readObject();

            String clientCommand;// = in.readLine();
            if (m != null) {

                // always logging in first
                if (m.GetType() == Message.MessageType.LOGIN) {
                    Message.LoginMessage msg = (Message.LoginMessage) m.msg;

                    // if the customer exists, then the customer is logged in ...
                    if (UserExists(msg.username, msg.password)) {

                        HandleExistingUser(msg.username, msg.password);

                    } else {

                        CreateUser(msg.username, msg.password, msg.userID);
                    }

                } else {

                    // connection is already established, if
                    ResumeExistingSession(m);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.println("Server Says : Stopping!");
                myClientSocket.close();
                in.close();
                out.close();

                System.out.println("...Stopped");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public void HandleExistingUser(String username, String password) {
        User c = GetUserType(username, password);
        if (c != null) {

            Message m = new Message();
            m.messageType = Message.MessageType.ACCEPT_LOGIN;
            m.baseProt = Message.BaseProtocol.YES_NO;
            try {
                this.ous.writeObject(m);
            } catch (IOException ex) {
                ex.printStackTrace();
                // Logger.getLogger(UserServiceThread.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("Connection ID: " + c.getConnectionID()
                    + ", " + c.GetUserType() + "ID: " + c.getUserID());

            m = null;

        } else {
            Message m = new Message();
            m.messageType = Message.MessageType.REJECT_LOGIN;
            m.baseProt = Message.BaseProtocol.YES_NO;
            try {
                this.ous.writeObject(m);
            } catch (IOException ex) {
                ex.printStackTrace();
                // Logger.getLogger(UserServiceThread.class.getName()).log(Level.SEVERE, null, ex);
            }

            m = null;
        }
        out.flush();
    }

    public void CreateUser(String username,
            String password, int userID) {
        // create the customer
        int connectionID = CreateUserType(username, password, userID);

        System.out.println("Connection Accepted");
        System.out.println("username: " + username
                + ", password: " + password);
        System.out.println("Connection ID: " + connectionID
                + ", CustomerID: " + userID);

        Message m = new Message();
        m.messageType = Message.MessageType.ACCEPT_LOGIN;
        m.baseProt = Message.BaseProtocol.YES_NO;
        m.connectionID = connectionID;

        try {
            this.ous.writeObject(m);
        } catch (IOException ex) {
            ex.printStackTrace();
            // Logger.getLogger(UserServiceThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public abstract void ResumeExistingSession(Message m);
}
