# java-multi-stateless-tcpip-server
WIP stateless multi threaded web server
   
This project is an information system for a Courier service. The main theme is food delivery, where the model is for the Customer to   
place an order on a web interface causing the datacenter to create a job. The Courier(s) selects a job from the list of available jobs    
and then travels to the restaurant to pick up the food, once the food is picked up, the Courier informs the datacenter then travels    
to the customer to deliver.    
   
The design uses TCP-IP to implememnt a stateless multi-threaded web server. The system is stateless because the server does    
not maintain the TCP connection for the clients - the data for the connection is maintained by a connection id number.    
The reason for this is to avoid unstable threads caused in stateful connections when the client and server lose their physical link.   
   
The multi-threaded aspect of the design attempts to utlize the multi-core processors of modern web servers to manage concurrent 
Client and Courier connections without causing the problems associated with queueing.
   
### Running

1. Open the project in Netbeans. (8.2)   
2. Right click the file OrgServer.java and select "Run File". This should start the server process - click the output bar at the bottom 
of the screen to see the output.   
3. Now do the same for AdminClient.java - another output process should appear and both outputs should list details of the connection. 
Ignore the interactive options for now.
4. Do the same for at least one Client.java process, CourierClient.java and SupplierClient.java process.
5. Now select the output for the Client.java process and run through the interactive output to place an order.
6. The lists of Couriers, Jobs, etc should be viewable using the Admin.java process interactive output.
7. Now load another Courier.java process - a selection of available jobs should appear, with the order id of the order you placed earlier.    
enter the number in the list to set the Courier to deliver the order.
    
### Bugs

I am not sure if the Courier will delivery the order now.
