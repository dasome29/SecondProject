package GUI;

import BinaryTree.Node;
import FileReader.DocumentFormat;
import FileReader.DocumentReader;
import FileReader.SortDocumentsBar;
import LinkedArrayList.LinkedArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
    private ToggleGroup searchBy;
    private RadioButton searchByWord;
    private RadioButton searchByPhase;
    private String word;
    private String phase;
    public static LinkedArrayList<File> listOfWords;
    public ScrollPane scrollPaneResults;
    private static Pane searchingResultsPane;
    private SortDocumentsBar sortDocumentsBar;
    public static LinkedArrayList<Results> resultsList = new LinkedArrayList<Results>();


    SearchBar(Pane root, DocumentReader documentReader){
        this.root = root;
        this.documentReader = documentReader;



    }

    void setSearchBar() {

        sortDocumentsBar = new SortDocumentsBar(root);


        searchingResultsPane = new Pane();
        searchingResultsPane.setLayoutX(0);
        searchingResultsPane.setLayoutY(0);
        searchingResultsPane.setPrefSize(550,800);
        searchingResultsPane.setBackground(new Background(new BackgroundFill(Color.rgb(147,147,147), CornerRadii.EMPTY, Insets.EMPTY)));


        scrollPaneResults = new ScrollPane();
        scrollPaneResults.setLayoutX(352);
        scrollPaneResults.setLayoutY(135);
        scrollPaneResults.setPrefSize(550, 800);
        scrollPaneResults.setBackground(new Background(new BackgroundFill(Color.rgb(147,147,147), CornerRadii.EMPTY, Insets.EMPTY)));
        scrollPaneResults.setContent(searchingResultsPane);
        root.getChildren().add(scrollPaneResults);


        searchBar = new Pane();
        searchBar.setPrefSize(550, 100);
        searchBar.setLayoutX(352);
        searchBar.setLayoutY(0);
        searchBar.setBackground(new Background(new BackgroundFill(Color.web("#e7ebda"), CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().add(searchBar);


        searchBy = new ToggleGroup();

        searchByWord = new RadioButton("By word");
        searchByWord.setLayoutX(350);
        searchByWord.setToggleGroup(searchBy);
        searchByWord.setLayoutY(25);

        searchByPhase = new RadioButton("By Phase");
        searchByPhase.setToggleGroup(searchBy);
        searchByPhase.setLayoutX(350);
        searchByPhase.setLayoutY(50);

        searchBar.getChildren().addAll(searchByWord, searchByPhase);

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
                if(searchByWord.isSelected()) {
                    resultsList.reset();
                    String string = textField.getText().trim();
                    listOfWords = documentReader.words.get(string).getRecurrences();
                    System.out.println(" Contiene " + string + " " + documentReader.words.contains(string));
                    addDocumentsToScreen(string);
                }
                if(searchByPhase.isSelected()){
                    resultsList.reset();
                    String[] string = textField.getText().split(" ");
                    listOfWords = documentReader.words.get(string[0]).getRecurrences();
                    searchPhase(string);
                }
            }catch (NullPointerException e){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Content", ButtonType.OK);
                alert.setHeaderText("No existe ninguna palabra o frase para buscar");
                alert.setContentText("Por favor introduzca una palabra o frase para realizar la búsqueda");
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.showAndWait();
            }
        }
    };


    private void searchPhase(String[] phase){
        int posy = 10;
        for(int i=0; i < listOfWords.getSize(); i++){
            File file = listOfWords.getElement(i);
            String[] text = DocumentFormat.verifyFormat(file);
            if(searchPhase(text, phase)){
                System.out.println("La frase se encuentra en alguno de los documentos");
                Results results=  new Results(searchingResultsPane, file, word, posy);
                resultsList.addLast(results);
                posy +=100;
            }

        }
    }
    private boolean searchPhase(String[] text, String[] phase){
        int index = 0;
        for(int i=0; i< text.length; i++){
            if(text[i].equals(phase[index]) ){
                if(index == phase.length -1){
                    return true;
                }else {
                    index++;
                }
            }
        }
        return false;
    }


    public static void addDocumentsToScreen(String word) {
        int posy = 10;
        for(int i=0; i< listOfWords.getSize(); i++){
            File file = listOfWords.getElement(i);
            Results results=  new Results(searchingResultsPane, file, word, posy );
            resultsList.addLast(results);
            posy += 280;
        }

    }

    public static void updatePositions(){
        int posy = 10;
        for(int i=0; i <= resultsList.getSize(); i++){
            Results results = resultsList.getElement(i);
            results.pane.setLayoutY(posy);
            posy += 28;
            if(posy > searchingResultsPane.getHeight()){
                searchingResultsPane.setPrefHeight(posy +150);
            }

        }
    }






}
