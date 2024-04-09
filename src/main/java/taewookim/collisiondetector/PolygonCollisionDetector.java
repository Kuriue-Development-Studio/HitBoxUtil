package taewookim.collisiondetector;

import org.bukkit.entity.Entity;
import taewookim.collisiondetector.element.Triangle;

public class PolygonCollisionDetector extends CollisionDetector {

    private Triangle[] triangles;

    @Override
    public boolean isCollision(Entity en) {
        for(Triangle triangle : triangles) {
            if(triangle.isHit(en)) {
                return true;
            }
        }
        return false;
    }
}
