package BinaryTree;

import LinkedArrayList.LinkedArrayList;
import java.io.File;

/**
 * Nodo del árbol binario
 */

public class Node {
    String element;
    LinkedArrayList<File> recurrences = new LinkedArrayList<File>();
    //private LinkedArrayList<LinkedArrayList<LinkedArrayList>> recurrences = new LinkedArrayList<LinkedArrayList<LinkedArrayList>>();

    public Node left;
    public Node right;


    /**
     *  Constructor del nodo
     * @param element
     */
    Node(String element){
        this.element = element;
    }



    /**
     * Método que retorn la lista  de concurrencias de la palanbra
     * @return Lista con las concurrencias
     */
    public LinkedArrayList<File> getRecurrences (){
        return recurrences;
    }
}
