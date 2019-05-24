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
public class Address implements java.io.Serializable{

    public Address() {
        addressLines = new String[4];

    }
    
    public Address(Address a){
        addressLines = new String[4];
        this.addressLines[0] = a.GetAddressLine0();
        this.addressLines[1] = a.GetAddressLine1();
        this.addressLines[2] = a.GetAddressLine2();
        this.addressLines[3] = a.GetAddressLine3();
        this.postcode = a.GetPostcode();
        
    }
    
    public synchronized String GetPostcode(){
        return postcode;
    }
    
    public synchronized void SetPostcode(String pcode){
        this.postcode = pcode;
    }
    
    public synchronized void SetAddressLine0( String line ){
        addressLines[0] = line;
    }
    public synchronized String GetAddressLine0(  ){
        return addressLines[0];
    }    
    
    public synchronized void SetAddressLine1( String line ){
        addressLines[1] = line;
    }
    public synchronized String GetAddressLine1(  ){
        return addressLines[1];
    }        
    
    public synchronized void SetAddressLine2( String line ){
        addressLines[2] = line;
    }
    public synchronized String GetAddressLine2(  ){
        return addressLines[2];
    }        
    
    public synchronized void SetAddressLine3( String line ){
        addressLines[3] = line;
    }
    public synchronized String GetAddressLine3(  ){
        return addressLines[3];
    }        
    
    public String[] addressLines;
    public String postcode = "";
}
