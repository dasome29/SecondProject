package Components;

import FileReader.DocumentManager;
import LinkedArrayList.LinkedArrayList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Biblioteca de documentos
 */

public class DocumentsLibrary {
    private static LinkedArrayList<CheckBox> checkButtonList;
    private static DocumentManager documentManager;
    private static Pane libraryPane;
    private static int posy = 20;

    /**
     * Constructor
     * @param libraryPane panel de documentos
     * @param paneButtons panel de botones
     * @param documentManager manager de documentos
     */

    public DocumentsLibrary(Pane libraryPane, Pane paneButtons, DocumentManager documentManager) {
        this.libraryPane = libraryPane;
        this.documentManager = documentManager;

        Button update = new Button("Update");
        update.setLayoutX(20);
        update.setLayoutY(850);
        update.setOnMouseClicked(updateEvent);
        paneButtons.getChildren().add(update);

        Button delete = new Button("Delete");
        delete.setLayoutX(90);
        delete.setLayoutY(850);
        delete.setOnMouseClicked(deleteFile);
        paneButtons.getChildren().add(delete);

        Button addNewFile = new Button("Add File");
        addNewFile.setLayoutX(275);
        addNewFile.setLayoutY(850);
        addNewFile.setOnMouseClicked(newFile);
        paneButtons.getChildren().addAll(addNewFile);


        Button addNewFolder = new Button("Add Folder");
        addNewFolder.setLayoutX(180);
        addNewFolder.setLayoutY(850);
        addNewFolder.setOnMouseClicked(newFolder);
        paneButtons.getChildren().addAll(addNewFolder);

        checkButtonList = new LinkedArrayList<CheckBox>();

    }


    /**
     * Método  que agrega un nuevo documento a la biblioteca, se inserta un checkButton con el nombre del documento
     * @param file documento
     */
    public static void addNewFileToLibrary(File file) {
        CheckBox button = new CheckBox();
        button.setLayoutX(20);
        button.setLayoutY(posy);
        button.setText(file.getName());
        button.setTextFill(Color.rgb(255, 255, 255));
        button.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        button.setUserData(file);
        checkButtonList.addLast(button);
        libraryPane.getChildren().add(button);
        posy += 50;
    }


    /**
     * Método que actualiza los documentos seleccionados por el usuario. Primeramente se eliminan de la aplicación para luego
     * volverlo a incluir con los nuevos cambios
     */
    public void updateDocuments() {
        for (int i = 0; i < checkButtonList.getSize(); i++) {
            if (checkButtonList.getElement(i).isSelected()) {
                System.out.println("Se actualiza");
                File file = (File) checkButtonList.getElement(i).getUserData();
                documentManager.deleteFile(file);
                documentManager.updateDocuments(file);
            }
        }
    }

    /**
     * Método que elimina los documentos gráficamente de la biblioteca y llama al método para eliminarlos lógicamente
     */

    private void deleteFiles() {
        System.out.println(checkButtonList.getSize());
        for (int i = 0; i < checkButtonList.getSize(); i++) {
            CheckBox fileButton = checkButtonList.getElement(i);
            System.out.println(fileButton.isSelected());
            if (fileButton.isSelected()) {
                File file = (File) fileButton.getUserData();
                System.out.println("Se eliminan");
                documentManager.deleteFile(file);
                libraryPane.getChildren().remove(fileButton);
                checkButtonList.delete(i);
                i--;
            }
        }
        updatePositions();
    }


    /**
     * Método que actualiza las posiciones de los documentos (botones) después de que se haya realizado alguna eliminación
     */
    private void updatePositions(){
        posy =20;
        for(int i=0; i< checkButtonList.getSize(); i++){
            checkButtonList.getElement(i).setLayoutY(posy);
            posy +=50;
        }
    }


    /**
     * Evento que linkeado al botón update para llamar al método correspondiente
     */
    private EventHandler<MouseEvent> updateEvent = event -> updateDocuments();


    /**
     * Evento linkeado al botón newFolder, se encarga de abrir el buscador de archivos para directorios y llama al método
     * para realizar la indización
     */
    private EventHandler<MouseEvent> newFolder = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setInitialDirectory(new File("/home"));
            File[] seletedFiles = directoryChooser.showDialog(null).listFiles();
            if(seletedFiles != null) {
                documentManager.documentReader(seletedFiles);
            }

        }
    };

    /**
     * Evento linkeado al botón newFolder, se encarga de abrir el buscador de archivos para archivos individuales y llama al método
     * para realizar la indización
     */
    private EventHandler<MouseEvent> newFile = new EventHandler<>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("/home"));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF files", "*.pdf"),
                    new FileChooser.ExtensionFilter("TXT files", "*.txt"), new FileChooser.ExtensionFilter("DOCX files", "*.docx"));
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                documentManager.documentReader(selectedFile);
            }

        }
    };


    /**
     * Evento linkeado al botón delete, se encarga llamar al método correspondiente
     */
    private EventHandler<MouseEvent> deleteFile = event -> deleteFiles();


}
