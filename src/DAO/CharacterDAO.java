/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAO.UserDAO;
import java.sql.*;
import Utils.DatabaseUtils;
import models.Character;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Data Access Object (DAO) for performing CRUD operations on Character records.
 * <p>
 * All methods use {@link DatabaseUtils#ConnecttoDB()} to obtain a JDBC
 * connection and operate on the `characters` table for the currently
 * authenticated user {@link UserDAO#currentUser}.
 * </p>
 *
 * @see DatabaseUtils
 * @see UserDAO#currentUser
 * @see Character
 */
public class CharacterDAO {
    
    /**
     * Inserts a new character record into the database and sets its generated ID.
     *
     * @param character the {@link Character} instance containing name,
     *                  description, and backstory (user_id is set automatically)
     * @return the same {@code character} instance, with its {@code characterId}
     *         field populated from the database-generated key
     * @throws SQLException if a database access error occurs or no rows
     *                      were affected
     */
    public static Character createCharacter(Character character) throws SQLException {
        String sql = "INSERT INTO characters (user_id, name, description, backstory) VALUES(?,?,?,?)";
        
        try (Connection conn = DatabaseUtils.ConnecttoDB();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, UserDAO.currentUser.getID());
            pstmt.setString(2, character.getName());
            pstmt.setString(3, character.getDescription());
            pstmt.setString(4, character.getBackstory());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating character failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    character.setCharacterId(generatedKeys.getInt(1));
                }
            }
            
            return character;
        }
    }
    
    /**
     * Retrieves a character by its unique identifier.
     *
     * @param id the {@code character_id} of the character to fetch
     * @return a {@link Character} object populated from the database,
     *         or {@code null} if no matching record is found
     * @throws SQLException if a database access error occurs
     */
    public static Character getCharacterById(int id) throws SQLException {
        String sql = "SELECT * FROM characters WHERE character_id = ?";
        
        try (Connection conn = DatabaseUtils.ConnecttoDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Character(
                    rs.getInt("character_id"),
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("backstory")
                );
            }
            return null;
        }
    }
    
    /**
     * Updates the database record for the given character.
     *
     * @param character the {@link Character} instance containing updated name,
     *                  description, backstory, and its valid {@code characterId}
     * @throws SQLException if a database access error occurs
     */
    public static void updateCharacter(Character character) throws SQLException {
        String sql = "UPDATE characters SET name = ?, description = ?, backstory = ? WHERE character_id = ?";
        
        try (Connection conn = DatabaseUtils.ConnecttoDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, character.getName());
            pstmt.setString(2, character.getDescription());
            pstmt.setString(3, character.getBackstory());
            pstmt.setInt(5, character.getCharacterId());
            
            pstmt.executeUpdate();
        }
    }

    /**
     * Retrieves all characters for the currently authenticated user.
     *
     * @return a {@link List} of {@link Character} objects for the current user,
     *         or {@code null} on database error
     * @throws SQLException if a database access error occurs
     */
    public static List<Character> getAllCharacters() throws SQLException {
        List<Character> characters = new ArrayList<>();
        String sql = "SELECT * FROM characters where user_id = ?";
        
        try {
            Connection conn = DatabaseUtils.ConnecttoDB();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, UserDAO.currentUser.getID());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                characters.add(new Character(
                    rs.getInt("character_id"),
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("backstory")
                ));
            }
            return characters;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Deletes a character record by its unique identifier.
     *
     * @param id the {@code character_id} of the character to delete
     * @throws SQLException if a database access error occurs
     */
    public static void deleteCharacter(int id) throws SQLException {
        String sql = "DELETE FROM characters WHERE character_id = ?";
        try (Connection conn = DatabaseUtils.ConnecttoDB();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
