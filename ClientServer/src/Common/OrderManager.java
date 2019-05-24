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
public class OrderManager {
    public OrderList ordersTodo = new OrderList();
    public OrderList currentOrders= new OrderList();
    public OrderList completeOrders= new OrderList();
    
    public void AddOrderTodo(Order o){
        ordersTodo.AddOrder( o);
    }
    
    public Order GetOrderByCustomerID(int customerID){
        return ordersTodo.Get(customerID);
    }
    
    public void StartOrder(int customerID){
        currentOrders.AddOrder(ordersTodo.Remove(customerID));
    }
    
    public void CompleteOrder(int customerID){
        completeOrders.AddOrder(currentOrders.Remove(customerID));
    }
}
