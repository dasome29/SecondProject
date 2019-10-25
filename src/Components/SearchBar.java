package Components;

import FileReader.DocumentFormat;
import FileReader.DocumentManager;
import LinkedArrayList.LinkedArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.File;

public class SearchBar{
    private Pane root;
    private Pane searchBar = new Pane();
    private DocumentManager documentManager;
    private Button searchButton;
    private TextField textField;
    private ToggleGroup searchBy;
    private RadioButton searchByWord;
    private RadioButton searchByPhrase;
    public static LinkedArrayList<File> listOfWords;
    public ScrollPane scrollPaneResults;
    private static Pane searchingResultsPane;
    private SortDocumentsBar sortDocumentsBar;
    public static LinkedArrayList<Results> resultsList = new LinkedArrayList<Results>();


    public SearchBar(Pane root, DocumentManager documentManager){
        this.root = root;
        this.documentManager = documentManager;

    }

    public void setSearchBar() {

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

        searchByPhrase = new RadioButton("By Phase");
        searchByPhrase.setToggleGroup(searchBy);
        searchByPhrase.setLayoutX(350);
        searchByPhrase.setLayoutY(50);

        searchBar.getChildren().addAll(searchByWord, searchByPhrase);

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
                    resetResults();
                    String string = textField.getText().trim();
                    System.out.println("EL arbol contiene a " + string + "  " + DocumentManager.words.contains(string));
                    listOfWords = documentManager.words.get(string).getRecurrences();
                    System.out.println(" Contiene " + string + " " + documentManager.words.contains(string));
                    addDocumentsToScreen(string);
                }
                if(searchByPhrase.isSelected()){
                    resetResults();
                    String[] string = textField.getText().split(" ");
                    listOfWords = documentManager.words.get(string[0]).getRecurrences();
                    searchPhrase(string);
                }
            } catch (NullPointerException e) {
            }
        }
    };


    private void searchPhrase(String[] phase){
        int posy = 10;
        for(int i=0; i < listOfWords.getSize(); i++){
            File file = listOfWords.getElement(i);
            LinkedArrayList<String[]> text = DocumentFormat.verifyFormat(file);
            assert text != null;
            for (int j = 0; j < text.getSize(); j++) {
                if (searchPhrase(text.getElement(j), phase)) {
                    Results results = new Results(searchingResultsPane, file, phase, posy);
                    resultsList.addLast(results);
                    posy += 280;
                }
            }

        }
    }
    private boolean searchPhrase(String[] text, String[] phase) {
        int index = 0;
        for (int i = 0; i < text.length; i++) {
            if(index==phase.length){return true;}
            if(text[i].equals(phase[index])){
                index++;
                for(int j=i+1; j<text.length; j++){
                    String word = text[j].replace(",", " ").replace(".", " ").replace(";", " ").replace("(", " ").replace(")", " ").replace("\n", " ").trim();
                    System.out.println("ENTRA");
                    if(word.equals("")){continue;}
                    if(index==phase.length){
                        System.out.println("CONTIENE LA FRASE");
                        return true;
                    }

                    if(word.equals(phase[index])){
                        System.out.println(j + " " + text[j] + " es igual a " + phase[index] +" " + index);
                        System.out.println();
                        index++;
                    }else{
                        System.out.println(text[j]+ " false"+ phase[index]);
                        index=0;
                        break;
                    }

                }
            }
        }
        System.out.println("NO CONTIENE LA FRASE");
        return false;
    }


    private static void addDocumentsToScreen(String word) {
        int posy = 10;
        searchingResultsPane.getChildren().clear();
        for(int i=0; i< listOfWords.getSize(); i++){
            File file = listOfWords.getElement(i);
            Results results=  new Results(searchingResultsPane, file, posy );
            resultsList.addLast(results);
            posy += 280;
            if(posy > searchingResultsPane.getHeight()){
                searchingResultsPane.setPrefHeight(posy +150);
            }
        }

    }

    public static void updatePositions(){
        int posy = 10;
        for(int i=0; i < resultsList.getSize(); i++){
            Results results = resultsList.getElement(i);
            System.out.println(results.file.getName() + "   " + posy);
            results.pane.setLayoutY(posy);
            if(posy > searchingResultsPane.getHeight()){
                searchingResultsPane.setPrefHeight(posy +150);
            }
            posy += 280;

        }
    }

    private void resetResults(){
        resultsList.reset();
        searchingResultsPane.getChildren().clear();
    }








}
