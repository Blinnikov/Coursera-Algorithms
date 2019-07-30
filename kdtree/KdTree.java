/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
    private Node root;
    private int n;

    // construct an empty set of points
    public KdTree() {
        this.n = 0;
        this.root = null;
    }

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p) {
            this.p = p;
        }
    }

    // is the set empty?
    public boolean isEmpty() {
        return this.root == null;
    }

    // number of points in the set
    public int size() {
        return this.n;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        this.root = insert(this.root, p, true);
    }

    private Node insert(Node node, Point2D point, boolean isVertical) {
        if (node == null) {
            this.n++;
            // TODO: Set also rect
            return new Node(point);
        }

        if (node.p.compareTo(point) == 0) {
            return node;
        }

        double cmp = isVertical ? point.x() - node.p.x() : point.y() - node.p.y();
        if (cmp < 0) node.lb = insert(node.lb, point, !isVertical);
        else node.rt = insert(node.rt, point, !isVertical);
        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return get(this.root, p, true) != null;
    }

    private Node get(Node node, Point2D point, boolean isVertical) {
        if (node == null) return null;

        if (node.p.compareTo(point) == 0) {
            return node;
        }

        double cmp = isVertical ? point.x() - node.p.x() : point.y() - node.p.y();
        if (cmp < 0) return get(node.lb, point, !isVertical);
        else return get(node.rt, point, !isVertical);
    }

    // draw all points to standard draw
    public void draw() {

    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        return null;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        return p;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
