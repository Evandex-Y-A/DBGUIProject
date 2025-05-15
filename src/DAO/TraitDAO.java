/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Utils.DatabaseUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author evandex
 */
public class TraitDAO {
    private Connection connection;
    
    public TraitDAO() {
        connection = DatabaseUtils.ConnecttoDB();
    }
    
    public boolean addTrait(String trait) {
        if (connection == null) {
            JOptionPane.showMessageDialog(null, "Database connection error");
            return false;
        }
        try {
            PreparedStatement newTrait = connection.prepareStatement("INSERT INTO traits (name) VALUES (?)");
            newTrait.setString(1, trait);
            int result = newTrait.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        }
        return false;
    }
    
    public boolean checkTrait(String trait) {
        try {
            PreparedStatement checkname = connection.prepareStatement("SELECT * FROM traits WHERE name = ?");
            checkname.setString(1, trait);
            ResultSet result = checkname.executeQuery();
            return result.next();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
            return false;
        }
    }
    
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error closing connection: " + e.getMessage());
        }
    }
}
