import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Baile Benedicte
 * @version 1.0.0
 *          11/09/2022
 */

public class BaileBenedicte {

    /** The editor used to manipulate a document. */
    private MyTextEditor editor = new MyTextEditor(); // create an instance of MyTextEditor

    /**
     * Utility method to read a document into a String array where each line is a
     * separate string.
     * 
     * @param source The path to the source text file to read.
     * @return The lines of the read file.
     */
    public String[] readDocument(Path source) {
        try {
            // Reads in pure lines without messy \r\n ending characters.
            String[] lines = Files.readAllLines(source).toArray(String[]::new);
            scrub(lines);
            return lines;
        } catch (IOException exception) {
            System.err.format("Error: File '%s' could not be read in path.%n", source);
            exception.printStackTrace();
            System.exit(2);
        }

        return new String[] {};
    }

    /**
     * Simple utility method for printing a test comparison result.
     * 
     * @param result The test condition.
     * @param test   The description of the test.
     * @return Pass through the same result.
     */
    private boolean verify(boolean result, String test) {
        System.out.format("Test %s is %s.%n", test, result);
        return result;
    }

    /**
     * A utility method to dump all text in hex format to reveal hidden whitepace.
     * 
     * @param text The text to dump as hex.
     * @return The hex form of the given text.
     */
    private String toHex(String text) {
        return String.format("%040x", new java.math.BigInteger(1, text.getBytes()));
    }

    /**
     * Purge the read is files from trailing whitespace at the end of some of the
     * lines. Technically the editor could adjust hte lines to match the
     * whitespaces, but its easier to meet the proejct requirements by just trimming
     * and removing whitespace. Beware some of the lines have additional spaces
     * added between characters.
     * 
     * @param lines The document lines to trim.
     */
    private void scrub(String[] lines) {
        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].trim();
        }
    }

    /**
     * Join the array line with newlines to make it look like a file document.
     * 
     * @param content The lines of the document
     * @return The full document in a single string joined with newlines.
     */
    private String join(String[] content) {
        return String.join("\n", content).trim();
    }
    // Testing Methods

    /**
     * Some tests to verify the implementation of ArraySequence is valid.
     */
    private void testArraySequence() {

        ArraySequence<Character> as = new ArraySequence<>();

        verify(as.isEmpty(), "isEmpty()");

        as.addFirst(Character.valueOf('A'));
        as.add(1, Character.valueOf('B'));
        as.add(2, Character.valueOf('C'));

        verify(as.size() == 3, "size()");

        verify(as.first().getElement() == 'A', "first()");
        verify(as.last().getElement() == 'C', "last()");
        verify(as.before(as.atIndex(1)) == as.first(), "before()");
        verify(as.after(as.atIndex(1)) == as.last(), "after()");

        as.remove(2); // remove C (list is now A B)
        verify(as.last().getElement() == 'B', "last()"); // check if last element is now B
        as.addLast(Character.valueOf('D')); // add D at the back of the list
        verify(as.last().getElement() == 'D', "last()"); // check if last element is now D (list is now A B D)
        as.addBefore(as.atIndex(2), Character.valueOf('C')); // add C before the element at index 2 (list is now A B C
                                                             // D)
        verify(as.before(as.atIndex(3)).getElement() == 'C', "Element at index 2:"); // verify that C is at the right
                                                                                     // index/position
        verify(as.atIndex(3).getElement() == 'D', "last element/Element at index 3:"); // verify that D is at the right
                                                                                       // index/position (last element
                                                                                       // of the list)
        as.addAfter(as.atIndex(3), Character.valueOf('E')); // add E after the element at index 3 (list is now A B C D
                                                            // E)
        verify(as.after(as.atIndex(3)).getElement() == 'E', "Element at index 4:"); // verify that C is at the right
                                                                                    // index/position
        System.out.println("After inserting E: \n" + as.toString()); // print elements of the list
        as.remove(as.atIndex(2)); // remove element at index 2 (list is now A B D E)
        System.out.println("After removing C: \n" + as.toString()); // print elements of the list
        verify(as.last().getElement() == 'E', "Last element:"); // verify that E is at the right index/position (last
                                                                // element of the list)
    }

    /**
     * Load the document in the editor and verify the content matched the
     * initial.txt content.
     * 
     * @return true if expectations have been met.
     */
    private boolean verifyInitial() {

        String[] initialContent = readDocument(Path.of("test-data/initial.txt")); // read content of initial.txt
        for (String line : initialContent) { // loop over lines in from initial.txt
            editor.insertAfterCursor(line); // insert each line to the editor
        }
        String editorContent = editor.toString(); // call to string to transfrom the output from one string to mutiple
                                                  // by inserting new lines
        return verify(join(initialContent).equals(editorContent), "Editor loaded with correct content"); // verify if
                                                                                                         // the content
                                                                                                         // of
                                                                                                         // initial.txt
                                                                                                         // is the same
                                                                                                         // as
        // the one we downloaded and transformed.
    }

    /**
     * Modify the document in the editor to meet the middle.txt expectations.
     * 
     * @return true if expectations have been met.
     */
    private boolean verifyMiddle() {

        editor.moveCursorToLine(3); // move cursor to line 3
        editor.insertAfterCursor(""); // insert a blank line
        editor.cursorDown(); // move cursort to next line
        editor.cursorDown(); // move cursort to next line
        editor.cursorDown(); // move cursort to next line
        editor.cursorDown(); // move cursort to next line
        editor.moveCursorToLine(8); // move cursor to line 8
        editor.insertAfterCursor(""); // insert a blank line
        editor.cursorDown(); // move cursort to next line
        editor.cursorDown(); // move cursort to next line
        editor.cursorDown(); // move cursort to next line
        editor.insertAfterCursor(""); // insert a blank line

        for (int i = 0; i <= (editor.size() - 1); i++) { // loop over text editor
            String newString; // create string variable
            int index; // create an integer variable
            editor.moveCursorToLine(i); // move the cursor to the line with number i
            String newLine = editor.getAtCursor(); // string variable is set equal to the string at the current position
                                                   // of the cursor

            if (newLine.startsWith("Narnia...")) { // check if line starts with Narnia...
                newString = newLine.replaceAll("Narnia...", "Narnia... "); // replace all instances of Narnia...
                editor.replaceAtCursor(newString); // insert new string at the position of the current cursor.
            }

            String[] words = newLine.split(""); // split line and insert it into an array of Strings
            if (newLine.indexOf("boys") != -1) { // check if boys is in that specific line
                index = newLine.indexOf("boys"); // get the index/position of the word boys in the string
                newString = newLine.substring(0, index + 4) + " and girls" + newLine.substring(index + 4); // use the
                                                                                                           // method
                                                                                                           // substring()
                                                                                                           // to dived
                                                                                                           // the
                                                                                                           // current
                                                                                                           // line and
                // to insert and girls into the string
                editor.replaceAtCursor(newString); // insert new string at the position of the current cursor.
            }

            if (newLine.indexOf("Narnia...where owls") != -1) { // check if line contains Narnia...where owls
                newString = newLine.replace("Narnia...where owls", "Narnia... where professors"); // replace all
                                                                                                  // instances
                                                                                                  // Narnia...where owls
                editor.replaceAtCursor(newString);// insert new string at the position of the current cursor.
            }

            if (newLine.indexOf("humans") != -1) { // check if line contains humans
                newString = newLine.replace("humans", "students"); // replace all instances of that humans
                editor.replaceAtCursor(newString); // insert new string at the position of the current cursor.
            }

            if (newLine.indexOf("happen,the world of wicked dragons") != -1) { // check if line contains happen,the
                                                                               // world of wicked dragons
                newString = newLine.replace("happen,the world of wicked dragons", // replace all instances
                        "happen, the world of wicked deans");
                editor.replaceAtCursor(newString); // insert new string at the position of the current cursor.
            }
        }

        String middleContent = join(readDocument(Path.of("test-data/middle.txt"))); // read the containt of middle.txt
                                                                                    // into a string
        String editorContent = editor.toString(); // read modified version of editor into a string
        return verify(middleContent.equals(editorContent), // compare both strings to see if they are equal and return
                                                           // true or false.
                "Editor changed the document to match 'middle.txt' expectations");
    }

    /**
     * Modify the document in the editor to meet the final.txt expectations.
     * 
     * @return true if expectations have been met.
     */
    private boolean verifyFinal() {

        for (int i = 0; i <= (editor.size() - 1); i++) { // loop over text editor
            String newString; // create string variable
            int index; // create an integer variable
            editor.moveCursorToLine(i); // move the cursor to the line with number i
            String newLine = editor.getAtCursor(); // string variable is set equal to the string at the current position
                                                   // of the cursor

            if (newLine.indexOf("Lucy. Narnia") != -1) { // check if line contains Lucy. Narnia
                index = newLine.indexOf("Lucy. Narnia"); // get the index/position of the word boys in the string
                newString = newLine.substring(0, index + 5); // calls substring() to create a new string by dividing the
                                                             // original string at the given index
                editor.replaceAtCursor(newString); // insert the new string at the current position of the cursor
                editor.insertAfterCursor(""); // insert a new line
                String newString2 = newLine.substring(index + 6); // create a new string using subtring() and pass in
                                                                  // the index
                editor.insertAfterCursor(newString2); // insert the new string at the current position of the cursor
            }

            if (newLine.startsWith("Narnia...where")) { // check if line starts with Narnia...where
                newString = newLine.replace("Narnia...where", "Narnia... where"); // replace all instances of
                                                                                  // Narnia...where
                editor.replaceAtCursor(newString); // insert new string at the position of the current cursor.
            }

            if (newLine.indexOf("happen(and most oftan does)") != -1) { // check if line contains happen(and most oftan
                                                                        // does)
                newString = newLine.replace("happen(and most oftan does)", "happen (and most often does)"); // replace
                                                                                                            // all
                                                                                                            // instances
                                                                                                            // of
                                                                                                            // happen(and
                                                                                                            // most
                                                                                                            // oftan
                                                                                                            // does)
                editor.replaceAtCursor(newString); // insert new string at the position of the current cursor.
            }

            if (newLine.indexOf("professors") != -1) { // check if line contains professors
                newString = newLine.replace("professors", "owls"); // replace all instances of professors
                editor.replaceAtCursor(newString); // insert new string at the position of the current cursor.
            }

            if (newLine.indexOf("strong-or are they") != -1) { // check if line contains strong-or are they
                newString = newLine.replace("strong-or are they", "strong---or are they really"); // replace all
                                                                                                  // instances of
                                                                                                  // strong-or are they
                editor.replaceAtCursor(newString); // insert new string at the position of the current cursor.
            }

        }

        String finalContent = join(readDocument(Path.of("test-data/final.txt"))); // read the containt of final.txt into
                                                                                  // a string
        String editorContent = editor.toString(); // read modified version of editor into a string
        return verify(finalContent.equals(editorContent), // compare both strings to see if they are equal and return
                                                          // true or false.
                "Editor changed the document to match 'final.txt' expectations");

    }

    /**
     * The testing program entry point
     * 
     * @param args No arguements epxected. All are ignored.
     */
    public static void main(String... args) {
        BaileBenedicte editorDriver = new BaileBenedicte(); // create an instance of class BaileBenedicte

        editorDriver.testArraySequence(); // check that methods from arraySequence are working properly

        // To assist in debugging use the "diff" command line tool. Also, dump text to
        // hex to find hidden spaces.
        System.out.println(
                editorDriver.verifyInitial() && editorDriver.verifyMiddle() && editorDriver.verifyFinal()
                        ? "All tests passed."
                        : "Some tests failed.");
    }
}
