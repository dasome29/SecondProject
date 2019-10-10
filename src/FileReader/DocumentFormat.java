package FileReader;

import LinkedArrayList.LinkedArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import java.io.*;


public class DocumentFormat {

    /**
     * Metodo que verifica a cual de los tres formatos permitidos por la aplicacion pertecene el documento a agregar
     * para llamar el metedo respectivo que realiza el parseo
     * @param file documento a agregar
     * @return Retorna un array que contiene todas las palabras
     */

    public static String[] verifyFormat(File file){
        String nameFile = file.getName();
        if(nameFile.substring(nameFile.length() -3).equals("pdf")) {
            return pdfDocument(file);
        }

        if(nameFile.substring(nameFile.length()-4).equals("docx")){
            return docxDocument(file);

        }
        if(nameFile.substring(nameFile.length()-3).equals("txt")){
            return txtDocument(file);

        }
        return null;
    }


    /**
     * Metodo que filtra las extensiones de los documentos que se encuentran en una carpeta, esto para cuando el usuario agregue
     * una carpeta a la bilioteca con documentos de otros formatos
     * @param files Arrays con los documentos de la carpeta
     * @return Lista enlazada con los documentos con formato permitido
     */
    public static LinkedArrayList filterExtensions(File[] files){
        LinkedArrayList linkedArrayList = new LinkedArrayList();
        for(int i=0; i< files.length; i++) {
            File file = files[i];
            String nameFile = file.getName();
            if (nameFile.substring(nameFile.length() - 3).equals("pdf")) {
                linkedArrayList.addLast(files[i]);
            }
            if (nameFile.substring(nameFile.length() - 4).equals("docx")) {
                linkedArrayList.addLast(files[i]);
            }

            if (nameFile.substring(nameFile.length() - 3).equals("txt")) {
                linkedArrayList.addLast(files[i]);

            }
        }
        return linkedArrayList;
    }


    /**
     * Metodo que realiza el parseo de los documento con formato .pdf
     * @param file Documentos a agregar
     * @return Array con el texto parseado del documento
     */

    private static String[] pdfDocument(File file){
        try {
            PDDocument pdDocument = new PDDocument().load(file);
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            pdfTextStripper.setWordSeparator("");
            String[] text = pdfTextStripper.getText(pdDocument).split(" ");
            return text;

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Empty File", ButtonType.OK);
            alert.setHeaderText("Problema con el documento que desea agregar");
            alert.setContentText("El documento se encuentra vacío, por favor intentelo nuevamente");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
        }
        return null;
    }


    /**
     *  Metodo que realiza el parseo de los documento con formato .docx
     * @param file Documentos a agregar* @return Array con el texto parseado del documento
     * @return Array con el textp parseado del documento
     */
    private static String[] docxDocument(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            System.out.println("Entra");
            XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);
            XWPFWordExtractor extractor = new XWPFWordExtractor(xwpfDocument);
            String[] text = extractor.getText().split(" ");
            return text;

        }catch (FileNotFoundException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Empty File", ButtonType.OK);
            alert.setHeaderText("Problema con el documento que desea agregar");
            alert.setContentText("El documento se encuentra vacío, por favor intentelo nuevamente");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Metodo que realiza el parseo de los documento con formato .txt
     * @param file Documentos a agregar* @return Array con el texto parseado del documento
     * @return Array con el textp parseado del documento
     */
    private static String[] txtDocument(File file) {
        try {
            String text = new String();
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (bufferedReader.ready()) {
                text += bufferedReader.readLine() + " ";

            }
            String[] newText = text.split(" ");
            return newText;

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Empty File", ButtonType.OK);
            alert.setHeaderText("Problema con el documento que desea agregar");
            alert.setContentText("El documento se encuentra vacío, por favor intentelo nuevamente");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();

            e.printStackTrace();
        }

        return null;
    }
}
