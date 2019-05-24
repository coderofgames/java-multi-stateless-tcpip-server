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
public class LoginDetail {

    private String username;
    private String password;
    private Integer userID;

    public synchronized String getUserName() {
        return username;

    }

    public synchronized void setUserName(String _username) {
        username = _username;

    }

    public synchronized String getPassword() {
        return password;

    }

    public synchronized void setPassword(String _password) {
        password = _password;

    }

    public synchronized Integer getUserID() {
        return userID;

    }

    public synchronized void setUserID(Integer _userID) {
        userID = _userID;

    }
}
