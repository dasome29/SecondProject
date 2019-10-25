package FileReader;

import BinaryTree.BinaryTree;
import Components.DocumentsLibrary;
import LinkedArrayList.LinkedArrayList;

import java.io.File;


/**
 * Clase la cual lee cada documento que ingresa a la biblioteca e realiza parte de la indizacion
 */
public class DocumentManager {
    public static BinaryTree words = new BinaryTree();
    private LinkedArrayList<String> documentContent = new LinkedArrayList<String>();
    public static LinkedArrayList<File> documents = new LinkedArrayList<File>();


    public DocumentManager() { }



    /**
     * Metodo el cual es llamado en el momento en que el usuario agrega una carpeta de documentos
     * @param files Array con todos los documentos de la carpeta
     */


    public void documentReader(File[] files) {
        LinkedArrayList<File> linkedArrayListFiles = DocumentFormat.filterExtensions(files);
        for (int i = 0; i < linkedArrayListFiles.getSize(); i++) {
            File file = linkedArrayListFiles.getElement(i);
            documentReader2(file);


        }
    }



    public void documentReader2(File file){
        if (!documentAlreadyExist(file)){
                documents.addLast(file); // Se agrega a la lista de documentos de la aplicación
                DocumentsLibrary.addNewFileToLibrary(file);
            updateDocuments(file);
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



    public void deleteFile(File file) {
        LinkedArrayList<String[]> text = DocumentFormat.verifyFormat(file);
        if (documents.contains(file) && text != null) {
            for (int i = 0; i < text.getSize(); i++) {
                for (int j = 0; j < text.getElement(i).length; j++) {
                    String s = text.getElement(i)[j];
                    s = s.replace(",", " ").replace(".", " ").replace(";", " ").replace("(", " ").replace(")", " ").replace("\n", " ");
                    s = s.trim();
                    if (!s.equals("")) {
                        if(words.contains(s)) {
                            words.get(s).getRecurrences().deleteElement(file);
                        }

                    }
                }
            }
            documents.deleteElement(file);
        }
    }


    public void updateDocuments(File file){
        LinkedArrayList<String[]> text = DocumentFormat.verifyFormat(file);
        if(text != null){
            for(int i=0; i<text.getSize(); i++) {
                for (int j=0; j<text.getElement(i).length; j++) {
                    String s = text.getElement(i)[j];
                    s = s.replace(",", " ").replace(".", " ").replace(";", " ").replace("(", " ").replace(")", " ").replace("\n", " ");
                    s = s.trim();
                    System.out.println("Se agrega " + s);
                    if (!s.equals("")) {
                        if (!words.contains(s)) {
                            words.insert(s);
                        }
                        if (!words.get(s).getRecurrences().contains(file)) {
                            words.get(s).getRecurrences().addLast(file);
                        }
                        if(!words.get(s).getRecurrences().getPositions(file).contains(i)) {
                            words.get(s).getRecurrences().getPositions(file).addLast(i);
                        }
                    }
                }
            }
        }
    }


    /*
    public void documentManager(File file){
        if (!documentAlreadyExist(file)) {
            documents.addLast(file);
            DocumentsLibrary.addNewFileToLibrary(file);
            LinkedArrayList<String[]> text = DocumentFormat.verifyFormat(file);
            if (text != null) {
                for (int i = 0; i < text.getSize(); i++) {
                    for (String s : text.getElement(i)) {
                        s = s.replace(",", " ").replace(".", " ").replace(";", " ").replace("(", " ")
                                .replace(")", " ").replace("\n", " ");
                        s = s.trim();
                        if (!s.equals("")) {
                            System.out.println("|" + s + "|");
                            documentContent.addLast(s);
                        }
                    }
                }
                for (int j = 0; j < documentContent.getSize(); j++) {
                    String word = documentContent.getElement(j);
                    if (!words.contains(word)) {
                        words.insert(word);
                    }
                    words.get(word).setDocument(file, recurrences(text, word));
                    documentContent.deleteElement(word);
                    j = -1;
                }
            } else {
                System.out.println("El documento se encuentra vacio");
            }
        } else {
            System.out.println("El documento ya existe en la biblioteca");
        }
    }

     */

    private LinkedArrayList<int[]> recurrences(LinkedArrayList<String[]> text, String word){
        LinkedArrayList<int[]> mat = new LinkedArrayList<int[]>();
        for (int i = 0; i < text.getSize(); i++) {
            String[] array = text.getElement(i);
            for (int j = 0; j < array.length; j++) {
                if (array[j].equals(word)){
                    mat.addLast(new int [] {i,j});
                }
            }
        }
        return mat;
    }





}
