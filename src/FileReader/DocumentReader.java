package FileReader;

import BinaryTree.BinaryTree;
import LinkedArrayList.LinkedArrayList;
import BinaryTree.Node;
import java.io.File;

public class DocumentReader {
    public BinaryTree files = new BinaryTree();
    private LinkedArrayList<String> documentContent = new LinkedArrayList<String>();
    private LinkedArrayList<File> documents = new LinkedArrayList<File>();


    public DocumentReader() {}



    public void documentReader(File[] files) {
        LinkedArrayList linkedArrayListFiles = DocumentFormat.filterExtensions(files);
        System.out.println(linkedArrayListFiles.getSize());
        for (int i = 0; i < linkedArrayListFiles.getSize(); i++) {
            File file = (File) linkedArrayListFiles.getElement(i);
            System.out.println("El nombre del documento es" + file.getName());
            documentReader(file);

        }
    }

    public void documentReader(File file) throws NullPointerException {
        if (!documentAlreadyExist(file)) {
            documents.addLast(file);
            String[] text = DocumentFormat.verifyFormat(file);
            if (text != null) {
                for (String s : text) {
                    documentContent.addLast(s);
                }
                for (int i = 0; i < documentContent.getSize(); i++) {
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


    private boolean documentAlreadyExist(File fileToAdd){
        for(int i=0; i<documents.getSize(); i++){
            File file = documents.getElement(i);
            if(file.equals(fileToAdd)){
                return true;
            }
        }
        return false;
    }

}
