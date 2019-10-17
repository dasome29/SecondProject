package Components;

import FileReader.DocumentReader;
import LinkedArrayList.LinkedArrayList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import jdk.jshell.EvalException;

import java.io.File;
import java.nio.file.Files;

public class DocumentsLibrary {
    private static LinkedArrayList<File> documents = DocumentReader.documents;
    private static  LinkedArrayList<CheckBox> checkButtonList;
    private static DocumentReader documentReader;
    private static Pane libraryPane;
    private static Pane paneButtons;
    private static int posy = 20;


    public DocumentsLibrary(Pane libraryPane, Pane paneButtons, DocumentReader documentReader){
        this.libraryPane = libraryPane;
        this.paneButtons = paneButtons;
        this.documentReader = documentReader;

        Button button = new Button("Update");
        button.setLayoutY(10);
        button.setLayoutY(850);
        button.setOnMouseClicked(updateEvent);
        paneButtons.getChildren().add(button);

        Button addNewFile = new Button("Add File");
        addNewFile.setLayoutX(275);
        addNewFile.setLayoutY(850);
        addNewFile.setOnMouseClicked(newFile);
        paneButtons.getChildren().addAll(addNewFile);


        Button addNewFolder = new Button("Add Folder");
        addNewFolder.setLayoutX(150);
        addNewFolder.setLayoutY(850);
        addNewFolder.setOnMouseClicked(newFolder);
        paneButtons.getChildren().addAll(addNewFolder);

        checkButtonList = new LinkedArrayList<CheckBox>();



    }


    public static void addNewFileToLibrary(File file){
        CheckBox button = new CheckBox();
        button.setLayoutX(20);
        button.setLayoutY(posy);
        button.setText(file.getName());
        button.setTextFill(Color.rgb(255,255,255));
        button.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        button.setUserData(file);
        checkButtonList.addLast(button);
        libraryPane.getChildren().add(button);
        posy += 50;
    }


    public static void updateDocuments(){
        for(int i=0; i< documents.getSize(); i++){
            documentReader.updateDocuments(documents.getElement(i));
            System.out.println("ACTUALIZANDO");
            System.out.println();

        }
    }


    private EventHandler<MouseEvent> updateEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            updateDocuments();
        }
    };


    EventHandler<MouseEvent> newFolder = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setInitialDirectory(new File("/home"));
            File[] seletedFiles = directoryChooser.showDialog(null).listFiles();
            if(seletedFiles != null) {
                documentReader.documentReader(seletedFiles);
            }

        }
    };

    EventHandler<MouseEvent> newFile = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("/home"));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF files", "*.pdf"),
                    new FileChooser.ExtensionFilter("TXT files", "*.txt"), new FileChooser.ExtensionFilter("DOCX files", "*.docx"));
            File selectedFile = fileChooser.showOpenDialog(null);

            if(selectedFile != null) {
                documentReader.documentReader2(selectedFile);
            }

        }
    };


    
}
