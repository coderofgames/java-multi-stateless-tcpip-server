/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author CHUWI
 */
public class JobList extends ObjectList<Job>{
    public JobList(){
        super();
    }
    
    Set<Map.Entry<Integer,Job>> GetEntrySet(){
        return table.entrySet();
    }    
    
}
