package jp.minecraftday.spigot.minecraftdayadmin;

import jp.minecraftday.spigot.minecraftdayadmin.config.LoginTimes;
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
    LoginTimes loginTimes;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(this, this);
        loginTimes = new LoginTimes(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            loginTimes.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLogin(final PlayerLoginEvent event) {
        loginTimes.login(event.getPlayer());
    }


    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        loginTimes.logout(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.isCancelled()) return;

        long currentOntime = loginTimes.getOnTime(event.getPlayer());

        //System.out.println(String.format("Player OnTime: %s", StringUtils.timeFormat(currentOntime)));
        //ここで、ある一定時間たったら、ランクをアップさせるとか実装できる
    }
}
