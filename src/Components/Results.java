package Components;

import FileReader.DocumentFormat;
import FileReader.DocumentManager;
import LinkedArrayList.LinkedArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public class Results {
    public Pane pane;
    public File file;
    private Pane searchingResultsPane;
    private TextArea textArea;
    private String word;
    private String[] phase;
    private int posy;
    private int start, end;
    public int Filesize;
    public TextFlow textFlow;
    private LinkedArrayList<String[]> text;
    private LinkedArrayList<Integer> index;


    public Results(Pane searchingResultsPane, File file, String[] phase, int posy) {
        this.searchingResultsPane = searchingResultsPane;
        this.file = file;
        this.posy = posy;
        this.phase = phase;
        this.Filesize = (int) file.length();
        setResultPane();
        addTextPhase();
    }


    public Results(Pane searchingResultsPane, File file, String word, int posy) {
        this.searchingResultsPane = searchingResultsPane;
        this.file = file;
        this.posy = posy;
        this.word = word;
        this.Filesize = (int) file.length();

        setResultPane();
        //addTextWord(word);


    }


    public void setResultPane() {

        pane = new Pane();
        pane.setPrefSize(520, 250);
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(144, 40, 40), CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setLayoutX(10);
        pane.setLayoutY(posy);

        Label label = new Label(file.getName());
        label.setLayoutX(3);
        label.setLayoutY(5);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        label.setTextFill(Color.rgb(255, 255, 255));
        pane.getChildren().add(label);


        Button button = new Button("Abrir");
        button.setLayoutX(450);
        button.setLayoutY(2);
        button.setOnMouseClicked(openDocument);
        button.setUserData(file);
        pane.getChildren().addAll(button);


        textFlow = new TextFlow();
        textFlow.setPrefSize(520, 220);
        //textFlow.setMaxSize(520,520);
        textFlow.setLayoutX(30);
        //pane.getChildren().add(textFlow);

        ScrollPane scrollPane = new ScrollPane(textFlow);
        scrollPane.setLayoutY(30);
        scrollPane.setMaxSize(520, 220);
        pane.getChildren().add(scrollPane);


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



    private void addTextPhase() {
        text = DocumentFormat.verifyFormat(file);
        index = DocumentManager.words.get(phase[0]).getRecurrences().getPositions(file);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < index.getSize(); i++) {
            if (searchPhrase(text.getElement(index.getElement(i)), phase)){
                int Index = index.getElement(i);
                for(int x=-1; x<=1; x++){
                    String[] line = text.getElement(Index+x);
                    if(Index+x == Index){
                        setHightLight(line, phase);
                        if(searchPhrase(text.getElement(i+1), phase)){
                            setHightLight(text.getElement(i+1), phase);
                            i++;
                        }
                    }
                    else{
                        for(int j=0; j<line.length; j++){
                            content.append(line[j] + " ");

                        }
                        content.append("\n");
                        textFlow.getChildren().add(new Text(content.toString()));
                        content.delete(0, content.length());
                    }
                }
                textFlow.getChildren().add(new Text("\n\n\n"));
            }
        }
    }

    private void setHightLight(String[] line, String[] phase) {
        StringBuilder textBeforeOrAfterWord = new StringBuilder();
        StringBuilder phaseString = new StringBuilder();

        for(int i=0; i<start; i++){
            textBeforeOrAfterWord.append(line[i]+ " ");
        }
        textBeforeOrAfterWord.append(" ");
        textFlow.getChildren().add(new Text(textBeforeOrAfterWord.toString()));
        textBeforeOrAfterWord.delete(0, textBeforeOrAfterWord.length());
        for(int i=start; i<=end; i++ ){
            phaseString.append(line[i]+ " ");
        }
        Text text = new Text(phaseString.toString());
        text.setFill(Color.web("F3720D"));
        text.setFont(Font.font("", FontWeight.BOLD, 14));
        textFlow.getChildren().add(text);
        for(int i=end+1; i<line.length; i++){
            textBeforeOrAfterWord.append(line[i]+ " ");
        }
        textBeforeOrAfterWord.append(" ");
        textFlow.getChildren().add(new Text(textBeforeOrAfterWord.toString()));
        textBeforeOrAfterWord.delete(0, textBeforeOrAfterWord.length());
}


    private boolean searchPhrase(String[] text, String[] phase) {
        int index = 0;
        for (int i = 0; i < text.length; i++) {
            if(index==phase.length){return true;}
            if(text[i].equals(phase[index])){
                start=i;
                index++;
                for(int j=i+1; j<text.length; j++){
                    String word = text[j].replace(",", " ").replace(".", " ").replace(";", " ").replace("(", " ").replace(")", " ").replace("\n", " ").trim();
                    if(word.equals("")){continue;}
                    if(index==phase.length){
                        end=j-1;
                        return true;
                    }
                    if(word.equals(phase[index])){
                        end=j;
                        index++;
                    }else{
                        index=0;
                        break;
                    }

                }
            }
        }
        System.out.println("NO CONTIENE LA FRASE");
        return false;
    }



}
