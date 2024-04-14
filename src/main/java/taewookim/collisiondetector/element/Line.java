package taewookim.collisiondetector.element;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import taewookim.hitbox.HitBox;

public class Line {

    private final double x;
    private final double y;
    private final double z;
    private final double dx;
    private final double dy;
    private final double dz;
    public HitBox hitbox;

    public Line(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.x = x1;
        this.y = y1;
        this.z = z1;
        this.dx = x2-x1;
        this.dy = y2-y1;
        this.dz = z2-z1;
    }

    public void sumonparticle() {
        hitbox.getChunk().getWorld().spawnParticle(Particle.CLOUD, x, y, z, 0, 0, 0, 0, 0);
        hitbox.getChunk().getWorld().spawnParticle(Particle.CLOUD, x+dx, y+dy, z+dz, 0, 0, 0, 0, 0);
    }

    public boolean isHitEntity(Entity entity) {
        Location loc = entity.getLocation();
        System.out.println(entity);
        double u = getU(loc.getX(), loc.getY(), loc.getZ(), dx, dy, dz);
        System.out.println(u);
        if(u<0||u>1) {
            return false;
        }
        double pdx = loc.getX()-x+u*dx;
        double pdy = loc.getY()+1.5-y+u*dy;
        double pdz = loc.getZ()-z+u*dz;
        return pdx<1&&pdy<1&&pdz<1;
    }

    public double[] getPoints() {
        return new double[]{x, y, z, x+dx, y+dy, z+dz};
    }

    public double[] getMainPoint() {
        return new double[]{x, y, z};
    }

    public double[] getCrossProduct(Line line) {
        return new double[]{dy*line.dz-dz*line.dy, dz*line.dx-dx*line.dz, dx*line.dy-dy*line.dx};
    }

    public double getU(double x, double y, double z, double dx, double dy, double dz) {
        double b = Math.abs(this.dx*dx+this.dy*dy+this.dz*dz);
        if(b<0.0001) {
            return -1;
        }
        return ((x-this.x)*dx+(y-this.y)*dy+(z-this.z*dz))/b;
    }

    public double sign(double x1, double y1,
                       double x2, double y2,
                       double x3, double y3) {
        return (x1-x3)*(y2-y3)-(x2-x3)*(y1-y3);
    }

    public boolean PointInTriangle(double x, double y,
                                   double x1, double y1,
                                   double x2, double y2,
                                   double x3, double y3) {
        double d1 = sign(x, y, x1, y1, x2, y2);
        double d2 = sign(x, y, x2, y2, x3, y3);
        double d3 = sign(x, y, x3, y3, x1, y1);
        return !((d1<0||d2<0)||d3<0)&&(d1>0||d2>0||d3>0);
    }

    public boolean isCrossWithTriangle(Triangle triangle) {
        double[] tmp = triangle.getMainPoint();
        double[] tcp = triangle.getCrossproduct();
        double u = getU(tmp[0], tmp[1], tmp[2], tcp[0], tcp[1], tcp[2]);
        if(u>=0&&u<=1) {
            return false;
        }
        double[] points = triangle.getPoints();
        double px = x+u*dx;
        double py = y+u*dy;
        double pz = z+u*dz;
        boolean is = false;
        if(tcp[0]>tcp[1]) {
            if(tcp[0]>tcp[2]) {
                //x가 큼
                is = PointInTriangle(py, pz, points[1], points[2],
                        points[4], points[5],
                        points[7], points[8]);
            }else {
                //z가 큼
                is = PointInTriangle(px, py, points[0], points[1],
                        points[3], points[4],
                        points[6], points[7]);
            }
        }else {
            if(tcp[1]>tcp[2]) {
                //y가 큼
                is = PointInTriangle(px, pz, points[0], points[2],
                        points[3], points[5],
                        points[6], points[8]);
            }else {
                //z가 큼
                is = PointInTriangle(px, py, points[0], points[1],
                        points[3], points[4],
                        points[6], points[7]);
            }
        }
        if(is) {
            hitbox.hitX = px;
            hitbox.hitY = py;
            hitbox.hitZ = pz;
            triangle.hitBox.hitX = px;
            triangle.hitBox.hitY = py;
            triangle.hitBox.hitZ = pz;
        }
        return is;
    }

}
