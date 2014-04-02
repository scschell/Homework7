import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Treap implentation of ordered map.
 * @author Edward Schembor < eschemb1@jhu.edu >
 * @author Sayge Schell < sschell3@jhu.edu >
 * Data Structures 600.226, Assignment 7
 * March 4th, 2014
 * @param <K>
 *            Type for keys.
 * @param <V>
 *            Type for values.
 */
public class TreapMap<K extends Comparable<? super K>, V>
    implements OrderedMap<K, V> {
    // Node class
    private class Node {
        Node left, right;
        K key;
        V value;
        int priority;;
        /**
         * Node Constructor.
         * @param k
         *            Key
         * @param v
         *            Value
         */
        Node(K k, V v) {
            // left and right default to null
            this.key = k;
            this.value = v;
            this.priority = rand.nextInt(); //random priority
        }
    }

    private Node root;
    private int size; //number of elements in treap
    private StringBuilder stringBuilder;
    private Random rand = new Random();

    @Override
    // Code taken from BinarySearchTreeMap.java.
    public int size() {
        return this.size;
    }

    /**
     * Returns the node for the given key. Code taken from
     * BinarySearchTreeMap.java.
     * @param k
     *            key
     * @return found node that has the given key, returns null if no node is
     *         found
     * @throws IllegalArgumentException
     *             if passed a null key
     */
    private Node find(K k) {
        if (k == null) {
            throw new IllegalArgumentException("cannot handle null key");
        }
        Node n = this.root;
        while (n != null) {
            int cmp = k.compareTo(n.key);
            if (cmp < 0) {
                n = n.left;
            } else if (cmp > 0) {
                n = n.right;
            } else {
                return n;
            }
        }
        return null;
    }

    @Override
    // Code taken from BinarySearchTreeMap.java.
    public boolean has(K k) {
        return this.find(k) != null;
    }

    /**
     * Returns node for given key, throwing an exception if the key is not in
     * the tree. Taken from BinarySearchTreeMap.java.
     * @param k
     *            key
     * @return found node with given key
     * @throws IllegalArgumentException
     *             if node does not exist in tree
     */
    private Node findForSure(K k) {
        Node n = this.find(k);
        if (n == null) {
            throw new IllegalArgumentException("cannot find key " + k);
        }
        return n;
    }

    @Override
    // Code taken from BinarySearchTreeMap.java.
    public void put(K k, V v) {
        Node n = this.findForSure(k);
        n.value = v;
    }

    @Override
    // Code taken from BinarySearchTreeMap.java.
    public V get(K k) {
        Node n = this.findForSure(k);
        return n.value;
    }

    /**
     * Rotates the given node left.
     * @param n
     *            node to be rotated
     * @return the new root of the rotated node
     */
    private Node rotateLeft(Node n) {
        Node temp = n.right; // save larger child
        // detach larger child and preserve temp's smaller child
        n.right = temp.left;
        // detach temp's smaller child and attach old root to temp
        temp.left = n;
        return temp; // return rotated node's new root
    }

    /**
     * Rotates the given node right.
     * @param n
     *            node to be rotated
     * @return the new root of the rotated node
     */
    private Node rotateRight(Node n) {
        Node temp = n.left; // save smaller child
        // detach smaller child and preserve temp's larger child
        n.left = temp.right;
        // detach temp's larger child and attach old root to temp
        temp.right = n;
        return temp; // return rotated node's new root
    }
    /**
     * Balances nodes by calling rotations on them. Code adapted from
     * lecture notes.
     * @param n
     *            node to balance
     * @return balanced node
     */
    private Node balance(Node n) {
        if ((n.right != null) && (n.priority < n.right.priority)) {
            n = this.rotateLeft(n); //only rotate left if left child exists
        } else if ((n.left != null) && (n.priority < n.left.priority)) {
            n = this.rotateRight(n); //only rotate right if right child exists
        }
        return n;
    }

    /**
     * Inserts node of a given key and value into a subtree rooted at given
     * node. Code is a modified version of BinarySearchTreeMap.java.
     * @param n
     *            root node
     * @param k
     *            key
     * @param v
     *            value
     * @return node with subtree added
     */
    private Node insert(Node n, K k, V v) {
        if (n == null) {
            return new Node(k, v);
        }

        int cmp = k.compareTo(n.key);
        if (cmp < 0) {
            n.left = this.insert(n.left, k, v);
        } else if (cmp > 0) {
            n.right = this.insert(n.right, k, v);
        } else {
            throw new IllegalArgumentException("duplicate key " + k);
        }

        n = this.balance(n); //updates balance
        return n;
    }

    @Override
    // Code taken from BinarySearchTreeMap.java.
    public void insert(K k, V v) {
        if (k == null) {
            throw new IllegalArgumentException("cannot handle null key");
        }
        this.root = this.insert(this.root, k, v);
        this.size += 1;
    }

    /**
     * Returns node with maximum key in subtree rooted at given node. Code taken
     * from BinarySearchTreeMap.java.
     * @param n
     *            root node
     * @return node with maximum key
     */
    private Node max(Node n) {
        while (n.right != null) {
            n = n.right;
        }
        return n;
    }

    /**
     * Removes node with given key from subtree rooted at given node. Modified
     * code taken from BinarySearchTreeMap.java.
     * @param n
     *            root node
     * @param k
     *            key
     * @return subtree with given key missing
     */
    private Node remove(Node n, K k) {
        if (n == null) {
            throw new IllegalArgumentException("cannot find key " + k);
        }

        int cmp = k.compareTo(n.key);
        if (cmp < 0) {
            n.left = this.remove(n.left, k);
        } else if (cmp > 0) {
            n.right = this.remove(n.right, k);
        } else {
            if ((n.right != null) && (n.left != null)) {
                //rotates in an arbitrary direction if node has children
                n = this.rotateLeft(n);
            } else {
                //once there are 1 or 0 children left, node is removed
                n = this.remove(n);
            }
            return n;
        }
        return n;
    }

    /**
     * Removes given node and returns the remaining tree. Modified code
     * taken from BinarySearchTree.java.
     * @param n
     *            node to remove
     * @return the tree with the removed node
     */
    private Node remove(Node n) {
        // 0 and 1 child
        if (n.left == null) {
            return n.right;
        }
        if (n.right == null) {
            return n.left;
        }

        return n;
    }

    @Override
    // Code taken from BinarySearchTree.java.
    public void remove(K k) {
        if (k == null) {
            throw new IllegalArgumentException("cannot handle null key");
        }
        this.root = this.remove(this.root, k);
        this.size -= 1;
    }

    /**
     * Adds keys from subtree rooted at given node. Code taken
     * from BinarySearchTree.java.
     * @param n
     *            root node
     * @param keys
     *            list of keys
     */
    private void iteratorHelper(Node n, List<K> keys) {
        if (n == null) { return; }
        this.iteratorHelper(n.left, keys);
        keys.add(n.key);
        this.iteratorHelper(n.right, keys);
    }

    @Override
    // Code taken from BinarySearchTree.java.
    public Iterator<K> iterator() {
        List<K> keys = new ArrayList<K>();
        this.iteratorHelper(this.root, keys);
        return keys.iterator();
    }

    /**
     * Creates and resets StringBuilder. Code taken from
     * BinarySearchTree.java.
     */
    private void setupStringBuilder() {
        if (this.stringBuilder == null) {
            this.stringBuilder = new StringBuilder();
        } else {
            this.stringBuilder.setLength(0);
        }
    }

    /**
     * Appends string representations of keys and value from subtree rooted at
     * given node. Code taken from BinarySearchTree.java.
     * @param n
     *            root node
     * @param s
     *            StringBuilder
     */
    private void toStringHelper(Node n, StringBuilder s) {
        if (n == null) { return; }
        this.toStringHelper(n.left, s);
        s.append(n.key);
        s.append(": ");
        s.append(n.value);
        s.append(", ");
        this.toStringHelper(n.right, s);
    }

    @Override
    // Code taken from BinarySearchTree.java.
    public String toString() {
        this.setupStringBuilder();
        this.stringBuilder.append("{");

        this.toStringHelper(this.root, this.stringBuilder);

        int length = this.stringBuilder.length();
        if (length > 1) {
            // If anything was appended at all, get rid of
            // the last ", " the toStringHelper put in.
            this.stringBuilder.setLength(length - 2);
        }
        this.stringBuilder.append("}");

        return this.stringBuilder.toString();
    }
}
