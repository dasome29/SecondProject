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

    public static LinkedArrayList<String[]> verifyFormat(File file){
        String nameFile = file.getName();
        String substring = nameFile.substring(nameFile.length() - 3);
        if(substring.equals("pdf")) {
            return pdfDocument(file);
        }

        if(nameFile.substring(nameFile.length()-4).equals("docx")){
            return docxDocument(file);

        }
        if(substring.equals("txt")){
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
    static LinkedArrayList<File> filterExtensions(File[] files){
        LinkedArrayList<File> linkedArrayList = new LinkedArrayList<File>();
        for (File file : files) {
            String nameFile = file.getName();
            String substring = nameFile.substring(nameFile.length() - 3);
            if (substring.equals("pdf")) {
                linkedArrayList.addLast(file);
            }
            if (nameFile.substring(nameFile.length() - 4).equals("docx")) {
                linkedArrayList.addLast(file);

            }

            if (substring.equals("txt")) {
                linkedArrayList.addLast(file);

            }
        }
        return linkedArrayList;
    }


    /**
     * Metodo que realiza el parseo de los documento con formato .pdf
     * @param file Documentos a agregar
     * @return Array con el texto parseado del documento
     */

    private static LinkedArrayList<String[]> pdfDocument(File file){
        try {
            PDFManager text = new PDFManager(file.getPath());
            String[] doc = text.getText().split("\n");
            LinkedArrayList<String[]> result = new LinkedArrayList<String[]>();
            for (String s : doc) {
                result.addLast(s.split(" "));
            }
            return result;
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
    private static LinkedArrayList<String[]> docxDocument(File file) {
        LinkedArrayList<String[]> result = new LinkedArrayList<String[]>();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            System.out.println("Entra");
            XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);
            XWPFWordExtractor extractor = new XWPFWordExtractor(xwpfDocument);
            String[] doc = extractor.getText().split("\n");
            for (String s : doc) {
                result.addLast(s.split(" "));
            }


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
    private static LinkedArrayList<String[]> txtDocument(File file) {
        BufferedReader bufferedReader;
        try {
            String line;
            LinkedArrayList<String[]> result = new LinkedArrayList<String[]>();
            FileReader fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                result.addLast(line.split(" "));
            }
            bufferedReader.close();
            return result;

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
