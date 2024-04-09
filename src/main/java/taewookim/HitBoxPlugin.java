package taewookim;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import taewookim.hitbox.HitBox;
import util.Key;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HitBoxPlugin extends JavaPlugin {

    public static HitBoxPlugin plugin;
    public Map<Key<Integer, Integer>, ArrayList<HitBox>> hitboxs = new HashMap<>();

    public void Update() {
        BukkitRunnable brun = new BukkitRunnable() {
            @Override
            public void run() {

            }
        };brun.runTaskTimer(this, 0, 0);
    }

    @Override
    public void onEnable() {
        plugin = this;
    }
}
