package GUI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import FileReader.DocumentReader;


import java.io.*;

public class Main extends Application {
    private static int posy = 10;
    private Pane root;
    private Pane documentsScroll;
    private DocumentReader documentReader = new DocumentReader();
    private TextField textField;


    @Override
    public void start(Stage primaryStage) throws Exception{
        root = new Pane();



        //Se crea la barra de búsqueda de texto
        SearchBar searchBar = new SearchBar(root, documentReader);
        searchBar.setSearchBar();



        // No se porque lo puse
        Pane pane = new Pane();
        pane.setPrefSize(350, 900);
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(140,40,40), CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().add(pane);


        //--------------------------------------------------------------------------------------------------------------//
        // Se crea los componentes graficos de la biblioteca de documentos//
        //-------------------------------------------------------------------------------------------------------------//
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





        //Stage principal
        primaryStage.setTitle("Text finder");
        primaryStage.setScene(new Scene(root, 900, 900));
        primaryStage.show();
    }


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


    EventHandler<MouseEvent> NewFile = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("/home"));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF files", "*.pdf"),
                    new FileChooser.ExtensionFilter("TXT files", "*.txt"), new FileChooser.ExtensionFilter("DOCX files", "*.docx"));
            File selectedFile = fileChooser.showOpenDialog(null);

            if(selectedFile != null) {
                documentReader.documentReader(selectedFile);
            }

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

