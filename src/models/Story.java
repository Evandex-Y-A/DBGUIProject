/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Timestamp;
/**
 *
 * @author evandex
 */
public class Story {
    private int story_id;
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
    
    public int getId() {
        return this.story_id;
    }
    public void setId(int story_id) {
        this.story_id = story_id;
    }
    
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getGenre() {
        return this.title;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public String getStatus() {
        return this.title;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getSynopsis() {
        return this.title;
    }
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
    
    public Timestamp getDate() {
        return this.creationDate;
    }
    public void setDate(String title) {
        this.title = title;
    }
}