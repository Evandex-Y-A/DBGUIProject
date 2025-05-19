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
 * Data Access Object (DAO) for performing CRUD operations on user traits.
 * <p>
 * Establishes a database connection via {@link DatabaseUtils} and provides
 * methods to add, rename, delete, and check the existence of traits for
 * the currently authenticated user.
 * </p>
 * 
 * @see DatabaseUtils
 * @see UserDAO#currentUser
 * 
 * @author evandex
 */
public class TraitDAO {
    
    /**
     * JDBC connection to the application database.
     */
    private Connection connection;
    
    /**
     * Constructs a new TraitDAO, obtaining a database connection
     * via {@link DatabaseUtils#ConnecttoDB()}.
     */
    public TraitDAO() {
        connection = DatabaseUtils.ConnecttoDB();
    }
    
    /**
     * Inserts a new trait record for the current user.
     * <p>
     * If the database connection is unavailable, shows an error dialog.
     * Otherwise, executes an INSERT statement into the traits table.
     * </p>
     *
     * @param trait the name of the trait to add
     * @return {@code true} if the insertion affected at least one row;
     *         {@code false} on error or if no rows were inserted
     */
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
    
    /**
     * Renames an existing trait.
     * <p>
     * Looks up the trait by its old name, and if found, updates its name
     * to the provided new name. Displays an error dialog if the connection
     * fails or an SQL exception occurs.
     * </p>
     *
     * @param oldTrait the current name of the trait to rename
     * @param newTrait the new name to assign to the trait
     * @return {@code true} if the update affected at least one row;
     *         {@code false} if the trait was not found or on error
     */
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
    
    /**
     * Deletes a trait by its name.
     * <p>
     * Executes a DELETE statement on the traits table for the given name.
     * Shows an error dialog if the connection fails or an SQL exception
     * occurs.
     * </p>
     *
     * @param trait the name of the trait to delete
     * @return {@code true} if the deletion affected at least one row;
     *         {@code false} otherwise or on error
     */
    public boolean deleteTrait(String trait) {
        if (connection == null) {
            JOptionPane.showMessageDialog(null, "Database connection error");
            return false;
        }
        try {
            PreparedStatement getTrait = connection.prepareStatement("DELETE FROM traits WHERE name = ?");
            getTrait.setString(1, trait);
            int rows = getTrait.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Checks whether a trait with the given name exists.
     * <p>
     * Executes a SELECT query to determine if any record matches the name.
     * Returns {@code true} if at least one match is found.
     * </p>
     *
     * @param trait the name of the trait to check
     * @return {@code true} if the trait exists; {@code false} otherwise or on error
     */
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
    
    /**
     * Closes the underlying database connection.
     * <p>
     * Should be called when DAO operations are complete to release resources.
     * Displays an error dialog if closing the connection fails.
     * </p>
     */
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
