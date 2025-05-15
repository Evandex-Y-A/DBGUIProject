/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Timestamp;
import java.util.Optional;

/**
 *
 * @author evandex
 */

public class User {
    private int userID;
    private String username;
    private String email;
    private String password;
    private Timestamp creationDate;
    
    public User() {}
    
    public User(int user_id, String name, Optional<String> emailOpt, Optional<String> passOpt, Optional<String> dateOpt) {
        String email = emailOpt.orElse("");
        String password = passOpt.orElse("");
        String date = dateOpt.orElse("");
        userID = user_id;
        username = name;
    }
    
    public int getID() {
        return userID;
    }
    public void setID(Integer ID) {
        this.userID = ID;
    }
    
    public String getName() {
        return username;
    }
    public void setName(String name) {
        this.username = name;
    }
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPass() {
        return password;
    }
    public void setPass(String pass) {
        this.password = pass;
    }
    
    public Timestamp getDate() {
        return creationDate;
    }
    public void setDate(Timestamp date) {
        this.creationDate = date;
    }
}
