package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.Optional;
import models.User;
import Utils.DatabaseUtils;
import Utils.Passwords;

/**
 * Data Access Object (DAO) for performing user-related database operations.
 * <p>
 * Manages connection to the database and provides methods for login,
 * registration, username checks, deletion, and cleanup.
 * Maintains the currently authenticated user in {@link #currentUser}.
 * </p>
 *
 * @see DatabaseUtils
 * @see Passwords
 * @see User
 * 
 * @author evandex
 */
public class UserDAO {
    /**
     * JDBC connection to the application database.
     */
    private Connection connection;
    
    /**
     * The currently logged-in user, or {@code null} if no user is authenticated.
     */
    public static User currentUser;
    
    /**
     * Constructs a new UserDAO, obtaining a database connection
     * via {@link DatabaseUtils#ConnecttoDB()}.
     */
    public UserDAO() {
        connection = DatabaseUtils.ConnecttoDB();
    }
    
    /**
     * Attempts to authenticate a user by username and plaintext password.
     * <p>
     * Hashes the provided password using {@link Passwords#hasher(String)},
     * queries the database for the stored hash, and compares them.
     * On success, sets {@link #currentUser} and returns {@code true}.
     * Displays an error dialog on database connection or query failures.
     * </p>
     *
     * @param username the username to authenticate
     * @param password the plaintext password to verify
     * @return {@code true} if authentication succeeds; {@code false} otherwise
     */
    public boolean userLogin(String username, String password) {
        if (connection == null) {
            JOptionPane.showMessageDialog(null, "Database connection error");
            return false;
        }
        
        String hashedPassword = Passwords.hasher(password);
        
        try {
            PreparedStatement login = connection.prepareStatement("SELECT user_id, username, password FROM users WHERE username=?");
            login.setString(1, username);
            ResultSet result = login.executeQuery();
            if (result.next()) {
                String storedPassword = result.getString("password");
                if (storedPassword.equals(hashedPassword)) {
                    User user = new User(result.getInt("user_id"), result.getString("username"), Optional.empty(), Optional.empty(), Optional.empty());
                    currentUser = user;
                    return true;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Creates a new user record in the database.
     * <p>
     * Hashes the provided password and inserts the username, email,
     * and hashed password into the users table.
     * Displays an error dialog on failure.
     * </p>
     *
     * @param username the desired username
     * @param email    the user's email address
     * @param password the plaintext password
     * @return {@code true} if the insertion succeeds; {@code false} otherwise
     */
    public boolean createNewUser(String username, String email, String password) {
        try {
            String hash = Passwords.hasher(password);
            PreparedStatement query = connection.prepareStatement("INSERT INTO users (username, email, password) VALUES (?, ?, ?)");
            query.setString(1, username);
            query.setString(2, email);
            query.setString(3, hash);
            int rowsInserted = query.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Checks whether a given username already exists in the database.
     * <p>
     * Executes a SELECT query for the provided username and returns
     * {@code true} if a matching record is found.
     * </p>
     *
     * @param username the username to check
     * @return {@code true} if the username exists; {@code false} otherwise
     */
    public boolean checkUsername(String username) {
        try {
            PreparedStatement checkname = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            checkname.setString(1, username);
            ResultSet result = checkname.executeQuery();
            return result.next();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Deletes a user record by its ID.
     * <p>
     * If deletion is successful and the deleted user matches
     * {@link #currentUser}, clears the static currentUser reference.
     * Displays an error dialog on failure.
     * </p>
     *
     * @param userID the ID of the user to delete
     * @return {@code true} if deletion succeeds; {@code false} otherwise
     */
    public boolean deleteUser(int userID) {
        if (connection == null) {
            JOptionPane.showMessageDialog(null, "Database connection error");
            return false;
        }
        try {
            PreparedStatement deleteUser = connection.prepareStatement("DELETE FROM users WHERE user_id = ?");
            deleteUser.setInt(1, userID);
            int rows = deleteUser.executeUpdate();
            if (rows > 0 && currentUser != null && currentUser.getID() == userID) {
            currentUser = null;
            }
            return rows > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Closes the underlying database connection.
     * <p>
     * Should be called when DAO operations are complete to release resources.
     * Displays an error dialog if closing fails.
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