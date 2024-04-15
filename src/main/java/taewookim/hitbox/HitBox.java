package taewookim.hitbox;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import taewookim.HitBoxPlugin;
import taewookim.collisiondetector.CollisionDetector;
import taewookim.collisiondetector.PolygonCollisionDetector;
import util.Key;

import java.util.*;

public abstract class HitBox {

    public double hitX;
    public double hitY;
    public double hitZ;
    private PolygonCollisionDetector[] collisiondetectors;
    private boolean isend = false;
    private int tick = 0;
    private final World world;
    private final int chunkx;
    private final int chunkz;
    private final LivingEntity owner;
    private final ArrayList<Entity> inhitboxs = new ArrayList<>();

    public HitBox(Location mainloc, LivingEntity owner) {
        Chunk mainchunk = mainloc.getChunk();
        world = mainloc.getWorld();
        chunkx = mainchunk.getX();
        chunkz = mainchunk.getZ();
        this.owner = owner;
    }

    public void setTick(int i) {
        tick = i;
    }

    public void summonParticle() {
        for(PolygonCollisionDetector detector : collisiondetectors) {
            detector.summonParticle();
        }
    }

    public void setCollisionDetectors(PolygonCollisionDetector[] detectors) {
        this.collisiondetectors = detectors;
        for(PolygonCollisionDetector detector : detectors) {
            detector.setHitbox(this);
        }
    }

    public Chunk getChunk() {
        return world.getChunkAt(chunkx, chunkz);
    }

    public int getChunkX() {
        return chunkx;
    }

    public int getChunkZ() {
        return chunkz;
    }

    protected abstract void enterHitBox(Entity en);
    protected abstract void quitHitBox(Entity en);
    protected abstract void collisionHitBox(HitBox hitBox);

    protected Chunk[] getRoundChunks() {
        ArrayList<Chunk> chunks = new ArrayList<>(9);
        for(int i = -1; i<2; i++) {
            for(int j = -1; j<2; j++) {
                Chunk chunk = world.getChunkAt(chunkx+i, chunkz+j);
                if(chunk.isLoaded()) {
                    chunks.add(chunk);
                }
            }
        }
        return chunks.toArray(Chunk[]::new);
    }

    public void entityCollisionDetect() {
        ArrayList<Entity> inhitboxs = new ArrayList<>();
        for(Chunk chunk : getRoundChunks()) {
            for(Entity en : chunk.getEntities()) {
                if(owner!=null&&!en.equals(owner)) {
                    for(CollisionDetector detector : collisiondetectors) {
                        if(detector.isCollision(en)) {
                            inhitboxs.add(en);
                            break;
                        }
                    }
                }
            }
        }
        ArrayList<Entity> nolist = (ArrayList<Entity>) this.inhitboxs.clone();
        nolist.removeAll(inhitboxs);
        inhitboxs.removeAll(this.inhitboxs);
        for(Entity en : inhitboxs) {
            enterHitBox(en);
            this.inhitboxs.add(en);
        }
        for(Entity en : nolist) {
            quitHitBox(en);
            this.inhitboxs.remove(en);
        }
    }

    public boolean isHit(HitBox hitBox) {
        for(PolygonCollisionDetector cd : collisiondetectors) {
            for(PolygonCollisionDetector cd1 : hitBox.collisiondetectors) {
                if(cd.isCollisionPolygon(cd1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void hitboxCollisionDetect() {
        Map<Key<Integer, Integer>, ArrayList<HitBox>> map = HitBoxPlugin.plugin.hitboxs;
        for(int x = -1; x<2; x++) {
            for(int z = -1; z<2; z++) {
                ArrayList<HitBox> hitBoxes = map.get(new Key<>(chunkx+x, chunkz+z));
                if(hitBoxes!=null&&!hitBoxes.isEmpty()) {
                    for(HitBox hitBox : hitBoxes) {
                        if(hitBox!=this&&isHit(hitBox)) {
                            collisionHitBox(hitBox);
                        }
                    }
                }
            }
        }
    }

    public void setEnd() {
        isend = true;
    }

    public boolean isEnd() {
        return isend;
    }

    public void update() {
        if(isend) {
            return;
        }
        hitboxCollisionDetect();
        if(isend) {
            return;
        }
        entityCollisionDetect();
        if(isend) {
            return;
        }
        tick--;
        if(tick<=0) {
            isend = true;
        }
    }

}
