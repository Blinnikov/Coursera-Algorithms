/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

public class PointSET {
    private SET<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        this.points = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return this.points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return this.points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("point is null");
        }

        if (this.contains(p)) {
            return;
        }

        this.points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("point is null");
        }

        return this.points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {

    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("rect is null");
        }

        ArrayList<Point2D> result = new ArrayList<>();

        for (Point2D point : this.points) {
            if (rect.contains(point)) {
                result.add(point);
            }
        }

        return result;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("point is null");
        }

        double minDistance = 1.0;
        Point2D result = null;

        for (Point2D point : this.points) {
            double currentDistance = p.distanceTo(point);
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                result = point;
            }
        }

        return result;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
