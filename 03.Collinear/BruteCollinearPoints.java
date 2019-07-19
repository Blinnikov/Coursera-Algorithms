/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private int numberOfSegments;
    private final ArrayList<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        checkArguments(points);

        this.numberOfSegments = 0;
        this.segments = new ArrayList<>();

        this.findCollinearPoint(points);
    }

    // the number of line segments
    public int numberOfSegments() {
        return this.numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        return this.segments.toArray(new LineSegment[this.numberOfSegments()]);
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

    private void findCollinearPoint(Point[] points) {
        for (int p = 0; p < points.length; p++) {
            for (int q = p + 1; q < points.length; q++) {
                for (int r = q + 1; r < points.length; r++) {
                    for (int s = r + 1; s < points.length; s++) {
                        if (p == q || p == r || p == s || q == r || q == s || r == s) {
                            continue;
                        }

                        if (points[p].slopeTo(points[q]) == points[q].slopeTo(points[r])
                                && points[q].slopeTo(points[r]) == points[r].slopeTo(points[s])) {
                            this.numberOfSegments++;
                            Point[] candidates = { points[p], points[q], points[r], points[s] };
                            Arrays.sort(candidates);
                            LineSegment lineSegment = new LineSegment(candidates[0], candidates[3]);
                            this.segments.add(lineSegment);
                        }
                    }
                }
            }
        }
    }
}
