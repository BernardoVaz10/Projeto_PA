package pt.pa.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a file in the file system.
 * A file is a file system element that can contain data and has a size in
 * bytes.
 *
 * @author João Oliveira (202200191)
 *         Alessandro Aguiar (202200272)
 *         Bernardo Vaz (20220278)
 *         Diogo Braizinha (202200223)
 * @version 1
 */

public class File implements FileSystemElement, Serializable {
    private Directory parent;
    private String name;
    private FileType type;
    private String content;
    private boolean statusUnlocked;
    private int countChanges;
    private boolean isFavorite;
    private LocalDate date;

    /**
     * Constructs a File object with the given parameters.
     *
     * @param name           - The name of the file.
     * @param type           - The type of the file (must be .txt or .csv).
     * @param content        - The content of the file.
     * @param statusUnlocked - Whether the file is locked or unlocked.
     * @throws IllegalArgumentException - If the file type is not .txt or .csv.
     */
    public File(String name, FileType type, String content, boolean statusUnlocked) {
        this.name = name;
        this.type = type;
        this.content = content;
        this.statusUnlocked = statusUnlocked;
        this.isFavorite = false;
        this.countChanges = 0;
        this.date = LocalDate.now();
    }


    /**
     * Constructs an empty File object.
     */
    public File() {
    }


    /**
     * Returns the name of the file.
     *
     * @return The name of the file.
     */
    @Override
    public String getName() {
        return this.name;
    }


    /***
     * Gets the creation date of the file.
     *
     * @return the creation date of the file.
     */
    @Override
    public LocalDate getDate() {
        return this.date;
    }


    /**
     * Returns the type of the file.
     *
     * @return The type of the file.
     */
    public FileType getType() {
        return this.type;
    }

    

    /**
     * Returns the parent directory of the file.
     *
     * @return The parent directory of the file.
     */
    public Directory getParent() {
        return this.parent;
    }


    /**
     * Returns the content of the file.
     *
     * @return The content of the file.
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Gets the count of changes made to the file.
     *
     * @return the number of times the file has been changed
     */
    public int getCountChanges(){
        return this.countChanges;
    }


    /**
     * Returns whether the file is locked or unlocked.
     *
     * @return True if the file is unlocked, false otherwise.
     */
    public boolean isStatusUnlocked() {
        return this.statusUnlocked;
    }


    /**
     * Checks if this directory is marked as a favorite.
     *
     * @return true if the directory is marked as a favorite
     */
    @Override
    public boolean isFavorite() {
        return this.isFavorite;
    }


    /**
     * Marks the directory as favorite.
     *
     * @param set true to mark the directory as a favorite, false to unmark it
     */
    @Override
    public void setFavorite(boolean set){
        this.isFavorite = set;
        countChanges++;
    }


    /**
     * Locks the file.
     */
    public void lock() {
        this.statusUnlocked = false;
        countChanges++;
    }


    /**
     * Unlocks the file.
     */
    public void unlock() {
        this.statusUnlocked = true;
        countChanges++;
    }


    /**
     * Sets the content of the file.
     *
     * @param content - The new content of the file.
     */
    public void setContent(String content) {
        this.content = content;
        countChanges++;
    }


    /**
     * Sets the name of the file.
     *
     * @param name - The new name of the file.
     */
    public void setName(String name) {
        this.name = name;
        countChanges++;
    };


    /**
     * Returns whether the file system element is a directory or not.
     *
     * @return False, as this is a file and not a directory.
     */
    @Override
    public boolean isDirectory() {
        return false;
    }


    /**
     * Compares this file with the specified object for equality.
     *
     * @param o - The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        File file = (File) o;
        return name.equals(file.name);
    }


    /**
     * Returns the hash code value for this file.
     *
     * @return The hash code value for this file.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    /**
     * Returns a string representation of the file.
     *
     * @return a string representation of the file (name + type).
     */
    public String toString() {
        String type = getType().toString();
        return (getName() + "." + type.toLowerCase());
    }
}
