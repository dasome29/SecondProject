package BinaryTree;

import LinkedArrayList.LinkedArrayList;

public class BinaryTree{
    private String[] file;
    private Node root = null;
    private Node last = null;


    boolean isEmpty(){
        return root == null;
    }


    boolean contains(String element){
        return this.contains(element, root);
    }

    private boolean contains(String element, Node current){
        if(current == null){
            return false;
        }
        else if(element.compareTo(current.element) < 0){
            return contains(element, current.left);
        }
        else if(element.compareTo(current.element) > 0){
            return contains(element, current.right);
        }
        else{
            return true;
        }
    }


    public void insert(String element){
        root = this.insert(element, root);
    }


    private Node insert(String element, Node current){
        if(current == null){
            last = new Node(element);
            return last;
        }
        else if(element.compareTo(current.element) < 0){
            current.left = insert(element, current.left);
        }
        else if(element.compareTo(current.element) > 0){
            current.right = insert(element, current.right);
        }else{
            return current;
        }
        return current;
    }
    public void setFile(String[] file){
        this.file = file;
    }

    public Node getLast() {
        return last;
    }
}


