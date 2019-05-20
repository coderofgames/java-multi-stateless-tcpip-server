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
public class CustomerShoppingCart { // NOT USED
    
    public ArrayList<Order> shoppingCart = new ArrayList();

    public void AddToCart(Customer c, int supplierID, int productID){
        for( int i = 0; i < shoppingCart.size(); i++ ){
            if( shoppingCart.get(i).supplierID == supplierID ){
                shoppingCart.get(i);
            }
        }
    }    
}
