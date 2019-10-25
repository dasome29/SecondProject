package BinaryTree;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {


        Pane root = new Pane();


        TextArea textArea = new TextArea("Hola como estan ustedes");
        textArea.setLayoutX(75);
        textArea.setLayoutY(75);
        textArea.setEditable(false);
        root.getChildren().add(textArea);


        Scene scene = new Scene(root,500,500);
        primaryStage.setScene(scene);
        primaryStage.show();

    }



    public static void main(String[] args){
        launch(args);
    }
}
