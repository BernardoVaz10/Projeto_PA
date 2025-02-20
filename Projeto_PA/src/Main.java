package pt.pa;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pt.pa.graphicalInterface.PFSView;


/**
 * Main class
 *
 * @author João Oliveira (202200191)
 *         Alessandro Aguiar (202200272)
 *         Bernardo Vaz (20220278)
 *         Diogo Braizinha (202200223)
 * @version 1
 */

public class Main extends Application {
    TreeView treeView;

    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        PFSView view = new PFSView();

        Scene scene = new Scene(view, 1300, 768);

        primaryStage.setTitle("Personal File System");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}


