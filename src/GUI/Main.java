package GUI;

import Components.DocumentsLibrary;
import Components.SearchBar;
import FileReader.DocumentManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    private static int posy = 10;
    private Pane root;
    private Pane documentsScroll;
    public DocumentManager documentManager = new DocumentManager();


    @Override
    public void start(Stage primaryStage) throws Exception{
        root = new Pane();




        //Se crea la barra de búsqueda de texto
        SearchBar searchBar = new SearchBar(root, documentManager);
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

        DocumentsLibrary documentsLibrary = new DocumentsLibrary(documentsScroll, pane, documentManager);


        //Stage principal
        primaryStage.setTitle("Text finder");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 900, 900));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

