package FileReader;

import BinaryTree.BinaryTree;
import LinkedArrayList.LinkedArrayList;
import BinaryTree.Node;

import java.io.File;

public class DocumentReader {
    private BinaryTree files = new BinaryTree();
    private LinkedArrayList<String> document = new LinkedArrayList<String>();



    public DocumentReader(File file) throws NullPointerException{
        try {
            String[] text = DocumentFormat.verifyFormat(file);
            files.setFile(text);
            for (String s : text) {
                document.addLast(s);
            }
            for (int i = 0; i < document.getSize(); i++) {
                files.insert(document.getElement(i));
                recurrences(files.getLast(), document.getElement(i), text);
                document.deleteElement(document.getElement(i));
                i = -1;
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    private void recurrences(Node node, String word, String[] file) {
        for (int i = 0; i < file.length; i++) {
            if (word.equals(file[i])) {
                node.getRecurrences().addLast(i);
            }
        }
    }

}
