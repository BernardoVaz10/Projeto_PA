package pt.pa.Test;
import org.junit.Before;
import org.junit.Test;
import pt.pa.adts.Position;
import pt.pa.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * TestPersonalFileSystem is a class that tests the functionality of
 * PersonalFileSystem.
 *
 * @author João Oliveira (202200191)
 *         Alessandro Aguiar (202200272)
 *         Bernardo Vaz (20220278)
 *         Diogo Braizinha (202200223)
 * @version 1
 */

public class TestPersonalFileSystem {
    PersonalFileSystem fileSystem;
    File newFile;

    @Before
    public void setUp() {
        fileSystem = new PersonalFileSystem();
    }

    @Test
    public void testCreateEmptyFileAndCreateEmptyDirectory() {
        Directory parentDirectory = fileSystem.createEmptyDirectory("ParentDirectory",
                (Directory) fileSystem.root().element());
        newFile = fileSystem.createEmptyFile("TestFile.txt", FileType.TXT, parentDirectory, true);
        assertNotNull(newFile);
        assertEquals("TestFile.txt", newFile.getName());
        assertEquals( FileType.TXT, newFile.getType());
        assertEquals("", newFile.getContent());
        assertTrue(newFile.isStatusUnlocked());
        assertEquals(fileSystem.findPosition(parentDirectory), fileSystem.parent(fileSystem.findPosition(newFile)));
    }

    @Test
    public void testCopyElement() {
        PersonalFileSystem pfs = new PersonalFileSystem();
        Directory sourceDirectory = pfs.createEmptyDirectory("sourceDirectory", (Directory) pfs.root().element());
        File sourceFile = pfs.createEmptyFile("SourceFile.txt", FileType.TXT, sourceDirectory, true);
        Directory destinationDirectory = pfs.createEmptyDirectory("DestinationDirectory",
                (Directory) pfs.root().element());
        File newFile = (File) pfs.copyElement(sourceFile, destinationDirectory);
        Position<FileSystemElement> aux2 = pfs.findPosition(newFile);
        Position<FileSystemElement> aux1 = pfs.parent(aux2);
        assertEquals(pfs.findPosition(destinationDirectory), aux1);
        Directory newDiretory = (Directory) pfs.copyElement(sourceDirectory, destinationDirectory);
        assertEquals(pfs.findPosition(destinationDirectory), pfs.parent(pfs.findPosition(newDiretory)));
        Position<FileSystemElement> aux = pfs.findPosition(destinationDirectory);
        for (Position<FileSystemElement> p : pfs.children(aux)) {
            if (pfs.children(p) == pfs.children(pfs.findPosition(sourceDirectory))) {
                assertTrue(true);
            }
        }
    }

    @Test
    public void testMoveElement() {
        Directory sourceParent = new Directory("SourceParent", null);
        Directory destination = new Directory("Destination", (Directory) fileSystem.root().element());
        fileSystem.addFolder((Directory) fileSystem.root().element(), sourceParent);
        fileSystem.addFolder((Directory) fileSystem.root().element(), destination);
        File sourceFile = new File("SourceFile", FileType.TXT, "File Content", true);
        fileSystem.addFile(sourceParent, sourceFile);
        File movedFile = (File) fileSystem.moveElement(sourceFile,  destination);
        assertNull(fileSystem.findPosition(sourceFile));
        Position<FileSystemElement> aux = fileSystem.findPosition(movedFile);
        assertNotNull(fileSystem.findPosition(fileSystem.parent(aux).element()));
        assertEquals("File Content", movedFile.getContent());
        assertNotNull(fileSystem.findPosition(sourceParent));
        assertEquals(fileSystem.findPosition(destination), fileSystem.parent(fileSystem.findPosition(movedFile)));

    }

    @Test
    public void testVisualize() {
        PersonalFileSystem fileSystem = new PersonalFileSystem();
        File file = new File("example.txt", FileType.TXT, "Conteúdo do arquivo", true);
        Directory directory = new Directory("docs", (Directory) fileSystem.root().element());
        fileSystem.addFile((Directory) fileSystem.root().element(), file);
        assertEquals("Conteúdo do arquivo", fileSystem.visualizeContent(file));
        assertNull(fileSystem.visualizeContent(directory));
    }

    @Test
    public void testEditContent() {
        PersonalFileSystem fileSystem = new PersonalFileSystem();
        File file = new File("example.txt", FileType.TXT, "Conteúdo inicial", true);
        fileSystem.addFile((Directory) fileSystem.root().element(), file);
        fileSystem.editContent(file, "Novo conteúdo");
        assertEquals("Novo conteúdo", file.getContent());
        file.lock();
        fileSystem.editContent(file, "Conteúdo Bloqueado");
        assertEquals("Novo conteúdo", file.getContent());
        file.unlock();
        fileSystem.editContent(file, "Conteúdo Novo");
        assertEquals("Conteúdo Novo", file.getContent());
    }

    @Test
    public void testDeleteElement() {
        // Crie uma instância da classe que contém o método deleteElement
        PersonalFileSystem pfs = new PersonalFileSystem();

        // Crie um diretório de teste
        Directory testDirectory = pfs.createEmptyDirectory("TestDirectory", (Directory) pfs.root().element());
        assertNotNull(testDirectory);

        // Crie um arquivo de teste
        File testFile = pfs.createEmptyFile("TestFile.txt", FileType.TXT, testDirectory, true);
        assertNotNull(testFile);

        // Chame o método deleteElement
        pfs.deleteElement(testFile);

        // Verifique se o arquivo foi removido do diretório
        assertNull(pfs.findPosition(testFile));

        // Verifique se o diretório não foi removido do diretório
        assertNotNull(pfs.findPosition(testDirectory));
    }

    @Test
    public void testRenameElement() {
        // Crie um diretório de teste
        Directory testDirectory = fileSystem.createEmptyDirectory("TestDirectory",
                (Directory) fileSystem.root().element());

        // Crie um arquivo de teste
        File testFile = fileSystem.createEmptyFile("TestFile", FileType.TXT, testDirectory, true);

        fileSystem.renameElement(testFile, "RenamedFile");
        assertEquals("RenamedFile", testFile.getName());

        fileSystem.renameElement(testDirectory, "RenamedDiretory");
        assertEquals("RenamedDiretory", testDirectory.getName());
    }

    @Test
    public void testToString() {
        File a = fileSystem.createEmptyFile("A", FileType.TXT, (Directory) fileSystem.root().element(), true);
        File b = fileSystem.createEmptyFile("B", FileType.TXT, (Directory) fileSystem.root().element(), true);

        Directory xx = fileSystem.createEmptyDirectory("XX", (Directory) fileSystem.root().element());

        File c = fileSystem.createEmptyFile("C", FileType.TXT, xx, true);
        File d = fileSystem.createEmptyFile("D", FileType.TXT, xx, true);
        File e = fileSystem.createEmptyFile("E", FileType.TXT, xx, true);

        System.out.println(fileSystem.toString());
    }

    @Test
    public void testGetDiretorys() {
        Directory xx = fileSystem.createEmptyDirectory("XX", (Directory) fileSystem.root().element());
        for (Directory p : fileSystem.getDirectories()) {
            System.out.println(p);
        }
    }
}

