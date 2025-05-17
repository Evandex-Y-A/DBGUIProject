/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAO.UserDAO;
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
            PreparedStatement newTrait = connection.prepareStatement("INSERT INTO traits (name, user_id) VALUES (?, ?)");
            newTrait.setString(1, trait);
            newTrait.setInt(2, UserDAO.currentUser.getID());
            int result = newTrait.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
            return false;
        }
    }
    
    public boolean renameTrait(String oldTrait, String newTrait) {
        if (connection == null) {
            JOptionPane.showMessageDialog(null, "Database connection error");
            return false;
        }
        try {
            PreparedStatement checkTrait = connection.prepareStatement("SELECT trait_id, name FROM traits WHERE name=?");
            checkTrait.setString(1, oldTrait);
            ResultSet oldTraitChecked = checkTrait.executeQuery();
            if (oldTraitChecked.next()) {
                PreparedStatement rename = connection.prepareStatement("UPDATE traits SET name = ? WHERE trait_id = ?");
                rename.setString(1, newTrait);
                rename.setInt(2, oldTraitChecked.getInt("trait_id"));
                int rows = rename.executeUpdate();
                return rows > 0;
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
            return false;
        }
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
