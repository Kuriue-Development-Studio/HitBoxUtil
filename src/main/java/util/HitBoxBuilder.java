package util;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import taewookim.HitBoxPlugin;
import taewookim.collisiondetector.PolygonCollisionDetector;
import taewookim.hitbox.HitBox;

import java.util.ArrayList;

public class HitBoxBuilder {

    private int tick = 10;
    private ArrayList<PolygonCollisionDetector> detectors = new ArrayList<>();
    private LivingEntity owner = null;
    private Location loc = null;

    public HitBoxBuilder setTick(int tick) {
        this.tick = tick;
        return this;
    }

    public HitBoxBuilder addPolygonDetector(PolygonCollisionDetector detector) {
        detectors.add(detector);
        return this;
    }

    public HitBoxBuilder setOwner(LivingEntity owner) {
        this.owner = owner;
        return this;
    }

    public HitBoxBuilder setLocation(Location loc) {
        this.loc = loc;
        return this;
    }

    public void build(Class<? extends HitBox> clz) {
        try{
            HitBox hitbox = clz.getDeclaredConstructor(Location.class, LivingEntity.class).newInstance(loc, owner);
            HitBoxPlugin.plugin.addHitBox(hitbox);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
