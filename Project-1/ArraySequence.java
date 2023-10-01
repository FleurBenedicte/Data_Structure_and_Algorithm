/**
 * @author Baile Benedicte
 * @version 1.0.0
 *          11/09/2022
 */

public class ArraySequence<E> implements Sequence<E> {

    // create an iner class named SequenceNode that implement the interface
    // Position<E>
    public class SequenceNode<E> implements Position<E> {

        /** Reference to the element stored. */
        private final E element; // create an element of type E
        private final int NodeIndex; // create variable to hold index of the node

        /**
         * Creates a SequenceNode with the given element and the index of the node.
         *
         * @param element   the element to be stored
         * @param NodeIndex index of the element
         */
        public SequenceNode(int NodeIndex, E element) {
            this.element = element; // set element equal to value that has been passed in
            this.NodeIndex = NodeIndex; // set nod of index to be the integer that was passed in
        }

        /**
         * Returns the element stored at the node.
         * 
         * @return the stored element
         * @throws IllegalStateException if SequenceNode not currently linked to others
         */
        public E getElement() throws IllegalStateException {
            if (element == null) { // check if element is null
                throw new IllegalStateException("Position no longer valid"); // throw invalid position error
            }
            return element;
        }

        /**
         * Returns the node of the element (or null if no such node).
         * 
         * @return the node of the element stored
         */
        public int getNodeIndex() {
            return NodeIndex;
        }

    }

    // END OF SequenceNode

    private final ArrayList<SequenceNode<E>> items; // create a and Array list to store elements of type SequenceNode

    public ArraySequence() {
        items = new ArrayList<SequenceNode<E>>(); // initialise list with default array size
    }

    public ArraySequence(int size) {
        items = new ArrayList<SequenceNode<E>>(size); // initialise list with default array size that has been passed in
    }

    /**
     * Returns the number of elements in the list.
     * 
     * @return number of elements in the list
     */
    public int size() {
        return items.size(); // calls size from arraylist class to get size of the list
    }

    /**
     * Tests whether the array list is empty.
     * 
     * @return true if the array list is empty, false otherwise
     */
    public boolean isEmpty() {
        return items.size() == 0; // return size of the list is equal 0
    }

    // ArrayList methods

    /**
     * Returns (but does not remove) the element at index i.
     * 
     * @param index The index of the element to return.
     * @return The element at the specified index.
     * @throws IndexOutOfBoundsException If the index is negative or greater than
     *                                   size()-1.
     */
    public E get(int index) throws IndexOutOfBoundsException {
        return items.get(index).getElement(); // calls get to pass the index of the element into it and then call
                                              // getElement to get the element at that index.
    }

    /**
     * Replaces the element at the specified index, and returns the element
     * previously stored.
     * 
     * @param index   The index of the element to replace.
     * @param element the new element to be stored.
     * @return the previously stored element.
     * @throws IndexOutOfBoundsException If the index is negative or greater
     *                                   than size()-1.
     */
    public E set(int index, E element) throws IndexOutOfBoundsException {
        SequenceNode<E> newElement = new SequenceNode(index, element); // create a variable of type Sequence node to add
                                                                       // it to the array list
        return items.set(index, newElement).getElement(); // calls set and pass index and element and return the
                                                          // previously stored element
    }

    /**
     * Inserts the given element at the specified index of the list, shifting
     * all subsequent elements in the list one position further to make room.
     * 
     * @param index   The index at which the new element should be stored.
     * @param element The new element to be stored.
     * @throws IndexOutOfBoundsException If the index is negative or greater
     *                                   than size().
     */
    public void add(int index, E element) throws IndexOutOfBoundsException {
        SequenceNode<E> elementAdd = new SequenceNode(index, element); // create a variable of type Sequence node to add
                                                                       // it to the array list
        items.add(index, elementAdd); // adding the new element added to the array list
        refreshIndex(index); // calls refresIndex() to update the Node indexes of the element after element
                             // was added
    }

    /**
     * Update all the Node indexes of the SequenceNode objects stored in the
     * arraylist
     * 
     * @param index the index at which the Node indexes start to be updated
     */
    private void refreshIndex(int index) {
        for (int i = index; i < size(); i++) { // loop through all elememt starting from the given index
            set(i, get(i)); // call set to change the Node index of the element
        }
    }

    /**
     * Removes and returns the element at the given index, shifting all
     * subsequent elements in the list one position closer to the front.
     * 
     * @param index The index of the element to be removed.
     * @return The element that had been stored at the given index.
     * @throws IndexOutOfBoundsException If the index is negative or greater than
     *                                   size()-1.
     */
    public E remove(int index) throws IndexOutOfBoundsException {
        E removed = (E) items.remove(index).getElement(); // calls remove and pass index to return the element that was
                                                          // removed from the
        // array list
        refreshIndex(index); // calls refresIndex() to update the Node indexes of the element after element
                             // was removed
        return removed;
    }

    // positionalList methods

    /**
     * Returns the first Position in the list.
     * 
     * @return the first Position in the list (or null, if empty).
     */
    public Position<E> first() {
        if (items.isEmpty()) { // check if the element in first position is null
            return null;
        }
        return atIndex(0); // calls atIndex and pass index = 0 to return first element
    }

    /**
     * Returns the last Position in the list.
     * 
     * @return the last Position in the list (or null, if empty).
     */
    public Position<E> last() {
        if (items.get(size() - 1) == null) { // check if the element in last position is null
            return null;
        }
        return atIndex(size() - 1); // calls atIndex and pass index = size()-1 to return last element
    }

    /**
     * Returns the Position immediately before the Position position.
     * 
     * @param position A Position of the list.
     * @return The Position of the preceding element (or null, if p is first).
     * @throws IllegalArgumentException if p is not a valid position for this list.
     */
    public Position<E> before(Position<E> position) throws IllegalArgumentException {
        return atIndex(indexOf(position) - 1); /// calls atIndex, pass an index and return the position of the preceding
                                               /// element in
        // array list
    }

    /**
     * Returns the Position immediately after Position p.
     * 
     * @param position A Position of the list.
     * @return The Position of the following element (or null, if p is last)
     * @throws IllegalArgumentException If position is not a valid position for this
     *                                  list.
     * 
     */
    public Position<E> after(Position<E> position) throws IllegalArgumentException {
        return atIndex(indexOf(position) + 1); // calls atIndex, pass an index and return the position of the next
                                               // element in
        // array list
    }

    /**
     * Inserts an element at the front of the list.
     * 
     * @param element the new element.
     * @return The Position representing the location of the new element.
     */
    public Position<E> addFirst(E element) {
        add(0, element); // add element of type E at the top of the list
        return atIndex(0); // calls atIndex, pass index= 0 and return the Position representing the
                           // location
        // of the new element
    }

    /**
     * Inserts an element at the back of the list.
     * 
     * @param element The new element.
     * @return The Position representing the location of the new element.
     */
    public Position<E> addLast(E element) {
        add(size(), element); // add element of type E at the back of the list
        return atIndex(size() - 1); // calls atIndex, pass size() and return the Position representing the location
        // of the new element
    }

    /**
     * Inserts an element immediately before the given Position.
     * 
     * @param position The Position before which the insertion takes place.
     * @param element  The new element.
     * @return The Position representing the location of the new element.
     * @throws IllegalArgumentException if p is not a valid position for this list.
     */
    public Position<E> addBefore(Position<E> position, E element) throws IllegalArgumentException {
        int index = indexOf(position); // create an integer that represent the node index of
                                       // the
                                       // position that is passed in
        add(index, element); // add new element casted into SequenceNode to the
                             // array list
        return atIndex(index); // calls atIndex, pass index and return the Position representing the location
                               // of the new element
    }

    /**
     * Inserts an element immediately after the given Position.
     * 
     * @param position The Position after which the insertion takes place.
     * @param element  The new element.
     * @return the Position representing the location of the new element
     * @throws IllegalArgumentException If position is not a valid position for this
     *                                  list.
     */
    public Position<E> addAfter(Position<E> position, E element) throws IllegalArgumentException {
        int index = indexOf(position) + 1; // create an integer that represent the node index of
                                           // the
                                           // position that is passed in
        add(index, element); // add new element casted into SequenceNode to the
                             // array list
        return atIndex(index); // calls atIndex, pass index+1 and return the Position representing the location
                               // of the new element
    }

    /**
     * Replaces the element stored at the given Position and returns the
     * replaced element.
     * 
     * @param position The Position of the element to be replaced.
     * @param element  The new element.
     * @return The replaced element.
     * @throws IllegalArgumentException If position is not a valid position for this
     *                                  list.
     */
    public E set(Position<E> position, E element) throws IllegalArgumentException {
        return set(indexOf(position), element); // call set to add new element to the list and return the
                                                // element that was replaced
    }

    /**
     * Removes the element stored at the given Position and returns it.
     * The given position is invalidated as a result.
     * 
     * @param position The Position of the element to be removed.
     * @return The removed element.
     * @throws IllegalArgumentException If position is not a valid position for this
     *                                  list.
     */
    public E remove(Position<E> position) throws IllegalArgumentException {
        return remove(indexOf(position)); // call remove and pass index to return the element that was removed
    }

    // Sequence ADT

    /**
     * Returns the position containing the element at the given index.
     * 
     * @param index The index of the element.
     * @return The position of the element at the specified index.
     * @throws IndexOutOfBoundsException if the index is negative or greater than
     *                                   size()-1
     */
    public Position<E> atIndex(int index) throws IndexOutOfBoundsException {
        return items.get(index); // calls get and pass index to get the position of element in list
    }

    /**
     * Returns the index of the element stored at the given Position.
     * 
     * @param position The Position of the element.
     * @return The index of the element at the specified Position.
     * @throws IllegalArgumentException if position is not a valid position for this
     *                                  list.
     */
    public int indexOf(Position<E> position) throws IllegalArgumentException {
        return ((SequenceNode) position).getNodeIndex(); // calls getNodeIndex to get the index of the element at the
                                                         // specified Position
    }

    /**
     * Return the element of the list in a form of a string
     * 
     * @return the entire array transform into a string
     */
    public String toString() {
        String result = ""; // create an empty string
        for (int i = 0; i <= size() - 1; i++) { // loop through the entire list
            result += items.get(i).getElement() + "\n"; // add every element to result concatenated with a new line
        }
        return result.trim(); // call trim to trim out the last new line and return result.
    }
}
