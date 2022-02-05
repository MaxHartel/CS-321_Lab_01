import java.util.NoSuchElementException;

/**
 * Double-linked node of a Two-Level Cache
 * @author maxhartel
 *
 * @param <T> Generic type
 */
class Cache<T> {
    private Node<T> head;
    private Node<T> tail;
    private int capacity;
    private int hits;
    private int accesses;
    private int size;


    /**
     * Initialize a new empty list
     */
    public Cache(int space) {
        head = null;
        tail = null;
        capacity = space;
        hits = 0;
        accesses = 0;
        size = 0;


    }

    public void addToFront(T element) {

        if(size == capacity){
            removeLast();
        }

        Node<T> newNode = new Node<T>(element);
        newNode.setNext(head);

        if (isEmpty()) {//special case: empty list
            tail = newNode;
        }else {//general case
            head.setPrevious(newNode);
        }

        head = newNode;

        size++;

    }


    private T removeLast() {
        if (isEmpty()) {//confirms that there are elements in the list to remove
            throw new NoSuchElementException();
        }
        T retVal = tail.getElement();

        //special case: one element list
        if (head == tail) {
            head = null;
            tail = null;

        } else {//general case: removes last element from multi-element list
            Node<T> newTail = tail.getPrevious();
            newTail.setNext(null);
            tail = newTail;
        }

        size--;

        return retVal;
    }

    private T removeFirst() {
        if (isEmpty()) {//confirms that there are elements in the list to remove
            throw new NoSuchElementException();
        }
        T retVal = head.getElement();

        //special case: one element list
        if (head == tail) {
            head = null;
            tail = null;

        } else {//general case: removes first element from multi-element list
            Node<T> newHead = head.getNext();
            newHead.setPrevious(null);
            head = newHead;
        }

        size--;

        return retVal;
    }

    /**
    moves the designated element to the top of the cache

    @param element the element to be moved
     */
    public void move(T element){
        remove(element);
        addToFront(element);

    }

    private T remove(T data) {

        Node<T> current = head;

        //special case: first element removal
        if (current.getElement().equals(data)) {
            return removeFirst();
        }

        //iterates through list to set "current" to the node before the correct element
        while (!current.getNext().getElement().equals(data)) {
            current = current.getNext();
        }

        //special case last element  removal
        if (current.getNext() == tail) {
            return removeLast();
        }

        //somewhere in the middle (general  case)
        //links the nodes before and after the target element to each other instead of the target
        T retVal = current.getNext().getElement();

        Node<T> afterDeleted = current.getNext().getNext();

        current.setNext(current.getNext().getNext());
        afterDeleted.setPrevious(current);

        size--;

        return retVal;
    }


    private int indexOf(T element) {
        Node<T> current = head;
        int currentIndex = 0;

        //iterates through list to set "currentIndex" to the node at the correct index
        while (current != null && !element.equals(current.getElement())) {
            current = current.getNext();
            currentIndex++;
        }
        if (current == null) {//sets currentIndex to -1 if element is not found in list
            currentIndex = -1;
        }
        return currentIndex;
    }

    public T contains(T target) {

        accesses ++;

        if (indexOf(target) > -1) {
            return target;
        } else {
            return null;
        }
    }

    private boolean isEmpty() {
        if (head == null) {
            return true;
        } else {
            return false;
        }
    }

    public double getHitRate(){
        double hitRate = ((double)hits)/accesses;

        return hitRate;

    }

    public void incrementHits(){
        hits++;
    }

    public double getMissRate(){
        double missRate = 1.0 - getHitRate();

        return missRate;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getAccesses() {
        return accesses;
    }

    public String toString() {
        StringBuilder retString = new StringBuilder();

        retString.append("[");

        Node<T> current = head;

        while (current.getNext() != null) {//adds a comma in between every list element
            retString.append(current.getNext());
            retString.append(", ");
            current = current.getNext();
        }

        if (!isEmpty()) {//confirms if there are elements in the list
            retString.delete(retString.length() - 2, retString.length());
        }

        retString.append("]");

        return retString.toString();
    }
}