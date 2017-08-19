import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints { 
    private final ArrayList<LineSegment> ls;
        
    public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points 
        if (points.length == 0)
            throw new NullPointerException("Empty array");
        ls = new ArrayList<LineSegment>();
        int n = points.length;
        Point[] sortedPoints = Arrays.copyOf(points, n);
        Arrays.sort(sortedPoints); // java.lang.NullPointerException will be thrown either the argument to the constructor is null or if any point in the array is null.
        for (int i = 1; i < n; ++i)
            if (sortedPoints[i-1].compareTo(sortedPoints[i]) == 0)
                throw new IllegalArgumentException("Repeated point");
        for (int i = 0; i < n-3; ++i) {
            for (int j = i+1; j < n-2; ++j) {
                for (int k = j+1; k < n-1; ++k) {
                    double slope = sortedPoints[i].slopeTo(sortedPoints[j]);
                    if (sortedPoints[i].slopeTo(sortedPoints[k]) != slope)
                        continue;
                    for (int l = k+1; l < n; ++l) {
                        if (sortedPoints[i].slopeTo(sortedPoints[l]) != slope) 
                            continue;
                        Point[] collinear = {sortedPoints[i], sortedPoints[j], sortedPoints[k], sortedPoints[l]};
                        Arrays.sort(collinear);
                        ls.add(new LineSegment(collinear[0], collinear[3]));
                    }
                }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        System.out.println(collinear.numberOfSegments());        
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}