/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 * Represents a user-defined trait with a unique identifier and name.
 * <p>
 * Instances of this class hold the data for a single trait record,
 * including its ID and descriptive name.
 * </p>
 * 
 * @author evandex
 */
public class Traits {

    /**
     * Unique identifier for the trait.
     */
    private int traitID;

    /**
     * Descriptive name of the trait.
     */
    private String traitName;

    /**
     * Default no-argument constructor.
     * <p>
     * Creates an empty Trait instance with default values.
     * </p>
     */
    public Traits() {}

    /**
     * Constructs a Trait with the specified ID and name.
     *
     * @param ID    the unique identifier for this trait
     * @param name  the descriptive name of this trait
     */
    public Traits(int ID, String name) {
        this.traitID = ID;
        this.traitName = name;
    }

    /**
     * Returns the unique identifier of this trait.
     *
     * @return the traitID
     */
    public int getID() {
        return traitID;
    }

    /**
     * Returns the name of this trait.
     *
     * @return the traitName
     */
    public String getTrait() {
        return traitName;
    }

    /**
     * Sets a new name for this trait.
     *
     * @param traitName the new descriptive name to assign
     */
    public void setTrait(String traitName) {
        this.traitName = traitName;
    }
}
