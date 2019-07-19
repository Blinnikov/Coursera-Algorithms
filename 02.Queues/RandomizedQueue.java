/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        Item[] array = (Item[]) new Object[2];
        this.queue = array;
        this.size = 0;
        // Performance requirements.  Your randomized queue implementation must support
        // each randomized queue operation (besides creating an iterator) in constant amortized time.
        // That is, any intermixed sequence of m randomized queue operations (starting from an empty queue)
        // must take at most cm steps in the worst case,
        // for some constant c.
        // A randomized queue containing n items must use at most 48n + 192 bytes of memory.
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null items are prohibited");
        }

        this.queue[this.size++] = item;
        if (this.size == this.queue.length) {
            this.resize(this.size * 2);
        }
    }

    // remove and return a random item
    public Item dequeue() {
        this.checkQueue();

        // StdRandom.shuffle(this.queue, 0, this.size);
        // int lastItemIndex = this.size - 1;
        // Item item = this.queue[lastItemIndex];
        // this.queue[lastItemIndex] = null;
        // this.size--;

        int lastItemIndex = this.size - 1;
        int index = StdRandom.uniform(0, this.size);
        Item item = this.queue[index];
        if (index != lastItemIndex) {
            this.queue[index] = this.queue[lastItemIndex];
        }

        this.queue[lastItemIndex] = null;
        this.size--;

        if (this.size() > 0 && this.size() == this.queue.length / 4) {
            this.resize(this.queue.length / 2);
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        this.checkQueue();

        int index = StdRandom.uniform(0, this.size);
        return this.queue[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        // 1) Iterator.  Each iterator must return the items in uniformly random order.
        // The order of two or more iterators to the same randomized queue must be mutually independent;
        // each iterator must maintain its own random order.

        // CC 3)
        // Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator when there are no more items to return.

        // CC 4)
        // Throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator.

        // Additionally, your iterator implementation must support operations next() and hasNext()
        // in constant worst-case time; and construction in linear time;
        // you may (and will need to) use a linear amount of extra memory per iterator.
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        private int current;
        private final Item[] shuffledArray;

        public QueueIterator() {
            this.current = 0;
            Item[] array = (Item[]) new Object[size];
            this.shuffledArray = array;
            for (int i = 0; i < size; i++) {
                this.shuffledArray[i] = queue[i];
            }

            StdRandom.shuffle(this.shuffledArray, 0, size);
        }

        public boolean hasNext() {
            return this.current < size;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            return this.shuffledArray[current++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        // Unit testing.  Your main() method must call directly every public constructor and method
        // to verify that they work as prescribed (e.g., by printing results to standard output).
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        StdOut.printf("Queue size is - %s\n", queue.size());
        StdOut.printf("Queue is empty - %s\n", queue.isEmpty());
        StdOut.println("Adding 001");
        queue.enqueue("001");
        StdOut.printf("Queue size is - %s\n", queue.size());
        StdOut.printf("Queue is empty - %s\n", queue.isEmpty());
        StdOut.println("Adding 002");
        queue.enqueue("002");
        StdOut.printf("Queue size is - %s\n", queue.size());
        StdOut.printf("Queue is empty - %s\n", queue.isEmpty());
        StdOut.println("Adding 003");
        queue.enqueue("003");
        StdOut.printf("Queue size is - %s\n", queue.size());
        StdOut.printf("Queue is empty - %s\n", queue.isEmpty());
        StdOut.println("Adding 004");
        queue.enqueue("004");
        StdOut.printf("Queue size is - %s\n", queue.size());
        StdOut.printf("Queue is empty - %s\n", queue.isEmpty());

        for (String first : queue) {
            StdOut.printf("%s: ", first);
            for (String second : queue) {
                StdOut.printf("%s ", second);
            }
            StdOut.println();
        }

        StdOut.println("Some samples: ");
        StdOut.println(queue.sample());
        StdOut.println(queue.sample());
        StdOut.println(queue.sample());
        StdOut.println(queue.sample());

        StdOut.println("Dequeing: ");
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        StdOut.printf("Queue size is - %s\n", queue.size());
        StdOut.printf("Queue is empty - %s\n", queue.isEmpty());
    }

    private void checkQueue() {
        if (this.isEmpty()) {
            // CC 2)
            throw new NoSuchElementException("Queue is empty");
        }
    }

    private void resize(int capacity) {
        assert capacity >= this.size;

        // textbook implementation
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < this.size; i++) {
            temp[i] = this.queue[i];
        }
        this.queue = temp;

        // alternative implementation
        // a = java.util.Arrays.copyOf(a, capacity);
    }
}
