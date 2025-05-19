/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 * Represents a character entity within the system, associated with a specific user.
 * This class encapsulates details such as the character's name, description, and backstory.
 * 
 * @author evandex
 */
public class Character {
    private int character_id;
    private int user_id;
    private String name;
    private String description;
    private String backstory;
    
    /**
     * Constructs an empty Character instance with default values.
     */
    public Character() {}
    
    /**
     * Constructs a Character with specified details.
     * 
     * @param characterID the unique identifier for the character
     * @param userID the identifier of the user who owns this character
     * @param name the name of the character
     * @param desc the description of the character
     * @param backstory the backstory of the character
     */
    public Character(int characterID, int userID, String name, String desc, String backstory) {
        this.character_id = characterID;
        this.user_id = userID;
        this.name = name;
        this.description = desc;
        this.backstory = backstory;
    }
    
    /**
     * Returns the unique identifier of this character.
     * 
     * @return the character's ID
     */
    public int getCharacterId() {
        return character_id;
    }

    /**
     * Sets the unique identifier of this character.
     * 
     * @param characterId the new ID for the character
     */
    public void setCharacterId(int characterId) {
        this.character_id = characterId;
    }

    /**
     * Returns the ID of the user who owns this character.
     * 
     * @return the user's ID
     */
    public int getUserId() {
        return user_id;
    }

    /**
     * Sets the ID of the user who owns this character.
     * 
     * @param userId the new user ID
     */
    public void setUserId(int userId) {
        this.user_id = userId;
    }

    /**
     * Returns the name of the character.
     * 
     * @return the character's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the character.
     * 
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description of the character.
     * 
     * @return the character's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the character.
     * 
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the backstory of the character.
     * 
     * @return the character's backstory
     */
    public String getBackstory() {
        return backstory;
    }

    /**
     * Sets the backstory of the character.
     * 
     * @param backstory the new backstory
     */
    public void setBackstory(String backstory) {
        this.backstory = backstory;
    }
}
