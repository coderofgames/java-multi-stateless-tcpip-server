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
public class DateTime implements java.io.Serializable{
    public class Date implements java.io.Serializable{
        public int monthOfYear;
        public int dayOfMonth;
    }
    public class Time implements java.io.Serializable{
        public int hourOfDay;
        public int minuteOfHour;
    }
    
    public Date date= new Date();
    public Time time= new Time();
    
    public DateTime(){
    }
    
    public int Month(){
        return date.monthOfYear;
    }
    public int Day(){
        return date.dayOfMonth;
    }
    
    public int Hour(){
        return time.hourOfDay;
    }
    
    public int Minute(){
        return time.minuteOfHour;
    }
}
