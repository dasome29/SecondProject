package LinkedArrayList;

/**
 * This Class is where the elements of the LinkedArrayList Class are stored.
 * @param <T> The type of Object.
 */
class ListNode<T> {
    private T valuex;
    private LinkedArrayList<Integer> positions = new LinkedArrayList<Integer>();
    private ListNode<T> nextx;
    private ListNode<T> prevx;

    /**
     * Constructor that only takes the element to add.
     * @param value Element to store.
     */
    ListNode(T value){
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
    ListNode(T value, ListNode<T> next, ListNode<T> prev){
        valuex = value;
        nextx = next;
        prevx = prev;

    }
    T getValue(){
        return valuex;
    }

    void setValue(T valuex) {
        this.valuex = valuex;
    }
    ListNode<T> getNext(){
        return nextx;
    }

    void setNext(ListNode<T> newNext){
        nextx = newNext;
    }
    ListNode<T> getPrev(){
        return prevx;
    }
    void setPrev(ListNode<T> newPrev){
        prevx = newPrev;
    }

    LinkedArrayList<Integer> getPositions(){
        return positions;
    }
}
