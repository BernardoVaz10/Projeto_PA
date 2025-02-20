package pt.pa.adts;

import pt.pa.model.PersonalFileSystem;

import java.util.Stack;

/**
 * The Caretaker class is responsible for managing the state of a PersonalFileSystem
 * using the Memento design pattern. It keeps track of snapshots (Mementos) of the file system
 * and provides functionality to save and undo states.
 *
 * @author Diogo Braizinha (202200223)
 *         Alessandro Aguiar (202200272)
 *         Bernardo Vaz (20220278)
 *         João Oliveira (202200191)
 * @version 1
 */
public class Caretaker {

    private Stack<PersonalFileSystem.Memento> mementos;
    private PersonalFileSystem pfs;

    /**
     * Creates a Caretaker with an associated PersonalFileSystem and initializes the stack of mementos.
     *
     * @param p The PersonalFileSystem instance to associate with the Caretaker.
     */
    public Caretaker(PersonalFileSystem p) {
        this.pfs = p;
        this.mementos = new Stack<>();
    }

    /**
     * Saves the current state of the associated PersonalFileSystem in a Memento.
     * Prints a message indicating that the state is being saved.
     */
    public void saveMemento() {
        System.out.println("Saving...");
        mementos.push(pfs.save());
    }

    /**
     * Reverts the state of the associated PersonalFileSystem to a previous state stored in a Memento.
     * Prints a message indicating that the undo operation is being performed.
     * If there are no Mementos in the stack, the undo operation is not executed.
     */
    public void undo() {
        if (!mementos.isEmpty()) {
            System.out.println("Undo...");
            PersonalFileSystem.Memento top = mementos.pop();
            pfs.restore(top);
        }
    }
}