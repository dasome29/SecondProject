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
    public static LinkedArrayList<File> documents = new LinkedArrayList<File>();
    public static LinkedArrayList<LinkedArrayList<String[]>> documentContent = new LinkedArrayList<LinkedArrayList<String[]>>();

    public DocumentManager() { }



    /**
     * Metodo el cual es llamado en el momento en que el usuario agrega una carpeta de documentos
     * @param files Array con todos los documentos de la carpeta
     */


    public void documentReader(File[] files) {
        LinkedArrayList<File> linkedArrayListFiles = DocumentParser.filterExtensions(files);
        for (int i = 0; i < linkedArrayListFiles.getSize(); i++) {
            File file = linkedArrayListFiles.getElement(i);
            documentReader(file);


        }
    }



    public void documentReader(File file){
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


    /**
     * Método que elimina los documentos seleccionados por el usuario
     * @param file archivo a eliminar
     */

    public void deleteFile(File file) {
        System.out.println(documentContent.getSize() + "  " + documents.getSize() + "  " + documents.getElementIndex(file));
        LinkedArrayList<String[]> text = documentContent.getElement(documents.getElementIndex(file));
       //LinkedArrayList<String[]> text = DocumentParser.verifyFormat(file);
        if (documents.contains(file) && text != null) {
            for (int i = 0; i < text.getSize(); i++) {
                for (int j = 0; j < text.getElement(i).length; j++) {
                    String s = text.getElement(i)[j];
                    s = s.replace(",", " ").replace(".", " ").replace(";", " ").replace("(", " ").replace(")", " ").replace("\n", " ");
                    s = s.trim();
                    if (!s.equals("")) {
                        if(words.contains(s)) {
                            words.get(s).getRecurrences().deleteElement(file);
                            //System.out.println("Palabra " + s  + " Contiene1" + words.get(s).getRecurrences().contains(file) + "size " + words.get(s).getRecurrences().getSize());
                        }

                    }
                }
            }
            documentContent.deleteElement(text);
            documents.deleteElement(file);
        }
    }


    /**
     * Método que inserta las palabras y sus recurrencias al árbol binario
     * @param file nuevo archivo
     */
    public void updateDocuments(File file){
        LinkedArrayList<String[]> text = DocumentParser.verifyFormat(file);
        documentContent.addLast(text);
        if(text != null){
            for(int i=0; i<text.getSize(); i++) {
                for (int j=0; j<text.getElement(i).length; j++) {
                    String s = text.getElement(i)[j];
                    s = s.replace(",", " ").replace(".", " ").replace(";", " ").replace("(", " ").replace(")", " ").replace("\n", " ");
                    s = s.trim();
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





}
