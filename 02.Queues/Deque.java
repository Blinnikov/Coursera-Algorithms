/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node first, last;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        // 6.a) Performance requirements.  Your deque implementation must support each deque operation (including construction) in constant worst-case time.
        // A deque containing n items must use at most 48n + 192 bytes of memory.
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        checkArguments(item);

        Node oldFirst = this.first;
        this.first = new Node();
        this.first.item = item;
        this.first.next = oldFirst;
        this.first.prev = null;

        if (oldFirst != null) {
            oldFirst.prev = this.first;
        }

        if (this.size() == 0) {
            this.last = this.first;
        }

        this.size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        checkArguments(item);

        Node oldLast = this.last;
        this.last = new Node();
        this.last.item = item;
        this.last.next = null;
        this.last.prev = oldLast;

        if (oldLast != null) {
            oldLast.next = this.last;
        }

        if (this.size() == 0) {
            this.first = this.last;
        }

        this.size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        checkQueue();

        Node oldFirst = this.first;
        this.first = oldFirst.next;
        this.size--;

        if (this.first == null) {
            this.last = null;
        }
        else {
            this.first.prev = null;
        }

        return oldFirst.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        checkQueue();

        Node oldLast = this.last;
        this.last = oldLast.prev;
        if (this.last == null) {
            this.first = null;
        }
        else {
            this.last.next = null;
        }

        this.size--;

        return oldLast.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item result = current.item;
            current = current.next;
            return result;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        StdOut.printf("Queue size is %s\n", deque.size());
        StdOut.println("Adding 01");
        deque.addFirst("01");
        StdOut.printf("Queue size is %s\n", deque.size());
        StdOut.println("Adding 02");
        deque.addLast("02");
        StdOut.printf("Queue size is %s\n", deque.size());

        StdOut.println("Printing the queue");
        for (String s : deque) {
            StdOut.println(s);
        }

        StdOut.println("Removing first: ");
        StdOut.println(deque.removeFirst());
        StdOut.printf("Queue size is %s\n", deque.size());
        StdOut.println("Removing last: ");
        StdOut.println(deque.removeLast());
        StdOut.printf("Queue size is %s\n", deque.size());

        StdOut.printf("Queue is empty - %s\n", deque.isEmpty());

        StdOut.println("This shouldn't print an empty queue");

        for (String s : deque) {
            StdOut.println(s);
        }

        deque.addLast("02");
        deque.addFirst("01");

        for (String s : deque) {
            StdOut.println(s);
        }

        // 5) Unit testing.  Your main() method must call directly every public constructor and method
        // to help verify that they work as prescribed (e.g., by printing results to standard output).
    }

    private void checkArguments(Item item) {
        if (item == null) {
            // 1)
            throw new IllegalArgumentException("Item cannot be null");
        }
    }

    private void checkQueue() {
        if (this.isEmpty()) {
            // 2)
            throw new NoSuchElementException("The deque is empty");
        }
    }
}
