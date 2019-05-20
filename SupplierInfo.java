/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.util.ArrayList;

/**
 *
 * @author CHUWI
 */
public class SupplierInfo implements java.io.Serializable {
    public String name = "";
    public ArrayList<Product> currentProductList = new ArrayList();
    public Address address = new Address();
}
