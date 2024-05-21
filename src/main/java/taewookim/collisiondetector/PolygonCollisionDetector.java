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

    public void setHitbox(HitBox hitbox) {
        this.hitbox = hitbox;
        for(Triangle triangle : triangles) {
            triangle.setHitBox(hitbox);
        }
    }

    public void summonParticle() {
        for(Triangle triangle : triangles) {
            triangle.summonParticle();
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

    @Override
    public void moveCollision(double dx, double dy, double dz) {
        for(Triangle triangle : triangles) {
            triangle.moveTriangle(dx, dy, dz);
        }
    }
}
