package BinaryTree;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.List;
import java.util.Random;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Pane root = new Pane();

        TextFlow textFlow = new TextFlow();
        textFlow.setMaxSize(200, 200);
        //textFlow.setMaxSize(200,200);
        textFlow.setBackground(new Background(new BackgroundFill(Color.rgb(255, 154, 75), CornerRadii.EMPTY, Insets.EMPTY)));
        //root.getChildren().add(textFlow);

        ScrollPane scrollPane = new ScrollPane(textFlow);
        scrollPane.setMaxSize(500, 200);
        root.getChildren().add(scrollPane);
        setHightLight(textFlow, "La casa es muy pequeña, pero deberiamos tener en consideracion que las personas son muy pequeñas tambien", new String[]{"pero", "deberiamos", "tener"});

        String[] text = "La casa es muy pequeña, pero deberiamos en consideracion que las son muy pequeñas tambien, deberiamos tener en cuenta".split(" ");
        searchPhrase(text, new String[]{"pero", "deberiamos","tener"});


        primaryStage.setScene(new Scene(root, 700, 700));
        primaryStage.show();

    }

    private void setHightLight(TextFlow textFlow, String string, String word) {
        String[] line = string.split(" ");
        StringBuilder textBeforeOrAfterWord = new StringBuilder();
        for (String s : line) {
            if (s.equals(word)) {
                Text text = new Text(word);
                text.setFill(Color.rgb(245, 125, 11));
                textFlow.getChildren().addAll(new Text(textBeforeOrAfterWord + " "), text);
                textBeforeOrAfterWord.delete(0, textBeforeOrAfterWord.length() - 1);
            } else {
                textBeforeOrAfterWord.append(s + " ");
            }
        }
        textFlow.getChildren().addAll(new Text(" " + textBeforeOrAfterWord));

    }

    private void setHightLight(TextFlow textFlow, String string, String[] phase){
        String[] line = string.split(" ");
        StringBuilder textBeforeOrAfterWord = new StringBuilder();
        StringBuilder phaseString = new StringBuilder();
        boolean flag = true;
        int i= 0;
        for(String s : line){
            s = s.replace(",", " ").replace(":", " ").replace(";", " ").replace("."," ").trim();
            if(i==phase.length -1 && flag){
                Text text = new Text(phaseString.toString() +" ");
                text.setFill(Color.web("F3720D"));
                text.setFont(Font.font("", FontWeight.BOLD, 14));
                textFlow.getChildren().addAll(new Text(textBeforeOrAfterWord + " "), text );
                textBeforeOrAfterWord.delete(0, textBeforeOrAfterWord.length());
                flag = false;
            }else if(s.equals(phase[i]) && flag){
                phaseString.append(phase[i] +" ");
                i++;

            }
            else{
                phaseString.delete(0, phaseString.length());
                textBeforeOrAfterWord.append(s + " ");
            }

        }

        textFlow.getChildren().addAll(new Text(" " + textBeforeOrAfterWord));
    }

    private boolean searchPhrase(String[] text, String[] phase) {
        int index = 0;
        for (int i = 0; i < text.length; i++) {
            if(text[i].equals(phase[index])){
                index++;
                for(int j=i+1; j<text.length; j++){
                    if(index==phase.length){
                        return true;
                    }
                    if(text[j].equals(phase[index])){
                        System.out.println(j + " " + text[j] + " es igual a " + phase[index] +" " + index);
                        index++;
                    }else{
                        index=0;
                        break;
                    }

                }
            }
        }
        return false;
    }


}
