package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Utility class for obtaining a JDBC connection to the application's
 * MySQL database. Encapsulates driver loading, connection URL, and
 * credentials.
 * <p>
 * Usage:
 * <pre>
 *     Connection conn = DatabaseUtils.ConnecttoDB();
 *     if (conn != null) {
 *         // proceed with database operations
 *     }
 * </pre>
 * </p>
 * 
 * @author evandex
 */
public class DatabaseUtils {
    /**
     * JDBC URL for connecting to the MySQL database.
     * Format: jdbc:mysql://&lt;host&gt;:&lt;port&gt;/&lt;databaseName&gt;
     */
    private static final String URL = "jdbc:mysql://localhost:3306/storykeep_db";
    
    /**
     * Username for the MySQL database connection.
     */
    private static final String USER = "root";
    
    /**
     * Password for the MySQL database connection.
     */
    private static final String PASSWORD = "";
    
    /**
     * Attempts to establish and return a connection to the configured
     * MySQL database.
     * <p>
     * Loads the MySQL JDBC driver, then uses {@link DriverManager} to
     * open a connection with the given URL, user, and password. If the
     * driver class is not found or the connection fails, an error dialog
     * is shown and {@code null} is returned.
     * </p>
     *
     * @return a valid {@link Connection} to the database, or {@code null}
     *         if the connection attempt failed
     */
    public static Connection ConnecttoDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Connection Failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
