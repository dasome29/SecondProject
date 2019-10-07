package GUI;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import FileReader.DocumentReader;


import java.io.*;
import java.time.temporal.Temporal;

public class Main extends Application {
    private static int posy = 10;
    private Pane root;
    private Pane documentsScroll;
    private DocumentReader documentReader = new DocumentReader();
    private TextField textField;


    @Override
    public void start(Stage primaryStage) throws Exception{
        root = new Pane();


        Pane pane = new Pane();
        pane.setPrefSize(350, 900);
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(140,40,40), CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().add(pane);


        documentsScroll = new Pane();
        documentsScroll.setPrefSize(350, 830);
        documentsScroll.setBackground(new Background(new BackgroundFill(Color.rgb(140,40,40), CornerRadii.EMPTY, Insets.EMPTY)));
        ScrollPane scrollPane = new ScrollPane(documentsScroll);
        pane.getChildren().addAll(scrollPane);


        Button addNewFile = new Button("Add File");
        addNewFile.setLayoutX(275);
        addNewFile.setLayoutY(850);
        addNewFile.setOnMouseClicked(NewFile);
        pane.getChildren().addAll(addNewFile);


        Button addNewFolder = new Button("Add Folder");
        addNewFolder.setLayoutX(150);
        addNewFolder.setLayoutY(850);
        addNewFolder.setOnMouseClicked(newFolder);
        pane.getChildren().addAll(addNewFolder);

        Pane searchBar = new Pane();
        searchBar.setPrefSize(650, 100);
        searchBar.setLayoutX(352);
        searchBar.setLayoutY(0);
        searchBar.setBackground(new Background(new BackgroundFill(Color.web("#e7ebda"), CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().add(searchBar);


        Button searchButton = new Button("Search");
        searchButton.setLayoutX(250);
        searchButton.setLayoutY(30);
        searchButton.setOnMouseClicked(searchWord);
        searchBar.getChildren().add(searchButton);


        textField = new TextField();
        textField.setPrefSize(200, 30);
        textField.setLayoutX(20);
        textField.setLayoutY(30);
        searchBar.getChildren().add(textField);




        primaryStage.setTitle("Text finder");
        primaryStage.setScene(new Scene(root, 1000, 900));
        primaryStage.show();
    }


    EventHandler<MouseEvent> newFolder = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setInitialDirectory(new File("/home"));
            File[] seletedFiles = directoryChooser.showDialog(null).listFiles();
            documentReader.documentReader(seletedFiles);




        }
    };


    EventHandler<MouseEvent> NewFile = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("/home"));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF files", "*.pdf"),
                    new FileChooser.ExtensionFilter("TXT files", "*.txt"), new FileChooser.ExtensionFilter("DOCX files", "*.docx"));
            File file = fileChooser.showOpenDialog(null);
            documentReader.documentReader(file);

        }
    };


    EventHandler<MouseEvent> searchWord = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            String string = textField.getText().trim();
            System.out.println(" Contiene " + string + " " +   documentReader.files.contains(string));



        }
    };

    private void addDocument(File file){
        Label label = new Label();
        label.setText("\n" + file.getName() + "\n");
        label.setBackground(new Background(new BackgroundFill(Color.rgb(140,80,80),CornerRadii.EMPTY, Insets.EMPTY)));
        label.setLayoutX(10);
        label.setLayoutY(posy);
        documentsScroll.getChildren().addAll(label);
        posy += 50;
    }


    public static void main(String[] args) {
        launch(args);
    }
}

