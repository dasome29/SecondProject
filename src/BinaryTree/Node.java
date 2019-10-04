package BinaryTree;

import LinkedArrayList.LinkedArrayList;

public class Node {
    String element;
    private LinkedArrayList<Integer> recurrences = new LinkedArrayList<Integer>();
    Node left;
    Node right;


    Node(String element){
        this.element = element;
    }

    public LinkedArrayList<Integer> getRecurrences (){
        return recurrences;
    }
}
