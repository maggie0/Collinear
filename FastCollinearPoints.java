//import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints { 
    private final ArrayList<LineSegment> ls = new ArrayList<LineSegment>();
    
    public FastCollinearPoints(Point[] points) { // finds all line segments containing 4 or more points 
        if (points == null)
            throw new NullPointerException("Empty array");
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints); // java.lang.NullPointerException will be thrown 
//        either the argument to the constructor is null or if any point in the array is null.
        int n = points.length;
        for (int i = 1; i < n; ++i)
            if (sortedPoints[i-1].compareTo(sortedPoints[i]) == 0)
                throw new IllegalArgumentException("Repeated point");         
        for (int i = 0; i < n-3; ++i) {
            Arrays.sort(sortedPoints);
            // This sort is guaranteed to be stable: equal elements will not be reordered as a result of the sort.
            Arrays.sort(sortedPoints, sortedPoints[i].slopeOrder()); 
            for (int first = 1, last = 2; last < n; ++last) {
                while (last < n && 
                       Double.compare(sortedPoints[0].slopeTo(sortedPoints[first]), 
                                      sortedPoints[0].slopeTo(sortedPoints[last])) == 0) {
                    ++last;
                }
                if (last-first >= 3 && sortedPoints[0].compareTo(sortedPoints[first]) < 0)
                    ls.add(new LineSegment(sortedPoints[0], sortedPoints[last-1]));
                first = last;
            }
        }
    }
    
    public int numberOfSegments() { // the number of line segments 
        return ls.size();        
    }
    
    public LineSegment[] segments() { // the line segments 
        return ls.toArray(new LineSegment[ls.size()]);
    }
    
    public static void main(String[] args) {
//        Point p1 = new Point(1, 1);
//        Point p2 = new Point(2, 2);
//        Point p3 = new Point(3, 3);
//        Point p4 = new Point(4, 4);
//        Point p5 = new Point(5, 5);
//        Point[] points = {p1, p2, p3, p4, p5};
        
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
        System.out.println(collinear.numberOfSegments());   
        LineSegment[] ls = collinear.segments();
        for (LineSegment segment : ls) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}