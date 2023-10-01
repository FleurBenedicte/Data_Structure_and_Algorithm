/**
 * @author Baile Benedicte
 * @version 1.0.0
 *          11/09/2022
 */

public class MyTextEditor implements SimpleTextEditor {

    private final int max = 25; // create a max size for the array
    private int cursor = -1; // cursor is set to be -1
    public ArraySequence<String> elements = new ArraySequence<String>(max); // create a new ArraySequence with size max

    public MyTextEditor() {
        cursor = -1;
    }

    public MyTextEditor(int start) {
        cursor = start; // cursor is set equal to int value that was passed in
    }

    /**
     * Returns true if the text is completely empty (and cursor is at line -1).
     * 
     * @return true if the text is empty and false otherwise
     */
    public boolean isEmpty() {
        if (elements.size() == 0) { // check if list is empty
            return true;
        }
        return false;
    }

    /**
     * Returns the current number of lines of text.
     * 
     * @return the current number of lines
     */
    public int size() {
        return (int) elements.size(); // call size to get the number of lines and return it
    }

    /**
     * Returns true if the cursor is at the last line in the text or the text
     * is empty.
     * 
     * @return true if the cursor is at the last line and false otherwise.
     */
    public boolean isCursorAtLastLine() {
        if (elements.last() == elements.atIndex(cursor)) { // check if the current line is the same as the lass line
            return true;
        }
        return false;
    }

    /**
     * Sets the cursor to be the text line after its current position.
     * 
     * @throws IndexOutOfBoundsException if the current line is size()-1
     */
    public void cursorDown() throws IndexOutOfBoundsException {
        elements.atIndex(cursor + 1); // call atIndex to retrun the position of the element at the specified index
        cursor += 1; // increment cursor by 1

    }

    /**
     * Sets the cursor to be the text line before its current position.
     * 
     * @throws IndexOutOfBoundsException if the current line is 0
     */
    public void cursorUp() throws IndexOutOfBoundsException {
        elements.atIndex(cursor - 1); // call atIndex to retrun the position of the element at the specified index
        cursor -= 1; // decrement cursor by 1
    }

    /**
     * Returns the string at the current cursor
     *
     * @return the string at the current cursor
     */
    public String getAtCursor() {
        return elements.get(cursor); // calls get and pass the cursor number to return the string stored at that
                                     // index
    }

    /**
     * Sets the cursor to be the line ranked i (the first line is line 0).
     * 
     * @param line The target line number.
     * @throws IndexOutOfBoundsException if the index is negative or greater
     *                                   than size()-1
     */
    public void moveCursorToLine(int lineNum) throws IndexOutOfBoundsException {
        elements.atIndex(lineNum); // call atIndex to retrun the position of the element at the specified index
        cursor = lineNum; // set cursor equal to the new index passed in
    }

    /**
     * Returns the line number (rank) of the current cursor line.
     * 
     * @return the line number (rank) where the current cursor is
     */
    public int cursorLineNum() {
        return cursor;
    }

    /**
     * Inserts a given string in the line after the current cursor, moving the
     * cursor to the line inserted.
     * 
     * @param insertion The string to be inserted.
     */
    public void insertAfterCursor(String insertion) {
        elements.add((cursor + 1), insertion); // add new string to the list at position cursor+1
        cursorDown(); // call cursorDow to move the cursor to the next line
    }

    /**
     * Inserts the given string in the line before the current cursor, moving the
     * cursor to the line inserted.
     * 
     * @param insertion The string to be inserted.
     */
    public void insertBeforeCursor(String insertion) {
        elements.add((cursor - 1), insertion); // add new string to the list at position cursor-1
        cursorUp(); // call cursorUp to move the cursor to the line before
    }

    /**
     * Replaces the string at the current cursor with the given string, keeping
     * the cursor at this line.
     * 
     * @param replacement The string to be inserted.
     */
    public void replaceAtCursor(String replacement) {
        elements.set(cursor, replacement); // calls set to replace the string at the current cursor woth a new one
    }

    /**
     * Removes the entire line at the current cursor, setting the cursor to now
     * be the position of the next line, unless the cursor was the last line,
     * in which case the cursor should move to the new last line.
     */
    public void removeAtCursor() {
        elements.remove(cursor); // calls remove to remove the line at the current cursor
        if (isCursorAtLastLine() == true) { // check if the cursor is that the last line
            cursorUp(); // move cursor to the line before
        }
    }

    /**
     * Return the entire array transform into a string
     * 
     * @return the entire array transform into a string
     */
    public String toString() {
        String result = ""; // create an empty string
        for (int i = 0; i <= size() - 1; i++) { // loop through the entire array Sequence
            result += elements.get(i) + "\n"; // add every new string to result concatenated with a new line
        }
        return result.trim(); // call trim to trim out the last new line and return result.
    }

    /**
     * Prints the entire text to the console.
     * 
     */
    public void print() {
        System.out.print(toString()); // calls print and pass in the strings that was formed to print out
    }
}
