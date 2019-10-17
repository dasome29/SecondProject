package BinaryTree;

import LinkedArrayList.LinkedArrayList;


/**
 * Clase donde se implementa el árbol binario necesario para la indización de los documentos
 */
public class BinaryTree{
    private Node root = null;
    private Node last = null;


    /**
     * Verifica si el árbol se encuentra vacío o no
     * @return
     */
    boolean isEmpty(){
        return root == null;
    }


    /**
     * Método que indica si el elemento ingresado por parámetros se encuentra dentro del árbol
     * @param element elemento a verificar
     * @return true en caso que sí lo contenga, false en caso contrario
     */
    public boolean contains(String element){
        return contains(element, root);
    }

    /**
     * Método auxiliar del contains, recorre el árbol en busca del elemento
     * @param element elemento a verificar
     * @param current nodo actual
     * @return true en caso que sí lo contenga, false en caso contrario
     */
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


    /**
     * Método para obtener el nodo del elemento
     * @param name palanbra
     * @return Nodo de la palabra
     */
    public Node get(String name){
        return get(name, root);


    }


    /**
     * Método auxiliar del get
     * @param element elemento a obtener
     * @param current Nodo actual
     * @return Nodo de la palabra
     */
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


    /**
     * Método que inserta un elemento al árbol
     * @param element elemento a insertar
     */
    public void insert(String element){
        root = this.insert(element, root);
    }

    /**
     * Método auxuliar del insert
     * @param element elemento a insertar
     * @param current Nodo actual
     * @return Nodo
     */

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


    /**
     * Método para remover un elemento del árbol
     * @param element elemento a remover
     */
    public void remove(String element){
        remove(element, root);
    }

    /**
     * Método auxiliar para remover un elemento del árbol
     * @param element elemento a remover
     * @param c Nodo actual
     * @return
     */
    private Node remove(String element, Node c){
        if( c == null){
            return null;
        }
        if (element.compareTo(c.element)<0){
            c.left = remove(element, c.left);
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


