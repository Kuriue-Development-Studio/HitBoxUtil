package taewookim.collisiondetector.element;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import taewookim.hitbox.HitBox;

public class Triangle {

    private final Line line1;
    private final Line line2;
    private final double[] crossproduct;
    public HitBox hitBox;

    public Triangle(double x1, double y1, double z1,
                    double x2, double y2, double z2,
                    double x3, double y3, double z3) {
        line1 = new Line(x1, y1, z1, x2, y2, z2);
        line2 = new Line(x1, y1, z1, x3, y3, z3);
        line1.hitbox = hitBox;
        line2.hitbox = hitBox;
        crossproduct = line1.getCrossProduct(line2);
    }

    public void moveTriangle(double dx, double dy, double dz) {
        line1.moveLine(dx, dy, dz);
        line2.moveLine(dx, dy, dz);
    }

    public double[] getMainPoint() {
        return line1.getMainPoint();
    }
    public double[] getPoints() {
        double[] p1 = line1.getPoints();
        double[] p2 = line2.getPoints();
        return new double[]{p1[0], p1[1], p1[2],
                p1[3], p1[4], p1[5]
                , p2[3], p2[4], p2[5]};
    }

    public void setHitBox(HitBox hitBox) {
        this.hitBox = hitBox;
        line1.hitbox = hitBox;
        line2.hitbox = hitBox;
    }

    public void summonParticle() {
        line1.sumonparticle();
        line2.sumonparticle();
    }

    public double[] getCrossproduct() {
        return crossproduct;
    }

    public boolean isCollision(Triangle triangle) {
        return line1.isCrossWithTriangle(triangle)||line2.isCrossWithTriangle(triangle)
                ||triangle.line1.isCrossWithTriangle(this)||triangle.line2.isCrossWithTriangle(this);
    }

    public boolean isHit(Entity entity) {
        return line1.isHitEntity(entity)||line2.isHitEntity(entity);
    }

}
