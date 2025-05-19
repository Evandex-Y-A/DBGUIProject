/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAO.UserDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Story;
import Utils.DatabaseUtils;
import javax.swing.JOptionPane;

/**
 * Data Access Object (DAO) for performing CRUD operations on Story records.
 * <p>
 * All methods use {@link DatabaseUtils#ConnecttoDB()} to obtain a JDBC
 * connection and operate on the `story` table for the currently
 * authenticated user {@link UserDAO#currentUser}.
 * </p>
 * 
 * @see DatabaseUtils
 * @see UserDAO#currentUser
 * @see Story
 * 
 * @author evandex
 */
public class StoryDAO {
    private static Connection connection;

    /**
     * Inserts a new story record into the database and sets its generated ID.
     * 
     * @param story the {@link Story} instance containing title, genre,
     *              status, and synopsis (user_id and creation_date are set
     *              by the database)
     * @return the same {@code story} instance, with its {@code id} field
     *         populated from the database-generated key
     * @throws SQLException if a database access error occurs or no rows
     *                      were affected
     */
    public static Story createStory(Story story) throws SQLException {
        String sql = "INSERT INTO story (user_id, title, genre, status, synopsis) VALUES (?,?,?,?,?)";
        
        try (Connection conn = DatabaseUtils.ConnecttoDB();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, UserDAO.currentUser.getID());
            pstmt.setString(2, story.getTitle());
            pstmt.setString(3, story.getGenre());
            pstmt.setString(4, story.getStatus());
            pstmt.setString(5, story.getSynopsis());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating story failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    story.setId(generatedKeys.getInt(1));
                }
            }
            return story;
        }
    }
    
    /**
     * Retrieves a story by its unique identifier.
     * 
     * @param id the {@code story_id} of the story to fetch
     * @return a {@link Story} object populated from the database, or
     *         {@code null} if no matching record is found or on error
     * @throws SQLException if a database access error occurs
     */
    public static Story getStoryById(int id) throws SQLException {
        connection = DatabaseUtils.ConnecttoDB();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM story WHERE story_id = ?");
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                Story story = new Story(
                        result.getInt("story_id"),
                        result.getString("title"),
                        result.getString("genre"),
                        result.getString("status"),
                        result.getString("synopsis"),
                        result.getTimestamp("creation_date")
                        );
                return story;
            } else {
                JOptionPane.showMessageDialog(null, "No story found with that id.");
                return null;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Updates the database record for the given story.
     * 
     * @param story the {@link Story} instance containing updated title,
     *              genre, status, synopsis, and its valid {@code id}
     * @throws SQLException if a database access error occurs
     */
    public static void updateStory(Story story) throws SQLException {
        String sql = "UPDATE story SET title = ?, genre = ?, status = ?, synopsis = ? WHERE story_id = ?";
        
        try (Connection conn = DatabaseUtils.ConnecttoDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, story.getTitle());
            pstmt.setString(2, story.getGenre());
            pstmt.setString(3, story.getStatus());
            pstmt.setString(4, story.getSynopsis());
            pstmt.setInt(5, story.getId());
            
            pstmt.executeUpdate();
        }
    }
    
    /**
     * Retrieves all stories for the currently authenticated user.
     * 
     * @return a {@link List} of {@link Story} objects for the current user,
     *         or {@code null} on database error
     * @throws SQLException if a database access error occurs
     */
    public static List<Story> getAllStories() throws SQLException {
        List<Story> stories = new ArrayList<>();
        String sql = "SELECT * FROM story where user_id = ?";
        
        try {
            Connection conn = DatabaseUtils.ConnecttoDB();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, UserDAO.currentUser.getID());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                stories.add(new Story(
                    rs.getInt("story_id"),
                    rs.getString("title"),
                    rs.getString("genre"),
                    rs.getString("status"),
                    rs.getString("synopsis"),
                    rs.getTimestamp("creation_date")
                ));
            }
            return stories;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
            return null;
        }

    }
    
    /**
     * Deletes a story record by its unique identifier.
     * 
     * @param id the {@code story_id} of the story to delete
     * @throws SQLException if a database access error occurs
     */
    public static void deleteStory(int id) throws SQLException {
        String sql = "DELETE FROM story WHERE story_id = ?";
        try (Connection conn = DatabaseUtils.ConnecttoDB();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
