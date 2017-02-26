package jp.minecraftday.spigot.minecraftdayadmin;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Optional;

/**
 * Created by masafumi on 2017/02/26.
 */
public class PlayerUtils {
    public static void setMetadata(Player player, String key, Object value, Plugin plugin) {
        player.setMetadata(key, new FixedMetadataValue(plugin, value));
    }

    public static Optional<Object> getMetadata(Player player, String key, Plugin plugin) {
        List<MetadataValue> values = player.getMetadata(key);
        for (MetadataValue value : values) {
            if (value.getOwningPlugin().getDescription().getName()
                    .equals(plugin.getDescription().getName())) {
                return Optional.ofNullable(value.value());
            }
        }
        return Optional.empty();
    }

}
