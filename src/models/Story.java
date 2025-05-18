/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Timestamp;
/**
 * Represents a user’s story with metadata such as title, genre, status,
 * synopsis, and creation timestamp.
 * <p>
 * Provides getters and setters for each property, along with a default
 * and full-argument constructor.
 * </p>
 * 
 * @author evandex
 */
public class Story {
    private int story_id;
    private int user_id;
    private String title;
    private String genre;
    private String status;
    private String synopsis;
    private Timestamp creationDate;
    
    public Story() {}
    
    public Story(int story_id, String title, String genre, String status, String synopsis, Timestamp creationDate) {
        this.story_id = story_id;
        this.title = title;
        this.genre = genre;
        this.status = status;
        this.synopsis = synopsis; 
        this.creationDate = creationDate;
    }
    
    /**
     * Returns the unique identifier of this story.
     *
     * @return the story_id
     */
    public int getId() {
        return this.story_id;
    }
    
    /**
     * Sets the unique identifier for this story.
     *
     * @param story_id the story_id to set
     */
    public void setId(int story_id) {
        this.story_id = story_id;
    }
    
    /**
     * Returns the title of this story.
     *
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }
    
     /**
     * Sets the title of this story.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Returns the genre of this story.
     *
     * @return the genre
     */
    public String getGenre() {
        return this.genre;
    }
    
    /**
     * Sets the genre of this story.
     *
     * @param genre the genre to set
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    /**
     * Returns the publishing status of this story.
     *
     * @return the status
     */
    public String getStatus() {
        return this.status;
    }
    
    /**
     * Sets the publishing status of this story.
     *
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * Returns the synopsis of this story.
     *
     * @return the synopsis
     */
    public String getSynopsis() {
        return this.synopsis;
    }
    
    /**
     * Sets the synopsis of this story.
     *
     * @param synopsis the synopsis to set
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
    
    /**
     * Returns the creation timestamp of this story.
     *
     * @return the creationDate
     */
    public Timestamp getDate() {
        return this.creationDate;
    }
    
    /**
     * Sets the creation timestamp of this story.
     *
     * @param date the creationDate to set
     */
    public void setDate(Timestamp date) {
        this.creationDate = date;
    }
}