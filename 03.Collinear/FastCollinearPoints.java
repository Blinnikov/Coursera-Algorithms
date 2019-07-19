/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private int numberOfSegments;
    private final ArrayList<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        checkArguments(points);

        this.numberOfSegments = 0;
        this.segments = new ArrayList<>();

        this.findCollinearPoint(points.clone());
    }

    // the number of line segments
    public int numberOfSegments() {
        return this.numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        return this.segments.toArray(new LineSegment[this.numberOfSegments()]);
    }

    private void findCollinearPoint(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            Arrays.sort(points);
            Arrays.sort(points, points[i].slopeOrder());

            int segmentStart = 1;
            int segmentEnd = 2;
            while (segmentEnd < points.length) {

                while (segmentEnd < points.length
                        && points[0].slopeTo(points[segmentStart])
                        == points[0].slopeTo(points[segmentEnd])) {
                    segmentEnd++;
                }

                if (segmentEnd - segmentStart > 2
                        && points[0].compareTo(points[segmentStart]) < 0) {
                    LineSegment segment = new LineSegment(points[0], points[segmentEnd - 1]);
                    this.numberOfSegments++;
                    this.segments.add(segment);
                }

                segmentStart = segmentEnd;
                segmentEnd++;
            }

        }
    }

    private void checkArguments(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points argument cannot be null");
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("points argument cannot contain null points");
            }
        }

        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException(
                            "points argument cannot contain duplicated points");
                }
            }
        }
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
