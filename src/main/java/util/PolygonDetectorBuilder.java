package util;

import org.bukkit.util.Vector;
import taewookim.collisiondetector.PolygonCollisionDetector;
import taewookim.collisiondetector.element.Triangle;

import java.util.ArrayList;

public class PolygonDetectorBuilder {

    private ArrayList<Vector> points = new ArrayList<>();
    private int tick = 10;

    public void addPoint(double x, double y, double z) {
        points.add(new Vector(x, y, z));
    }

    public void addPoint(Vector v) {
        points.add(v);
    }

    public PolygonCollisionDetector create() {
        int trianglesize = points.size()-2;
        if(trianglesize>0) {
            Triangle[] triangles = new Triangle[trianglesize];
            Vector mainvector = points.get(0);
            double mainx = mainvector.getX();
            double mainy = mainvector.getY();
            double mainz = mainvector.getZ();
            for(int i = 0; i<trianglesize; i++) {
                Vector v1 = points.get(i+1);
                Vector v2 = points.get(i+2);
                triangles[i] = new Triangle(mainx, mainy, mainz,
                        v1.getX(), v1.getY(), v1.getZ(),
                        v2.getX(), v2.getY(), v2.getZ());
            }
            return new PolygonCollisionDetector(triangles);
        }
        return null;
    }

}
