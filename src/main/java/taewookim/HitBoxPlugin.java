package taewookim;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class HitBoxPlugin extends JavaPlugin {

    public void Update() {
        BukkitRunnable brun = new BukkitRunnable() {
            @Override
            public void run() {

            }
        };brun.runTaskTimer(this, 0, 0);
    }

    @Override
    public void onEnable() {

    }
}
