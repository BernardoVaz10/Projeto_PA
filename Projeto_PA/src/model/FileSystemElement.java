package pt.pa.model;

import java.time.LocalDate;

/**
 * Represents an element in the file system, such as a file or a directory.
 * Each file system element has a name, an optional description, and can be
 * either a file or a directory.
 *
 * This interface defines methods for retrieving the name, creation date,
 * and type (file or directory) of the file system element. It also provides
 * methods to check if the element is a favorite and to set its favorite status.
 *
 * @author
 *         Diogo Braizinha (202200223)
 *         Alessandro Aguiar (202200272)
 *         Bernardo Vaz (20220278)
 *         João Oliveira (202200191)
 * @version 1
 */
public interface FileSystemElement {

    /**
     * Returns the name of the file system element.
     *
     * @return the name of the file system element.
     */
    String getName();

    /**
     * Returns the creation date of the file system element.
     *
     * @return the creation date of the file system element.
     */
    LocalDate getDate();

    /**
     * Determines whether this file system element is a directory.
     *
     * @return true if this file system element is a directory, false otherwise.
     */
    boolean isDirectory();

    /**
     * Checks if this file system element is marked as a favorite.
     *
     * @return true if the element is marked as a favorite, false otherwise.
     */
    boolean isFavorite();

    /**
     * Sets the favorite status of this file system element.
     *
     * @param set true to mark the element as a favorite, false otherwise.
     */
    void setFavorite(boolean set);
}
