/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

/**
 *
 * @author CHUWI
 */
public class Product implements java.io.Serializable{
    public String name = "";
    public float price = 0.0f;
    int supplierID=0;
    
    public Product(String _name, float _price, int suppID){
        name = _name;
        price = _price;
        supplierID = suppID;
    }
}
