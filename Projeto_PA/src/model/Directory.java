package pt.pa.model;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a directory in the file system.
 * A directory is a file system element that can contain other file system
 * elements, such as files and directories.
 *
 * @author João Oliveira (202200191)
 *         Alessandro Aguiar (202200272)
 *         Bernardo Vaz (20220278)
 *         Diogo Braizinha (202200223)
 * @version 1
 */
public class Directory implements FileSystemElement {
    private String name;
    private Directory parent;
    private boolean isFavorite;
    private LocalDate date;

    /**
     * Constructs a directory with the given name.
     *
     * @param name the name of the directory
     */
    public Directory(String name, Directory parent) {
        if (name == null && parent == null){
            throw new IllegalArgumentException("Os 2 argumentos não podem ser nulos");
        }
        this.parent = parent;
        this.isFavorite = false;
        this.name = name;
        this.date = LocalDate.now();
    }

    /**
     * Gets the name of the directory.
     *
     * @return the name of the directory
     */
    @Override
    public String getName() {
        return this.name;
    }

    /***
     * Gets the creation date of the directory.
     *
     * @return the creation date of the directory.
     */
    @Override
    public LocalDate getDate() {
        return this.date;
    }

    /***
     * Gets the parent directory of the current directory
     *
     * @return the parent directory of the current directory
     */
    public Directory getParent() { return this.parent; }

    /**
     * Sets the name of the directory.
     *
     * @param name the new name of the directory
     */
    public void setName(String name) {
        if (name == null){
            throw new IllegalArgumentException("O nome não pode ser nulo");
        }
        this.name = name;
    };

    /**
     * Checks if this directory is a directory.
     *
     * @return true as this object represents a directory
     */
    @Override
    public boolean isDirectory() {
        return true;
    }

    /***
     * Checks if this directory is marked as a favorite.
     *
     * @return true if the directory is marked as a favorite
     */
    @Override
    public boolean isFavorite() {
        return this.isFavorite;
    }

    /***
     * Marks the directory as favorite
     *
     * @param set true to mark the directory as a favorite, false to unmark it
     */
    @Override
    public void setFavorite(boolean set){
        this.isFavorite = set;
    }

    /**
     * Checks if this object is equal to another object.
     *
     * @param o the object to compare to this object
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Directory directory = (Directory) o;
        return name.equals(directory.name);
    }

    /**
     * Gets the hash code of this object.
     *
     * @return the hash code of this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    public String toString() {
        return getName();
    }
}
