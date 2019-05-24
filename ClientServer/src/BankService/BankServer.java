/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BankService;

import Common.AdminSharedData;
import Common.CourierSharedData;
import Common.CustomerSharedData;

import Common.SupplierSharedData;
import Common.UserTypes;
import ServerProcess.Server;

import java.net.Socket;

/**
 *
 * @author CHUWI
 */
public class BankServer extends Server {



    public BankServer(int port) {
        super(port);


    }

    @Override
    public void HandleSocketAccept(Socket clientSocket) {
        BankServiceThread bsT = new BankServiceThread((Server)this,clientSocket);
        bsT.start();
    }


    static Server s = null;

    public static void main(String[] args) {
 
        s = new BankServer(1111);
        s.start();
    }
}

