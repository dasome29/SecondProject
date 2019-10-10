package GUI;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Results {
    private Pane pane;
    private Pane searchingResultsPane;
    private TextArea textArea;
    private File file;
    private String word;
    private int posy;


    public Results(Pane searchingResultsPane, File file, String word, int posy){
        this.searchingResultsPane = searchingResultsPane;
        this.file = file;
        this.posy = posy;
        this.word = word;


        pane = new Pane();
        pane.setPrefSize(300, 150);
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setLayoutX(10);
        pane.setLayoutY(posy);


        textArea = new TextArea(file.getName());
        textArea.setPrefSize(300, 125);
        textArea.setLayoutY(25);
        pane.getChildren().add(textArea);

        searchingResultsPane.getChildren().add(pane);


    }

}
