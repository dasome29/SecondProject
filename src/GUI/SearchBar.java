package GUI;

import FileReader.DocumentReader;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

class SearchBar{
    private Pane root;
    private Pane searchBar = new Pane();
    private DocumentReader documentReader;
    private Button searchButton;
    private TextField textField;


    SearchBar(Pane root, DocumentReader documentReader){
        this.root = root;
        this.documentReader = documentReader;

    }

    void setSearchBar() {

        searchBar = new Pane();
        searchBar.setPrefSize(650, 100);
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
            String string = textField.getText().trim();
            System.out.println(documentReader.words.get("nombre"));
            System.out.println(" Contiene " +   string+ " " +   documentReader.words.contains(string));

        }
    };


}
