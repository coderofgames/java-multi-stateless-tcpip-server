/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerProcess;


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




public abstract class Server extends Thread {

    public ServerSocket myServerSocket;

    public boolean ServerOn = true;

    public Server(int port) {

        try {
            myServerSocket = new ServerSocket(port);
        } catch (IOException ioe) {
            System.out.println("Could not create server socket on port 1234. Quitting.");
            System.exit(-1);
        }
    }

    public abstract void HandleSocketAccept(Socket clientSocket);

    public void run() {

        Calendar now = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(
                "E yyyy.MM.dd 'at' hh:mm:ss a zzz");

        System.out.println("It is now : " + formatter.format(now.getTime()));

        while (ServerOn) {
            try {
                Socket clientSocket = myServerSocket.accept();
                HandleSocketAccept(clientSocket);
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
}
