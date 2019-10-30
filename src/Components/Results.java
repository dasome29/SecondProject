package Components;

import FileReader.DocumentParser;
import FileReader.DocumentManager;
import LinkedArrayList.LinkedArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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

/**
 * Clase encarga de mostrar los resultados en pantalla
 */
public class Results {
    public Pane pane;
    public File file;
    private Pane searchingResultsPane;
    private String word;
    private String[] phase;
    private boolean searchingWord = false, searchingPhase = false;
    private int posy;
    private int start, end;
    public int Filesize;
    public TextFlow textFlow;
    private LinkedArrayList<String[]> text;
    private LinkedArrayList<Integer> index;


    /**
     * Constructor
     * @param searchingResultsPane panel de resultados
     * @param file archivo
     * @param phase frase
     * @param posy posición
     */
    public Results(Pane searchingResultsPane, File file, String[] phase, int posy) {
        this.searchingResultsPane = searchingResultsPane;
        this.file = file;
        this.posy = posy;
        this.phase = phase;
        this.Filesize = (int) file.length();
        searchingPhase = true;
        setResultPane();
        addTextPhase();
    }


    /**
     * Constructor
     * @param searchingResultsPane panel de resultados
     * @param file archivo
     * @param word palabra
     * @param posy posición
     */
    public Results(Pane searchingResultsPane, File file, String word, int posy) {
        this.searchingResultsPane = searchingResultsPane;
        this.file = file;
        this.posy = posy;
        this.word = word;
        searchingWord = true;
        this.Filesize = (int) file.length();

        setResultPane();
        addTextWord();


    }

    /**
     * Establece el panel que contiene un butón encargado de abrir el documento, un label con el nombre del documento y
     * un textFlow que muestra los resultados
     */
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


        Button open = new Button("Abrir");
        open.setLayoutX(450);
        open.setLayoutY(2);
        open.setOnMouseClicked(openDocument);
        open.setUserData(file);
        pane.getChildren().addAll(open);


        textFlow = new TextFlow();
        textFlow.setPrefSize(520, 220);
        textFlow.setLayoutX(30);

        ScrollPane scrollPane = new ScrollPane(textFlow);
        scrollPane.setLayoutY(30);
        scrollPane.setMaxSize(520, 220);
        pane.getChildren().add(scrollPane);


        searchingResultsPane.getChildren().add(pane);
    }


    /**
     * Evento linkeado al butón open, se encarga de abrir el documento correspondiente al resultado que se muestra
     */
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
    private void addTextWord() {
        text = DocumentParser.verifyFormat(file);
        index = DocumentManager.words.get(word).getRecurrences().getPositions(file);
        StringBuilder content = new StringBuilder();
        System.out.println(file.getName());
        for (int i = 0; i < index.getSize(); i++) {
            int Index = index.getElement(i);
            for (int x = -1; x <= 1; x++) {
                String[] line = text.getElement(Index + x);
                if (Index + x == Index) {
                    setHightLight(line, word);
                    if (index.contains(Index + 1) && Index +1  != text.getSize()-1) {
                        setHightLight(text.getElement(Index + 1), word);
                        i++;
                        break;
                    }
                } else {
                    for (int j = 0; j < line.length; j++) {
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


    /**
     * Método encargado de realizar el resaltado de la palabra que se buscó dentro de la línea de donde se encuentra
     * @param line linea
     * @param word palabra
     */
    private void setHightLight(String[] line, String word) {
        StringBuilder textBeforeOrAfterWord = new StringBuilder();
        for (String s : line) {
            s = s.replace(",", " ").replace(":", " ").replace(";", " ").replace(".", " ").trim();
            if (s.equals(word)) {
                Text text = new Text(word + " ");
                text.setFill(Color.web("F3720D"));
                text.setFont(Font.font("", FontWeight.BOLD, 14));
                textFlow.getChildren().addAll(new Text(textBeforeOrAfterWord + " "), text);
                textBeforeOrAfterWord.delete(0, textBeforeOrAfterWord.length());
            } else {
                textBeforeOrAfterWord.append(s + " ");
            }
        }
        textFlow.getChildren().addAll(new Text(" " + textBeforeOrAfterWord));
    }


    /**
     * Método encargado añadir las líneas donde se encuentran las frases en el TextFLow
     */

    private void addTextPhase() {
        text = DocumentParser.verifyFormat(file);
        index = DocumentManager.words.get(phase[0]).getRecurrences().getPositions(file);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < index.getSize(); i++) {
            if (searchPhrase(text.getElement(index.getElement(i)), phase)){
                int Index = index.getElement(i);
                for(int x=-1; x<=1; x++){
                    String[] line = text.getElement(Index+x);
                    if(Index+x == Index && Index != text.getSize()-1){
                        setHightLight(line);
                        if(searchPhrase(text.getElement(i+1), phase)){
                            setHightLight(text.getElement(i+1));
                            i++;
                            break;
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

    /***
     * Método encargado de realizar el resaltado de la frase que se buscó dentro de la línea en que se encuentra
     * @param line
     */
    private void setHightLight(String[] line) {
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


    /**
     * Método encargado de realizar la búsqueda de la frase dentro de la línea y establecer el inicio y fin dentro de esta
     * @param line linea
     * @param phase frase
     * @return true en caso de que se encuentre, false en caso contrario
     */
    private boolean searchPhrase(String[] line, String[] phase) {
        int index = 0;
        for (int i = 0; i < line.length; i++) {
            if(index==phase.length){return true;}
            if(line[i].equals(phase[index])){
                start=i;
                index++;
                for(int j=i+1; j<line.length; j++){
                    String word = line[j].replace(",", " ").replace(".", " ").replace(";", " ").replace("(", " ").replace(")", " ").replace("\n", " ").trim();
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
