package taewookim;

import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.units.qual.K;
import taewookim.hitbox.HitBox;
import util.Key;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HitBoxPlugin extends JavaPlugin {

    public static HitBoxPlugin plugin;
    public Map<Key<Integer, Integer>, ArrayList<HitBox>> hitboxs = new HashMap<>();
    private ArrayList<HitBox> adding = new ArrayList<>();
    private boolean isupdating = false;

    public void addHitBox(HitBox hitBox) {
        if(isupdating) {
            adding.add(hitBox);
        }else {
            Key<Integer, Integer> key = new Key<>(hitBox.getChunkX(), hitBox.getChunkZ());
            hitboxs.computeIfAbsent(key, k -> new ArrayList<>());
            hitboxs.get(key).add(hitBox);
        }
    }

    private void Update() {
        BukkitRunnable brun = new BukkitRunnable() {
            @Override
            public void run() {
                ArrayList<HitBox> removing = new ArrayList<>();
                isupdating = true;
                for(Map.Entry<Key<Integer, Integer>, ArrayList<HitBox>> entry : hitboxs.entrySet()) {
                    for(HitBox hitBox : entry.getValue()) {
                        hitBox.update();
                        if(hitBox.isEnd()) {
                            removing.add(hitBox);
                        }
                    }
                }
                isupdating = false;
                for(HitBox hitBox : removing) {
                    Key<Integer, Integer> key = new Key<>(hitBox.getChunkX(), hitBox.getChunkZ());
                    hitboxs.get(key).remove(hitBox);
                }
                for(HitBox hitBox : adding) {
                    Key<Integer, Integer> key = new Key<>(hitBox.getChunkX(), hitBox.getChunkZ());
                    hitboxs.computeIfAbsent(key, k -> new ArrayList<>());
                    hitboxs.get(key).add(hitBox);
                }
                adding.clear();
            }
        };brun.runTaskTimer(this, 0, 0);
    }

    @Override
    public void onEnable() {
        plugin = this;
    }
}
