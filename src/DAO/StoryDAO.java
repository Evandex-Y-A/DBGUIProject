/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Story;
import Utils.DatabaseUtils;
/**
 *
 * @author evandex
 */
public class StoryDAO {
    private static Connection connection;
    
    public static Story getStoryById(int id) {
        connection = DatabaseUtils.ConnecttoDB();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM story WHERE story_id = ?");
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
        } catch (SQLException e) {
            return null;
        }
        return null;
    }
}
