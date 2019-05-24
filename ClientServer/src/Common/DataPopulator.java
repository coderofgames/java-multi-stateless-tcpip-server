/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author CHUWI
 */
/**
 *
 * @author CHUWI
 *
 * maybe I will need multiple instances of database for the multi-threaded form
 */
public class DataPopulator {

    public static String[] firstnames = {"John", "Amir", "Mary", "Ali", "Alex", "Sam", "Roger",
        "Joel", "Lucy", "Jane", "Charlie", "Andy", "Dave",
        "Pete", "Carol", "Emma", "Denny", "Sarah", "Frank",
        "Paul", "Michael", "Jacob"};

    public static String[] lastnames = {"Ford", "Hunt", "Harold", "Jacobs", "MacDonald", "Greaves",
        "Hamilton", "Brown", "Newton", "Benyon", "Jones", "Rogers",
        "Richardson", "Merick", "Andrews", "Smith", "Robinson",
        "London", "Elder"};

    public static String[] suppliernames = {"Caminandos", "Pizza Place", "Bens Grill",
        "Best Tandori", "Thai Food Emporium", "Fromage Frais",
        "Tasty Burghers", "Chippy Chip Shop", "Ice Cream Retreat"};

    public static String[] caminandoesProducts = {"chicken", "cajun chips", "onion rings",
        "drum sticks", "wrap"}; // 5
    public static String[] pizzaPlaceProducts = {"Cheese and Tomato", "Margerita", "Pepperoni",
        "Meat Feast", "Garlic Bread", "Coke"}; // 6
    public static String[] bensGrillProducts = {"Pork Ribs", "chicken legs", "Beef Burger",
        "Chicken Burger", "Chips"}; // 5
    public static String[] bestTandoriProducts = {"Curry for 4", "Chicken Tikka", "Vegetable Dahl",
        "Naan Bread", "Tandori Feast"}; // 5
    public static String[] thaiFoodEmporium = {"prawn soup", "lamb and rice", "chicken satays",
        "fluffy rice", "spring rolls"}; // 5
    public static String[] fromageFraisProducts = {"New York Cheese Cake", "Donut Selection",
        "Frozen Yogurt", "Waffles", "Ice Cream"}; // 5
    public static String[] tastyBurgherProducts = {"Hamburgher", "CheeseBurgher", "salty chips",
        "spicy chips", "sweet potato fries", "milkshake"}; //6
    public static String[] chippyChipShopProducts = {"Chips", "Fish", "Mushy Peas", "Sausage",
        "Battered Mars Bar", "Coke", "Sprite"}; //7
    public static String[] iceCreamRetreatProducts = {"glacial dream", "ice cream scoop", "waffle",
        "cake", "milkshake", "fruity tutti"}; // 6

    public static float[] caminandosPrices = {8.0f, 2.0f, 2.0f, 5.0f, 5.0f}; // 5
    public static float[] pizzaplacePrices = {6.0f, 6.0f, 7.0f, 10.0f, 5.0f, 1.0f}; //6
    public static float[] bensGrillPrices = {8.0f, 7.0f, 5.0f, 5.0f, 5.0f}; //5
    public static float[] bestTandoriPrices = {30.0f, 10.0f, 10.0f, 3.0f, 40.0f}; // 5
    public static float[] thaiFoodEmporiumPrices = {8.0f, 7.0f, 6.0f, 3.0f, 2.0f}; // 5
    public static float[] fromageFraisPrices = {5.0f, 14.0f, 2.0f, 4.0f, 3.0f}; // 5
    public static float[] tastyBurgherPrices = {5.0f, 6.0f, 3.0f, 3.0f, 4, 0f, 2.0f}; // 6 
    public static float[] chippyChipShopPrices = {2.0f, 4.0f, 1.0f, 3.0f, 10.0f, 1.0f, 1.0f}; //7
    public static float[] iceCreamRetreatPrices = {15.0f, 1.5f, 3.0f, 4.0f, 3.0f, 10.0f}; // 6       

    public static String[] streetNames = {"St George", "South", "East", "North Avenue", "West",
        "Pall Mall", "Rodney", "Berkley", "Hunt", "St Pauls",
        "London", "Old Lane", "The Strawberry Patch", "Hamilton"};
    public static  String[] roadNames = {"Blacksmith", "Old Monks", "Loud", "Slowdown", "Steep",
        "Newcastle", "Glasgow", "Liverpool", "Oxford", "South Wales"};

    public static  String[] caminandoAddress = {"32 Great Western St, X City Centre, XX4 4SN"};
    public static String[] pizzaPlaceAddress = {"49 Great Western St, X City Centre, XX4 4SP"};
    public static String[] bensGrillAddress = {"9 Southern End Road, X City Centre, XX5 4CC"};
    public static String[] bestTandoriAddress = {"18 London St, X City Centre, XX2 4AD"};
    public static String[] thaiFoodEmporiumAddress = {"Unit 6, Jumbo Mall, X City Centre, XX3 5EE"};
    public static String[] fromageFraisAddress = {"Unit 12, Jumbo Mall, X City Centre, XX3 4EE"};
    public static String[] tastyBurgherAddress = {"Unit 14, Jumbo Mall, X City Centre, XX3 4EE"};
    public static String[] chippChipShopAddress = {"3 South Port road, X City Centre, XX6 4RR"};
    public static String[] iceCreamRetreatAddress = {"15 South Port road, X City Centre, XX6 4RT"};

    public static ArrayList<String[]> restaurantProducts;
    public static ArrayList<float[]> restaurantPrices;
    public static ArrayList<Integer> supplierHashes;
    public static ArrayList<ArrayList<Product>> supplierProducts;
    public static ArrayList<String[]> pickupPoints;
    
    
    public static     String [] courierusernames = {"dave","mark", "john", "andy", "steve"};
    public static     String [] courierpasswords = {"psksks", "asdas", "asdff", "fkjdkd", "mkfdmkd"};
    public static int [] courierIDs = {329293,32992392,29392932,32823,299329};
        

    DataCenter dC;

    public DataPopulator(DataCenter dataCenter) {

        dC = dataCenter;

        restaurantProducts = new ArrayList();
        restaurantPrices = new ArrayList();
        supplierHashes = new ArrayList();
        supplierProducts = new ArrayList();
        pickupPoints = new ArrayList();

        restaurantProducts.add(caminandoesProducts);
        restaurantProducts.add(pizzaPlaceProducts);
        restaurantProducts.add(bensGrillProducts);
        restaurantProducts.add(bestTandoriProducts);
        restaurantProducts.add(thaiFoodEmporium);
        restaurantProducts.add(fromageFraisProducts);
        restaurantProducts.add(tastyBurgherProducts);
        restaurantProducts.add(chippyChipShopProducts);
        restaurantProducts.add(iceCreamRetreatProducts);

        restaurantPrices.add(caminandosPrices);
        restaurantPrices.add(pizzaplacePrices);
        restaurantPrices.add(bensGrillPrices);
        restaurantPrices.add(bestTandoriPrices);
        restaurantPrices.add(thaiFoodEmporiumPrices);
        restaurantPrices.add(fromageFraisPrices);
        restaurantPrices.add(tastyBurgherPrices);
        restaurantPrices.add(chippyChipShopPrices);
        restaurantPrices.add(iceCreamRetreatPrices);

        for (int j = 0; j < suppliernames.length; j++) {

            Supplier supp = new Supplier(dC,suppliernames[j],
                                        suppliernames[j],j);
            supp.name = suppliernames[j];

            ArrayList<Product> products = new ArrayList();
            for (int k = 0; k < restaurantProducts.get(j).length; k++) {
                Product p = new Product(restaurantProducts.get(j)[k],
                        restaurantPrices.get(j)[k], j);

                //p. = k;
                products.add(p);
                supp.products.add(p);
            }
            supplierProducts.add(products);

            
            supp.setConnectionID((int)Math.random()*1000);
            dC.supplierManager.AddUser(supp);
            
            dC.supplierManager.LogInUser(supp.getUserID());
            supplierHashes.add(supp.getUserID());
        }

        pickupPoints.add(caminandoAddress);
        pickupPoints.add(pizzaPlaceAddress);
        pickupPoints.add(bensGrillAddress);
        pickupPoints.add(bestTandoriAddress);
        pickupPoints.add(thaiFoodEmporiumAddress);
        pickupPoints.add(fromageFraisAddress);
        pickupPoints.add(tastyBurgherAddress);
        pickupPoints.add(chippChipShopAddress);
        pickupPoints.add(iceCreamRetreatAddress);

        for (int i = 0; i < 50; i++) {
            Customer c = RandomCustomer(i);
            if( c != null ) dC.AddActive(c);
        }
        
        
        for(int i = 0; i < courierusernames.length; i++){
            Courier c2 = new Courier(dC,courierusernames[i],
                                    courierpasswords[i],
                                    courierIDs[i]);

            c2.setConnectionID((int)Math.random()*3000);
            dC.courierManager.AddUser(c2);
        } 
        
        
        
        
        dC.CreateAdmin("Dave", "1234", 1234);

    }

    Customer RandomCustomer(int userID) {
        Customer c = new Customer(dC);
        c.dC = dC;
        c.setUserID(userID);
        c.setConnectionID((int)Math.random()*100000);
        c.setFirstName(firstnames[(int) (Math.random() * firstnames.length)] );
        c.setSecondName( lastnames[(int) (Math.random() * lastnames.length)] );
        c.setUserName( c.getFirstName());
        c.setPassword( c.getSecondName());

        if (Math.random() > 0.5) {
            c.SetAddressLine0((Math.random() * 100) + " " + streetNames[(int) (Math.random() * streetNames.length)]);
        } else {
            c.SetAddressLine0( (int) (Math.random() * 100) + " " + roadNames[(int) (Math.random() * roadNames.length)]);
        }

        c.SetAddressLine1("X City" );
        c.SetPostCode("XXX XXX" );
        

        return c;
    }

    Set<Integer> CustomerHashes() {
        return dC.customerManager.onlineUsers.table.keySet();
    }

    int RandomRestaurant() {
        return (int) (Math.random() * suppliernames.length);
    }

    int RandomProduct(int restaurant) {
        return (int) Math.random() * restaurantProducts.get(restaurant).length;
    }

    DateTime RandomTime() {
        DateTime dtime = new DateTime();
        dtime.date.monthOfYear = (int) (Math.random() * 12);
        dtime.date.dayOfMonth = (int) (Math.random() * 30);
        dtime.time.minuteOfHour = (int) (Math.random() * 60);
        dtime.time.hourOfDay = (int) (Math.random() * 12);
        dtime.time.minuteOfHour = (int) (Math.random() * 60);
        return dtime;
    }

    Order RandomOrder(Customer c, int key) {
        Order o = new Order();
        o.customerID = c.getUserID();

        o.deliveryTime = RandomTime();
        int restaurantPick = RandomRestaurant();
        o.supplierID = supplierHashes.get(restaurantPick);

        o.deliverAddress = c.copyAddress();

        int numProducts = (int) (Math.random() * restaurantProducts.get(restaurantPick).length * 2);

        for (int j = 0; j < numProducts; j++) {
            int productChoice = RandomProduct(restaurantPick);
         //   o.productsString += ", " + supplierProducts.get(restaurantPick).get(productChoice);
        }

        o.transactionRecord.SetTransactionNumber(key);

        return o;
    }

    void PopulateWithOrders() {

        Set<Integer> keyArray = CustomerHashes();

        int i = 0;
        for (Iterator it = keyArray.iterator(); it.hasNext();i++) {

            int key = (Integer) it.next();
            Customer c = dC.GetActiveCustomer(i);

            Order o = RandomOrder(c, key);

            dC.paymentManager.AddPayment(o.transactionRecord);

            /*if (dC.PlaceOrder(o) == false) {
                System.out.println("Failed to Place Order");
            }*/
        }

        /*Job job = null;

        for (Iterator jobIt = dC.jobManager.jobsTodo.jobs.iterator(); 
                jobIt.hasNext();  ){
            
            job = (Job)jobIt.next();
            
            if(job!=null){
                String s1 = "Customer Name: " + job.customer.personName.firstName + " " + job.customer.personName.lastName;
                System.out.println(s1);
                String s2 = "Customer Hash: " + job.customer.activityHash;
                System.out.println(s2);
                String s3 ="Supplier Hash: " + job.supplier.activityHash;
                System.out.println(s3);
                String s4 = "Supplier Name: " + job.supplier.businessName;
                System.out.println(s4);
                System.out.println("------------------------------------------------");
            
            }
            else System.out.println("Job is null");
          
            //jobManager.jobsTodo.jobs.remove(job);
        }        
         */
    }

}
