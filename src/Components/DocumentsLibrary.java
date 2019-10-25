package Components;

import FileReader.DocumentManager;
import LinkedArrayList.LinkedArrayList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;

/**
 */

public class DocumentsLibrary {
    private static LinkedArrayList<File> documents = DocumentManager.documents;
    private static LinkedArrayList<CheckBox> checkButtonList;
    private static DocumentManager documentManager;
    private static Pane libraryPane;
    private static Pane paneButtons;
    private static int posy = 20;
    public static boolean deleting;


    public DocumentsLibrary(Pane libraryPane, Pane paneButtons, DocumentManager documentManager) {
        this.libraryPane = libraryPane;
        this.paneButtons = paneButtons;
        this.documentManager = documentManager;

        Button update = new Button("Update");
        update.setLayoutX(20);
        update.setLayoutY(850);
        update.setOnMouseClicked(updateEvent);
        paneButtons.getChildren().add(update);

        Button delete = new Button("Delete");
        delete.setLayoutX(90);
        delete.setLayoutY(850);
        delete.setOnMouseClicked(deleteFile);
        paneButtons.getChildren().add(delete);

        Button addNewFile = new Button("Add File");
        addNewFile.setLayoutX(275);
        addNewFile.setLayoutY(850);
        addNewFile.setOnMouseClicked(newFile);
        paneButtons.getChildren().addAll(addNewFile);


        Button addNewFolder = new Button("Add Folder");
        addNewFolder.setLayoutX(180);
        addNewFolder.setLayoutY(850);
        addNewFolder.setOnMouseClicked(newFolder);
        paneButtons.getChildren().addAll(addNewFolder);

        checkButtonList = new LinkedArrayList<CheckBox>();

    }


    public static void addNewFileToLibrary(File file) {
        CheckBox button = new CheckBox();
        button.setLayoutX(20);
        button.setLayoutY(posy);
        button.setText(file.getName());
        button.setTextFill(Color.rgb(255, 255, 255));
        button.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        button.setUserData(file);
        checkButtonList.addLast(button);
        libraryPane.getChildren().add(button);
        posy += 50;
    }


    public void updateDocuments() {
        for (int i = 0; i < checkButtonList.getSize(); i++) {
            if (checkButtonList.getElement(i).isSelected()) {
                File file = (File) checkButtonList.getElement(i).getUserData();
                documentManager.deleteFile(file);
                documentManager.updateDocuments(file);
            }
        }
    }


    private void deleteFiles() {
        deleting = true;
        for (int i = 0; i < checkButtonList.getSize(); i++) {
            CheckBox fileButton = checkButtonList.getElement(i);
            if (fileButton.isSelected()) {
                File file = (File) fileButton.getUserData();
                System.out.println("Se eliminan");
                documentManager.deleteFile(file);
                libraryPane.getChildren().remove(fileButton);
                checkButtonList.delete(i);
                System.out.println("Sale");
            }
        }
        deleting= false;
        updatePositions();
    }


    private void updatePositions(){
        posy =20;
        for(int i=0; i< checkButtonList.getSize(); i++){
            System.out.println("Se actualizan posiciones");
            checkButtonList.getElement(i).setLayoutY(posy);
            posy +=50;
        }
    }


    private EventHandler<MouseEvent> updateEvent = event -> updateDocuments();


    private EventHandler<MouseEvent> newFolder = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setInitialDirectory(new File("/home"));
            File[] seletedFiles = directoryChooser.showDialog(null).listFiles();
            if(seletedFiles != null) {
                documentManager.documentReader(seletedFiles);
            }

        }
    };

    private EventHandler<MouseEvent> newFile = new EventHandler<>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("/home"));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF files", "*.pdf"),
                    new FileChooser.ExtensionFilter("TXT files", "*.txt"), new FileChooser.ExtensionFilter("DOCX files", "*.docx"));
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                documentManager.documentReader2(selectedFile);
            }

        }
    };


    private EventHandler<MouseEvent> deleteFile = event -> deleteFiles();


}
