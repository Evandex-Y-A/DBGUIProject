/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Timestamp;
import java.util.Optional;

/**
 * Represents a user entity in the system. Encapsulates user data including
 * identifiers, credentials, and timestamps.
 * 
 * <p>Contains fields and methods for:
 * <ul>
 * <li>User ID management</li>
 * <li>Username handling</li>
 * <li>Email address storage</li>
 * <li>Password management</li>
 * <li>Account creation timestamp tracking</li>
 * </ul>
 * 
 * @author evandex
 */

public class User {
    private int userID;
    private String username;
    private String email;
    private String password;
    private Timestamp creationDate;
    
    /**
     * Default constructor. Creates an empty User instance with uninitialized fields.
     */
    public User() {}
    
    /**
     * Constructs a User with specified parameters, handling optional values.
     * 
     * @param user_id Unique identifier for the user
     * @param name Display name for the user
     * @param emailOpt Optional container for email (defaults to empty string)
     * @param passOpt Optional container for password (defaults to empty string)
     * @param dateOpt Optional container for creation date Timestamp (defaults to null)
     */
    public User(int user_id, String name, Optional<String> emailOpt, Optional<String> passOpt, Optional<Timestamp> dateOpt) {
        String user_email = emailOpt.orElse("");
        String user_password = passOpt.orElse("");
        Timestamp date = dateOpt.orElse(null);
        this.userID = user_id;
        this.username = name;
        this.email = user_email;
        this.password = user_password;
        this.creationDate = date;
    }
    
    /**
     * Retrieves the user's unique identifier
     * @return Current user ID as integer
     */
    public int getID() {
        return userID;
    }
    
    /**
     * Sets the user's unique identifier
     * @param ID New integer value for user ID
     */
    public void setID(Integer ID) {
        this.userID = ID;
    }
    
    /**
     * Retrieves the user's display name
     * @return Current username as String
     */
    public String getName() {
        return username;
    }
    
    /**
     * Updates the user's display name
     * @param name New String value for username
     */
    public void setName(String name) {
        this.username = name;
    }
    
    /**
     * Retrieves the user's email address
     * @return Current email as String
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Updates the user's email address
     * @param email New String value for email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Retrieves the user's password
     * @return Current password as String
     */
    public String getPass() {
        return password;
    }
    
    /**
     * Updates the user's password
     * @param pass New String value for password
     */
    public void setPass(String pass) {
        this.password = pass;
    }
    
    /**
     * Retrieves the account creation timestamp
     * @return Timestamp object representing account creation time
     */
    public Timestamp getDate() {
        return creationDate;
    }
    
    /**
     * Updates the account creation timestamp
     * @param date New Timestamp value for creation date
     */
    public void setDate(Timestamp date) {
        this.creationDate = date;
    }
}
