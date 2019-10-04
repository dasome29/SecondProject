package FileReader;

import BinaryTree.BinaryTree;
import LinkedArrayList.LinkedArrayList;
import BinaryTree.Node;

public class FileReader {
    private BinaryTree files = new BinaryTree();
    private LinkedArrayList<String> document = new LinkedArrayList<String>();

    FileReader(String[] file) {
        files.setFile(file);
        for (String s : file) {
            document.addLast(s);
        }
        for (int i = 0; i < document.getSize(); i++){
            files.insert(document.getElement(i));
            recurrences(files.getLast(), document.getElement(i), file);
            document.deleteElement(document.getElement(i));
            i=-1;
        }
    }
    private void recurrences(Node node, String word, String[] file){
        for (int i = 0; i < file.length; i++) {
            if (word.equals(file[i])){
                node.getRecurrences().addLast(i);
            }
        }
    }
}
