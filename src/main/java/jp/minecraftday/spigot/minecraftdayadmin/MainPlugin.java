package jp.minecraftday.spigot.minecraftdayadmin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class MainPlugin extends JavaPlugin implements Listener {
    private static final String KEY_FIRST_LOGIN_TIME = "minecraftday.fisrtLogin";
    private static final String KEY_ONTIME = "minecraftday.ontime";
    private FileConfiguration loginTimes;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(this, this);

        loginTimes = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "loginTime.yml"));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            loginTimes.save(new File(getDataFolder(), "loginTime.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLogin(final PlayerLoginEvent event) {
        Player p = event.getPlayer();
        long loginTime = loginTimes.getLong(p.getUniqueId().toString());
        PlayerUtils.setMetadata(p, KEY_FIRST_LOGIN_TIME, System.currentTimeMillis(), this);
        PlayerUtils.setMetadata(p, KEY_ONTIME, loginTime, this);
    }


    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLogin(final PlayerQuitEvent event) {
        Player p = event.getPlayer();
        long loginTime = (long)PlayerUtils.getMetadata(event.getPlayer(), KEY_FIRST_LOGIN_TIME, this).orElse(System.currentTimeMillis());
        long onTime = (long)PlayerUtils.getMetadata(event.getPlayer(), KEY_ONTIME, this).orElse(0);
        long currentOntime = onTime + (System.currentTimeMillis() - loginTime);
        loginTimes.set(p.getUniqueId().toString(), currentOntime);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.isCancelled()) return;

        long loginTime = (long)PlayerUtils.getMetadata(event.getPlayer(), KEY_FIRST_LOGIN_TIME, this).orElse(System.currentTimeMillis());
        long onTime = (long)PlayerUtils.getMetadata(event.getPlayer(), KEY_ONTIME, this).orElse(0);
        long currentOntime = onTime + (System.currentTimeMillis() - loginTime);

        //System.out.println(String.format("PlayerTime: %s", StringUtils.timeFormat(currentOntime)));
        //ここで、ある一定時間たったら、ランクをアップさせるとか実装できる
    }
}
