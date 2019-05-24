/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerProcess;

import Common.AdminSharedData;
import Common.CourierSharedData;
import Common.CustomerSharedData;
import Common.DataCenter;
import Common.SupplierSharedData;
import Common.UserTypes;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author CHUWI
 */
public class OrgServer extends Server {

    UserTypes userType;
    DataCenter dC = null;

    public OrgServer(int port, UserTypes _userType, DataCenter dataCenter) {
        super(port);
        dC = dataCenter;
        userType = _userType;

    }

    @Override
    public void HandleSocketAccept(Socket clientSocket) {
        ClientServiceThreadFactory.CreateUserServiceThread((Server) this, clientSocket, userType, dC);
    }

    static Server[] s = {null, null, null, null};

    public static void main(String[] args) {
        DataCenter dC = new DataCenter();
        s[0] = new OrgServer(CustomerSharedData.CUSTOMER_PORT, UserTypes.CUSTOMER, dC);
        s[0].start();
        s[1] = new OrgServer(SupplierSharedData.SUPPLIER_PORT, UserTypes.SUPPLIER, dC);
        s[1].start();
        s[2] = new OrgServer(CourierSharedData.COURIER_PORT, UserTypes.COURIER, dC);
        s[2].start();
        s[3] = new OrgServer(AdminSharedData.ADMIN_PORT, UserTypes.ADMIN, dC);
        s[3].start();
    }
}
