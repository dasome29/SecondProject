package Components;

import FileReader.DocumentFormat;
import FileReader.DocumentReader;
import LinkedArrayList.LinkedArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Results {
    public Pane pane;
    public File file;
    private Pane searchingResultsPane;
    private TextArea textArea;
    private String word;
    private String[] phase;
    private int posy;
    public int Filesize;
    private LinkedArrayList<String[]> text;
    private LinkedArrayList<int[]> index;


    public Results(Pane searchingResultsPane, File file, String[] phase, int posy){
        this.searchingResultsPane = searchingResultsPane;
        this.file = file;
        this.posy = posy;
        this.phase = phase;
        this.Filesize = (int)file.length();
        setResultPane();
        addTextPhase();
    }


    public Results(Pane searchingResultsPane, File file, String word, int posy){
        this.searchingResultsPane = searchingResultsPane;
        this.file = file;
        this.posy = posy;
        this.word = word;
        this.Filesize = (int)file.length();
        setResultPane();
        addTextWord();


    }


    public void setResultPane(){

        pane = new Pane();
        pane.setPrefSize(520, 250);
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(144,40,40), CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setLayoutX(10);
        pane.setLayoutY(posy);

        Label label = new Label(file.getName());
        label.setLayoutX(3);
        label.setLayoutY(5);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        label.setTextFill(Color.rgb(255,255,255));
        pane.getChildren().add(label);


        Button button = new Button("Abrir");
        button.setLayoutX(450);
        button.setLayoutY(2);
        button.setOnMouseClicked(openDocument);
        button.setUserData(file);
        pane.getChildren().addAll(button);



        textArea = new TextArea(file.getName());
        textArea.setPrefSize(520, 220);
        textArea.setEditable(false);
        textArea.setLayoutY(30);
        pane.getChildren().add(textArea);


        searchingResultsPane.getChildren().add(pane);
    }

    private EventHandler<MouseEvent> openDocument = event -> new Thread(() -> {
        try {
            System.out.println(event.getSource());
            File file = new File(((File) ((Button) event.getSource()).getUserData()).getPath());
            System.out.println("Abre documento");
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
        }
    }).start();


    /**
     * Método utilizado para agregar el texto en el TextArea en caso de que se busque una palabra individual
     */
    private void addTextWord(){
        text = DocumentFormat.verifyFormat(file);
        System.out.println("La palabra es " + word);
        index = DocumentReader.words.get(word).getRecurrences().getPositions(file);
        String content = "";
        for(int i=0; i< index.getSize(); i++) {
            for(int x=-1; x<=1; x++ ) {
                    String[] line = text.getElement(index.getElement((i))[0] + x);
                    for (int j = 0; j < line.length; j++) {
                        if (line[j].equals(word)) {
                        }
                        content += line[j] + " ";
                    }
                    content += "\n";
                }
                content += "\n\n";
            }
        textArea.setText(content);
    }


    private void addTextPhase() {
        text = DocumentFormat.verifyFormat(file);
        index = DocumentReader.words.get(phase[0]).getRecurrences().getPositions(file);
        StringBuilder content = new StringBuilder();
        for(int i=0; i<index.getSize(); i++){
            if(searchPhrase(text.getElement(index.getElement(i)[0]), phase)){
                for(int x=-1; x<=1; x++){
                    String[] lineToAdd = text.getElement(index.getElement(i)[0] + x);
                    for(int j=0; j< lineToAdd.length; j++){
                        content.append(lineToAdd[j]);
                        content.append(" ");
                    }
                    content.append("\n");
                }
                content.append("\n\n");
                System.out.println(content);

            }
        }
        textArea.setText(content.toString());

    }


    private boolean searchPhrase(String[] text, String[] phase) {
        int index = 0;
        for (int i = 0; i < text.length; i++) {
            if (text[i].equals(phase[index])) {
                System.out.println(index + "  " + (phase.length - 1));
                if (index == phase.length - 1) {
                    System.out.println("Es verdad");
                    return true;
                }
                index++;
                System.out.println("No son iguales");
            }
        }
        return false;
    }




}
