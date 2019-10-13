package BinaryTree;

import LinkedArrayList.LinkedArrayList;

public class BinaryTree{
    private Node root = null;
    private Node last = null;

    boolean isEmpty(){
        return root == null;
    }


    public boolean contains(String element){
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

    public Node get(String name){
        return this.get(name, root);


    }


    private Node get(String element, Node current){
        if(current == null){
            return null;
        }
        else if(element.compareTo(current.element) < 0){
            return get(element, current.left);
        }
        else if(element.compareTo(current.element) > 0){
            return get(element, current.right);
        }
        else{
            return current;
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

    public void remove(String element){
        remove(element, root);
    }
    private Node remove(String element, Node c){
        if( c == null){
            return null;
        }
        if (element.compareTo(c.element)<0){
            c. left = remove(element, c.left);
        }
        else if(element.compareTo(c.element) > 0){
            c.right = remove(element, c.right);
        }
        else if(c.left != null && c.right != null){
            c.element = findMin(c.right).element;
            c = c.right = remove(c.element, c.right);
        }else{
            c= c.left != null ? c.left : c.right;
        }
        return c;
    }
    private Node findMin(Node c){
        if (c.left.element.compareTo(c.right.element) > 0 ){
            return findMin(c.left);
        }else if (c.left.element.compareTo(c.right.element) < 0 ){
            return findMin(c.right);
        }
        return c;
    }

    public Node getLast() {
        return last;
    }
}


