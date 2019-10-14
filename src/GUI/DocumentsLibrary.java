package GUI;

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
            //documentReader.updateDocuments(documents.getElement(i));
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


    
}
