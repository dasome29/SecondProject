package GUI;

import LinkedArrayList.LinkedArrayList;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDFormContentStream;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.util.Scanner;

public class Main extends Application {
    private static int sizeY =0;
    private static int posy = 10;
    private Pane root;
    private Pane documentsScroll;


    @Override
    public void start(Stage primaryStage) throws Exception{
        root = new Pane();


        Pane pane = new Pane();
        pane.setPrefSize(350, 900);
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(140,40,40), CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().add(pane);


        documentsScroll = new Pane();
        documentsScroll.setPrefSize(350, 830);
        documentsScroll.setBackground(new Background(new BackgroundFill(Color.rgb(140,40,40), CornerRadii.EMPTY, Insets.EMPTY)));
        ScrollPane scrollPane = new ScrollPane(documentsScroll);
        pane.getChildren().addAll(scrollPane);


        Button addNewFile = new Button("Add");
        addNewFile.setLayoutX(290);
        addNewFile.setLayoutY(850);
        addNewFile.setOnMouseClicked(NewFile);
        pane.getChildren().addAll(addNewFile);




        primaryStage.setTitle("Text finder");
        primaryStage.setScene(new Scene(root, 1500, 900));
        primaryStage.show();
    }



    EventHandler<MouseEvent> NewFile = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("/home"));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF files", "*.pdf"),
                    new FileChooser.ExtensionFilter("TXT files", "*.txt"), new FileChooser.ExtensionFilter("DOCX files", "*.docx"));
            File file = fileChooser.showOpenDialog(null);





        }
    };



    private void addDocument(File file){
        Label label = new Label();
        label.setText("\n" + file.getName() + "\n");
        label.setBackground(new Background(new BackgroundFill(Color.rgb(140,80,80),CornerRadii.EMPTY, Insets.EMPTY)));
        label.setLayoutX(10);
        label.setLayoutY(posy);
        documentsScroll.getChildren().addAll(label);
        posy += 50;
    }


    public static void main(String[] args) {
        launch(args);
    }
}

