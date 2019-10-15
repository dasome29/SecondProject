package BinaryTree;

import FileReader.DocumentFormat;
import LinkedArrayList.LinkedArrayList;

import java.io.File;

public class Node {
    String element;
    private LinkedArrayList<LinkedArrayList<LinkedArrayList>> recurrences = new LinkedArrayList<LinkedArrayList<LinkedArrayList>>();

    Node left;
    Node right;


    Node(String element){
        this.element = element;
    }

    public void setDocument(File file, LinkedArrayList<int[]> mat){
        LinkedArrayList<File> document = new LinkedArrayList<File>();
        document.addLast(file);

        LinkedArrayList<LinkedArrayList> pairs = new LinkedArrayList<LinkedArrayList>();
        pairs.addLast(document);
        pairs.addLast(mat);

        recurrences.addLast(pairs);
    }
    public LinkedArrayList<File> getDocuments(){
        LinkedArrayList<File> docs = new LinkedArrayList<File>();
        for (int i = 0; i < recurrences.getSize(); i++) {
            docs.addLast((File) recurrences.getElement(i).getElement(0).getElement(0));
        }
        return docs;
    }
    public LinkedArrayList getPositions(File file){
        LinkedArrayList<int[]> result = new LinkedArrayList<int[]>();
        for (int i = 0; i < recurrences.getSize(); i++) {
            if (file.equals(recurrences.getElement(i).getElement(0).getElement(0))){
                for (int j = 0; j<recurrences.getElement(i).getElement(1).getSize();j++){
                    result.addLast((int[]) recurrences.getElement(i).getElement(1).getElement(j));
                }
            }
        }
        return result;
    }
    public void deleteDoc(File file){
        for (int i = 0; i < recurrences.getSize(); i++) {
            if (recurrences.getElement(i).getElement(0).getElement(0).equals(file)){
                recurrences.delete(i);
            }
        }
    }


    public LinkedArrayList<LinkedArrayList<LinkedArrayList>> getRecurrences (){
        return recurrences;
    }
}
