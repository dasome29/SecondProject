package LinkedArrayList;

/**
 * This Class is where the elements of the LinkedArrayList Class are stored.
 * @param <T> The type of Object.
 */
class Node<T> {
    private T valuex;
    private Node<T> nextx;
    private Node<T> prevx;

    /**
     * Constructor that only takes the element to add.
     * @param value Element to store.
     */
    Node(T value){
        valuex = value;
        nextx = null;
        prevx = null;
    }

    /**
     * Constructor that takes the value, the next node before the current, and the previous one.
     * @param value Element to store.
     * @param next The next node.
     * @param prev The previous node.
     */
    Node(T value, Node<T> next, Node<T> prev){
        valuex = value;
        nextx = next;
        prevx = prev;

    }
    T getValue(){
        return valuex;
    }
    Node<T> getNext(){
        return nextx;
    }
    void setNext(Node<T> newNext){
        nextx = newNext;
    }
    Node<T> getPrev(){
        return prevx;
    }
    void setPrev(Node<T> newPrev){
        prevx = newPrev;
    }
}
