package GUI;

import BinaryTree.Node;
import FileReader.DocumentReader;
import FileReader.SortDocumentsBar;
import LinkedArrayList.LinkedArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SearchBar{
    private Pane root;
    private Pane searchBar = new Pane();
    private DocumentReader documentReader;
    private Button searchButton;
    private TextField textField;
    public static LinkedArrayList<File> listOfWords;
    private static Pane searchingResultsPane;
    private SortDocumentsBar sortDocumentsBar;


    SearchBar(Pane root, DocumentReader documentReader){
        this.root = root;
        this.documentReader = documentReader;



    }

    void setSearchBar() {

        sortDocumentsBar = new SortDocumentsBar(root);


        searchingResultsPane = new Pane();
        searchingResultsPane.setLayoutX(352);
        searchingResultsPane.setLayoutY(135);
        searchingResultsPane.setPrefSize(550,800);
        searchingResultsPane.setBackground(new Background(new BackgroundFill(Color.rgb(147,147,147), CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().add(searchingResultsPane);

        searchBar = new Pane();
        searchBar.setPrefSize(550, 100);
        searchBar.setLayoutX(352);
        searchBar.setLayoutY(0);
        searchBar.setBackground(new Background(new BackgroundFill(Color.web("#e7ebda"), CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().add(searchBar);

        searchButton = new Button("Search");
        searchButton.setLayoutX(250);
        searchButton.setLayoutY(30);
        searchButton.setOnMouseClicked(searchWord);
        searchBar.getChildren().add(searchButton);


        textField = new TextField();
        textField.setPrefSize(200, 30);
        textField.setLayoutX(20);
        textField.setLayoutY(30);
        searchBar.getChildren().add(textField);
    }



    private EventHandler<MouseEvent> searchWord = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            try {
                String string = textField.getText().trim();
                listOfWords = documentReader.words.get(string).getRecurrences();
                System.out.println(" Contiene " + string + " " + documentReader.words.contains(string));
                addDocumentsToScreen();
            }catch (NullPointerException e){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Content", ButtonType.OK);
                alert.setHeaderText("No existe ninguna palabra o frase para buscar");
                alert.setContentText("Por favor introduzca una palabra o frase para realizar la búsqueda");
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.showAndWait();
            }
        }
    };



    private static EventHandler<MouseEvent> openDocument = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            new Thread(() -> {
                try {
                    File file = new File(((File) ((Label) event.getSource()).getUserData()).getPath());
                    System.out.println("Abre documento");
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                }
            }).start();
        }
    };


    public static void addDocumentsToScreen(){
        int posy = 10;
        searchingResultsPane.getChildren().clear();
        for(int i=0; i<listOfWords.getSize(); i++){
            addDocument(listOfWords.getElement(i), posy);
            posy +=100;
        }
    }


    private static void addDocument(File file, int posy){
        javafx.scene.control.Label label = new Label();
        label.setPrefWidth(400);
        label.setPrefHeight(80);
        label.setUserData(file);
        label.setText("\n" + file.getName() + "\n" + file.length() +  " Mb" + "\n");
        label.setFont(Font.font("Arial Black", FontWeight.BOLD, 15));
        label.setAlignment(Pos.TOP_CENTER);
        label.setOnMouseClicked(openDocument);
        label.setBackground(new Background(new BackgroundFill(Color.rgb(140,80,80), CornerRadii.EMPTY, Insets.EMPTY)));
        label.setLayoutX(50);
        label.setLayoutY(posy);
        searchingResultsPane.getChildren().addAll(label);
    }




}
