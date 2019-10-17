package FileReader;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PDFManager{
    private PDDocument pdDoc;
    private String text;

    private String filePath;

    PDFManager(String filePath) throws IOException {
        this.filePath = filePath;
        this.text = toText();
    }

    private String toText() throws IOException {
        this.pdDoc = null;

        File file = new File(filePath);
        PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));

        parser.parse();
        PDFTextStripper pdfStripper = new PDFTextStripper();
        pdDoc = new PDDocument(parser.getDocument());

        pdfStripper.setStartPage(0);
        pdfStripper.setEndPage(pdDoc.getNumberOfPages());

        return pdfStripper.getText(pdDoc);
    }
    public void setFilePath(String filePath){

    }

    public PDDocument getPdDoc(){
        return pdDoc;
    }
    String getText(){
        return text;
    }
}
