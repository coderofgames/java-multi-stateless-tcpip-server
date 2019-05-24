/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BankService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;

/**
 *
 * @author CHUWI
 */
public class BankMessage {
    
    public static String MessageBank(String command, int second) {

        try {
            Socket sock = new Socket("localhost", 1111);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(sock.getInputStream()));
            PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(sock.getOutputStream()));

            out.println(command);
            out.println(second); // or something
            return in.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;

    }
    
    
}
