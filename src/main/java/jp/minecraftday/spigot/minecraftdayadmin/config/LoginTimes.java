package jp.minecraftday.spigot.minecraftdayadmin.config;

import jp.minecraftday.spigot.minecraftdayadmin.util.PlayerUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * Created by masafumi on 2017/02/26.
 */
public class LoginTimes {
    private static final String FILENAME = "loginTime.yml";
    private static final String KEY_FIRST_LOGIN_TIME = "minecraftday.fisrtLogin";
    private static final String KEY_ONTIME = "minecraftday.ontime";
    private FileConfiguration loginTimes;
    private Plugin plugin;

    public LoginTimes(Plugin plugin){
        this.plugin = plugin;
        loginTimes = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), FILENAME));
    }

    public void commit() throws IOException {
        loginTimes.save(new File(plugin.getDataFolder(), FILENAME));
    }

    public void login(Player p){
        long loginTime = loginTimes.getLong(p.getUniqueId().toString());
        PlayerUtils.setMetadata(p, KEY_FIRST_LOGIN_TIME, System.currentTimeMillis(), plugin);
        PlayerUtils.setMetadata(p, KEY_ONTIME, loginTime, plugin);

    }

    public void logout(Player p){
        long loginTime = (long)PlayerUtils.getMetadata(p, KEY_FIRST_LOGIN_TIME, plugin).orElse(System.currentTimeMillis());
        long onTime = (long)PlayerUtils.getMetadata(p, KEY_ONTIME, plugin).orElse(0);
        long currentOntime = onTime + (System.currentTimeMillis() - loginTime);
        loginTimes.set(p.getUniqueId().toString(), currentOntime);
    }

    public long getOnTime(Player p){
        long loginTime = (long)PlayerUtils.getMetadata(p, KEY_FIRST_LOGIN_TIME, plugin).orElse(System.currentTimeMillis());
        long onTime = (long)PlayerUtils.getMetadata(p, KEY_ONTIME, plugin).orElse(0);
        long currentOntime = onTime + (System.currentTimeMillis() - loginTime);
        return currentOntime;
    }
}
