/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author evandex
 */
public class Traits {
    private int traitID;
    private String traitName;
    
    public Traits() {}
    public Traits(int ID, String name) {
        this.traitID = ID;
        this.traitName = name;
    }
    
    public int getID() {
        return traitID;
    }
    
    public String getTrait() {
        return traitName;
    }
    public void setTrait(String traitName) {
        this.traitName = traitName;
    }
}
