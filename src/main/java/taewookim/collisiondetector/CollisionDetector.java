package taewookim.collisiondetector;

import org.bukkit.entity.Entity;

public abstract class CollisionDetector {

    public abstract boolean isCollision(Entity en);

    public abstract boolean isCollisionPolygon(PolygonCollisionDetector collisionDetector);

}
