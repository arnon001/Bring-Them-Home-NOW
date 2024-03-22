package games.arnon.bringthemhomenow;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Instant;

public final class BringThemHomeNow extends JavaPlugin {

    private final long StartUnixTimeStamp = 1696653000L;
    private final int hostageCount = 134; //test number, btw, this is the actual number
    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Bring Them Home Now Timer has been enabled!");
        new BukkitRunnable() {
            @Override
            public void run(){
                SendMessage();
            }
        }.runTaskTimer(this,0,20 * 60 * 10);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Bring Them Home Now timer has been disabled!");
    }

    private void SendMessage() {
        long currentTimeUnix = Instant.now().getEpochSecond();
        long durationSeconds = currentTimeUnix - StartUnixTimeStamp;

        long days = durationSeconds / (24 * 3600);
        long hours = (durationSeconds % (24 * 3600)) / 3600;
        long minutes = ((durationSeconds % (24 * 3600)) % 3600) / 60;
        long seconds = ((durationSeconds % (24 * 3600)) % 3600) % 60;

        String message = String.format("BRING THEM HOME NOW! It's been %d Days, %d hours, %d mins, %d sec %d Hostages",
                days, hours, minutes, seconds, hostageCount);

        Bukkit.getServer().broadcastMessage(message);
    }
}
