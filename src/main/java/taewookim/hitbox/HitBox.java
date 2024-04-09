package taewookim.hitbox;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import taewookim.collisiondetector.CollisionDetector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class HitBox {

    private CollisionDetector[] collisiondetectors;
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

    protected abstract void enterHitBox(Entity en);
    protected abstract void quitHitBox(Entity en);

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

    public void update() {
        ArrayList<Entity> inhitboxs = new ArrayList<>();
        for(Chunk chunk : getRoundChunks()) {
            for(Entity en : chunk.getEntities()) {
                for(CollisionDetector detector : collisiondetectors) {
                    if(detector.isCollision(en)) {
                        inhitboxs.add(en);
                        break;
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

}
