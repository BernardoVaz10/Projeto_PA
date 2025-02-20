package pt.pa.graphicalInterface;

import java.time.LocalDate;

/**
 * Represents a class to compare the date between two FileSystemElements
 *
 * @author João Oliveira (202200191)
 *         Alessandro Aguiar (202200272)
 *         Bernardo Vaz (20220278)
 *         Diogo Braizinha (202200223)
 * @version 1
 */
public class DateNameFileSystemElement implements Comparable<DateNameFileSystemElement> {
    private LocalDate data;
    private String nomeArquivo;


    /**
     * Constructs a DateNameFileSystemElement with the specified date and file name.
     *
     * @param data         The date associated with the FileSystemElement.
     * @param nomeArquivo  The name of the FileSystemElement (e.g., file name).
     */
    public DateNameFileSystemElement(LocalDate data, String nomeArquivo) {
        this.data = data;
        this.nomeArquivo = nomeArquivo;
    }


    /**
     * Gets the date associated with the FileSystemElement.
     *
     * @return The date of the FileSystemElement.
     */
    public LocalDate getData() {
        return data;
    }


    /**
     * Gets the name of the FileSystemElement.
     *
     * @return The name of the FileSystemElement.
     */
    public String getNomeArquivo() {
        return nomeArquivo;
    }


    /**
     * Compares this DateNameFileSystemElement with another based on date.
     *
     * @param outroPar The DateNameFileSystemElement to compare with.
     * @return A negative integer, zero, or a positive integer if this object is less than, equal to,
     *         or greater than the specified object, based on date.
     */
    @Override
    public int compareTo(DateNameFileSystemElement outroPar) {
        return this.data.compareTo(outroPar.data);
    }
}
