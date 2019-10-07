package BinaryTree;

import FileReader.DocumentFormat;
import LinkedArrayList.LinkedArrayList;

import java.io.File;

public class Node {
    String element;
    private LinkedArrayList<File> recurrences = new LinkedArrayList<File>();
    Node left;
    Node right;


    Node(String element){
        this.element = element;
    }

    public LinkedArrayList<File> getRecurrences (){
        return recurrences;
    }
}
