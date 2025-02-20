package pt.pa.model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import pt.pa.adts.Position;
import pt.pa.adts.TreeLinked;
import pt.pa.graphicalInterface.DateNameFileSystemElement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents the main structure of a personal file system.
 * The personal file system consists of files and directories, where each
 * element has a name, an optional description, and can contain other elements.
 * The file system supports creating new files and directories, moving elements
 * within the file system, renaming elements, and deleting elements.
 *
 * @author João Oliveira (202200191)
 *         Alessandro Aguiar (202200272)
 *         Bernardo Vaz (20220278)
 *         Diogo Braizinha (202200223)
 * @version 1
 */
public class PersonalFileSystem extends TreeLinked<FileSystemElement> {
    private ObservableList<FileSystemElement> fileList = FXCollections.observableArrayList();
    private List<FileSystemElement> favorites;
    private List<Position<FileSystemElement>> removeds;

    /**
     * Creates a new PersonalFileSystem with the given root directory.
     */
    public PersonalFileSystem() {
        super(new Directory("Root", null));
        favorites = new ArrayList<>();
        removeds = new ArrayList<>();
    }

    /**
     * Retrieves a list of DateNameFileSystemElement objects representing the dates and names
     * of the FileSystemElements in the fileList.
     *
     * @return a list of DateNameFileSystemElement objects
     */
    public List<DateNameFileSystemElement> getDates(){
        List<DateNameFileSystemElement> dates = new ArrayList<>();
        for(FileSystemElement f : fileList){
            dates.add(new DateNameFileSystemElement(f.getDate(), f.getName()));
        }
        return dates;
    }

    /**
     * Retrieves the list of FileSystemElements.
     *
     * @return an ObservableList containing FileSystemElements.
     */
    public ObservableList<FileSystemElement> getFileList() {
        return fileList;
    }


    /**
     * Adds a file to the file system.
     *
     * @param parent parent directory where the file will be added
     * @param file   file to be added
     */
    public void addFile(Directory parent, File file) {
        Position<FileSystemElement> parentPosition = findPosition(parent);
        if (parentPosition != null) {
            insert(parentPosition, file);
            fileList.add(file);
        }
    }


    /**
     * Adds a folder to the file system.
     *
     * @param parent parent directory where the folder will be added
     * @param folder folder to be added
     */
    public void addFolder(Directory parent, Directory folder) {
        Position<FileSystemElement> parentPosition = findPosition(parent);
        if (parentPosition != null) {
            insert(parentPosition, folder);
            fileList.add(folder);
        }
    }


    /**
     * Finds the position of a file system element in the tree.
     *
     * @param element file system element to find
     * @return position of the element if found, null otherwise
     */
    public Position<FileSystemElement> findPosition(FileSystemElement element) {
        for (Position<FileSystemElement> position : positions()) {
            if (position.element() == element) {
                return position;
            }
        }
        return null;
    }


    /**
     * Creates an empty file in the file system.
     *
     * @param fileName       The name of the file.
     * @param fileType       The type of the file.
     * @param parent         The parent directory of the file.
     * @param statusUnlocked True if the file should be unlocked, false otherwise.
     * @return The newly created file.
     */
    public File createEmptyFile(String fileName, FileType fileType, Directory parent, boolean statusUnlocked) {
        File newFile = new File(fileName, fileType, "", statusUnlocked);
        addFile(parent, newFile);
        return newFile;
    }


    /**
     * Creates an empty directory in the file system.
     *
     * @param directoryName The name of the directory.
     * @param parent        The parent directory of the new directory.
     * @return The newly created directory.
     */
    public Directory createEmptyDirectory(String directoryName, Directory parent) {
        Directory newDirectory = new Directory(directoryName,parent);
        addFolder(parent, newDirectory);
        return newDirectory;
    }


    /**
     * This method creates a copy of the specified sourceElement in the specified
     * destination directory.
     *
     * @param sourceElement the element to be copied. It can be a File or a
     *                      Directory.
     * @param destination   the directory where the copy of the sourceElement will
     *                      be stored.
     * @return the newly created copy of the sourceElement.
     */
    public FileSystemElement copyElement(FileSystemElement sourceElement, Directory destination) {
        if (sourceElement instanceof File) {
            File sourceFile = (File) sourceElement;
            File newFile = new File(sourceFile.getName(), FileType.TXT, sourceFile.getContent(),
                    sourceFile.isStatusUnlocked());
            addFile(destination, newFile);
            return newFile;
        } else if (sourceElement instanceof Directory) {
            Directory sourceDirectory = (Directory) sourceElement;
            Directory newDirectory = new Directory(sourceDirectory.getName(),destination);
            addFolder(destination, newDirectory);
            for (Position<FileSystemElement> childPosition : this.children(findPosition(sourceDirectory))) {
                copyElement(childPosition.element(), newDirectory);
            }
            return newDirectory;
        }
        return null;
    }

    /**
     * Restores the excluded FileSystemElement (either File or Directory) from the list of removed elements
     * to the specified destination Directory. If the sourceElement is a File, it is handled appropriately,
     * otherwise, if it is a Directory, the restoration is performed recursively.
     *
     * @param sourceElement The FileSystemElement to be restored.
     * @param destination The destination Directory where the sourceElement will be restored.
     * @return The destination Directory after the restoration, or null if the operation failed.
     */
    public FileSystemElement excludedElement(FileSystemElement sourceElement, Directory destination) {
        if (sourceElement instanceof File) {
            // Lógica para lidar com a cópia de arquivos (mesmo que já esteja no seu código)

        } else if (sourceElement instanceof Directory) {
            Directory sourceDirectory = (Directory) sourceElement;

            for (Position<FileSystemElement> removedPosition : removeds) {
                if (removedPosition.element().equals(sourceDirectory)) {
                    restoreDirectoryRecursively(removedPosition, destination);
                    return destination;
                }
            }

            System.out.println("Posição do diretório de origem não encontrada na lista removeds.");
        }

        return null;
    }

    /**
     * Restores the specified removed FileSystemElement (either File or Directory) and its children recursively
     * to the specified destination Directory. If the sourceElement is a File, a new File with the same properties
     * is created and added to the destination Directory. If the sourceElement is a Directory, a new subdirectory
     * is created within the destination Directory, and the restoration process is performed recursively for its children.
     *
     * @param removedPosition The position of the removed FileSystemElement in the removeds list.
     * @param destination The destination Directory where the sourceElement and its children will be restored.
     */
    private void restoreDirectoryRecursively(Position<FileSystemElement> removedPosition, Directory destination) {
        FileSystemElement sourceElement = removedPosition.element();

        if (sourceElement instanceof File) {
            File sourceFile = (File) sourceElement;
            File newFile = new File(sourceFile.getName(), FileType.TXT, sourceFile.getContent(), sourceFile.isStatusUnlocked());
            addFile(destination, newFile);
        } else if (sourceElement instanceof Directory) {
            Directory sourceDirectory = (Directory) sourceElement;

            Directory newSubdirectory = findOrCreateDirectory(destination, sourceDirectory);

            for (Position<FileSystemElement> childPosition : children(removedPosition)) {
                restoreDirectoryRecursively(childPosition, newSubdirectory);
            }
        }
    }


    /**
     * Finds or creates a Directory within the specified destination Directory that corresponds to the
     * specified sourceDirectory. If a matching Directory is found among the children of the destination,
     * it is returned. Otherwise, a new Directory is created within the destination, added to its children,
     * and returned.
     *
     * @param destination The destination Directory where the matching or new Directory will be created.
     * @param sourceDirectory The source Directory to find or create within the destination.
     * @return The found or newly created Directory.
     */
    private Directory findOrCreateDirectory(Directory destination, Directory sourceDirectory) {
        for (Position<FileSystemElement> position : children(findPosition(destination))) {
            if (position.element().equals(sourceDirectory)) {
                return (Directory) position.element();
            }
        }

        Directory newSubdirectory = new Directory(sourceDirectory.getName(), destination);
        addFolder(destination, newSubdirectory);
        return newSubdirectory;
    }


    /**
     * This method moves the specified sourceElement from its current parent
     * directory to the specified destination directory.
     *
     * @param sourceElement the element to be moved. It can be a File or a
     *                      Directory.
     * @param destination   the directory where the sourceElement will be moved to.
     * @return the moved sourceElement.
     */
    public FileSystemElement moveElement(FileSystemElement sourceElement, Directory destination) {
        Position<FileSystemElement> sourcePosition = findPosition(sourceElement);

        if (sourcePosition != null) {
            fileList.clear();
            FileSystemElement movedElement = moveElementRecursively(sourcePosition, destination);
            return movedElement;
        }
        return null;
    }


    /**
     * Moves the specified FileSystemElement (either File or Directory) and its children recursively
     * to the specified destination Directory. If the sourceElement is a File, a new File with the same properties
     * is created, the original element is removed, and the new File is added to the destination Directory.
     * If the sourceElement is a Directory, a new Directory is created with the same name within the destination Directory,
     * the original element is removed, and the new Directory is added to the destination. The process is performed recursively
     * for the children of the sourceElement.
     *
     * @param sourcePosition The position of the source FileSystemElement in the file system structure.
     * @param destination The destination Directory where the sourceElement and its children will be moved.
     * @return The moved FileSystemElement or null if the operation failed.
     */
    private FileSystemElement moveElementRecursively(Position<FileSystemElement> sourcePosition, Directory destination) {
        FileSystemElement sourceElement = sourcePosition.element();

        if (sourceElement instanceof File) {
            File sourceFile = (File) sourceElement;
            File movedFile = new File(sourceFile.getName(), sourceFile.getType(), sourceFile.getContent(),
                    sourceFile.isStatusUnlocked());
            remove(sourcePosition);
            addFile(destination, movedFile);
            fileList.remove(sourceElement);
            fileList.add(movedFile);
            return movedFile;
        } else if (sourceElement instanceof Directory) {
            Directory sourceDirectory = (Directory) sourceElement;
            Directory movedDirectory = new Directory(sourceDirectory.getName(), destination);
            remove(sourcePosition);
            addFolder(destination, movedDirectory);
            fileList.remove(sourceElement);
            fileList.add(movedDirectory);

            for (Position<FileSystemElement> childPosition : children(sourcePosition)) {
                moveElementRecursively(childPosition, movedDirectory);
            }
            return movedDirectory;
        }

        return null;
    }



    /**
     * Renames the specified source element to the new name.
     * If the source element is a File or Directory, its name will be updated.
     *
     * @param sourceElement the element to be renamed
     * @param newName       the new name of the element
     */
    public void renameElement(FileSystemElement sourceElement, String newName) {
        Position<FileSystemElement> sourcePosition = findPosition(sourceElement);
        if (sourcePosition != null) {
            if (sourceElement instanceof File) {
                ((File) sourceElement).setName(newName);
            } else if (sourceElement instanceof Directory) {
                ((Directory) sourceElement).setName(newName);
            }
        }
    }


    /**
     * Deletes the specified source element from the file system.
     *
     * @param sourceElement the element to be deleted
     */
    public void deleteElement(FileSystemElement sourceElement) {
        Position<FileSystemElement> sourcePosition = findPosition(sourceElement);
        if (sourcePosition != null) {
            removeds.add(sourcePosition);
            remove(sourcePosition);
        }
    }


    /**
     * Returns the content of the specified source element.
     * If the source element is a File, its content will be returned.
     *
     * @param sourceElement the element whose content is to be retrieved
     * @return the content of the source element
     */
    public String visualizeContent(FileSystemElement sourceElement) {
        Position<FileSystemElement> sourcePosition = findPosition(sourceElement);
        if (sourcePosition != null) {
            if (sourceElement instanceof File) {
                return ((File) sourceElement).getContent();
            }
        }
        return null;
    }


    /**
     * This method allows you to edit the content of a FileSystemElement.
     * The changes will only be made if the element is unlocked (File status).
     *
     * @param sourceElement The FileSystemElement to be modified.
     * @param newContent    The new content for the FileSystemElement.
     */
    public void editContent(FileSystemElement sourceElement, String newContent) {
        Position<FileSystemElement> sourcePosition = findPosition(sourceElement);
        if (sourcePosition != null) {
            if (sourceElement instanceof File) {
                if (((File) sourceElement).isStatusUnlocked()) {
                    ((File) sourceElement).setContent(newContent);
                }
            }
        }
    }


    /**
     * Adds or removes the specified FileSystemElement to/from the favorites list.
     * If the FileSystemElement is already marked as a favorite, it is removed from the favorites list
     * and its favorite status is set to false. If it is not marked as a favorite, it is added to the favorites list
     * and its favorite status is set to true.
     *
     * @param fse The FileSystemElement to be added to or removed from the favorites list.
     */
    public void addRemoveFavorites(FileSystemElement fse){
        if(fse.isFavorite()){
            favorites.remove(fse);
            fse.setFavorite(false);
        } else {
            favorites.add(fse);
            fse.setFavorite(true);
        }

    }

    /**
     * Removes the specified position representing a FileSystemElement from the list of removed elements.
     *
     * @param fse The position of the FileSystemElement to be removed from the list of removed elements.
     */
    public void removeRemoveds(Position<FileSystemElement> fse){
        removeds.remove(fse);
    }


    /**
     * Calculates the percentage of space occupied by the directory and its subdirectories.
     *
     * @param directory The directory for which to calculate the space.
     * @return The percentage of space occupied by the directory and its subdirectories.
     */
    public List<Pair<String, Double>> calculateSpaceOccupiedPercentage(Directory directory) {
        List<Pair<String, Double>> result = new ArrayList<>();
        int totalFiles = countFiles(directory);
        calculateSpaceOccupiedPercentageRecursively(directory, totalFiles, result);
        return result;
    }


    /**
     * Calculates the percentage of space occupied by the specified directory and its subdirectories.
     * The result is added to the provided list as a Pair, where the first element is the directory's name
     * and the second element is the calculated percentage.
     *
     * @param directory The directory for which to calculate the space percentage.
     * @param totalFiles The total number of files considered for the calculation.
     * @param result The list to which the calculated space percentage is added as a Pair.
     */
    private void calculateSpaceOccupiedPercentageRecursively(Directory directory, int totalFiles, List<Pair<String, Double>> result) {
        Position<FileSystemElement> directoryPosition = findPosition(directory);
        if (directoryPosition != null) {
            calculateSpaceOccupiedPercentageRecursively(directoryPosition, totalFiles, result);
        }
    }

    /**
     * Recursively calculates the percentage of space occupied by the FileSystemElement at the specified position
     * and its children. If the element is a Directory, the calculation is performed for each child. If the element is a File,
     * the percentage is calculated based on the total number of files considered and added to the result list as a Pair,
     * where the first element is the file's name and the second element is the calculated percentage.
     *
     * @param position The position of the FileSystemElement for which to calculate the space percentage.
     * @param totalFiles The total number of files considered for the calculation.
     * @param result The list to which the calculated space percentage is added as Pairs.
     */
    private void calculateSpaceOccupiedPercentageRecursively(Position<FileSystemElement> position, int totalFiles, List<Pair<String, Double>> result) {
        FileSystemElement element = position.element();
        if (element instanceof Directory) {
            for (Position<FileSystemElement> childPosition : children(position)) {
                calculateSpaceOccupiedPercentageRecursively(childPosition, totalFiles, result);
            }
        } else if (element instanceof File) {
            double filePercentage = (1.0 / totalFiles) * 100;
            result.add(new Pair<>(element.getName(), filePercentage));
        }
    }

    /**
     * Counts the number of files in the specified directory and its subdirectories.
     *
     * @param directory The directory for which to count the files.
     * @return The total number of files in the directory and its subdirectories.
     */
    private int countFiles(Directory directory) {
        Position<FileSystemElement> directoryPosition = findPosition(directory);
        if (directoryPosition != null) {
            return countFilesRecursively(directoryPosition);
        }
        return 0;
    }


    /**
     * Recursively counts the number of files in the FileSystemElement at the specified position and its children.
     *
     * @param position The position of the FileSystemElement for which to count the files.
     * @return The total number of files in the element and its children.
     */
    private int countFilesRecursively(Position<FileSystemElement> position) {
        int count = 0;

        FileSystemElement element = position.element();
        if (element instanceof File) {
            count += 1;
        } else if (element instanceof Directory) {
            for (Position<FileSystemElement> childPosition : children(position)) {
                count += countFilesRecursively(childPosition);
            }
        }

        return count;
    }


    /**
     * Counts the number of directories in the specified directory and its subdirectories.
     *
     * @param directory The directory for which to count the directories.
     * @return The total number of directories in the directory and its subdirectories.
     */
    public int countDirectoriesInDirectory(Directory directory) {
        Position<FileSystemElement> directoryPosition = findPosition(directory);
        if (directoryPosition != null) {
            return countDirectoriesRecursively(directoryPosition);
        }
        return 0;
    }


    /**
     * Recursively counts the number of directories in the FileSystemElement at the specified position and its children.
     *
     * @param position The position of the FileSystemElement for which to count the directories.
     * @return The total number of directories in the element and its children.
     */
    private int countDirectoriesRecursively(Position<FileSystemElement> position) {
        int count = 0;

        FileSystemElement element = position.element();
        if (element instanceof Directory) {
            count++;

            for (Position<FileSystemElement> childPosition : children(position)) {
                count += countDirectoriesRecursively(childPosition);
            }
        }

        return count;
    }


    /**
     * Counts the total number of files in the specified directory and its subdirectories.
     *
     * @param directory The directory for which to count the total number of files.
     * @return The total number of files in the directory and its subdirectories.
     */
    public int countTotalFilesInDirectory(Directory directory) {
        Position<FileSystemElement> directoryPosition = findPosition(directory);
        if (directoryPosition != null) {
            return countTotalFiles(directoryPosition);
        }
        return 0;
    }


    /**
     * Recursively counts the total number of files in the FileSystemElement at the specified position and its children.
     *
     * @param position The position of the FileSystemElement for which to count the total number of files.
     * @return The total number of files in the element and its children.
     */
    private int countTotalFiles(Position<FileSystemElement> position) {
        int count = 0;

        FileSystemElement element = position.element();
        if (element instanceof File) {
            count++;
        } else if (element instanceof Directory) {
            for (Position<FileSystemElement> childPosition : children(position)) {
                count += countTotalFiles(childPosition);
            }
        }

        return count;
    }



    /**
     * Calculates the depth of a given directory in the file system tree.
     * The depth is defined as the number of levels between the specified directory
     * and the root of the file system.
     *
     * @param directory The directory for which the depth is calculated.
     * @return The depth of the directory, or -1 if the directory is not found.
     */
    public int calculateDepth(Directory directory) {
        Position<FileSystemElement> directoryPosition = findPosition(directory);
        if (directoryPosition != null) {
            return calculateDepthRecursively(directoryPosition);
        }
        return -1;
    }

    /**
     * Helper method to calculate the depth of a directory recursively.
     *
     * @param position The position of the directory in the file system tree.
     * @return The depth of the directory.
     */
    private int calculateDepthRecursively(Position<FileSystemElement> position) {
        int depth = 0;

        FileSystemElement element = position.element();
        while (position != null && position != root()) {
            position = parent(position);
            depth++;
        }

        return depth;
    }



    /**
     * Finds the top 5 directories in the file system with the most descendants.
     * A descendant is considered either a file or a directory contained within
     * the specified directory.
     *
     * @return A list of pairs containing the directory and the count of its descendants.
     */
    public List<Pair<Directory, Integer>> findTop5DirectoriesWithMostDescendants() {
        List<Pair<Directory, Integer>> topDirectories = new ArrayList<>();
        List<Directory> allDirectories = getDirectories();

        allDirectories.sort(Comparator.comparingInt(dir -> countDescendants((Directory) dir)).reversed());

        int count = 0;
        for (Directory directory : allDirectories) {
            topDirectories.add(new Pair<>(directory, countDescendants(directory)));
            count++;

            if (count >= 5) {
                break;
            }
        }

        return topDirectories;
    }

    private <T extends FileSystemElement> List<T> getElements(Class<T> elementClass) {
        List<T> elements = new ArrayList<>();
        for (Position<FileSystemElement> p : positions()) {
            if (elementClass.isInstance(p.element())) {
                elements.add((T) p.element());
            }
        }
        return elements;
    }

    public List<Directory> getDirectories() {
        return getElements(Directory.class);
    }

    public List<File> getFiles() {
        return getElements(File.class);
    }

    /**
     * Counts the number of descendants (files and directories) of a given directory.
     *
     * @param directory The directory for which descendants are counted.
     * @return The count of descendants.
     */
    private int countDescendants(Directory directory) {
        Position<FileSystemElement> directoryPosition = findPosition(directory);

        if (directoryPosition != null) {
            return countDescendantsRecursively(directoryPosition);
        }

        return 0;
    }

    /**
     * Helper method to count descendants recursively.
     *
     * @param position The position of the directory in the file system tree.
     * @return The count of descendants.
     */
    private int countDescendantsRecursively(Position<FileSystemElement> position) {
        int count = 0;

        for (Position<FileSystemElement> childPosition : children(position)) {
            FileSystemElement childElement = childPosition.element();
            if (childElement instanceof Directory || childElement instanceof File) {
                count++;
                count += countDescendantsRecursively(childPosition);
            }
        }

        return count;
    }



    /**
     * Returns a list of positions corresponding to elements that were removed from the file system.
     *
     * @return A list of removed positions
     */
    public List<Position<FileSystemElement>> getRemoveds(){
        return removeds;
    }


    /**
     * Returns a list of elements marked as favorites in the file system.
     *
     * @return A list of favorite elements.
     */
    public List<FileSystemElement> getFavorites(){
        return favorites;
    }


    /**
     * Returns a list of direct descendants (files or directories) of a given directory based on the specified criteria.
     *
     * @param directory      The directory for which descendants are retrieved.
     * @param filesOnly      True if only files should be included, false otherwise.
     * @param directoriesOnly True if only directories should be included, false otherwise.
     * @return A list of direct descendants based on the specified criteria.
     */
    public List<FileSystemElement> getDirectDescendants(Directory directory, boolean filesOnly, boolean directoriesOnly) {
        List<FileSystemElement> descendants = new ArrayList<>();
        Position<FileSystemElement> directoryPosition = findPosition(directory);

        if (directoryPosition != null) {
            for (Position<FileSystemElement> childPosition : children(directoryPosition)) {
                FileSystemElement child = childPosition.element();
                if ((child instanceof File && filesOnly) || (child instanceof Directory && directoriesOnly)) {
                    descendants.add(child);
                } else if (!filesOnly && !directoriesOnly) {
                    descendants.add(child);
                }
            }
        }

        return descendants;
    }

    /**
     * Returns a list of files that have been changed (edited) in the file system.
     *
     * @return A list of changed files.
     */
    public List<File> getChangedFiles(){
        List<File> changedFiles = new ArrayList<>();

        for(File f : getFiles()){
            if (f.getCountChanges() > 0) {
                changedFiles.add(f);
            }
        }
        return changedFiles;
    }

    /**
     * This method converts the file system structure into a formatted string
     * representation.
     *
     * @return A string representation of the file system structure.
     */
    @Override
    public String toString() {
        return toStringHelper(root(), 1);
    }

    /**
     * This method is a helper method that constructs a string representation of the
     * file system tree.
     * The method performs a depth-first traversal of the tree, and at each node, it
     * visits the node
     * itself (by converting it to a string), and then recursively visits all the
     * children of the node.
     *
     * @param position The current position in the file system tree.
     * @param level    The current level in the tree. This is used to print
     *                 indentation for nested levels.
     * @return A string representation of the subtree rooted at the current
     *         position.
     */
    private String toStringHelper(Position<FileSystemElement> position, int level) {
        StringBuilder sb = new StringBuilder(position.element().toString()); // visit (position)

        for (Position<FileSystemElement> w : children(position)) {
            sb.append("\n").append(printLevel(level)).append(toStringHelper(w, level + 1));
        }

        return sb.toString();
    }

    /**
     * Clears the contents of the `PersonalFileSystem` by removing all elements.
     * This method removes all children of the root node and clears the `fileList`
     * associated with the file system.
     */
    public void clear() {
        Position<FileSystemElement> rootPosition = root();

        for (Position<FileSystemElement> childPosition : children(rootPosition)) {
            remove(childPosition);
        }

        fileList.clear();
    }



    /**
     * This method is a helper method that constructs a string of spaces with the
     * length equivalent to the provided level.
     * This method is used to provide proper indentation for nested levels when
     * printing the file system tree.
     *
     * @param level The level for which the indentation string should be created.
     *              This corresponds to the number of spaces in the string.
     * @return A string containing level number of spaces, which can be used for
     *         indentation purposes.
     */
    private String printLevel(int level) {
        String aux = "";
        for (int i = 0; i < level; i++) {
            aux += " ";
        }
        return aux;
    }

    /**
     * Creates a snapshot of the current state of the file system.
     *
     * @return A `Memento` object representing the current state of the file system.
     */
    public Memento save(){
        return new Memento(this);
    }


    /**
     * Restores the file system to a previous state using a provided `Memento`.
     *
     * @param m The `Memento` containing the snapshot of the file system state to be restored.
     */
    public void restore(Memento m){

        clear();

        this.favorites.clear();
        this.removeds.clear();

        copyFrom(m.fileListMemento, this, m.fileListMemento.root(), this.root());
        this.favorites = new ArrayList<>(m.favoritesMemento);
        this.removeds = new ArrayList<>(m.removedsMemento);
    }


    /**
     * Helper method within the `PersonalFileSystem` class for copying elements from one
     * file system to another. It supports the copying of both files and directories along
     * with their hierarchical structure.
     *
     * @param source                    The source file system from which elements are to be copied.
     * @param destination               The destination file system to which elements are to be copied.
     * @param sourcePosition            The position of the element in the source file system.
     * @param destinationParentPosition The position of the parent directory in the destination file system.
     */
    private void copyFrom(PersonalFileSystem source, PersonalFileSystem destination,
                          Position<FileSystemElement> sourcePosition,
                          Position<FileSystemElement> destinationParentPosition) {
        FileSystemElement element = sourcePosition.element();

        if (element instanceof File) {
            Directory destinationParent = (destinationParentPosition != null) ? (Directory) destinationParentPosition.element() : (Directory) destination.root();
            destination.addFile(destinationParent, (File) element);

        } else if (element instanceof Directory) {
            // Verifica se já existe um diretório com o mesmo nome no destino
            Directory existingDirectory = findDirectoryByName(destination, element.getName());

            Directory newDirectory;

            if (existingDirectory != null) {
                newDirectory = existingDirectory; // Usa o diretório existente
            } else {
                Directory destinationParent = (destinationParentPosition != null) ? (Directory) destinationParentPosition.element() : (Directory) destination.root();
                newDirectory = new Directory(element.getName(), destinationParent);
                destination.addFolder(destinationParent, newDirectory);
            }

            for (Position<FileSystemElement> childPosition : source.children(sourcePosition)) {
                copyFrom(source, destination, childPosition, destination.findPosition(newDirectory));
            }
        }
    }

    /**
     * Helper method within the `PersonalFileSystem` class to find a directory by name
     * within the file system tree.
     *
     * @param tree The `PersonalFileSystem` tree in which to search for the directory.
     * @param name The name of the directory to find.
     * @return The found `Directory` object with the specified name, or null if not found.
     */
    private Directory findDirectoryByName(PersonalFileSystem tree, String name) {
        for (Position<FileSystemElement> position : tree.positions()) {
            if (position.element() instanceof Directory && position.element().getName().equals(name)) {
                return (Directory) position.element();
            }
        }
        return null;
    }

    /**
     * Inner class `Memento` within the `PersonalFileSystem` class that represents a snapshot
     * of the file system's state. It is used for implementing the Memento design pattern,
     * allowing the system to save and restore its state.
     *
     * This class includes fields to store a copy of the file system (`fileListMemento`),
     * a list of favorite elements (`favoritesMemento`), and a list of removed elements
     * (`removedsMemento`). The Memento is created by providing a `PersonalFileSystem`
     * instance, from which the state is copied.
     */
    public class Memento {
        private PersonalFileSystem fileListMemento;
        private List<FileSystemElement> favoritesMemento;
        private List<Position<FileSystemElement>> removedsMemento;

        /**
         * Constructs a `Memento` object by creating a snapshot of the provided
         * `PersonalFileSystem` instance.
         *
         * @param fileList The `PersonalFileSystem` instance to capture in the Memento.
         */
        public Memento(PersonalFileSystem fileList){

            this.fileListMemento = new PersonalFileSystem();
            this.favoritesMemento = new ArrayList<>(favorites);
            this.removedsMemento = new ArrayList<>(removeds);

            copyFrom(fileList, fileListMemento, fileList.root(), fileListMemento.root());

        }
    }
}