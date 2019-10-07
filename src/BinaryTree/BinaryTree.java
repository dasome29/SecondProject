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
            System.out.println("Esta vacio");
            return false;
        }
        else if(element.compareTo(current.element) < 0){
            System.out.println("Busqueda" + current.element);
            return contains(element, current.left);
        }
        else if(element.compareTo(current.element) > 0){
            System.out.println("Busqueda" + current.element);
            return contains(element, current.right);
        }
        else{
            System.out.println("Busqueda" + current.element);
            return true;
        }
    }

    public Node get(String element){
        return this.get(element, root);


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

    public void print(){
        this.print(root);
    }

    private void print(Node element){
        if(element == null){
            System.out.println("Vacio");

        }
        else{
            System.out.println("                  " + element.element  + "               ");
            System.out.println(element.right.element + "    "  +    element.left.element);
        }


    }


    public Node getLast() {
        return last;
    }
}


