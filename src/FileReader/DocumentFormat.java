package FileReader;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DocumentFormat {



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



    private static String[] pdfDocument(File file){
        try {
            PDDocument pdDocument = new PDDocument().load(file);
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String[] text = pdfTextStripper.getText(pdDocument).split(" ");
            System.out.println(text);
            return text;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static String[] docxDocument(File file) {
        try {
            System.out.println("WORD");
            FileInputStream fileInputStream = new FileInputStream(file);
            XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);

            XWPFWordExtractor extractor = new XWPFWordExtractor(xwpfDocument);
            String[] text = extractor.getText().split(" ");

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private static String[] txtDocument(File file){
        return null;
    }
}
