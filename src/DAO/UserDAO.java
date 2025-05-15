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

public class UserDAO {
    private Connection connection;
    private static User currentUser;
    
    public UserDAO() {
        connection = DatabaseUtils.ConnecttoDB();
    }
    
    public boolean userLogin(String username, String password) {
        if (connection == null) {
            JOptionPane.showMessageDialog(null, "Database connection error");
            return false;
        }
        
        String hashedPassword = Passwords.hasher(password);
        
        try {
            PreparedStatement login = connection.prepareStatement("SELECT username, password FROM users WHERE username=?");
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