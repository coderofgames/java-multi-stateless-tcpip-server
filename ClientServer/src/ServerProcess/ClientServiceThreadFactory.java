/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerProcess;

import Common.DataCenter;
import Common.UserTypes;
import java.net.Socket;

/**
 *
 * @author CHUWI
 */
public class ClientServiceThreadFactory {
    public static void CreateUserServiceThread(Server server, Socket clientSocket, UserTypes userType, DataCenter dC) {
        switch (userType) {
            case CUSTOMER: {
                CustomerServiceThread cliThread = new CustomerServiceThread(server, clientSocket, dC);
                cliThread.start();
                break;
            }
            case SUPPLIER: {
                SupplierServiceThread cliThread = new SupplierServiceThread(server, clientSocket, dC);
                cliThread.start();
                break;
            }
            case COURIER: {
                CourierServiceThread cliThread = new CourierServiceThread(server, clientSocket, dC);
                cliThread.start();
                break;
            }
            case ADMIN: {
                AdminServiceThread cliThread = new AdminServiceThread(server, clientSocket, dC);
                cliThread.start();
                break;
            }
        }
    }    
}
