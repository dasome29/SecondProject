package GUI;

import FileReader.DocumentFormat;
import FileReader.DocumentReader;
import LinkedArrayList.LinkedArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Results {
    public Pane pane;
    public File file;
    private Pane searchingResultsPane;
    private TextArea textArea;
    private String word;
    private int posy;
    public int Filesize;


    Results(Pane searchingResultsPane, File file, String word, int posy){
        this.searchingResultsPane = searchingResultsPane;
        this.file = file;
        this.posy = posy;
        this.word = word;
        this.Filesize = (int)file.length();
        System.out.println(Filesize);


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

        addText();

        searchingResultsPane.getChildren().add(pane);


    }

    private EventHandler<MouseEvent> openDocument = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            new Thread(() -> {
                try {
                    System.out.println(event.getSource());
                    File file = new File(((File) ((Button) event.getSource()).getUserData()).getPath());
                    System.out.println("Abre documento");
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                }
            }).start();
        }
    };


    private void addText(){
        System.out.println("--------------------");
        LinkedArrayList<String[]> text = DocumentFormat.verifyFormat(file);
        LinkedArrayList<int[]> index = DocumentReader.words.get(word).getRecurrences().getPositions(file);
        String content = "";
        for(int i=0; i< index.getSize(); i++) {
                String[] line = text.getElement(index.getElement((i))[0]);
                for (int j = 0; j < line.length; j++) {
                    content += line[j] + " ";
                }

            content += "\n\n\n";
        }
        textArea.setText(content);
    }

}
