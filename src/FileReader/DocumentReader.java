package FileReader;

import BinaryTree.BinaryTree;
import LinkedArrayList.LinkedArrayList;
import BinaryTree.Node;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.File;


/**
 * Clase la cual lee cada documento que ingresa a la biblioteca e realiza parte de la indizacion
 */
public class DocumentReader {
    public BinaryTree files = new BinaryTree();
    private LinkedArrayList<String> documentContent = new LinkedArrayList<String>();
    private LinkedArrayList<File> documents = new LinkedArrayList<File>();


    /**
     * Metodo el cual es llamado en el momento en que el usuario agrega una carpeta de documentos
     * @param files Array con todos los documentos de la carpeta
     */
    public void documentReader(File[] files) {
        LinkedArrayList linkedArrayListFiles = DocumentFormat.filterExtensions(files);
        System.out.println(linkedArrayListFiles.getSize());
        for (int i = 0; i < linkedArrayListFiles.getSize(); i++) {
            File file = (File) linkedArrayListFiles.getElement(i);
            System.out.println("El nombre del documento es" + file.getName());
            documentReader(file);

        }
    }


    /**
     * Documento que recibe un solo documentos y realiza parte de la indizacion de este.
     * @param file documento a agrega
     */
    public void documentReader(File file){
        if (!documentAlreadyExist(file)) {
            documents.addLast(file);
            String[] text = DocumentFormat.verifyFormat(file);
            if (text != null) {
                addDocumentToLibrary(file);
                for (String s : text) {
                    documentContent.addLast(s);
                }
                for (int i = 0; i < documentContent.getSize(); i++) {
                    System.out.println(documentContent.getElement(i));
                    files.insert(documentContent.getElement(i));
                    recurrences(files.getLast(), documentContent.getElement(i), text);
                    documentContent.deleteElement(documentContent.getElement(i));
                    i = -1;
                }
            }else{
                System.out.println("El documento se encuentra vacio");
            }
        }else{
            System.out.println("El documento ya existe en la biblioteca");
        }
    }

    private void recurrences(Node node, String word, String[] file) {
        for (int i = 0; i < file.length; i++) {
            if (word.equals(file[i])) {
                node.getRecurrences().addLast(i);
            }
        }
    }


    /**
     * Verifica si el documentos que se quiere agregar ya existe en la biblioteca
     * @param fileToAdd documento a agregar
     * @return true en caso de que ya existe, false en caso contrario
     */
    private boolean documentAlreadyExist(File fileToAdd){
        for(int i=0; i<documents.getSize(); i++){
            File file = documents.getElement(i);
            if(file.equals(fileToAdd)){
                return true;
            }
        }
        return false;
    }


    private void addDocumentToLibrary(File file){
        Label label = new Label();
        label.setPrefWidth(310);
        label.setPrefHeight(50);
        label.setText("\n" + file.getName() + "\n");
        label.setFont(Font.font("Arial Black", FontWeight.BOLD, 15));
        label.setAlignment(Pos.TOP_CENTER);
        label.setBackground(new Background(new BackgroundFill(Color.rgb(140,80,80), CornerRadii.EMPTY, Insets.EMPTY)));
        label.setLayoutX(10);
        //label.setLayoutY(posy);
        //documentScroll.getChildren().addAll(label);
        //posy += 75;
    }


}
