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
public class Location implements java.io.Serializable {
    public float X = 0.0f;
    public float Y = 0.0f;
    public Location(float x, float y){
        X=x;
        Y=y;
    }
    public Location(){}
}
