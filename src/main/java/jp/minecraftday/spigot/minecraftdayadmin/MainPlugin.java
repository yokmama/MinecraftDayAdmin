package jp.minecraftday.spigot.minecraftdayadmin;

import org.bukkit.plugin.java.JavaPlugin;

public final class MainPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}