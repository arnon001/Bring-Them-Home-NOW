package games.arnon.bringthemhomenow;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Instant;

public final class BringThemHomeNow extends JavaPlugin {

    private int sendMessageInterval = 10;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Bring Them Home Now Timer has been enabled!");
        //Loading Config.
        loadConfig();
        //bStats
        int pluginId = 21400;
        Metrics metrics = new Metrics(this, pluginId);

        //Checking for new version of the plugin
        new UpdateChecker(this, 115760).getVersion(version -> {
            if(this.getDescription().getVersion().equals(version)) {
                getLogger().info("There isn't a new update available.");
            } else {
                getLogger().info("There is a new version download here: www.spigotmc.org/resources/115760/");
            }
        });


        new BukkitRunnable() {
            @Override
            public void run(){
                SendMessage();
            }
        }.runTaskTimer(this,0, 20L * 60 * sendMessageInterval);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Bring Them Home Now timer has been disabled!");
    }

    private void SendMessage() {
        long currentTimeUnix = Instant.now().getEpochSecond();
        String message = getMessage(currentTimeUnix);
        //the actual number of hostages
        int hostageCount = 73;
        String HostagesMsg = String.format(ChatColor.RED + "There Are %d Hostages Right Now In Gaza Kidnapped", hostageCount);

        Bukkit.getServer().broadcastMessage(ChatColor.RED + "BRING THEM HOME NOW!");
        Bukkit.getServer().broadcastMessage(message);
        Bukkit.getServer().broadcastMessage(HostagesMsg);
    }

    private static String getMessage(long currentTimeUnix) {

        long startUnixTimeStamp = 1696653000L;
        long durationSeconds = currentTimeUnix - startUnixTimeStamp;

        long days = durationSeconds / (24 * 3600);
        long hours = (durationSeconds % (24 * 3600)) / 3600;
        long minutes = ((durationSeconds % (24 * 3600)) % 3600) / 60;
        long seconds = ((durationSeconds % (24 * 3600)) % 3600) % 60;

        return String.format(ChatColor.RED + "It's been %d Days, %d hours, %d minutes and %d seconds since October 7th, 2023.",
                days, hours, minutes, seconds);
    }

    private void loadConfig() {
        this.saveDefaultConfig();
        this.reloadConfig();
        FileConfiguration config = this.getConfig();
        sendMessageInterval = config.getInt("SendMessageInMins");

    }
}
