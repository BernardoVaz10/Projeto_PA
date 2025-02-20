package pt.pa.graphicalInterface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.util.Pair;
import pt.pa.adts.Caretaker;
import pt.pa.adts.Position;
import pt.pa.model.*;
import javafx.util.StringConverter;

import javax.crypto.spec.PSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

/**
 * Represents the main view of the file system hierarchy for a user.
 * It contains a TreeView for displaying the file system hierarchy and a toolbar
 * for adding, removing, and renaming files and directories.
 *
 * @author João Oliveira (202200191)
 *         Alessandro Aguiar (202200272)
 *         Bernardo Vaz (20220278)
 *         Diogo Braizinha (202200223)
 * @version 1
 */
public class PFSView extends BorderPane {
    private TreeView<String> pfsView;
    private PersonalFileSystem pfs;
    private ObservableList<Directory> directories;
    private ObservableList<File> files;
    private ObservableList<FileSystemElement> elements;
    private ObservableList<String> typeChoose;
    private Caretaker caretaker;
    private Button createElem, copyElem, moveElem, renameElem, removeElem, visualizeElem, editElem, favoriteElem, excludedElem, listElem,  estatisticElem,undoElem;
    private Label lchange, lchangeBox, counter, counterBox, date, dateBox;
    private TextArea contentArea;

    public PFSView() {
        this.pfsView = new TreeView<>();
        this.pfs = new PersonalFileSystem();
        this.caretaker = new Caretaker(pfs);

        createPfs();
        doLayout();

        createElemsButton();
        copyElemButton();
        moveElemButton();
        renameElemButton();
        removeElemButton();
        visualizeElemButton();
        editElemButton();
        favoriteElemButton();
        excludedElemButton();
        listElemButton();
        estatisticElemButton();
        undoElemButton();
    }

    public PersonalFileSystem getPFS() {
        return this.pfs;
    }

    /**
     * Creates the PersonalFileSystem object and initializes it with directories and
     * files.
     */
    private void createPfs() {
        TreeItem<String> root = new TreeItem<>(pfs.root().element().toString());
        pfsView.setRoot(root);

        Directory docs = pfs.createEmptyDirectory("docs", (Directory) pfs.root().element());
        Directory toPrint = pfs.createEmptyDirectory("to_print", (Directory) pfs.root().element());
        File todo = pfs.createEmptyFile("todo", FileType.TXT, (Directory) pfs.root().element(), true);
        Directory tools = pfs.createEmptyDirectory("tools", (Directory) pfs.root().element());
        File ev = pfs.createEmptyFile("ev", FileType.TXT, docs, true);
        File data = pfs.createEmptyFile("data", FileType.CSV, docs, true);
        File tetris = pfs.createEmptyFile("tetris", FileType.TXT, tools, true);
        File grep = pfs.createEmptyFile("grep", FileType.CSV, tools, true);
        Directory backup = pfs.createEmptyDirectory("backup", tools);

        TreeItem<String> branch1 = new TreeItem<>(docs.toString());
        TreeItem<String> branch2 = new TreeItem<>(toPrint.toString());
        TreeItem<String> branch3 = new TreeItem<>(todo.toString());
        TreeItem<String> branch4 = new TreeItem<>(tools.toString());
        TreeItem<String> branch5 = new TreeItem<>(ev.toString());
        TreeItem<String> branch6 = new TreeItem<>(data.toString());
        TreeItem<String> branch7 = new TreeItem<>(tetris.toString());
        TreeItem<String> branch8 = new TreeItem<>(grep.toString());
        TreeItem<String> branch9 = new TreeItem<>(backup.toString());

        root.getChildren().addAll(branch1, branch2, branch3, branch4);
        branch1.getChildren().addAll(branch5, branch6);
        branch4.getChildren().addAll(branch7, branch8, branch9);

        this.directories = FXCollections.observableArrayList();
        this.directories.addAll(pfs.getDirectories());

        this.files = FXCollections.observableArrayList();
        this.files.addAll(pfs.getFiles());

        this.elements = FXCollections.observableArrayList();
        this.elements.addAll(pfs.getDirectories());
        this.elements.addAll(pfs.getFiles());

        this.typeChoose = FXCollections.observableArrayList();
        this.typeChoose.addAll("TXT", "CSV");

    }

    /**
     * Sets up the layout for the PFSView by adding the TreeView to the center of
     * the BorderPane.
     */
    private void doLayout() {
        HBox toolBar = new HBox(10);
        toolBar.setPadding(new Insets(30));

        toolBar.setStyle("-fx-background-color: #DCDCDC;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 0 0 1 0;" + "-fx-border-color: black;");

        this.createElem = new Button("Criar");
        this.copyElem = new Button("Copiar");
        this.moveElem = new Button("Mover");
        this.renameElem = new Button("Renomear");
        this.removeElem = new Button("Remover");
        this.visualizeElem = new Button("Visualizar");
        this.editElem = new Button("Editar");
        this.undoElem = new Button("Undo");
        this.favoriteElem = new Button("Favoritos");
        this.excludedElem = new Button("Excluidos");
        this.listElem = new Button("Listas");
        this.estatisticElem = new Button("Estatistica");

        createElem.setPrefSize(100, 50);
        createElem.setFont(new Font(15));

        copyElem.setPrefSize(100, 50);
        copyElem.setFont(new Font(15));

        moveElem.setPrefSize(100, 50);
        moveElem.setFont(new Font(15));

        renameElem.setPrefSize(100, 50);
        renameElem.setFont(new Font(15));

        removeElem.setPrefSize(100, 50);
        removeElem.setFont(new Font(15));

        visualizeElem.setPrefSize(100, 50);
        visualizeElem.setFont(new Font(15));

        editElem.setPrefSize(100, 50);
        editElem.setFont(new Font(15));

        favoriteElem.setPrefSize(100, 50);
        favoriteElem.setFont(new Font(15));

        excludedElem.setPrefSize(100, 50);
        excludedElem.setFont(new Font(15));

        listElem.setPrefSize(100, 50);
        listElem.setFont(new Font(15));

        estatisticElem.setPrefSize(100, 50);
        estatisticElem.setFont(new Font(15));

        undoElem.setPrefSize(100, 50);
        undoElem.setFont(new Font(15));

        toolBar.getChildren().addAll(createElem, copyElem, moveElem, renameElem, removeElem,
                visualizeElem, editElem, favoriteElem, excludedElem, listElem, estatisticElem,undoElem);

        setTop(toolBar);

        this.pfsView.setPrefWidth(200);
        this.pfsView.setMinWidth(200);

        setLeft(this.pfsView);

        this.contentArea = new TextArea();
        contentArea.setEditable(false);
        contentArea.setWrapText(true);
        contentArea.setPrefHeight(400);
        contentArea.setPrefWidth(1100);
        contentArea.setMinWidth(1100);
        contentArea.setStyle("-fx-font-size: 20");

        setRight(contentArea);

        // Details (bottom part)
        HBox details = new HBox(10);
        details.setPadding(new Insets(30));

        details.setStyle("-fx-background-color: #DCDCDC;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 1 0 0 0;" + "-fx-border-color: black;");

        this.date = new Label("Data de criação:");
        this.dateBox = new Label("####");

        this.lchange = new Label(" Última alteração:");
        this.lchangeBox = new Label("####");

        this.counter = new Label(" Nº alterações:");
        this.counterBox = new Label("0");

        details.getChildren().addAll(date, dateBox, lchange, lchangeBox, counter, counterBox);

        setBottom(details);
    }

    private void createElemsButton() {
        createElem.setOnAction(event -> actionCreateButton());
    }

    private void actionCreateButton() {
        Stage stage = new Stage();
        stage.setTitle("Criar diretoria/ficheiro");

        Label labelCreate = new Label("Escolha uma opção:");

        Button createDir = new Button("Criar Diretoria");
        Button createFil = new Button("Criar Ficheiro");

        ComboBox<Directory> parentsChoose = new ComboBox<>(directories);
        ComboBox<String> types = new ComboBox<>(typeChoose);

        parentsChoose.setConverter(new dirConverter());
        types.setConverter(new TypeConverter());

        createDir.setOnAction(e -> createDirectory(stage, parentsChoose));
        createFil.setOnAction(e -> createFile(stage, parentsChoose, types));

        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(labelCreate, createDir, createFil);

        Scene scene = new Scene(vbox, 300, 200);

        stage.setScene(scene);
        stage.showAndWait();

        refreshComboBox();
    }

    private void createDirectory(Stage parentStage, ComboBox<Directory> parentsChoose) {
        Stage stageDir = createDirectoryStage(parentStage, parentsChoose);

        TextField dirName = createDirectoryNameField();
        Button selectD = createSelectButton(stageDir);

        selectD.setOnAction(ev -> handleCreateDirectory(parentsChoose, parentStage, stageDir, dirName));

        VBox vbox = createDirectoryVBox(dirName, parentsChoose, selectD);

        Scene scene = new Scene(vbox, 300, 250);
        stageDir.setScene(scene);
        stageDir.showAndWait();
    }

    private Stage createDirectoryStage(Stage parentStage, ComboBox<Directory> parentsChoose) {
        Stage stageDir = new Stage();
        stageDir.setTitle("Criar diretoria");
        return stageDir;
    }

    private TextField createDirectoryNameField() {
        TextField dirName = new TextField();
        dirName.setPromptText("Nome diretoria");
        return dirName;
    }

    private Button createSelectButton(Stage stageDir) {
        Button selectD = new Button("Criar");
        return selectD;
    }

    private void handleCreateDirectory(ComboBox<Directory> parentsChoose, Stage parentStage, Stage stageDir, TextField dirName) {
        caretaker.saveMemento();
        String name = dirName.getText().trim();
        Directory parent = parentsChoose.getValue(); // Utilize .getValue() em vez de .getSelectedItem()

        if (!name.isEmpty() && parent != null) {
            Directory add = pfs.createEmptyDirectory(name, parent);
            changeDetailsPanel();
            updateTreeView();

            // Fechar a janela de criação de arquivo
            parentStage.close();
            stageDir.close();
        } else {
            alertWarnings("Diretoria precisa ter um nome! \nDestino não foi selecionado!");
        }
    }


    private VBox createDirectoryVBox(TextField dirName, ComboBox<Directory> parentsChoose, Button selectD) {
        Label labName = new Label("Nome da diretoria:");
        Label dirChoose = new Label("Selecione a diretoria:");

        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(labName, dirName, dirChoose, parentsChoose, selectD);
        return vbox;
    }


    private void createFile(Stage parentStage, ComboBox<Directory> parentsChoose, ComboBox<String> types) {
        Stage stageFile = createFileStage(parentStage, parentsChoose, types);

        TextField fileName = createFileNameField();
        ToggleGroup group = createToggleGroup();
        Button select = createSelectButton(parentsChoose, stageFile, fileName, types, group);

        select.setOnAction(ev -> handleCreateFile(parentsChoose, parentStage, stageFile, fileName, types, group));

        VBox vbox = createFileVBox(fileName, types, parentsChoose, group, select);

        Scene scene = new Scene(vbox, 500, 450);
        stageFile.setScene(scene);
        stageFile.showAndWait();
    }

    private Stage createFileStage(Stage parentStage, ComboBox<Directory> parentsChoose, ComboBox<String> types) {
        Stage stageFile = new Stage();
        stageFile.setTitle("Criar ficheiro");
        return stageFile;
    }

    private TextField createFileNameField() {
        TextField fileName = new TextField();
        fileName.setPromptText("Nome ficheiro");
        return fileName;
    }

    private ToggleGroup createToggleGroup() {
        ToggleGroup group = new ToggleGroup();
        return group;
    }

    private Button createSelectButton(ComboBox<Directory> parentsChoose, Stage stageFile, TextField fileName, ComboBox<String> types, ToggleGroup group) {
        Button select = new Button("Criar");
        return select;
    }

    private void handleCreateFile(ComboBox<Directory> parentsChoose, Stage parentStage, Stage stageFile, TextField fileName, ComboBox<String> types, ToggleGroup group) {
        caretaker.saveMemento();
        String name = fileName.getText().trim();
        String type = types.getValue();

        if (!name.isEmpty() && type != null) {
            Directory parent = parentsChoose.getValue();
            boolean isUnlocked = getBooleanValue(group);

            File add = pfs.createEmptyFile(name, FileType.valueOf(type), parent, isUnlocked);
            changeDetailsPanel();
            updateTreeView();
            closeStages(parentStage, stageFile);
        } else {
            alertWarnings("Ficheiro precisa ter um nome! \nTipo de ficheiro não foi selecionado!");
        }
    }

    private VBox createFileVBox(TextField fileName, ComboBox<String> types, ComboBox<Directory> parentsChoose, ToggleGroup group, Button select) {
        Label labName = new Label("Nome do ficheiro:");
        Label fileType = new Label("Selecione o tipo do ficheiro:");
        Label dirChoose = new Label("Selecione a diretoria:");
        Label statusLabel = new Label("Status (unlocked / locked):");

        RadioButton trueButton = new RadioButton("Unlocked");
        RadioButton falseButton = new RadioButton("Locked");
        trueButton.setToggleGroup(group);
        falseButton.setToggleGroup(group);
        trueButton.setSelected(true);

        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(labName, fileName, fileType, types, dirChoose, parentsChoose, statusLabel, trueButton, falseButton, select);
        return vbox;
    }

    private void closeStages(Stage... stages) {
        for (Stage stage : stages) {
            if (stage != null) {
                stage.close();
            }
        }
    }

    private void updateTreeView() {
        pfsView.getRoot().getChildren().clear();
        buildTreeView(pfsView.getRoot(), pfs.root());
    }







    private void copyElemButton() {
        copyElem.setOnAction(event -> handleCopyElement());
    }

    private void handleCopyElement() {
        Stage stageCopy = createCopyStage();
        ComboBox<FileSystemElement> elementChoose = createElementComboBox();
        ComboBox<Directory> parentsChoose = createDirectoryComboBox();
        Button copy = createCopyButton(stageCopy, elementChoose, parentsChoose);

        copy.setOnAction(ev -> handleCopyAction(stageCopy, elementChoose, parentsChoose));

        VBox vbox = createCopyVBox(elementChoose, parentsChoose, copy);

        Scene scene = new Scene(vbox, 300, 250);

        stageCopy.setScene(scene);
        stageCopy.showAndWait();
        refreshComboBox();
    }

    private Stage createCopyStage() {
        Stage stageCopy = new Stage();
        stageCopy.setTitle("Copiar Diretório/Ficheiro");
        return stageCopy;
    }

    private ComboBox<FileSystemElement> createElementComboBox() {
        ComboBox<FileSystemElement> elementChoose = new ComboBox<>(this.elements);
        return elementChoose;
    }

    private ComboBox<Directory> createDirectoryComboBox() {
        ComboBox<Directory> parentsChoose = new ComboBox<>(directories);
        return parentsChoose;
    }

    private Button createCopyButton(Stage stageCopy, ComboBox<FileSystemElement> elementChoose, ComboBox<Directory> parentsChoose) {
        Button copy = new Button("Copiar");
        return copy;
    }

    private void handleCopyAction(Stage stageCopy, ComboBox<FileSystemElement> elementChoose, ComboBox<Directory> parentsChoose) {
        caretaker.saveMemento();
        FileSystemElement copied = elementChoose.getValue();
        Directory parent = parentsChoose.getValue();

        if (copied != null && parent != null) {
            pfs.copyElement(copied, parent);

            changeDetailsPanel();

            pfsView.getRoot().getChildren().clear();
            buildTreeView(pfsView.getRoot(), pfs.root());

            // Fechar a janela de copia de arquivo
            ((Stage) stageCopy.getScene().getWindow()).close();
        } else {
            alertWarnings("Nenhum ficheiro/diretoria foi selecionado! \nDestino não foi selecionado!");
        }
    }

    private VBox createCopyVBox(ComboBox<FileSystemElement> elementChoose, ComboBox<Directory> parentsChoose, Button copy) {
        Label fileSystem = new Label("Selecione o Diretório/Ficheiro:");
        Label dirChoose = new Label("Selecione a diretoria:");

        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(fileSystem, elementChoose, dirChoose, parentsChoose, copy);
        return vbox;
    }


    private void moveElemButton() {
        moveElem.setOnAction(event -> handleMoveElement());
    }

    private void handleMoveElement() {
        Stage stageMove = createMoveStage();
        ComboBox<FileSystemElement> elementChoose = createElementComboBox();
        ComboBox<Directory> parentsChoose = createDirectoryComboBox();
        Button move = createMoveButton(stageMove, elementChoose, parentsChoose);

        move.setOnAction(ev -> handleMoveAction(stageMove, elementChoose, parentsChoose));

        VBox vbox = createMoveVBox(elementChoose, parentsChoose, move);

        Scene scene = new Scene(vbox, 300, 250);

        stageMove.setScene(scene);
        stageMove.showAndWait();
        refreshComboBox();
    }

    private Stage createMoveStage() {
        Stage stageMove = new Stage();
        stageMove.setTitle("Mover Diretório/Ficheiro");
        return stageMove;
    }

    private Button createMoveButton(Stage stageMove, ComboBox<FileSystemElement> elementChoose, ComboBox<Directory> parentsChoose) {
        Button move = new Button("Mover");
        return move;
    }

    private void handleMoveAction(Stage stageMove, ComboBox<FileSystemElement> elementChoose, ComboBox<Directory> parentsChoose) {
        caretaker.saveMemento();
        FileSystemElement elementToMove = elementChoose.getValue();
        Directory destination = parentsChoose.getValue();

        if (elementToMove != null && destination != null) {
            pfs.moveElement(elementToMove, destination);

            changeDetailsPanel();

            pfsView.getRoot().getChildren().clear();
            buildTreeView(pfsView.getRoot(), pfs.root());

            stageMove.close();
        } else {
            alertWarnings("Nenhum ficheiro/diretoria foi selecionado! \nDestino não foi selecionado!");
        }
    }

    private VBox createMoveVBox(ComboBox<FileSystemElement> elementChoose, ComboBox<Directory> parentsChoose, Button move) {
        Label fileSystem = new Label("Selecione o Diretório/Ficheiro:");
        Label dirChoose = new Label("Selecione a diretoria:");

        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(fileSystem, elementChoose, dirChoose, parentsChoose, move);
        return vbox;
    }


    private void renameElemButton() {
        renameElem.setOnAction(event -> handleRenameElement());
    }

    private void handleRenameElement() {
        Stage renameStage = createRenameStage();
        ComboBox<FileSystemElement> elementChoose = createElementComboBox();
        elementChoose.getItems().remove(pfs.root().element());

        Label newFileName = new Label("Novo Nome:");
        TextField newNameField = createNewNameField();

        Button renameBtn = createRenameButton(renameStage, elementChoose, newNameField);
        renameBtn.setOnAction(e -> handleRenameAction(renameStage, elementChoose, newNameField));

        VBox renameVBox = createRenameVBox(elementChoose, newFileName, newNameField, renameBtn);

        Scene renameScene = new Scene(renameVBox, 300, 250);
        renameStage.setScene(renameScene);
        renameStage.showAndWait();
        refreshComboBox();
    }

    private Stage createRenameStage() {
        Stage renameStage = new Stage();
        renameStage.setTitle("Renomear Diretório/Ficheiro");
        return renameStage;
    }

    private TextField createNewNameField() {
        TextField newNameField = new TextField();
        newNameField.setPromptText("Novo Nome");
        return newNameField;
    }

    private Button createRenameButton(Stage renameStage, ComboBox<FileSystemElement> elementChoose, TextField newNameField) {
        Button renameBtn = new Button("Renomear");
        return renameBtn;
    }

    private void handleRenameAction(Stage renameStage, ComboBox<FileSystemElement> elementChoose, TextField newNameField) {
        caretaker.saveMemento();
        String newName = newNameField.getText().trim();
        FileSystemElement selectedElement = elementChoose.getValue();

        if (!newName.isEmpty() && selectedElement != null) {
            pfs.renameElement(selectedElement, newName);

            changeDetailsPanel();

            pfsView.getRoot().getChildren().clear();
            buildTreeView(pfsView.getRoot(), pfs.root());

            renameStage.close();
        } else {
            alertWarnings("Nenhum ficheiro/diretoria foi selecionado! \nNovo nome não foi inserido!");
        }
    }

    private VBox createRenameVBox(ComboBox<FileSystemElement> elementChoose, Label newFileName, TextField newNameField, Button renameBtn) {
        Label fileSystem = new Label("Selecione o Diretório/Ficheiro:");

        VBox renameVBox = new VBox(20);
        renameVBox.setPadding(new Insets(20));
        renameVBox.getChildren().addAll(fileSystem, elementChoose, newFileName, newNameField, renameBtn);
        return renameVBox;
    }


    private void removeElemButton() {
        removeElem.setOnAction(event -> handleRemoveElement());
    }

    private void handleRemoveElement() {
        Stage removeStage = createRemoveStage();
        ComboBox<FileSystemElement> elementChoose = createElementComboBox();
        elementChoose.getItems().remove(pfs.root().element());

        Button removeBtn = createRemoveButton(removeStage, elementChoose);
        removeBtn.setOnAction(e -> handleRemoveAction(removeStage, elementChoose));

        VBox removeVBox = createRemoveVBox(elementChoose, removeBtn);

        Scene removeScene = new Scene(removeVBox, 300, 150);
        removeStage.setScene(removeScene);
        removeStage.showAndWait();

        refreshComboBox();
    }

    private Stage createRemoveStage() {
        Stage removeStage = new Stage();
        removeStage.setTitle("Remover Diretório/Ficheiro");
        return removeStage;
    }

    private Button createRemoveButton(Stage removeStage, ComboBox<FileSystemElement> elementChoose) {
        Button removeBtn = new Button("Remover");
        return removeBtn;
    }

    private void handleRemoveAction(Stage removeStage, ComboBox<FileSystemElement> elementChoose) {
        caretaker.saveMemento();
        FileSystemElement selectedElement = elementChoose.getValue();

        if (selectedElement != null) {
            pfs.deleteElement(selectedElement);

            changeDetailsPanel();

            pfsView.getRoot().getChildren().clear();
            buildTreeView(pfsView.getRoot(), pfs.root());

            removeStage.close();
        } else {
            alertWarnings("Nenhum ficheiro/diretoria foi selecionado!");
        }
    }

    private VBox createRemoveVBox(ComboBox<FileSystemElement> elementChoose, Button removeBtn) {
        Label fileSystem = new Label("Selecione o Diretório/Ficheiro:");

        VBox removeVBox = new VBox(20);
        removeVBox.setPadding(new Insets(20));
        removeVBox.getChildren().addAll(fileSystem, elementChoose, removeBtn);
        return removeVBox;
    }


    private void visualizeElemButton() {
        visualizeElem.setOnAction(event -> handleVisualizeElement());
    }

    private void handleVisualizeElement() {
        Stage visStage = createVisualizeStage();

        ComboBox<File> filesChoose = createFilesComboBox();
        Button visBtn = createVisualizeButton(visStage, filesChoose);
        Button clearBtn = createClearButton(visStage);

        VBox visVBox = createVisualizeVBox(filesChoose, visBtn, clearBtn);

        Scene visScene = new Scene(visVBox, 300, 200);
        visStage.setScene(visScene);
        visStage.showAndWait();

        refreshComboBox();
    }

    private Stage createVisualizeStage() {
        Stage visStage = new Stage();
        visStage.setTitle("Visualizar Ficheiro");
        return visStage;
    }

    private ComboBox<File> createFilesComboBox() {
        Label fileSystem = new Label("Selecione o Ficheiro:");
        ComboBox<File> filesChoose = new ComboBox<>(this.files);
        return filesChoose;
    }

    private Button createVisualizeButton(Stage visStage, ComboBox<File> filesChoose) {
        Button visBtn = new Button("Visualizar");
        visBtn.setOnAction(e -> handleVisualizeAction(visStage, filesChoose));
        return visBtn;
    }

    private void handleVisualizeAction(Stage visStage, ComboBox<File> filesChoose) {
        caretaker.saveMemento();
        File selectedFile = filesChoose.getValue();

        if (selectedFile != null) {
            String content = pfs.visualizeContent(selectedFile);
            contentArea.setText(content);
            visStage.close();
        } else {
            alertWarnings("Nenhum ficheiro foi selecionado!");
        }
    }

    private Button createClearButton(Stage visStage) {
        Button clearBtn = new Button("Limpar");
        clearBtn.setOnAction(e -> handleClearAction(visStage));
        return clearBtn;
    }

    private void handleClearAction(Stage visStage) {
        caretaker.saveMemento();
        if (!contentArea.getText().isEmpty()) {
            contentArea.setText("");
            visStage.close();
        } else {
            alertWarnings("Não existe texto para apagar!");
        }
    }

    private VBox createVisualizeVBox(ComboBox<File> filesChoose, Button visBtn, Button clearBtn) {
        VBox visVBox = new VBox(20);
        visVBox.setPadding(new Insets(20));
        visVBox.getChildren().addAll(filesChoose, visBtn, clearBtn);
        return visVBox;
    }


    private void editElemButton() {
        editElem.setOnAction(event -> handleEditElement());
    }

    private void handleEditElement() {
        caretaker.saveMemento();

        Stage editStage = createEditStage();

        ComboBox<File> filesChoose = createFilesComboBox();
        Label newFileContent = createNewLabel("Novo Conteúdo:");
        TextArea newContentField = createTextArea();
        Button editBtn = createEditButton(editStage, filesChoose, newContentField);
        Button cleanCtn = createCleanContentButton(editStage, filesChoose);

        VBox editVBox = createEditVBox(filesChoose, newFileContent, newContentField, editBtn, cleanCtn);

        Scene editScene = new Scene(editVBox, 300, 300);
        editStage.setScene(editScene);
        editStage.showAndWait();

        refreshComboBox();
    }

    private Stage createEditStage() {
        Stage editStage = new Stage();
        editStage.setTitle("Editar Ficheiro");
        return editStage;
    }

    private Label createNewLabel(String text) {
        Label newFileContent = new Label(text);
        return newFileContent;
    }

    private TextArea createTextArea() {
        TextArea newContentField = new TextArea();
        newContentField.setWrapText(true);
        newContentField.setPromptText("Novo Conteúdo");
        return newContentField;
    }

    private Button createEditButton(Stage editStage, ComboBox<File> filesChoose, TextArea newContentField) {
        Button editBtn = new Button("Editar");
        editBtn.setOnAction(e -> handleEditAction(editStage, filesChoose, newContentField));
        return editBtn;
    }

    private void handleEditAction(Stage editStage, ComboBox<File> filesChoose, TextArea newContentField) {
        String newContent = newContentField.getText().trim();
        File selectedFile = filesChoose.getValue();

        if (!newContent.isEmpty() && selectedFile != null) {
            pfs.editContent(selectedFile, newContent);
            changeDetailsPanel();
            updateTreeView();
            editStage.close();
        } else {
            alertWarnings("Nenhum ficheiro foi selecionado! \nNenhum conteúdo foi inserido!");
        }
    }

    private Button createCleanContentButton(Stage editStage, ComboBox<File> filesChoose) {
        Button cleanCtn = new Button("Limpar Conteúdo");
        cleanCtn.setOnAction(e -> handleCleanContentAction(editStage, filesChoose));
        return cleanCtn;
    }

    private void handleCleanContentAction(Stage editStage, ComboBox<File> filesChoose) {
        File selectedFile = filesChoose.getValue();

        if (selectedFile != null) {
            selectedFile.setContent("");
            changeDetailsPanel();
            updateTreeView();
            editStage.close();
        } else {
            alertWarnings("Nenhum ficheiro foi selecionado!");
        }
    }

    private VBox createEditVBox(ComboBox<File> filesChoose, Label newFileContent,
                                TextArea newContentField, Button editBtn, Button cleanCtn) {
        VBox editVBox = new VBox(20);
        editVBox.setPadding(new Insets(20));
        editVBox.getChildren().addAll(filesChoose, newFileContent, newContentField, editBtn, cleanCtn);
        return editVBox;
    }

    private void favoriteElemButton() {
        favoriteElem.setOnAction(event -> {
            caretaker.saveMemento();

            TreeItem<String> item = pfsView.getSelectionModel().getSelectedItem();
            for(FileSystemElement f : pfs.elements()){
                if(item.getValue().equals(f.toString())){
                    pfs.addRemoveFavorites(f);
                }
            }

            System.out.println(pfs.getFavorites());

            pfsView.getRoot().getChildren().clear();
            buildTreeView(pfsView.getRoot(), pfs.root());

            refreshComboBox();
        });
    }

    private void excludedElemButton() {
        excludedElem.setOnAction(event -> handleExcludedElement());
    }

    private void handleExcludedElement() {
        caretaker.saveMemento();

        Stage excludedStage = createExcludedStage();
        ComboBox<Position<FileSystemElement>> excludedChoose = createExcludedComboBox();
        Button excludedBtn = createExcludedButton(excludedStage, excludedChoose);

        VBox excludedVBox = createExcludedVBox(excludedChoose, excludedBtn);
        Scene excludedScene = createExcludedScene(excludedVBox);

        excludedStage.setScene(excludedScene);
        excludedStage.showAndWait();

        refreshComboBox();
    }

    private Stage createExcludedStage() {
        Stage excludedStage = new Stage();
        excludedStage.setTitle("Excluidos");
        return excludedStage;
    }

    private ComboBox<Position<FileSystemElement>> createExcludedComboBox() {
        ObservableList<Position<FileSystemElement>> removeds = FXCollections.observableArrayList(pfs.getRemoveds());

        ComboBox<Position<FileSystemElement>> excludedChoose = new ComboBox<>(removeds);

        excludedChoose.setConverter(new StringConverter<Position<FileSystemElement>>() {
            @Override
            public String toString(Position<FileSystemElement> position) {
                return (position != null) ? position.element().getName() : null;
            }

            @Override
            public Position<FileSystemElement> fromString(String string) {
                return null;
            }
        });

        return excludedChoose;
    }

    private Button createExcludedButton(Stage excludedStage, ComboBox<Position<FileSystemElement>> excludedChoose) {
        Button excludedBtn = new Button("Restaurar");
        excludedBtn.setOnAction(e -> handleExcludedAction(excludedStage, excludedChoose));
        return excludedBtn;
    }

    private void handleExcludedAction(Stage excludedStage, ComboBox<Position<FileSystemElement>> excludedChoose) {
        Position<FileSystemElement> selectedExcluded = excludedChoose.getValue();

        if (selectedExcluded != null) {
            Directory parent = (selectedExcluded instanceof File)
                    ? ((File) selectedExcluded.element()).getParent()
                    : ((Directory) selectedExcluded.element()).getParent();

            pfs.excludedElement(selectedExcluded.element(), parent);
            pfs.removeRemoveds(selectedExcluded);

            updateTreeView();
        }

        excludedStage.close();
    }

    private VBox createExcludedVBox(ComboBox<Position<FileSystemElement>> excludedChoose, Button excludedBtn) {
        VBox excludedVBox = new VBox(20);
        excludedVBox.setPadding(new Insets(20));
        excludedVBox.getChildren().addAll(excludedChoose, excludedBtn);
        return excludedVBox;
    }

    private Scene createExcludedScene(VBox excludedVBox) {
        Scene excludedScene = new Scene(excludedVBox, 300, 225);
        return excludedScene;
    }


    private void listElemButton() {
        listElem.setOnAction(event -> handleListElement());
    }

    private void handleListElement() {
        caretaker.saveMemento();

        Stage listStage = createListStage();
        ComboBox<Directory> options = createDirectoriesComboBox();

        RadioButton trueButton = new RadioButton("Ficheiros");
        RadioButton falseButton = new RadioButton("Diretórios");
        trueButton.setSelected(true);

        Button directBtn = createDescendentsButton(options, trueButton, falseButton);
        Button changedButton = createChangedFilesButton();
        Button lastFiles = createLastFilesButton();
        Button lastFilesChanged = createLastFilesChangedButton();

        VBox listVBox = createListVBox(options, trueButton, falseButton, directBtn, changedButton, lastFiles, lastFilesChanged);
        Scene listScene = createListScene(listVBox);

        listStage.setScene(listScene);
        listStage.showAndWait();

        refreshComboBox();
    }

    private Stage createListStage() {
        Stage listStage = new Stage();
        listStage.setTitle("Listas");
        return listStage;
    }

    private ComboBox<Directory> createDirectoriesComboBox() {
        ComboBox<Directory> options = new ComboBox<>(this.directories);
        return options;
    }

    private Button createDescendentsButton(ComboBox<Directory> options, RadioButton trueButton, RadioButton falseButton) {
        Button directBtn = new Button("Descendentes diretos");
        directBtn.setOnAction(e -> handleDescendentsAction(options, trueButton, falseButton));
        return directBtn;
    }

    private void handleDescendentsAction(ComboBox<Directory> options, RadioButton trueButton, RadioButton falseButton) {
        if ((trueButton.isSelected() || falseButton.isSelected()) && options.getValue() != null) {
            List<FileSystemElement> descendantsList = pfs.getDirectDescendants(options.getValue(), trueButton.isSelected(), falseButton.isSelected());

            StringBuilder stringBuilder = new StringBuilder();

            for (FileSystemElement element : descendantsList) {
                stringBuilder.append(element.toString()).append("\n");
            }

            contentArea.setText(stringBuilder.toString());
        } else {
            alertWarnings("Nenhuma opção foi selecionada! \nNenhuma directoria foi selecionada!");
        }
    }

    private Button createChangedFilesButton() {
        Button changedButton = new Button("Ficheiros alterados");
        changedButton.setOnAction(e -> handleChangedFilesAction());
        return changedButton;
    }

    private void handleChangedFilesAction() {
        List<File> changedFilesList = pfs.getChangedFiles();

        StringBuilder stringBuilder = new StringBuilder();

        if (!changedFilesList.isEmpty()) {
            for (File element : changedFilesList) {
                stringBuilder.append(element.toString()).append(" - ").append(element.getCountChanges()).append("\n");
            }
        } else {
            alertWarnings("Nenhum ficheiro foi alterado!");
        }

        contentArea.setText(stringBuilder.toString());
    }

    private Button createLastFilesButton() {
        Button lastFiles = new Button("Ultimos Ficheiros");
        lastFiles.setOnAction(e -> handleLastFilesAction());
        return lastFiles;
    }

    private void handleLastFilesAction() {
        List<DateNameFileSystemElement> date = pfs.getDates();
        Collections.sort(date, Collections.reverseOrder());

        List<DateNameFileSystemElement> vinteDatasMaisRecentes = date.subList(0, Math.min(20, date.size()));

        ObservableList<DateNameFileSystemElement> observableList = FXCollections.observableList(vinteDatasMaisRecentes);

        StringBuilder stringBuilder = new StringBuilder();

        for (DateNameFileSystemElement data : observableList) {
            stringBuilder.append(data.getData().toString()).append(" - ").append(data.getNomeArquivo()).append("\n");
        }

        contentArea.setText(stringBuilder.toString());
    }

    private Button createLastFilesChangedButton() {
        Button lastFilesChanged = new Button("Ultimos Ficheiros Alterados");
        lastFilesChanged.setOnAction(e -> handleLastFilesChangedAction());
        return lastFilesChanged;
    }

    private void handleLastFilesChangedAction() {
        List<File> changedFilesList = pfs.getChangedFiles();

        List<File> dezFicheirosAlteradosMaisRecentes = changedFilesList.subList(0, Math.min(10, changedFilesList.size()));

        StringBuilder stringBuilder = new StringBuilder();

        if (!changedFilesList.isEmpty()) {
            for (File element : dezFicheirosAlteradosMaisRecentes) {
                stringBuilder.append(element.toString()).append(" - ").append(element.getCountChanges()).append(" - ").append(element.getDate());
            }
        } else {
            alertWarnings("Nenhum ficheiro foi alterado!");
        }

        contentArea.setText(stringBuilder.toString());
    }

    private VBox createListVBox(ComboBox<Directory> options, RadioButton trueButton, RadioButton falseButton, Button directBtn, Button changedButton, Button lastFiles, Button lastFilesChanged) {
        VBox listVBox = new VBox(20);
        listVBox.setPadding(new Insets(20));
        listVBox.getChildren().addAll(options, trueButton, falseButton, directBtn, changedButton, lastFiles, lastFilesChanged);
        return listVBox;
    }

    private Scene createListScene(VBox listVBox) {
        Scene listScene = new Scene(listVBox, 300, 300);
        return listScene;
    }

    private void estatisticElemButton() {
        estatisticElem.setOnAction(event -> handleEstatisticAction());
    }

    private void handleEstatisticAction() {
        caretaker.saveMemento();

        Stage listStage = createEstatisticStage();
        ComboBox<Directory> options = createDirectoriesComboBoxForEstatistic();

        Button espacoOcupado = createEspacoOcupadoButton(options);
        Button diretoriasFicheirosTotais = createDiretoriasFicheirosTotaisButton(options);
        Button profundidade = createProfundidadeButton(options);
        Button topCinco = createTopCincoButton(options);

        VBox listVBox = createEstatisticVBox(options, espacoOcupado, diretoriasFicheirosTotais, profundidade, topCinco);
        Scene listScene = createEstatisticScene(listVBox);

        listStage.setScene(listScene);
        listStage.showAndWait();

        refreshComboBox();
    }

    private Stage createEstatisticStage() {
        Stage listStage = new Stage();
        listStage.setTitle("Estatísticas");
        return listStage;
    }

    private ComboBox<Directory> createDirectoriesComboBoxForEstatistic() {
        ComboBox<Directory> options = new ComboBox<>(this.directories);
        return options;
    }

    private Button createEspacoOcupadoButton(ComboBox<Directory> options) {
        Button espacoOcupado = new Button("Espaço Ocupado");
        espacoOcupado.setOnAction(e -> handleEspacoOcupadoAction(options));
        return espacoOcupado;
    }

    private void handleEspacoOcupadoAction(ComboBox<Directory> options) {
        contentArea.clear();

        Directory directory = options.getSelectionModel().getSelectedItem();

        List<Pair<String, Double>> space = pfs.calculateSpaceOccupiedPercentage(directory);

        for (Pair<String,Double> p : space) {
            contentArea.appendText(p.toString() + "\n");
        }
    }

    private Button createDiretoriasFicheirosTotaisButton(ComboBox<Directory> options) {
        Button diretoriasFicheirosTotais = new Button("Diretorias e Ficheiros Totais");
        diretoriasFicheirosTotais.setOnAction(e -> handleDiretoriasFicheirosTotaisAction(options));
        return diretoriasFicheirosTotais;
    }

    private void handleDiretoriasFicheirosTotaisAction(ComboBox<Directory> options) {
        contentArea.clear();

        Directory directory = options.getSelectionModel().getSelectedItem();

        int files = pfs.countTotalFilesInDirectory(directory);
        int directories = pfs.countDirectoriesInDirectory(directory);
        directories--;

        contentArea.appendText("Número de ficheiros: " + files + "\n");
        contentArea.appendText("Número de directorias: " + directories);
    }

    private Button createProfundidadeButton(ComboBox<Directory> options) {
        Button profundidade = new Button("Profundidade");
        profundidade.setOnAction(e -> handleProfundidadeAction(options));
        return profundidade;
    }

    private void handleProfundidadeAction(ComboBox<Directory> options) {
        contentArea.clear();

        Directory directory = options.getSelectionModel().getSelectedItem();
        int depth = pfs.calculateDepth(directory);

        contentArea.appendText("Profundidade: " + depth + "\n");
    }

    private Button createTopCincoButton(ComboBox<Directory> options) {
        Button topCinco = new Button("Top 5 Maiores Descendentes");
        topCinco.setOnAction(e -> handleTopCincoAction(options));
        return topCinco;
    }

    private void handleTopCincoAction(ComboBox<Directory> options) {
        contentArea.clear();

        Directory directory = options.getSelectionModel().getSelectedItem();

        List<Pair<Directory, Integer>> top5 = pfs.findTop5DirectoriesWithMostDescendants();
        for (Pair<Directory, Integer> p : top5) {
            contentArea.appendText(p.toString() + "\n");
        }
    }

    private VBox createEstatisticVBox(ComboBox<Directory> options, Button espacoOcupado, Button diretoriasFicheirosTotais, Button profundidade, Button topCinco) {
        VBox listVBox = new VBox(20);
        listVBox.setPadding(new Insets(20));
        listVBox.getChildren().addAll(options, espacoOcupado, diretoriasFicheirosTotais, profundidade, topCinco);
        return listVBox;
    }

    private Scene createEstatisticScene(VBox listVBox) {
        Scene listScene = new Scene(listVBox, 300, 300);
        return listScene;
    }

    private void undoElemButton() {
        undoElem.setOnAction(event -> {
            caretaker.undo();

            pfsView.getRoot().getChildren().clear();
            buildTreeView(pfsView.getRoot(), pfs.root());

            refreshComboBox();
        });
    }

    private void alertWarnings(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    private void changeDetailsPanel() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = currentTime.format(formatter);

        dateBox.setText(formattedDateTime);
        lchangeBox.setText(formattedDateTime);

        int currentValue = Integer.parseInt(counterBox.getText());
        currentValue++;
        counterBox.setText(String.valueOf(currentValue));
    }


    /**
     * Gets the boolean value of the selected radio button in the given group.
     * The boolean value is true if the selected radio button has the text
     * "Unlocked", otherwise it is false.
     *
     * @param group The toggle group containing the radio buttons.
     * @return The boolean value of the selected radio button.
     */
    private boolean getBooleanValue(ToggleGroup group) {
        RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();

        return selectedRadioButton.getText().equals("Unlocked");
    }

    private void refreshComboBox(){
        this.directories.clear();
        this.directories.addAll(pfs.getDirectories());

        this.files.clear();
        this.files.addAll(pfs.getFiles());

        this.elements.clear();
        this.elements.addAll(pfs.getDirectories());
        this.elements.addAll(pfs.getFiles());
    }

    /**
     * This class converts objects of type String to and from a string.
     * It is a private class and cannot be instantiated outside this class.
     *
     * @author Name
     * @version
     */
    private class TypeConverter extends StringConverter<String> {
        /**
         * Converts the object of type String to a string.
         *
         * @param object The object to be converted.
         * @return The converted string.
         */
        @Override
        public String toString(String object) {
            return object == null ? "" : object;
        }

        /**
         * Converts the string back to an object of type String.
         *
         * @param string The string to be converted.
         * @return The converted object.
         */
        @Override
        public String fromString(String string) {
            return string;
        }
    }

    /**
     * This class converts objects of type Directory to and from a string.
     * It is a private class and cannot be instantiated outside this class.
     *
     * @author Name
     * @version
     */
    private class dirConverter extends StringConverter<Directory> {
        /**
         * Converts the object of type Directory to a string.
         *
         * @param dir The object to be converted.
         * @return The converted string.
         */
        @Override
        public String toString(Directory dir) {
            if (dir == null)
                return "";
            return dir.toString();
        }

        /**
         * Converts the string back to an object of type Directory.
         * This method always returns null as it is not implemented.
         *
         * @param string The string to be converted.
         * @return The converted object, which is always null.
         */
        @Override
        public Directory fromString(String string) {
            return null;
        }
    }

    // Método para construir a árvore a partir do nó root
    private void buildTreeView(TreeItem<String> rootItem, Position<FileSystemElement> position) {
        for (Position<FileSystemElement> child : pfs.children(position)) {
            TreeItem<String> childItem = new TreeItem<>(child.element().toString());
            rootItem.getChildren().add(childItem);
            buildTreeView(childItem, child);
        }
    }
}