package Components;

import FileReader.DocumentFormat;
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

class Results {
    Pane pane;
    File file;
    private Pane searchingResultsPane;
    private String[] phrase;
    private int posy;
    private int start, end;
    int FileSize;
    private TextFlow textFlow;
    private LinkedArrayList<String[]> text;
    private LinkedArrayList<Integer> index;


    Results(Pane searchingResultsPane, File file, String[] phrase, int posy) {
        this.searchingResultsPane = searchingResultsPane;
        this.file = file;
        this.posy = posy;
        this.phrase = phrase;
        this.FileSize = (int) file.length();
        setResultPane();
        addTextPhase();
    }


    Results(Pane searchingResultsPane, File file,String word,  int posy) {
        this.searchingResultsPane = searchingResultsPane;
        this.file = file;
        this.posy = posy;
        this.FileSize = (int) file.length();

        setResultPane();
        addTextWord(word);


    }


    private void setResultPane() {

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
        } catch (IOException ignored) {
        }
    }).start();


    /**
     * Método utilizado para agregar el texto en el TextArea en caso de que se busque una palabra individual
     */


    private void addTextPhase() {
        text = DocumentFormat.verifyFormat(file);
        index = DocumentManager.words.get(phrase[0]).getRecurrences().getPositions(file);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < index.getSize(); i++) {
            if (searchPhrase(text.getElement(index.getElement(i)), phrase)) {
                int Index = index.getElement(i);
                for (int x = -1; x <= 1; x++) {
                    String[] line = text.getElement(Index + x);
                    if (Index + x == Index) {
                        highlightLine(line);
                        if (searchPhrase(text.getElement(i + 1), phrase)) {
                            highlightLine(text.getElement(i + 1));
                            i++;
                        }
                    } else {
                        for (String s : line) {
                            content.append(s).append(" ");

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

    private void highlightLine(String[] line) {
        StringBuilder textBeforeOrAfterWord = new StringBuilder();
        StringBuilder phaseString = new StringBuilder();

        for (int i = 0; i < start; i++) {
            textBeforeOrAfterWord.append(line[i]).append(" ");
        }
        textBeforeOrAfterWord.append(" ");
        textFlow.getChildren().add(new Text(textBeforeOrAfterWord.toString()));
        textBeforeOrAfterWord.delete(0, textBeforeOrAfterWord.length());
        for (int i = start; i <= end; i++) {
            phaseString.append(line[i]).append(" ");
        }
        Text text = new Text(phaseString.toString());
        text.setFill(Color.web("F3720D"));
        text.setFont(Font.font("", FontWeight.BOLD, 14));
        textFlow.getChildren().add(text);
        for (int i = end + 1; i < line.length; i++) {
            textBeforeOrAfterWord.append(line[i]).append(" ");
        }
        textBeforeOrAfterWord.append(" ");
        textFlow.getChildren().add(new Text(textBeforeOrAfterWord.toString()));
        textBeforeOrAfterWord.delete(0, textBeforeOrAfterWord.length());
    }


    private boolean searchPhrase(String[] text, String[] phase) {
        int index = 0;
        for (int i = 0; i < text.length; i++) {
            if (index == phase.length) {
                return true;
            }
            if (text[i].equals(phase[index])) {
                start = i;
                index++;
                for (int j = i + 1; j < text.length; j++) {
                    String word = text[j].replace(",", " ").replace(".", " ").replace(";", " ").replace("(", " ").replace(")", " ").replace("\n", " ").trim();
                    if (word.equals("")) {
                        continue;
                    }
                    if (index == phase.length) {
                        end = j - 1;
                        return true;
                    }
                    if (word.equals(phase[index])) {
                        end = j;
                        index++;
                    } else {
                        index = 0;
                        break;
                    }

                }
            }
        }
        System.out.println("NO CONTIENE LA FRASE");
        return false;
    }

    private void addTextWord(String word) {
        text = DocumentFormat.verifyFormat(file);
        index = DocumentManager.words.get(word).getRecurrences().getPositions(file);
        StringBuilder content = new StringBuilder();
        System.out.println(file.getName());
        for (int i = 0; i < index.getSize(); i++) {
            int Index = index.getElement(i);
            for (int x = -1; x <= 1; x++) {
                String[] line = text.getElement(Index + x);
                if (Index + x == Index) {
                    highlightLine(line, word);
                    if (index.contains(Index + 1)) {
                        highlightLine(text.getElement(Index + 1), word);
                        i++;
                    }
                } else {
                    for (String s : line) {
                        content.append(s).append(" ");
                    }
                    content.append("\n");
                    textFlow.getChildren().add(new Text(content.toString()));
                    content.delete(0, content.length());
                }
            }
            textFlow.getChildren().add(new Text("\n\n\n"));
        }
    }

    private void highlightLine(String[] line, String word) {
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
                textBeforeOrAfterWord.append(s).append(" ");
            }
        }
        textFlow.getChildren().addAll(new Text(" " + textBeforeOrAfterWord));
    }


}
