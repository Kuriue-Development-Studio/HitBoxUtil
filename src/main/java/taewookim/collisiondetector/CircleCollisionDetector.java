package taewookim.collisiondetector;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class CircleCollisionDetector extends CollisionDetector {

    final double x;
    final double y;
    final double z;
    final double r2;

    public CircleCollisionDetector(Location loc, double r) {
        x = loc.getX();
        y = loc.getY();
        z = loc.getZ();
        this.r2 = r*r;
    }

    @Override
    public boolean isCollision(Entity en) {
        Location enloc = en.getLocation();
        double dx = enloc.getX()+1.5-x;
        double dy = enloc.getY()+1.5-y;
        double dz = enloc.getZ()+1.5-z;
        return dx*dx+dy*dy+dz*dz<r2;
    }
}
