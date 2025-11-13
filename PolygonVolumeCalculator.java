import java.util.*;

public class PolygonVolumeCalculator {

    static class Point {
        double x, y;
        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    // Compute area and perimeter of the polygon
    static double[] polygonProperties(List<Point> points) {
        int n = points.size();
        if (n < 3) return new double[]{0.0, 0.0};

        double area = 0.0, perimeter = 0.0;

        for (int i = 0; i < n; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get((i + 1) % n);

            area += (p1.x * p2.y) - (p2.x * p1.y);
            perimeter += Math.hypot(p1.x - p2.x, p1.y - p2.y);
        }

        area = Math.abs(area) / 2.0;
        return new double[]{area, perimeter};
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        List<Point> points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            points.add(new Point(sc.nextDouble(), sc.nextDouble()));
        }

        double[] props = polygonProperties(points);
        double area = props[0];
        double perimeter = props[1];

        if (area == 0.0) {
            System.out.println("0.00");
            return;
        }

        // Find minimum edge length
        double minEdge = Double.POSITIVE_INFINITY;
        for (int i = 0; i < n; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get((i + 1) % n);
            double len = Math.hypot(p1.x - p2.x, p1.y - p2.y);
            minEdge = Math.min(minEdge, len);
        }

        // Max possible height
        double maxH = Math.max(0.0, (minEdge - 0.1) / 2.0);
        if (maxH < 0.1) {
            System.out.println("0.00");
            return;
        }

        // Compute best volume
        double maxVolume = 0.0;
        for (int i = 1; i <= (int)(maxH / 0.1); i++) {
            double h = i * 0.1;
            double baseArea = area - (perimeter * h) + (4 * h * h);
            if (baseArea <= 0) continue;
            double volume = baseArea * h;
            maxVolume = Math.max(maxVolume, volume);
        }

        System.out.printf("%.2f%n", maxVolume);
    }
}