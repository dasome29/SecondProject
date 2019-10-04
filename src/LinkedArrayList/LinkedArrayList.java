package LinkedArrayList;

/**
 * This Class is the main storage for this project.
 * It can hold every type of object in it.
 * @param <T> T is the type of the list, it could be any Object.
 */
public class LinkedArrayList<T> {
    private ListNode<T> head = null;
    private ListNode<T> last = null;
    private int size = 0;

    /**
     * This method adds a new object in the first position of the list, every time.
     * @param element Corresponds to the item that is entering the list.
     */
    private void addFirst(T element) {
        if (head != null) {
            ListNode<T> headx = new ListNode<T>(element, head, null);
            headx.setPrev(head);
            head = headx;
        } else {
            head = new ListNode<>(element);
            last = head;
        }
        size++;
    }
    /**
     * This method adds a new object in the last position of the list, every time.
     * @param element Corresponds to the item that is entering the list.
     */
    public void addLast(T element) {
        if (head == null) {
            addFirst(element);
        } else {
            ListNode<T> newLast = new ListNode<T>(element, null, last);

            last.setNext(newLast);

            last = newLast;
            size++;
        }
    }

    /**
     * Returns an object in the index i of the list.
     * @param i The position of the object in the list.
     * @return Returns the
     */
    public T getElement(int i) {
        if (head == null) {
            return null;
        } else {
            ListNode<T> temp = head;
            for (int count = 0; count < i && temp.getNext() != null; ++count) {
                temp = temp.getNext();
            }
            return temp.getValue();
        }
    }

    /**
     * Takes the list to its initial state.
     */
    public void reset(){
        head = null;
        size = 0;
    }

    /**
     * Deletes all the elements that were requested to delete.
     * @param element It's the element that will be deleted from the list.
     */
    public void deleteElement(T element){
        ListNode<T> temp = head;
        for (int count = 0; count < size; ++count) {
            if (temp.getValue().equals(element)){
                if (count == 0){
                    head = temp.getNext();
                    temp= head;
                    count=-1;
                }
                else if (count==size-1) {
                    temp.getPrev().setNext(null);
                    last = temp.getPrev();
                }
                else{
                    temp.getNext().setPrev(temp.getPrev());
                    temp.getPrev().setNext(temp.getNext());
                    temp =  temp.getNext();
                    count= count-1;
                }
                size--;
            }
            else {
                temp = temp.getNext();
            }
        }
    }

    /**
     * Changes the value of the node in the position "i".
     * @param i The position of the node.
     * @param element The new value of the node.
     */
    public void replace(int i, T element){
        if (size>0){
            ListNode<T> temp = head;
            for (int count = 0; count < i && temp.getNext() != null; ++count) {
                temp = temp.getNext();
            }
            temp.setValue(element);
        }

    }

    public int getSize() {
        return size;
    }
}
