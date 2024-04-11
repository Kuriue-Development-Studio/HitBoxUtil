package taewookim.collisiondetector;

import org.bukkit.entity.Entity;
import taewookim.collisiondetector.element.Triangle;
import taewookim.hitbox.HitBox;

public class PolygonCollisionDetector extends CollisionDetector {

    private final Triangle[] triangles;
    public HitBox hitbox;

    public PolygonCollisionDetector(Triangle[] triangles) {
        this.triangles = triangles;
        for(Triangle triangle : triangles) {
            triangle.hitBox = hitbox;
        }
    }

    @Override
    public boolean isCollision(Entity en) {
        for(Triangle triangle : triangles) {
            if(triangle.isHit(en)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isCollisionPolygon(PolygonCollisionDetector collisionDetector) {
        for(Triangle triangle : triangles) {
            for(Triangle triangle1 : collisionDetector.triangles) {
                if(triangle.isCollision(triangle1)) {
                    return true;
                }
            }
        }
        return false;
    }
}
